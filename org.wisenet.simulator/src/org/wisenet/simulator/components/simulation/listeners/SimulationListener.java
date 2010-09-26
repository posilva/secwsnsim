/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.simulation.listeners;

import java.util.EventListener;

/**
 * Simulation listener  to handle simulation events
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface SimulationListener extends EventListener {

    public void onStartFailure(SimulationEvent event);

    public void beforeStart(SimulationEvent event);

    public void afterStart(SimulationEvent event);

    public void beforeStop(SimulationEvent event);

    public void onStopFailure(SimulationEvent event);

    public void afterStop(SimulationEvent event);

    public void beforeBuildNetwork(SimulationEvent event);

    public void afterBuildNetwork(SimulationEvent event);

    public void onBuildNetworkFailure(SimulationEvent event);

    public void onEmptyQueue(SimulationEvent event);

    public void onNewSimulatorRound(SimulationEvent event);
}
