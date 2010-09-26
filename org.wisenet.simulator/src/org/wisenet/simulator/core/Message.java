package org.wisenet.simulator.core;

import java.awt.Color;
import java.util.Arrays;

/**
 * This class represents a network message send by the nodes
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class Message implements Cloneable {

    static long numberOfTotalMessage = 0; // global message counter
    private long messageNumber = 0;    // global number of the message
    private byte[] payload;               // payload of the message
    protected Color color = Color.green;  // message color for de ui
    protected boolean showColor = true;  // message color flag
    protected long totalHops;             // number of hops (must be updated outside)

    public Message() {
        messageNumber = numberOfTotalMessage++;
    }

    /**
     * Constructor
     * @param payload
     */
    public Message(byte[] payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload cannot be null");
        }
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
     * Clone  this message
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Message m = (Message) super.clone();
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

    public long getTotalHops() {
        return totalHops;
    }

    /**
     * Increment a hop in the total hops counter
     */
    public void hop() {
        totalHops++;
    }
}
