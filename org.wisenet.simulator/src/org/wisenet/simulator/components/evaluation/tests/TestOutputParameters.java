/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.common.PersistantObject;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class TestOutputParameters extends PersistantObject {

    /**
     *
     */
    protected long totalMessagesSent;
    /**
     *
     */
    protected int totalNodes;
    /**
     *
     */
    protected int averageMessagesSentPerNode;
    /**
     *
     */
    protected int totalStableNodes;
    /**
     *
     */
    protected double percentRadioNeighbors;
    /**
     *
     */
    protected double percentRoutingNeighbors;
    /**
     *
     */
    protected int totalOfMessagesReceived;
    /**
     *
     */
    protected double totalEnergySpent;
    /**
     *
     */
    protected double averageEnergyPerMessage;
    /**
     *
     */
    protected double averageHopNumberPerMessageSent;
    /**
     *
     */
    protected double averageTimePerMessageSent;
    /**
     *
     */
    protected TestInputParameters inputParams;

    /**
     *
     * @param inputParams
     */
    public TestOutputParameters(TestInputParameters inputParams) {
        this.inputParams = inputParams;

    }


    /**
     *
     * @return
     */
    public double getAverageEnergyPerMessage() {
        return averageEnergyPerMessage;
    }

    /**
     *
     * @param averageEnergyPerMessage
     */
    public void setAverageEnergyPerMessage(double averageEnergyPerMessage) {
        this.averageEnergyPerMessage = averageEnergyPerMessage;
    }

    /**
     *
     * @return
     */
    public double getAverageHopNumberPerMessageSent() {
        return averageHopNumberPerMessageSent;
    }

    /**
     *
     * @param averageHopNumberPerMessageSent
     */
    public void setAverageHopNumberPerMessageSent(double averageHopNumberPerMessageSent) {
        this.averageHopNumberPerMessageSent = averageHopNumberPerMessageSent;
    }

    /**
     *
     * @return
     */
    public int getAverageMessagesSentPerNode() {
        return averageMessagesSentPerNode;
    }

    /**
     *
     * @param averageMessagesSentPerNode
     */
    public void setAverageMessagesSentPerNode(int averageMessagesSentPerNode) {
        this.averageMessagesSentPerNode = averageMessagesSentPerNode;
    }

    /**
     *
     * @return
     */
    public double getAverageTimePerMessageSent() {
        return averageTimePerMessageSent;
    }

    /**
     *
     * @param averageTimePerMessageSent
     */
    public void setAverageTimePerMessageSent(double averageTimePerMessageSent) {
        this.averageTimePerMessageSent = averageTimePerMessageSent;
    }

    /**
     *
     * @return
     */
    public double getPercentRadioNeighbors() {
        return percentRadioNeighbors;
    }

    /**
     *
     * @param percentRadioNeighbors
     */
    public void setPercentRadioNeighbors(double percentRadioNeighbors) {
        this.percentRadioNeighbors = percentRadioNeighbors;
    }

    /**
     *
     * @return
     */
    public double getPercentRoutingNeighbors() {
        return percentRoutingNeighbors;
    }

    /**
     *
     * @param percentRoutingNeighbors
     */
    public void setPercentRoutingNeighbors(double percentRoutingNeighbors) {
        this.percentRoutingNeighbors = percentRoutingNeighbors;
    }

    /**
     *
     * @return
     */
    public double getTotalEnergySpent() {
        return totalEnergySpent;
    }

    /**
     *
     * @param totalEnergySpent
     */
    public void setTotalEnergySpent(double totalEnergySpent) {
        this.totalEnergySpent = totalEnergySpent;
    }

    /**
     *
     * @return
     */
    public long getTotalMessagesSent() {
        return totalMessagesSent;
    }

    /**
     *
     * @param totalMessagesSent
     */
    public void setTotalMessagesSent(long totalMessagesSent) {
        this.totalMessagesSent = totalMessagesSent;
    }

    /**
     *
     * @return
     */
    public int getTotalNodes() {
        return totalNodes;
    }

    /**
     *
     * @param totalNodes
     */
    public void setTotalNodes(int totalNodes) {
        this.totalNodes = totalNodes;
    }

    /**
     *
     * @return
     */
    public int getTotalOfMessagesReceived() {
        return totalOfMessagesReceived;
    }

    /**
     *
     * @param totalOfMessagesReceived
     */
    public void setTotalOfMessagesReceived(int totalOfMessagesReceived) {
        this.totalOfMessagesReceived = totalOfMessagesReceived;
    }

    /**
     *
     * @return
     */
    public int getTotalStableNodes() {
        return totalStableNodes;
    }

    /**
     *
     * @param totalStableNodes
     */
    public void setTotalStableNodes(int totalStableNodes) {
        this.totalStableNodes = totalStableNodes;
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
    }
}
