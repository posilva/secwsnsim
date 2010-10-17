/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.ui;

import java.awt.Graphics;
import org.wisenet.simulator.core.Simulator;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class NoDisplay implements ISimulationDisplay {

    private Simulator simulator=null;

    /**
     *
     * @param simulator
     */
    public NoDisplay(Simulator simulator) {
        this.simulator = simulator;

    }

    /**
     *
     */
    public NoDisplay() {
        super();
    }

    /**
     *
     * @return
     */
    public Graphics getGraphics() {
        return null;
    }

    /**
     *
     * @param x
     * @return
     */
    public int x2ScreenX(double x) {
        return (int) x;
    }

    /**
     *
     * @param y
     * @return
     */
    public int y2ScreenY(double y) {
        return (int) y;
    }

    /**
     *
     */
    public void updateDisplay() {
    }

    /**
     *
     * @return
     */
    public Simulator getSimulator() {
        return simulator;
    }

    /**
     *
     */
    public void show() {
    }
}
