/**
 * 
 */
package pt.unl.fct.di.mei.securesim.network.nodes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import pt.unl.fct.di.mei.securesim.core.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.core.Simulator;
import pt.unl.fct.di.mei.securesim.core.nodes.Mica2Node;
import pt.unl.fct.di.mei.securesim.core.nodes.Node;
import pt.unl.fct.di.mei.securesim.core.radio.RadioModel;

/**
 * @author posilva
 * 
 */
public abstract class SensorNode extends Mica2Node {

    protected boolean sinkNode = false;
    private boolean paintNeighborhoodDst = false;
    private boolean paintNeighborhoodOrg = false;
    private boolean showID = false;
    private static Font monoFont = new Font("Courier", Font.BOLD, 8);

    /**
     * @param sim
     * @param radioModel
     */
    public SensorNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
    }

    /**
     * @return the sinkNode
     */
    public boolean isSinkNode() {
        return sinkNode;
    }

    @Override
    public void displayOn(ISimulationDisplay disp) {
        Graphics g = disp.getGraphics();
        Color oldColor = g.getColor();
        int x = disp.x2ScreenX(this.getX());
        int y = disp.y2ScreenY(this.getY());

        Font oldFont = g.getFont();

        if (turnedOn) {
            super.displayOn(disp);

            if (isPaintNeighborhoodDst() && isPaintNeighborhoodOrg()) {
                for (Node n : getNeighborhood().neighbors) {
                    int x2 = disp.x2ScreenX(n.getX());
                    int y2 = disp.y2ScreenY(n.getY());
                    // tem os dois sentidos
                    if (getNeighborhood().neighborsThatKnowMeSet.contains(n)) {
                        g.setColor(Color.GREEN);
                        g.drawLine(x, y, x2, y2);
                    } else {
                        if (isPaintNeighborhoodDst()) {
                            g.setColor(Color.BLUE);
                            g.drawLine(x, y, x2, y2);
                        }
//                        else {
//                            if (isPaintNeighborhoodOrg()) {
//                                g.setColor(Color.RED);
//                                g.drawLine(x, y, x2, y2);
//                            }
//                        }

                    }
                }
            } else {
                if (isPaintNeighborhoodDst()) {
                    g.setColor(Color.BLUE);
                    for (Node n : getNeighborhood().neighbors) {
                        int x2 = disp.x2ScreenX(n.getX());
                        int y2 = disp.y2ScreenY(n.getY());
                        g.drawLine(x, y, x2, y2);
                    }
                } else {
                    if (isPaintNeighborhoodOrg()) {
                        g.setColor(Color.RED);
                        for (Node n : getNeighborhood().neighborsThatKnowMe) {
                            int x2 = disp.x2ScreenX(n.getX());
                            int y2 = disp.y2ScreenY(n.getY());
                            g.drawLine(x, y, x2, y2);
                        }
                    }
                }
            }

        } else {
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
}
