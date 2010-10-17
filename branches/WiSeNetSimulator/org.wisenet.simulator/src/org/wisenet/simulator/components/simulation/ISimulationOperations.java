/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.components.simulation;

import java.awt.Dimension;
import java.util.List;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument;
import org.wisenet.simulator.core.energy.EnergyController;
import org.wisenet.simulator.components.instruments.latency.LatencyInstrument;
import org.wisenet.simulator.components.instruments.reliability.ReliabilityInstrument;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayerController;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public interface ISimulationOperations {

    /**
     *
     * @param radioStrenght
     */
    void applyRadioStrength(long radioStrenght);

    /**
     *
     */
    void buildNetwork();

    /**
     *
     * @param selected
     */
    void enableMACLayerDebug(boolean selected);

    /**
     *
     * @param selected
     */
    void enableRoutingLayerDebug(boolean selected);

    /**
     *
     */
    void enterPlatform();

    /**
     *
     */
    void exitPlatform();

    /**
     *
     * @return
     */
    Dimension fieldSize();

    /**
     *
     * @return
     */
    int getAverageNeighborsPerNode();

    /**
     *
     * @return
     */
    long getCurrentSimulationTime();

    /**
     *
     * @return
     */
    long getExecutionRealtime();

    /**
     *
     * @return
     */
    CoverageInstrument getCoverageInstrument();

    /**
     *
     * @return
     */
    EnergyController getEnergyController();

    /**
     *
     * @return
     */
    LatencyInstrument getLatencyInstrument();

    /**
     *
     * @return
     */
    ReliabilityInstrument getReliabilityInstrument();

    /**
     *
     * @return
     */
    RoutingLayerController getRoutingLayerController();

    /**
     *
     * @return
     */
    ISimulationGUI getSimulationGUI();

    /**
     *
     * @return
     */
    String getSimulationState();

    /**
     *
     * @return
     */
    boolean isLogEnergyEnable();

    /**
     *
     * @return
     */
    boolean isNetworkDeployed();

    /**
     *
     * @return
     */
    boolean isStarted();

    /**
     *
     * @param platform
     */
    void registerGUI(ISimulationGUI platform);

    /**
     *
     */
    void reset();

    /**
     *
     * @param nroNodes
     * @return
     */
    List selectRandomNodes(int nroNodes);

    /**
     *
     * @param nroNodes
     * @param condition
     * @return
     */
    List selectRandomNodes(int nroNodes, NodeSelectionCondition condition);

    /**
     *
     * @param status
     * @param condition
     */
    void selectNodes(boolean status, NodeSelectionCondition condition);

    /**
     *
     * @param status
     * @param condition
     */
    void markStableNodes(boolean status, NodeSelectionCondition condition);

    /**
     *
     * @param nroNodes
     * @param excludeNodes
     * @return
     */
    List selectRandomNodes(int nroNodes, List excludeNodes);

    /**
     *
     * @param logEnergyEnable
     */
    void setLogEnergyEnable(boolean logEnergyEnable);

    /**
     *
     * @param state
     */
    void setSimulationState(String state);

    /**
     *
     * @return
     */
    public int getNumberOfStableNodes();

    /**
     *
     * @param filename
     * @throws Exception
     */
    public void saveNetworkTopology(String filename) throws Exception;

    /**
     *
     * @param filename
     * @throws Exception
     */
    public void loadNetworkTopology(String filename) throws Exception;

    /**
     *
     */
    public void stop();

    /**
     *
     * @return
     */
    public int getNumberOfSinkNodes();
}
