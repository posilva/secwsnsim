/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.simulation;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationException extends Exception {

    /**
     *
     * @param arg0
     */
    public SimulationException(Throwable arg0) {
        super(arg0);
    }

    /**
     *
     * @param arg0
     * @param arg1
     */
    public SimulationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     *
     * @param arg0
     */
    public SimulationException(String arg0) {
        super(arg0);
    }

    /**
     *
     */
    public SimulationException() {
        super();
    }
}
