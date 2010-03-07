package org.mei.securesim.simulation;

import java.util.logging.Logger;

import org.mei.securesim.configuration.ConfigurableObject;
import org.mei.securesim.core.ISimulationDisplay;
import org.mei.securesim.core.Simulator;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.network.Network;
import org.mei.securesim.network.basic.DefaultNetwork;
import org.mei.securesim.core.nodes.basic.DefaultNodeFactory;

public abstract class Simulation extends ConfigurableObject {

    public final static Logger LOG = Logger.getLogger(Simulation.class.getName());
    protected String name;
    protected DefaultSimulator simulator;
    protected RadioModel radioModel;
    protected DefaultNodeFactory simpleNodeFactory;
    protected DefaultNodeFactory sinkNodeFactory;
    protected DefaultNetwork network;
    protected ISimulationDisplay display;
    private boolean bPreInit=false;

    public Simulation() {
        super();
    }

    public void setup() {
        if(!bPreInit) preInit();
        if (simpleNodeFactory == null) {
            throw new IllegalStateException("Não existe um Simple NodeFactory instanciado");
        }
        if (sinkNodeFactory == null) {
            throw new IllegalStateException("Não existe um Sink NodeFactory instanciado");
        }

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
    protected void init() {
    }

    public void preInit() {
        bPreInit=true;
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
        simulator.setNetwork(network);
        simulator.setDisplay(display);

    }

  public void reset(){
      
  }
}
