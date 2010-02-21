package pt.unl.fct.di.mei.securesim.simulation;

import java.util.logging.Logger;

import pt.unl.fct.di.mei.securesim.configuration.BaseConfigurationException;
import pt.unl.fct.di.mei.securesim.configuration.ConfigurableObject;
import pt.unl.fct.di.mei.securesim.configuration.Configuration;
import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.network.Network;

public abstract class Simulation extends ConfigurableObject{
	private final static Logger LOG = Logger.getLogger(Simulation.class.getName());
	
	protected Simulator simulator;
	
	protected SimulationController controller;
	
	protected Network network;

    protected ISimulationDisplay display;

    public ISimulationDisplay getDisplay() {
        return display;
    }

    public void setDisplay(ISimulationDisplay display) {
        this.display = display;
    }


	public Simulation() {
		super();
	}
	
	public Simulator getSimulator() {
		return this.simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public SimulationController getController() {
		return this.controller;
	}

	public void setController(SimulationController controller) {
		this.controller = controller;
	}

	public Network getNetwork() {
		return this.network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public abstract void stop() ;
	public  abstract void start() ;
	
	public static void main(String[] args) {
		Simulation s = new Simulation() {
			
			@Override
			protected void init() {
				
			}

			@Override
			public void start() {
				LOG.info("Simulation Started");
			}

			@Override
			public void stop() {
				LOG.info("Simulation Stoped");
			}
		};
		
		Network n= new  Network() {
			
			@Override
			protected void init() {
				
			}
		};;

		SimulationController c=new SimulationController();
		Simulator sim=new Simulator();
		s.setController(c);
		s.setNetwork(n);
		s.setSimulator(sim);
		Configuration conf=new SimulationConfiguration();
		s.setConfiguration(conf);
		try {
			s.getConfiguration().loadFromFile("conf/simulation.config.xml");
		} catch (BaseConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
