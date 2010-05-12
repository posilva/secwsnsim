package org.mei.securesim.test.insens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.components.crypto.CryptoFunctions;
import org.mei.securesim.test.insens.utils.INSENSConstants;
import org.mei.securesim.test.insens.messages.APPMsg;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.core.engine.Event;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.layers.routing.RoutingLayer;
import org.mei.securesim.test.insens.messages.FDBKMsg;
import org.mei.securesim.test.insens.messages.INSENSMsg;
import org.mei.securesim.test.insens.messages.MessagesFactory;
import org.mei.securesim.test.insens.messages.RREQMsg;
import org.mei.securesim.test.insens.messages.RUPDMsg;

import org.mei.securesim.test.insens.utils.INSENSFunctions;
import org.mei.securesim.test.insens.utils.MACS;
import org.mei.securesim.test.insens.utils.NbrInfo;
import org.mei.securesim.test.insens.utils.PathInfo;
import org.mei.securesim.test.insens.utils.graph.Dijkstra;
import org.mei.securesim.test.insens.utils.graph.WeightedGraph;
import static org.mei.securesim.test.insens.utils.INSENSFunctions.cloneMAC;

/**
 *
 * @author pedro
 */
public class INSENSRoutingLayer extends RoutingLayer {

    public enum TimeoutAction {

        WAIT_FEEDBACK, WAIT_BUILD_ROUTING
    }

    public enum ProtocolState {

        STABLE, ROUTE_REQUEST, BUILD_ROUTING_INFO;
    }
    private Set feedbackMessagesSet = new HashSet();
    private Hashtable tableOfNetworkNeighbors = new Hashtable();
    private Hashtable tableOfNetworkNodes = new Hashtable();
    private Hashtable tableOfForwardingTables=new Hashtable();
    private HashSet edges;
    private HashSet vertix;
    private HashMap neighboorsSet = new HashMap();

    private ProtocolState state = ProtocolState.ROUTE_REQUEST;
    private long currentOWS = -1; // currente OWS value
    private RREQMsg parentRREQMsg; // message received from parent
    private int roundNumber = -1; // helper for sink node start new round
    private byte[] myMACR; // record the mac sent for each first RREQ received
    /*
     * Base methods
     */
    @Override
    public void receiveMessage(Object message) {
        if (message instanceof INSENSMsg) {
            try {
                INSENSMsg msg = (INSENSMsg) message;
//                System.out.println("["+ getNode().getId()+ "] Receive Message:"+msg +" From " + msg.getOrigin() );
                msg = (INSENSMsg) ((INSENSMsg) message).clone();
                handleMessageReceive(msg);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Not a INSENS Message");
        }
    }

    @Override
    public void sendMessageDone() {
//        System.out.println("Message " + getNode().getMessage() + "   done...");
    }

    @Override
    public boolean sendMessage(Object message, Application app) {

        if (message instanceof INSENSMsg) {
            this.application = app;
            handleMessageSend(message);
            return true;
        } else {
            return false;
        }
    }

    /*
     * Working Methods for handling messages
     */
    private void handleMessageSend(Object message) {
        if (message instanceof APPMsg) {
            handleAPPSend(message);
        }
    }

    private void handleMessageReceive(Object message) {
        if (message instanceof RREQMsg) {
            handleRREQReceive(message);
        } else if (message instanceof FDBKMsg) {
            handleFDBKReceive(message);
        } else if (message instanceof RUPDMsg) {
            handleRUPDReceive(message);
        }
    }

    private void handleRUPDReceive(Object message) {
    }

    private void handleFDBKReceive(Object message) {

        FDBKMsg m = (FDBKMsg) message;
        if (getNode().isSinkNode()) {
            saveFeedbackMessage(m);
        } else {
            // verifica se eu sou o pai do vizinho que enviou a mensagem
            if (isFromMyChild(m)) {
                forwardMessage2BaseStation(m);
            } else {
//                System.out.println("["+ getNode().getId()+ "]\t Não é meu filho... descarto Origem: "+ m.getOrigin());
            }
        }
    }

    /**
     * tratamento de recepção de mensagem de Route Requests
     * @param message
     */
    private void handleRREQReceive(Object message) {
        RREQMsg msg = (RREQMsg) message;

        int senderID = msg.getId();
        long ows = msg.getOWS();
        byte[] parentMAC = msg.getMAC();

        if (getNode().isSinkNode()) {
            // DESCARTO AS MENSAGENS RECEBIDAS
        } else {

            if (verifyOWS(ows)) {
                currentOWS = ows;
//                System.out.println("[" + getNode().getId() + "]\t Receive RREQ: " + msg.getMessageNumber() + " From " + msg.getOrigin());
                saveFirstRREQMessage(msg);
                newRouteDiscovery(msg);
            }
        }
        addToNeighboorSet(senderID, INSENSFunctions.cloneMAC(parentMAC));
    }

    /**
     * permite controlar se se pode utilizar o encaminhamento para
     * comunicação de dados ou se encontra em fase de organização da rede
     * @return
     */
    public boolean isStable() {
        return state == ProtocolState.STABLE;
    }

    /**
     * Mensagens especiais para interacção com a camada de aplicação
     * @param message
     */
    private void handleAPPSend(Object message) {
        APPMsg appMsg = (APPMsg) message;

        if (appMsg.getAction() == INSENSConstants.ACTION_START) {
            if (getNode().isSinkNode()) {
                // this is a base station so... lets start protocol
                reorganizeNetwork();
            }
        }
    }

    /**
     * Inicio de uma nova organização da rede
     */
    private void reorganizeNetwork() {
        roundNumber++;
        currentOWS = INSENSFunctions.getNextOWS(roundNumber);
        startNetworkOrganization();
    }

    /**
     * Mensagem inicial de reorganização da rede
     */
    private void startNetworkOrganization() {
        /* create a initial route request message */
        RREQMsg m = (RREQMsg) MessagesFactory.createRouteRequestMessage(getNode().getId(), currentOWS, ((INSENSNode) getNode()).getMacKey());

        myMACR = cloneMAC(m.getMAC());

        if (myMACR == null) {
            System.out.println("MAC NULL");
        }

        sendDelayedMessage(m);
        waitToCalculateRoutingTables();

    }

    private void addToNeighboorSet(int id, byte[] mac) {
        //System.out.println(getNode().getId() +": Add Neighbor: "+id);
        neighboorsSet.put(id, mac);
    }

    private void newRouteDiscovery(RREQMsg reqMessage) {
        resetNeighboorSet();

        state = ProtocolState.BUILD_ROUTING_INFO;

        INSENSMsg m = MessagesFactory.createForwardRouteRequestMessage(reqMessage, getNode().getId(), ((INSENSNode) getNode()).getMacKey());

        if (m.getMAC() == null) {
            System.out.println("newRouteDiscovery: MAC == NULL");
        }

        myMACR = cloneMAC(m.getMAC());

        sendDelayedMessage(m);

        waitToSendFeedBack();
    }

    /**
     * Reinicia os dados referentes aos vizinhos conhecidos
     */
    private void resetNeighboorSet() {
        neighboorsSet.clear();
    }

    /**
     * Temporizador que controla o tempo de inicio de envio de Mensagem de
     * Feedback
     */
    private void waitToSendFeedBack() {
        Event e = new TimeoutWaitEvent();
        e.setTime(getNode().getSimulator().getSimulationTime() + INSENSConstants.FEEDBACK_WAITING_TIME);
        getNode().getSimulator().addEvent(e);
    }

    /**
     * 
     */
    private void sendFeedbackMessage() {

        try {
            PathInfo pathInfo = new PathInfo();
            pathInfo.id = getNode().getId();
            pathInfo.size = parentRREQMsg.getSize();
            pathInfo.pathToMe = new Vector();
            for (Object i : parentRREQMsg.getPath()) {
                int v = ((Integer) i).intValue();
                pathInfo.pathToMe.addElement(v);
            }
            pathInfo.MACRx = cloneMAC(myMACR);//parentRREQMsg.getMAC());

            NbrInfo nbrInfo = new NbrInfo();
            nbrInfo.size = neighboorsSet.size();
            Vector macs = new Vector();
            for (Object key : neighboorsSet.keySet()) {
                int i = ((Integer) key).intValue();
                macs.add(new MACS(i, cloneMAC((byte[]) neighboorsSet.get(key))));
            }
            nbrInfo.macs = macs;

            FDBKMsg message = (FDBKMsg) MessagesFactory.createFeedbackMessage(currentOWS, ((INSENSNode) getNode()).getMacKey(), (NbrInfo) (nbrInfo.clone()), (PathInfo) (pathInfo.clone()), cloneMAC((byte[]) neighboorsSet.get(parentRREQMsg.getId())));

            message.setPathInfo(pathInfo);
            message.setNbrInfo(nbrInfo);
            message.setOrigin(getNode().getId());
//            System.out.println("[" + getNode().getId() + "]\t Send FeedBack " + message.getMessageNumber() +" " + message);

            sendDelayedMessage(message);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param m
     * @return
     */
    private boolean isFromMyChild(FDBKMsg m) {
        if (myMACR == null) {
            System.out.println("MAC NULL");
            return false;
        }
        return (Arrays.equals(m.getMACRParent(), myMACR));
    }

    /**
     * Ao receber uma mensagem de um nó descendente, é necessário copiar a msg
     * por forma a não arrastar referencias
     * @param m
     */
    private void forwardMessage2BaseStation(FDBKMsg m) {
        try {
//            System.out.println("[" + getNode().getId() + "]\t Forward FDBK: "
//                    + m.getMessageNumber() + " From " + m.getOrigin());

            // copia da mensagem
            FDBKMsg clone = (FDBKMsg) m.clone();
            clone.setMACRParent(cloneMAC((byte[]) neighboorsSet.get(parentRREQMsg.getId())));
            clone.setForwardBy(getNode().getId());
            sendDelayedMessage(clone);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * enviar uma mensagem usando um temporizador
     * @param m
     */
    private void sendDelayedMessage(INSENSMsg m) {
        Event e = new DelayedMessageEvent(m);
        getNode().getSimulator().addEvent(e);
//        try {
//            System.out.println("[" + getNode().getId()+ "] Send Message: "+m);
//            if (!getNode().getMacLayer().sendMessage(m, this)) {
//                System.out.println("Sending failed");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void computeForwardingTables() {
        //@TODO MUST VALIDATE EDGE AND VERTIX SETS*/
        int numberOfNodes = vertix.size();
        System.out.println("Number of knowned nodes: " + numberOfNodes);
        WeightedGraph graph = new WeightedGraph(numberOfNodes);

        Vector nodeTable = new Vector();
        nodeTable.addAll(vertix);


        for (Object object : vertix) {
            int n1 = nodeTable.indexOf(object);
            graph.setLabel(n1, object);
        }

        for (Object edge : edges) {

            Edge e = (Edge) edge;
            int n1 = nodeTable.indexOf(e.node1);
            int n2 = nodeTable.indexOf(e.node2);
            graph.addEdge(n1, n2);
        }
        for (Object key : tableOfNetworkNodes.keySet()) {
            int node = ((Integer) key).intValue();
            getNodeDijkstraPaths(graph, nodeTable, numberOfNodes, node);
        }
//        getNodeDijkstraPaths(graph, nodeTable, numberOfNodes,(int) getNode().getId());
    }

    private void saveFeedbackMessage(FDBKMsg m) {
        if (state == ProtocolState.BUILD_ROUTING_INFO) {
            return;
        }
//        System.out.println("Saved FDBK Message " + m.getMessageNumber() + " From " + m.getOrigin());
        if (sinkNodeVerificationOfMACFx(m)) {
            feedbackMessagesSet.add(m);
        }
    }

    /**
     * 
     * @param msg
     */
    private void saveFirstRREQMessage(RREQMsg msg) {
        try {
            // se receber um ows diferente do corrente
            // então é uma nova epoca e esta é a primeira vez q recebe
            // então faz broadcast
            parentRREQMsg = (RREQMsg) msg.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(INSENSRoutingLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param m
     * @return
     */
    private boolean sinkNodeVerificationOfMACFx(FDBKMsg m) {
        byte[] receivedMACF = m.getMAC();
        int origin = m.getPathInfo().id;
        byte[] payload = m.toByteArray();
        byte[] key = INSENSFunctions.getShareMacKey(origin).getEncoded();
        boolean status = CryptoFunctions.verifyMessageIntegrityMAC(payload, receivedMACF, key);

//        System.out.println("Verification of FDBK Message from " + m.getPathInfo().id);
//        System.out.println("\t Received MACF: " + INSENSFunctions.toHex(receivedMACF));
//        System.out.println("\t Received Payload: " + INSENSFunctions.toHex(payload));
//        System.out.println("\t Origin KEY: " + INSENSFunctions.toHex(key));
//        System.out.println("\t Verification: " + (status ? "OK" : "FAILED"));

        return status;
    }

    /**
     *
     * @param ows
     * @return
     */
    private boolean verifyOWS(long ows) {
        return ows != currentOWS;
    }

    /**
     *
     */
    private void waitToCalculateRoutingTables() {
        Event e = new TimeoutWaitEvent(TimeoutAction.WAIT_BUILD_ROUTING);
        e.setTime(getNode().getSimulator().getSimulationTime() + INSENSConstants.FEEDBACK_WAITING_TIME * 2);
        getNode().getSimulator().addEvent(e);

    }

    /**
     * Class para materializar um evento de temporização
     * de mensagens de feedback
     */
    class TimeoutWaitEvent extends Event {

        TimeoutAction action = TimeoutAction.WAIT_FEEDBACK;

        public TimeoutWaitEvent(long time, TimeoutAction action) {
            super(time);
            this.action = action;
        }

        public TimeoutWaitEvent(TimeoutAction action) {
            this.action = action;
        }

        public TimeoutWaitEvent() {

            setTime(getNode().getSimulator().getSimulationTime() + INSENSConstants.FEEDBACK_WAITING_TIME);
        }

        @Override
        public void execute() {
            if (action == TimeoutAction.WAIT_FEEDBACK) {
                sendFeedbackMessage();
            } else if (action == TimeoutAction.WAIT_BUILD_ROUTING) {
                startBuildRoutingInfo();
            }

        }
    }

    /**
     * Class que possibilita o envio de uma mensagens usando
     * um temporizador
     */
    class DelayedMessageEvent extends Event {

        Object message;

        public Object getMessage() {
            return message;
        }

        public DelayedMessageEvent(Object message) {
            double result = INSENSConstants.MIN_DELAY_TIME_MESSAGE + (int) (Simulator.randomGenerator.random().nextDouble() * (INSENSConstants.MAX_DELAY_TIME_MESSAGE - INSENSConstants.MIN_DELAY_TIME_MESSAGE));
            setTime(getNode().getSimulator().getSimulationTime() + (int) result);
            this.message = message;
        }

        public DelayedMessageEvent(long time, Object message) {
            super(time);
            this.message = message;
        }

        @Override
        public void execute() {
            if (!getNode().getMacLayer().sendMessage(getMessage(), INSENSRoutingLayer.this)) {
//                System.out.println("SEND FAILED " + ((DefaultMessage) getMessage()).getMessageNumber());
            }

        }
    }

    /**
     * Class representing a edge from a graph
     */
    class Edge {

        int node1;
        int node2;

        private Edge(int n1, int n2) {
            node1 = n1;
            node2 = n2;
        }
    }

    /**
     *
     */
    private void startBuildRoutingInfo() {
        System.out.println("Starting build routing information");
        this.state = ProtocolState.BUILD_ROUTING_INFO;
        if (feedbackMessagesSet.size() > 0) {
            buildNetworkNeighborsTable();
            edges = (HashSet) verifyNetworkNeighborsTableAndGetEdges();
            if (edges != null) {
                if (edges.size() > 0) {
                    computeForwardingTables();
                }
            }
        }
    }

    /**
     * 
     */
    private void buildNetworkNeighborsTable() {
        tableOfNetworkNeighbors.clear();
        tableOfNetworkNodes.clear();

        for (Object message : feedbackMessagesSet) {
            FDBKMsg m = (FDBKMsg) message;
            int idOfNode = m.getPathInfo().id;
            byte[] MACRNode = cloneMAC(m.getPathInfo().MACRx);
            /* construir uma tabela com os nos da rede*/
            tableOfNetworkNodes.put(idOfNode, MACRNode);
            tableOfNetworkNeighbors.put(idOfNode, m.getNbrInfo().macs);

        }
        if (getNode().isSinkNode()) {
            tableOfNetworkNodes.put((int) getNode().getId(), myMACR);
            Vector v = new Vector();
            for (Object key : neighboorsSet.keySet()) {
                v.add(new MACS(((Integer) key).intValue(), cloneMAC((byte[]) neighboorsSet.get(key))));
            }
            tableOfNetworkNeighbors.put((int) getNode().getId(), v);
        }

    }

    /**
     * 
     */
    private Set verifyNetworkNeighborsTableAndGetEdges() {
        /* It's necessary to compare MACRx received back from each node
         * with MACRx reported by neighbors, if x is neighbor from y
         * than y must report x as a neighbor too
         */
        Hashtable neighborhoodTable = new Hashtable();
        edges = new HashSet();
        vertix = new HashSet();

        for (Object key : tableOfNetworkNeighbors.keySet()) {
            Vector neighbors = (Vector) tableOfNetworkNeighbors.get(key);
            for (Object neighbor : neighbors) {
                MACS n = (MACS) neighbor;
                byte[] MACRxReported = (byte[]) tableOfNetworkNodes.get(n.id);
                if (MACRxReported != null) {
                    if (Arrays.equals(MACRxReported, n.MACR)) {
                        /* if reported MAC is equal to MACR knowned by neighbor
                        thats a good start */
                        if (neighborhoodTable.get(key) == null) {
                            neighborhoodTable.put(key, new Vector());
                        }
                        Vector v = (Vector) neighborhoodTable.get(key);
                        v.add(n.id);
                        int k = ((Integer) key).intValue();
                        neighborhoodTable.put(k, v);
                    } else {
                        // this could be a case of intrusion or a tampering attack
                        System.out.println("MCR doesn't match:key: " + key + " " + INSENSFunctions.toHex(MACRxReported) + ",n.id: " + n.id + " " + INSENSFunctions.toHex(n.MACR));
                    }
                } else {
//                    System.out.println("MAC Reported is null: " + n.id);
                    // is means that the baseStation doesn't receive a feedback
                    // message from this node so we don't have a MAC to validate
                    // against the neighbor info
                }
            }
        }

        for (Object node1 : neighborhoodTable.keySet()) {
            Vector neighbors = (Vector) neighborhoodTable.get(node1);

            for (Object neighbor : neighbors) {

                Vector otherNeighbors = (Vector) neighborhoodTable.get(neighbor);
                if (otherNeighbors != null) {
                    /*node x said that have y as neigboor and y must confirm that information*/
                    if (otherNeighbors.contains(node1)) {
                        int v1 = ((Integer) node1).intValue();
                        int v2 = ((Integer) neighbor).intValue();
                        edges.add(new Edge(v1, v2));
                        vertix.add(v1);
                        vertix.add(v2);
//                        otherNeighbors.remove(node1);
//                        neighborhoodTable.put(neighbor, otherNeighbors);
                    }
                }
            }
        }
        //printEdges(edges);
        return edges;
    }

    /**
     * 
     * @param edges
     */
    private void printEdges(HashSet edges) {
        for (Object object : edges) {
            Edge e = (Edge) object;
            System.out.println(e.node1 + "," + e.node2);

        }
    }

    /**
     *
     * @param tableOfNetworkNeighbors
     */
    private void dumpTableOfNetworkNeighbors(Hashtable tableOfNetworkNeighbors) {
        if (tableOfNetworkNeighbors == null) {
            return;
        }
        for (Object key : tableOfNetworkNeighbors.keySet()) {
            System.out.print("Node " + key + ": ");
            Vector v = (Vector) tableOfNetworkNeighbors.get(key);
            for (Object object : v) {
                MACS m = (MACS) object;
                System.out.print("," + m.id);
            }
            System.out.print("\n");
        }
    }

    private void getNodeDijkstraPaths(WeightedGraph graph, Vector nodeTable, int numberOfNodes, int fromNode) {
        final int fromNodePosition = nodeTable.indexOf(fromNode);

        final int[] pred = Dijkstra.dijkstra(graph, fromNodePosition);

        for (int n = 0; n < numberOfNodes; n++) {

            if (n != fromNodePosition) {
                Dijkstra.printPath(graph, pred, fromNodePosition, n);
            }
        }
    }
}