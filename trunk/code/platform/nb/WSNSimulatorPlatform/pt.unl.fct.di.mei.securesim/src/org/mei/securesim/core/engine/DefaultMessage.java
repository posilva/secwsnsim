/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.engine;

/**
 *
 * @author posilva
 */
public class DefaultMessage {
    static long numberOfTotalMessage=0;

    private byte[] payload;
    private int origin;
    private int destination;
    private long messageNumber;

    public static long getNumberOfTotalMessage() {
        return numberOfTotalMessage;
    }

    public long getMessageNumber() {
        return messageNumber;
    }

    public DefaultMessage(byte[] payload) {
        this.payload = payload;
        messageNumber=numberOfTotalMessage++;
    }

    public byte[] getPayload() {
        return payload;
    }
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public int size() {
        if(this.payload==null) return 0;
        return this.payload.length;
    }

    // A pedido do tiago araujo
    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

}
