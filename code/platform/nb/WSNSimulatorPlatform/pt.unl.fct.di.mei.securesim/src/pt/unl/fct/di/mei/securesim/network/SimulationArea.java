/**
 * 
 */
package pt.unl.fct.di.mei.securesim.network;

import java.awt.Dimension;

/**
 * @author posilva
 * 
 */
public class SimulationArea {
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
}
