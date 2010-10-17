/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class GraphicNode {

    /**
     *
     */
    public final static int UNDER_ATTACK = 1;
    /**
     *
     */
    public final static int SINK_NODE = 2;
    /**
     *
     */
    public static int NONE = -1;
    /**
     *
     */
    protected Node physicalNode;
    int id;
    static int count = 0;
    /**
     *
     */
    protected int radius = 1;
    /**
     *
     */
    protected boolean filled = false;
    /**
     *
     */
    protected boolean selected = false;
    /**
     *
     */
    protected Color selectedBackcolor = Color.RED;
    /**
     *
     */
    protected Color backcolor = Color.RED;
    /**
     *
     */
    protected Color linecolor = Color.BLACK;
    /**
     *
     */
    protected Color oldColor = Color.WHITE;
    /**
     *
     */
    protected Color markColor = Color.ORANGE;
    /**
     *
     */
    protected Color markStableColor = Color.CYAN;
    /**
     *
     */
    protected Color sourceColor = Color.GREEN;
    /**
     *
     */
    protected Color stableColor = Color.ORANGE;
    /**
     *
     */
    protected Color destinationColor = Color.BLUE;
    /**
     *
     */
    protected Color attackMarkColor = Color.RED;
    /**
     *
     */
    protected Color receiverColor = Color.DARK_GRAY;
    /**
     *
     */
    protected Rectangle rectangle;
    /**
     *
     */
    protected boolean marked;
    /**
     *
     */
    protected boolean source;
    /**
     *
     */
    protected boolean destination;
    /**
     *
     */
    protected int mode = -1;
    /**
     *
     */
    protected boolean markedAsStable;

    /**
     *
     * @param aPhysicalNode
     */
    public GraphicNode(Node aPhysicalNode) {
        this.id = count++;
        this.physicalNode = aPhysicalNode;
    }

    /**
     *
     * @return
     */
    public Color getBackcolor() {
        return backcolor;
    }

    /**
     *
     * @param backcolor
     */
    public void setBackcolor(Color backcolor) {
        this.backcolor = backcolor;
    }

    /**
     *
     * @return
     */
    public Color getSelectedBackcolor() {
        return selectedBackcolor;
    }

    /**
     *
     * @param selectedBackcolor
     */
    public void setSelectedBackcolor(Color selectedBackcolor) {
        this.selectedBackcolor = selectedBackcolor;
    }

    /**
     *
     * @return
     */
    public boolean isFilled() {
        return filled;
    }

    /**
     *
     * @param filled
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getRadius() {
        return radius;
    }

    /**
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return (int) getPhysicalNode().getX();
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.getPhysicalNode().setX(x);
    }

    /**
     *
     * @return
     */
    public int getY() {
        return (int) getPhysicalNode().getY();
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.getPhysicalNode().setY(y);

    }

    /**
     *
     * @return
     */
    public Node getPhysicalNode() {
        return physicalNode;
    }

    /**
     *
     * @param physicalNode
     */
    public void setPhysicalNode(Node physicalNode) {
        this.physicalNode = physicalNode;
    }

    /**
     *
     * @param g
     */
    protected void restoreOldColor(Graphics g) {
        g.setColor(oldColor);
    }

    /**
     *
     * @param g
     */
    protected void saveOldColor(Graphics g) {
        oldColor = g.getColor();
    }

    /**
     *
     * @param oldColor
     */
    public void setOldColor(Color oldColor) {
        this.oldColor = oldColor;
    }

    /**
     *
     * @param display
     */
    protected void paintCenter(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());
        saveOldColor(g);
        g.setColor(Color.BLACK);
        g.drawLine(_x, _y, _x, _y);
        restoreOldColor(g);

    }

    /**
     *
     * @param display
     */
    protected void fill(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());
        saveOldColor(g);
        g.setColor(backcolor);
        g.fillOval(_x - radius, _y - radius, radius * 2, radius * 2);
        restoreOldColor(g);
    }

    /**
     *
     * @param display
     */
    protected void paintSelectionBorder(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());
        if (!selected) {
            return;

        }
        saveOldColor(g);
        g.setColor(selectedBackcolor);
        g.drawRect(_x - radius, _y - radius, radius * 2, radius * 2);
        restoreOldColor(g);
    }

    /**
     *
     * @param display
     */
    protected void paintBorder(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());
        saveOldColor(g);
        g.setColor(linecolor);
        g.drawOval(_x - radius, _y - radius, radius * 2, radius * 2);
        restoreOldColor(g);
    }

    /**
     *
     * @param display
     */
    public void paint(ISimulationDisplay display) {
        paintMark(display);
        paintAsDestination(display);
        paintStable(display);
        fill(display);
        paintAsSource(display);
        paintAsReceiver(display);
        paintMode(display);
        paintBorder(display);
        paintSelectionBorder(display);
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public boolean contains(double _x, double _y) {
        return distanceTo(_x, _y) <= radius;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public boolean contains(int x, int y) {

        return distanceTo(x, y) <= radius;
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void moveTo(int x, int y, int z) {
        this.getPhysicalNode().setPosition(x, y, z);
        moveTo(x, y);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        this.getPhysicalNode().setPosition(x, y, getZ());
        rectangle = new Rectangle(x - radius, y - radius, radius + radius, radius + radius);
    }

    /**
     *
     * @param b
     */
    public void select(boolean b) {
        selected = b;
    }

    /**
     *
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     *
     * @return
     */
    public double area() {
        return Math.PI * radius * radius;
    }

    /**
     *
     * @return
     */
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean intersects(GraphicNode c) {
        return distanceTo(c.getX(), c.getY()) <= radius + c.radius;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Circle " + id;
    }

    /**
     *
     * @return
     */
    public int getZ() {
        return (int) this.getPhysicalNode().getZ();
    }

    /**
     *
     * @param z
     */
    public void setZ(int z) {
        this.getPhysicalNode().setZ(z);
    }

    /**
     *
     */
    public void mark() {
        marked = true;
    }

    /**
     *
     */
    public void unmark() {
        marked = false;
    }

    /**
     *
     * @return
     */
    public boolean isMarked() {
        return marked;
    }

    private void paintAsDestination(ISimulationDisplay display) {
        if (!isDestination()) {
            return;
        }
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());

        saveOldColor(g);
        g.setColor(destinationColor);
        int r = (int) (radius * 4);
        g.drawOval(_x - r, _y - r, r * 2, r * 2);
        g.fillOval(_x - r, _y - r, r * 2, r * 2);
        restoreOldColor(g);
    }

    private void paintAsSource(ISimulationDisplay display) {
        if (!isSource()) {
            return;
        }
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());

        saveOldColor(g);
        g.setColor(sourceColor);
        int r = (int) (radius * 2);
        g.drawRect(_x - r, _y - r, r * 2, r * 2);
        g.fillRect(_x - r, _y - r, r * 2, r * 2);
        restoreOldColor(g);
    }

    private void paintAsReceiver(ISimulationDisplay display) {
        if (!isReceiver()) {
            return;
        }
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());

        saveOldColor(g);
        g.setColor(receiverColor);
        int r = (int) (radius * 2);
        Polygon p = new Polygon();
        p.addPoint(_x, _y - r);
        p.addPoint(_x + r, _y);

        p.addPoint(_x, _y + r);
        p.addPoint(_x - r, _y);
        g.drawPolygon(p);
        g.fillPolygon(p);
        restoreOldColor(g);
    }

    private void paintMark(ISimulationDisplay display) {
        if (!isMarked()) {
            return;
        }
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());

        saveOldColor(g);
        g.setColor(markColor);
        int r = (int) (radius * 3);
        g.drawOval(_x - r, _y - r, r * 2, r * 2);
        g.fillOval(_x - r, _y - r, r * 2, r * 2);
        restoreOldColor(g);
    }

    private void paintStable(ISimulationDisplay display) {
        if (!isMarkedAsStable()) {
            return;
        }

        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());

        saveOldColor(g);
        g.setColor(getStableColor());
        int r = (int) (radius * 4);
        g.drawOval(_x - r, _y - r, r * 2, r * 2);
        g.fillOval(_x - r, _y - r, r * 2, r * 2);
        restoreOldColor(g);
    }

    /**
     *
     * @return
     */
    public Color getMarkColor() {
        return markColor;
    }

    /**
     *
     * @param markColor
     */
    public void setMarkColor(Color markColor) {
        this.markColor = markColor;
    }

    /**
     *
     * @return
     */
    public boolean isDestination() {
        return destination;
    }

    /**
     *
     * @param destination
     */
    public void setDestination(boolean destination) {
        this.destination = destination;
    }

    /**
     *
     * @return
     */
    public boolean isSource() {
        return getPhysicalNode().isSource();
    }

    /**
     *
     * @param source
     */
    public void setSource(boolean source) {
        this.source = source;
    }

    /**
     *
     * @return
     */
    public Color getDestinationColor() {
        return destinationColor;
    }

    /**
     *
     * @param destinationColor
     */
    public void setDestinationColor(Color destinationColor) {
        this.destinationColor = destinationColor;
    }

    /**
     *
     * @return
     */
    public Color getSourceColor() {
        return sourceColor;
    }

    /**
     *
     * @param sourceColor
     */
    public void setSourceColor(Color sourceColor) {
        this.sourceColor = sourceColor;
    }

    /**
     *
     * @param mode
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    private void paintMode(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());
        saveOldColor(g);

        if (this.mode == -1) {
            return;
        }


        switch (this.mode) {
            case UNDER_ATTACK:
                Polygon p = new Polygon();
                int r = radius * 2;
                p.addPoint(_x, _y - r);
                p.addPoint(_x - r, _y + r);
                p.addPoint(_x + r, _y + r);
                g.setColor(getAttackMarkColor());
                g.fillPolygon(p);
//                g.fillRect(_x - radius, _y - radius, radius * 3, radius * 3);
                break;
            case SINK_NODE:
                break;
        }
        restoreOldColor(g);
    }

    /**
     *
     * @return
     */
    public boolean isMarkedAsStable() {
        return markedAsStable && getPhysicalNode().getRoutingLayer().isStable();
    }

    /**
     *
     * @param stable
     */
    public void setStable(boolean stable) {
        this.markedAsStable = stable;
    }

    /**
     *
     * @return
     */
    public Color getStableColor() {
        return stableColor;
    }

    /**
     *
     * @param stableColor
     */
    public void setStableColor(Color stableColor) {
        this.stableColor = stableColor;
    }

    /**
     *
     * @return
     */
    public Color getAttackMarkColor() {
        return attackMarkColor;
    }

    /**
     *
     * @param attackMarkColor
     */
    public void setAttackMarkColor(Color attackMarkColor) {
        this.attackMarkColor = attackMarkColor;
    }

    /**
     *
     * @return
     */
    public double r() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    /**
     *
     * @return
     */
    public double theta() {
        return Math.atan2(getY(), getX());
    }

    // Euclidean distance between this point and that point
    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public double distanceTo(double _x, double _y) {
        double dx = this.getX() - _x;
        double dy = this.getY() - _y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     *
     */
    public void unmarkStable() {
        markedAsStable = false;
    }

    /**
     *
     */
    public void markStable() {
        markedAsStable = true;

    }

    /**
     *
     */
    public void reset() {
//        backcolor = Color.RED;
    }

    private boolean isReceiver() {
        return getPhysicalNode().isReceiver();
    }

    /**
     *
     * @return
     */
    public Color getMarkStableColor() {
        return markStableColor;
    }

    /**
     *
     * @param markStableColor
     */
    public void setMarkStableColor(Color markStableColor) {
        this.markStableColor = markStableColor;
    }

    /**
     *
     * @return
     */
    public Color getReceiverColor() {
        return receiverColor;
    }

    /**
     *
     * @param receiverColor
     */
    public void setReceiverColor(Color receiverColor) {
        this.receiverColor = receiverColor;
    }
}
