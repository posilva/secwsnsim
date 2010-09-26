package org.wisenet.simulator.components.simulation;

import java.util.logging.Level;
import org.apache.commons.configuration.ConfigurationException;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument;
import org.wisenet.simulator.components.instruments.latency.LatencyInstrument;
import org.wisenet.simulator.components.instruments.reliability.ReliabilityInstrument;
import org.wisenet.simulator.components.simulation.listeners.SimulationListener;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.components.output.IOutputDisplay;
import org.wisenet.simulator.components.simulation.listeners.SimulationEvent;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.listeners.SimulatorEvent;
import org.wisenet.simulator.core.listeners.SimulatorListener;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayerController;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.utilities.RandomGenerator;
import org.wisenet.simulator.utilities.Utilities;
import org.wisenet.simulator.utilities.console.SimulationSettings;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class Simulation extends AbstractSimulation implements SimulatorListener {

    /**
     * Logger object
     */
    private static final Logger LOGGER = Logger.getLogger(Simulation.class.getName());
    /**
     * The simulations registered listeners
     */
    EventListenerList simulationListeners = new EventListenerList();
    /**
     * Control flag for radio strength apply moment
     */
    protected boolean applyingRadioStrength;
    /**
     * Control flag for start state
     */
    protected boolean started;
    /**
     * Start timestamp for real time
     */
    protected long startRealTime;
    /**
     * 
     */
    protected long stopRealTime;
    /**
     * 
     */
    protected long executionRealtime;
    /**
     * If has a SimulationGUI registered then keep a reference
     * for notification
     */
    protected ISimulationGUI simulationGUI;
    /**
     *
     */
    protected CoverageInstrument coverageInstrument = new CoverageInstrument(this);
    /**
     *
     */
    protected ReliabilityInstrument reliabilityInstrument = new ReliabilityInstrument(this);
    /**
     *
     */
    protected LatencyInstrument latencyInstrument = new LatencyInstrument(this);
    /**
     *
     */
    protected boolean logEnergyEnable;
    /**
     *
     */
    protected String simulationState;
    /**
     *
     */
    protected RoutingLayerController routingLayerController;
    /**
     * 
     */
    protected IOutputDisplay macOutputDisplay;
    /**
     *
     */
    protected IOutputDisplay routingOutputDisplay;
    /**
     * 
     */
    protected IOutputDisplay applicationOutputDisplay;
    /**
     * 
     */
    private boolean networkBuilded = false;
    private SimulationSettings settings;

    /**
     *
     */
    public Simulation() {
        setupInstruments();

    }

    /**
     * Initialize instruments
     */
    private void setupInstruments() {
        this.coverageInstrument = new CoverageInstrument(this);
        this.coverageInstrument.setRadioModelThreshold(1);
        this.reliabilityInstrument = new ReliabilityInstrument(this);
        this.latencyInstrument = new LatencyInstrument(this);
    }

    @Override
    public void stop() {
        SimulationEvent event = new SimulationEvent(this);
        fireBeforeStop(event);
        if (event.isCancel()) {
            event.setReason("Cancelled by user");
            fireOnStopFailure(event);
            return;
        }
        doStop();
        event = new SimulationEvent(this);
        fireAfterStop(event);
    }

    @Override
    public void start() {
        if (!networkBuilded) {
            buildNetwork();
        }
        SimulationEvent event = new SimulationEvent(this);
        fireBeforeStart(event);
        if (event.isCancel()) {
            event.setReason("Cancelled by user");
            fireOnStartFailure(event);
            return;
        }
        doStart();
        event = new SimulationEvent(this);
        fireAfterStart(event);
    }

    @Override
    public void pause() {
        if (!isValid()) {
            return;
        }
        getSimulator().pause();
    }

    /**
     * Apply radio strength for each node in the platform
     * @param radioStrenght
     */
    public void applyRadioStrength(long radioStrenght) {
        if (!isValid()) {
            return;
        }
        if (applyingRadioStrength) {
            return;
        }
        try {
            applyingRadioStrength = true;
            for (Node node : getSimulator().getNodes()) {
                node.getConfig().setMaximumRadioStrength(radioStrenght);
            }
        } catch (Exception e) {
        } finally {
            applyingRadioStrength = false;

        }

    }

    public void buildNetwork() {
        if (!isValid()) {
            return;
        }
        SimulationEvent event = new SimulationEvent(this);
        fireBeforeBuildNetwork(event);
        if (!event.isCancel()) {
            if (isNetworkDeployed()) {
                getSimulator().init();
                fireAfterBuildNetwork(event);
                networkBuilded = true;
            }

        } else {
            networkBuilded = false;
        }
    }

    public void enableMACLayerDebug(boolean selected) {
        if (!isValid()) {
            return;
        }
        Collection<Node> nodes;
        nodes = getSimulator().getNodes();
        for (Node node : nodes) {
            node.getMacLayer().setDebugEnabled(selected);
        }
    }

    public void enableRoutingLayerDebug(boolean selected) {
        if (!isValid()) {
            return;
        }
        Collection<Node> nodes;
        nodes = getSimulator().getNodes();
        for (Node node : nodes) {
            node.getRoutingLayer().setDebugEnabled(selected);
        }
    }

    public void enterPlatform() {
    }

    public void exitPlatform() {
    }

    public int getAverageNeighborsPerNode() {
        try {
            if (!isValid()) {
                return -1;
            }
            Collection nodes = getSimulator().getNodes();
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

    public Dimension fieldSize() {

        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int h, w;
        if (isValid()) {

            if (getSimulator().getNodes().size() > 0) {
                for (Node node : getSimulator().getNodes()) {
                    maxX = Math.max(maxX, node.getGraphicNode().getX());
                    minX = Math.min(minX, node.getGraphicNode().getX());
                    maxY = Math.max(maxY, node.getGraphicNode().getY());
                    minY = Math.min(minY, node.getGraphicNode().getY());
                }
                h = maxY - minY;
                w = maxX - minX;
                return new Dimension(w, h);
            }
            return new Dimension(0, 0);
        } else {
            return new Dimension(0, 0);
        }
    }

    public ISimulationGUI getSimulationPlatform() {
        throw new UnsupportedOperationException("Not supported yet.");




    }

    /**
     * Verify if the field has some sensor deployed
     * @return
     */
    public boolean isNetworkDeployed() {
        return (isValid() && (getSimulator().getNodes().size() > 0));
    }

    /**
     * Returns if the simulation is started
     * @return
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Register AbstractSimulation GUI control
     * @param gui
     */
    public void registerGUI(ISimulationGUI gui) {
        simulationGUI = gui;
    }

    /**
     * Select a number of random nodes
     * @param nroNodes
     * @return a list with nodes
     */
    public List selectRandomNodes(int nroNodes) {
        return selectRandomNodes(nroNodes, new ArrayList());
    }

    /**
     * Select a number of random nodes based on a condition
     * @param nroNodes
     * @param condition
     * @return a list with nodes
     */
    public List selectRandomNodes(int nroNodes, NodeSelectionCondition condition) {
        if (nroNodes > getSimulator().getNodes().size()) {
            throw new IllegalArgumentException("Cannot select more nodes than the number of nodes in the network ");
        }
        List randomNodes = new ArrayList();
        while (randomNodes.size() < nroNodes) {
            Node node = getSimulator().getRandomNode();
            if (condition.select(node)) {
                randomNodes.add(node);
            }
        }
        return randomNodes;
    }

    /**
     * The some result can be returned using {@link NodeSelectionCondition}
     *
     * @param nroNodes
     * @param excludeNodes
     * @return
     * @deprecated
     */
    public List selectRandomNodes(int nroNodes, List excludeNodes) {
        if (nroNodes > getSimulator().getNodes().size()) {
            throw new IllegalArgumentException("Cannot select more nodes than the number of nodes in the network ");
        }
        List randomNodes = new ArrayList();
        while (randomNodes.size() < nroNodes) {
            Node node = getSimulator().getRandomNode();
            if (!(randomNodes.contains(node) || excludeNodes.contains(node))) {
                randomNodes.add(node);
            }
        }
        return randomNodes;
    }

    public void setSimulationState(String state) {
        simulationState = state;
    }

    public String getSimulationState() {
        return simulationState;
    }

    private void fireAfterStart(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).afterStart(event);
            }
        }
    }

    public void addSimulationListener(SimulationListener listener) {
        simulationListeners.add(SimulationListener.class, listener);
    }

    public void removeSimulationListener(SimulationListener listener) {
        simulationListeners.remove(SimulationListener.class, listener);
    }

    private void fireBeforeStop(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).beforeStop(event);
            }
        }
    }

    private void fireAfterStop(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).afterStop(event);
            }
        }
    }

    private void fireBeforeStart(SimulationEvent event) {
        LOGGER.entering(this.getClass().getName(), "fireBeforeStart");
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).beforeStart(event);
            }
        }
        LOGGER.exiting(this.getClass().getName(), "fireBeforeStart");
    }

    private void doStart() {
        startRealTime = System.currentTimeMillis();
        simulator.start();
        started = true;
    }

    private void doStop() {
        stopRealTime = System.currentTimeMillis();
        executionRealtime = stopRealTime - startRealTime;
        started = false;
    }

    protected boolean isValid() {
        return getSimulator() != null;
    }

    protected boolean isSimulationPlatformRegistered() {
        return simulationGUI != null;
    }

    public ISimulationGUI getSimulationGUI() {
        return simulationGUI;
    }

    public CoverageInstrument getCoverageInstrument() {
        return coverageInstrument;
    }

    public LatencyInstrument getLatencyInstrument() {
        return latencyInstrument;
    }

    public ReliabilityInstrument getReliabilityInstrument() {
        return reliabilityInstrument;
    }

    public long getExecutionRealtime() {
        return executionRealtime;
    }

    public long getCurrentSimulationTime() {
        return System.currentTimeMillis() - startRealTime;
    }

    public boolean isLogEnergyEnable() {
        return logEnergyEnable;
    }

    public void setLogEnergyEnable(boolean logEnergyEnable) {
        this.logEnergyEnable = logEnergyEnable;
    }

    private void fireAfterBuildNetwork(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).afterBuildNetwork(event);
            }
        }
    }

    private void fireBeforeBuildNetwork(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).beforeBuildNetwork(event);
            }
        }
    }

    private void fireOnStartFailure(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).onStartFailure(event);
            }
        }
    }

    private void fireOnStopFailure(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).onStopFailure(event);
            }
        }
    }

    private void fireOnEmptyQueue(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).onEmptyQueue(event);
            }
        }
    }

    public IOutputDisplay getApplicationOutputDisplay() {
        return applicationOutputDisplay;
    }

    public void setApplicationOutputDisplay(IOutputDisplay applicationOutputDisplay) {
        this.applicationOutputDisplay = applicationOutputDisplay;
    }

    public IOutputDisplay getMacOutputDisplay() {
        return macOutputDisplay;
    }

    public void setMacOutputDisplay(IOutputDisplay macOutputDisplay) {
        this.macOutputDisplay = macOutputDisplay;
    }

    public IOutputDisplay getRoutingOutputDisplay() {
        return routingOutputDisplay;
    }

    public void setRoutingOutputDisplay(IOutputDisplay routingOutputDisplay) {
        this.routingOutputDisplay = routingOutputDisplay;
    }

    public void onEmptyQueue(SimulatorEvent event) {
        fireOnEmptyQueue(new SimulationEvent(this));
    }

    public void onNewStepRound(SimulatorEvent event) {
        fireOnNewSimulatorRound(new SimulationEvent(this));
    }

    private void fireOnNewSimulatorRound(SimulationEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).onNewSimulatorRound(event);
            }
        }
    }

    /**
     * Set selection state based on a selection condition
     * @param status
     * @param condition
     */
    public void selectNodes(boolean status, NodeSelectionCondition condition) {
        if (getSimulator().getNodes().size() > 0) {
            for (Node node : getSimulator().getNodes()) {
                if (condition.select(node)) {
                    node.getGraphicNode().select(status);
                }
            }
        }
    }

    /**
     * Set the nodes marked based on a condition
     * @param status
     * @param condition
     */
    public void markNodes(boolean status, NodeSelectionCondition condition) {
        if (getSimulator().getNodes().size() > 0) {
            for (Node node : getSimulator().getNodes()) {
                if (condition.select(node)) {
                    if (status) {
                        node.getGraphicNode().mark();
                    } else {
                        node.getGraphicNode().unmark();
                    }
                }

            }
        }
    }

    @Override
    public void initialSetup() {
        super.initialSetup();
        getSimulator().addListener(this);
    }

    @Override
    public int getNumberOfStableNodes() {
        int stableCount = 0;
        Collection<Node> nodes = getSimulator().getNodes();
        for (Node node : nodes) {
            if (node.getRoutingLayer().isStable()) {
                stableCount++;
            }
        }
        return stableCount;
    }

    public void saveNetworkTopology(String filename) throws Exception {
        if (this.isValid()) {
            saveNT(filename);
        } else {
            throw new Exception("Simulation isn't valid");
        }
    }

    public void loadNetworkTopology(String filename) throws Exception {
        XMLConfiguration file = new XMLConfiguration();
        file.load(filename);

    }

    private void saveNT(String filename) throws ConfigurationException {

        Collection<Node> nodes = getSimulator().getNodes();
        XMLConfiguration file = new XMLConfiguration();
        file.addProperty("simulation.topology.nodes.size", nodes.size());
        for (Node node : nodes) {
            file.addProperty("simulation.topology.nodes.node(-1).id", node.getId());
            file.addProperty("simulation.topology.nodes.node.x", node.getX());
            file.addProperty("simulation.topology.nodes.node.y", node.getY());
            file.addProperty("simulation.topology.nodes.node.z", node.getZ());
        }
        file.save(filename);
    }

    public RoutingLayerController getRoutingLayerController() {
        return RoutingLayer.getController();
    }

    public void setSettings(SimulationSettings settings) {
        this.settings = settings;
    }

    @Override
    public void create(SimulationSettings settings) {
        try {
            this.settings = settings; // save settings
            setSimulator((Simulator) Utilities.loadClassInstance(settings.getSimulatorClassName()));
            setNodeFactory((AbstractNodeFactory) Utilities.loadClassInstance(settings.getNodeFactoryClassName()));
            getNodeFactory().setNodeMaxRadioStregth(settings.getMaxNodeRadioStrength());
            setRadioModel((RadioModel) Utilities.loadClassInstance(settings.getRadioModelClassName()));
            setEnergyModel((EnergyModel) Utilities.loadClassInstance(settings.getEnergyModelClassName()));
            setMode(settings.isFastMode() ? Simulator.FAST : Simulator.REAL);
            Simulator.randomGenerator = new RandomGenerator(settings.getSeed());
            initialSetup();
            
        } catch (Exception ex) {
            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
