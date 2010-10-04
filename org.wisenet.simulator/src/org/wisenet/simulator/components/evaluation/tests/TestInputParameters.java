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
public class TestInputParameters extends PersistantObject{

    protected int numberOfSenderNodes;
    protected int numberOfReceiverNodes;
    protected boolean percentOfSenderNodes;
    protected double percentOfSenderNodesValue;
    protected boolean onlyConsiderToSenderStableNodes;
    protected boolean onlyConsiderToReceiverSinkNodes;
    protected int numberOfMessagesPerNode;
    protected int intervalBetweenMessagesSent;
    protected int numberOfRetransmissions;
    protected String attackSelected;
    protected int numberOfAttackNodes;
    protected boolean percentOfAttackNodes;
    protected double percentOfAttackNodesValue;

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

    public double getPercentOfAttackNodesValue() {
        return percentOfAttackNodesValue;
    }

    public void setPercentOfAttackNodesValue(double percentOfAttackNodesValue) {
        this.percentOfAttackNodesValue = percentOfAttackNodesValue;
    }

    public boolean isPercentOfSenderNodes() {
        return percentOfSenderNodes;
    }

    public void setPercentOfSenderNodes(boolean percentOfSenderNodes) {
        this.percentOfSenderNodes = percentOfSenderNodes;
    }

    public double getPercentOfSenderNodesValue() {
        return percentOfSenderNodesValue;
    }

    public void setPercentOfSenderNodesValue(double percentOfSenderNodesValue) {
        this.percentOfSenderNodesValue = percentOfSenderNodesValue;
    }

    public void saveToXML(XMLConfiguration configuration) throws PersistantException {

    }

    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {

    }
}
