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

    /**
     *
     */
    public boolean cancel = false;
    /**
     *
     */
    public String reason;

    /**
     *
     * @param source
     */
    public SimulationEvent(Object source) {
        super(source);
    }

    /**
     *
     * @return
     */
    public AbstractSimulation getSimulation() {
        return (AbstractSimulation) source;
    }

    /**
     *
     * @return
     */
    public boolean isCancel() {
        return cancel;
    }

    /**
     *
     * @param cancel
     */
    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     *
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

}
