package pt.unl.fct.di.mei.securesim.platform.ui;

import pt.unl.fct.di.mei.securesim.test.broadcast.BroadcastRoutingLayer;
import pt.unl.fct.di.mei.securesim.test.broadcast.BroadcastNode;
import pt.unl.fct.di.mei.securesim.test.broadcast.BroadcastApplication;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

import pt.unl.fct.di.mei.securesim.engine.*;
import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.engine.radio.GaussianRadioModel;
import pt.unl.fct.di.mei.securesim.network.SimulationArea;
import pt.unl.fct.di.mei.securesim.network.basic.DefaultNetwork;
import pt.unl.fct.di.mei.securesim.network.nodes.*;
import pt.unl.fct.di.mei.securesim.network.nodes.basic.DefaultNodeFactory;
import pt.unl.fct.di.mei.securesim.simulation.DefaultSimulator;
import pt.unl.fct.di.mei.securesim.simulation.SimulationConfiguration;
import pt.unl.fct.di.mei.securesim.simulation.basic.BasicSimulation;
import pt.unl.fct.di.mei.securesim.topology.RandomTopologyManager;
import pt.unl.fct.di.mei.securesim.ui.*;

/**
 *
 * @author posilva
 */
public class SimulationPanel extends javax.swing.JPanel implements ISimulationDisplay {

    public static final int OFFSET_NETWORK = 0;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    //   ArrayList<GraphicNode> graphicNodes = new ArrayList<GraphicNode>();
    ArrayList<GraphicNode> selectedNodes = new ArrayList<GraphicNode>();
    BufferedImage backImage = null;
    private Graphics currentGraphics;
    private SimulationArea simulationArea;
    private Thread simulationThread;
    private SimpleNode sinkNode;
    private BroadcastApplication app;
    private BasicSimulation simulation;
    private RandomTopologyManager topologyManager;
    private Dimension dim;
    private boolean selectionTool = false;
    private GraphicPoint pressedPoint = null;
    private GeneralPath selectedArea;
    private boolean individualNodeSelectionTool = false;
    private int mouseX;
    private int mouseY;
    private boolean paintMouseCoordinates = false;
    private GraphicNode currentSelectedNode;

    /** Creates new form SimulationPanel */
    public SimulationPanel() {
        initComponents();
        simulation = new BasicSimulation();
        simulation.setDisplay(this);
        simulationArea = new SimulationArea();
        simulationArea.setHeigth(600);
        simulationArea.setWidth(800);
        simulationArea.setMaxElevation(0);
        try {
            loadImage();
        } catch (Exception ex) {
            Logger.getLogger(SimulationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateMouseCoordinates(MouseEvent evt) {
//        mouseX = x2ScreenX(evt.getX());
//        mouseY = y2ScreenY(evt.getY());
        mouseX = evt.getX();
        mouseY = evt.getY();
    }

    void fireEnterCircleInfoEvent(NodeInfoEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == MouseOverNodeEventListener.class) {
                ((MouseOverNodeEventListener) listeners[i + 1]).mouseEnterCircleOccured(evt);
            }
        }
    }

    void fireExitCircleInfoEvent(NodeInfoEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == MouseOverNodeEventListener.class) {
                ((MouseOverNodeEventListener) listeners[i + 1]).mouseExitCircleOccured(evt);
            }
        }
    }

    public void addMouseOverCircleEventListener(MouseOverNodeEventListener listener) {
        listenerList.add(MouseOverNodeEventListener.class, listener);
    }

    public void removeMouseOverCircleEventListener(MouseOverNodeEventListener listener) {
        listenerList.remove(MouseOverNodeEventListener.class, listener);
    }

    public void paintNetwork(Graphics g) {
        if (simulation == null) {
            return;
        }
        //  dim = getSize();
        //  Image offscreen = createImage(dim.width, dim.height);
        Graphics b = g;// offscreen.getGraphics();

        currentGraphics = b;

        // paint background
//        b.setColor(Color.white);
//        b.fillRect(0, 0, dim.width, dim.height);

        // draw nodes
        if (simulation.getSimulator() != null) {
            simulation.getSimulator().display(this);
        }
//        g.drawImage(offscreen, 0, 0, this);

    }

    public void deployNetwork(int size) {
        createSimulation(size);
        setPreferredSize(getSize());
        update();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        currentSelectedNodePopupMenu = new javax.swing.JPopupMenu();
        selNodeVerVizinhos = new javax.swing.JCheckBoxMenuItem();
        selNodeVerID = new javax.swing.JCheckBoxMenuItem();

        currentSelectedNodePopupMenu.setLabel("Configurar Sensor");

        selNodeVerVizinhos.setSelected(true);
        selNodeVerVizinhos.setText("Ver Vizinhos");
        selNodeVerVizinhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeVerVizinhosActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeVerVizinhos);

        selNodeVerID.setSelected(true);
        selNodeVerID.setText("Ver ID");
        selNodeVerID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeVerIDActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeVerID);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(pt.unl.fct.di.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(SimulationPanel.class);
        setBackground(resourceMap.getColor("background")); // NOI18N
        setBorder(null);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        updateMouseCoordinates(evt);
        if (!selectionTool) {
            return;
        }
        if (simulation.getSimulator() == null) {
            return;
        }
        if (evt.getButton() == 0) {
            if (isMousePressed()) {
                drawSelectedArea(evt);
                selectedNodes.clear();

                for (Node node : simulation.getSimulator().getNodes()) {
                    GraphicNode circle = node.getGraphicNode();
                    boolean isSelected = selectedArea.contains(new Point(circle.getX(), circle.getY()));
                    circle.select(isSelected);
                    if (isSelected) {
                        selectedNodes.add(circle);
                    }
                }
            }
            update();
        }

    }//GEN-LAST:event_formMouseDragged

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        updateMouseCoordinates(evt);
        
        if (simulation.getSimulator() != null) {
        }
        update();
    }

    private GraphicNode selectedCircle(MouseEvent evt) {
        if (simulation.getSimulator() == null) {
            return null;
        }
        GraphicNode circle = null;
        GraphicNode circleSelected = null;
        for (Node node : simulation.getSimulator().getNodes()) {
            circle = node.getGraphicNode();
            circle.select(false);
            if (circle.contains(mouseX, mouseY)) {
                circle.select(true);
                circleSelected=circle;
            }
        }
        
        return circleSelected;
    }

    @Override
    public void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        paintNetwork(grphcs);
        if (selectionTool) {
            paintSelectedArea(grphcs);
        }
        if (paintMouseCoordinates) {
            paintMouseCoordinates(grphcs);
        }
    }//GEN-LAST:event_formMouseMoved
    private void paintImage(Graphics g) {
        if (backImage != null) {
            g.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void drawSelectedArea(java.awt.event.MouseEvent evt) {
        if (!selectionTool) {
            return;
        }
        selectedArea = new GeneralPath();
        selectedArea.moveTo(pressedPoint.x(), pressedPoint.y());
        selectedArea.lineTo(mouseX, pressedPoint.y());
        selectedArea.lineTo(mouseX, mouseY);
        selectedArea.lineTo(pressedPoint.x(), mouseY);
        selectedArea.lineTo(pressedPoint.x(), pressedPoint.y());
    }

    private void selectCircle(java.awt.event.MouseEvent evt) {
        if (!selectionTool) {
            return;
        }
        if (simulation.getSimulator() == null) {
            return;
        }
        for (Node node : simulation.getSimulator().getNodes()) {
            GraphicNode circle = node.getGraphicNode();
            circle.select(circle.contains(mouseX, mouseY));
        }

    }
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        updateMouseCoordinates(evt);
        selectCircle(evt);
        repaint();
    }//GEN-LAST:event_formMouseClicked

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        paintMouseCoordinates = true;
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        paintMouseCoordinates = false;
    }//GEN-LAST:event_formMouseExited

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        updateMouseCoordinates(evt);
        if (selectionTool) {
            if (insideSelectionArea(evt) && evt.getButton() == MouseEvent.BUTTON3) {
                System.out.println("POP UP");
            } else {

                clearSelection();
                pressedPoint = new GraphicPoint(mouseX, mouseY);
            }
        } else {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                currentSelectedNode=null;
                currentSelectedNode = selectedCircle(evt);
                if (currentSelectedNode != null) {
                    fireEnterCircleInfoEvent(new NodeInfoEvent(currentSelectedNode, mouseX, mouseY));
                } else {
                    fireExitCircleInfoEvent(new NodeInfoEvent(new GraphicNode(), mouseX, mouseY));
                }
            } else if (evt.getButton() == MouseEvent.BUTTON3) {
                if (currentSelectedNode != null) {
                    SensorNode s = (SensorNode) currentSelectedNode.getPhysicalNode();
                    selNodeVerID.setSelected(s.isShowID());
                    selNodeVerVizinhos.setSelected(s.isPaintNeighborhood());
                    currentSelectedNodePopupMenu.show(this, mouseX, mouseY);
                }

            }

        }

    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        updateMouseCoordinates(evt);
        pressedPoint = null;
        //selectedArea = null;
    }//GEN-LAST:event_formMouseReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        simulationArea.setHeigth(getSize().height);
        simulationArea.setWidth(getSize().width);
        update();
    }//GEN-LAST:event_formComponentResized

    private void selNodeVerVizinhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeVerVizinhosActionPerformed
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
            ((SensorNode) currentSelectedNode.getPhysicalNode()).setPaintNeighborhood(m.isSelected());
            update();
        }
    }//GEN-LAST:event_selNodeVerVizinhosActionPerformed

    private void selNodeVerIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeVerIDActionPerformed
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
            ((SensorNode) currentSelectedNode.getPhysicalNode()).setShowID(m.isSelected());
            update();
        }
    }//GEN-LAST:event_selNodeVerIDActionPerformed

    protected boolean isMousePressed() {
        return pressedPoint != null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu currentSelectedNodePopupMenu;
    private javax.swing.JCheckBoxMenuItem selNodeVerID;
    private javax.swing.JCheckBoxMenuItem selNodeVerVizinhos;
    // End of variables declaration//GEN-END:variables

    private void paintSelectedArea(Graphics grphcs) {
        if (!selectionTool) {
            return;
        }

        if (selectedArea == null) {
            return;
        }
        Graphics2D g = (Graphics2D) grphcs;
        Stroke o = g.getStroke();
        Stroke Pen1 = new BasicStroke(1.0F, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 1.0f, new float[]{3.0f, 3.0f}, 0.0f);
        g.setStroke(Pen1);
        g.draw(selectedArea);
        g.setStroke(o);
    }

    public int x2ScreenX(double x) {
        //  return 40+(int)((dim.width-80)  * x / simulationArea.getWidth());
        return OFFSET_NETWORK + (int) ((simulationArea.getWidth() - (OFFSET_NETWORK * 2)) * x / simulationArea.getWidth());
    }

    /* (non-Javadoc)
     * @see net.tinyos.prowler.IDisplay#y2ScreenY(double)
     */
    /* (non-Javadoc)
     * @see net.tinyos.prowler.extension.ISimulationDisplay#y2ScreenY(double)
     */
    public int y2ScreenY(double y) {
        // return 40+(int)((dim.height-80) * y / simulationArea.getHeigth());
        return OFFSET_NETWORK + (int) ((simulationArea.getHeigth() - (OFFSET_NETWORK * 2)) * y / simulationArea.getHeigth());
    }

    public void update() {
        JComponent parent = (JComponent) getParent();
        if (parent != null) {
            ((JComponent) getParent()).revalidate();
            ((JComponent) getParent()).repaint();
        } else {
            revalidate();
            repaint();
        }
    }

    public Simulator getSimulator() {
        return simulation.getSimulator();
    }

    @Override
    public Graphics getGraphics() {
        return currentGraphics;
    }

    public void createSimulation(int size) {
        simulation = new BasicSimulation();
        simulation.setSimulator(new DefaultSimulator());
        simulation.setRadioModel(new GaussianRadioModel());
        simulation.setNetwork(new DefaultNetwork());
        // associar a configuracao à simulação
        topologyManager = new RandomTopologyManager();
        topologyManager.setRandom(simulation.getSimulator().random);

        // assignar àrea de simulação à rede
        simulation.getNetwork().setSimulationArea(simulationArea);

        simulation.setSimpleNodeFactory(new DefaultNodeFactory(simulation.getSimulator(), BroadcastNode.class));
        simulation.setSinkNodeFactory(new DefaultNodeFactory(simulation.getSimulator(), BroadcastNode.class));

        simulation.setDisplay(this);
        simulation.setup();

        // adicionar um nó sink e dois nós simples
        //SinkNode sinkNode = null;
        sinkNode = null;
        app = new BroadcastApplication();
        List<Node> listOfSimpleNodes = null;
        try {

            sinkNode = (SimpleNode) simulation.getSinkNodeFactory().createNode((short) 1);
            sinkNode.setRoutingLayer(new BroadcastRoutingLayer());
            sinkNode.addApplication(app);
            sinkNode.setPaintNeighborhood(true);
            listOfSimpleNodes = simulation.getSimpleNodeFactory().createNodes(2, size - 1);
            simulation.getNetwork().addNode(sinkNode);
            for (Node simpleNode1 : listOfSimpleNodes) {
                simpleNode1.setRoutingLayer(new BroadcastRoutingLayer());
                simpleNode1.addApplication(new BroadcastApplication());
                simulation.getNetwork().addNode((SensorNode) simpleNode1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        simulation.getNetwork().applyTopology(topologyManager);
        simulation.getSimulator().init();
        update();
    }

    public void viewVizinhos(boolean selected) {
        for (Node node : simulation.getSimulator().getNodes()) {
            ((SensorNode) node).setPaintNeighborhood(selected);
        }
        update();
    }

    void selectionToolSelected(boolean selected) {
        selectionTool = selected;
        if (selectionTool == false) {
            clearSelection();
        }
    }

    private boolean insideSelectionArea(java.awt.event.MouseEvent evt) {
        if (selectedArea != null) {
            if (selectedArea.contains(new Point(mouseX, mouseY))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    void saveSimulation() {
        SimulationConfiguration configuration = new SimulationConfiguration();
        configuration.save(simulation);
    }

//    public void setSimulator(Simulator s) {
//        simulation.setSimulator((DefaultSimulator) s);
//    }
    public void RunSimulation() {

        sinkNode.sendMessageFromApplication("1", app);
        simulation.start();
    }

    private void clearSelection() {
        selectedArea = null;
        for (GraphicNode graphicNode : selectedNodes) {
            graphicNode.select(false);
        }
        update();
    }

    protected void loadImage() throws Exception {
        //  GoogleMaps.setApiKey("ABQIAAAAXg0F4Zi2pcwHeCjCvk7LRhSoSGvlWgPKK04fS0Rib--DYJNIihQASC7FQc_5lQHTrrgdlZoWfL-eZg");
        //   double[] lanLng = GoogleMaps.geocodeAddress("Academia da Força Aérea, Portugal");
        // backImage = ImageViewer.toBufferedImage(GoogleMaps.retrieveStaticImage(1024, 600, 38.835780, -9.334334, 19, "png32"));
    }

    public ArrayList<GraphicNode> getSelectedNodes() {
        return selectedNodes;
    }

    void selectIndividualNodeSelect(boolean selected) {
        individualNodeSelectionTool = selected;
    }

    private void paintMouseCoordinates(Graphics grphcs) {
        Color c = grphcs.getColor();
        grphcs.setColor(Color.BLACK);
        grphcs.drawString(mouseX + "," + mouseY, mouseX, mouseY);
        grphcs.setColor(c);
        update();
    }
}
