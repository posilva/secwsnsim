/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.flooding.messages;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class LatencyTestMessage extends FloodingMessage {

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

    public long getUniqueId() {
        return Long.valueOf(getMessageData());
    }

    public void setSourceId(Object id) {
        setSource((Short) id);
    }

    public void setDestinationId(Object id) {
        setDestin((Short) id);
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
