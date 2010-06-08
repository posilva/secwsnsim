/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments;

import java.util.List;
import org.mei.securesim.components.instruments.listeners.CoverageListener;
import org.mei.securesim.components.instruments.listeners.SignalUpdateEvent;
import org.mei.securesim.components.instruments.utils.SignalHandler;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author posilva
 */
public class CoverageController {

    private List sources;
    private List destination;
    private SignalHandler radioModelNeighbors = new SignalHandler();
    private SignalHandler routingModelNeighbors = new SignalHandler();
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

    public void notifyMessageSent(Object message, Node node) {
    }

    public void notifyMessageReception(Object message, Node node) {
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

    public void updateNetworkSize(){
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
        sources.add(srcNode);
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
}
