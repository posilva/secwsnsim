/**
 * 
 */
package pt.unl.fct.di.mei.securesim.simulation;

import java.util.Collection;

import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.network.Network;

/**
 * @author posilva
 * 
 */
public class DefaultSimulator extends Simulator {
	private Network network = null;

	/**
	 * 
	 */
	public DefaultSimulator() {

	}

	/**
	 * @param network the network to set
	 */
	public void setNetwork(Network network) {
		this.network = network;
		this.network.setSimulator(this);
	}

	/**
	 * @return the network
	 */
	public Network getNetwork() {
		return network;
	}
	
	public Collection<Node> getNodes(){
		return network.getNodeDB().nodes();
	}
}
