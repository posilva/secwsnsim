/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.evaluation;

/**
 *
 * @author CIAdmin
 */
public class EvaluationTestSettings {

    int nrSenderNodes;
    int nrReceiverNodes;
    boolean onlyStableNodes;
    boolean onlySinkNodes;
    boolean senderNodesPercent;
    boolean receiverNodesPercent;
    int nrMessages;
    int nrRetransmissions;
    int interval;
    int delay;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getNrMessages() {
        return nrMessages;
    }

    public void setNrMessages(int nrMessages) {
        this.nrMessages = nrMessages;
    }

    public int getNrReceiverNodes() {
        return nrReceiverNodes;
    }

    public void setNrReceiverNodes(int nrReceiverNodes) {
        this.nrReceiverNodes = nrReceiverNodes;
    }

    public int getNrRetransmissions() {
        return nrRetransmissions;
    }

    public void setNrRetransmissions(int nrRetransmissions) {
        this.nrRetransmissions = nrRetransmissions;
    }

    public int getNrSenderNodes() {
        return nrSenderNodes;
    }

    public void setNrSenderNodes(int nrSenderNodes) {
        this.nrSenderNodes = nrSenderNodes;
    }

    public boolean isOnlySinkNodes() {
        return onlySinkNodes;
    }

    public void setOnlySinkNodes(boolean onlySinkNodes) {
        this.onlySinkNodes = onlySinkNodes;
    }

    public boolean isOnlyStableNodes() {
        return onlyStableNodes;
    }

    public void setOnlyStableNodes(boolean onlyStableNodes) {
        this.onlyStableNodes = onlyStableNodes;
    }

    public boolean isReceiverNodesPercent() {
        return receiverNodesPercent;
    }

    public void setReceiverNodesPercent(boolean receiverNodesPercent) {
        this.receiverNodesPercent = receiverNodesPercent;
    }

    public boolean isSenderNodesPercent() {
        return senderNodesPercent;
    }

    public void setSenderNodesPercent(boolean senderNodesPercent) {
        this.senderNodesPercent = senderNodesPercent;
    }
}
