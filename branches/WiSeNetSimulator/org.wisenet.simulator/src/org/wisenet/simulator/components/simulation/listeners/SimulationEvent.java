/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.simulation.listeners;

import java.util.EventObject;
import org.wisenet.simulator.components.simulation.AbstractSimulation;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationEvent extends EventObject {

    public boolean cancel = false;
    public String reason;

    public SimulationEvent(Object source) {
        super(source);
    }

    public AbstractSimulation getSimulation() {
        return (AbstractSimulation) source;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
