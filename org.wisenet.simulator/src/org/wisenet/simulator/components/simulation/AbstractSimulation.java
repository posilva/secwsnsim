package org.wisenet.simulator.components.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wisenet.simulator.common.PersistantObject;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.evaluation.tests.TestSet;
import org.wisenet.simulator.components.evaluation.tests.TestTypeEnum;
import org.wisenet.simulator.core.energy.EnergyController;
import org.wisenet.simulator.components.topology.TopologyManager;

import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.Batery;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.utilities.Utilities;
import org.wisenet.simulator.utilities.console.SimulationSettings;
import org.wisenet.simulator.utilities.xml.XMLFileReader;

/**
 *
 * @author posilva
 */
public abstract class AbstractSimulation extends PersistantObject implements ISimulationOperations {

    /**
     * Energy model for this simulation
     */
    int mode = Simulator.FAST;
    EnergyModel energyModel; //TODO: uma vez que passou para dentro da node factory deve-se poder tirar
    /**
     *
     */
    protected String name;
    private String description;
    /**
     *
     */
    protected Simulator simulator;
    /**
     *
     */
    protected RadioModel radioModel;
    /**
     *
     */
    protected AbstractNodeFactory nodeFactory;
    /**
     *
     */
    protected ISimulationDisplay display;
    /**
     *
     */
    protected int initialMaxNodeRange;
    /**
     *
     */
    protected long seed;
    private boolean bPreInit = false;
    private TestSet testSet = new TestSet();
    /**
     *
     */
    protected SimulationSettings settings;
    /**
     * Control flag for start state
     */
    protected boolean started;

    /**
     *
     */
    public AbstractSimulation() {
        super();
    }

    /**
     *
     * @return
     */
    public boolean isStarted() {
        return started;
    }

    /**
     *
     * @param started
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    public abstract void stop();

    /**
     *
     */
    public abstract void start();

    /**
     *
     */
    public abstract void pause();

    /**
     *
     */
    public void setup() {
        if (!bPreInit) {
            preInit();
        }
        if (nodeFactory == null) {
            throw new IllegalStateException("must exist a node factory instance!");
        }
    }

    /**
     *
     * @return
     */
    public int getInitialMaxNodeRange() {
        return initialMaxNodeRange;
    }

    /**
     *
     * @param nodeRange
     */
    public void setInitialMaxNodeRange(int nodeRange) {
        this.initialMaxNodeRange = nodeRange;
    }

    /**
     *
     * @return
     */
    public long getSeed() {
        return seed;
    }

    /**
     *
     * @param seed
     */
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     *
     * @return
     */
    public ISimulationDisplay getDisplay() {
        return display;
    }

    /**
     *
     * @param display
     */
    public void setDisplay(ISimulationDisplay display) {
        this.display = display;
        if (getSimulator() != null) {
            getSimulator().setDisplay(display);

        }
    }

    /**
     *
     * @return
     */
    public Simulator getSimulator() {
        return this.simulator;
    }

    /**
     *
     * @param simulator
     */
    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
        this.simulator.setSimulation(this);
        if (getDisplay() != null) {
            this.simulator.setDisplay(getDisplay());
        }

    }

    /**
     *
     */
    public void reset() {
        started = false;
        this.bPreInit = false;

    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public RadioModel getRadioModel() {
        return radioModel;
    }

    /**
     *
     * @param radioModel
     */
    public void setRadioModel(RadioModel radioModel) {
        this.radioModel = radioModel;
    }

    /**
     *
     * @return
     */
    public AbstractNodeFactory getNodeFactory() {
        return nodeFactory;
    }

    /**
     *
     * @param simpleNodeFactory
     */
    public void setNodeFactory(AbstractNodeFactory simpleNodeFactory) {
        this.nodeFactory = simpleNodeFactory;
    }

    /**
     *
     */
    public void preInit() {
        bPreInit = true;
        if (simulator == null) {
            throw new IllegalStateException("Must set a simulator instance");
        }
        if (radioModel == null) {
            throw new IllegalStateException("Must set a radiomodel instance");
        }
        if (display == null) {
            throw new IllegalStateException("Must set a display instance");
        }
        simulator.setRadioModel(radioModel);
        radioModel.reset();
        simulator.setDisplay(display);
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     */
    public void resume() {
        simulator.resume();
    }

    /**
     *
     * @return
     */
    public long getTime() {
        return Simulator.getSimulationTime();
    }

    /**
     *
     * @return
     */
    public long getTimeInMilliseconds() {
        return Simulator.getSimulationTimeInMillisec();
    }

    /**
     * Load configurations from file
     * @param file
     * @return
     */
    public boolean loadFromFile(String file) {
        File f = new File(file);
        NodeList childs;
        if (f.exists()) {
            XMLFileReader fileReader = new XMLFileReader(file);
            fileReader.open();
            Node root = fileReader.getRootNode();
            if (root.getChildNodes().getLength() > 0) {
                childs = root.getChildNodes();
                for (int i = 0; i < childs.getLength(); i++) {
                    Node child = childs.item(i);
                    if (child.getNodeName().equals("Name")) {
                        this.name = child.getNodeValue();
                    } else if (child.getNodeName().equals("Description")) {
                        this.description = child.getNodeValue();
                    } else if (child.getNodeName().equals("Seed")) {
                        this.seed = Long.valueOf(child.getNodeValue());
                    }
                }
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * Read a simulation configuration from a file
     * @param file
     * @return
     * @throws SimulationException
     */
    public AbstractSimulation readFromFile(String file) throws SimulationException {
        try {
            AbstractSimulation sim = this;
            Properties props = new Properties();
            props.load(new FileInputStream(file));

            sim.setName(props.getProperty("simulation.name"));
            sim.setDescription(props.getProperty("simulation.description"));
            sim.setSeed(Long.valueOf(props.getProperty("simulation.seed")));
            sim.setInitialMaxNodeRange(Integer.valueOf(props.getProperty("simulation.noderange")));
            String simulatorClass = props.getProperty("simulation.simulator.class");
            String nodeFactoryClass = props.getProperty("simulation.nodefactory.class");
            String radioModelClass = props.getProperty("simulation.radiomodel.class");
            String energyModelClass = props.getProperty("simulation.energymodel.class");
            int nrNodes = Integer.valueOf(props.getProperty("simulation.nodes.count"));

            Object instance = Utilities.loadClassInstance(simulatorClass);
            sim.setSimulator((Simulator) instance);
            instance = Utilities.loadClassInstance(nodeFactoryClass);
            sim.setNodeFactory((AbstractNodeFactory) instance);
            instance = Utilities.loadClassInstance(radioModelClass);
            sim.setRadioModel((RadioModel) instance);
            instance = Utilities.loadClassInstance(energyModelClass);
            sim.getNodeFactory().setEnergyModel(((EnergyModel) instance).getInstanceWithDefaultValues());

            sim.getSimulator().setRadioModel(sim.getRadioModel());
            sim.getNodeFactory().setSimulator(sim.getSimulator());

            for (int i = 0; i < nrNodes; i++) {
                String key = "simulation.nodes.node" + i + ".";
                short id = Short.valueOf(props.getProperty(key + "id"));

                double x = Double.valueOf(props.getProperty(key + "X"));
                double y = Double.valueOf(props.getProperty(key + "Y"));
                double z = Double.valueOf(props.getProperty(key + "Z"));
                double m = Double.valueOf(props.getProperty(key + "maxstrenght"));

                org.wisenet.simulator.core.node.Node n = sim.getNodeFactory().createNode(id);
                n.setPosition(x, y, z);
                n.getConfig().setMaximumRadioStrength(m);
                n.getConfig().setSetRadioRange(sim.getInitialMaxNodeRange());
                sim.getSimulator().addNode(n);
            }
            return sim;
        } catch (Exception ex) {
            throw new SimulationException(ex);
        }
    }

    /**
     * Save this simulation settings
     * @param file
     * @throws SimulationException
     */
    public void save(String file) throws SimulationException {
        createSimulationFile(this, file);
    }

    /**
     * Create a simulation file based on a simulation
     * @param simulation 
     * @param file
     * @throws SimulationException
     */
    public static void createSimulationFile(AbstractSimulation simulation, String file) throws SimulationException {
        try {
            Properties props = new Properties();
            props.setProperty("simulation.name", simulation.getName());
            props.setProperty("simulation.description", simulation.getDescription());
            props.setProperty("simulation.seed", String.valueOf(simulation.getSeed()));
            props.setProperty("simulation.noderange", String.valueOf(simulation.getInitialMaxNodeRange()));
            props.setProperty("simulation.simulator.class", simulation.getSimulator().getClass().getCanonicalName());
            props.setProperty("simulation.radiomodel.class", simulation.getRadioModel().getClass().getCanonicalName());
            props.setProperty("simulation.nodefactory.class", simulation.getNodeFactory().getClass().getCanonicalName());
            props.setProperty("simulation.energymodel.class", simulation.getNodeFactory().getEnergyModel().getClass().getCanonicalName());
            props.setProperty("simulation.nodes.count", String.valueOf(simulation.getSimulator().getNodes().size()));
            int i = 0;
            for (org.wisenet.simulator.core.node.Node n : simulation.getSimulator().getNodes()) {
                String key = "simulation.nodes.node" + i + ".";
                props.setProperty(key + "id", String.valueOf(n.getId()));
                props.setProperty(key + "X", String.valueOf(n.getX()));
                props.setProperty(key + "Y", String.valueOf(n.getY()));
                props.setProperty(key + "Z", String.valueOf(n.getZ()));
                props.setProperty(key + "maxstrenght", String.valueOf(n.getConfig().getMaximumRadioStrength()));
                i++;
            }
            props.store(new FileOutputStream(file), "");
        } catch (IOException ex) {
            throw new SimulationException("CreateSimulation File Failed: " + ex.getCause().getMessage());
        }

    }

    /**
     *
     * @return
     */
    public EnergyModel getEnergyModel() {
        return energyModel;
    }

    /**
     *
     * @param energyModel
     */
    public void setEnergyModel(EnergyModel energyModel) {
        this.energyModel = energyModel;
    }

    /**
     * Initial phase for simulation setup
     */
    public void initialSetup() {
        if (simulator == null) {
            throw new IllegalStateException("Must set a simulator instance");
        }
        if (radioModel == null) {
            throw new IllegalStateException("Must set a radiomodel instance");
        }
        if (display == null) {
            throw new IllegalStateException("Must set a display instance");
        }
        simulator.setRadioModel(radioModel);
        simulator.setMode(mode);
        nodeFactory.setEnergyModel(energyModel);
        nodeFactory.setSimulator(simulator);
        radioModel.reset();
        simulator.setDisplay(display);
        bPreInit = true;
    }

    /**
     *
     * @return
     */
    public int getMode() {
        return mode;
    }

    /**
     *
     * @param mode
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     *
     * @param topologyManager
     * @throws Exception
     */
    public void appendNodes(TopologyManager topologyManager) throws Exception {
        topologyManager.setNodeFactory(getNodeFactory());
        List<org.wisenet.simulator.core.node.Node> nodes = topologyManager.createTopology();
        for (org.wisenet.simulator.core.node.Node node : nodes) {
            getSimulator().addNode(node);
        }
    }

    /**
     *
     * @return
     */
    public EnergyController getEnergyController() {
        return Batery.getController();
    }

    /**
     *
     * @param testType
     * @return
     */
    public List getTestByType(TestTypeEnum testType) {
        return testSet.getTestByType(testType);
    }

    /**
     *
     * @return
     */
    public SimulationSettings getSettings() {
        return settings;
    }

    /**
     *
     * @param settings
     */
    public abstract void create(SimulationSettings settings);

    /**
     *
     * @param result
     */
    public void addTest(AbstractTest result) {
    }
}
