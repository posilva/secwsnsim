/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.unl.fct.di.mei.securesim.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import pt.unl.fct.di.mei.securesim.engine.ISimulationDisplay;
import pt.unl.fct.di.mei.securesim.engine.nodes.Node;

/**
 *
 * @author posilva
 */
public class GraphicNode {

    protected Node physicalNode;
    int id;
    static int count = 0;
    protected int x = 0;
    protected int y = 0;
    protected int z = 0;

    protected GraphicPoint center = new GraphicPoint(x, y);
    protected int radius = 3;
    protected boolean filled = false;
    protected boolean selected = false;
    protected Color selectedBackcolor = Color.YELLOW;
    protected Color backcolor = Color.RED;
    protected Color linecolor = Color.BLACK;
    protected Color oldColor = Color.WHITE;
    protected Color MARKEDCOLOR = Color.ORANGE;

    protected Rectangle rectangle;
    private boolean marked;


  

    public GraphicNode(int x, int y) {
        moveTo(x, y);
        this.id = count++;
    }

    public GraphicNode() {
        this.id = count++;
    }

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
    public GraphicPoint getCenter() {
        return center;
    }

    public void setCenter(GraphicPoint center) {
        this.center = center;
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
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
        int _x = display.x2ScreenX(x);
        int _y = display.y2ScreenY(y);
        saveOldColor(g);
        g.setColor(Color.BLACK);
        g.drawLine(_x, _y, _x, _y);
        restoreOldColor(g);

    }

    protected void fill(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(x);
        int _y = display.y2ScreenY(y);
        saveOldColor(g);
        g.setColor(backcolor);
        g.fillOval(_x - radius, _y - radius, radius * 2, radius * 2);
        restoreOldColor(g);
    }

    protected void paintSelectionBorder(ISimulationDisplay display) {
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(x);
        int _y = display.y2ScreenY(y);
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
        int _x = display.x2ScreenX(x);
        int _y = display.y2ScreenY(y);
        saveOldColor(g);
        g.setColor(linecolor);
        g.drawOval(_x - radius, _y - radius, radius * 2, radius * 2);
        restoreOldColor(g);
    }

    public void paint(ISimulationDisplay display) {
        paintMark(display);
        fill(display);
        
        paintBorder(display);
        paintSelectionBorder(display);
        
        //paintCenter(g);
    }

    public boolean contains(GraphicPoint p) {
        return p.distanceTo(center) <= radius;
    }

    public boolean contains(int x, int y) {
        GraphicPoint p = new GraphicPoint(x, y);
        return p.distanceTo(center) <= radius;
    }

    public void moveTo(int x, int y,int z) {
        this.z = z;
        moveTo(x, y);
    }
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
        rectangle = new Rectangle(x - radius, y - radius, radius + radius, radius + radius);
        this.center = new GraphicPoint(x, y);
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
        return center.distanceTo(c.center) <= radius + c.radius;
    }

    @Override
    public String toString() {
        return "Circle " + id;
    }

    
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
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

    private void paintMark(ISimulationDisplay display) {
        if(!isMarked()) return ;
        Graphics g = display.getGraphics();
        int _x = display.x2ScreenX(x);
        int _y = display.y2ScreenY(y);

        saveOldColor(g);
        g.setColor(MARKEDCOLOR);
        int r=(int) (radius*3);
        g.drawOval(_x - r, _y - r, r * 2, r * 2);
        g.fillOval(_x - r, _y - r, r * 2, r * 2);
        restoreOldColor(g);
    }
    
}
