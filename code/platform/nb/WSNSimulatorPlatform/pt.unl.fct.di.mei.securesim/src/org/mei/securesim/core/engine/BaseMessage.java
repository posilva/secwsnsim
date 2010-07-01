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
public class BaseMessage implements Cloneable {

    static long numberOfTotalMessage = 0; // global message counter
    private long messageNumber=0;    // global number of the message
    private byte[] payload;               // payload of the message
    private short sourceNodeId;           // node that create the message
    private short destinationNodeId;      // message was sent to
    private short forwardBy;              // last node that forward message
    protected Color color = Color.green;  // message color for de ui
    protected boolean showColor = false;  // message color flag

    public BaseMessage() {
        messageNumber = numberOfTotalMessage++;
    }


    /**
     * Contructor
     * @param payload
     */
    public BaseMessage(byte[] payload) {
        this.payload = payload;
        messageNumber = numberOfTotalMessage++;
    }

    /**
     * Get de total number of messages in Simulation
     * @return
     */
    public static long getNumberOfTotalMessage() {
        return numberOfTotalMessage;
    }

    /**
     * Returns the number of the message
     * @return
     */
    public long getMessageNumber() {
        return messageNumber;
    }

    /**
     * Get Payload of the message
     * @return
     */
    public byte[] getPayload() {
        return payload;
    }

    /**
     * Set payload de message
     * @param payload
     */
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    /**
     * Gets the size of payload
     * @return
     */
    public int size() {
        if (this.payload == null) {
            return 0;
        }
        return this.payload.length;
    }

    /**
     * Gets ID of destination Node
     * @return
     */
    public short getDestinationNodeId() {
        return destinationNodeId;
    }

    /**
     * Sets ID of destination node
     * @param destination
     */
    public void setDestinationNodeId(short destination) {
        this.destinationNodeId = destination;
    }

    /**
     * Get ID for sourcenode
     * @return
     */
    public short getSourceNodeId() {
        return sourceNodeId;
    }

    /**
     * Set Id of source node
     * @param origin
     */
    public void setSourceNodeId(short origin) {
        this.sourceNodeId = origin;
    }
    
    /**
     * Clone  this message
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BaseMessage m = (BaseMessage) super.clone();
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
