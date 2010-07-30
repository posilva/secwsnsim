/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.simulator.core.network;

import java.awt.Color;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.gui.IDisplayable;

/**
 *
 * @author posilva
 */
public class Message implements IDisplayable {

    protected boolean paint = false;
    protected Color color = Color.BLACK;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isPaint() {
        return paint;
    }

    public void setPaint(boolean paint) {
        this.paint = paint;
    }

    public Message() {
        this.paint = false;
    }

    public Message(boolean paint) {
        this.paint = true;
    }

    public Message(boolean paint, Color color) {
        this.paint = true;
        this.color = color;
    }

    public void displayOn(ISimulationDisplay display) {
        if (display != null) {
            if (paint) {
            }
        }
    }
}
