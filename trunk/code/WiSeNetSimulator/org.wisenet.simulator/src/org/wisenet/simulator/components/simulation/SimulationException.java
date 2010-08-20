/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.components.simulation;

/**
 *
 * @author posilva
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
