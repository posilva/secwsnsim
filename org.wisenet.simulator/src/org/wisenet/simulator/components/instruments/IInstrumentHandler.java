/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface IInstrumentHandler {

    /**
     * Return a representation of a unique id for Node
     * (may result from a routing organization, base for example in a
     * topological based address)
     * @return
     */
    public Object getUniqueId();

    /**
     * Execute a probing action (Normally is a message sent action)
     * @param message
     */
    public void probing(IInstrumentMessage message);
}
