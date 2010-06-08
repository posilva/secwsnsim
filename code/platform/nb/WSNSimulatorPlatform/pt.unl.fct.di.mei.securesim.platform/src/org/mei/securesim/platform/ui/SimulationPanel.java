package org.mei.securesim.platform.ui;

import java.io.IOException;
import org.mei.securesim.core.energy.listeners.EnergyEvent;
import org.mei.securesim.gui.GraphicPoint;
import org.mei.securesim.gui.GraphicNode;
import org.mei.securesim.core.nodes.basic.SensorNode;
import org.mei.securesim.core.engine.Simulator;
import org.mei.securesim.core.ui.ISimulationDisplay;
import org.mei.securesim.core.engine.SimulatorFinishListener;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.mei.securesim.core.engine.events.SimulatorEvent;
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
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.mei.securesim.components.instruments.CoverageController;
import org.mei.securesim.core.energy.Batery;
import org.mei.securesim.core.energy.listeners.EnergyListener;
import org.mei.securesim.core.nodes.factories.NodeFactory;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.platform.core.instruments.energy.EnergyWatcherThread;
import org.mei.securesim.components.simulation.Simulation;
import org.mei.securesim.components.simulation.SimulationFactory;
import org.mei.securesim.components.simulation.basic.BasicSimulation;
import org.mei.securesim.components.topology.RandomTopologyManager;
import org.mei.securesim.platform.PlatformView;
import org.mei.securesim.platform.core.PlatformController;
import org.mei.securesim.platform.core.charts.ui.ChartPanel;
import org.mei.securesim.platform.ui.frames.NodePropertiesDialog;
import org.mei.securesim.platform.utils.gui.GUI_Utils;
import org.mei.securesim.utils.RandomList;

/**
 *
 * @author posilva
 */
public class SimulationPanel extends javax.swing.JPanel implements ISimulationDisplay, SimulatorFinishListener {

    public static final int OFFSET_NETWORK = 0;
    public static final int SHIFT_KEYCODE = 16;
    /**
     * MAIN VARIABLES
     */
    private Simulation simulation;
    private SimulationFactory simulationFactory;
    /**
     * GRAPHIC HANDLING VARIABLES
     */
    Vector<GraphicNode> selectedNodes = new Vector<GraphicNode>();
    private Graphics currentGraphics;
    private GraphicPoint pressedPoint = null;
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
    private boolean networkBuilded = false;
    private boolean networkDeployed = false;
    private boolean networkRunning = false;
    private boolean resizeSimulatedArea = true;
    private boolean visibleMouseCoordinates = false;
    private boolean deployNodeToolSelected = false;
    private boolean paintNodesInfo = false;
    private boolean selectionPointerToolSelected = false;
    private boolean controlKeyPressed;
    private boolean shiftKeyPressed;
    /**
     * UTILS VARIABLES
     */
    BufferedImage backImage = null;
    private boolean mouseDrag;
    private boolean mousePressed;

    /**
     * CONSTRUCTORS
     */
    public SimulationPanel() {
        initComponents();
        try {
            loadImage();
        } catch (Exception ex) {
            Logger.getLogger(SimulationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Settings for the simulation
     * @param s
     */
    public void settingSimulation(SimulationFactory sf) {
        this.simulationFactory = sf;
        clearSimulation();

    }

    /**
     * Clear Simulation
     * @return
     */
    void clearSimulation() {
        try {
            this.simulation = this.simulationFactory.getNewInstance();
            simulation.setDisplay(this);
            initSimulation();

        } catch (InstantiationException ex) {
            Logger.getLogger(SimulationPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SimulationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void initSimulation() {
        Simulator.randomGenerator.reset();
        Node.resetCounter();
        simulation.setDisplay(this);
        simulation.preInit();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        currentSelectedNodePopupMenu = new javax.swing.JPopupMenu();
        selNodeVisualizacao = new javax.swing.JMenu();
        selNodeVerVizinhos = new javax.swing.JCheckBoxMenuItem();
        selNodeVerID = new javax.swing.JCheckBoxMenuItem();
        selNodeVerOsQueMeConhecem = new javax.swing.JCheckBoxMenuItem();
        selNodeMarcar = new javax.swing.JCheckBoxMenuItem();
        selNodeOperacao = new javax.swing.JMenu();
        selNodeOnOff = new javax.swing.JCheckBoxMenuItem();
        selNodeSink = new javax.swing.JCheckBoxMenuItem();
        selNodeMonitorizacao = new javax.swing.JMenu();
        selNodeMonitEnergia = new javax.swing.JMenuItem();
        selNodeRunEvent = new javax.swing.JMenuItem();
        selNodeShowProperties = new javax.swing.JMenuItem();
        selectionToolPopupMenu = new javax.swing.JPopupMenu();
        selNodesVisualizacao = new javax.swing.JMenu();
        selNodesVerVizinhos = new javax.swing.JCheckBoxMenuItem();
        selNodesVerID = new javax.swing.JCheckBoxMenuItem();
        selNodesVerOsQueMeConhecem = new javax.swing.JCheckBoxMenuItem();
        selNodesOperacao = new javax.swing.JMenu();
        selNodesOnOff = new javax.swing.JCheckBoxMenuItem();
        selNodesMonitorizacao = new javax.swing.JMenu();
        selNodesMonitEnergia = new javax.swing.JMenuItem();
        selNodesInstruments = new javax.swing.JMenu();
        selNodesInstrumentsCoverage = new javax.swing.JMenu();
        selNodesInstrumentsCoverageEnable = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        selNodesInstrumentsCoverageSelSources = new javax.swing.JMenuItem();
        selNodesInstrumentsReliability = new javax.swing.JMenu();
        selNodesInstrumentsReliabilityEnable = new javax.swing.JCheckBoxMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        selNodesInstrumentsReliabilitySelSources = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        selNodesShowProperties = new javax.swing.JMenuItem();
        selNodesRemove = new javax.swing.JMenuItem();
        deployNodesPopupMenu = new javax.swing.JPopupMenu();
        depNodesDeploy = new javax.swing.JMenu();
        depNodesRandomTopology = new javax.swing.JMenuItem();
        depNodesGridTopology = new javax.swing.JMenuItem();

        currentSelectedNodePopupMenu.setLabel("Configurar Sensor");

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(SimulationPanel.class);
        selNodeVisualizacao.setText(resourceMap.getString("selNodeVisualizacao.text")); // NOI18N

        selNodeVerVizinhos.setSelected(true);
        selNodeVerVizinhos.setText(resourceMap.getString("selNodeVerVizinhos.text")); // NOI18N
        selNodeVerVizinhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeVerVizinhosActionPerformed(evt);
            }
        });
        selNodeVisualizacao.add(selNodeVerVizinhos);

        selNodeVerID.setSelected(true);
        selNodeVerID.setText(resourceMap.getString("selNodeVerID.text")); // NOI18N
        selNodeVerID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeVerIDActionPerformed(evt);
            }
        });
        selNodeVisualizacao.add(selNodeVerID);

        selNodeVerOsQueMeConhecem.setSelected(true);
        selNodeVerOsQueMeConhecem.setText(resourceMap.getString("selNodeVerOsQueMeConhecem.text")); // NOI18N
        selNodeVerOsQueMeConhecem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeVerOsQueMeConhecemActionPerformed(evt);
            }
        });
        selNodeVisualizacao.add(selNodeVerOsQueMeConhecem);

        selNodeMarcar.setSelected(true);
        selNodeMarcar.setText(resourceMap.getString("selNodeMarcar.text")); // NOI18N
        selNodeMarcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeMarcarActionPerformed(evt);
            }
        });
        selNodeVisualizacao.add(selNodeMarcar);

        currentSelectedNodePopupMenu.add(selNodeVisualizacao);

        selNodeOperacao.setText(resourceMap.getString("selNodeOperacao.text")); // NOI18N

        selNodeOnOff.setSelected(true);
        selNodeOnOff.setText(resourceMap.getString("selNodeOnOff.text")); // NOI18N
        selNodeOnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeOnOffActionPerformed(evt);
            }
        });
        selNodeOperacao.add(selNodeOnOff);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getActionMap(SimulationPanel.class, this);
        selNodeSink.setAction(actionMap.get("SetNodeAsSink")); // NOI18N
        selNodeSink.setSelected(true);
        selNodeOperacao.add(selNodeSink);

        currentSelectedNodePopupMenu.add(selNodeOperacao);

        selNodeMonitorizacao.setText(resourceMap.getString("selNodeMonitorizacao.text")); // NOI18N

        selNodeMonitEnergia.setText(resourceMap.getString("selNodeMonitEnergia.text")); // NOI18N
        selNodeMonitEnergia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeMonitEnergiaActionPerformed(evt);
            }
        });
        selNodeMonitorizacao.add(selNodeMonitEnergia);

        currentSelectedNodePopupMenu.add(selNodeMonitorizacao);

        selNodeRunEvent.setText("Run application");
        selNodeRunEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodeRunEventActionPerformed(evt);
            }
        });
        currentSelectedNodePopupMenu.add(selNodeRunEvent);

        selNodeShowProperties.setAction(actionMap.get("ShowNodeProperties")); // NOI18N
        selNodeShowProperties.setText(resourceMap.getString("selNodeShowProperties.text")); // NOI18N
        selNodeShowProperties.setToolTipText(resourceMap.getString("selNodeShowProperties.toolTipText")); // NOI18N
        currentSelectedNodePopupMenu.add(selNodeShowProperties);

        selNodesVisualizacao.setText(resourceMap.getString("selNodesVisualizacao.text")); // NOI18N

        selNodesVerVizinhos.setSelected(true);
        selNodesVerVizinhos.setText(resourceMap.getString("selNodesVerVizinhos.text")); // NOI18N
        selNodesVerVizinhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesVerVizinhosActionPerformed(evt);
            }
        });
        selNodesVisualizacao.add(selNodesVerVizinhos);

        selNodesVerID.setSelected(true);
        selNodesVerID.setText(resourceMap.getString("selNodesVerID.text")); // NOI18N
        selNodesVerID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesVerIDActionPerformed(evt);
            }
        });
        selNodesVisualizacao.add(selNodesVerID);

        selNodesVerOsQueMeConhecem.setSelected(true);
        selNodesVerOsQueMeConhecem.setText(resourceMap.getString("selNodesVerOsQueMeConhecem.text")); // NOI18N
        selNodesVerOsQueMeConhecem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesVerOsQueMeConhecemActionPerformed(evt);
            }
        });
        selNodesVisualizacao.add(selNodesVerOsQueMeConhecem);

        selectionToolPopupMenu.add(selNodesVisualizacao);

        selNodesOperacao.setText(resourceMap.getString("selNodesOperacao.text")); // NOI18N

        selNodesOnOff.setSelected(true);
        selNodesOnOff.setText(resourceMap.getString("selNodesOnOff.text")); // NOI18N
        selNodesOnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesOnOffActionPerformed(evt);
            }
        });
        selNodesOperacao.add(selNodesOnOff);

        selectionToolPopupMenu.add(selNodesOperacao);

        selNodesMonitorizacao.setText(resourceMap.getString("selNodesMonitorizacao.text")); // NOI18N

        selNodesMonitEnergia.setText(resourceMap.getString("selNodesMonitEnergia.text")); // NOI18N
        selNodesMonitEnergia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesMonitEnergiaActionPerformed(evt);
            }
        });
        selNodesMonitorizacao.add(selNodesMonitEnergia);

        selectionToolPopupMenu.add(selNodesMonitorizacao);

        selNodesInstruments.setText(resourceMap.getString("selNodesInstruments.text")); // NOI18N
        selNodesInstruments.setToolTipText("");

        selNodesInstrumentsCoverage.setText(resourceMap.getString("selNodesInstrumentsCoverage.text")); // NOI18N
        selNodesInstrumentsCoverage.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                selNodesInstrumentsCoverageMenuSelected(evt);
            }
        });

        selNodesInstrumentsCoverageEnable.setSelected(true);
        selNodesInstrumentsCoverageEnable.setText(resourceMap.getString("selNodesInstrumentsCoverageEnable.text")); // NOI18N
        selNodesInstrumentsCoverage.add(selNodesInstrumentsCoverageEnable);
        selNodesInstrumentsCoverage.add(jSeparator2);

        selNodesInstrumentsCoverageSelSources.setText(resourceMap.getString("selNodesInstrumentsCoverageSelSources.text")); // NOI18N
        selNodesInstrumentsCoverage.add(selNodesInstrumentsCoverageSelSources);

        selNodesInstruments.add(selNodesInstrumentsCoverage);

        selNodesInstrumentsReliability.setText("jMenu2");

        selNodesInstrumentsReliabilityEnable.setSelected(true);
        selNodesInstrumentsReliabilityEnable.setText(resourceMap.getString("selNodesInstrumentsReliabilityEnable.text")); // NOI18N
        selNodesInstrumentsReliabilityEnable.setFocusPainted(true);
        selNodesInstrumentsReliability.add(selNodesInstrumentsReliabilityEnable);
        selNodesInstrumentsReliability.add(jSeparator3);

        selNodesInstrumentsReliabilitySelSources.setSelected(true);
        selNodesInstrumentsReliabilitySelSources.setText(resourceMap.getString("selNodesInstrumentsReliabilitySelSources.text")); // NOI18N
        selNodesInstrumentsReliability.add(selNodesInstrumentsReliabilitySelSources);

        selNodesInstruments.add(selNodesInstrumentsReliability);

        selectionToolPopupMenu.add(selNodesInstruments);
        selectionToolPopupMenu.add(jSeparator1);

        selNodesShowProperties.setAction(actionMap.get("ShowNodesProperties")); // NOI18N
        selNodesShowProperties.setText(resourceMap.getString("selNodesShowProperties.text")); // NOI18N
        selNodesShowProperties.setToolTipText(resourceMap.getString("selNodesShowProperties.toolTipText")); // NOI18N
        selectionToolPopupMenu.add(selNodesShowProperties);

        selNodesRemove.setAction(actionMap.get("RemoveNodesSelected")); // NOI18N
        selNodesRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selNodesRemoveActionPerformed(evt);
            }
        });
        selectionToolPopupMenu.add(selNodesRemove);

        depNodesDeploy.setText(resourceMap.getString("depNodesDeploy.text")); // NOI18N

        depNodesRandomTopology.setAction(actionMap.get("deployNodesUsingRandomTopology")); // NOI18N
        depNodesRandomTopology.setText(resourceMap.getString("depNodesRandomTopology.text")); // NOI18N
        depNodesRandomTopology.setToolTipText(resourceMap.getString("depNodesRandomTopology.toolTipText")); // NOI18N
        depNodesDeploy.add(depNodesRandomTopology);

        depNodesGridTopology.setAction(actionMap.get("deployNodesGridTopology")); // NOI18N
        depNodesGridTopology.setText(resourceMap.getString("depNodesGridTopology.text")); // NOI18N
        depNodesGridTopology.setToolTipText(resourceMap.getString("depNodesGridTopology.toolTipText")); // NOI18N
        depNodesDeploy.add(depNodesGridTopology);

        deployNodesPopupMenu.add(depNodesDeploy);

        setBackground(resourceMap.getColor("background")); // NOI18N
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
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        updateMouseCoordinates(evt);
        if (deployNodeToolSelected) {
            if (evt.getButton() == 0) {
                if (isMousePressed()) {
                    drawSelectedArea(evt);
                }
                update();
            }


        } else if (selectionTool) {
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
                    PlatformController.getInstance().getPlatformView().setSelectedNodes(selectedNodes.size() + "");
                }
                update();
            }
        } else if (selectionPointerToolSelected) {
            if (!mousePressed) {
                return;
            }
            mouseDrag = true;
            if (currentSelectedNode != null) {
//                if(currentSelectedNode.contains(evt.getX(), evt.getY()))
                currentSelectedNode.moveTo(mouseX, mouseY);
                update();
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
                GraphicNode c = selectedCircle(evt);
                if (c != null) {
                    nodeInfo = c.getPhysicalNode().getInfo();
                } else {
                    nodeInfo = null;
                }

            }
        }
        update();

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
            }
        }

        return circleSelected;
    }

    @Override
    public void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        mainPaintLoop(grphcs);
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
        } else if (selectionPointerToolSelected) {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                currentSelectedNode = null;
                currentSelectedNode = selectedCircle(evt);
                if (evt.getClickCount() > 1) {
                    if (currentSelectedNode != null) {
//                        showNodeProperties();
                    }
                } else {
                    if (shiftKeyPressed) {
                        selectedNodes.add(currentSelectedNode);
                    }

                }
                PlatformController.getInstance().getPlatformView().setSelectedNodes(selectedNodes.size() + "");

            } else if (evt.getButton() == MouseEvent.BUTTON3) {

                currentSelectedNode = null;
                currentSelectedNode = selectedCircle(evt);

                if (currentSelectedNode != null) {
                    SensorNode s = SensorNode.cast(currentSelectedNode.getPhysicalNode());
                    selNodeVerID.setSelected(s.isShowID());
                    selNodeVerVizinhos.setSelected(s.isPaintNeighborhoodDst());
                    selNodeOnOff.setSelected(s.isTurnedOn());
                    selNodeSink.setSelected(s.isSinkNode());
                    selNodeMarcar.setSelected(currentSelectedNode.isMarked());
                    currentSelectedNodePopupMenu.show(this, mouseX, mouseY);
                }

            }
        }
        update();
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
            if ((insideSelectionArea(evt) && evt.getButton() == MouseEvent.BUTTON3) || selectedNodes.size() > 0) {
                selectionToolPopupMenu.show(this, mouseX, mouseY);
            } else {
                clearSelection();
                pressedPoint = new GraphicPoint(mouseX, mouseY);
            }
        } else if (deployNodeToolSelected) {

            if (insideSelectionArea(evt) && evt.getButton() == MouseEvent.BUTTON3) {
                deployNodesPopupMenu.show(this, mouseX, mouseY);
            } else {
                clearSelection();
                pressedPoint = new GraphicPoint(mouseX, mouseY);
            }
        } else if (selectionPointerToolSelected) {
            mousePressed = true;
        }


    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        updateMouseCoordinates(evt);
        mouseDrag = false;
        pressedPoint = null;
        //selectedArea = null;
        currentSelectedNode = null;
        mousePressed = false;
        update();
    }//GEN-LAST:event_formMouseReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        resizeUpdateSimulatedArea();
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

    private void selNodeOnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeOnOffActionPerformed
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
            if (m.isSelected()) {
                ((SensorNode) currentSelectedNode.getPhysicalNode()).powerOn();
            } else {
                ((SensorNode) currentSelectedNode.getPhysicalNode()).shutdown();
            }

            update();
        }
    }//GEN-LAST:event_selNodeOnOffActionPerformed

    private void selNodesVerVizinhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesVerVizinhosActionPerformed
        if (selectionTool) {
            if (selectedNodes != null) {
                JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
                for (GraphicNode selNode : selectedNodes) {
                    ((SensorNode) selNode.getPhysicalNode()).setPaintNeighborhood(m.isSelected());
                }
                update();
            }
        }
    }//GEN-LAST:event_selNodesVerVizinhosActionPerformed

    private void selNodesVerIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesVerIDActionPerformed
        if (selectionTool) {
            if (selectedNodes != null) {
                JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
                for (GraphicNode selNode : selectedNodes) {
                    ((SensorNode) selNode.getPhysicalNode()).setShowID(m.isSelected());
                }
                update();
            }
        }
    }//GEN-LAST:event_selNodesVerIDActionPerformed

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

                update();
            }
        }

    }//GEN-LAST:event_selNodesOnOffActionPerformed

    private void selNodesVerOsQueMeConhecemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesVerOsQueMeConhecemActionPerformed
        if (selectionTool) {
            if (selectedNodes != null) {
                JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
                for (GraphicNode selNode : selectedNodes) {
                    ((SensorNode) selNode.getPhysicalNode()).setPaintNeighborhoodOrg(m.isSelected());
                }
                update();
            }
        }
    }//GEN-LAST:event_selNodesVerOsQueMeConhecemActionPerformed

    private void selNodeVerOsQueMeConhecemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeVerOsQueMeConhecemActionPerformed
        if (currentSelectedNode != null) {
            JCheckBoxMenuItem m = (JCheckBoxMenuItem) evt.getSource();
            ((SensorNode) currentSelectedNode.getPhysicalNode()).setPaintNeighborhoodOrg(m.isSelected());
            update();
        }
    }//GEN-LAST:event_selNodeVerOsQueMeConhecemActionPerformed

    private void selNodeMonitEnergiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeMonitEnergiaActionPerformed
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
        update();
//        }

    }//GEN-LAST:event_selNodeMonitEnergiaActionPerformed

    private void selNodeMarcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeMarcarActionPerformed
        if (currentSelectedNode != null) {
            JMenuItem m = (JMenuItem) evt.getSource();
            if (!currentSelectedNode.isMarked()) {
                currentSelectedNode.mark();
            } else {
                currentSelectedNode.unmark();
            }
            update();
        }

    }//GEN-LAST:event_selNodeMarcarActionPerformed

    private void selNodesMonitEnergiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodesMonitEnergiaActionPerformed


        createEnergyWatcher(selectedNodes.toArray());

    }//GEN-LAST:event_selNodesMonitEnergiaActionPerformed

    private void selNodeRunEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selNodeRunEventActionPerformed

        if (currentSelectedNode != null) {
            JMenuItem m = (JMenuItem) evt.getSource();
            if (networkRunning) {
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
    private void selNodesInstrumentsCoverageMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_selNodesInstrumentsCoverageMenuSelected
        boolean enable = CoverageController.getInstance().isEnable();
        selNodesInstrumentsCoverageSelSources.setEnabled(enable);
        selNodesInstrumentsCoverageEnable.setSelected(enable);
    }//GEN-LAST:event_selNodesInstrumentsCoverageMenuSelected

    protected boolean isMousePressed() {
        return pressedPoint != null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu currentSelectedNodePopupMenu;
    private javax.swing.JMenu depNodesDeploy;
    private javax.swing.JMenuItem depNodesGridTopology;
    private javax.swing.JMenuItem depNodesRandomTopology;
    private javax.swing.JPopupMenu deployNodesPopupMenu;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JCheckBoxMenuItem selNodeMarcar;
    private javax.swing.JMenuItem selNodeMonitEnergia;
    private javax.swing.JMenu selNodeMonitorizacao;
    private javax.swing.JCheckBoxMenuItem selNodeOnOff;
    private javax.swing.JMenu selNodeOperacao;
    private javax.swing.JMenuItem selNodeRunEvent;
    private javax.swing.JMenuItem selNodeShowProperties;
    private javax.swing.JCheckBoxMenuItem selNodeSink;
    private javax.swing.JCheckBoxMenuItem selNodeVerID;
    private javax.swing.JCheckBoxMenuItem selNodeVerOsQueMeConhecem;
    private javax.swing.JCheckBoxMenuItem selNodeVerVizinhos;
    private javax.swing.JMenu selNodeVisualizacao;
    private javax.swing.JMenu selNodesInstruments;
    private javax.swing.JMenu selNodesInstrumentsCoverage;
    private javax.swing.JCheckBoxMenuItem selNodesInstrumentsCoverageEnable;
    private javax.swing.JMenuItem selNodesInstrumentsCoverageSelSources;
    private javax.swing.JMenu selNodesInstrumentsReliability;
    private javax.swing.JCheckBoxMenuItem selNodesInstrumentsReliabilityEnable;
    private javax.swing.JCheckBoxMenuItem selNodesInstrumentsReliabilitySelSources;
    private javax.swing.JMenuItem selNodesMonitEnergia;
    private javax.swing.JMenu selNodesMonitorizacao;
    private javax.swing.JCheckBoxMenuItem selNodesOnOff;
    private javax.swing.JMenu selNodesOperacao;
    private javax.swing.JMenuItem selNodesRemove;
    private javax.swing.JMenuItem selNodesShowProperties;
    private javax.swing.JCheckBoxMenuItem selNodesVerID;
    private javax.swing.JCheckBoxMenuItem selNodesVerOsQueMeConhecem;
    private javax.swing.JCheckBoxMenuItem selNodesVerVizinhos;
    private javax.swing.JMenu selNodesVisualizacao;
    private javax.swing.JPopupMenu selectionToolPopupMenu;
    // End of variables declaration//GEN-END:variables

    private void paintSelectedArea(Graphics grphcs) {
        if (!canPaintSelectionArea()) {
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
        return OFFSET_NETWORK + (int) ((getSize().getWidth() - (OFFSET_NETWORK * 2)) * x / getSize().getWidth());
    }

    public int y2ScreenY(double y) {
        return OFFSET_NETWORK + (int) ((getSize().getHeight() - (OFFSET_NETWORK * 2)) * y / getSize().getHeight());
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

    public void viewVizinhos(boolean selected) {
        if (simulation.getSimulator() != null) {
            for (Node node : simulation.getSimulator().getNodes()) {
                ((SensorNode) node).setPaintNeighborhood(selected);
            }
            update();
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
        update();
    }

    protected void loadImage() throws Exception {
        //  GoogleMaps.setApiKey("ABQIAAAAXg0F4Zi2pcwHeCjCvk7LRhSoSGvlWgPKK04fS0Rib--DYJNIihQASC7FQc_5lQHTrrgdlZoWfL-eZg");
        //   double[] lanLng = GoogleMaps.geocodeAddress("Academia da Força Aérea, Portugal");
        // backImage = ImageViewer.toBufferedImage(GoogleMaps.retrieveStaticImage(1024, 600, 38.835780, -9.334334, 19, "png32"));
    }

    public Vector<GraphicNode> getSelectedNodes() {
        return selectedNodes;
    }

    private void paintMouseCoordinates(Graphics grphcs) {
        Color c = grphcs.getColor();
        grphcs.setColor(Color.BLACK);
        grphcs.drawString(mouseX + "," + mouseY, mouseX, mouseY);
        grphcs.setColor(c);
        update();
    }

    public void buildNetwork() {
        if (!networkDeployed) {
            return;
        }
        if (simulation.getSimulator() != null) {
            simulation.getSimulator().init();
            networkBuilded = true;
            PlatformView.getInstance().setSimulationNrNodes(getSimulation().getSimulator().getNodes().size());
            update();
        }
    }

    void viewOsQueConhecem(boolean selected) {
        if (simulation.getSimulator() != null) {
            for (Node node : simulation.getSimulator().getNodes()) {
                ((SensorNode) node).setPaintNeighborhoodOrg(selected);
            }
            update();
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
        if (simulation.getSimulator() != null) {
            if (networkDeployed) {
                node = searchForNode(intValue);
                if (node != null) {
                    node.getGraphicNode().mark();
                }
            }
            update();
        }
    }

    /**
     * 
     * @param selectedNodes
     */
    private void createEnergyWatcher(Object[] selectedNodes) {

        if (selectionTool || selectionPointerToolSelected) {
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

    public void onFinish(SimulatorEvent evt) {
        JOptionPane.showMessageDialog(this, "Simulation has terminated: " + evt.toString());
    }

    void deployNodesToolSelected(boolean selected) {
        deployNodeToolSelected = selected;
    }

    void setViewNodeInfo(boolean selected) {
        paintNodesInfo = selected;
    }

    void selectionPointerSelected(boolean selected) {
        this.selectionPointerToolSelected = selected;
    }

    void startSimulation() {
        if (!networkRunning) {
            simulation.start();
            networkRunning = true;
        } else {
            simulation.resume();
        }
    }

    void stopSimulation() {
        if (networkRunning) {
            simulation.stop();
        }

    }

    void pauseSimulation() {
        if (networkRunning) {
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
        if (v.size() == 0) {
            return;
        }

        NodePropertiesDialog dialog = new NodePropertiesDialog();
        dialog.showNodesProperties(v);
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
            }

        }
        GUI_Utils.mouseDefault(this);
        PlatformController.getInstance().getPlatformView().setSelectedNodes(selectedNodes.size() + "");
        update();

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

                        //double c = node.getBateryEnergy().getAverageConsumption();//.getLastConsume();//100 - (node.getBateryEnergy().getCurrentPower() * 100 / node.getBateryEnergy().getInitialPower());
                        double c = 100 - (node.getBateryEnergy().getCurrentPower() * 100 / node.getBateryEnergy().getInitialPower());
                        stat.addValue(c);
                    }
                    double x = NodesEnergyWatcher.this.nodes.get(0).getSimulator().getSimulationTimeInMillisec() / 1000;
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

    @Action
    public void deployNodesGridTopology() {
        JOptionPane.showMessageDialog(this, "Feature not implemented yet!", "", JOptionPane.WARNING_MESSAGE);
    }

    @Action(block = Task.BlockingScope.COMPONENT)
    public Task deployNodesUsingRandomTopology() {
        return new DeployNodesUsingRandomTopologyTask(org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class));
    }

    public class DeployNodesUsingRandomTopologyTask extends org.jdesktop.application.Task<Object, Void> {

        boolean ok = false;
        int nNodes = 0;
        RandomTopologyManager tm = new RandomTopologyManager();
        NodeFactory nf = simulation.getNodeFactory();
        int nRange = simulation.getNodeRange();
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
                String sNodes = JOptionPane.showInputDialog("Number of nodes to deploy:");
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
            try {
                GUI_Utils.mouseWait(SimulationPanel.this);
                this.setMessage("Generating nodes using factory");
                Vector<Node> nodes = (Vector<Node>) nf.createNodes(nNodes);
                nodes = tm.apply(deployArea, nodes);
                this.setProgress(status, 0, 1);



                for (Node node : nodes) {
                    status++;
                    node.getConfig().setSetRadioRange(nRange);
                    simulation.getNetwork().addNode((SensorNode) node);
                    this.setProgress(status, 0, nodes.size());
                }
            } catch (Exception ex) {
                GUI_Utils.mouseDefault(SimulationPanel.this);
                Logger.getLogger(SimulationPanel.class.getName()).log(Level.SEVERE, null, ex);
                networkDeployed = false;
                update();
                return false;
            }

            networkDeployed = true;
            update();
            this.setMessage("Building Network using radio");
            this.setProgress(0, 0, 1);
            GUI_Utils.mouseWait(SimulationPanel.this);
            buildNetwork();

            update();
            return true;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            setMessage("Building Network... done in " + (System.currentTimeMillis() - start) + " milliseconds");
            this.setProgress(1, 0, 1);
            GUI_Utils.mouseDefault(SimulationPanel.this);
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

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(BasicSimulation simulation) {
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

    public void mainPaintLoop(Graphics grphcs) {
        paintNetwork(grphcs);
        if (canPaintSelectionArea()) {
            paintSelectedArea(grphcs);
        }
        if (paintMouseCoordinates && visibleMouseCoordinates) {
            paintMouseCoordinates(grphcs);
        }

        if (paintNodesInfo) {
            paintInfo(grphcs);
        }
        if (getSimulation() != null) {
            if (getSimulator() != null) {
                GUI.showSimulationEvents(getSimulator().eventQueue.size());
            } else {
                GUI.showSimulationEvents(0);
            }
        }
    }

    private void paintImage(Graphics g) {
        if (backImage != null) {
            g.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void drawSelectedArea(java.awt.event.MouseEvent evt) {
        if (!canPaintSelectionArea()) {
            return;
        }
        selectedArea = new GeneralPath();
        selectedArea.moveTo(pressedPoint.x(), pressedPoint.y());
        selectedArea.lineTo(mouseX, pressedPoint.y());
        selectedArea.lineTo(mouseX, mouseY);
        selectedArea.lineTo(pressedPoint.x(), mouseY);
        selectedArea.lineTo(pressedPoint.x(), pressedPoint.y());
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
        return new RemoveNodesSelectedTask(org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class));
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
                    setMessage("Removing nodes...");
                    for (GraphicNode graphicNode : selectedNodes) {
                        Node n = graphicNode.getPhysicalNode();
                        getSimulator().getNodes().remove(n);
                    }
                    setMessage("Removing nodes... done!");
                    GUI.showSimulationNrOfNodes(getSimulator().getNodes().size());

                    networkDeployed = true;
                    update();
                    start = System.currentTimeMillis();
                    this.setMessage("Building Network using radio");
                    this.setProgress(0, 0, 1);
                    buildNetwork();

                    update();
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
            }

            update();
        }

    }
    protected Hashtable<Node, Object> energyControllersTable = new Hashtable<Node, Object>();
}
