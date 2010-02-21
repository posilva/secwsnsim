package pt.unl.fct.di.mei.securesim.simulation;

import java.util.logging.Logger;

import pt.unl.fct.di.mei.securesim.configuration.ConfigurableObject;
import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;
import pt.unl.fct.di.mei.securesim.network.Network;
import pt.unl.fct.di.mei.securesim.network.basic.DefaultNetwork;
import pt.unl.fct.di.mei.securesim.network.nodes.basic.DefaultNodeFactory;

public abstract class Simulation extends ConfigurableObject {

    public final static Logger LOG = Logger.getLogger(Simulation.class.getName());
    protected String name;
    protected DefaultSimulator simulator;
    protected RadioModel radioModel;
    protected DefaultNodeFactory simpleNodeFactory;
    protected DefaultNodeFactory sinkNodeFactory;
    protected DefaultNetwork network;
    protected ISimulationDisplay display;

    public Simulation() {
        super();
    }

    public void setup() {
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
        if (simpleNodeFactory == null) {
            throw new IllegalStateException("Não existe um Simple NodeFactory instanciado");
        }
        if (sinkNodeFactory == null) {
            throw new IllegalStateException("Não existe um Sink NodeFactory instanciado");
        }

        simulator.setRadioModel(radioModel);
        simulator.setNetwork(network);
        simulator.setDisplay(display);

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

    public void setSimulator(DefaultSimulator simulator) {
        this.simulator = simulator;
    }

    public Network getNetwork() {
        return this.network;
    }

    public void setNetwork(DefaultNetwork network) {
        this.network = network;
    }

    public abstract void stop();

    public abstract void start();

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

    public DefaultNodeFactory getSimpleNodeFactory() {
        return simpleNodeFactory;
    }

    public void setSimpleNodeFactory(DefaultNodeFactory simpleNodeFactory) {
        this.simpleNodeFactory = simpleNodeFactory;
    }

    public DefaultNodeFactory getSinkNodeFactory() {
        return sinkNodeFactory;
    }

    public void setSinkNodeFactory(DefaultNodeFactory sinkNodeFactory) {
        this.sinkNodeFactory = sinkNodeFactory;
    }
}
