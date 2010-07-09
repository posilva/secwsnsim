/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.protocols.insens.messages.evaluation;

import org.mei.securesim.protocols.insens.messages.INSENSDATAMessage;

/**
 *
 * @author CIAdmin
 */
public class EvaluationINSENSDATAMessage extends INSENSDATAMessage {

    public EvaluationINSENSDATAMessage() {
        super();
    }

    public EvaluationINSENSDATAMessage(byte[] payload) {
        super(payload);
    }

    public Object getSourceId() {
        return getSource();
    }

    public Object getDestinationId() {
        return getDestination();
    }

    public void setSourceId(Object id) {
        setSource((Short) id);
    }

    public void setDestinationId(Object id) {
        setDestination((Short) id);
    }

    public void setUniqueId(Object id) {
        setID((Long) id);
    }

    public Object getUniqueId() {
        return getID();
    }
}
