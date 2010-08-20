/**
 * 
 */
package org.wisenet.simulator.core.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.radio.RadioModel;
import org.wisenet.simulator.gui.GraphicNode;

/**
 * @author posilva
 * 
 */
public abstract class SensorNode extends Node {

    protected Color baseColor = Color.YELLOW;
    protected Color receivingCorruptedColor = Color.red;
    protected Color receivingNotCorruptedColor = Color.green;
    protected Color sendingColor = Color.blue;
    protected boolean paintNeighborhoodDst = false;
    protected boolean paintNeighborhoodOrg = false;
    protected boolean showID = false;
    protected static Font monoFont = new Font("Courier", Font.BOLD, 8);

    /**
     * @param sim
     * @param radioModel
     */
    public SensorNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
        //    configureMACLayer(radioModel);

    }

    protected abstract void configureMACLayer(RadioModel radioModel);

    @Override
    public synchronized void displayOn(ISimulationDisplay disp) {
        Graphics g = disp.getGraphics();
        Color oldColor = g.getColor();
        int x = disp.x2ScreenX(this.getX());
        int y = disp.y2ScreenY(this.getY());
        super.displayOn(disp);
        
        Font oldFont = g.getFont();
        if (turnedOn) {

            if (isPaintNeighborhoodDst() && isPaintNeighborhoodOrg()) {
                for (Node n : getMacLayer().getNeighborhood().neighbors) {
                    int x2 = disp.x2ScreenX(n.getX());
                    int y2 = disp.y2ScreenY(n.getY());
                    // tem os dois sentidos
                    if (getMacLayer().getNeighborhood().neighborsThatKnowMeSet.contains(n)) {
                        g.setColor(Color.GREEN);
                        g.drawLine(x, y, x2, y2);
                    } else {
                        if (isPaintNeighborhoodDst()) {
                            g.setColor(Color.BLUE);
                            g.drawLine(x, y, x2, y2);
                        }
                    }
                }
            } else {
                if (isPaintNeighborhoodDst()) {
                    g.setColor(Color.BLUE);
                    for (Node n : getMacLayer().getNeighborhood().neighbors) {
                        int x2 = disp.x2ScreenX(n.getX());
                        int y2 = disp.y2ScreenY(n.getY());
                        g.drawLine(x, y, x2, y2);
                    }
                } else {
                    if (isPaintNeighborhoodOrg()) {
                        g.setColor(Color.RED);
                        for (Node n : getMacLayer().getNeighborhood().neighborsThatKnowMe) {
                            int x2 = disp.x2ScreenX(n.getX());
                            int y2 = disp.y2ScreenY(n.getY());
                            g.drawLine(x, y, x2, y2);
                        }
                    }
                }
            }
            if(getRoutingLayer().isUnderAttack()){
                getGraphicNode().setMode(GraphicNode.UNDER_ATTACK);
            }
        } else {
            // do nothing 
        }
        if (showID) {
            g.setColor(Color.black);
            g.setFont(monoFont);
            g.drawString("" + getId(), x - getRadius() * 2, y - getRadius() * 2);
        }

        g.setColor(oldColor);

        g.setFont(oldFont);
    }

    /**
     * @param paintNeighborhood
     *            the paintNeighborhood to set
     */
    public void setPaintNeighborhood(boolean paintNeighborhood) {
        this.paintNeighborhoodDst = paintNeighborhood;


    }

    /**
     * @return the paintNeighborhood
     */
    public boolean isPaintNeighborhoodDst() {
        return paintNeighborhoodDst;


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

    public static SensorNode cast(Node n) {
        return (SensorNode) n;


    }

    public boolean isPaintNeighborhoodOrg() {
        return paintNeighborhoodOrg;


    }

    public void setPaintNeighborhoodOrg(boolean paintNeighborhoodOrg) {
        this.paintNeighborhoodOrg = paintNeighborhoodOrg;

    }

    @Override
    public void init() {
        configureMACLayer(getSimulator().getRadioModel());
    }

    public Color getBaseColor() {
        return graphicNode.getBackcolor();
    }

    public void setBaseColor(Color baseColor) {
        this.graphicNode.setBackcolor(baseColor);
    }

    public Color getReceivingCorruptedColor() {
        return receivingCorruptedColor;
    }

    public void setReceivingCorruptedColor(Color receivingCorruptedColor) {
        this.receivingCorruptedColor = receivingCorruptedColor;
    }

    public Color getReceivingNotCorruptedColor() {
        return receivingNotCorruptedColor;
    }

    public void setReceivingNotCorruptedColor(Color receivingNotCorruptedColor) {
        this.receivingNotCorruptedColor = receivingNotCorruptedColor;
    }

    public Color getSendingColor() {
        return sendingColor;
    }

    public void setSendingColor(Color sendingColor) {
        this.sendingColor = sendingColor;
    }
}
