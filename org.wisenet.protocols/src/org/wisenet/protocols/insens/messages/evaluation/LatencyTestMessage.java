/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.protocols.insens.messages.evaluation;

/**
 *
 * @author CIAdmin
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
