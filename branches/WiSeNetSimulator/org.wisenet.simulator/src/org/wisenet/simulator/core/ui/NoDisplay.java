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

    public NoDisplay(Simulator simulator) {
        this.simulator = simulator;

    }

    public NoDisplay() {
        super();
    }

    public Graphics getGraphics() {
        return null;
    }

    public int x2ScreenX(double x) {
        return (int) x;
    }

    public int y2ScreenY(double y) {
        return (int) y;
    }

    public void updateDisplay() {
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public void show() {
    }
}
