/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.simulation;

/**
 *
 * @author CIAdmin
 */
public interface ISimulationPlatform {

    void showLogMessage(String message);

    void onStartPlatform();

    void onExitPlatform();

    void onStartSimulation();

    void onPauseSimulation();

    void onStopSimulation();

    void updateSimulationState(String state);

    public void setSimulationNrNodes(int size);

    public void updateSimulationFieldSize();

    public void updateAverageNeighborsPerNode();

    public void update();
}
