/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.messages.evaluation;

import org.mei.securesim.components.instruments.latency.ILatencyMessage;

/**
 *
 * @author CIAdmin
 */
public class LatencyTestMessage extends EvaluationINSENSDATAMessage implements ILatencyMessage {

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
