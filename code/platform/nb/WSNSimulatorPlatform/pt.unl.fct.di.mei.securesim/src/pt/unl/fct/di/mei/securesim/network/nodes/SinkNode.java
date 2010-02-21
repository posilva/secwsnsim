/**
 * 
 */
package pt.unl.fct.di.mei.securesim.network.nodes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.Simulator;
import pt.unl.fct.di.mei.securesim.engine.radio.RadioModel;

/**
 * @author posilva
 *
 */
public abstract class SinkNode extends SensorNode {

	/**
	 * @param sim
	 * @param radioModel
	 */
	public SinkNode(Simulator sim, RadioModel radioModel) {
		super(sim, radioModel);
		this.sinkNode=true;
	}
	
	public void displayOn(ISimulationDisplay disp) {
		Graphics g = disp.getGraphics();
		super.displayOn(disp);
		int      x = disp.x2ScreenX(this.getX());
		int      y = disp.y2ScreenY(this.getY());
       
		g.setColor( Color.green);
		Polygon p = getTriangle(x,y);
		g.fillPolygon(p);
		g.setColor( Color.black );
		g.drawPolygon(p);
	}
	
	private Polygon getTriangle(int x, int y){
		Polygon p = new Polygon();
		p.addPoint(x, y-10);
		p.addPoint(x-10, y);
		p.addPoint(x+10, y);
		return p;
		
		
		
	}
}
