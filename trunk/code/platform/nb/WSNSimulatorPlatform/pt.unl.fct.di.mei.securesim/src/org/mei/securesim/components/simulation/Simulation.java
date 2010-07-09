package org.mei.securesim.components.simulation;

import java.util.logging.Logger;
import org.mei.securesim.components.instruments.SimulationController;

import org.mei.securesim.components.configuration.ConfigurableObject;
import org.mei.securesim.core.ui.ISimulationDisplay;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.core.network.Network;
import org.mei.securesim.core.nodes.factories.NodeFactory;

public abstract class Simulation extends ConfigurableObject {

    public final static Logger LOG = Logger.getLogger(Simulation.class.getName());
    protected String name;
    private String description;
    protected Simulator simulator;
    protected RadioModel radioModel;
    protected NodeFactory simpleNodeFactory;
    protected Network network;
    protected ISimulationDisplay display;
    protected int nodeRange;
    protected long seed;
    private boolean bPreInit = false;

    public Simulation() {
        super();
        SimulationController.getInstance().registerSimulation(this);

    }

    public void setup() {
        if (!bPreInit) {
            preInit();
        }
        if (simpleNodeFactory == null) {
            throw new IllegalStateException("Não existe um Simple NodeFactory instanciado");
        }
    }

    public int getNodeRange() {
        return nodeRange;
    }

    public void setNodeRange(int nodeRange) {
        this.nodeRange = nodeRange;
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
    }

    public Network getNetwork() {
        return this.network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public abstract void stop();

    public abstract void start();

    public void reset() {
        this.name="";
        this.bPreInit=false;
        this.description="";
        this.network=null;
        this.nodeRange=0;
        this.radioModel=null;
        this.seed=0;
        this.simpleNodeFactory=null;
        this.simulator=null;
    }

    public abstract void pause();

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

    public NodeFactory getNodeFactory() {
        return simpleNodeFactory;
    }

    public void setNodeFactory(NodeFactory simpleNodeFactory) {
        this.simpleNodeFactory = simpleNodeFactory;
    }

    protected void init() {
    }

    public void preInit() {
        SimulationController.getInstance().resetSimulation();
        bPreInit = true;
        if (simulator == null) {
            throw new IllegalStateException("Não existe um simulador instanciado");
        }
        if (radioModel == null) {
            throw new IllegalStateException("Não existe um radiomodel instanciado");
        }
        if (network == null) {
            throw new IllegalStateException("Não existe uma network instanciada");
        }
        if (display == null) {
            throw new IllegalStateException("Não existe um display instanciado");
        }
        simulator.setRadioModel(radioModel);
        radioModel.reset();
        network.reset();
        simulator.setNetwork(network);

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
}
