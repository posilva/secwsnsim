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
import org.mei.securesim.core.engine.DefaultMessage;
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

    private Hashtable networkNeighbors = new Hashtable();

    public enum ProtocolState {

        STABLE, BUILD
    }
    ProtocolState state = ProtocolState.BUILD;
    /**
     * Attributes
     */
    private long currentOWS = -1; // currente OWS value
    private RREQMsg parentRREQMsg; // message received from parent
    private int roundNumber = -1; // helper for sink node start new round
    private HashMap<Integer, byte[]> neighboorsSet = new HashMap<Integer, byte[]>();
    private byte[] sentRREQMAC; // record the mac sent for each first RREQ received

    /*
     * Base methods
     */
    @Override
    public void receiveMessage(Object message) {
        if (message instanceof INSENSMsg) {
            try {
                INSENSMsg msg = (INSENSMsg) ((INSENSMsg) message).clone();
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
        System.out.println("Message " + getNode().getMessage() + "   done...");
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

        if (getNode().isSinkNode()) {
            // DESCARTO AS MENSAGENS RECEBIDAS
        } else {
            RREQMsg msg = (RREQMsg) message;

            int senderID = msg.getId();
            long ows = msg.getOWS();
            byte[] parentMAC = msg.getMAC();

            if (verifyOWS(ows)) {
                currentOWS = ows;
                System.out.println("[" + getNode().getId() + "]\t Receive RREQ: " + msg.getMessageNumber() + " From " + msg.getOrigin());
                saveFirstRREQMessage(msg);
                newRouteDiscovery(msg);
            }
            addToNeighboorSet(senderID, INSENSFunctions.cloneMAC(parentMAC));
        }
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
        // create de message instance
        RREQMsg m = (RREQMsg) MessagesFactory.createRouteRequestMessage(getNode().getId(), currentOWS, ((INSENSNode) getNode()).getMacKey());

        sentRREQMAC = cloneMAC(m.getMAC());

        if (sentRREQMAC == null) {
            System.out.println("MAC NULL");
        }

        sendDelayedMessage(m);
        waitToCalculateRoutingTables();

    }

    private void addToNeighboorSet(int id, byte[] mac) {
        neighboorsSet.put(id, mac);
    }

    private void newRouteDiscovery(RREQMsg reqMessage) {
        resetNeighboorSet();

        state = ProtocolState.BUILD;

        INSENSMsg m = MessagesFactory.createForwardRouteRequestMessage(reqMessage, getNode().getId(), ((INSENSNode) getNode()).getMacKey());

        if (m.getMAC() == null) {
            System.out.println("newRouteDiscovery: MAC == NULL");
        }

        sentRREQMAC = cloneMAC(m.getMAC());

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
        Event e = new FeedbackWaitEvent();
        getNode().getSimulator().addEvent(e);
    }

    /**
     * 
     */
    private synchronized void sendFeedbackMessage() {
        try {
            PathInfo pathInfo = new PathInfo();
            pathInfo.id = getNode().getId();
            pathInfo.size = parentRREQMsg.getSize();
            pathInfo.pathToMe = new Vector();
            for (Integer i : parentRREQMsg.getPath()) {
                pathInfo.pathToMe.addElement(i.intValue());
            }
            pathInfo.MACRx = cloneMAC(parentRREQMsg.getMAC());

            NbrInfo nbrInfo = new NbrInfo();
            nbrInfo.size = neighboorsSet.size();
            Vector macs = new Vector();
            for (Integer key : neighboorsSet.keySet()) {
                macs.add(new MACS(key.intValue(), neighboorsSet.get(key)));
            }
            nbrInfo.macs = macs;

            FDBKMsg message = (FDBKMsg) MessagesFactory.createFeedbackMessage(currentOWS, ((INSENSNode) getNode()).getMacKey(), (NbrInfo) (nbrInfo.clone()), (PathInfo) (pathInfo.clone()), cloneMAC(neighboorsSet.get(parentRREQMsg.getId())));

            message.setPathInfo(pathInfo);
            message.setNbrInfo(nbrInfo);
            message.setOrigin(getNode().getId());
            System.out.println("[" + getNode().getId() + "]\t Send FeedBack: " + message.getMessageNumber());

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
        if (sentRREQMAC == null) {
            System.out.println("MAC NULL");
            return false;
        }
        return (Arrays.equals(m.getMACRParent(), sentRREQMAC));
    }

    /**
     * Ao receber uma mensagem de um nó descendente, é necessário copiar a msg
     * por forma a não arrastar referencias
     * @param m
     */
    private void forwardMessage2BaseStation(FDBKMsg m) {
        try {
            System.out.println("[" + getNode().getId() + "]\t Forward FDBK: "
                    + m.getMessageNumber() + " From " + m.getOrigin());

            // copia da mensagem
            FDBKMsg clone = (FDBKMsg) m.clone();
            clone.setMACRParent(cloneMAC(neighboorsSet.get(parentRREQMsg.getId())));
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

//        if (!getNode().getMacLayer().sendMessage(m, this)) {
//            System.out.println("Sending failed");
//        }
    }

    private void computeForwardingTables() {
        int numberOfNodes = networkNeighbors.size();

        Set<Edge> edges = verifyEdges();

        WeightedGraph graph = new WeightedGraph(numberOfNodes);

        for (int i = 0; i < numberOfNodes; i++) {
            graph.setLabel(i, "node_" + i);
        }

        for (Edge e : edges) {
            graph.addEdge(e.node1, e.node2);
        }
        final int[] pred = Dijkstra.dijkstra(graph, getNode().getId());
        for (int n = 0; n < numberOfNodes; n++) {
            Dijkstra.printPath(graph, pred, getNode().getId(), n);
        }




    }

    private void saveFeedbackMessage(FDBKMsg m) {
        System.out.println("Saved FDBK Message " + m.getMessageNumber() + " From " + m.getOrigin());

        if (sinkNodeVerificationOfMACFx(m)) {
            updateTableOfNetworkNeighbors(m);
        }

        // save the list of nodes existing in network

        // for each node verify the MAC

        // save list of parents

        // save neighborhood information

        // build routing tables

        //



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
     * @param m
     * @return
     */
    private boolean verifyNeighborHoodInformation(FDBKMsg m) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     *
     * @param m
     */
    private void updateTableOfNetworkNeighbors(FDBKMsg m) {
        Vector neighbors = (Vector) networkNeighbors.get(m.getPathInfo().id);
        if (neighbors == null) {
            networkNeighbors.put(m.getPathInfo().id, m.getNbrInfo());
        }
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
     * @return
     */
    private Set verifyEdges() {
        Set edges = new HashSet();
        for (Object o : networkNeighbors.keySet()) {
            Vector v = (Vector) networkNeighbors.get(o);
            for (Object o2 : v) {
                MACS m = (MACS) o2;
                if (nodesAreNeighbors(((Integer) o).intValue(), m.id)) {
                    edges.add(new Edge(((Integer) o).intValue(), m.id));
                }
            }



        }
        return edges;
    }

    /**
     *
     * @param n1
     * @param n2
     * @return
     */
    private boolean nodesAreNeighbors(int n1, int n2) {
        Vector nb1 = (Vector) networkNeighbors.get(n1);
        Vector nb2 = (Vector) networkNeighbors.get(n2);
        return true;



    }
    
    /**
     *
     */
    private void waitToCalculateRoutingTables() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Class para materializar um evento de temporização
     * de mensagens de feedback
     */
    class FeedbackWaitEvent extends Event {

        public FeedbackWaitEvent() {

            setTime(getNode().getSimulator().getSimulationTime() + INSENSConstants.FEEDBACK_WAITING_TIME);
        }

        @Override
        public void execute() {
            sendFeedbackMessage();
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
                System.out.println("SEND FAILED " + ((DefaultMessage) getMessage()).getMessageNumber());
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
}
