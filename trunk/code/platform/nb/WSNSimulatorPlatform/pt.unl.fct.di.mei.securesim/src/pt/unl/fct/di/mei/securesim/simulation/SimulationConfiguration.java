package pt.unl.fct.di.mei.securesim.simulation;

import java.util.logging.Logger;

import pt.unl.fct.di.mei.securesim.configuration.Configuration;

public class SimulationConfiguration extends Configuration{
	
	private final static Logger LOGGER = Logger.getLogger(SimulationConfiguration.class.toString());

	private int numberOfNodes;
	private String name;
	
	
	@Override
	protected void init() {
		this.name =this.config.getString("configuration.name");
		this.numberOfNodes = this.config.getInt("configuration.network.numberOfNodes");
		LOGGER.info("Simulation Name: "+ this.name);
		LOGGER.info("Simulation Nro Nodes: "+ this.numberOfNodes);
		
	}
	
}
