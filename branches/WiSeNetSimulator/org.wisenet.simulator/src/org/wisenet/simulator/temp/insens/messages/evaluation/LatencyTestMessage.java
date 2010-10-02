/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.temp.insens.messages.evaluation;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class LatencyTestMessage extends EvaluationINSENSDATAMessage {

    private int hops;

    public LatencyTestMessage() {
        super("LTM".getBytes());
    }

    public LatencyTestMessage(byte[] payload) {
        super(payload);
    }

    public void incrementHop() {
        hops++;
    }

    public int getNumberOfHops() {
        return hops;
    }
}
