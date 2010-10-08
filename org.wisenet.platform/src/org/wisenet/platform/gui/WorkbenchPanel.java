/*
 * WorkbenchPanel.java
 *
 * Created on Jan 23, 2010, 12:46:23 AM
 */
package org.wisenet.platform.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.xml.ws.Action;
import org.jdesktop.application.Task;
import org.wisenet.platform.common.ui.PlatformDialog;
import org.wisenet.platform.common.ui.PlatformFrame;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.gui.listeners.DeployEvent;
import org.wisenet.platform.gui.listeners.SimulationPanelEventListener;
import org.wisenet.platform.gui.panels.ImageViewerPanel;
import org.wisenet.platform.gui.panels.NodeSettingsPanel;
import org.wisenet.platform.gui.panels.RoutingInfoPanel;
import org.wisenet.platform.gui.panels.SimulationEnergyPanel;
import org.wisenet.platform.gui.panels.TestBuilderPanel;
import org.wisenet.platform.utils.GUI_Utils;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.simulation.listeners.SimulationEvent;

import org.wisenet.simulator.utilities.DebugConsole;
import org.wisenet.simulator.components.simulation.SimulationFactory;
import org.wisenet.simulator.components.simulation.listeners.SimulationListener;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class WorkbenchPanel extends javax.swing.JPanel implements SimulationPanelEventListener, SimulationListener {

    AbstractTest currentTest = null;

    /** Creates new form WorkbenchPanel */
    public WorkbenchPanel() {
        initComponents();

        simulationPanel1.addSimulationPanelListerner(this);
        jScrollPane1.setPreferredSize(new Dimension(1280, 800));
        jScrollPane1.setAutoscrolls(true);
        btnDeployNodesMode.setSelected(true);
        updateSelectionGroup();
    }

    private boolean executeNodeSearch() {
        String s = txtSearchNode.getText();
        if (!org.apache.commons.lang.StringUtils.isNumeric(s)) {
            return true;
        }
        if (s.isEmpty()) {
            return true;
        }
        if (s != null) {
            try {
                simulationPanel1.searchNode(Integer.valueOf(s).intValue());
            } catch (Exception e) {
                GUI_Utils.showException(e);
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SelectionBG = new javax.swing.ButtonGroup();
        OperationBG = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        simulationPanel1 = new org.wisenet.platform.gui.SimulationPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnRebuildNetwork = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        verVizinhos = new javax.swing.JToggleButton();
        verOsQueConhecem = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSimulationStart = new javax.swing.JToggleButton();
        btnSimulationPause = new javax.swing.JToggleButton();
        btnSimulationStop = new javax.swing.JToggleButton();
        btnSimulationReset = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        selectionPointerTool = new javax.swing.JToggleButton();
        btnDeployNodesMode = new javax.swing.JToggleButton();
        btnSelectionTool = new javax.swing.JToggleButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        showMouseCoordinates = new javax.swing.JToggleButton();
        viewNodesInfo = new javax.swing.JToggleButton();
        btnRoutingInfo = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        showDebugWindow = new javax.swing.JToggleButton();
        topToolbar = new javax.swing.JToolBar();
        btnSnapshot = new javax.swing.JButton();
        btnColorSettings = new javax.swing.JButton();
        gmaps = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        selRandomNodes = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cmdMonitEnergy = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        btnLoadTopology = new javax.swing.JButton();
        btnSaveTopology = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnStableMark = new javax.swing.JToggleButton();
        btnStableSelect = new javax.swing.JToggleButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        cmdSelAttackedNodes = new javax.swing.JButton();
        tgbRoutingAttackMode = new javax.swing.JToggleButton();
        cmdSelectAttack = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        cmdTest = new javax.swing.JButton();
        lblActiveTest = new javax.swing.JLabel();
        cmdResetTest = new javax.swing.JButton();
        cmdRunTest = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        searchPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtSearchNode = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        lblField = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout simulationPanel1Layout = new javax.swing.GroupLayout(simulationPanel1);
        simulationPanel1.setLayout(simulationPanel1Layout);
        simulationPanel1Layout.setHorizontalGroup(
            simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1088, Short.MAX_VALUE)
        );
        simulationPanel1Layout.setVerticalGroup(
            simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(simulationPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        btnRebuildNetwork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/refresh.png"))); // NOI18N
        btnRebuildNetwork.setFocusable(false);
        btnRebuildNetwork.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRebuildNetwork.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRebuildNetwork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRebuildNetworkActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRebuildNetwork);
        jToolBar1.add(jSeparator4);

        verVizinhos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Neighboors16.png"))); // NOI18N
        verVizinhos.setToolTipText("Show Neighborhood "); // NOI18N
        verVizinhos.setFocusable(false);
        verVizinhos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        verVizinhos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        verVizinhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verVizinhosActionPerformed(evt);
            }
        });
        jToolBar1.add(verVizinhos);

        verOsQueConhecem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/OthersNeighboor16.png"))); // NOI18N
        verOsQueConhecem.setToolTipText("Show Two Way Connecions");
        verOsQueConhecem.setFocusable(false);
        verOsQueConhecem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        verOsQueConhecem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        verOsQueConhecem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verOsQueConhecemActionPerformed(evt);
            }
        });
        jToolBar1.add(verOsQueConhecem);
        jToolBar1.add(jSeparator1);

        OperationBG.add(btnSimulationStart);
        btnSimulationStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Play16.png"))); // NOI18N
        btnSimulationStart.setToolTipText("Play Simulation");
        btnSimulationStart.setFocusable(false);
        btnSimulationStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSimulationStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimulationStartActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSimulationStart);

        OperationBG.add(btnSimulationPause);
        btnSimulationPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Pause16.png"))); // NOI18N
        btnSimulationPause.setToolTipText("Pause Simulation");
        btnSimulationPause.setFocusable(false);
        btnSimulationPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSimulationPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimulationPauseActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSimulationPause);

        OperationBG.add(btnSimulationStop);
        btnSimulationStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Stop16.png"))); // NOI18N
        btnSimulationStop.setToolTipText("Stop Simulation");
        btnSimulationStop.setFocusable(false);
        btnSimulationStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSimulationStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimulationStopActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSimulationStop);

        btnSimulationReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Reset16.png"))); // NOI18N
        btnSimulationReset.setToolTipText("Reset Simulation");
        btnSimulationReset.setFocusable(false);
        btnSimulationReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSimulationReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimulationResetActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSimulationReset);
        jToolBar1.add(jSeparator2);

        SelectionBG.add(selectionPointerTool);
        selectionPointerTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Pointer.png"))); // NOI18N
        selectionPointerTool.setToolTipText("Select Node");
        selectionPointerTool.setFocusable(false);
        selectionPointerTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectionPointerTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectionPointerTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionPointerToolActionPerformed(evt);
            }
        });
        jToolBar1.add(selectionPointerTool);

        SelectionBG.add(btnDeployNodesMode);
        btnDeployNodesMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/DEPLOY.png"))); // NOI18N
        btnDeployNodesMode.setToolTipText("Deploy Nodes Tool");
        btnDeployNodesMode.setFocusable(false);
        btnDeployNodesMode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeployNodesMode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeployNodesMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeployNodesModeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDeployNodesMode);

        SelectionBG.add(btnSelectionTool);
        btnSelectionTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Selection.png"))); // NOI18N
        btnSelectionTool.setToolTipText("Selection Area Tool");
        btnSelectionTool.setFocusable(false);
        btnSelectionTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSelectionTool.setMargin(null);
        btnSelectionTool.setMaximumSize(new java.awt.Dimension(20, 20));
        btnSelectionTool.setMinimumSize(new java.awt.Dimension(20, 20));
        btnSelectionTool.setPreferredSize(new java.awt.Dimension(20, 20));
        btnSelectionTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelectionTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectionToolActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSelectionTool);
        jToolBar1.add(jSeparator11);

        showMouseCoordinates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/MouseXY.png"))); // NOI18N
        showMouseCoordinates.setToolTipText("Show Mouse Coordinates");
        showMouseCoordinates.setFocusable(false);
        showMouseCoordinates.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showMouseCoordinates.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showMouseCoordinates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMouseCoordinatesActionPerformed(evt);
            }
        });
        jToolBar1.add(showMouseCoordinates);

        viewNodesInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/about-icon.png"))); // NOI18N
        viewNodesInfo.setToolTipText("View Nodes Info");
        viewNodesInfo.setFocusable(false);
        viewNodesInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        viewNodesInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        viewNodesInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewNodesInfoActionPerformed(evt);
            }
        });
        jToolBar1.add(viewNodesInfo);

        btnRoutingInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/routinginfo.png"))); // NOI18N
        btnRoutingInfo.setToolTipText("Show Routing Info");
        btnRoutingInfo.setFocusable(false);
        btnRoutingInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRoutingInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRoutingInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoutingInfoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRoutingInfo);
        jToolBar1.add(jSeparator6);

        showDebugWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/terminal-icon.png"))); // NOI18N
        showDebugWindow.setToolTipText("Debug Console");
        showDebugWindow.setFocusable(false);
        showDebugWindow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showDebugWindow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showDebugWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDebugWindowActionPerformed(evt);
            }
        });
        jToolBar1.add(showDebugWindow);

        add(jToolBar1, java.awt.BorderLayout.LINE_START);

        topToolbar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topToolbar.setFloatable(false);
        topToolbar.setRollover(true);

        btnSnapshot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/camera.png"))); // NOI18N
        btnSnapshot.setToolTipText("Take a network snapshot");
        btnSnapshot.setFocusable(false);
        btnSnapshot.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSnapshot.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSnapshot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSnapshotActionPerformed(evt);
            }
        });
        topToolbar.add(btnSnapshot);

        btnColorSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/ColorSettings.png"))); // NOI18N
        btnColorSettings.setToolTipText("Set color settings");
        btnColorSettings.setFocusable(false);
        btnColorSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColorSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnColorSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColorSettingsActionPerformed(evt);
            }
        });
        topToolbar.add(btnColorSettings);

        gmaps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/File-Pictures-icon.png"))); // NOI18N
        gmaps.setToolTipText("Load background image...");
        gmaps.setFocusable(false);
        gmaps.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gmaps.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gmaps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gmapsActionPerformed(evt);
            }
        });
        topToolbar.add(gmaps);
        topToolbar.add(jSeparator5);

        selRandomNodes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/RandomSelection.png"))); // NOI18N
        selRandomNodes.setToolTipText("Select random nodes...");
        selRandomNodes.setFocusable(false);
        selRandomNodes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selRandomNodes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selRandomNodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selRandomNodesActionPerformed(evt);
            }
        });
        topToolbar.add(selRandomNodes);
        topToolbar.add(jSeparator3);

        cmdMonitEnergy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Battery-icon.png"))); // NOI18N
        cmdMonitEnergy.setToolTipText("Observe Simulation Energy"); // NOI18N
        cmdMonitEnergy.setFocusable(false);
        cmdMonitEnergy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdMonitEnergy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdMonitEnergy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdMonitEnergyActionPerformed(evt);
            }
        });
        topToolbar.add(cmdMonitEnergy);
        topToolbar.add(jSeparator10);

        btnLoadTopology.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/loadTopology.png"))); // NOI18N
        btnLoadTopology.setToolTipText("Load network topology from file...");
        btnLoadTopology.setFocusable(false);
        btnLoadTopology.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLoadTopology.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLoadTopology.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadTopologyActionPerformed(evt);
            }
        });
        topToolbar.add(btnLoadTopology);

        btnSaveTopology.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/rsz_save-icon_(1).png"))); // NOI18N
        btnSaveTopology.setToolTipText("Save network topology to file...");
        btnSaveTopology.setFocusable(false);
        btnSaveTopology.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveTopology.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveTopology.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTopologyActionPerformed(evt);
            }
        });
        topToolbar.add(btnSaveTopology);
        topToolbar.add(jSeparator7);

        btnStableMark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/MarkStable.png"))); // NOI18N
        btnStableMark.setToolTipText("Mark Stable Nodes"); // NOI18N
        btnStableMark.setFocusable(false);
        btnStableMark.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStableMark.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStableMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStableMarkActionPerformed(evt);
            }
        });
        topToolbar.add(btnStableMark);

        btnStableSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/SelectStable.png"))); // NOI18N
        btnStableSelect.setToolTipText("Select Stable Nodes");
        btnStableSelect.setFocusable(false);
        btnStableSelect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStableSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStableSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStableSelectActionPerformed(evt);
            }
        });
        topToolbar.add(btnStableSelect);
        topToolbar.add(jSeparator8);

        cmdSelAttackedNodes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/attackNodes.png"))); // NOI18N
        cmdSelAttackedNodes.setToolTipText("Select Attacked Nodes");
        cmdSelAttackedNodes.setFocusable(false);
        cmdSelAttackedNodes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdSelAttackedNodes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdSelAttackedNodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSelAttackedNodesActionPerformed(evt);
            }
        });
        topToolbar.add(cmdSelAttackedNodes);

        tgbRoutingAttackMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/powerOff.png"))); // NOI18N
        tgbRoutingAttackMode.setToolTipText("Enable Routing Attack Mode");
        tgbRoutingAttackMode.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/powerON.png"))); // NOI18N
        tgbRoutingAttackMode.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/powerON.png"))); // NOI18N
        topToolbar.add(tgbRoutingAttackMode);

        cmdSelectAttack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/attack.png"))); // NOI18N
        cmdSelectAttack.setToolTipText("Select Routing Attack");
        cmdSelectAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSelectAttackActionPerformed(evt);
            }
        });
        topToolbar.add(cmdSelectAttack);

        jComboBox1.setMinimumSize(new java.awt.Dimension(200, 24));
        jComboBox1.setPreferredSize(new java.awt.Dimension(200, 24));
        topToolbar.add(jComboBox1);
        topToolbar.add(jSeparator9);

        cmdTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Test.png"))); // NOI18N
        cmdTest.setToolTipText("Config Test");
        cmdTest.setFocusable(false);
        cmdTest.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdTest.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTestActionPerformed(evt);
            }
        });
        topToolbar.add(cmdTest);

        lblActiveTest.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActiveTest.setLabelFor(cmdRunTest);
        lblActiveTest.setText("None");
        lblActiveTest.setToolTipText("Selected Test");
        lblActiveTest.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblActiveTest.setMaximumSize(new java.awt.Dimension(200, 25));
        lblActiveTest.setMinimumSize(new java.awt.Dimension(200, 25));
        lblActiveTest.setPreferredSize(new java.awt.Dimension(200, 25));
        topToolbar.add(lblActiveTest);

        cmdResetTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/clear.png"))); // NOI18N
        cmdResetTest.setToolTipText("Reset current test");
        cmdResetTest.setFocusable(false);
        cmdResetTest.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdResetTest.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdResetTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdResetTestActionPerformed(evt);
            }
        });
        topToolbar.add(cmdResetTest);

        cmdRunTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/runit.png"))); // NOI18N
        cmdRunTest.setToolTipText("Run Selected Test");
        cmdRunTest.setFocusable(false);
        cmdRunTest.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdRunTest.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdRunTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRunTestActionPerformed(evt);
            }
        });
        topToolbar.add(cmdRunTest);
        topToolbar.add(jSeparator12);

        searchPanel.setPreferredSize(new java.awt.Dimension(100, 25));
        searchPanel.setLayout(new java.awt.BorderLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Search16.png"))); // NOI18N
        jButton1.setToolTipText("Search a node by ID");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        searchPanel.add(jButton1, java.awt.BorderLayout.LINE_END);

        txtSearchNode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtSearchNode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSearchNode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchNodeKeyPressed(evt);
            }
        });
        searchPanel.add(txtSearchNode, java.awt.BorderLayout.CENTER);

        topToolbar.add(searchPanel);

        add(topToolbar, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(1137, 25));

        lblField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblField.setToolTipText("Covered field size");
        lblField.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblField.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblMessage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 378, Short.MAX_VALUE)
                .addComponent(lblField, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
            .addComponent(lblField, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void selectStableNodes(boolean select) {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            PlatformManager.getInstance().getActiveSimulation().selectNodes(select, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    return node.getRoutingLayer().isStable();
                }
            });
        }
    }

    private void verVizinhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verVizinhosActionPerformed

        simulationPanel1.viewVizinhos(verVizinhos.isSelected());
    }//GEN-LAST:event_verVizinhosActionPerformed

    private void btnSelectionToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectionToolActionPerformed
        updateSelectionGroup();
    }//GEN-LAST:event_btnSelectionToolActionPerformed

    private void verOsQueConhecemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verOsQueConhecemActionPerformed
        simulationPanel1.viewOsQueConhecem(verOsQueConhecem.isSelected());
    }//GEN-LAST:event_verOsQueConhecemActionPerformed

    private void showMouseCoordinatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMouseCoordinatesActionPerformed
        simulationPanel1.setPaintMouseCoordinates(showMouseCoordinates.isSelected());
    }//GEN-LAST:event_showMouseCoordinatesActionPerformed

    private void showDebugWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDebugWindowActionPerformed
        if (showDebugWindow.isSelected()) {
            DebugConsole.getInstance().setVisible(true);
        } else {
            DebugConsole.getInstance().setVisible(false);
        }
    }//GEN-LAST:event_showDebugWindowActionPerformed

    private void viewNodesInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewNodesInfoActionPerformed

        simulationPanel1.setViewNodeInfo(viewNodesInfo.isSelected());
    }//GEN-LAST:event_viewNodesInfoActionPerformed

    private void selectionPointerToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectionPointerToolActionPerformed
        updateSelectionGroup();
    }//GEN-LAST:event_selectionPointerToolActionPerformed

    private void gmapsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gmapsActionPerformed
        ImageViewerPanel ivp = new ImageViewerPanel();
        PlatformDialog f = PlatformDialog.display(ivp, "Background image", PlatformFrame.OKCANCEL_MODE);
        if (ivp.getImage() != null) {
            getSimulationPanel().updateImage(ivp.getImage(), ivp.strechImage());
        }

}//GEN-LAST:event_gmapsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (executeNodeSearch()) {
            return;
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnStableMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStableMarkActionPerformed
        markStableNodes(btnStableMark.isSelected());
    }//GEN-LAST:event_btnStableMarkActionPerformed

    private void btnStableSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStableSelectActionPerformed
        selectStableNodes(btnStableSelect.isSelected());
    }//GEN-LAST:event_btnStableSelectActionPerformed

    private void btnRoutingInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoutingInfoActionPerformed

        showRoutingInfo();
    }//GEN-LAST:event_btnRoutingInfoActionPerformed

    private void btnLoadTopologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadTopologyActionPerformed
        loadNetworkTopology();
    }//GEN-LAST:event_btnLoadTopologyActionPerformed

    private void btnSaveTopologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTopologyActionPerformed
        saveNetworkTopology();
    }//GEN-LAST:event_btnSaveTopologyActionPerformed

    private void txtSearchNodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchNodeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            executeNodeSearch();
        }

    }//GEN-LAST:event_txtSearchNodeKeyPressed

    private void selRandomNodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selRandomNodesActionPerformed
        RandomNodeSelection();
    }//GEN-LAST:event_selRandomNodesActionPerformed

    private void btnSnapshotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSnapshotActionPerformed
        TakeSnapshot();
    }//GEN-LAST:event_btnSnapshotActionPerformed

    private void btnColorSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColorSettingsActionPerformed
        ColorSettingsAction();
    }//GEN-LAST:event_btnColorSettingsActionPerformed

    private void btnRebuildNetworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRebuildNetworkActionPerformed
        RebuildNetwork();
    }//GEN-LAST:event_btnRebuildNetworkActionPerformed

    private void btnSimulationStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimulationStartActionPerformed
        StartSimulation();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimulationStartActionPerformed

    private void btnSimulationPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimulationPauseActionPerformed
        PauseSimulation();
    }//GEN-LAST:event_btnSimulationPauseActionPerformed

    private void btnSimulationStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimulationStopActionPerformed
        StopSimulation();
    }//GEN-LAST:event_btnSimulationStopActionPerformed

    private void btnSimulationResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimulationResetActionPerformed
        ResetSimulation();
    }//GEN-LAST:event_btnSimulationResetActionPerformed

    private void btnDeployNodesModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeployNodesModeActionPerformed
        selectedNodeDeployMode();
    }//GEN-LAST:event_btnDeployNodesModeActionPerformed

    private void cmdSelectAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSelectAttackActionPerformed
        selectRoutingAttack();
    }//GEN-LAST:event_cmdSelectAttackActionPerformed

    private void cmdSelAttackedNodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSelAttackedNodesActionPerformed
        selectAttackedNodes();
    }//GEN-LAST:event_cmdSelAttackedNodesActionPerformed

    private void cmdMonitEnergyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdMonitEnergyActionPerformed
        showSimulationEnergyPanel();
    }//GEN-LAST:event_cmdMonitEnergyActionPerformed

    private void cmdTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTestActionPerformed
        TestBuilderPanel tc = new TestBuilderPanel();
        tc.setSimulation((Simulation) getSimulationPanel().getSimulation());

        PlatformDialog d = PlatformDialog.display(tc, " Configure Routing Test", PlatformDialog.OKCANCEL_MODE);
        if (d.getStatus() == PlatformDialog.OK_STATUS) {
            getSimulationPanel().getSimulation().addTest((AbstractTest) tc.getResult());
            if (tc.getActivateNow().isSelected()) {
                this.currentTest = (AbstractTest) tc.getResult();
                lblActiveTest.setText(((AbstractTest) tc.getResult()).getName());
            }

        }
    }//GEN-LAST:event_cmdTestActionPerformed

    private void cmdRunTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRunTestActionPerformed
        runCurrentTest();
    }//GEN-LAST:event_cmdRunTestActionPerformed

    private void cmdResetTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResetTestActionPerformed
        resetCurrentTest();        // TODO add your handling code here:
    }//GEN-LAST:event_cmdResetTestActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup OperationBG;
    private javax.swing.ButtonGroup SelectionBG;
    private javax.swing.JButton btnColorSettings;
    private javax.swing.JToggleButton btnDeployNodesMode;
    private javax.swing.JButton btnLoadTopology;
    private javax.swing.JButton btnRebuildNetwork;
    private javax.swing.JButton btnRoutingInfo;
    private javax.swing.JButton btnSaveTopology;
    private javax.swing.JToggleButton btnSelectionTool;
    private javax.swing.JToggleButton btnSimulationPause;
    private javax.swing.JButton btnSimulationReset;
    private javax.swing.JToggleButton btnSimulationStart;
    private javax.swing.JToggleButton btnSimulationStop;
    private javax.swing.JButton btnSnapshot;
    private javax.swing.JToggleButton btnStableMark;
    private javax.swing.JToggleButton btnStableSelect;
    private javax.swing.JButton cmdMonitEnergy;
    private javax.swing.JButton cmdResetTest;
    private javax.swing.JButton cmdRunTest;
    private javax.swing.JButton cmdSelAttackedNodes;
    private javax.swing.JButton cmdSelectAttack;
    private javax.swing.JButton cmdTest;
    private javax.swing.JButton gmaps;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblActiveTest;
    private javax.swing.JLabel lblField;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JButton selRandomNodes;
    private javax.swing.JToggleButton selectionPointerTool;
    private javax.swing.JToggleButton showDebugWindow;
    private javax.swing.JToggleButton showMouseCoordinates;
    private org.wisenet.platform.gui.SimulationPanel simulationPanel1;
    private javax.swing.JToggleButton tgbRoutingAttackMode;
    private javax.swing.JToolBar topToolbar;
    private javax.swing.JFormattedTextField txtSearchNode;
    private javax.swing.JToggleButton verOsQueConhecem;
    private javax.swing.JToggleButton verVizinhos;
    private javax.swing.JToggleButton viewNodesInfo;
    // End of variables declaration//GEN-END:variables

    private void updateSelectionGroup() {
        simulationPanel1.deployNodesToolSelected(btnDeployNodesMode.isSelected());
        simulationPanel1.selectionToolSelected(btnSelectionTool.isSelected());
        simulationPanel1.selectionPointerSelected(selectionPointerTool.isSelected());

    }

    public void createSimulation(SimulationFactory simulationFactory) {
        simulationPanel1.createSimulation(simulationFactory);
    }

    public void StartSimulation() {

        try {
            if (getSimulationPanel().getSimulation().getSimulator().getNodes().size() > 0) {
                getSimulationPanel().startSimulation();
            } else {
                GUI_Utils.showWarningMessage("Cannot start simulation without nodes deployed");
                btnSimulationStart.setSelected(false);
            }
        } catch (Exception e) {
            GUI_Utils.showException(e);
        }
    }

    public void PauseSimulation() {
        try {
            if (getSimulationPanel().getSimulation().getSimulator().getNodes().size() > 0) {
                simulationPanel1.pauseSimulation();
            } else {
                GUI_Utils.showMessage("Cannot pause simulation without nodes deployed", JOptionPane.WARNING_MESSAGE);
                btnSimulationPause.setSelected(false);
            }
        } catch (Exception e) {
        }
    }

    public void StopSimulation() {
        try {
            if (getSimulationPanel().getSimulation().getSimulator().getNodes().size() > 0) {
                simulationPanel1.stopSimulation();
            } else {
                GUI_Utils.showMessage("Cannot stop simulation without nodes deployed", JOptionPane.WARNING_MESSAGE);
                btnSimulationStop.setSelected(false);
            }
        } catch (Exception e) {
        }

    }

    public void ResetSimulation() {
        try {
            if (getSimulationPanel().getSimulation().getSimulator().getNodes().size() > 0) {
                simulationPanel1.resetSimulation();
            }
            OperationBG.clearSelection();
        } catch (Exception e) {
        }
    }

    @Action
    public Task RebuildNetwork() {
        return new RebuildNetworkTask(org.jdesktop.application.Application.getInstance(org.wisenet.platform.PlatformApp.class));
    }

    public void buildSimulationNetwork() {
        getSimulationPanel().getSimulation().buildNetwork();
        simulationPanel1.updateDisplay();
    }

    public SimulationPanel getSimulationPanel() {
        return simulationPanel1;
    }

    private void markStableNodes(boolean mark) {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            PlatformManager.getInstance().getActiveSimulation().markStableNodes(mark, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    return node.getRoutingLayer().isStable();
                }
            });
        }

    }

    private void showRoutingInfo() {
        PlatformFrame.display(new RoutingInfoPanel(), "Routing Information", PlatformFrame.OK_MODE);
    }

    private void loadNetworkTopology() {
        try {
            String file = GUI_Utils.showOpenDialog(new FileFilter[]{GUI_Utils.XML()}, "Open Network Topology File");
            if (file != null) {
                String valid = Utilities.networkTopologyFileIsValid(file);
                if (valid != null) {
                    GUI_Utils.showWarningMessage(valid);
                } else {
                    GUI_Utils.mouseWait(this);
                    PlatformManager.getInstance().getActiveSimulation().loadNetworkTopology(file);
                    GUI_Utils.mouseDefault(this);
                    PlatformManager.getInstance().getActiveSimulation().buildNetwork();

                }
            }
        } catch (Exception ex) {
            GUI_Utils.mouseDefault(this);
            GUI_Utils.showException(ex);
        }
    }

    private void saveNetworkTopology() {
        try {
            String file = GUI_Utils.showSaveDialog(new FileFilter[]{GUI_Utils.XML()}, "Save Network Topology ");
            if (file != null) {
                GUI_Utils.mouseWait(this);
                PlatformManager.getInstance().getActiveSimulation().saveNetworkTopology(file);
                GUI_Utils.showMessage("Network Topology saved!");
                GUI_Utils.mouseDefault(this);
            }
        } catch (Exception ex) {
            GUI_Utils.mouseDefault(this);
            GUI_Utils.showException(ex);
        }

    }

    @Override
    public void afterNodeDeploy(DeployEvent event) {

        Dimension d = PlatformManager.getInstance().getActiveSimulation().fieldSize();
        lblField.setText(d.getWidth() + "-" + d.getHeight());
    }

    @Override
    public void beforeNodeDeploy(DeployEvent event) {
    }

    private void selectRoutingAttack() {
        List<Node> nodes = (List) getSimulationPanel().getSimulation().getSimulator().getNodes();

        for (Node node : nodes) {
            if (!node.getRoutingLayer().getAttacks().getAttacksList().isEmpty()) {
                node.getRoutingLayer().getAttacks().getAttacksList().getFirst().setEnable(true);
            }

        }
    }

    private void selectAttackedNodes() {
        getSimulationPanel().getSimulation().selectNodes(true, new NodeSelectionCondition() {

            @Override
            public boolean select(Node node) {
                return (node.getRoutingLayer().isUnderAttack());
            }
        });
        getSimulationPanel().updateLocal();
    }

    private void showSimulationEnergyPanel() {
        if (getSimulationPanel().getSimulation().isStarted()) {
            SimulationEnergyPanel sep = new SimulationEnergyPanel();
            sep.setValues(getSimulationPanel().getSimulation().getEnergyController().getDatabase().getNodesEnergy());
            PlatformFrame.display(sep, "Simulation Energy", PlatformFrame.NOACTIONS_MODE);
        }
    }

    @Override
    public void onStartFailure(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void beforeStart(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void afterStart(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void beforeStop(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onStopFailure(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void afterStop(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void beforeBuildNetwork(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void afterBuildNetwork(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onBuildNetworkFailure(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onEmptyQueue(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onNewSimulatorRound(SimulationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void resetCurrentTest() {
        if (currentTest != null) {
            currentTest = null;
            lblActiveTest.setText("None");
            showMessage("Reset current test done!");
        } else {
            showMessage("No current test to reset!");
        }
    }

    private void runCurrentTest() {
        if (currentTest == null) {
            return;
        }
        if (!getSimulationPanel().getSimulation().isStarted()) {
            return;
        }
        currentTest.execute();
    }

    private class RebuildNetworkTask extends org.jdesktop.application.Task<Object, Void> {

        RebuildNetworkTask(org.jdesktop.application.Application app) {
            super(app);
        }

        @Override
        protected Object doInBackground() {
            if (!getSimulationPanel().getSimulation().isStarted()) {
                long start = System.currentTimeMillis();
                setMessage("Building Network...");
                buildSimulationNetwork();
                setMessage("Building Network... done in " + (System.currentTimeMillis() - start) + " milliseconds");
            }
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
        }
    }

    public void selectedNodeDeployMode() {
        updateSelectionGroup();

    }

    public void RandomNodeSelection() {
        simulationPanel1.selectRandomNodes(10);
    }

    public void TakeSnapshot() {
        simulationPanel1.takeSnapshot();
    }

    public void ColorSettingsAction() {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            Simulation simulation = (Simulation) PlatformManager.getInstance().getActiveSimulation();
            if (!simulation.getSimulator().isEmpty()) {

                PlatformDialog pd = PlatformDialog.display(null, new NodeSettingsPanel(), "Color Settings", PlatformDialog.OKCANCELAPPLY_MODE);
                pd.dispose();
            } else {
                GUI_Utils.showWarningMessage("No sensor nodes added");
            }

        } else {
            GUI_Utils.showWarningMessage("No active simulation");
        }



    }

    public AbstractTest getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(AbstractTest currentTest) {
        this.currentTest = currentTest;
    }

    public boolean isNetworkDeployed() {
        return (getSimulationPanel().getSimulation().isNetworkDeployed());
    }

    public void showMessage(final String message) {
        lblMessage.setText(message);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    lblMessage.setText("");
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }
}
