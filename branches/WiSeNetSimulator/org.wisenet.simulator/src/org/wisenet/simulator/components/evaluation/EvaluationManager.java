package org.wisenet.simulator.components.evaluation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.evaluation.tests.TestResults;
import org.wisenet.simulator.core.Message;
import org.wisenet.simulator.core.energy.GlobalEnergyDatabase;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;

/**
 *
 * @author posilva
 */
public class EvaluationManager {

    MessageDatabase messageDatabase = new MessageDatabase();
    GlobalEnergyDatabase energyDatabase;
    List<Message> allMessages = new ArrayList<Message>();
    AbstractTest test;
    private boolean started;
    private int countAttackedMessages;
    private TestResults testResult;

    /**
     * Notify test start 
     * @param test
     *          a test instance that started
     */
    public void startTest(AbstractTest test) {
        if (!started) {
            countAttackedMessages = 0;
            this.test = test;
            energyDatabase = test.getSimulation().getEnergyController().createDatabase(test.getName(), true);
            started = true;
        }
    }

    /**
     * Notify test end
     */
    public void endTest() {
        if (started) {
            energyDatabase = test.getSimulation().getEnergyController().getDatabase(test.getName());
            createResult();
        }
    }

    /**
     *
     * @param message
     * @param routing
     */
    public void registerMessageSent(Object message, RoutingLayer routing) {
        messageDatabase.registerMessageSent((Message) message, routing);
    }

    /**
     *
     * @param message
     * @param routing
     */
    public void registerMessageArrived(Object message, RoutingLayer routing) {
    }

    /**
     *
     * @param message
     * @param routing
     */
    public void registerMessageReceivedDone(Object message, RoutingLayer routing) {
        // Message received right
        messageDatabase.registerMessageReceived((Message) message, routing);
    }

    /**
     *
     * @return
     */
    public GlobalEnergyDatabase getEnergyDatabase() {
        return energyDatabase;
    }

    /**
     * 
     * @return
     */
    public MessageDatabase getMessageDatabase() {
        return messageDatabase;
    }

    /**
     *
     * @return
     */
    public boolean isStarted() {
        return started;
    }

    public double getEnergyAvgPerNode() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Object o : getEnergyDatabase().getNodesEnergy().values()) {
            stats.addValue((Double) o);

        }
        return stats.getMean();
    }

    public double getTotalEnergy() {
        return getEnergyDatabase().getTotalEnergySpent();
    }

    public double getLatencyMin() {
        return getMessageDatabase().getLatencyMin();
    }

    public double getLatencyMax() {
        return getMessageDatabase().getLatencyMax();
    }

    public double getLatencyAvg() {
        return getMessageDatabase().getLatencyAvg();
    }

    public void incrementAttackedMessages() {
        countAttackedMessages++;
    }

    private void createResult() {
        testResult = new TestResults();
        testResult.setTestName(test.getName());
        testResult.setTotalOfMessagesAttacked(getCountAttackedMessages());
        testResult.setTotalNodes(test.getSimulation().getSimulator().getNodes().size());
        testResult.setTotalStableNodes(RoutingLayer.getController().getTotalStableNodes());
        testResult.setTotalSenderNodes(test.getSourceNodes().size());
        testResult.setTotalReceiverNodes(test.getReceiverNodes().size());
        testResult.setTotalAttackNodes(test.getAttackNodes().size());
        testResult.setAvgNeighboorsPerNode(test.getSimulation().getAverageNeighborsPerNode());
        testResult.setTotalOfMessagesSent(messageDatabase.getTotalNumberOfUniqueMessagesSent());
//        testResult.setMessagesPerNode(test.getInputParameters().getNumberOfMessagesPerNode());
//        testResult.setMessagesInterval(test.getInputParameters().getIntervalBetweenMessagesSent());
//        testResult.setMessagesRetransmissions(test.getInputParameters().getNumberOfRetransmissions());
        testResult.setReliabilityMessagesSent((int) messageDatabase.getTotalNumberOfUniqueMessagesSent());
        testResult.setReliabilityMessagesReceived((int) messageDatabase.getTotalMessagesReceived());
        testResult.setReliabilityPercent(messageDatabase.getReliabilityPercent());
        testResult.setCoverageSenderNodes(messageDatabase.getTotalSenderNodes());
        testResult.setCoverageReceivedNodes(messageDatabase.getTotalCoveredNodes());
        testResult.setCoveragePercent(messageDatabase.getCoveragePercent());
        testResult.setMinHopsPerMessage(messageDatabase.getLatencyMin());
        testResult.setMaxHopsPerMessage(messageDatabase.getLatencyMax());
        testResult.setAvgHopsPerMessage(messageDatabase.getLatencyAvg());
        testResult.setTotalEnergySpent(energyDatabase.getTotalEnergySpent());
        testResult.setAverageEnergyPerNode(getEnergyAvgPerNode());
    }

    public TestResults getTestResult() {
        return testResult;
    }

    public int getCountAttackedMessages() {
        return countAttackedMessages;
    }
}
