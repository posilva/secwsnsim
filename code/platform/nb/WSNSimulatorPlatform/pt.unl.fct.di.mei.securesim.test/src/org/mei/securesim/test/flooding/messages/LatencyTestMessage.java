/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.flooding.messages;

import org.mei.securesim.components.instruments.latency.ILatencyMessage;

/**
 *
 * @author CIAdmin
 */
public class LatencyTestMessage extends FloodingMessage implements ILatencyMessage {
    private int hops;

    public LatencyTestMessage() {
    }

    public LatencyTestMessage(byte[] payload) {
        super(payload);
    }

    public Object getSourceId() {
        return getSource();
    }

    public Object getDestinationId() {
        return getDestin();
    }

    public Object getUniqueId() {
        return getMessageData();
    }

    public void setSourceId(Object id) {
        setSource((Short)id);
    }

    public void setDestinationId(Object id) {
        setDestin((Short)id);
    }

    public void setUniqueId(Object id) {
        setMessageData(id.toString());
    }

    public void incrementHop() {
        hops++;
    }

    public int getNumberOfHops() {
        return hops;
    }

}
