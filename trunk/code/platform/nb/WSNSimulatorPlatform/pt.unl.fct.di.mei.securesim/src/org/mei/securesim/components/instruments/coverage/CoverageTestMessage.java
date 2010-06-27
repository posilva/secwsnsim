/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.components.instruments.coverage;

import org.mei.securesim.core.engine.BaseMessage;

/**
 *
 * @author Pedro Marques da Silva
 */
public class CoverageTestMessage extends BaseMessage implements ICoverageMessage{
    private Object sourceNodeId;
    private Object destinationNodeId;

    public CoverageTestMessage(byte[] payload) {
        super(payload);
    }

    public Object getSourceId() {
        return sourceNodeId;
    }

    public Object getDestinationId() {
        return destinationNodeId;
    }

    public Object getUniqueId() {
        return getMessageNumber();
    }

    public void setSourceId(Object id) {
        sourceNodeId=id;
    }

    public void setDestinationId(Object id) {
        destinationNodeId=id;
    }

}
