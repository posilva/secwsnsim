/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.listeners;

import java.util.EventListener;

/**
 * Listener for Simulator 
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface SimulatorListener extends EventListener {
    /**
     *
     * @param event
     */
    public void onEmptyQueue(SimulatorEvent event);
    /**
     *
     * @param event
     */
    public void onNewStepRound(SimulatorEvent event);
}
