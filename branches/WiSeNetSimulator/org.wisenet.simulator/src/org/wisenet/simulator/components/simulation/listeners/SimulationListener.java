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

    /**
     *
     * @param event
     */
    public void onStartFailure(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void beforeStart(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void afterStart(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void beforeStop(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void onStopFailure(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void afterStop(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void beforeBuildNetwork(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void afterBuildNetwork(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void onBuildNetworkFailure(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void onEmptyQueue(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void onNewSimulatorRound(SimulationEvent event);

    /**
     *
     * @param event
     */
    public void afterTestExecution(SimulationTestEvent event);

    /**
     *
     * @param event
     */
    public void startTestExecution(SimulationTestEvent event);
}
