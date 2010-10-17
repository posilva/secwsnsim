/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.simulation;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface ISimulationGUI {

    /**
     *
     * @param message
     */
    void showLogMessage(String message);

    /**
     *
     */
    void onStartPlatform();

    /**
     *
     */
    void onExitPlatform();
}
