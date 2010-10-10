/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.evaluation.tests;

import org.wisenet.simulator.core.Message;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class TestMessage extends Message implements org.wisenet.simulator.components.instruments.IInstrumentMessage {

    public abstract Object getSourceId();

    public abstract Object getDestinationId();

    public abstract Object getUniqueId();

    public abstract void setSourceId(Object id);

    public abstract void setDestinationId(Object id);

    public abstract void setUniqueId(Object id);
}
