/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments.coverage;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.mei.securesim.components.instruments.IControlPanel;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.components.instruments.events.InstrumentsEventsFactory;
import org.mei.securesim.components.instruments.listeners.SignalUpdateEvent;
import org.mei.securesim.components.instruments.utils.SignalHandler;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.nodes.Node;

/***************************************
 * PARA A ANALISE DE COBERTURA TOTAL
 *
 * Para a Utilização do modulo de cobertura é necessário implementar duas classes
 * Adaptadas ao protocolo que se quer analisar:
 * - Implementar uma class derivada de NodeIdComparator que permite comparar o identificador
 * de dois nós da rede
 * - Implementar uma mensagem que permita ao protocolo depois fazer o encaminhamento
 * mas que implemente o interface ICoveraMessage
 *
 * - Esta class deve ser passada para o controlador com vista a que em cada evento de
 * Cobertura seja criada uma mensagem para reenvio periodico
 *
 * - É necessário implementar um interface ICoverageId ao nível do routing layer
 * se não existir esta implementação não é possivel ao controlador comparar os IDs dos nós
 * e com isso assinalar a chegada de uma mensagem
 * PARA A ANALISE DE COBERTURA PARCIAL (COM OS VIZINHOS)
 *
 * O depois de seleccionar os nós Sources o controlador vai enviar N mensagens para os nós vizinhos
 * se estes receberem pelo menos um threshold de mensagens então tem cobertura que deve
 * ser comparada com a cobertura fisica de radio.
 *
 */
/**
 *
 * @author posilva
 */
public class CoverageController {

    private static int uniqueMessagesID = 0;
    private int messageIntervalPerNode = 3;
    private int repetitionsPerNode = 1;
    private List sourcesNodes = new ArrayList();
    private List destinationNodes = new ArrayList();
    private Hashtable statistics = new Hashtable();
    private int totalMessagesSent = 0;
    private SignalHandler radioModelNeighbors = new SignalHandler();
    private SignalHandler routingModelNeighbors = new SignalHandler();
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    private Class totalCoverageMessageClass;
    private Class partialCoverageMessageClass;
    private NodeIdComparator nodeIdComparator;
    private boolean debug = true;
    private IControlPanel controlPanel;

    public IControlPanel getControlPanel() {
        return controlPanel;
    }

    public NodeIdComparator getNodeIdComparator() {
        return nodeIdComparator;
    }

    public void setNodeIdComparator(NodeIdComparator nodeIdComparator) {
        this.nodeIdComparator = nodeIdComparator;
    }

    private void log(String msg) {
        if (debug) {
            System.out.println(msg);
        }
    }

    public void runTest() {
    }

    public void setControlPanel(IControlPanel controlPanel) {
        this.controlPanel = controlPanel;

    }

    public void signalNeighborDetectionReset(CoverageModelEnum model) {
        switch (model) {
            case RADIO:
                radioModelNeighbors.clear();
                break;
            case ROUTING:
                routingModelNeighbors.clear();
                break;
        }


    }

    public class ListMessageEntry {

        ITotalCoverageMessage message;

        public ListMessageEntry(ITotalCoverageMessage message) {
            this.message = message;

        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ListMessageEntry)) {
                return false;
            }
            ListMessageEntry other = (ListMessageEntry) obj;
            return other.message.getUniqueId() == message.getUniqueId();
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + (this.message != null ? this.message.hashCode() : 0);
            return hash;
        }
    }

    public class StatisticEntry {

        List sentMessages;
        List receivedMessages;
        boolean valid = true;

        public StatisticEntry() {
            this.sentMessages = new ArrayList();
            this.receivedMessages = new ArrayList();

        }

        public void reset() {
            this.sentMessages = new ArrayList();
            this.receivedMessages = new ArrayList();
            valid = true;
        }

        public List getReceivedMessages() {
            return receivedMessages;
        }

        public void setReceivedMessages(List receivedMessages) {
            this.receivedMessages = receivedMessages;
        }

        public List getSentMessages() {
            return sentMessages;
        }

        public void setSentMessages(List sentMessages) {
            this.sentMessages = sentMessages;
        }
    }
    // TODO: Arranjar uma semantica para o ID por forma a poder implementar um ID adequado

    public synchronized void notifyMessageSent(Object message, Node node) {
        if (message instanceof ITotalCoverageMessage) { // é mensagem de cobertura
            if (node instanceof ITotalCoverageHandler) {
                ITotalCoverageHandler coverageNode = (ITotalCoverageHandler) node;
                ITotalCoverageMessage msg = (ITotalCoverageMessage) message;
                if (getNodeIdComparator().isEqual(msg.getSourceId(), coverageNode.getCoverageId())) { // primeiro envio
                    if (sourcesNodes.contains(node)) { // o nó é de source
                        totalMessagesSent++;
                        addToDestinationBox(msg);
                        refreshControlPanel();
                    }
                }
            }
        } else {
//            throw new IllegalArgumentException("Node must be a instance of ICoverageHandler");
        }

    }

    public synchronized void notifyMessageReception(Object message, Node node) {

        if (message instanceof ITotalCoverageMessage) { // é mensagem de cobertura total
            ITotalCoverageMessage msg = (ITotalCoverageMessage) message;
            ITotalCoverageHandler n = (ITotalCoverageHandler) node;

            if (getNodeIdComparator().isEqual(msg.getDestinationId(), n.getCoverageId())) { // chegou ao destino
                if (destinationNodes.contains(node)) { // o nó é de destino
                    updateStatistics(msg);
                    refreshControlPanel();
                }
            }
        }
    }

    public void unregisterSourceNode(Node node) {
        sourcesNodes.remove(node);
        node.getGraphicNode().setSource(false);
        refreshControlPanel();
    }

    public void unregisterDestinationNode(Node node) {
        destinationNodes.remove(node);
        node.getGraphicNode().setDestination(false);
        refreshControlPanel();
    }

    /**
     *
     * @param msg
     */
    private synchronized void addToDestinationBox(ITotalCoverageMessage msg) {
        StatisticEntry se = (StatisticEntry) statistics.get(msg.getDestinationId());
        if (se == null) {
            se = new StatisticEntry();
        }
        se.getSentMessages().add(new ListMessageEntry(msg));
        statistics.put(msg.getDestinationId(), se);
    }

    /**
     * 
     * @param msg
     */
    private synchronized void updateStatistics(ITotalCoverageMessage msg) {
        StatisticEntry se = (StatisticEntry) statistics.get(msg.getDestinationId());
        if (se == null) {
            return;
        }
        ListMessageEntry lme = new ListMessageEntry(msg);
        if (!se.getReceivedMessages().contains(lme)) {
            se.getReceivedMessages().add(lme);
            statistics.put(msg.getDestinationId(), se);
        }
    }

    /**
     * There 3 types of converage models
     * 1. Radio model: based on radio conectivity
     * 2. Logical model: based on routing layer specific neighbooring info
     * 3. Total model: selected N source nodes wich generate X events we can
     * evaluate ao many of this source nodes are covered 
     */
    public enum CoverageModelEnum {

        RADIO, ROUTING, LOGICAL;
    }
    /**
     * Controls the state of the controller
     */
    protected boolean enable = false;
    protected static CoverageController instance;

    public void updateNetworkSize() {
        radioModelNeighbors.setTotalOfNodes(SimulationController.getInstance().getSimulation().getSimulator().getNodes().size());
        routingModelNeighbors.setTotalOfNodes(SimulationController.getInstance().getSimulation().getSimulator().getNodes().size());

    }

    /**
     * Singleton
     * @return
     */
    public static CoverageController getInstance() {
        if (instance == null) {
            instance = new CoverageController();
        }
        return instance;
    }

    /**
     *
     * @param srcNode
     */
    public void registerSourceNode(Node srcNode) {

        if (!sourcesNodes.contains(srcNode)) {
            if (destinationNodes.contains(srcNode)) {
                throw new IllegalArgumentException("Source node cannot be destination node at same time!");
            }
            //      log("Registered a new node " + srcNode);
            sourcesNodes.add(srcNode);
            srcNode.getGraphicNode().setSource(true);
            refreshControlPanel();
        }
    }

    /**
     * 
     * @param dstNode
     */
    public void registerDestinationNode(Node dstNode) {
        if (!destinationNodes.contains(dstNode)) {
            if (sourcesNodes.contains(dstNode)) {
                throw new IllegalArgumentException("Destination node cannot be source node at same time!");
            }
            destinationNodes.add(dstNode);
            dstNode.getGraphicNode().setDestination(true);
            refreshControlPanel();
        }
    }

    /**
     * every time a message is arrived using a routing protocol we can signal
     * this event to increment de coverage evaluation
     * @param src
     * @param dst
     */
    public static void signalDestinationReceivedMessage(Node src, Node dst) {
    }

    /**
     * For Radio and Logical model each time a neighbor is discovered we must
     * signal this event to increment values of coverage
     * @param model
     * @param n
     */
    public synchronized void signalNeighborDetection(CoverageModelEnum model, Node n) {
        if (model == CoverageModelEnum.RADIO) {
            radioModelNeighbors.signal(n);
            SignalUpdateEvent event = new SignalUpdateEvent(n);
            event.setModel(model);
            event.setSignal(true);
            fireSignalUpdateEvent(event);
        } else if (model == CoverageModelEnum.ROUTING) {
            SignalUpdateEvent event = new SignalUpdateEvent(n);
            event.setModel(model);
            event.setSignal(true);
            fireSignalUpdateEvent(event);
            routingModelNeighbors.signal(n);
        }
    }

    /**
     * Read the value estimated for the coverage in the indicated model
     * (note that the calculations made in this function is based in threshold)
     * @param model
     * @return
     */
    public synchronized double getCoverageValueByModel(CoverageModelEnum model) {
        if (model == CoverageModelEnum.RADIO) {
            return radioModelNeighbors.calculateGlobalThreshold();
        } else if (model == CoverageModelEnum.ROUTING) {
            return routingModelNeighbors.calculateGlobalThreshold();
        } else {
            throw new IllegalArgumentException("Only radio and routing model can be calculated using this method");
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getRoutingModelThreshold() {
        return routingModelNeighbors.getThreshold();
    }

    public void setRoutingModelThreshold(int routingModelThreshold) {
        routingModelNeighbors.setThreshold(routingModelThreshold);
    }

    public int getRadioModelThreshold() {
        return radioModelNeighbors.getThreshold();
    }

    public void setRadioModelThreshold(int radioModelThreshold) {
        radioModelNeighbors.setThreshold(radioModelThreshold);
    }

    void fireSignalUpdateEvent(SignalUpdateEvent event) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == CoverageListener.class) {
                ((CoverageListener) listeners[i + repetitionsPerNode]).onSignalUpdate(event);
            }
        }
    }

    /**
     *
     * @param listener
     */
    public void addCoverageListener(CoverageListener listener) {
        listenerList.add(CoverageListener.class, listener);
    }

    /**
     *
     * @param listener
     */
    public void removeCoverageListener(CoverageListener listener) {
        listenerList.remove(CoverageListener.class, listener);
    }

    /**
     * 
     * @throws Exception
     */
    public void startTotalCoverageAnalisys() throws Exception {
        if (sourcesNodes.isEmpty()) {
            throw new IllegalStateException("Must add source nodes to coverage controller");
        }
        if (destinationNodes.isEmpty()) {
            throw new IllegalStateException("Must add destination nodes to coverage controller");
        }

        if (totalCoverageMessageClass == null) {
            throw new IllegalStateException("Must set a Class definition for totalCoverageMessageClass");
        }

        // para cada um das sources adiciono um evento de repetição na fila do simulador
        int ct = 0;
        for (Object srcNode : sourcesNodes) {
            for (Object dstNode : destinationNodes) {
                Node n = (Node) srcNode;
                long delay = ct++ * messageIntervalPerNode * Simulator.ONE_SECOND;
                TotalCoverageEvent evt = (TotalCoverageEvent) InstrumentsEventsFactory.createTotalCoverageEvent((short) repetitionsPerNode, delay, messageIntervalPerNode * Simulator.ONE_SECOND, getTotalCoverageTestMessageClass(), uniqueMessagesID++);
                evt.setSourceNode(n);
                evt.setDestinationNode((Node) dstNode);
                SimulationController.getInstance().getSimulation().getSimulator().addEvent(evt);
            }
        }
        refreshControlPanel();
    }

    public Class getTotalCoverageTestMessageClass() {
        return totalCoverageMessageClass;
    }

    public Class getTotalCoverageMessageClass() {
        return totalCoverageMessageClass;
    }

    public void setTotalCoverageMessageClass(Class totalCoverageMessageClass) {
        this.totalCoverageMessageClass = totalCoverageMessageClass;

    }

    public List getDestinationNodes() {
        return destinationNodes;
    }

    public List getSourcesNodes() {
        return sourcesNodes;
    }

    public Hashtable getStatistics() {
        return statistics;
    }

    public int getMessageIntervalPerNode() {
        return messageIntervalPerNode;
    }

    public void setMessageIntervalPerNode(int messageIntervalPerNode) {
        this.messageIntervalPerNode = messageIntervalPerNode;
    }

    public int getRepetitionsPerNode() {
        return repetitionsPerNode;
    }

    public void setRepetitionsPerNode(int repetitionsPerNode) {
        this.repetitionsPerNode = repetitionsPerNode;
    }

    public Class getPartialCoverageMessageClass() {
        return partialCoverageMessageClass;
    }

    public void setPartialCoverageMessageClass(Class partialCoverageMessageClass) {
        this.partialCoverageMessageClass = partialCoverageMessageClass;
    }

    private synchronized void refreshControlPanel() {
//        if (controlPanel != null) {
//            controlPanel.refresh();
//        }
    }
}
