package org.mei.securesim.test.pingpong;

import java.awt.Color;
import java.awt.Graphics;

import org.mei.securesim.core.ISimulationDisplay;
import org.mei.securesim.core.Simulator;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.core.radio.RadioModel;
import org.mei.securesim.ui.IDisplayable;
import org.mei.securesim.core.nodes.basic.Mica2SensorNode;

public class PingPongNode extends Mica2SensorNode implements IDisplayable {

    private static int CLEAR_TIME = Simulator.ONE_SECOND * 10;


    public PingPongNode(Simulator sim, RadioModel radioModel) {
        super(sim, radioModel);
        setRoutingLayer(new PingPongRoutingLayer_());
    }
   
    /** This field is true if this mote rebroadcasted the message already. */
    boolean sent = false;
    /** This field stores the mote from which the message was first received. */
    private Node parent = null;

    /**
     * Draws a filled circle, which is: <br>
     *  - blue if the node is sending a message <br>
     *  - red if the node received a corrupted message <br>
     *  - green if the node received a message without problems <br>
     *  - pink if the node sent a message <br>
     *  - and black as a default<br>
     * It also draws a line between mote and its parent, which is another mote
     * who sent the message first to this.
     */
    @Override
    public void displayOn(ISimulationDisplay disp) {
        Graphics g = disp.getGraphics();

        int _x = disp.x2ScreenX(this.getX());
        int _y = disp.y2ScreenY(this.getY());

        super.displayOn(disp);
        if (turnedOn) {


            Color c = g.getColor();
            if (getId() == 1) {
                c = Color.yellow;
            } else if (getId()==945) {
                c = Color.MAGENTA;
            } else if (getMacLayer().isSending()) {
                c = Color.blue;
            } else if (getMacLayer().isReceiving()) {
                if (getMacLayer().isCorrupted()) {
                    c = Color.red;
                } else {
                    c = Color.green;
                }
            } else {
                if (sent) {
                    c = Color.pink;
                } else {
                    c = Color.CYAN;
                }
            }
            if (parent != null) {
                g.setColor(Color.black);
                int x1 = disp.x2ScreenX(parent.getX());
                int y1 = disp.y2ScreenY(parent.getY());
                g.drawLine((int) _x, (int) _y, x1, y1);
            }
            getGraphicNode().setBackcolor(c);
            getGraphicNode().paint(disp);
        } else {
            getGraphicNode().setBackcolor(Color.WHITE);
            getGraphicNode().paint(disp);
        }

    }

    public void sentMenssage(boolean b) {
        sent = true;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }
    
    public static PingPongNode cast(Node n) {
        return (PingPongNode) n;
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
}
