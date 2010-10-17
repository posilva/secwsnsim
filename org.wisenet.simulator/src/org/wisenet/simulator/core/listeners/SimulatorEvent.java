/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.listeners;

import java.util.EventObject;
import org.wisenet.simulator.core.Simulator;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulatorEvent extends EventObject {

    /**
     *
     * @param source
     */
    public SimulatorEvent(Object source) {
        super(source);
    }
    /**
     * Gets the simulator (source) instance
     * @return
     */
    public Simulator getSimulator() {
        return (Simulator) source;
    }
}
