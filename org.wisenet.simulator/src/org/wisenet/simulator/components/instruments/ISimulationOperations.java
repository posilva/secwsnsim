/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.instruments;

import java.awt.Dimension;
import java.util.List;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument;
import org.wisenet.simulator.components.instruments.energy.EnergyController;
import org.wisenet.simulator.components.instruments.latency.LatencyInstrument;
import org.wisenet.simulator.components.instruments.reliability.ReliabilityInstrument;
import org.wisenet.simulator.components.simulation.ISimulationGUI;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayerController;

/**
 *
 * @author posilva
 */
public interface ISimulationOperations {

    void applyRadioStrength(long radioStrenght);

    void buildNetwork();

    void enableMACLayerDebug(boolean selected);

    void enableRoutingLayerDebug(boolean selected);

    void enterPlatform();

    void exitPlatform();

    Dimension fieldSize();

    int getAverageNeighborsPerNode();

    long getCurrentSimulationTime();

    long getExecutionRealtime();

    CoverageInstrument getCoverageInstrument();

    EnergyController getEnergyController();

    LatencyInstrument getLatencyInstrument();

    ReliabilityInstrument getReliabilityInstrument();

    RoutingLayerController getRoutingLayerController();

    ISimulationGUI getSimulationGUI();

    String getSimulationState();

    boolean isLogEnergyEnable();

    boolean isNetworkDeployed();

    boolean isStarted();

    void registerGUI(ISimulationGUI platform);

    void reset();

    List selectRandomNodes(int nroNodes);

    List selectRandomNodes(int nroNodes, NodeSelectionCondition condition);

    List selectRandomNodes(int nroNodes, List excludeNodes);

    void setLogEnergyEnable(boolean logEnergyEnable);

    void setSimulationState(String state);

    /**
     *
     */
    void stop();
}
