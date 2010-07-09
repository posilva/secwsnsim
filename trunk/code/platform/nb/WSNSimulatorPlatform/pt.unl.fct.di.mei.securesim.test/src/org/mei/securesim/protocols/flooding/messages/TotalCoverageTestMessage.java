/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.flooding.messages;

import org.mei.securesim.components.instruments.coverage.ITotalCoverageMessage;

/**
 *
 * @author CIAdmin
 */
public class TotalCoverageTestMessage extends FloodingMessage implements ITotalCoverageMessage {

    public TotalCoverageTestMessage() {
    }

    public TotalCoverageTestMessage(byte[] payload) {
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
        setSource((Short) id);
    }

    public void setDestinationId(Object id) {
        setDestin((Short) id);
    }

    public void setUniqueId(Object id) {
        setMessageData(id.toString());
    }

    public void setData(byte[] data) {
        setPayload(data);
    }

    public byte[] getData() {
        return getPayload();
    }
}
