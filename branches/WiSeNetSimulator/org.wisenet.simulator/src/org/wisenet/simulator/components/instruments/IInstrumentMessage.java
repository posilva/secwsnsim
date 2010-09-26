/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments;

/**
 *
 * @author Pedro Marques da Silva
 */
public interface IInstrumentMessage {

    public Object getSourceId();

    public Object getDestinationId();

    public long getUniqueId();

    public void setSourceId(Object id);

    public void setDestinationId(Object id);

    public void setUniqueId(long id);
}
