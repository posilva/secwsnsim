package org.wisenet.platform.gui;

import org.wisenet.platform.gui.frames.ChartFrame;
import java.io.IOException;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.PipedOutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.event.EventListenerList;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.wisenet.platform.common.ui.PlatformDialog;
import org.wisenet.platform.core.instruments.energy.EnergyWatcherThread;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.core.charts.ui.ChartPanel;
import org.wisenet.platform.gui.listeners.DeployEvent;
import org.wisenet.platform.gui.listeners.SimulationPanelEventListener;
import org.wisenet.platform.gui.panels.RoutingAttacksPanel;
import org.wisenet.platform.utils.GUI_Utils;
import org.wisenet.platform.utils.PlatformUtils;
import org.wisenet.simulator.components.simulation.AbstractSimulation;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.simulation.SimulationFactory;
import org.wisenet.simulator.components.topology.GridTopologyManager;
import org.wisenet.simulator.components.topology.GridTopologyParameters;
import org.wisenet.simulator.components.topology.RandomTopologyManager;
import org.wisenet.simulator.components.topology.RandomTopologyParameters;
import org.wisenet.simulator.core.energy.Batery;
import org.wisenet.simulator.core.energy.listeners.EnergyEvent;
import org.wisenet.simulator.core.energy.listeners.EnergyListener;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.factories.AbstractNodeFactory;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.SensorNode;
import org.wisenet.simulator.core.ui.ISimulationDisplay;
import org.wisenet.simulator.gui.GraphicNode;
import org.wisenet.simulator.utilities.RandomList;
import org.wisenet.simulator.utilities.console.SimulationSettings;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class SimulationPanel extends javax.swing.JPanel implements ISimulationDisplay {

    protected EventListenerList simulationPanelListeners = new EventListenerList();
    public static final int OFFSET_NETWORK = 0;
    public static final int SHIFT_KEYCODE = 16;
    /**
     * MAIN VARIABLES
     */
    private AbstractSimulation simulation;
    private SimulationFactory simulationFactory;
    /**
     * GRAPHIC HANDLING VARIABLES
     */
    Vector<GraphicNode> selectedNodes = new Vector<GraphicNode>();
    private Graphics currentGraphics;
//    private GraphicPoint pressedPoint = null;
    private GeneralPath selectedArea;
    private int mouseX;
    private int mouseY;
    private GraphicNode currentSelectedNode;
    private String[] nodeInfo = null;
    /**
     * STATE VARIABLES
     */
    private boolean selectionTool = false;
    private boolean paintMouseCoordinates = false;
    private boolean simulationRunning = false;
    private boolean resizeSimulatedArea = true;
    private boolean visibleMouseCoordinates = false;
    private boolean deployNodeToolSelected = false;
    private boolean paintNodesInfo = false;
    private boolean singleNodeSelectionTool = false;
    private boolean controlKeyPressed;
    private boolean shiftKeyPressed;
    /**
     * UTILS VARIABLES
     */
    BufferedImage backImage = null;
    private boolean mouseDrag;
    private boolean mousePressed;
    private Dimension dim = new Dimension(300, 300);
    private Color selectionColor = Color.black;
    private int pressedPoint_x;
    private int pressedPoint_y;
    private boolean stretch;
    private double currentSelectedArea_w = 0;
    private double currentSelectedArea_h = 0;
    private boolean painting = false;

    /**
     * CONSTRUCTORS
     */
    public SimulationPanel() {
        initComponents();
    }

    public void reset() {
    }

    public void initSimulation(SimulationSettings settings) {
        this.simulation = new Simulation();
        simulation.setDisplay(this);
        this.simulation.create(settings);
        PlatformManager.getInstance().setActiveSimulation(simulation);
    }

    public void createSimulation(SimulationFactory sf) {
        try {

            this.simulation = sf.getNewInstanceFromClasses();
            simulation.setDisplay(this);
            Simulator.randomGenerator.reset();
            Node.resetCounter();
            simulation.setDisplay(this);
            simulation.preInit();
            PlatformManager.getInstance().setActiveSimulation(simulation);
        } catch (Exception ex) {
            GUI_Utils.showException(ex);
        }
    }

    private void autoResizeScrollSimulationPanel(int w, int h) {
        if (w > getSize().getWidth() || h > getSize().getHeight()) {
            int mx = (int) Math.max(getSize().getWidth(), w);
            int my = (int) Math.max(getSize().getHeight(), h);
            setPreferredSize(new Dimension(mx + 50, my + 50));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        currentSelectedNodePopupMenu = new javax.swing.JPopupMenu();
        selNodeRunEvent = new javax.swing.JMenuItem();
        selNodeShowNeighbors = new javax.swing.JCheckBoxMenuItem();
        selNodeSelectNeighbors = new javax.swing.JCheckBoxMenuItem();
        selNodeShowID = new javax.swing.JCheckBoxMenuItem();
        selNodeShowOthers = new javax.swing.JCheckBoxMenuItem();
        selNodeMark = new javax.swing.JCheckBoxMenuItem();
        selNodeOnOff = new javax.swing.JCheckBoxMenuItem();
        selNodeSink = new javax.swing.JCheckBoxMenuItem();
        selNodeMonitEnergy = new javax.swing.JMenuItem();
        selNodeSetZ = new javax.swing.JMenuItem();
        selNodeSetEnvironmentAttenuation = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        selNodeRoutingAttacks = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuTest = new javax.swing.JMenu();
        addRemoveSource = new javax.swing.JMenuItem();
        addRemoveReceiver = new javax.swing.JMenuItem();
        selNodeUnderAttack = new javax.swing.JCheckBoxMenuItem();
        selectionToolPopupMenu = new javax.swing.JPopupMenu();
        selNodesRunEvent = new javax.swing.JMenuItem();
        selNodesShowNeighbors = new javax.swing.JCheckBoxMenuItem();
        selNodesShowID = new javax.swing.JCheckBoxMenuItem();
        selNodesShowNodesThatKnownme = new javax.swing.JCheckBoxMenuItem();
        selNodesOnOff = new javax.swing.JCheckBoxMenuItem();
        selNodesMonitEnergy = new javax.swing.JMenuItem();
        selNodesSetZ = new javax.swing.JMenuItem();
        selNodesSetEnvironmentAttenuation = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        selNodesRemove = new javax.swing.JMenuItem();
        mnuTest1 = new javax.swing.JMenu();
        addRemoveSourceNodes = new javax.swing.JMenuItem();
        addRemoveReceiverNodes = new javax.swing.JMenuItem();
        selNodesUnderAttack = new javax.swing.JCheckBoxMenuItem();
        deployNodesPopupMenu = new javax.swing.JPopupMenu();
        depNodesDeploy = new javax.swing.JMenu();
        depNodesRandomTopology = new javax.swing.JMenuItem();
        depNodesGridTopology = new javax.swing.JMenuItem();
        deployNodePopupMenu = new javax.swing.JPopupMenu();
        depNodeDeployOneNode = new javax.swing.JMenuItem();
        depNodeSetArea = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();

        currentSelectedNodePopupMenu.setLabel("");

        selNodeRunEvent.setText("Run application");
        selNodeRunEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeRunEventActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeRunEvent);

        selNodeShowNeighbors.setSelected(true);
        selNodeShowNeighbors.setText("Show Neighbors");
        selNodeShowNeighbors.setToolTipText("");
        selNodeShowNeighbors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeShowNeighborsActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeShowNeighbors);

        selNodeSelectNeighbors.setSelected(true);
        selNodeSelectNeighbors.setText("Select Neighbors");
        selNodeSelectNeighbors.setToolTipText("");
        selNodeSelectNeighbors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeSelectNeighborsActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeSelectNeighbors);

        selNodeShowID.setSelected(true);
        selNodeShowID.setText("Show Node ID"); // NOI18N
        selNodeShowID.setToolTipText("");
        selNodeShowID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeShowIDActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeShowID);

        selNodeShowOthers.setSelected(true);
        selNodeShowOthers.setText("Show Others that Known Me");
        selNodeShowOthers.setToolTipText("");
        selNodeShowOthers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeShowOthersActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeShowOthers);

        selNodeMark.setSelected(true);
        selNodeMark.setText("Mark"); // NOI18N
        selNodeMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeMarkActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeMark);

        selNodeOnOff.setSelected(true);
        selNodeOnOff.setText("On/Off");
        selNodeOnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeOnOffActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeOnOff);

        selNodeSink.setSelected(true);
        selNodeSink.setText("Sink Node"); // NOI18N
        selNodeSink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeSinkActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeSink);

        selNodeMonitEnergy.setText("Monitor Energy");
        selNodeMonitEnergy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeMonitEnergyActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeMonitEnergy);

        selNodeSetZ.setText("Set Z");
        selNodeSetZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeSetZActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeSetZ);

        selNodeSetEnvironmentAttenuation.setText("Set environment attenuation");
        selNodeSetEnvironmentAttenuation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeSetEnvironmentAttenuationActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeSetEnvironmentAttenuation);
        currentSelectedNodePopupMenu.add(jSeparator3);

        selNodeRoutingAttacks.setText("Routing Attacks");
        selNodeRoutingAttacks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeRoutingAttacksActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeRoutingAttacks);
        currentSelectedNodePopupMenu.add(jSeparator5);

        mnuTest.setText("Testing");

        addRemoveSource.setText("Add/Remove Source Node");
        addRemoveSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRemoveSourceActionPerformed(evt);
            }
        });
        mnuTest.add(addRemoveSource);

        addRemoveReceiver.setText("Add/Remove Receiver Node");
        addRemoveReceiver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRemoveReceiverActionPerformed(evt);
            }
        });
        mnuTest.add(addRemoveReceiver);

        selNodeUnderAttack.setSelected(true);
        selNodeUnderAttack.setText("Under Routing Attack");
        selNodeUnderAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeUnderAttackActionPerformed(evt);
            }
        });
        mnuTest.add(selNodeUnderAttack);

        currentSelectedNodePopupMenu.add(mnuTest);

        selNodesRunEvent.setText("Run application");
        selNodesRunEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesRunEventActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesRunEvent);

        selNodesShowNeighbors.setSelected(true);
        selNodesShowNeighbors.setText("Show Neighbors");
        selNodesShowNeighbors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesShowNeighborsActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesShowNeighbors);

        selNodesShowID.setSelected(true);
        selNodesShowID.setText("Show Node ID");
        selNodesShowID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesShowIDActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesShowID);

        selNodesShowNodesThatKnownme.setSelected(true);
        selNodesShowNodesThatKnownme.setText("Show Other Nodes That Known Me");
        selNodesShowNodesThatKnownme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesShowNodesThatKnownmeActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesShowNodesThatKnownme);

        selNodesOnOff.setSelected(true);
        selNodesOnOff.setText("On/Off");
        selNodesOnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesOnOffActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesOnOff);

        selNodesMonitEnergy.setText("Monitor Energy");
        selNodesMonitEnergy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesMonitEnergyActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesMonitEnergy);

        selNodesSetZ.setText("Set Z ");
        selNodesSetZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesSetZActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesSetZ);

        selNodesSetEnvironmentAttenuation.setText("Set environment attenuation");
        selNodesSetEnvironmentAttenuation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesSetEnvironmentAttenuationActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesSetEnvironmentAttenuation);
        selectionToolPopupMenu.add(jSeparator2);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(SimulationPanel.class, this);
        selNodesRemove.setAction(actionMap.get("RemoveNodesSelected")); // NOI18N
        selNodesRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesRemoveActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesRemove);

        mnuTest1.setText("Testing");

        addRemoveSourceNodes.setText("Add/Remove Source Nodes");
        mnuTest1.add(addRemoveSourceNodes);

        addRemoveReceiverNodes.setText("Add/Remove Receiver Nodes");
        mnuTest1.add(addRemoveReceiverNodes);

        selNodesUnderAttack.setText("Under Routing Attack");
        selNodesUnderAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesUnderAttackActionPerformed(evt);
            }
        });
        mnuTest1.add(selNodesUnderAttack);

        selectionToolPopupMenu.add(mnuTest1);

        depNodesDeploy.setText("Deploy sensors...");

        depNodesRandomTopology.setText("Random topology");
        depNodesRandomTopology.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depNodesRandomTopologyActionPerformed(evt);
            }
        });
        depNodesDeploy.add(depNodesRandomTopology);

        depNodesGridTopology.setText("Grid topology");
        depNodesGridTopology.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depNodesGridTopologyActionPerformed(evt);
            }
        });
        depNodesDeploy.add(depNodesGridTopology);

        deployNodesPopupMenu.add(depNodesDeploy);

        depNodeDeployOneNode.setText("Deploy sensor here..."); // NOI18N
        depNodeDeployOneNode.setToolTipText("Deploy sensor here..."); // NOI18N
        depNodeDeployOneNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depNodeDeployOneNodeActionPerformed(evt);
            }
        });
        deployNodePopupMenu.add(depNodeDeployOneNode);

        depNodeSetArea.setText("Set deploy area from here");
        depNodeSetArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depNodeSetAreaActionPerformed(evt);
            }
        });
        deployNodePopupMenu.add(depNodeSetArea);

        setBackground(new java.awt.Color(254, 254, 254));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        updateMouseCoordinates(evt);
        if (deployNodeToolSelected) {
            if (evt.getButton() == 0) {
                if (isMousePressed()) {
                    drawSelectedArea(evt);
                    updateScrollArea();
                }
                updateLocal();
            }
        } else if (selectionTool) {
            if (simulation.getSimulator() == null) {
                return;
            }
            if (evt.getButton() == 0) {
                if (isMousePressed()) {
                    drawSelectedArea(evt);
                    updateScrollArea();
                    selectedNodes.clear();

                    for (Node node : simulation.getSimulator().getNodes()) {
                        GraphicNode circle = node.getGraphicNode();
                        boolean isSelected = selectedArea.contains(new Point(circle.getX(), circle.getY()));
                        circle.select(isSelected);
                        if (isSelected) {
                            selectedNodes.add(circle);
                        }
                    }
                    PlatformManager.getInstance().getPlatformView().updateSelectedNodes(selectedNodes.size() + "");

                }
                updateLocal();

            }
        } else if (singleNodeSelectionTool) {
            if (!mousePressed) {
                return;
            }
            mouseDrag = true;
            if (currentSelectedNode != null) {
                currentSelectedNode.moveTo(mouseX, mouseY);
                updateLocal();
            }
        }

    }//GEN-LAST:event_formMouseDragged
    /**
     * 
     * @param evt
     */
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        updateMouseCoordinates(evt);
        if (!selectionTool) {
            if (simulation.getSimulator() != null) {
                GraphicNode c = selectedSingleNode(evt);
                if (c != null) {
                    nodeInfo = c.getPhysicalNode().getInfo();
                } else {
                    nodeInfo = null;
                }
            }
        }
        updateLocal();
    }

    private Double inputNumberValue(String message, String defValue) throws NumberFormatException, HeadlessException {
        boolean ok = false;
        double nNodes = 0;
        try {

            while (!ok) {
                String sNodes = JOptionPane.showInputDialog(message, defValue);
                if (sNodes != null) {
                    if (NumberUtils.isNumber(sNodes)) {
                        nNodes = Integer.parseInt(sNodes);
                        ok = true;
                        return nNodes;
                    }
                } else {
                    return null;
                }
                if (!ok) {
                    JOptionPane.showMessageDialog(SimulationPanel.this, "Invalid value!");
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Selects a node
     * @param evt
     * @return
     */
    private GraphicNode selectedSingleNode(MouseEvent evt) {
        if (simulation.getSimulator() == null) {
            return null;
        }
        if (deployNodeToolSelected) {
            return null;
        }
        GraphicNode circle = null;
        GraphicNode circleSelected = null;
        for (Node node : simulation.getSimulator().getNodes()) {
            circle = node.getGraphicNode();
            if (circle.contains(mouseX, mouseY)) {
                circleSelected = circle;
            }
        }
        return circleSelected;
    }

    /**
     * 
     * @param evt
     * @return
     */
    private GraphicNode selectedCircle(MouseEvent evt) {
        if (simulation.getSimulator() == null) {
            return null;
        }
        if (deployNodeToolSelected) {
            return null;
        }



        GraphicNode circle = null;
        GraphicNode circleSelected = null;
        for (Node node : simulation.getSimulator().getNodes()) {
            circle = node.getGraphicNode();
            circle.select(false);
            if (circle.contains(mouseX, mouseY)) {
                circle.select(true);
                circleSelected = circle;
                return circleSelected;
            }
        }
        return circleSelected;
    }

    @Override
    public void paintComponent(Graphics grphcs) {
        try {


            painting = true;
            super.paintComponent(grphcs);
            //        dim = getSize();
            //        Image offscreen = createImage(dim.width, dim.height);
            //        Graphics b = offscreen.getGraphics();
            //
            //        currentGraphics = b;
            // paint background
            //        b.setColor(Color.white);
            //        b.fillRect(0, 0, dim.width, dim.height);
            Graphics b = grphcs;
            mainPaintLoop(b);
        } catch (Exception e) {
        } finally {
            painting = false;
        }
//        grphcs.drawImage(offscreen, 0, 0, this);
    }//GEN-LAST:event_formMouseMoved

    /**
     *
     * @param evt
     */
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

        updateMouseCoordinates(evt);

        if (selectionTool) {
            /**
             * Done nothing
             * 
             */
        } else if (singleNodeSelectionTool) {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                currentSelectedNode = null;
                currentSelectedNode = selectedSingleNode(evt);
                if (evt.getClickCount() > 1) { // double click handler
                    if (currentSelectedNode != null) {
//                        showNodeProperties();
                    }
                } else {
                    if (currentSelectedNode != null) {
                        currentSelectedNode.select(true);
                        selectedNodes.add(currentSelectedNode);
                    } else {
                        selectNone();
                    }

                }
                PlatformManager.getInstance().getPlatformView().updateSelectedNodes(selectedNodes.size() + "");

            } else if (evt.getButton() == MouseEvent.BUTTON3) {

                currentSelectedNode = null;
                currentSelectedNode = selectedCircle(evt);

                if (currentSelectedNode != null) {
                    SensorNode s = SensorNode.cast(currentSelectedNode.getPhysicalNode());
                    selNodeShowID.setSelected(s.isShowID());
                    selNodeShowNeighbors.setSelected(s.isPaintNeighborhoodDst());
                    selNodeOnOff.setSelected(s.isTurnedOn());
                    selNodeSink.setSelected(s.isSinkNode());
                    selNodeMark.setSelected(currentSelectedNode.isMarked());
                    currentSelectedNodePopupMenu.show(this, mouseX, mouseY);
                }

            }
        }
        updateLocal();
    }//GEN-LAST:event_formMouseClicked

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        visibleMouseCoordinates = true;
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        visibleMouseCoordinates = false;
    }//GEN-LAST:event_formMouseExited

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        updateMouseCoordinates(evt);
        if (selectionTool) {
            if ((insideSelectionArea(evt) && evt.getButton() == MouseEvent.BUTTON3) || (selectedNodes.size() > 0 && evt.getButton() == MouseEvent.BUTTON3)) {
                selectionToolPopupMenu.show(this, mouseX, mouseY);
            } else {
                clearSelection();
                pressedPoint_x = mouseX;
                pressedPoint_y = mouseY;

            }
        } else if (deployNodeToolSelected) {

            if (selectedArea == null && evt.getButton() == MouseEvent.BUTTON3) {
                deployNodePopupMenu.show(this, mouseX, mouseY);
            } else {
                if (insideSelectionArea(evt) && evt.getButton() == MouseEvent.BUTTON3) {
                    deployNodesPopupMenu.show(this, mouseX, mouseY);
                } else {
                    clearSelection();
                    pressedPoint_x = mouseX;
                    pressedPoint_y = mouseY;
                }
            }
        } else if (singleNodeSelectionTool) {
            mousePressed = true;
            if (selectedCircle(evt) == null) {
//                selectNone();
//                selectedNodes.clear();
            }

        }


    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        updateMouseCoordinates(evt);
        mouseDrag = false;
        pressedPoint_x = -1;
        pressedPoint_y = -1;

        mousePressed = false;
        //selectedArea = null;
        currentSelectedNode = null;
        mousePressed = false;
        updateLocal();
    }//GEN-LAST:event_formMouseReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        resizeUpdateSimulatedArea();
        updateLocal();
    }//GEN-LAST:event_formComponentResized

    private void selNodeShowNeighborsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeShowNeighborsActionPerformed
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
            ((SensorNode) currentSelectedNode.getPhysicalNode()).setPaintNeighborhood(m.isSelected());
            updateLocal();
        }
    }//GEN-LAST:event_selNodeShowNeighborsActionPerformed

    private void selNodeShowIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeShowIDActionPerformed
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
            ((SensorNode) currentSelectedNode.getPhysicalNode()).setShowID(m.isSelected());
            updateLocal();
        }
    }//GEN-LAST:event_selNodeShowIDActionPerformed

    private void selNodeOnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeOnOffActionPerformed
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
            if (m.isSelected()) {
                ((SensorNode) currentSelectedNode.getPhysicalNode()).powerOn();
            } else {
                ((SensorNode) currentSelectedNode.getPhysicalNode()).shutdown();
            }

            updateLocal();
        }
    }//GEN-LAST:event_selNodeOnOffActionPerformed

    private void selNodesShowNeighborsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesShowNeighborsActionPerformed
        if (selectionTool) {
            if (selectedNodes != null) {
                JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
                for (GraphicNode selNode : selectedNodes) {
                    ((SensorNode) selNode.getPhysicalNode()).setPaintNeighborhood(m.isSelected());
                }
                updateLocal();
            }
        }
    }//GEN-LAST:event_selNodesShowNeighborsActionPerformed

    private void selNodesShowIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesShowIDActionPerformed
        if (selectionTool) {
            if (selectedNodes != null) {
                JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
                for (GraphicNode selNode : selectedNodes) {
                    ((SensorNode) selNode.getPhysicalNode()).setShowID(m.isSelected());
                }
                updateLocal();
            }
        }
    }//GEN-LAST:event_selNodesShowIDActionPerformed

    private void selNodesOnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesOnOffActionPerformed
        if (selectionTool) {
            if (selectedNodes != null) {
                JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
                for (GraphicNode selNode : selectedNodes) {
                    if (m.isSelected()) {
                        ((SensorNode) selNode.getPhysicalNode()).powerOn();
                    } else {
                        ((SensorNode) selNode.getPhysicalNode()).shutdown();
                    }
                }

                updateLocal();
            }
        }

    }//GEN-LAST:event_selNodesOnOffActionPerformed

    private void selNodesShowNodesThatKnownmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesShowNodesThatKnownmeActionPerformed
        if (selectionTool) {
            if (selectedNodes != null) {
                JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
                for (GraphicNode selNode : selectedNodes) {
                    ((SensorNode) selNode.getPhysicalNode()).setPaintNeighborhoodOrg(m.isSelected());
                }
                updateLocal();
            }
        }
    }//GEN-LAST:event_selNodesShowNodesThatKnownmeActionPerformed

    private void selNodeShowOthersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeShowOthersActionPerformed
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
            ((SensorNode) currentSelectedNode.getPhysicalNode()).setPaintNeighborhoodOrg(m.isSelected());
            updateLocal();
        }
    }//GEN-LAST:event_selNodeShowOthersActionPerformed

    private void selNodeMonitEnergyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeMonitEnergyActionPerformed
//        EnergyController ec;
//        if (currentSelectedNode != null) {
//            JMenuItem m = (JMenuItem) evt.getSource();
//            Node n = currentSelectedNode.getPhysicalNode();
//            if (!energyControllersTable.contains(n)) {
//                ec = new EnergyController();
//                n.getBateryEnergy().addEnergyListener(ec);
//                energyControllersTable.put(n, ec);
//                ec.start();
//            }
//            if (!nodeenergyWatchers.contains(n)) {
//                displayNewChartFrame(n);
//
//            }
//


        createEnergyWatcher(new Object[]{currentSelectedNode});

//            EnergyHook eh = EnergyHook.hookToNode(n, 100, true);
//
        updateLocal();
//        }

    }//GEN-LAST:event_selNodeMonitEnergyActionPerformed

    private void selNodeMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeMarkActionPerformed
        if (currentSelectedNode != null) {
            JMenuItem m = (JMenuItem) evt.getSource();
            if (!currentSelectedNode.isMarked()) {
                currentSelectedNode.mark();
            } else {
                currentSelectedNode.unmark();
            }
            updateLocal();
        }

    }//GEN-LAST:event_selNodeMarkActionPerformed

    private void selNodesMonitEnergyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesMonitEnergyActionPerformed


        createEnergyWatcher(selectedNodes.toArray());

    }//GEN-LAST:event_selNodesMonitEnergyActionPerformed

    private void selNodeRunEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeRunEventActionPerformed

        if (currentSelectedNode != null) {
            JMenuItem m = (JMenuItem) evt.getSource();
            if (simulationRunning) {
                currentSelectedNode.getPhysicalNode().getApplication().run();
            } else {
                GUI_Utils.showMessage("Simulation is not running yet!", JOptionPane.WARNING_MESSAGE);

            }
        }
    }//GEN-LAST:event_selNodeRunEventActionPerformed

    private void selNodesRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesRemoveActionPerformed
    }//GEN-LAST:event_selNodesRemoveActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        shiftKeyPressed = (evt.getKeyCode() == SHIFT_KEYCODE);
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == SHIFT_KEYCODE) {
            shiftKeyPressed = false;
        }
    }//GEN-LAST:event_formKeyReleased
    /**
     * 
     * @param evt
     */
    private void selNodesRunEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesRunEventActionPerformed
        if (selectedNodes.size() > 0) {
            for (GraphicNode graphicNode : selectedNodes) {
                graphicNode.getPhysicalNode().getApplication().run();
            }
            updateLocal();
        }
    }//GEN-LAST:event_selNodesRunEventActionPerformed

    private void selNodeRoutingAttacksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeRoutingAttacksActionPerformed
        if (currentSelectedNode != null) {
            JMenuItem m = (JMenuItem) evt.getSource();
            showAttackProperties(currentSelectedNode.getPhysicalNode());
            updateLocal();
        }
    }//GEN-LAST:event_selNodeRoutingAttacksActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
//        GUI_Utils.showMessage("Gained Focus");
    }//GEN-LAST:event_formFocusGained

    private void selNodeUnderAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeUnderAttackActionPerformed
        if (currentSelectedNode != null) {
            if (currentSelectedNode.getPhysicalNode().getRoutingLayer().isStable()) {
                currentSelectedNode.getPhysicalNode().getRoutingLayer().setUnderAttack(selNodeUnderAttack.isSelected());
            }
            updateLocal();
        }
    }//GEN-LAST:event_selNodeUnderAttackActionPerformed

    private void selNodesUnderAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesUnderAttackActionPerformed

        setUnderAttackModeSelectedNodes(selNodesUnderAttack.isSelected());
    }//GEN-LAST:event_selNodesUnderAttackActionPerformed

    private void depNodeDeployOneNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depNodeDeployOneNodeActionPerformed
        PlaceNodeHereAction();
    }//GEN-LAST:event_depNodeDeployOneNodeActionPerformed

    private void depNodesRandomTopologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depNodesRandomTopologyActionPerformed
        deployNodesUsingRandomTopology().execute();
    }//GEN-LAST:event_depNodesRandomTopologyActionPerformed

    private void depNodesGridTopologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depNodesGridTopologyActionPerformed
        deployNodesGridTopology().execute();
    }//GEN-LAST:event_depNodesGridTopologyActionPerformed

    private void selNodeSinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeSinkActionPerformed
        SetNodeAsSink();
    }//GEN-LAST:event_selNodeSinkActionPerformed

    private void selNodeSetZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeSetZActionPerformed
        if (currentSelectedNode != null) {
            selectedNodes = new Vector<GraphicNode>();
            selectedNodes.add(currentSelectedNode);
            setZPosition(selectedNodes);
            updateLocal();
        }
    }//GEN-LAST:event_selNodeSetZActionPerformed

    private void selNodeSetEnvironmentAttenuationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeSetEnvironmentAttenuationActionPerformed
        if (currentSelectedNode != null) {
            selectedNodes = new Vector<GraphicNode>();
            selectedNodes.add(currentSelectedNode);
            setEnvironmentAttenuation(selectedNodes);
            updateLocal();
        }

    }//GEN-LAST:event_selNodeSetEnvironmentAttenuationActionPerformed

    private void selNodesSetZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesSetZActionPerformed
        setZPosition(selectedNodes);
        updateLocal();
    }//GEN-LAST:event_selNodesSetZActionPerformed

    private void selNodesSetEnvironmentAttenuationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesSetEnvironmentAttenuationActionPerformed
        setEnvironmentAttenuation(selectedNodes);
        updateLocal();
    }//GEN-LAST:event_selNodesSetEnvironmentAttenuationActionPerformed

    private void addRemoveSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRemoveSourceActionPerformed
        setSelectedNodesAsSources();
    }//GEN-LAST:event_addRemoveSourceActionPerformed

    private void addRemoveReceiverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRemoveReceiverActionPerformed
        setSelectedNodesAsReceivers();
    }//GEN-LAST:event_addRemoveReceiverActionPerformed

    private void selNodeSelectNeighborsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeSelectNeighborsActionPerformed


        if (currentSelectedNode != null) {
            selectNeighbors(currentSelectedNode.getPhysicalNode());
        }


    }//GEN-LAST:event_selNodeSelectNeighborsActionPerformed

    private void depNodeSetAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depNodeSetAreaActionPerformed
        setDeployArea();
    }//GEN-LAST:event_depNodeSetAreaActionPerformed

    protected boolean isMousePressed() {
        return pressedPoint_x != -1;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addRemoveReceiver;
    private javax.swing.JMenuItem addRemoveReceiverNodes;
    private javax.swing.JMenuItem addRemoveSource;
    private javax.swing.JMenuItem addRemoveSourceNodes;
    private javax.swing.JPopupMenu currentSelectedNodePopupMenu;
    private javax.swing.JMenuItem depNodeDeployOneNode;
    private javax.swing.JMenuItem depNodeSetArea;
    private javax.swing.JMenu depNodesDeploy;
    private javax.swing.JMenuItem depNodesGridTopology;
    private javax.swing.JMenuItem depNodesRandomTopology;
    private javax.swing.JPopupMenu deployNodePopupMenu;
    private javax.swing.JPopupMenu deployNodesPopupMenu;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JMenu mnuTest;
    private javax.swing.JMenu mnuTest1;
    private javax.swing.JCheckBoxMenuItem selNodeMark;
    private javax.swing.JMenuItem selNodeMonitEnergy;
    private javax.swing.JCheckBoxMenuItem selNodeOnOff;
    private javax.swing.JMenuItem selNodeRoutingAttacks;
    private javax.swing.JMenuItem selNodeRunEvent;
    private javax.swing.JCheckBoxMenuItem selNodeSelectNeighbors;
    private javax.swing.JMenuItem selNodeSetEnvironmentAttenuation;
    private javax.swing.JMenuItem selNodeSetZ;
    private javax.swing.JCheckBoxMenuItem selNodeShowID;
    private javax.swing.JCheckBoxMenuItem selNodeShowNeighbors;
    private javax.swing.JCheckBoxMenuItem selNodeShowOthers;
    private javax.swing.JCheckBoxMenuItem selNodeSink;
    private javax.swing.JCheckBoxMenuItem selNodeUnderAttack;
    private javax.swing.JMenuItem selNodesMonitEnergy;
    private javax.swing.JCheckBoxMenuItem selNodesOnOff;
    private javax.swing.JMenuItem selNodesRemove;
    private javax.swing.JMenuItem selNodesRunEvent;
    private javax.swing.JMenuItem selNodesSetEnvironmentAttenuation;
    private javax.swing.JMenuItem selNodesSetZ;
    private javax.swing.JCheckBoxMenuItem selNodesShowID;
    private javax.swing.JCheckBoxMenuItem selNodesShowNeighbors;
    private javax.swing.JCheckBoxMenuItem selNodesShowNodesThatKnownme;
    private javax.swing.JCheckBoxMenuItem selNodesUnderAttack;
    private javax.swing.JPopupMenu selectionToolPopupMenu;
    // End of variables declaration//GEN-END:variables

    private void paintSelectedArea(Graphics grphcs) {
        if (!canPaintSelectionArea()) {
            return;
        }

        if (selectedArea == null) {
            return;
        }
        Color oldColor = grphcs.getColor();
        grphcs.setColor(selectionColor);
        Graphics2D g = (Graphics2D) grphcs;
        Stroke o = g.getStroke();
        Stroke Pen1 = new BasicStroke(1.0F, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 1.0f, new float[]{3.0f, 3.0f}, 0.0f);
        g.setStroke(Pen1);
        g.draw(selectedArea);
        g.setStroke(o);
        grphcs.setColor(oldColor);
        paintSelectionSize(grphcs);
    }

    public int x2ScreenX(double x) {
        return OFFSET_NETWORK + (int) ((getSize().getWidth() - (OFFSET_NETWORK * 2)) * x / getSize().getWidth());
    }

    public int y2ScreenY(double y) {
        return OFFSET_NETWORK + (int) ((getSize().getHeight() - (OFFSET_NETWORK * 2)) * y / getSize().getHeight());
    }

    protected void updateLocal() {
        if (painting) {
            return;
        }
        JComponent parent = (JComponent) getParent();
        if (parent != null) {
            ((JComponent) getParent()).revalidate();
            ((JComponent) getParent()).repaint();
        } else {
            revalidate();
            repaint();
        }
    }

    @Override
    public synchronized void updateDisplay() {

        updateLocal();
      
    }

    @Override
    public Simulator getSimulator() {
        return simulation.getSimulator();
    }

    @Override
    public Graphics getGraphics() {
        return currentGraphics;
    }

    public void viewVizinhos(boolean selected) {
        if (simulation.getSimulator() != null) {
            for (Node node : simulation.getSimulator().getNodes()) {
                ((SensorNode) node).setPaintNeighborhood(selected);
            }
            updateLocal();
        }
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

    private void clearSelection() {
        selectedArea = null;
        for (GraphicNode graphicNode : selectedNodes) {
            graphicNode.select(false);
        }
        selectedNodes.clear();
        updateLocal();
    }

    public void updateImage(BufferedImage image) {
        backImage = image;
        updateLocal();
    }

    public Vector<GraphicNode> getSelectedNodes() {
        return selectedNodes;
    }

    private void paintMouseCoordinates(Graphics grphcs) {
        Color c = grphcs.getColor();
        grphcs.setColor(Color.BLACK);
        grphcs.drawString(mouseX + "," + mouseY, mouseX, mouseY);
        grphcs.setColor(c);
//        updateLocal();
    }

    private void paintSelectionSize(Graphics grphcs) {
        if (selectedArea == null) {
            return;
        }
        Color c = grphcs.getColor();
        grphcs.setColor(Color.BLACK);
        grphcs.drawString(currentSelectedArea_w + "," + currentSelectedArea_h, mouseX + 15, mouseY + 30);
        grphcs.setColor(c);
//        updateLocal();

    }

    void viewOsQueConhecem(boolean selected) {
        if (simulation.getSimulator() != null) {
            for (Node node : simulation.getSimulator().getNodes()) {
                ((SensorNode) node).setPaintNeighborhoodOrg(selected);
            }
            updateLocal();
        }
    }

    protected Node searchForNode(int id) {
        for (Node node : simulation.getSimulator().getNodes()) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    void searchNode(int intValue) {
        Node node = null;
        if (isSimulationValid()) {
            if (simulation.isNetworkDeployed()) {
                node = searchForNode(intValue);
                if (node != null) {
                    node.getGraphicNode().mark();
                    autoResizeScrollSimulationPanel((int) node.getX(), (int) node.getY());
                    autoScrollPanel((int) node.getX(), (int) node.getY());
                }
            }
            updateLocal();
        }
    }

    /**
     * 
     * @param selectedNodes
     */
    private void createEnergyWatcher(Object[] selectedNodes) {

        if (selectionTool || singleNodeSelectionTool) {
            if (selectedNodes != null) {
                if (selectedNodes.length > 0) {
                    String input = "";
                    if (selectedNodes.length == 1) {
                        input = "Monitoring Node " + ((GraphicNode) selectedNodes[0]).getId();
                    } else {
                        input = JOptionPane.showInputDialog("Name of the monitoring action");
                    }
                    if (input != null) {
                        new NodesEnergyWatcher(selectedNodes, input.toUpperCase()).start();
                    }
                }
            }
        } else {
            // é necessário seleccionar ou a selection tool ou a pointer tool
        }

    }

    void deployNodesToolSelected(boolean selected) {
        deployNodeToolSelected = selected;
    }

    void setViewNodeInfo(boolean selected) {
        paintNodesInfo = selected;
    }

    void selectionPointerSelected(boolean selected) {
        this.singleNodeSelectionTool = selected;
    }

    void startSimulation() {
        try {

            if (!simulationRunning) {
                PlatformManager.getInstance().getPlatformView().showMessage("Starting simulation");
                simulation.start();
                simulationRunning = true;
            } else {
                simulation.resume();
            }
        } catch (Exception e) {
            GUI_Utils.showException(e);
        }

    }

    void stopSimulation() {
        if (simulationRunning) {
            simulation.stop();
        }

    }

    void pauseSimulation() {
        if (simulationRunning) {
            simulation.pause();
        }

    }

    void resumeSimulation() {
        simulation.resume();
    }

    private void showNodeProperties(Vector v) {

        if (v == null) {
            return;
        }
        if (v.isEmpty()) {
            return;
        }

    }

    void selectRandomNodes(int n) {
        if (!selectionTool) {
            GUI_Utils.showMessage("Selection tool must be selected to use Random Node selection feature", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean ok = false;
        int nNodes = 0;

        while (!ok) {
            String sNodes = JOptionPane.showInputDialog("Number of random nodes:");
            if (sNodes != null) {
                if (NumberUtils.isNumber(sNodes)) {
                    nNodes = Integer.parseInt(sNodes);
                    if (nNodes > 0 && nNodes <= getSimulation().getSimulator().getNodes().size()) {
                        ok = true;
                    }
                }
            } else {
                return;
            }
            if (!ok) {
                JOptionPane.showMessageDialog(SimulationPanel.this, "Invalid value!");
            }
        }
        updateLocal();
        if (!ok) {
            return;
        }

        clearSelection();
        if (((RandomList) getSimulator().getNodes()).size() == 0) {
            return;
        }
        int selNodesCount = 0;
        GUI_Utils.mouseWait(this);
        while (selNodesCount < nNodes) {
            Node node = (Node) ((RandomList) getSimulator().getNodes()).randomElement();
            node.getGraphicNode().select(true);
            if (!selectedNodes.contains(node.getGraphicNode())) {
                selNodesCount++;
                selectedNodes.add(node.getGraphicNode());
                updateLocal();
            }

        }
        GUI_Utils.mouseDefault(this);
        PlatformManager.getInstance().getPlatformView().updateSelectedNodes(selectedNodes.size() + "");
        updateLocal();

    }

    private void updateScrollArea() {
        if (mouseX > (getSize().getWidth() - 10)) {
            this.setPreferredSize(new Dimension((int) getSize().getWidth() + 10, (int) getSize().getHeight()));
        }
        if (mouseY > (getSize().getHeight() - 10)) {
            this.setPreferredSize(new Dimension(getWidth(), (int) getHeight() + 10));

        }
        this.scrollRectToVisible(new Rectangle(mouseX, mouseY, 10, 10));

        updateLocal();
    }

    private boolean isSimulationValid() {
        return simulation != null;
    }

    public void resetSimulation() {
        if (getSimulation().isStarted()) {
            getSimulation().reset();
        }
        simulationRunning = false;

    }

    /**
     * 
     * @param node
     */
    private void showAttackProperties(Node node) {
        RoutingAttacksPanel attackPanel = new RoutingAttacksPanel(node);
        attackPanel.setNode(node);
        PlatformDialog d = new PlatformDialog(attackPanel, "Routing Attacks Properties");
        d.display();
    }

    /**
     * 
     */
    private void selectNone() {
        for (GraphicNode graphicNode : selectedNodes) {
            graphicNode.select(false);
        }
        selectedNodes.clear();

    }

    public Color getSelecctionColor() {
        return selectionColor;
    }

    void updateImage(BufferedImage image, boolean strechImage) {
        stretch = strechImage;
        updateImage(image);
    }

    private void updateAreaData() {
        if (selectedArea != null) {
            currentSelectedArea_w = selectedArea.getBounds().getWidth();
            currentSelectedArea_h = selectedArea.getBounds().getHeight();
        }
    }

    void setUnderAttackModeSelectedNodes() {


        int invalid = 0;
        if (selectedNodes.size() > 0) {
            for (GraphicNode graphicNode : selectedNodes) {
                Node node = graphicNode.getPhysicalNode();
                if (node.isSource() || node.isReceiver()) {
                    invalid++;
                } else {
                    node.getRoutingLayer().setUnderAttack(!node.getRoutingLayer().isUnderAttack());
                }
            }
            if (invalid > 0) {
                GUI_Utils.showWarningMessage("Cannot apply attacked nodes to " + invalid + " nodes");
            }
            updateLocal();
        }


    }

    private void setZPosition(Vector<GraphicNode> selectedNodes) {
        Double v = inputNumberValue("Insert Z value:", "");
        if (v == null) {
            return;
        }
        for (GraphicNode graphicNode : selectedNodes) {
            Node node = graphicNode.getPhysicalNode();
            node.setZ(v);
        }
    }

    private void setEnvironmentAttenuation(Vector<GraphicNode> selectedNodes) {
        Double v = inputNumberValue("Insert Enviroment Attenuation Factor value:", "");
        if (v == null) {
            return;
        }
        for (GraphicNode graphicNode : selectedNodes) {
            Node node = graphicNode.getPhysicalNode();
            node.setEnvironmentAttenuation(v);
        }
    }

    private void setUnderAttackModeSelectedNodes(boolean selected) {


        int invalid = 0;
        if (selectedNodes.size() > 0) {
            for (GraphicNode graphicNode : selectedNodes) {
                Node node = graphicNode.getPhysicalNode();
                if (node.isSource() || node.isReceiver()) {
                    invalid++;
                } else {
                    node.getRoutingLayer().setUnderAttack(selected);
                }
            }
            if (invalid > 0) {
                GUI_Utils.showWarningMessage("Cannot apply attacked nodes to " + invalid + " nodes");
            }
            updateLocal();
        }

        if (selectedNodes.size() > 0) {
            for (GraphicNode graphicNode : selectedNodes) {
                graphicNode.getPhysicalNode().getRoutingLayer().setUnderAttack(selected);
            }
            updateLocal();
        }

    }

    void setSelectedNodesAsSources() {
        int invalid = 0;
        if (selectedNodes.size() > 0) {
            for (GraphicNode graphicNode : selectedNodes) {
                Node node = graphicNode.getPhysicalNode();
                if (node.isReceiver() || node.getRoutingLayer().isUnderAttack()) {
                    invalid++;
                } else {
                    node.setSource(!node.isSource());
                }
            }
            if (invalid > 0) {
                GUI_Utils.showWarningMessage("Cannot apply source nodes to " + invalid + " nodes");
            }
            updateLocal();
        }
    }

    void setSelectedNodesAsReceivers() {
        int invalid = 0;
        if (selectedNodes.size() > 0) {
            for (GraphicNode graphicNode : selectedNodes) {
                Node node = graphicNode.getPhysicalNode();
                if (node.isSource() || node.getRoutingLayer().isUnderAttack()) {
                    invalid++;
                } else {
                    node.setReceiver(!node.isReceiver());
                }
            }
            if (invalid > 0) {
                GUI_Utils.showWarningMessage("Cannot apply receiver nodes to " + invalid + " nodes");
            }
            updateLocal();
        }
    }

    private void selectNeighbors(Node selected) {
        Node node = selected;
        selectedNodes.clear();
        for (Node n : node.getMacLayer().getNeighborhood().neighbors) {
            n.getGraphicNode().select(!n.getGraphicNode().isSelected());
            selectedNodes.add(n.getGraphicNode());
        }
        updateLocal();
    }

    private void setDeployArea() {
        Dimension d = inputAreaSize("Insert area size (w-h):");
        if (d != null) {
            int x = mouseX;
            int y = mouseY;
            int h = (int) (y + d.getHeight());
            int w = (int) (x + d.getWidth());
            selectedArea = new GeneralPath();
            selectedArea.moveTo(x, y);
            selectedArea.lineTo(w, y);
            selectedArea.lineTo(w, h);
            selectedArea.lineTo(x, h);
            selectedArea.lineTo(x, y);
            autoResizeScrollSimulationPanel(w, h);
            updateAreaData();
            updateLocal();
        }
    }

    private Dimension inputAreaSize(String message) {
        boolean ok = false;
        String delimiter = "-";
        String[] temp;
        try {

            while (!ok) {
                String sSize = JOptionPane.showInputDialog(message, "w-h");
                if (sSize != null) {
                    temp = sSize.split(delimiter);
                    if (temp != null && temp.length == 2) {
                        if (NumberUtils.isNumber(temp[0])) {
                            if (NumberUtils.isNumber(temp[1])) {
                                ok = true;
                                return new Dimension(Integer.valueOf(temp[0]).intValue(), Integer.valueOf(temp[1]).intValue());
                            }
                        }
                    }
                } else {
                    return null;
                }
                if (!ok) {
                    JOptionPane.showMessageDialog(SimulationPanel.this, "Invalid values!");
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    private void autoScrollPanel(int x, int y) {
        if (getParent() instanceof JViewport) {
            JViewport p = (JViewport) getParent();
            p.scrollRectToVisible(new Rectangle(x - 25, y - 25, x + 25, y + 25));
        }
    }

    private void paintHeatMap(Graphics g) {
    }

    private void updateScroll() {
        if (getSimulation() != null) {
            int w = (int) getSimulation().fieldSize().getWidth();
            int h = (int) getSimulation().fieldSize().getHeight();
            autoResizeScrollSimulationPanel(w, h);
        }
    }

    void searchSinkNode() {
        Node node = null;
        if (isSimulationValid()) {
            if (simulation.isNetworkDeployed()) {
                node = searchForSinkNode();
                if (node != null) {
                    node.getGraphicNode().mark();
                    autoResizeScrollSimulationPanel((int) node.getX(), (int) node.getY());
                    autoScrollPanel((int) node.getX(), (int) node.getY());
                }
            }
            updateLocal();
        }

    }

    private Node searchForSinkNode() {
        for (Node node : simulation.getSimulator().getNodes()) {
            if (node.isSinkNode()) {
                return node;
            }
        }
        return null;

    }

    /**
     * 
     */
    class NodesEnergyWatcher extends Thread {

        String name = "";
        int delay = 0;   // delay for 5 sec.
        int period = 1000;  // repeat every sec.
        Vector<Node> nodes = new Vector<Node>();
        ChartFrame cf = new ChartFrame();
        ChartPanel cp = cf.getChartPanel();
        Timer timer = new Timer();

        /**
         * 
         * @param nodes
         * @param name
         */
        public NodesEnergyWatcher(Object[] nodes, String name) {
            importNodes(nodes);
            this.name = name;
            cf.configChartPanel("Energy Consumption", "Time", "Energy Consumed");
            cf.setTitle("Energy watcher: " + name);
            cf.setPreferredSize(new Dimension(300, 200));
            cf.pack();
            cf.setVisible(true);
            cf.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent w) {
                    timer.cancel();
                }
            });

            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    SummaryStatistics stat = new SummaryStatistics();
                    for (Node node : NodesEnergyWatcher.this.nodes) {
                        double c = 100 - (node.getBateryEnergy().getCurrentPower() * 100 / node.getBateryEnergy().getInitialPower());
                        stat.addValue(c);
                    }
                    double x = Simulator.getSimulationTimeInMillisec() / 1000;
                    cp.updateChart(x, stat.getMean());
                }
            }, delay, period);


        }

        private void importNodes(Object[] gnodes) {
            for (Object o : gnodes) {
                GraphicNode graphicNode = (GraphicNode) o;
                nodes.add(graphicNode.getPhysicalNode());
            }

        }
    }

    private boolean canPaintSelectionArea() {
        return (selectionTool || deployNodeToolSelected);
    }

    @Action(block = Task.BlockingScope.ACTION)
    public Task deployNodesGridTopology() {
        return new DeployNodesGridTopologyTask(org.jdesktop.application.Application.getInstance(org.wisenet.platform.PlatformApp.class));
    }

    private class DeployNodesGridTopologyTask extends org.jdesktop.application.Task<Object, Void> {

        boolean ok = false;
        int nDistance = 0;
        AbstractNodeFactory nf = simulation.getNodeFactory();
        int nRange = simulation.getInitialMaxNodeRange();
        private long start;
        Rectangle deployArea;

        DeployNodesGridTopologyTask(org.jdesktop.application.Application app) {

            super(app);
            if (selectedArea == null) {
                deployArea = getBounds();
            } else {
                deployArea = ((GeneralPath) selectedArea.clone()).getBounds();
            }

            Double v = inputNumberValue("Distance Between Nodes:", "");
            if (v == null) {
                ok = false;
                return;
            } else {
                nDistance = v.intValue();
                ok = true;
            }

        }

        @Override
        protected Object doInBackground() {
            if (!ok) {
                return null;
            }
            start = System.currentTimeMillis();
            try {
                fireBeforeNodeDeploy(new DeployEvent(SimulationPanel.this));
                GUI_Utils.mouseWait(SimulationPanel.this);
                this.setMessage("Generating nodes using factory");
                GridTopologyManager manager = createGridTopologyManager(deployArea, nDistance);
                simulation.appendNodes(manager);
            } catch (Exception ex) {
                GUI_Utils.mouseDefault(SimulationPanel.this);
                GUI_Utils.showException(ex);
                updateLocal();
                return false;
            }

            updateLocal();
            this.setMessage("Building Network using radio");
            this.setProgress(0, 0, 1);
            GUI_Utils.mouseWait(SimulationPanel.this);
            simulation.buildNetwork();
            updateLocal();
            return true;  // return your result
        }

        private GridTopologyManager createGridTopologyManager(Rectangle deployArea, int nDistance) {
            GridTopologyManager manager = new GridTopologyManager();
            manager.setNodeFactory(getSimulation().getNodeFactory());
            GridTopologyParameters parameters = new GridTopologyParameters();
            parameters.set("x", (int) deployArea.getX());
            parameters.set("y", (int) deployArea.getY());
            parameters.set("width", (int) deployArea.getWidth());
            parameters.set("height", (int) deployArea.getHeight());
            parameters.set("distance", (int) nDistance);
            manager.setParameters(parameters);
            return manager;
        }

        @Override
        protected void succeeded(Object result) {
            setMessage("Building Network... done in " + (System.currentTimeMillis() - start) + " milliseconds");
            this.setProgress(1, 0, 1);
            fireAfterNodeDeploy(new DeployEvent(SimulationPanel.this));
            GUI_Utils.mouseDefault(SimulationPanel.this);
        }
    }

    @Action(block = Task.BlockingScope.COMPONENT)
    public Task deployNodesUsingRandomTopology() {
        return new DeployNodesUsingRandomTopologyTask(org.jdesktop.application.Application.getInstance(org.wisenet.platform.PlatformApp.class));
    }

    public class DeployNodesUsingRandomTopologyTask extends org.jdesktop.application.Task<Object, Void> {

        boolean ok = false;
        int nNodes = 0;
        RandomTopologyManager tm = new RandomTopologyManager();
        AbstractNodeFactory nf = simulation.getNodeFactory();
        int nRange = simulation.getInitialMaxNodeRange();
        private long start;
        Rectangle deployArea;

        DeployNodesUsingRandomTopologyTask(org.jdesktop.application.Application app) {
            super(app);
            if (selectedArea == null) {
                deployArea = getBounds();
            } else {
                deployArea = ((GeneralPath) selectedArea.clone()).getBounds();
            }
            while (!ok) {
                String sNodes = JOptionPane.showInputDialog("Number of nodes to deploy:", getDefaultNumberOfNodes(deployArea));
                if (sNodes != null) {
                    if (NumberUtils.isNumber(sNodes)) {
                        nNodes = Integer.parseInt(sNodes);
                        if (nNodes > 0) {
                            ok = true;
                        }
                    }
                } else {
                    return;
                }
                if (!ok) {
                    JOptionPane.showMessageDialog(SimulationPanel.this, "Invalid value!");
                }
            }
        }

        @Override
        protected Object doInBackground() {
            if (!ok) {
                return null;
            }
            int status = 0;
            start = System.currentTimeMillis();
            fireBeforeNodeDeploy(new DeployEvent(SimulationPanel.this));
            try {
                GUI_Utils.mouseWait(SimulationPanel.this);
                RandomTopologyManager manager = new RandomTopologyManager();
                manager.setNodeFactory(getSimulation().getNodeFactory());
                RandomTopologyParameters parameters = new RandomTopologyParameters();
                parameters.set("x", (int) deployArea.getX());
                parameters.set("y", (int) deployArea.getY());
                parameters.set("width", (int) deployArea.getWidth());
                parameters.set("height", (int) deployArea.getHeight());
//                parameters.set("maxelevation", (int) manager.getNodeFactory().getMaxZ());
                parameters.set("nodes", (int) nNodes);
                manager.setParameters(parameters);
                simulation.appendNodes(manager);
            } catch (Exception ex) {
                GUI_Utils.mouseDefault(SimulationPanel.this);
//                Logger.getLogger(SimulationPanel.class.getName()).log(Level.SEVERE, null, ex);
                GUI_Utils.showException(ex);
                updateLocal();
                return false;
            }

            updateLocal();
            this.setMessage("Building Network using radio");
            this.setProgress(0, 0, 1);
            GUI_Utils.mouseWait(SimulationPanel.this);
            simulation.buildNetwork();

            updateLocal();
            fireAfterNodeDeploy(new DeployEvent(SimulationPanel.this));
            return true;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            setMessage("Building Network... done in " + (System.currentTimeMillis() - start) + " milliseconds");
            this.setProgress(1, 0, 1);
            GUI_Utils.mouseDefault(SimulationPanel.this);
        }

        private int getDefaultNumberOfNodes(Rectangle deployArea) {
            double w = deployArea.getWidth();
            double h = deployArea.getHeight();
            int range = getSimulation().getNodeFactory().getNodeMaxRadioRange();
            double r = h / range;
            double c = w / range;
            return (int) (r * c);
        }
    }

    void paintInfo(Graphics g) {

        if (nodeInfo != null) {
            Color oldColor = g.getColor();
            Graphics2D graphics2 = (Graphics2D) g;
            Font monoFont = new Font("Times", Font.BOLD, 10);
            g.setFont(monoFont);
            FontMetrics fm = g.getFontMetrics();
            int step = fm.getMaxAscent() + fm.getMaxDescent();
            int mSize = 0;
            for (int i = 0; i < nodeInfo.length; i++) {
                String string = nodeInfo[i];
                int s = fm.charsWidth(string.toCharArray(), 0, string.length());
                if (s > mSize) {
                    mSize = s;
                }
            }

            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(mouseX, mouseY, 10 + mSize, 10 + (nodeInfo.length * (step)), 30, 30);
            // desenha o fundo
            g.setColor(Color.lightGray);
            graphics2.fill(roundedRectangle);
            // desenha a border do rectangulo
            g.setColor(Color.BLACK);
            graphics2.draw(roundedRectangle);

            int y = mouseY + 5;
            for (int i = 0; i < nodeInfo.length; i++) {
                String string = nodeInfo[i];
                y += step;
                g.drawString(string, mouseX + 5, y);
            }
            g.setColor(oldColor);
        }
    }

    public AbstractSimulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     *
     * PAINTING FUNCTIONS
     *
     */
    public void resizeUpdateSimulatedArea() {
        setAutoscrolls(true);

        if (!resizeSimulatedArea) {
            return;
        }
    }

    public void updateMouseCoordinates(MouseEvent evt) {
        mouseX = x2ScreenX(evt.getX());
        mouseY = y2ScreenY(evt.getY());
    }

    public void paintNetwork(Graphics g) {
        if (simulation == null) {
            return;
        }

        currentGraphics = g;

        if (simulation.getSimulator() != null) {
            simulation.getSimulator().display(this);
        }
    }

    public boolean isPaintMouseCoordinates() {
        return paintMouseCoordinates;
    }

    public void setPaintMouseCoordinates(boolean paintMouseCoordinates) {
        this.paintMouseCoordinates = paintMouseCoordinates;
    }

    public synchronized void mainPaintLoop(Graphics g) {
//        Image offscreen = createImage(getWidth(), getHeight());
//        Graphics b = offscreen.getGraphics();
//        currentGraphics = g;
        Graphics grphcs = g;
        paintImage(g);
        paintNetwork(grphcs);
        if (canPaintSelectionArea()) {
            paintSelectedArea(grphcs);
        }
        if (paintMouseCoordinates && visibleMouseCoordinates) {
            paintMouseCoordinates(grphcs);
        }
        paintSelectionSize(grphcs);

        if (paintNodesInfo) {
            paintInfo(grphcs);
        }

        paintHeatMap(g);
        updateScroll();
    }

    private void paintImage(Graphics g) {
        if (backImage != null) {
            if (!stretch) {
                g.drawImage(backImage, 0, 0, backImage.getWidth(this), backImage.getHeight(this), this);
            } else {
                g.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void drawSelectedArea(java.awt.event.MouseEvent evt) {
        if (!canPaintSelectionArea()) {
            return;
        }

        selectedArea = new GeneralPath();
        selectedArea.moveTo(pressedPoint_x, pressedPoint_y);
        selectedArea.lineTo(mouseX, pressedPoint_y);
        selectedArea.lineTo(mouseX, mouseY);
        selectedArea.lineTo(pressedPoint_x, mouseY);
        selectedArea.lineTo(pressedPoint_x, pressedPoint_y);
        updateAreaData();
    }

    class EnergyController implements EnergyListener {

        protected PipedOutputStream outputStream;
        protected DataOutputStream dataOutputStream;
        protected EnergyWatcherThread energyWatcherThread;

        public EnergyController() {
            energyWatcherThread = new EnergyWatcherThread();
            this.dataOutputStream = new DataOutputStream(energyWatcherThread.getOutputStream());
        }

        public void onConsume(EnergyEvent evt) {
            try {
                Batery b = (Batery) evt.getSource();
                this.dataOutputStream.writeDouble(evt.getValue());
                //this.dataOutputStream.writeDouble(b.getAverageConsumption());
                double t = evt.getRealTime();//b.getHostNode().getSimulator().getSimulationTimeInMillisec() / 1000;//System.nanoTime()*10E3; //
                this.dataOutputStream.writeDouble(t);
            } catch (IOException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            }
        }

        public Logger getLogger() {
            return Logger.getLogger(SimulationPanel.class.getName());
        }

        private void start() {
            energyWatcherThread.start();
        }
    }

    @Action
    public void ShowNodeProperties() {
        Vector v = new Vector();
        if (currentSelectedNode != null) {
            v.add(currentSelectedNode);
        }
        showNodeProperties(v);
    }

    @Action
    public void ShowNodesProperties() {
        showNodeProperties(selectedNodes);
    }

    @Action
    public Task RemoveNodesSelected() {
        return new RemoveNodesSelectedTask(org.jdesktop.application.Application.getInstance(org.wisenet.platform.PlatformApp.class));
    }

    private class RemoveNodesSelectedTask extends org.jdesktop.application.Task<Object, Void> {

        boolean ok = false;
        private long start;

        RemoveNodesSelectedTask(org.jdesktop.application.Application app) {
            super(app);
            ok = GUI_Utils.confirm("Do you want to delete the selected nodes?");
        }

        @Override
        protected Object doInBackground() {
            if (ok) {
                if (selectedArea != null) {
                    fireBeforeNodeDeploy(new DeployEvent(SimulationPanel.this));
                    setMessage("Removing nodes...");
                    for (GraphicNode graphicNode : selectedNodes) {
                        Node n = graphicNode.getPhysicalNode();
                        getSimulator().getNodes().remove(n);
                    }
                    setMessage("Removing nodes... done!");
//                    GUI.showSimulationNrOfNodes(getSimulator().getNodes().size());

                    updateLocal();
                    start = System.currentTimeMillis();
                    this.setMessage("Building Network using radio");
                    this.setProgress(0, 0, 1);
                    simulation.buildNetwork();
                    updateLocal();
                    fireAfterNodeDeploy(new DeployEvent(SimulationPanel.this));
                    return true;  // return your result

                }

            }


            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            setMessage("Building Network... done in " + (System.currentTimeMillis() - start) + " milliseconds");
        }
    }

    @Action
    public void SetNodeAsSink() {
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) selNodeSink;
            if (m.isSelected()) {
                ((SensorNode) currentSelectedNode.getPhysicalNode()).setSinkNode(true);
            } else {
                ((SensorNode) currentSelectedNode.getPhysicalNode()).setSinkNode(false);
                currentSelectedNode.unmark();

            }

            updateLocal();
        }

    }

    public void PlaceNodeHereAction() {
        try {
            fireBeforeNodeDeploy(new DeployEvent(SimulationPanel.this));
            AbstractNodeFactory nf = simulation.getNodeFactory();
            int nRange = simulation.getInitialMaxNodeRange();
            Node node = nf.createNode(mouseX, mouseY, 0);
            node.getConfig().setSetRadioRange(nRange);
            simulation.getSimulator().addNode((SensorNode) node);
            fireAfterNodeDeploy(new DeployEvent(SimulationPanel.this));
            simulation.buildNetwork();
            updateLocal();
        } catch (Exception ex) {
            Logger.getLogger(SimulationPanel.class.getName()).log(Level.SEVERE, null, ex);
            GUI_Utils.showException(ex);
        }



    }

    public BufferedImage getNetworkImage() {
        if (getSimulator().getNodes().isEmpty()) {
            return null;
        }

        BufferedImage bImage = new BufferedImage(this.getWidth(), this.getHeight(), 1);
        Graphics g = bImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        mainPaintLoop(g);
        return bImage;
    }
    protected Hashtable<Node, Object> energyControllersTable = new Hashtable<Node, Object>();

    public void takeSnapshot() {
        if (getSimulator().getNodes().isEmpty()) {
            return;
        }

        BufferedImage bImage = new BufferedImage(this.getWidth(), this.getHeight(), 1);
        Graphics g = bImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        mainPaintLoop(g);

        String returnVal = PlatformUtils.selectImageFile2save(this);
        if (returnVal != null) {
            if (PlatformUtils.saveImageToFile(returnVal, bImage)) {
                return;
            }
        }
    }

    @Action
    public void ShowAttackOptions() {
    }

    public void addSimulationPanelListerner(SimulationPanelEventListener listener) {
        simulationPanelListeners.add(SimulationPanelEventListener.class, listener);
    }

    public void removeSimulationPanelListerner(SimulationPanelEventListener listener) {
        simulationPanelListeners.remove(SimulationPanelEventListener.class, listener);
    }

    private void fireAfterNodeDeploy(DeployEvent event) {
        Object[] listeners = simulationPanelListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationPanelEventListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationPanelEventListener) listeners[i + 1]).afterNodeDeploy(event);
            }
        }

    }

    private void fireBeforeNodeDeploy(DeployEvent event) {
        Object[] listeners = simulationPanelListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i
                < numListeners; i += 2) {
            if (listeners[i] == SimulationPanelEventListener.class) {
                // pass the event to the listeners event dispatch method
                ((SimulationPanelEventListener) listeners[i + 1]).beforeNodeDeploy(event);
            }
        }

    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
    }
}
