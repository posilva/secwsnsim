/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.temp.insens.messages.evaluation;

import org.wisenet.simulator.components.instruments.IInstrumentMessage;
import org.wisenet.simulator.temp.insens.messages.INSENSDATAMessage;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class EvaluationINSENSDATAMessage extends INSENSDATAMessage implements IInstrumentMessage {

    public EvaluationINSENSDATAMessage() {
        super("".getBytes());
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
