/**
 * 
 */
package org.mei.securesim.topology;

import java.util.List;
import org.mei.securesim.core.nodes.Node;

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
