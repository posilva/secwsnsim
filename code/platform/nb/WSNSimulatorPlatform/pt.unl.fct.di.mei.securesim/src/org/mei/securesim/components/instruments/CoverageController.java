/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments;

import org.mei.securesim.components.instruments.messages.ICoverageMessage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.mei.securesim.components.instruments.events.InstrumentsEventsFactory;
import org.mei.securesim.components.instruments.events.TotalCoverageEvent;
import org.mei.securesim.components.instruments.listeners.CoverageListener;
import org.mei.securesim.components.instruments.listeners.SignalUpdateEvent;
import org.mei.securesim.components.instruments.utils.NodeIdComparator;
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

    private List sources = new ArrayList();
    private List destination = new ArrayList();
    private Hashtable statistics = new Hashtable();
    private int totalMessagesSent = 0;
    private SignalHandler radioModelNeighbors = new SignalHandler();
    private SignalHandler routingModelNeighbors = new SignalHandler();
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    private Class totalCoverageMessageClass;
    private NodeIdComparator nodeIdComparator;

    public NodeIdComparator getNodeIdComparator() {
        return nodeIdComparator;
    }

    public void setNodeIdComparator(NodeIdComparator nodeIdComparator) {
        this.nodeIdComparator = nodeIdComparator;
    }
    class StatisticEntry {

        int sentMessages;
        int receivedMessages;

        public StatisticEntry() {
            this.sentMessages = 0;
            this.receivedMessages = 0;

        }

        public int getReceivedMessages() {
            return receivedMessages;
        }

        public void setReceivedMessages(int receivedMessages) {
            this.receivedMessages = receivedMessages;
        }

        public int getSentMessages() {
            return sentMessages;
        }

        public void setSentMessages(int sentMessages) {
            this.sentMessages = sentMessages;
        }
    }
    // TODO: Arranjar uma semantica para o ID por forma a poder implementar um ID adequado

    public void notifyMessageSent(Object message, Node node) {
        if (message instanceof ICoverageMessage) { // é mensagem de cobertura
            ICoverageMessage msg = (ICoverageMessage) message;

            if ( getNodeIdComparator().isEqual(msg.getSourceNodeId() , node.getId())) { // primeiro envio
                if (sources.contains(node)) { // o nó é de source
                    addToDestinationBox(msg);
                }
            }
        }
    }

    public void notifyMessageReception(Object message, Node node) {
        if (message instanceof ICoverageMessage) { // é mensagem de cobertura
            ICoverageMessage msg = (ICoverageMessage) message;
            if ( getNodeIdComparator().isEqual(msg.getDestinationNodeId() , node.getId())) { // chegou ao destino
                if (destination.contains(node)) { // o nó é de destino
                    updateStatistics(msg);
                }
            }
        }
    }

    public void unregisterSourceNode(Node node) {
        sources.remove(node);
    }

    public void unregisterDestinationNode(Node node) {
        destination.remove(node);
    }

    private void addToDestinationBox(ICoverageMessage msg) {
        System.out.println("Adicionei à destination box ");

        StatisticEntry se = (StatisticEntry) statistics.get(msg.getDestinationNodeId());
        if (se == null) {
            se = new StatisticEntry();
        }

        se.setSentMessages(se.getSentMessages()+1);

        statistics.put(msg.getDestinationNodeId(), se);
    }

    private void updateStatistics(ICoverageMessage msg) {
        System.out.println("Actualizei estatisticas");

        StatisticEntry se = (StatisticEntry) statistics.get(msg.getDestinationNodeId());
        if (se == null) {
            return;
        }
        se.setReceivedMessages(se.getReceivedMessages()+1);
        statistics.put(msg.getDestinationNodeId(), se);
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
     * This method enables the programmer to select the nodes that will be 
     * subject to coverage evaluation relative a what destination source
     * @param srcNode
     * @param dstNode
     */
    public void registerSourceAndDestinationNode(Node srcNode, Node dstNode) {
    }

    public void registerSourceNode(Node srcNode) {

        if (!sources.contains(srcNode)) {
            System.out.println("Registered a new node " + srcNode);
            sources.add(srcNode);
        }
    }

    public void registerDestinationNode(Node dstNode) {
        destination.add(dstNode);
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
                ((CoverageListener) listeners[i + 1]).onSignalUpdate(event);
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


    void startTotalCoverageAnalisys() throws Exception{
        if (sources.isEmpty())
            throw  new IllegalStateException("Must add source nodes to coverage controller");
        if (sources.isEmpty())
            throw  new IllegalStateException("Must add destination nodes to coverage controller");
            // para cada um das sources adiciono um evento de repetição na fila do simulador

        for (Object object : sources) {
            Node n = (Node) object;
            
            TotalCoverageEvent evt = (TotalCoverageEvent) InstrumentsEventsFactory.createTotalCoverageEvent((short)20, Simulator.ONE_SECOND*5, getTotalCoverageTestMessageClass());
            evt.setSourceNode(n);
            SimulationController.getInstance().getSimulation().getSimulator().addEvent(evt);

        }

    }
    public Class getTotalCoverageTestMessageClass(){
        return totalCoverageMessageClass;
}
}
