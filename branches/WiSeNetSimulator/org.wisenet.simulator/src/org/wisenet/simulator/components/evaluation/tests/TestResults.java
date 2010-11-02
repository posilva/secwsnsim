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
public class TestResults extends PersistantObject {

    /**
     *
     */
    protected String testName;
    /**
     *
     */
    protected int totalNodes;
    /**
     *
     */
    protected int totalStableNodes;
    /**
     *
     */
    protected int totalSenderNodes;
    /**
     *
     */
    protected int totalReceiverNodes;
    /**
     *
     */
    protected int totalAttackNodes;
    /**
     * 
     */
    protected double avgNeighboorsPerNode;
    /**
     *
     */
    protected long totalOfMessagesSent;
    /**
     *
     */
    protected int totalOfMessagesReceived;
    /**
     *
     */
    protected int totalOfMessagesAttacked;
    /**
     *
     */
    protected int messagesPerNode;
    /**
     *
     */
    protected int messagesInterval;
    /**
     * 
     */
    protected int messagesRetransmissions;
    /**
     *
     */
    protected int averageMessagesSentPerNode;
    /**
     *
     */
    protected double totalEnergySpent;
    /**
     *
     */
    protected double averageEnergyPerNode;
    /**
     *
     */
    protected double avgHopsPerMessage;
    /**
     *
     */
    protected double minHopsPerMessage;
    /**
     * 
     */
    protected double maxHopsPerMessage;
    /**
     *
     */
    protected double averageTimePerMessageSent;
    /**
     *
     */
    protected int reliabilityMessagesSent;
    /**
     *
     */
    protected int reliabilityMessagesReceived;
    /**
     *
     */
    protected double reliabilityPercent;
    /**
     *
     */
    protected int coverageSenderNodes;
    /**
     *
     */
    protected int coverageReceivedNodes;
    /**
     *
     */
    protected double coveragePercent;

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

    public double getAverageEnergyPerNode() {
        return averageEnergyPerNode;
    }

    public void setAverageEnergyPerNode(double averageEnergyPerNode) {
        this.averageEnergyPerNode = averageEnergyPerNode;
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

    public double getAvgHopsPerMessage() {
        return avgHopsPerMessage;
    }

    public void setAvgHopsPerMessage(double avgHopsPerMessage) {
        this.avgHopsPerMessage = avgHopsPerMessage;
    }

    public double getAvgNeighboorsPerNode() {
        return avgNeighboorsPerNode;
    }

    public void setAvgNeighboorsPerNode(double avgNeighboorsPerNode) {
        this.avgNeighboorsPerNode = avgNeighboorsPerNode;
    }

    public double getCoveragePercent() {
        return coveragePercent;
    }

    public void setCoveragePercent(double coveragePercent) {
        this.coveragePercent = coveragePercent;
    }

    public int getCoverageReceivedNodes() {
        return coverageReceivedNodes;
    }

    public void setCoverageReceivedNodes(int coverageReceivedNodes) {
        this.coverageReceivedNodes = coverageReceivedNodes;
    }

    public int getCoverageSenderNodes() {
        return coverageSenderNodes;
    }

    public void setCoverageSenderNodes(int coverageSenderNodes) {
        this.coverageSenderNodes = coverageSenderNodes;
    }

    public double getMaxHopsPerMessage() {
        return maxHopsPerMessage;
    }

    public void setMaxHopsPerMessage(double maxHopsPerMessage) {
        this.maxHopsPerMessage = maxHopsPerMessage;
    }

    public int getMessagesInterval() {
        return messagesInterval;
    }

    public void setMessagesInterval(int messagesInterval) {
        this.messagesInterval = messagesInterval;
    }

    public int getMessagesPerNode() {
        return messagesPerNode;
    }

    public void setMessagesPerNode(int messagesPerNode) {
        this.messagesPerNode = messagesPerNode;
    }

    public int getMessagesRetransmissions() {
        return messagesRetransmissions;
    }

    public void setMessagesRetransmissions(int messagesRetransmissions) {
        this.messagesRetransmissions = messagesRetransmissions;
    }

    public double getMinHopsPerMessage() {
        return minHopsPerMessage;
    }

    public void setMinHopsPerMessage(double minHopsPerMessage) {
        this.minHopsPerMessage = minHopsPerMessage;
    }

    public int getReliabilityMessagesReceived() {
        return reliabilityMessagesReceived;
    }

    public void setReliabilityMessagesReceived(int reliabilityMessagesReceived) {
        this.reliabilityMessagesReceived = reliabilityMessagesReceived;
    }

    public int getReliabilityMessagesSent() {
        return reliabilityMessagesSent;
    }

    public void setReliabilityMessagesSent(int reliabilityMessagesSent) {
        this.reliabilityMessagesSent = reliabilityMessagesSent;
    }

    public double getReliabilityPercent() {
        return reliabilityPercent;
    }

    public void setReliabilityPercent(double reliabilityPercent) {
        this.reliabilityPercent = reliabilityPercent;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTotalAttackNodes() {
        return totalAttackNodes;
    }

    public void setTotalAttackNodes(int totalAttackNodes) {
        this.totalAttackNodes = totalAttackNodes;
    }

    public double getTotalEnergySpent() {
        return totalEnergySpent;
    }

    public void setTotalEnergySpent(double totalEnergySpent) {
        this.totalEnergySpent = totalEnergySpent;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(int totalNodes) {
        this.totalNodes = totalNodes;
    }

    public int getTotalOfMessagesAttacked() {
        return totalOfMessagesAttacked;
    }

    public void setTotalOfMessagesAttacked(int totalOfMessagesAttacked) {
        this.totalOfMessagesAttacked = totalOfMessagesAttacked;
    }

    public int getTotalOfMessagesReceived() {
        return totalOfMessagesReceived;
    }

    public void setTotalOfMessagesReceived(int totalOfMessagesReceived) {
        this.totalOfMessagesReceived = totalOfMessagesReceived;
    }

    public long getTotalOfMessagesSent() {
        return totalOfMessagesSent;
    }

    public void setTotalOfMessagesSent(long totalOfMessagesSent) {
        this.totalOfMessagesSent = totalOfMessagesSent;
    }

    public int getTotalReceiverNodes() {
        return totalReceiverNodes;
    }

    public void setTotalReceiverNodes(int totalReceiverNodes) {
        this.totalReceiverNodes = totalReceiverNodes;
    }

    public int getTotalSenderNodes() {
        return totalSenderNodes;
    }

    public void setTotalSenderNodes(int totalSenderNodes) {
        this.totalSenderNodes = totalSenderNodes;
    }

    public int getTotalStableNodes() {
        return totalStableNodes;
    }

    public void setTotalStableNodes(int totalStableNodes) {
        this.totalStableNodes = totalStableNodes;
    }

    public String[] toRow() {
        return new String[]{
                    getTestName() + "",
                    getTotalOfMessagesAttacked() + "",
                    getTestName() + "",
                    getTotalNodes() + "",
                    getTotalStableNodes() + "",
                    getTotalSenderNodes() + "",
                    getTotalReceiverNodes() + "",
                    getTotalAttackNodes() + "",
                    getAvgNeighboorsPerNode() + "",
                    getTotalOfMessagesSent() + "",
                    getTotalReceiverNodes() + "",
                    getTotalOfMessagesAttacked() + "",
                    getMessagesPerNode() + "",
                    getMessagesInterval() + "",
                    getMessagesRetransmissions() + "",
                    getReliabilityMessagesSent() + "",
                    getReliabilityMessagesReceived() + "",
                    getReliabilityPercent() + "",
                    getCoverageSenderNodes() + "",
                    getCoverageReceivedNodes() + "",
                    getCoveragePercent() + "",
                    getMinHopsPerMessage() + "",
                    getMaxHopsPerMessage() + "",
                    getAvgHopsPerMessage() + "",
                    getTotalEnergySpent() + "",
                    getAverageEnergyPerNode() + ""
                };
    }
}
