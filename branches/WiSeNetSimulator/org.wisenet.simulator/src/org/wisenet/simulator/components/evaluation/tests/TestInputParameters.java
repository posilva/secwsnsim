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
public class TestInputParameters extends PersistantObject {

    static String prefix = "inputparameters";
    protected int numberOfSenderNodes;
    protected int numberOfReceiverNodes;
    protected boolean percentOfSenderNodes;
    protected boolean percentOfReceiverNodes;
    protected boolean onlyConsiderToSenderStableNodes;
    protected boolean onlyConsiderToAttackStableNodes;
    protected boolean onlyConsiderToReceiverSinkNodes;
    protected int numberOfMessagesPerNode;
    protected int intervalBetweenMessagesSent;
    protected int numberOfRetransmissions;
    protected String attackSelected;
    protected int numberOfAttackNodes;
    protected boolean percentOfAttackNodes;

    public int getIntervalBetweenMessagesSent() {
        return intervalBetweenMessagesSent;
    }

    public void setIntervalBetweenMessagesSent(int intervalBetweenMessagesSent) {
        this.intervalBetweenMessagesSent = intervalBetweenMessagesSent;
    }

    public int getNumberOfMessagesPerNode() {
        return numberOfMessagesPerNode;
    }

    public void setNumberOfMessagesPerNode(int numberOfMessagesPerNode) {
        this.numberOfMessagesPerNode = numberOfMessagesPerNode;
    }

    public int getNumberOfReceiverNodes() {
        return numberOfReceiverNodes;
    }

    public void setNumberOfReceiverNodes(int numberOfReceiverNodes) {
        this.numberOfReceiverNodes = numberOfReceiverNodes;
    }

    public int getNumberOfRetransmissions() {
        return numberOfRetransmissions;
    }

    public void setNumberOfRetransmissions(int numberOfRetransmissions) {
        this.numberOfRetransmissions = numberOfRetransmissions;
    }

    public int getNumberOfSenderNodes() {
        return numberOfSenderNodes;
    }

    public void setNumberOfSenderNodes(int numberOfSenderNodes) {
        this.numberOfSenderNodes = numberOfSenderNodes;
    }

    public boolean isOnlyConsiderToReceiverSinkNodes() {
        return onlyConsiderToReceiverSinkNodes;
    }

    public void setOnlyConsiderToReceiverSinkNodes(boolean onlyConsiderToReceiverSinkNodes) {
        this.onlyConsiderToReceiverSinkNodes = onlyConsiderToReceiverSinkNodes;
    }

    public boolean isOnlyConsiderToSenderStableNodes() {
        return onlyConsiderToSenderStableNodes;
    }

    public void setOnlyConsiderToSenderStableNodes(boolean onlyConsiderToSenderStableNodes) {
        this.onlyConsiderToSenderStableNodes = onlyConsiderToSenderStableNodes;
    }

    public String getAttackSelected() {
        return attackSelected;
    }

    public void setAttackSelected(String attackSelected) {
        this.attackSelected = attackSelected;
    }

    public int getNumberOfAttackNodes() {
        return numberOfAttackNodes;
    }

    public void setNumberOfAttackNodes(int numberOfAttackNodes) {
        this.numberOfAttackNodes = numberOfAttackNodes;
    }

    public boolean isPercentOfAttackNodes() {
        return percentOfAttackNodes;
    }

    public void setPercentOfAttackNodes(boolean percentOfAttackNodes) {
        this.percentOfAttackNodes = percentOfAttackNodes;
    }

    public boolean isPercentOfSenderNodes() {
        return percentOfSenderNodes;
    }

    public void setPercentOfSenderNodes(boolean percentOfSenderNodes) {
        this.percentOfSenderNodes = percentOfSenderNodes;
    }

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {

        configuration.addProperty(prefix + ".NumberOfAttackNodes", getNumberOfAttackNodes());
        configuration.addProperty(prefix + ".PercentOfAttackNodes", isPercentOfAttackNodes());
        configuration.addProperty(prefix + ".OnlyConsiderToAttackStableNodes", isOnlyConsiderToAttackStableNodes());

        configuration.addProperty(prefix + ".NumberOfReceiverNodes", getNumberOfReceiverNodes());
        configuration.addProperty(prefix + ".OnlyConsiderToReceiverSinkNodes", isOnlyConsiderToReceiverSinkNodes());
        configuration.addProperty(prefix + ".PercentOfReceiverNodes", isPercentOfReceiverNodes());


        configuration.addProperty(prefix + ".NumberOfSenderNodes", getNumberOfSenderNodes());
        configuration.addProperty(prefix + ".OnlyConsiderToSenderStableNodes", isOnlyConsiderToSenderStableNodes());
        configuration.addProperty(prefix + ".PercentOfSenderNodes", isPercentOfSenderNodes());

        configuration.addProperty(prefix + ".IntervalBetweenMessagesSent", getIntervalBetweenMessagesSent());
        configuration.addProperty(prefix + ".NumberOfMessagesPerNode", getNumberOfMessagesPerNode());
        configuration.addProperty(prefix + ".NumberOfRetransmissions", getNumberOfRetransmissions());

        configuration.addProperty(prefix + ".AttackSelected", getAttackSelected());
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        setNumberOfAttackNodes(configuration.getInt(prefix + ".NumberOfAttackNodes"));
        setPercentOfAttackNodes(configuration.getBoolean(prefix + ".PercentOfAttackNodes"));
        configuration.getBoolean(prefix + ".OnlyConsiderToAttackStableNodes", isOnlyConsiderToAttackStableNodes());

        configuration.getInt(prefix + ".NumberOfReceiverNodes", getNumberOfReceiverNodes());
        configuration.getBoolean(prefix + ".OnlyConsiderToReceiverSinkNodes", isOnlyConsiderToReceiverSinkNodes());
        configuration.getBoolean(prefix + ".PercentOfReceiverNodes", isPercentOfReceiverNodes());


        configuration.getInt(prefix + ".NumberOfSenderNodes", getNumberOfSenderNodes());
        configuration.getBoolean(prefix + ".OnlyConsiderToSenderStableNodes", isOnlyConsiderToSenderStableNodes());
        configuration.getBoolean(prefix + ".PercentOfSenderNodes", isPercentOfSenderNodes());

        configuration.getInt(prefix + ".IntervalBetweenMessagesSent", getIntervalBetweenMessagesSent());
        configuration.getInt(prefix + ".NumberOfMessagesPerNode", getNumberOfMessagesPerNode());
        configuration.getInt(prefix + ".NumberOfRetransmissions", getNumberOfRetransmissions());

        configuration.getString(prefix + ".AttackSelected", getAttackSelected());
    }

    public boolean isPercentOfReceiverNodes() {
        return percentOfReceiverNodes;
    }

    public void setPercentOfReceiverNodes(boolean percentOfReceiverNodes) {
        this.percentOfReceiverNodes = percentOfReceiverNodes;
    }

    public boolean isOnlyConsiderToAttackStableNodes() {
        return onlyConsiderToAttackStableNodes;
    }

    public void setOnlyConsiderToAttackStableNodes(boolean onlyConsiderToAttackStableNodes) {
        this.onlyConsiderToAttackStableNodes = onlyConsiderToAttackStableNodes;
    }
}
