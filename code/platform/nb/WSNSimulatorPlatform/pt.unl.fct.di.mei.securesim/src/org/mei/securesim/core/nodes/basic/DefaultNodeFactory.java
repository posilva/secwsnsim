/**
 * 
 */
package org.mei.securesim.core.nodes.basic;

import org.mei.securesim.core.Simulator;
import org.mei.securesim.core.factories.NodeFactory;

/**
 * @author posilva
 *
 */
@SuppressWarnings("unchecked")
public class DefaultNodeFactory extends NodeFactory {

	/**
	 * @param simulator
	 * @param classOfNodes
	 */
	
	public DefaultNodeFactory(Simulator simulator, Class classOfNodes,Class appClass, Class routingClass) {
		super(simulator, classOfNodes,appClass,routingClass);
	}

}
