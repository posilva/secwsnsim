/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments.logging;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class EnergyLogger {

    /**
     *
     */
    public abstract void init();

    /**
     *
     * @param id 
     * @param event
     * @param simTime
     * @param realTime
     * @param state
     * @param value
     */
    public abstract void update(short id, String event, long realTime, long simTime, double value, String state);

    /**
     *
     */
    public abstract void open();

    /**
     *
     */
    public abstract void close();

    /**
     *
     */
    public abstract void reset();
}
