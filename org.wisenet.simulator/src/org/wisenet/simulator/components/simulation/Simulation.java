package org.wisenet.simulator.components.simulation;

import org.wisenet.simulator.components.simulation.listeners.SimulationTestEvent;
import java.util.logging.Level;
import org.apache.commons.configuration.ConfigurationException;
import org.wisenet.simulator.common.PersistantException;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.simulation.listeners.SimulationListener;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.io.File;
import javax.swing.event.EventListenerList;
import org.apache.commons.configuration.XMLConfiguration;
import org.wisenet.simulator.components.evaluation.tests.TestTopology;
import org.wisenet.simulator.utilities.NodeSelectionCondition;
import org.wisenet.simulator.components.output.IOutputDisplay;
import org.wisenet.simulator.components.simulation.listeners.SimulationEvent;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.listeners.SimulatorEvent;
import org.wisenet.simulator.core.listeners.SimulatorListener;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.layers.mac.MACLayer;
import org.wisenet.simulator.core.node.layers.routing.RoutingLayer;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.utilities.RandomGenerator;
import org.wisenet.simulator.utilities.RandomList;
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
//    protected boolean logEnergyEnable;
    /**
     *
     */
    protected String simulationState;
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

    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
    public void buildNetwork() {
        if (!isValid()) {
            return;
        }
        long start = System.currentTimeMillis();
        SimulationEvent event = new SimulationEvent(this);
        fireBeforeBuildNetwork(event);
        if (!event.isCancel()) {
            if (isNetworkDeployed()) {
                getSimulator().init();
                fireAfterBuildNetwork(event);
                System.out.println("Build in " + ((System.currentTimeMillis() - start) / 1000) + " Seconds");
                networkBuilded = true;
            }

        } else {
            networkBuilded = false;
        }
    }

    /**
     *
     * @param selected
     */
    public void enableMACLayerDebug(boolean selected) {
        if (!isValid()) {
            return;
        }
        Collection nodes = getSimulator().getNodes();
        for (Object n : nodes) {
            Node node = (Node) n;
            node.getMacLayer().setDebugEnabled(selected);

        }
    }

    /**
     *
     * @param selected
     */
    public void enableRoutingLayerDebug(boolean selected) {
        if (!isValid()) {
            return;
        }
        Collection nodes = getSimulator().getNodes();
        for (Object n : nodes) {
            Node node = (Node) n;
            node.getRoutingLayer().setDebugEnabled(selected);

        }
    }

    /**
     *
     */
    public void enterPlatform() {
    }

    /**
     *
     */
    public void exitPlatform() {
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
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
                maxX += getNodeFactory().getNodeMaxRadioRange();
                maxY -= getNodeFactory().getNodeMaxRadioRange();
                minX += getNodeFactory().getNodeMaxRadioRange();
                minY -= getNodeFactory().getNodeMaxRadioRange();
                h = maxY - minY;
                w = maxX - minX;
                return new Dimension(w, h);
            }
            return new Dimension(0, 0);
        } else {
            return new Dimension(0, 0);
        }
    }

    /**
     *
     * @return
     */
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
        RandomList allNodes = new RandomList(getSimulator().getNodes());
        while (randomNodes.size() < nroNodes && allNodes.size() > 0) {

            Node node = (Node) allNodes.randomElement();
            if (condition.select(node)) {
                randomNodes.add(node);
            }
            allNodes.remove(node);
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

    /**
     *
     * @param state
     */
    public void setSimulationState(String state) {
        simulationState = state;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param listener
     */
    public void addSimulationListener(SimulationListener listener) {
        simulationListeners.add(SimulationListener.class, listener);
    }

    /**
     *
     * @param listener
     */
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

    /**
     *
     * @return
     */
    protected boolean isValid() {
        return getSimulator() != null;
    }

    /**
     *
     * @return
     */
    protected boolean isSimulationPlatformRegistered() {
        return simulationGUI != null;
    }

    /**
     *
     * @return
     */
    public ISimulationGUI getSimulationGUI() {
        return simulationGUI;
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
    public boolean isLogEnergyEnable() {
        return getEnergyController().isLogEnergyEnable();
    }

    /**
     *
     * @param logEnergyEnable
     */
    public void setLogEnergyEnable(boolean logEnergyEnable) {
        getEnergyController().setLogEnergyEnable(logEnergyEnable);
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

    /**
     *
     * @return
     */
    public IOutputDisplay getApplicationOutputDisplay() {
        return applicationOutputDisplay;
    }

    /**
     *
     * @param applicationOutputDisplay
     */
    public void setApplicationOutputDisplay(IOutputDisplay applicationOutputDisplay) {
        this.applicationOutputDisplay = applicationOutputDisplay;
    }

    /**
     *
     * @return
     */
    public IOutputDisplay getMacOutputDisplay() {
        return macOutputDisplay;
    }

    /**
     *
     * @param macOutputDisplay
     */
    public void setMacOutputDisplay(IOutputDisplay macOutputDisplay) {
        this.macOutputDisplay = macOutputDisplay;
    }

    /**
     *
     * @return
     */
    public IOutputDisplay getRoutingOutputDisplay() {
        return routingOutputDisplay;
    }

    /**
     *
     * @param routingOutputDisplay
     */
    public void setRoutingOutputDisplay(IOutputDisplay routingOutputDisplay) {
        this.routingOutputDisplay = routingOutputDisplay;
    }

    /**
     *
     * @param event
     */
    public void onEmptyQueue(SimulatorEvent event) {
        fireOnEmptyQueue(new SimulationEvent(this));
    }

    /**
     *
     * @param event
     */
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
    public void markStableNodes(boolean status, NodeSelectionCondition condition) {
        if (getSimulator().getNodes().size() > 0) {
            for (Node node : getSimulator().getNodes()) {
                if (condition.select(node)) {
                    if (status) {
                        node.getGraphicNode().markStable();
                    } else {
                        node.getGraphicNode().unmarkStable();
                    }
                }

            }
        }
    }

    @Override
    public void initialSetup() {
        super.initialSetup();
        getEnergyModel().setParameters(settings.getEnergyModelParameters());
        getSimulator().addListener(this);
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param filename
     * @throws Exception
     */
    public void saveNetworkTopology(String filename) throws Exception {
        if (this.isValid()) {
            saveNT(filename);
        } else {
            throw new Exception("Simulation isn't valid");
        }
    }

    /**
     *
     * @param filename
     * @throws Exception
     */
    public void loadNetworkTopology(String filename) throws Exception {
        XMLConfiguration file = new XMLConfiguration();
        file.load(filename);
        int numberOfNodes = file.getInt("simulation.topology.nodes.size");

        for (int i = 0; i < numberOfNodes; i++) {
            String tag = "simulation.topology.nodes.node(" + i + ")";
            double x = file.getDouble(tag + ".x");
            double y = file.getDouble(tag + ".y");
            double z = file.getDouble(tag + ".z");
            boolean s = file.getBoolean(tag + ".sink");
            Node node = getNodeFactory().createNode();
            node.setPosition(x, y, z);
            node.setSinkNode(s);
            getSimulator().addNode(node);
        }

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
            file.addProperty("simulation.topology.nodes.node.sink", node.isSinkNode());

        }
        File f = new File(filename);
        if (f.exists()) {
            f.delete();
        }
        file.save(filename);
    }

    /**
     *
     * @param settings
     */
    @Override
    public void create(SimulationSettings settings) {
        try {
            this.settings = settings; // save settings
            setSimulator((Simulator) Utilities.loadClassInstance(settings.getSimulatorClassName()));
            setNodeFactory((AbstractNodeFactory) Utilities.loadClassInstance(settings.getNodeFactoryClassName()));
            getNodeFactory().setNodeMaxRadioRange(settings.getMaxNodeRadioRange());
            setRadioModel((RadioModel) Utilities.loadClassInstance(settings.getRadioModelClassName()));
            setEnergyModel(((EnergyModel) Utilities.loadClassInstance(settings.getEnergyModelClassName())).getInstanceWithDefaultValues());

            getNodeFactory().setEnvironmentAttenuation(settings.getEnvironAttenuation());
            getNodeFactory().setStaticZ(settings.isStaticZ());
            getNodeFactory().setMaxZ(settings.getMaxZ());
            getNodeFactory().setMinZ(settings.getMinZ());
            setMode(settings.isFastMode() ? Simulator.FAST : Simulator.REAL);
            Simulator.randomGenerator = new RandomGenerator(settings.getSeed());

            initialSetup();

        } catch (Exception ex) {
            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void saveToXML(XMLConfiguration configuration) throws PersistantException {
        settings.saveToXML(configuration);
        MACLayer.getController().saveToXML(configuration);
        RoutingLayer.getController().saveToXML(configuration);
    }

    /**
     *
     * @param configuration
     * @throws PersistantException
     */
    public void loadFromXML(XMLConfiguration configuration) throws PersistantException {
        settings.loadFromXML(name);
    }

    /**
     *
     */
    @Override
    public void reset() {
        super.reset();

        getSimulator().reset();
        MACLayer.getController().reset();
        RoutingLayer.getController().reset();
        getEnergyController().reset();
    }

    public int getNumberOfAttackedNodes() {
        int ct = 0;
        for (Node node : getSimulator().getNodes()) {
            if (node.getRoutingLayer().isUnderAttack()) {
                ct++;
            }
        }
        return ct;
    }

    /**
     *
     * @return
     */
    public int getNumberOfSinkNodes() {
        int ct = 0;
        for (Node node : getSimulator().getNodes()) {
            if (node.isSinkNode()) {
                ct++;
            }
        }
        return ct;
    }

    /**
     *
     * @param test
     */
    public void notifyEndTest(AbstractTest test) {
        fireAfterTestExecution(new SimulationTestEvent(test));
    }

    private void fireAfterTestExecution(SimulationTestEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).afterTestExecution(event);
            }
        }
    }

    /**
     *
     * @param test
     */
    public void notifyStartTest(AbstractTest test) {
        fireStartTestExecution(new SimulationTestEvent(test));
    }

    private void fireStartTestExecution(SimulationTestEvent event) {
        Object[] listeners = simulationListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationListener) listeners[i + 1]).startTestExecution(event);
            }
        }
    }

    public void loadTestTopology(String filename) throws Exception {

        TestTopology testTopology = new TestTopology();
        testTopology.setSimulation(this);
        testTopology.loadFromXML(filename);
//      file.load(filename);
//        int numberOfNodes = file.getInt("attack.topology.nodes.size");
//
//        for (int i = 0; i < numberOfNodes; i++) {
//            String tag = "attack.topology.nodes.node(" + i + ")";
//            double x = file.getDouble(tag + ".id");
//            boolean s = file.getBoolean(tag + ".source");
//            boolean r = file.getBoolean(tag + ".receiver");
//            boolean a = file.getBoolean(tag + ".attacked");
//
//
//
////            getSimulator().addNode(node);
//        }

    }

    public void saveTestTopology(String file) throws Exception {
        TestTopology testTopology = new TestTopology();
        testTopology.setSimulation(this);
        testTopology.saveToXML(file);

    }

    public int[] countAdHocTestNodes() {

        if (!isValid()) {
            return null;
        }

        if (!isNetworkDeployed()) {
            return null;
        }
        Collection<Node> nodes = getSimulator().getNodes();

        int s = 0;
        int r = 0;
        int a = 0;
        for (Node node : nodes) {
            if (node.isSource()) {
                s++;
            }
            if (node.isReceiver()) {
                r++;
            }
            if (node.getRoutingLayer().isUnderAttack()) {
                a++;
            }
        }
        return new int[]{s, r, a};

    }
}
