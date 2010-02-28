/**
 * 
 */
package pt.unl.fct.di.mei.securesim.topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.network.Network;

/**
 * @author posilva
 *
 */
public class MinDistanceRandomTopologyManager extends RandomTopologyManager {
	
	protected int minimumDistance=-1;

	/**
	 * 
	 */
	public MinDistanceRandomTopologyManager() {
		
	}

	
	/* (non-Javadoc)
	 * @see pt.unl.fct.di.mei.securesim.configuration.ConfigurableObject#init()
	 */
	@Override
	protected void init() {
		super.init();

	}
	public void apply(Network network) {
		List<Node> nodesHandled = new ArrayList<Node>();
		if (random== null)
			random= new Random();
		for (Node n : network.getNodeDB().nodes()) {
			boolean isMinimum=false;
			while(!isMinimum){
				double x = network.getSimulationArea().getWidth() * random.nextDouble();
				double y = network.getSimulationArea().getHeigth() * random.nextDouble();
				double z = network.getSimulationArea().getMaxElevation() * random.nextDouble();
				n.setPosition(x,y,z);
				isMinimum=moreThanMinimumDistance(n, nodesHandled);
			}
			nodesHandled.add(n);
		}
	}
	protected boolean moreThanMinimumDistance(Node node , List<Node> nodes){
		if (minimumDistance==-1) return true;
		for (Node node2 : nodes) {
			if(Math.sqrt(node.getDistanceSquare(node2))<minimumDistance)
				return false;
		}
		return true;
	}

	/**
	 * @param minimumDistance the minimumDistance to set
	 */
	public void setMinimumDistance(int minimumDistance) {
		this.minimumDistance = minimumDistance;
	}


	/**
	 * @return the minimumDistance
	 */
	public int getMinimumDistance() {
		return minimumDistance;
	}

	
	
	
}
