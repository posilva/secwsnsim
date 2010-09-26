/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.mac;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class MACLayerController {

    protected long totalMessagesSent = 0;
    protected long totalMessagesNotSent = 0;
    protected long totalMessagesCorrupted = 0;

    public long getTotalMessagesCorrupted() {
        return totalMessagesCorrupted;
    }

    public void incrementTotalMessagesCorrupted() {
        totalMessagesCorrupted += 1;
    }

    public long getTotalMessagesNotSent() {
        return totalMessagesNotSent;
    }

    public void incrementTotalMessagesNotSent() {
        totalMessagesNotSent += 1;
    }

    public long getTotalMessagesSent() {
        return totalMessagesSent;
    }

    public void incrementTotalMessagesSent() {
        totalMessagesSent += 1;
    }
}
