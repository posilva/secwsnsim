/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.mei.securesim.components.logging.file.EnergyRawFileLogger;
import org.mei.securesim.components.simulation.ISimulationPlatform;
import org.mei.securesim.components.simulation.Simulation;
import org.mei.securesim.core.nodes.Node;

/**
 *
 * @author CIAdmin
 */
public class SimulationController {

    protected static SimulationController instance;
    private long startRealTime = 0;
    private long stopRealTime = 0;
    private long executionRealtime;
    private Stack timepoints = new Stack();
    protected boolean logEnergyEnable = true;
    protected ISimulationPlatform simulationPlatform;
    protected Simulation simulation;
    private String simulationState;
    private int mode = FAST;
    public final static int FAST = 0;
    public final static int REAL = 1;

    public SimulationController() {
        EnergyController.getInstance().setEnergyLogger(new EnergyRawFileLogger());
        EnergyController.getInstance().getEnergyLogger().open();
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void registerPlatform(ISimulationPlatform platform) {
        this.simulationPlatform = platform;
        platform.onStartPlatform();
    }

    public void registerSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void resetSimulation() {
    }

    public List selectRandomNodes(int nroNodes) {
        return selectRandomNodes(nroNodes, new ArrayList());
    }

    public List selectRandomNodes(int nroNodes, List excludeNodes) {

        if (nroNodes > simulation.getSimulator().getNodes().size()) {
            throw new IllegalArgumentException("Cannot select more nodes than the number of nodes in the network ");
        }


        List randomNodes = new ArrayList();
        while (randomNodes.size() < nroNodes) {
            Node node = simulation.getSimulator().getNetwork().getNodeDB().randomNode();
            if (!(randomNodes.contains(node) || excludeNodes.contains(node))) {
                randomNodes.add(node);
            }
        }
        return randomNodes;
    }

    /**
     *
     */
    class Timepoint {

        String name;
        long timemillisecs;

        public Timepoint(String name, long timemillisecs) {
            this.name = name;
            this.timemillisecs = timemillisecs;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getTimemillisecs() {
            return timemillisecs;
        }

        public void setTimemillisecs(long timemillisecs) {
            this.timemillisecs = timemillisecs;
        }
    }

    /**
     * 
     * @return
     */
    public static SimulationController getInstance() {
        if (instance == null) {
            instance = new SimulationController();
        }
        return instance;
    }

    /**
     *
     */
    public void begin() {
        startRealTime = System.currentTimeMillis();
        simulationPlatform.onStartSimulation();
    }

    /**
     *
     */
    public void stop() {
        stopRealTime = System.currentTimeMillis();
        executionRealtime = stopRealTime - startRealTime;
        simulationPlatform.onStopSimulation();
    }

    /**
     *
     * @return
     */
    public long getExecutionRealtime() {
        return executionRealtime;
    }

    /**
     *
     * @return
     */
    public long getCurrentSimulationTime() {
        return System.currentTimeMillis() - startRealTime;
    }

    /**
     *
     * @return
     */
    public Stack getTimepoints() {
        return timepoints;
    }

    public void addTimePoint(String name) {
        timepoints.push(new Timepoint(name, getCurrentSimulationTime()));
    }

    public boolean isLogEnergyEnable() {
        return logEnergyEnable;
    }

    public void setLogEnergyEnable(boolean logEnergyEnable) {
        this.logEnergyEnable = logEnergyEnable;
    }

    /**
     * 
     * @param simulationPlatform
     */
    public void registerSimulationPlatform(ISimulationPlatform simulationPlatform) {
        this.simulationPlatform = simulationPlatform;
    }

    public ISimulationPlatform getSimulationPlatform() {
        return simulationPlatform;
    }

    public void enterPlatform() {
        System.out.println("PLATFORM OPENED");
    }

    public void exitPlatform() {

        EnergyController.getInstance().stop();
    }

    public void setSimulationState(String state) {
        simulationState = state;
    }

    public String getSimulationState() {
        return simulationState;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
