/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.components.instruments;

import org.mei.securesim.components.instruments.energy.EnergyInstrument;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import org.mei.securesim.components.logging.file.EnergyRawFileLogger;
import org.mei.securesim.components.simulation.ISimulationPlatform;
import org.mei.securesim.components.simulation.Simulation;
import org.mei.securesim.core.layers.routing.RoutingLayerController;
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
    protected boolean logEnergyEnable = false;
    protected ISimulationPlatform simulationPlatform;
    protected Simulation simulation;
    private String simulationState;
    private int mode = FAST;
    public final static int FAST = 0;
    public final static int REAL = 1;
    private boolean applyingRadioStrength = false;
    private boolean started;

    public SimulationController() {
        EnergyInstrument.getInstance().setEnergyLogger(new EnergyRawFileLogger());
        EnergyInstrument.getInstance().getEnergyLogger().open();
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
        if (this.simulation != null) {
            this.simulation.reset();
        }
    }

    public List selectRandomNodes(int nroNodes) {
        return selectRandomNodes(nroNodes, new ArrayList());
    }

    public List selectRandomNodes(int nroNodes, NodeSelectionCondition condition) {
        if (nroNodes > simulation.getSimulator().getNodes().size()) {
            throw new IllegalArgumentException("Cannot select more nodes than the number of nodes in the network ");
        }
        List randomNodes = new ArrayList();
        while (randomNodes.size() < nroNodes) {
            Node node = simulation.getSimulator().getNetwork().getNodeDB().randomNode();
            if (condition.select(node)) {
                randomNodes.add(node);
            }
        }
        return randomNodes;

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

    public void applyRadioStrength(long radioStrenght) {
        if (applyingRadioStrength) {
            return;
        }
        try {
            applyingRadioStrength = true;
            if (canApplyRadioStrength()) {
                for (Node node : simulation.getNetwork().getNodeDB().nodes()) {
                    node.getConfig().setMaximumRadioStrength(radioStrenght);
                }
            }
        } catch (Exception e) {
        } finally {
            applyingRadioStrength = false;

        }
    }

    private boolean canApplyRadioStrength() {
        return simulation != null;
    }

    public int getAverageNeighborsPerNode() {
        try {
            if (simulation == null) {
                return -1;
            }
            Collection nodes = simulation.getNetwork().getNodeDB().nodes();
            int total = 0;
            for (Object n : nodes) {
                Node node = (Node) n;
                total += node.getMacLayer().getNeighborhood().neighbors.size();
            }
            return total / nodes.size();

        } catch (Exception e) {
        }
        return 0;
    }

    public void reset() {
        getSimulation().getSimulator().reset();
        getSimulation().reset();
        RoutingLayerController.getInstance().reset();
    }

    public void buildNetwork() {
        if (!SimulationController.getInstance().isNetworkDeployed()) {
            return;
        }
        if (simulation.getSimulator() != null) {
            simulation.getSimulator().init();
            simulationPlatform.setSimulationNrNodes(getSimulation().getSimulator().getNodes().size());
            simulationPlatform.updateSimulationFieldSize();
            simulationPlatform.updateAverageNeighborsPerNode();
            simulationPlatform.update();

            //JOptionPane.showMessageDialog(this, "Average Neighbors Per Node: " + SimulationController.getInstance().updateAverageNeighborsPerNode());
        }

    }

    public void enableRoutingLayerDebug(boolean selected) {
        Collection<Node> nodes;
        if (getSimulation() != null) {
            if (getSimulation().getSimulator() != null) {
                nodes = getSimulation().getSimulator().getNodes();
                for (Node node : nodes) {
                    node.getRoutingLayer().setDebugEnabled(selected);
                }
            }
        }
    }

    public void enableMACLayerDebug(boolean selected) {
        Collection<Node> nodes;
        if (getSimulation() != null) {
            if (getSimulation().getSimulator() != null) {
                nodes = getSimulation().getSimulator().getNodes();
                for (Node node : nodes) {
                    node.getMacLayer().setDebugEnabled(selected);
                }
            }
        }
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
        started = true;
    }

    /**
     *
     */
    public void stop() {
        stopRealTime = System.currentTimeMillis();
        executionRealtime = stopRealTime - startRealTime;
        simulationPlatform.onStopSimulation();
        started = false;
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

        EnergyInstrument.getInstance().stop();
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

    public boolean isStarted() {
        return started;
    }

    public Dimension fieldSize() {
        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int h, w;
        if (simulation != null) {
            if (simulation.getSimulator() != null) {
                if (simulation.getSimulator().getNodes().size() > 0) {
                    for (Node node : simulation.getSimulator().getNodes()) {
                        maxX = Math.max(maxX, node.getGraphicNode().getX());
                        minX = Math.min(minX, node.getGraphicNode().getX());
                        maxY = Math.max(maxY, node.getGraphicNode().getY());
                        minY = Math.min(minY, node.getGraphicNode().getY());
                    }
                    h = maxY - minY;
                    w = maxX - minX;
                    return new Dimension(w, h);

                }
            }
        }
        return new Dimension(0, 0);
    }

    public RoutingLayerController getRoutingLayerController() {
        return RoutingLayerController.getInstance();
    }

    public boolean isNetworkDeployed() {
        if (getSimulation() != null) {
            if (getSimulation().getSimulator() != null) {
                return (getSimulation().getSimulator().getNodes().size() > 0);
            }
        }
        return false;
    }
}
