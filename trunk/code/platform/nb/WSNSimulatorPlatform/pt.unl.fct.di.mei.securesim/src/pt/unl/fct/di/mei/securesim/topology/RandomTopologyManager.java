/**
 * 
 */
package pt.unl.fct.di.mei.securesim.topology;

import java.util.Random;

import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.network.Network;
import pt.unl.fct.di.mei.securesim.network.SimulationArea;

/**
 * @author posilva
 *
 */
public class RandomTopologyManager extends TopologyManager {

	protected Random random =null;
	
	
	/**
	 * 
	 */
	public RandomTopologyManager() {
		
	}

	/* (non-Javadoc)
	 * @see pt.unl.fct.di.mei.securesim.configuration.ConfigurableObject#init()
	 */
	@Override
	protected void init() {
		

	}

	/**
	 * @param random the random to set
	 */
	public void setRandom(Random random) {
		this.random = random;
	}

	/**
	 * @return the random
	 */
	public Random getRandom() {
		return random;
	}

	@Override
	public void apply(Network network) {
		if (random== null)
			random= new Random();
		for (Node n : network.getNodeDB().nodes()) {
            final double x = network.getSimulationArea().getWidth() * random.nextDouble();
            final double y = network.getSimulationArea().getHeigth() * random.nextDouble();
            final double z = network.getSimulationArea().getMaxElevation() * random.nextDouble();
			n.setPosition(x, y, z);
		}
	}

	
	
	
}
