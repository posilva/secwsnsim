/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.protocols.insens.messages.evaluation;

import org.wisenet.protocols.insens.messages.INSENSDATAMessage;
import org.wisenet.simulator.components.instruments.IInstrumentMessage;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class EvaluationINSENSDATAMessage extends INSENSDATAMessage implements IInstrumentMessage {

    public EvaluationINSENSDATAMessage() {
        super("A".getBytes());
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

//    public Object getUniqueId() {
//        return getID();
//    }
    public long getUniqueId() {
        return getID();
    }

    public void setUniqueId(long id) {
        setID(id);
    }
}
