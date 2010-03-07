/**
 * 
 */
package org.mei.securesim.simulation;

import java.util.Collection;

import org.mei.securesim.core.Simulator;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.network.Network;

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
