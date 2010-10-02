/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.simulator.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class GraphicNode {

    public final static int UNDER_ATTACK = 1;
    public final static int SINK_NODE = 2;
    protected Node physicalNode;
    int id;
    static int count = 0;
    protected int radius = 1;
    protected boolean filled = false;
    protected boolean selected = false;
    protected Color selectedBackcolor = Color.RED;
    protected Color backcolor = Color.RED;
    protected Color linecolor = Color.BLACK;
    protected Color oldColor = Color.WHITE;
    protected Color markColor = Color.ORANGE;
    protected Color markStableColor = Color.CYAN;
    protected Color sourceColor = Color.GREEN;
    protected Color stableColor = Color.ORANGE;
    protected Color destinationColor = Color.BLUE;
    protected Color attackMarkColor = Color.RED;
    protected Rectangle rectangle;
    protected boolean marked;
    protected boolean source;
    protected boolean destination;
    protected int mode = -1;
    protected boolean markedAsStable;

    public GraphicNode(Node aPhysicalNode) {
        this.id = count++;
        this.physicalNode = aPhysicalNode;
    }

    public Color getBackcolor() {
        return backcolor;
    }

    public void setBackcolor(Color backcolor) {
        this.backcolor = backcolor;
    }

    public Color getSelectedBackcolor() {
        return selectedBackcolor;
    }

    public void setSelectedBackcolor(Color selectedBackcolor) {
        this.selectedBackcolor = selectedBackcolor;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getX() {
        return (int) getPhysicalNode().getX();
    }

    public void setX(int x) {
        this.getPhysicalNode().setX(x);
    }

    public int getY() {
        return (int) getPhysicalNode().getY();
    }

    public void setY(int y) {
        this.getPhysicalNode().setY(y);

    }

    public Node getPhysicalNode() {
        return physicalNode;
    }

    public void setPhysicalNode(Node physicalNode) {
        this.physicalNode = physicalNode;
    }

    protected void restoreOldColor(Graphics g) {
        g.setColor(oldColor);
    }

    protected void saveOldColor(Graphics g) {
        oldColor = g.getColor();
    }

    public void setOldColor(Color oldColor) {
        this.oldColor = oldColor;
    }

    protected void paintCenter(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());
        saveOldColor(g);
        g.setColor(Color.BLACK);
        g.drawLine(_x, _y, _x, _y);
        restoreOldColor(g);

    }

    protected void fill(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());
        saveOldColor(g);
        g.setColor(backcolor);
        g.fillOval(_x - radius, _y - radius, radius * 2, radius * 2);
        restoreOldColor(g);
    }

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

    protected void paintBorder(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(getX());
        int _y = display.y2ScreenY(getY());
        saveOldColor(g);
        g.setColor(linecolor);
        g.drawOval(_x - radius, _y - radius, radius * 2, radius * 2);
        restoreOldColor(g);
    }

    public void paint(ISimulationDisplay display) {
        paintMark(display);
        paintAsDestination(display);
        paintAsSource(display);
        paintMode(display);
        paintStable(display);
        fill(display);
        paintBorder(display);
        paintSelectionBorder(display);
    }

    public boolean contains(double _x, double _y) {
        return distanceTo(_x, _y) <= radius;
    }

    public boolean contains(int x, int y) {

        return distanceTo(x, y) <= radius;
    }

    public void moveTo(int x, int y, int z) {
        this.getPhysicalNode().setPosition(x, y, z);
        moveTo(x, y);
    }

    public void moveTo(int x, int y) {
        this.getPhysicalNode().setPosition(x, y, getZ());
        rectangle = new Rectangle(x - radius, y - radius, radius + radius, radius + radius);
    }

    public void select(boolean b) {
        selected = b;
    }

    public boolean isSelected() {
        return selected;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    public boolean intersects(GraphicNode c) {
        return distanceTo(c.getX(), c.getY()) <= radius + c.radius;
    }

    @Override
    public String toString() {
        return "Circle " + id;
    }

    public int getZ() {
        return (int) this.getPhysicalNode().getZ();
    }

    public void setZ(int z) {
        this.getPhysicalNode().setZ(z);
    }

    public void mark() {
        marked = true;
    }

    public void unmark() {
        marked = false;
    }

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
        int r = (int) (radius * 4);
        g.drawOval(_x - r, _y - r, r * 2, r * 2);
        g.fillOval(_x - r, _y - r, r * 2, r * 2);
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

    public Color getMarkColor() {
        return markColor;
    }

    public void setMarkColor(Color markColor) {
        this.markColor = markColor;
    }

    public boolean isDestination() {
        return destination;
    }

    public void setDestination(boolean destination) {
        this.destination = destination;
    }

    public boolean isSource() {
        return source;
    }

    public void setSource(boolean source) {
        this.source = source;
    }

    public Color getDestinationColor() {
        return destinationColor;
    }

    public void setDestinationColor(Color destinationColor) {
        this.destinationColor = destinationColor;
    }

    public Color getSourceColor() {
        return sourceColor;
    }

    public void setSourceColor(Color sourceColor) {
        this.sourceColor = sourceColor;
    }

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
                g.setColor(getAttackMarkColor());
                g.fillRect(_x - radius, _y - radius, radius * 3, radius * 3);
                break;
            case SINK_NODE:
                break;
        }
        restoreOldColor(g);
    }

    public boolean isMarkedAsStable() {
        return markedAsStable && getPhysicalNode().getRoutingLayer().isStable();
    }

    public void setStable(boolean stable) {
        this.markedAsStable = stable;
    }

    public Color getStableColor() {
        return stableColor;
    }

    public void setStableColor(Color stableColor) {
        this.stableColor = stableColor;
    }

    public Color getAttackMarkColor() {
        return attackMarkColor;
    }

    public void setAttackMarkColor(Color attackMarkColor) {
        this.attackMarkColor = attackMarkColor;
    }

    public double r() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public double theta() {
        return Math.atan2(getY(), getX());
    }

    // Euclidean distance between this point and that point
    public double distanceTo(double _x, double _y) {
        double dx = this.getX() - _x;
        double dy = this.getY() - _y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void unmarkStable() {
        markedAsStable = false;
    }

    public void markStable() {
        markedAsStable = true;

    }
}
