/**
 * 
 */
package pt.unl.fct.di.mei.securesim.network.nodes;


import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;

/**
 * @author posilva
 *
 */
public abstract class SimpleNode extends SensorNode {

	/**
	 * @param sim
	 * @param radioModel
	 */
	public SimpleNode(Simulator sim, RadioModel radioModel) {
		super(sim, radioModel);
	}

	@Override
	public void displayOn(ISimulationDisplay disp) {
//		Graphics g = disp.getGraphics();
		super.displayOn(disp);
//		int      x = disp.x2ScreenX(this.x);
//		int      y = disp.y2ScreenY(this.y);
//        g.setColor( Color.yellow);
//        g.fillOval( x-radius, y-radius, radius*2, radius*2);
//		g.setColor( Color.black );
//		g.drawOval(x-radius, y-radius, radius*2, radius*2);
        getGraphicNode().paint(disp);
	}
}
