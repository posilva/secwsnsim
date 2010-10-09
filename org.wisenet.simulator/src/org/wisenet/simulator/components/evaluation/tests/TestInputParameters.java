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

    public static String PREFIX_CFG = AbstractTest.PREFIX_CFG + ".inputparameters";
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

        configuration.addProperty(PREFIX_CFG + ".NumberOfAttackNodes", getNumberOfAttackNodes());
        configuration.addProperty(PREFIX_CFG + ".PercentOfAttackNodes", isPercentOfAttackNodes());
        configuration.addProperty(PREFIX_CFG + ".OnlyConsiderToAttackStableNodes", isOnlyConsiderToAttackStableNodes());

        configuration.addProperty(PREFIX_CFG + ".NumberOfReceiverNodes", getNumberOfReceiverNodes());
        configuration.addProperty(PREFIX_CFG + ".OnlyConsiderToReceiverSinkNodes", isOnlyConsiderToReceiverSinkNodes());
        configuration.addProperty(PREFIX_CFG + ".PercentOfReceiverNodes", isPercentOfReceiverNodes());


        configuration.addProperty(PREFIX_CFG + ".NumberOfSenderNodes", getNumberOfSenderNodes());
        configuration.addProperty(PREFIX_CFG + ".OnlyConsiderToSenderStableNodes", isOnlyConsiderToSenderStableNodes());
        configuration.addProperty(PREFIX_CFG + ".PercentOfSenderNodes", isPercentOfSenderNodes());

        configuration.addProperty(PREFIX_CFG + ".IntervalBetweenMessagesSent", getIntervalBetweenMessagesSent());
        configuration.addProperty(PREFIX_CFG + ".NumberOfMessagesPerNode", getNumberOfMessagesPerNode());
        configuration.addProperty(PREFIX_CFG + ".NumberOfRetransmissions", getNumberOfRetransmissions());

        configuration.addProperty(PREFIX_CFG + ".AttackSelected", getAttackSelected());
    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        setNumberOfAttackNodes(configuration.getInt(PREFIX_CFG + ".NumberOfAttackNodes"));
        setPercentOfAttackNodes(configuration.getBoolean(PREFIX_CFG + ".PercentOfAttackNodes"));
        setOnlyConsiderToAttackStableNodes(configuration.getBoolean(PREFIX_CFG + ".OnlyConsiderToAttackStableNodes"));

        setNumberOfReceiverNodes(configuration.getInt(PREFIX_CFG + ".NumberOfReceiverNodes"));
        setOnlyConsiderToReceiverSinkNodes(configuration.getBoolean(PREFIX_CFG + ".OnlyConsiderToReceiverSinkNodes"));
        setPercentOfReceiverNodes(configuration.getBoolean(PREFIX_CFG + ".PercentOfReceiverNodes"));


        setNumberOfSenderNodes(configuration.getInt(PREFIX_CFG + ".NumberOfSenderNodes"));
        setOnlyConsiderToSenderStableNodes(configuration.getBoolean(PREFIX_CFG + ".OnlyConsiderToSenderStableNodes"));
        setPercentOfSenderNodes(configuration.getBoolean(PREFIX_CFG + ".PercentOfSenderNodes"));

        setIntervalBetweenMessagesSent(configuration.getInt(PREFIX_CFG + ".IntervalBetweenMessagesSent"));
        setNumberOfMessagesPerNode(configuration.getInt(PREFIX_CFG + ".NumberOfMessagesPerNode"));
        setNumberOfRetransmissions(configuration.getInt(PREFIX_CFG + ".NumberOfRetransmissions"));

        setAttackSelected(configuration.getString(PREFIX_CFG + ".AttackSelected"));
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
