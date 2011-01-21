/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import java.util.Collection;
import org.wisenet.simulator.components.simulation.AbstractSimulation;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class AdHocTestInfo {

    private static AdHocTestInfo instance = null;
    int numberOfSourceNodes = 0;
    int numberOfReceiverNodes = 0;
    int numberOfAttackedNodes = 0;
    int numberOfStableSourceNodes = 0;

    public static AdHocTestInfo getInstance() {
        if (instance == null) {
            instance = new AdHocTestInfo();
        }

        return instance;
    }

    public AdHocTestInfo retrieveInfo(AbstractSimulation simulation) {
        if (!(simulation != null && simulation.getSimulator() != null && simulation.isNetworkDeployed())) {
            throw new IllegalStateException("Simulation or simulator invalid status ");
        }
        reset();
        Collection<Node> nodes = simulation.getSimulator().getNodes();
        for (Node node : nodes) {


            if (node.isSource()) {
                numberOfSourceNodes++;
                if (node.getRoutingLayer().isStable()) {
                    numberOfStableSourceNodes++;
                }
            }
            if (node.isReceiver()) {
                numberOfReceiverNodes++;
                if (node.isSinkNode()) {
                    numberOfSinkReceiverNodes++;
                }
            }
            if (node.getRoutingLayer().isUnderAttack()) {
                numberOfAttackedNodes++;
                if (node.getRoutingLayer().isStable()) {
                    numberOfStableAttackedNodes++;
                }
            }
        }
        return this;
    }

    public int getNumberOfAttackedNodes() {
        return numberOfAttackedNodes;
    }

    public void setNumberOfAttackedNodes(int numberOfAttackedNodes) {
        this.numberOfAttackedNodes = numberOfAttackedNodes;
    }

    public int getNumberOfReceiverNodes() {
        return numberOfReceiverNodes;
    }

    public void setNumberOfReceiverNodes(int numberOfReceiverNodes) {
        this.numberOfReceiverNodes = numberOfReceiverNodes;
    }

    public int getNumberOfSinkReceiverNodes() {
        return numberOfSinkReceiverNodes;
    }

    public void setNumberOfSinkReceiverNodes(int numberOfSinkReceiverNodes) {
        this.numberOfSinkReceiverNodes = numberOfSinkReceiverNodes;
    }

    public int getNumberOfSourceNodes() {
        return numberOfSourceNodes;
    }

    public void setNumberOfSourceNodes(int numberOfSourceNodes) {
        this.numberOfSourceNodes = numberOfSourceNodes;
    }

    public int getNumberOfStableAttackedNodes() {
        return numberOfStableAttackedNodes;
    }

    public void setNumberOfStableAttackedNodes(int numberOfStableAttackedNodes) {
        this.numberOfStableAttackedNodes = numberOfStableAttackedNodes;
    }

    public int getNumberOfStableSourceNodes() {
        return numberOfStableSourceNodes;
    }

    public void setNumberOfStableSourceNodes(int numberOfStableSourceNodes) {
        this.numberOfStableSourceNodes = numberOfStableSourceNodes;
    }
    int numberOfSinkReceiverNodes;
    int numberOfStableAttackedNodes;

    private void reset() {
        numberOfSourceNodes = 0;
        numberOfReceiverNodes = 0;
        numberOfAttackedNodes = 0;
        numberOfStableSourceNodes = 0;
        numberOfStableAttackedNodes = 0;
        numberOfSinkReceiverNodes = 0;
    }

    @Override
    public String toString() {

        String result = "";
        result += " Source Nodes: " + numberOfSourceNodes;
        result += " \t(Stable: " + numberOfStableSourceNodes + ")";
        result += " \n\nReceiver Nodes: " + numberOfReceiverNodes;
        result += " \t(Sink: " + numberOfSinkReceiverNodes + ")";
        result += " \n\n Attacked Nodes: " + numberOfAttackedNodes;
        result += " \t(Stable: " + numberOfStableAttackedNodes + ")";
        return result;

    }
}
