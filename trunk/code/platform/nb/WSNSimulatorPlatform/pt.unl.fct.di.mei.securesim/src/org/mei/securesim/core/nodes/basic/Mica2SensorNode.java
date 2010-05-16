/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mei.securesim.core.nodes.basic;

import java.awt.Color;
import java.awt.Graphics;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.core.ui.ISimulationDisplay;

/**
 *
 * @author posilva
 */
public abstract class Mica2SensorNode extends SensorNode {
    private Color paintingPathColor = Color.LIGHT_GRAY;;

    private boolean paintingPaths;

    public Mica2SensorNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
    }

    @Override
    public void configureMACLayer(RadioModel radioModel) {

        if (macLayer == null) {
            throw new IllegalStateException("MacLayer cannot be null");
        }
        getMacLayer().setNode(this);
        getMacLayer().setRadioModel(radioModel);
        getMacLayer().setNeighborhood(radioModel.createNeighborhood());
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void displayOn(ISimulationDisplay disp) {
        Graphics g = disp.getGraphics();

        int _x = disp.x2ScreenX(this.getX());
        int _y = disp.y2ScreenY(this.getY());

        super.displayOn(disp);

        if (turnedOn) {
            Color c = g.getColor();
            if (getMacLayer().isSending()) {
                c = getSendingColor();
            } else if (getMacLayer().isReceiving()) {
                if (getMacLayer().isCorrupted()) {
                    c = getReceivingCorruptedColor();
                } else {
                    c = getReceivingNotCorruptedColor();
                }
            } else {
                c = getBaseColor();
            }

            getGraphicNode().setBackcolor(c);
            getGraphicNode().paint(disp);

            if (paintingPaths) {
                paintPath2Parent(g, _x, _y);
            }

        } else {
            getGraphicNode().setBackcolor(Color.WHITE);
            getGraphicNode().paint(disp);
        }
    }

    private void paintPath2Parent(Graphics g, int _x, int _y) {
        Color oldcolor = g.getColor();
        if (getParentNode() != null) {
            Color c = paintingPathColor;
            int x1 = getParentNode().getGraphicNode().getX();
            int y1 = getParentNode().getGraphicNode().getY();
            g.drawLine(_x, _y, x1, y1);
        }
        g.setColor(oldcolor);
    }

    public boolean isPaintingPaths() {
        return paintingPaths;
    }

    public void setPaintingPaths(boolean paintingPaths) {
        this.paintingPaths = paintingPaths;
    }

    public Color getPaintingPathColor() {
        return paintingPathColor;
    }

    public void setPaintingPathColor(Color paintingPathColor) {
        this.paintingPathColor = paintingPathColor;
    }
    
}
