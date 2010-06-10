/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.components.instruments.messages;

import org.mei.securesim.core.engine.DefaultMessage;

/**
 *
 * @author Pedro Marques da Silva
 */
public class CoverageTestMessage extends DefaultMessage implements ICoverageMessage{
    private Object sourceNodeId;
    private Object destinationNodeId;

    public CoverageTestMessage(byte[] payload) {
        super(payload);
    }

    public Object getSourceNodeId() {
        return sourceNodeId;
    }

    public Object getDestinationNodeId() {
        return destinationNodeId;
    }

    public int getId() {
        return (int) getMessageNumber();
    }

    public void setSourceId(Object id) {
        sourceNodeId=id;
    }

    public void setDestinationId(Object id) {
        destinationNodeId=id;
    }

}
