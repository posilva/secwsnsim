/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class TestOutputParameters {

    protected long totalMessagesSent;
    protected int totalNodes;
    protected int averageMessagesSentPerNode;
    protected int totalStableNodes;
    protected double percentRadioNeighbors;
    protected double percentRoutingNeighbors;
    protected int totalOfMessagesReceived;
    protected double totalEnergySpent;
    protected double averageEnergyPerMessage;
    protected double averageHopNumberPerMessageSent;
    protected double averageTimePerMessageSent;
    protected TestInputParameters inputParams;

    public TestOutputParameters(TestInputParameters inputParams) {
        this.inputParams = inputParams;

    }


    public double getAverageEnergyPerMessage() {
        return averageEnergyPerMessage;
    }

    public void setAverageEnergyPerMessage(double averageEnergyPerMessage) {
        this.averageEnergyPerMessage = averageEnergyPerMessage;
    }

    public double getAverageHopNumberPerMessageSent() {
        return averageHopNumberPerMessageSent;
    }

    public void setAverageHopNumberPerMessageSent(double averageHopNumberPerMessageSent) {
        this.averageHopNumberPerMessageSent = averageHopNumberPerMessageSent;
    }

    public int getAverageMessagesSentPerNode() {
        return averageMessagesSentPerNode;
    }

    public void setAverageMessagesSentPerNode(int averageMessagesSentPerNode) {
        this.averageMessagesSentPerNode = averageMessagesSentPerNode;
    }

    public double getAverageTimePerMessageSent() {
        return averageTimePerMessageSent;
    }

    public void setAverageTimePerMessageSent(double averageTimePerMessageSent) {
        this.averageTimePerMessageSent = averageTimePerMessageSent;
    }

    public double getPercentRadioNeighbors() {
        return percentRadioNeighbors;
    }

    public void setPercentRadioNeighbors(double percentRadioNeighbors) {
        this.percentRadioNeighbors = percentRadioNeighbors;
    }

    public double getPercentRoutingNeighbors() {
        return percentRoutingNeighbors;
    }

    public void setPercentRoutingNeighbors(double percentRoutingNeighbors) {
        this.percentRoutingNeighbors = percentRoutingNeighbors;
    }

    public double getTotalEnergySpent() {
        return totalEnergySpent;
    }

    public void setTotalEnergySpent(double totalEnergySpent) {
        this.totalEnergySpent = totalEnergySpent;
    }

    public long getTotalMessagesSent() {
        return totalMessagesSent;
    }

    public void setTotalMessagesSent(long totalMessagesSent) {
        this.totalMessagesSent = totalMessagesSent;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(int totalNodes) {
        this.totalNodes = totalNodes;
    }

    public int getTotalOfMessagesReceived() {
        return totalOfMessagesReceived;
    }

    public void setTotalOfMessagesReceived(int totalOfMessagesReceived) {
        this.totalOfMessagesReceived = totalOfMessagesReceived;
    }

    public int getTotalStableNodes() {
        return totalStableNodes;
    }

    public void setTotalStableNodes(int totalStableNodes) {
        this.totalStableNodes = totalStableNodes;
    }
}
