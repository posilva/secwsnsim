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
public class BasicNodeFactory extends NodeFactory {

	/**
	 * @param simulator
	 * @param classOfNodes
	 */
	
	public BasicNodeFactory(Simulator simulator, Class classOfNodes) {
		super(simulator, classOfNodes);
	}

}
