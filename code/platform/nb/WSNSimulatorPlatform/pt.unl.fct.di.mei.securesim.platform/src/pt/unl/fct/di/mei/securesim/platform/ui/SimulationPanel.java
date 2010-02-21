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
import javax.swing.JComponent;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

import pt.unl.fct.di.mei.securesim.engine.*;
import pt.unl.fct.di.mei.securesim.engine.nodes.Node;
import pt.unl.fct.di.mei.securesim.engine.radio.GaussianRadioModel;
import pt.unl.fct.di.mei.securesim.network.SimulationArea;
import pt.unl.fct.di.mei.securesim.network.basic.BasicNetwork;
import pt.unl.fct.di.mei.securesim.network.nodes.*;
import pt.unl.fct.di.mei.securesim.network.nodes.basic.BasicNodeFactory;
import pt.unl.fct.di.mei.securesim.simulation.BasicSimulator;
import pt.unl.fct.di.mei.securesim.simulation.basic.BasicSimulation;
import pt.unl.fct.di.mei.securesim.topology.RandomTopologyManager;
import pt.unl.fct.di.mei.securesim.ui.*;

/**
 *
 * @author posilva
 */
public class SimulationPanel extends javax.swing.JPanel implements ISimulationDisplay {

    public static final int OFFSET_NETWORK = 10;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    //   ArrayList<GraphicNode> graphicNodes = new ArrayList<GraphicNode>();
    ArrayList<GraphicNode> selectedNodes = new ArrayList<GraphicNode>();
    BufferedImage backImage = null;
    private BasicSimulator simulator;
    private Graphics currentGraphics;
    private SimulationArea simulationArea;
    private Thread simulationThread;
    private SimpleNode sinkNode;
    private BroadcastApplication app;
    private BasicSimulation simulation;
    private GaussianRadioModel radioModel;
    private RandomTopologyManager topologyManager;
    private BasicNetwork network;
    private BasicNodeFactory simpleNodeFactory;
    private BasicNodeFactory sinkNodeFactory;
    private Dimension dim;
    private boolean selectionTool = false;

    public void RunSimulation() {

        sinkNode.sendMessageFromApplication("1", app);
        simulationThread = new Thread(new Runnable() {

            public void run() {
                simulator.runWithDisplay();
            }
        });
        simulationThread.start();
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
    Random rg = new Random();
    private GraphicPoint pressedPoint = null;
    private GeneralPath selectedArea;
    private int maxH = 300;
    private int maxW = 300;

    /** Creates new form SimulationPanel */
    public SimulationPanel() {
        initComponents();
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

    void paintNetwork(Graphics g) {
        if (simulator != null) {
            for (Node node : simulation.getNetwork().getNodeDB().nodes()) {
                node.displayOn(this);
            }


        }
    }

    public void re_display(Graphics g) {
        dim = getSize();
        Image offscreen = createImage(dim.width, dim.height);
        Graphics b = offscreen.getGraphics();

        currentGraphics = b;

        // paint background
        b.setColor(Color.white);
        b.fillRect(0, 0, dim.width, dim.height);

        // draw nodes
        if (simulator != null) {
            simulator.display(this);
        }
        g.drawImage(offscreen, 0, 0, this);

    }

    public void initCircles(int mw, int mh) {
        createSimulation();

//        while (graphicNodes.size() < mw) {
//            final int w = (int) (mw * rg.nextDouble());
//            final int h = (int) (mh * rg.nextDouble());
//            GraphicNode c = new GraphicNode(w, h);
//            c.radius = 3;
//            if (!((c.x + c.radius) > mw || (c.x - c.radius) < 0 || (c.y + c.radius) > mh || (c.y - c.radius) < 0)) {
//                maxH = (h > maxH ? h : maxH);
//                maxW = (w > maxW ? w : maxW);
//                graphicNodes.add(c);
//            }
        setPreferredSize(new Dimension(maxW + 40, maxH + 40));
//        }
        update();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(220, 215, 215));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
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
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if (!selectionTool) {
            return;
        }
        if (simulator == null) {
            return;
        }
        System.out.println("Mouse Dragged button "+evt.getButton());
        if (evt.getButton() == 0) {
            System.out.println("Mouse Dragged");
            if (isMousePressed()) {
                drawSelectedArea(evt);
                selectedNodes.clear();

//            for (GraphicNode circle : graphicNodes) {
//                boolean isSelected = selectedArea.contains(new Point(circle.x, circle.y));
//                circle.select(isSelected);
//
//                if (isSelected) {
//                    selectedNodes.add(circle);
//                }
//
//            }

                for (Node node : simulator.getNodes()) {
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
        if (simulator == null) {
            return;
        }
        if (selectedArea != null) {
            GraphicNode c = selectedCircle(evt);
            if (c != null) {
                fireEnterCircleInfoEvent(new NodeInfoEvent(c, evt.getX(), evt.getY()));
            } else {
                fireExitCircleInfoEvent(new NodeInfoEvent(new GraphicNode(), evt.getX(), evt.getY()));
            }
        }
        update();
    }

    private GraphicNode selectedCircle(MouseEvent evt) {
        if (!selectionTool) {
            return null;
        }
        if (simulator == null) {
            return null;
        }
//        for (GraphicNode circle : graphicNodes) {
//            if (circle.contains(evt.getX(), evt.getY())) {
//                return circle;
//            }
//        }
        for (Node node : simulator.getNodes()) {
            GraphicNode circle = node.getGraphicNode();
            if (circle.contains(evt.getX(), evt.getY())) {
                return circle;
            }
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        re_display(grphcs);

//System.out.println("Painting Called");
////
////        paintImage(grphcs);
////        for (GraphicNode circle : graphicNodes) {
////            circle.paint(grphcs);
////        }
        paintSelectedArea(grphcs);

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
        selectedArea.lineTo(evt.getX(), pressedPoint.y());
        selectedArea.lineTo(evt.getX(), evt.getY());
        selectedArea.lineTo(pressedPoint.x(), evt.getY());
        selectedArea.lineTo(pressedPoint.x(), pressedPoint.y());
    }

    private void selectCircle(java.awt.event.MouseEvent evt) {
        if (!selectionTool) {
            return;
        }
//        for (GraphicNode circle : graphicNodes) {
//            circle.select(circle.contains(evt.getX(), evt.getY()));
//        }
        if (simulator == null) {
            return;
        }
        for (Node node : simulator.getNodes()) {
            GraphicNode circle = node.getGraphicNode();
            circle.select(circle.contains(evt.getX(), evt.getY()));
        }

    }
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        selectCircle(evt);
        repaint();
    }//GEN-LAST:event_formMouseClicked

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
    }//GEN-LAST:event_formMouseExited

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (!selectionTool) {
            return;
        }
        System.out.println("BUTTON: " + evt.getButton());
        if (insideSelectionArea(evt) && evt.getButton() == evt.BUTTON3) {
            System.out.println("POP UP");
        } else {

            clearSelection();
            pressedPoint = new GraphicPoint(evt.getX(), evt.getY());
        }

    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        pressedPoint = null;
        //selectedArea = null;
    }//GEN-LAST:event_formMouseReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        simulationArea.setHeigth(getSize().height);
        simulationArea.setWidth(getSize().width);
        update();
    }//GEN-LAST:event_formComponentResized

    protected boolean isMousePressed() {
        return pressedPoint != null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
        if(parent!=null){
            ((JComponent) getParent()).revalidate();
            ((JComponent) getParent()).repaint();
        }else{
            revalidate();
            repaint();
        }
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public void setSimulator(Simulator s) {
        this.simulator = (BasicSimulator) s;
    }

    @Override
    public Graphics getGraphics() {
        return currentGraphics;
    }

    public void createSimulation() {
        //create simulator
        simulator = new BasicSimulator();
        // criar o modelo de rádio
        radioModel = new GaussianRadioModel();
        // create simulation object
        simulation = new BasicSimulation();
        // criar uma configuração para a simulação
        //	SimulationConfiguration simulationConfiguration = new SimulationConfiguration();
        // associa modelo de rádio ao simulador
        simulator.setRadioModel(radioModel);
        // associa o simulador à simulação
        simulation.setSimulator(simulator);
        // associar a configuracao à simulação
        topologyManager = new RandomTopologyManager();
        topologyManager.setRandom(simulator.random);
        // create a network
        network = new BasicNetwork();
        // definição da àrea de simulação

        // assignar àrea de simulação à rede
        network.setSimulationArea(simulationArea);
        // cria factories de nós sink e simples
        simpleNodeFactory = new BasicNodeFactory(simulator, BroadcastNode.class);
        sinkNodeFactory = new BasicNodeFactory(simulator, BroadcastNode.class);

        this.simulator.setNetwork(network);

        // adicionar um nó sink e dois nós simples
        //SinkNode sinkNode = null;
        sinkNode = null;
        app = new BroadcastApplication();
        List<Node> listOfSimpleNodes = null;
        try {

            sinkNode = (SimpleNode) sinkNodeFactory.createNode((short) 1);
            sinkNode.setRoutingLayer(new BroadcastRoutingLayer());
            sinkNode.addApplication(app);
            sinkNode.setPaintNeighborhood(true);
            listOfSimpleNodes = simpleNodeFactory.createNodes(2, 999);
            network.addNode(sinkNode);
            for (Node simpleNode1 : listOfSimpleNodes) {
                simpleNode1.setRoutingLayer(new BroadcastRoutingLayer());
                simpleNode1.addApplication(new BroadcastApplication());
                network.addNode((SensorNode) simpleNode1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        network.applyTopology(topologyManager);
        simulator.init();

        simulator.setDisplay(this);
        update();
    }

    public void viewVizinhos(boolean selected) {
        for (Node node : simulator.getNodes()) {
            ((SensorNode) node).setPaintNeighborhood(selected);
        }
        update();
    }

    void selectionToolSelected(boolean selected) {
        selectionTool = selected;
        System.out.println("selection tool selected " + selectionTool);
        if (selectionTool == false) {
            clearSelection();
        }
    }

    public Task deployTask() {
        return new Task(Application.getInstance()) {

            @Override
            protected Object doInBackground() throws Exception {
                createSimulation();
                return null;
            }
        };
    }

    private boolean insideSelectionArea(java.awt.event.MouseEvent evt) {
        if (selectedArea != null) {
            if (selectedArea.contains(new Point(evt.getX(), evt.getY()))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
