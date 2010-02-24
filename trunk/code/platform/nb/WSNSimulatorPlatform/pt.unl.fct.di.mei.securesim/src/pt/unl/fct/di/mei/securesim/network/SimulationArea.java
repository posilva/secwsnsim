/**
 * 
 */
package pt.unl.fct.di.mei.securesim.network;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.ui.IDisplayable;

/**
 * @author posilva
 * 
 */
public class SimulationArea implements IDisplayable{
	protected int width;

	protected int heigth;

	protected int maxElevation;
    private static final int MARGIN=20;

	/**
	 * 
	 */
	public SimulationArea() {
		// TODO Auto-generated constructor stub
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return this.heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getMaxElevation() {
		return this.maxElevation;
	}

	public void setMaxElevation(int maxElevation) {
		this.maxElevation = maxElevation;
	}

    public Dimension getDimension(){
        return new Dimension(width+MARGIN,heigth+MARGIN);
    }

    public void displayOn(ISimulationDisplay disp) {
        Graphics g = disp.getGraphics();
        Color oldColor = g.getColor();

        g.setColor(Color.black);
        g.drawRect(0, 0, width, heigth);


        g.setColor(oldColor);

    }
}
