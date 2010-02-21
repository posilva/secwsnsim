/**
 * 
 */
package pt.unl.fct.di.mei.securesim.network.nodes.basic;

import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;
import pt.unl.fct.di.mei.securesim.network.nodes.SimpleNode;

/**
 * @author posilva
 *
 */
public class BasicSimpleNode extends SimpleNode {

	/**
	 * @param sim
	 * @param radioModel
	 */
	public BasicSimpleNode(Simulator sim, RadioModel radioModel) {
		super(sim, radioModel);
	}
	@Override
	public void displayOn(ISimulationDisplay disp) {
		super.displayOn(disp);
	}
	
}
