package pt.unl.fct.di.mei.securesim.engine;

import java.awt.Graphics;

public interface ISimulationDisplay {

	/* (non-Javadoc)
	 * @see net.tinyos.prowler.IDisplay#getGraphics()
	 */
	public abstract Graphics getGraphics();

	/* (non-Javadoc)
	 * @see net.tinyos.prowler.IDisplay#x2ScreenX(double)
	 */
	public abstract int x2ScreenX(double x);

	/* (non-Javadoc)
	 * @see net.tinyos.prowler.IDisplay#y2ScreenY(double)
	 */
	public abstract int y2ScreenY(double y);

	/* (non-Javadoc)
	 * @see net.tinyos.prowler.IDisplay#update()
	 */
	public abstract void update();


    public Simulator getSimulator();

   // public void  setSimulator(Simulator s);

    public void show();
    
}