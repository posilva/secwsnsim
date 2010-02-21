/**
 * 
 */
package pt.unl.fct.di.mei.securesim.network.nodes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.nodes.Mica2Node;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;

/**
 * @author posilva
 * 
 */
public abstract class SensorNode extends Mica2Node {

	protected boolean sinkNode = false;
	private boolean paintNeighborhood = false;
	private boolean showID = true;
	private static Font monoFont = new Font("Courier", Font.BOLD, 8);
	
	/**
	 * @param sim
	 * @param radioModel
	 */
	public SensorNode(Simulator sim, RadioModel radioModel) {
		super(sim, radioModel);
	}

	/**
	 * @return the sinkNode
	 */
	public boolean isSinkNode() {
		return sinkNode;
	}

	@Override
	public void displayOn(ISimulationDisplay disp) {
		Graphics g = disp.getGraphics();
		super.displayOn(disp);
		int      x = disp.x2ScreenX(this.getX());
		int      y = disp.y2ScreenY(this.getY());
		Color oldColor = g.getColor();
		Font oldFont = g.getFont();
		
//		if (isPaintNeighborhood()) {
//
//            g.setColor(Color.BLUE);
//			for (Node n : getNeighborhood().neighbors) {
//				int x2 = disp.x2ScreenX(n.getX());
//				int y2 = disp.y2ScreenY(n.getY());
//				g.drawLine(x, y, x2, y2);
//			}
//		}
//
//		if (showID){
//			g.setColor(Color.black);
//			g.setFont(monoFont);
//		    g.drawString(""+id, x+10, y+10);
//		}
		g.setColor(oldColor);
		g.setFont(oldFont);
	}

	/**
	 * @param paintNeighborhood
	 *            the paintNeighborhood to set
	 */
	public void setPaintNeighborhood(boolean paintNeighborhood) {
		this.paintNeighborhood = paintNeighborhood;
	}

	/**
	 * @return the paintNeighborhood
	 */
	public boolean isPaintNeighborhood() {
		return paintNeighborhood;
	}

	/**
	 * @param showID the showID to set
	 */
	public void setShowID(boolean showID) {
		this.showID = showID;
	}

	/**
	 * @return the showID
	 */
	public boolean isShowID() {
		return showID;
	}
}
