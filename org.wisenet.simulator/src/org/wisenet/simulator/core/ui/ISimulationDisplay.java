package org.wisenet.simulator.core.ui;

import org.wisenet.simulator.core.Simulator;
import java.awt.Graphics;

/**
 *
 * @author posilva
 */
public interface ISimulationDisplay {

	/* (non-Javadoc)
	 * @see net.tinyos.prowler.IDisplay#getGraphics()
	 */
    /**
     *
     * @return
     */
    public abstract Graphics getGraphics();

	/* (non-Javadoc)
	 * @see net.tinyos.prowler.IDisplay#x2ScreenX(double)
	 */
    /**
     *
     * @param x
     * @return
     */
    public abstract int x2ScreenX(double x);

	/* (non-Javadoc)
	 * @see net.tinyos.prowler.IDisplay#y2ScreenY(double)
	 */
        /**
         *
         * @param y
         * @return
         */
        public abstract int y2ScreenY(double y);

	/* (non-Javadoc)
	 * @see net.tinyos.prowler.IDisplay#updateDisplay()
	 */
        /**
         *
         */
        public abstract void updateDisplay();


        /**
         *
         * @return
         */
        public Simulator getSimulator();

   // public void  setSimulator(Simulator s);

    /**
     *
     */
    public void show();
    
}