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

    public SimulationException(Throwable arg0) {
        super(arg0);
    }

    public SimulationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SimulationException(String arg0) {
        super(arg0);
    }

    public SimulationException() {
        super();
    }
}
