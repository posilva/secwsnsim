/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.engine;

import java.awt.Color;
import java.util.Arrays;

/**
 *
 * @author posilva
 */
public class DefaultMessage implements Cloneable {

    static long numberOfTotalMessage = 0;
    private byte[] payload;
    private short origin;
    private short forwardBy;
    private short destination;
    private long messageNumber;
    protected Color color = Color.green;
    protected boolean showColor=false;
    public static long getNumberOfTotalMessage() {
        return numberOfTotalMessage;
    }

    public long getMessageNumber() {
        return messageNumber;
    }

    public DefaultMessage(byte[] payload) {
        this.payload = payload;
        messageNumber = numberOfTotalMessage++;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public int size() {
        if (this.payload == null) {
            return 0;
        }
        return this.payload.length;
    }

    // A pedido do tiago araujo
    public short getDestination() {
        return destination;
    }

    public void setDestination(short destination) {
        this.destination = destination;
    }

    public short getOrigin() {
        return origin;
    }

    public void setOrigin(short origin) {
        this.origin = origin;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultMessage m = (DefaultMessage) super.clone();
        byte[] c = null;
        if (payload != null) {
            c = Arrays.copyOf(payload, payload.length);
        }
        m.setPayload(c);
        return m;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isShowColor() {
        return showColor;
    }

    public void setShowColor(boolean showColor) {
        this.showColor = showColor;
    }

    public short getForwardBy() {
        return forwardBy;
    }

    public void setForwardBy(short forwardBy) {
        this.forwardBy = forwardBy;
    }
    
}
