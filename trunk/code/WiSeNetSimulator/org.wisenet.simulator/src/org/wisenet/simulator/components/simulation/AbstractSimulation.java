package org.wisenet.simulator.components.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wisenet.simulator.components.instruments.ISimulationOperations;

import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.energy.EnergyModel;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.utilities.Utilities;
import org.wisenet.simulator.utilities.xml.XMLFileReader;


public abstract class AbstractSimulation implements ISimulationOperations {

    protected String name;
    private String description;
    protected Simulator simulator;
    protected RadioModel radioModel;
    protected AbstractNodeFactory nodeFactory;
    protected ISimulationDisplay display;
    protected int initialMaxNodeRange;
    protected long seed;
    private boolean bPreInit = false;

    public AbstractSimulation() {
        super();
    }

    public abstract void stop();

    public abstract void start();

    public abstract void pause();

    public void setup() {
        if (!bPreInit) {
            preInit();
        }
        if (nodeFactory == null) {
            throw new IllegalStateException("must exist a node factory instance!");
        }
    }

    public int getInitialMaxNodeRange() {
        return initialMaxNodeRange;
    }

    public void setInitialMaxNodeRange(int nodeRange) {
        this.initialMaxNodeRange = nodeRange;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public ISimulationDisplay getDisplay() {
        return display;
    }

    public void setDisplay(ISimulationDisplay display) {
        this.display = display;
    }

    public Simulator getSimulator() {
        return this.simulator;
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
        this.simulator.setSimulation(this);
    }

    public void reset() {
        this.name = "";
        this.bPreInit = false;
        this.description = "";
        this.initialMaxNodeRange = 0;
        this.radioModel = null;
        this.seed = 0;
        this.nodeFactory = null;
        this.simulator = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RadioModel getRadioModel() {
        return radioModel;
    }

    public void setRadioModel(RadioModel radioModel) {
        this.radioModel = radioModel;
    }

    public AbstractNodeFactory getNodeFactory() {
        return nodeFactory;
    }

    public void setNodeFactory(AbstractNodeFactory simpleNodeFactory) {
        this.nodeFactory = simpleNodeFactory;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void resume() {
        simulator.resume();
    }

    public long getTime() {
        return getSimulator().getSimulationTime();
    }

    public long getTimeInMilliseconds() {
        return getSimulator().getSimulationTimeInMillisec();
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
     * @throws SimulationBuilderException
     */
    public AbstractSimulation readFromFile(String file) throws  SimulationException {
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
            String energyModel = props.getProperty("simulation.energymodel.class");
            int nrNodes = Integer.valueOf(props.getProperty("simulation.nodes.count"));

            Object instance = Utilities.loadClassInstance(simulatorClass);
            sim.setSimulator((Simulator) instance);
            instance = Utilities.loadClassInstance(nodeFactoryClass);
            sim.setNodeFactory((AbstractNodeFactory) instance);
            instance = Utilities.loadClassInstance(radioModelClass);
            sim.setRadioModel((RadioModel) instance);
            instance = Utilities.loadClassInstance(energyModel);
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
     * @param file
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
}
