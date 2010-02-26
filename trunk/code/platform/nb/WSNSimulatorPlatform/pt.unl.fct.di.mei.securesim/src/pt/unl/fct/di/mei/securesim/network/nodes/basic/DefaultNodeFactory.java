/**
 * 
 */
package pt.unl.fct.di.mei.securesim.network.nodes.basic;

import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.network.nodes.NodeFactory;

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
