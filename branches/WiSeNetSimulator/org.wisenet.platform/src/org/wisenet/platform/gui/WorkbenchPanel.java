/*
 * WorkbenchPanel.java
 *
 * Created on Jan 23, 2010, 12:46:23 AM
 */
package org.wisenet.platform.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.xml.ws.Action;
import org.jdesktop.application.Task;
import org.wisenet.platform.common.ui.PlatformDialog;
import org.wisenet.platform.common.ui.PlatformFrame;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.gui.listeners.DeployEvent;
import org.wisenet.platform.gui.listeners.SimulationPanelEventListener;
import org.wisenet.platform.gui.panels.AdHocTestParametersPanel;
import org.wisenet.platform.gui.panels.ImageViewerPanel;
import org.wisenet.platform.gui.panels.NodeSettingsPanel;
import org.wisenet.platform.gui.panels.RoutingInfoPanel;
import org.wisenet.platform.gui.panels.SimulationEnergyPanel;
import org.wisenet.platform.gui.panels.TestBuilderPanel;
import org.wisenet.platform.test.EnergyHeatMap;
import org.wisenet.platform.test.EnergyHeatMapFrame;
import org.wisenet.platform.utils.GUI_Utils;
import org.wisenet.platform.utils.PlatformUtils;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.evaluation.tests.AdHocTestInfo;
import org.wisenet.simulator.utilities.NodeSelectionCondition;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.simulation.listeners.SimulationEvent;
import org.wisenet.simulator.components.simulation.listeners.SimulationTestEvent;

import org.wisenet.simulator.components.simulation.SimulationFactory;
import org.wisenet.simulator.components.simulation.listeners.SimulationListener;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksEntry;
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
        initOutputWindow();
    }

    private void buildAdHocTest() {
        AdHocTestParametersPanel tc = new AdHocTestParametersPanel();
        if (currentTest != null) {
            tc.setCurrentTest(currentTest);
        }
        tc.setSimulation((Simulation) getSimulationPanel().getSimulation());
        PlatformDialog d = PlatformDialog.display(tc, " Configure AdHoc Routing Test", PlatformDialog.OKCANCEL_MODE);
        if (d.getStatus() == PlatformDialog.OK_STATUS) {
            getSimulationPanel().getSimulation().addTest((AbstractTest) tc.getResult());
            // activate now
            this.currentTest = (AbstractTest) tc.getResult();
            lblActiveTest.setText(((AbstractTest) tc.getResult()).getName());
            this.currentTest.setSimulation((Simulation) getSimulationPanel().getSimulation());
        }
    }

    private void buildTest() {
        TestBuilderPanel tc = new TestBuilderPanel();
        if (currentTest != null) {
            tc.setCurrentTest(currentTest);
        }
        tc.setSimulation((Simulation) getSimulationPanel().getSimulation());
        PlatformDialog d = PlatformDialog.display(tc, " Configure Routing Test", PlatformDialog.OKCANCEL_MODE);
        if (d.getStatus() == PlatformDialog.OK_STATUS) {
            getSimulationPanel().getSimulation().addTest((AbstractTest) tc.getResult());
            if (tc.getActivateNow().isSelected()) {
                this.currentTest = (AbstractTest) tc.getResult();
                lblActiveTest.setText(((AbstractTest) tc.getResult()).getName());
                this.currentTest.setSimulation((Simulation) getSimulationPanel().getSimulation());
            }
        }
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
        logActions = new javax.swing.JPopupMenu();
        mnuClear = new javax.swing.JMenuItem();
        mnuSave = new javax.swing.JMenuItem();
        logErrorActions = new javax.swing.JPopupMenu();
        mnuClearErrors = new javax.swing.JMenuItem();
        mnuSaveErrors = new javax.swing.JMenuItem();
        leftToolbar = new javax.swing.JToolBar();
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
        cmdHeatMapEnergy = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        btnLoadTopology = new javax.swing.JButton();
        btnSaveTopology = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnStableMark = new javax.swing.JToggleButton();
        btnStableSelect = new javax.swing.JToggleButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        cmdSelAttackedNodes = new javax.swing.JButton();
        cmdSelectAttack = new javax.swing.JButton();
        cboSimAttacks = new javax.swing.JComboBox();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        cmdTest = new javax.swing.JButton();
        lblActiveTest = new javax.swing.JLabel();
        cmdResetTest = new javax.swing.JButton();
        cmdRunTest = new javax.swing.JButton();
        testPrepare = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        searchPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtSearchNode = new javax.swing.JFormattedTextField();
        workbenchSplit = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        simulationPanel1 = new org.wisenet.platform.gui.SimulationPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        logPane = new javax.swing.JTextPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        logOutputPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        logOutputArea = new javax.swing.JTextArea();
        logOutputErrPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        logOutputErrorArea = new javax.swing.JTextArea();
        rightToolbar = new javax.swing.JToolBar();
        createAdhocTest = new javax.swing.JButton();
        jSeparator13 = new javax.swing.JToolBar.Separator();
        loadTestTopology = new javax.swing.JButton();
        saveTestTopology = new javax.swing.JButton();
        clearTestTopology = new javax.swing.JButton();
        jSeparator14 = new javax.swing.JToolBar.Separator();
        testAddRemoveSender = new javax.swing.JButton();
        testAddRemoveReceivers = new javax.swing.JButton();
        testSetAttackNode = new javax.swing.JButton();
        jSeparator15 = new javax.swing.JToolBar.Separator();
        adhocTestInfo = new javax.swing.JButton();

        mnuClear.setText("Clear");
        mnuClear.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClearActionPerformed(evt);
            }
        });
        logActions.add(mnuClear);

        mnuSave.setText("Save...");
        mnuSave.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveActionPerformed(evt);
            }
        });
        logActions.add(mnuSave);

        mnuClearErrors.setText("Clear");
        mnuClearErrors.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClearErrorsActionPerformed(evt);
            }
        });
        logErrorActions.add(mnuClearErrors);

        mnuSaveErrors.setText("Save...");
        mnuSaveErrors.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveErrorsActionPerformed(evt);
            }
        });
        logErrorActions.add(mnuSaveErrors);

        setLayout(new java.awt.BorderLayout());

        leftToolbar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        leftToolbar.setFloatable(false);
        leftToolbar.setOrientation(1);
        leftToolbar.setRollover(true);

        btnRebuildNetwork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/refresh.png"))); // NOI18N
        btnRebuildNetwork.setToolTipText("Rebuild Network"); // NOI18N
        btnRebuildNetwork.setFocusable(false);
        btnRebuildNetwork.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRebuildNetwork.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRebuildNetwork.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRebuildNetworkActionPerformed(evt);
            }
        });
        leftToolbar.add(btnRebuildNetwork);
        leftToolbar.add(jSeparator4);

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
        leftToolbar.add(verVizinhos);

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
        leftToolbar.add(verOsQueConhecem);
        leftToolbar.add(jSeparator1);

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
        leftToolbar.add(btnSimulationStart);

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
        leftToolbar.add(btnSimulationPause);

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
        leftToolbar.add(btnSimulationStop);

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
        leftToolbar.add(btnSimulationReset);
        leftToolbar.add(jSeparator2);

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
        leftToolbar.add(selectionPointerTool);

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
        leftToolbar.add(btnDeployNodesMode);

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
        leftToolbar.add(btnSelectionTool);
        leftToolbar.add(jSeparator11);

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
        leftToolbar.add(showMouseCoordinates);

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
        leftToolbar.add(viewNodesInfo);

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
        leftToolbar.add(btnRoutingInfo);
        leftToolbar.add(jSeparator6);

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
        leftToolbar.add(showDebugWindow);

        add(leftToolbar, java.awt.BorderLayout.LINE_START);

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

        cmdHeatMapEnergy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/heatMap.png"))); // NOI18N
        cmdHeatMapEnergy.setToolTipText("Show Energy Heat Map"); // NOI18N
        cmdHeatMapEnergy.setFocusable(false);
        cmdHeatMapEnergy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdHeatMapEnergy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdHeatMapEnergy.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHeatMap();
            }
        });
        topToolbar.add(cmdHeatMapEnergy);

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

        cmdSelectAttack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Attack.png"))); // NOI18N
        cmdSelectAttack.setToolTipText("Select Active Routing Attack"); // NOI18N
        cmdSelectAttack.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSelectAttackActionPerformed(evt);
            }
        });
        topToolbar.add(cmdSelectAttack);

        cboSimAttacks.setToolTipText("Simulation existing attacks ");
        cboSimAttacks.setMinimumSize(new java.awt.Dimension(200, 24));
        cboSimAttacks.setPreferredSize(new java.awt.Dimension(200, 24));
        topToolbar.add(cboSimAttacks);
        topToolbar.add(jSeparator9);

        cmdTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/CreateTests.png"))); // NOI18N
        cmdTest.setToolTipText("Test Builder");
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
        lblActiveTest.setToolTipText("Current Active Test");
        lblActiveTest.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblActiveTest.setMaximumSize(new java.awt.Dimension(175, 25));
        lblActiveTest.setMinimumSize(new java.awt.Dimension(175, 25));
        lblActiveTest.setPreferredSize(new java.awt.Dimension(200, 25));
        topToolbar.add(lblActiveTest);

        cmdResetTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/clear.png"))); // NOI18N
        cmdResetTest.setToolTipText("Reset current active test");
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
        cmdRunTest.setToolTipText("Run current active Test");
        cmdRunTest.setFocusable(false);
        cmdRunTest.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdRunTest.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdRunTest.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRunTestActionPerformed(evt);
            }
        });
        topToolbar.add(cmdRunTest);

        testPrepare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Rebuild.png"))); // NOI18N
        testPrepare.setToolTipText("Build Test"); // NOI18N
        testPrepare.setFocusable(false);
        testPrepare.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        testPrepare.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        testPrepare.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testPrepareActionPerformed(evt);
            }
        });
        topToolbar.add(testPrepare);
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

        workbenchSplit.setDividerLocation(1024);
        workbenchSplit.setDividerSize(4);
        workbenchSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        workbenchSplit.setAutoscrolls(true);
        workbenchSplit.setContinuousLayout(true);
        workbenchSplit.setOneTouchExpandable(true);

        javax.swing.GroupLayout simulationPanel1Layout = new javax.swing.GroupLayout(simulationPanel1);
        simulationPanel1.setLayout(simulationPanel1Layout);
        simulationPanel1Layout.setHorizontalGroup(
                simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1146, Short.MAX_VALUE));
        simulationPanel1Layout.setVerticalGroup(
                simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 498, Short.MAX_VALUE));

        jScrollPane1.setViewportView(simulationPanel1);

        workbenchSplit.setLeftComponent(jScrollPane1);

        logPane.setEditable(false);
        jScrollPane2.setViewportView(logPane);

        workbenchSplit.setRightComponent(jScrollPane2);

        jSplitPane1.setDividerLocation(600);
        jSplitPane1.setOneTouchExpandable(true);

        logOutputPanel.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Output Window");
        logOutputPanel.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        logOutputArea.setColumns(20);
        logOutputArea.setEditable(false);
        logOutputArea.setRows(5);
        logOutputArea.setComponentPopupMenu(logActions);
        jScrollPane3.setViewportView(logOutputArea);

        logOutputPanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(logOutputPanel);

        logOutputErrPanel.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Output Error Window");
        logOutputErrPanel.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        logOutputErrorArea.setColumns(20);
        logOutputErrorArea.setEditable(false);
        logOutputErrorArea.setForeground(new java.awt.Color(192, 36, 2));
        logOutputErrorArea.setRows(5);
        logOutputErrorArea.setComponentPopupMenu(logErrorActions);
        jScrollPane4.setViewportView(logOutputErrorArea);

        logOutputErrPanel.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(logOutputErrPanel);

        workbenchSplit.setRightComponent(jSplitPane1);

        add(workbenchSplit, java.awt.BorderLayout.CENTER);

        rightToolbar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        rightToolbar.setFloatable(false);
        rightToolbar.setOrientation(1);
        rightToolbar.setRollover(true);

        createAdhocTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/newTest.png"))); // NOI18N
        createAdhocTest.setToolTipText("Create Ad-Hoc Test");
        createAdhocTest.setFocusable(false);
        createAdhocTest.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createAdhocTest.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        createAdhocTest.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAdhocTestActionPerformed(evt);
            }
        });
        rightToolbar.add(createAdhocTest);
        rightToolbar.add(jSeparator13);

        loadTestTopology.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/File-Open-icon.png"))); // NOI18N
        loadTestTopology.setToolTipText("Load Ad-Hoc Test Topology");
        loadTestTopology.setFocusable(false);
        loadTestTopology.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadTestTopology.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadTestTopology.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTestTopologyActionPerformed(evt);
            }
        });
        rightToolbar.add(loadTestTopology);

        saveTestTopology.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/save-as-icon.png"))); // NOI18N
        saveTestTopology.setToolTipText("Save Ad-Hoc Test Topology");
        saveTestTopology.setFocusable(false);
        saveTestTopology.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveTestTopology.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveTestTopology.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTestTopologyActionPerformed(evt);
            }
        });
        rightToolbar.add(saveTestTopology);

        clearTestTopology.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/clearTest.png"))); // NOI18N
        clearTestTopology.setToolTipText("Clear Ad-Hoc Test Topology");
        clearTestTopology.setFocusable(false);
        clearTestTopology.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clearTestTopology.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clearTestTopology.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearTestTopologyActionPerformed(evt);
            }
        });
        rightToolbar.add(clearTestTopology);
        rightToolbar.add(jSeparator14);

        testAddRemoveSender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/SourceNode.png"))); // NOI18N
        testAddRemoveSender.setToolTipText("Add/Remove Source Sensors");
        testAddRemoveSender.setFocusable(false);
        testAddRemoveSender.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        testAddRemoveSender.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        testAddRemoveSender.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testAddRemoveSenderActionPerformed(evt);
            }
        });
        rightToolbar.add(testAddRemoveSender);

        testAddRemoveReceivers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/ReceiverNode.png"))); // NOI18N
        testAddRemoveReceivers.setToolTipText("Add/Remove Receiver Sensors");
        testAddRemoveReceivers.setFocusable(false);
        testAddRemoveReceivers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        testAddRemoveReceivers.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        testAddRemoveReceivers.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testAddRemoveReceiversActionPerformed(evt);
            }
        });
        rightToolbar.add(testAddRemoveReceivers);

        testSetAttackNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/powerOff.png"))); // NOI18N
        testSetAttackNode.setToolTipText("Add/Remove Attacked Sensors");
        testSetAttackNode.setFocusable(false);
        testSetAttackNode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        testSetAttackNode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        testSetAttackNode.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSetAttackNodeActionPerformed(evt);
            }
        });
        rightToolbar.add(testSetAttackNode);
        rightToolbar.add(jSeparator15);

        adhocTestInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/app-info-icon.png"))); // NOI18N
        adhocTestInfo.setToolTipText("Show Ad-Hoc Test Topology Info");
        adhocTestInfo.setFocusable(false);
        adhocTestInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        adhocTestInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        adhocTestInfo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adhocTestInfoActionPerformed(evt);
            }
        });
        rightToolbar.add(adhocTestInfo);

        add(rightToolbar, java.awt.BorderLayout.EAST);
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

    private void showDebugWindow() {
        showLogPane(showDebugWindow.isSelected());
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
        showDebugWindow();
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
        try {
            getSimulationPanel().getSimulation().buildNetwork();
        } catch (Exception e) {
        }

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
        buildTest();
    }//GEN-LAST:event_cmdTestActionPerformed

    private void cmdRunTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRunTestActionPerformed
        runCurrentTest();
    }//GEN-LAST:event_cmdRunTestActionPerformed

    private void cmdResetTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdResetTestActionPerformed
        resetCurrentTest();        // TODO add your handling code here:
    }//GEN-LAST:event_cmdResetTestActionPerformed

    private void testPrepareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testPrepareActionPerformed
        // TODO add your handling code here:
        prepareTest();
    }//GEN-LAST:event_testPrepareActionPerformed

    private void mnuClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClearActionPerformed
        clearLogArea();
    }//GEN-LAST:event_mnuClearActionPerformed

    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveActionPerformed
        saveLogArea();
    }//GEN-LAST:event_mnuSaveActionPerformed

    private void mnuClearErrorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClearErrorsActionPerformed
        clearLogErrorArea();
    }//GEN-LAST:event_mnuClearErrorsActionPerformed

    private void mnuSaveErrorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveErrorsActionPerformed
        saveLogErrorArea();
    }//GEN-LAST:event_mnuSaveErrorsActionPerformed

    private void testSetAttackNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testSetAttackNodeActionPerformed
        getSimulationPanel().setUnderAttackModeSelectedNodes();
    }//GEN-LAST:event_testSetAttackNodeActionPerformed

    private void testAddRemoveSenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testAddRemoveSenderActionPerformed
        getSimulationPanel().setSelectedNodesAsSources();
    }//GEN-LAST:event_testAddRemoveSenderActionPerformed

    private void testAddRemoveReceiversActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testAddRemoveReceiversActionPerformed
        getSimulationPanel().setSelectedNodesAsReceivers();
    }//GEN-LAST:event_testAddRemoveReceiversActionPerformed

    private void saveTestTopologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTestTopologyActionPerformed
        saveTestTopologyAction();
    }//GEN-LAST:event_saveTestTopologyActionPerformed

    private void loadTestTopologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTestTopologyActionPerformed
        loadTestTopologyAction();
    }//GEN-LAST:event_loadTestTopologyActionPerformed

    private void clearTestTopologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearTestTopologyActionPerformed
        clearTestTopologyAction();

    }//GEN-LAST:event_clearTestTopologyActionPerformed

    private void adhocTestInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adhocTestInfoActionPerformed
        showAdhocTestInfoAction();
    }//GEN-LAST:event_adhocTestInfoActionPerformed

    private void createAdhocTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAdhocTestActionPerformed
        createAdhocTestAction();
    }//GEN-LAST:event_createAdhocTestActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup OperationBG;
    private javax.swing.ButtonGroup SelectionBG;
    private javax.swing.JButton adhocTestInfo;
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
    private javax.swing.JComboBox cboSimAttacks;
    private javax.swing.JButton clearTestTopology;
    private javax.swing.JButton cmdMonitEnergy;
    private javax.swing.JButton cmdHeatMapEnergy;
    private javax.swing.JButton cmdResetTest;
    private javax.swing.JButton cmdRunTest;
    private javax.swing.JButton cmdSelAttackedNodes;
    private javax.swing.JButton cmdSelectAttack;
    private javax.swing.JButton cmdTest;
    private javax.swing.JButton createAdhocTest;
    private javax.swing.JButton gmaps;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator13;
    private javax.swing.JToolBar.Separator jSeparator14;
    private javax.swing.JToolBar.Separator jSeparator15;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar leftToolbar;
    private javax.swing.JToolBar rightToolbar;
    private javax.swing.JLabel lblActiveTest;
    private javax.swing.JButton loadTestTopology;
    private javax.swing.JPopupMenu logActions;
    private javax.swing.JPopupMenu logErrorActions;
    private javax.swing.JTextArea logOutputArea;
    private javax.swing.JPanel logOutputErrPanel;
    private javax.swing.JTextArea logOutputErrorArea;
    private javax.swing.JPanel logOutputPanel;
    private javax.swing.JTextPane logPane;
    private javax.swing.JMenuItem mnuClear;
    private javax.swing.JMenuItem mnuClearErrors;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JMenuItem mnuSaveErrors;
    private javax.swing.JButton saveTestTopology;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JButton selRandomNodes;
    private javax.swing.JToggleButton selectionPointerTool;
    private javax.swing.JToggleButton showDebugWindow;
    private javax.swing.JToggleButton showMouseCoordinates;
    private org.wisenet.platform.gui.SimulationPanel simulationPanel1;
    private javax.swing.JButton testAddRemoveReceivers;
    private javax.swing.JButton testAddRemoveSender;
    private javax.swing.JButton testPrepare;
    private javax.swing.JButton testSetAttackNode;
    private javax.swing.JToolBar topToolbar;
    private javax.swing.JFormattedTextField txtSearchNode;
    private javax.swing.JToggleButton verOsQueConhecem;
    private javax.swing.JToggleButton verVizinhos;
    private javax.swing.JToggleButton viewNodesInfo;
    private javax.swing.JSplitPane workbenchSplit;
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
                OperationBG.clearSelection();

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
                OperationBG.clearSelection();
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
                OperationBG.clearSelection();
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
        if (getSimulationPanel().getSimulation().isStarted()) {
            PlatformFrame.display(new RoutingInfoPanel(), "Routing Information", PlatformFrame.OK_MODE);
        }
    }

    private void loadNetworkTopology() {
        try {
            String file = GUI_Utils.showOpenDialog(new FileFilter[]{GUI_Utils.XML_FILTER()}, "Open Network Topology File");
            if (file != null) {
                String valid = Utilities.networkTopologyFileIsValid(file);
                if (valid != null) {
                    GUI_Utils.showWarningMessage(valid);
                } else {
                    GUI_Utils.mouseWait(this);
                    PlatformManager.getInstance().getActiveSimulation().loadNetworkTopology(file);
                    simulationPanel1.updateDisplay();
                    GUI_Utils.mouseDefault(this);
                    PlatformManager.getInstance().getActiveSimulation().buildNetwork();

                }
                updateSimulationInfo();
            }
        } catch (Exception ex) {
            GUI_Utils.mouseDefault(this);
            GUI_Utils.showException(ex);
        }
    }

    private void saveNetworkTopology() {
        try {
            String file = GUI_Utils.showSaveDialog(new FileFilter[]{GUI_Utils.XML_FILTER()}, "Save Network Topology ");
            if (file != null) {
                GUI_Utils.mouseWait(this);
                PlatformManager.getInstance().getActiveSimulation().saveNetworkTopology(file);
                showMessage("Network Topology saved!");
                GUI_Utils.mouseDefault(this);
            }
        } catch (Exception ex) {
            GUI_Utils.mouseDefault(this);
            GUI_Utils.showException(ex);
        }

    }

    @Override
    public void afterNodeDeploy(DeployEvent event) {
        updateSimulationInfo();
    }

    @Override
    public void beforeNodeDeploy(DeployEvent event) {
    }

    private void selectRoutingAttack() {
        List<Node> nodes = (List) getSimulationPanel().getSimulation().getSimulator().getNodes();
        for (Node node : nodes) {
            if (!node.getRoutingLayer().getAttacks().getAttacksList().isEmpty()) {
                AttacksEntry a = (AttacksEntry) cboSimAttacks.getModel().getSelectedItem();
                if (a != null) {
                    String c = a.getAttack().getClass().getName();
                    node.getRoutingLayer().getAttacks().enableAttack(c);
                }



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
        showMessage("Simulation start failed!");
    }

    @Override
    public void beforeStart(SimulationEvent event) {
        showMessage("Simulation starting...");

    }

    @Override
    public void afterStart(SimulationEvent event) {
        showMessage("Simulation started :)!");

    }

    @Override
    public void beforeStop(SimulationEvent event) {
        showMessage("Simulation about to stop...");

    }

    @Override
    public void onStopFailure(SimulationEvent event) {
        showMessage("Simulation stop failed!");

    }

    @Override
    public void afterStop(SimulationEvent event) {
        showMessage("Simulation stopped :)");

    }

    @Override
    public void beforeBuildNetwork(SimulationEvent event) {
        showMessage("Network building...");

    }

    @Override
    public void afterBuildNetwork(SimulationEvent event) {
        showMessage("Network building... done!");
    }

    @Override
    public void onBuildNetworkFailure(SimulationEvent event) {
        showMessage("Network building... failed!");
    }

    @Override
    public void onEmptyQueue(SimulationEvent event) {
        showMessage("No more events to handle!");
    }

    @Override
    public void onNewSimulatorRound(SimulationEvent event) {
        showMessage("Starting new simulation round!");
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
            showMessage("No active test to run!");
            return;
        }
        if (!getSimulationPanel().getSimulation().isStarted()) {
            showMessage("Simulation must have been started!");
            return;
        }
        if (currentTest.isPrepared()) {
            currentTest.execute();
        } else {
            currentTest.run();
        }
    }

    private void showFieldArea() {
        PlatformManager.getInstance().getPlatformView().updateSimulationFieldSize();
    }

    private void loadSimulationAttacks() {


        Object selected = cboSimAttacks.getSelectedItem();
        PlatformUtils.loadSimulationAttacksIntoCombo(cboSimAttacks);
        for (int i = 0; i < cboSimAttacks.getItemCount(); i++) {
            Object object = cboSimAttacks.getModel().getElementAt(i);
            if (object.equals(selected)) {
                cboSimAttacks.setSelectedIndex(i);
            }
            return;
        }

    }

    @Override
    public void afterTestExecution(SimulationTestEvent event) {
        AbstractTest t = (AbstractTest) event.getSource();
        String msg = "TestName: " + t.getName()
                + "\n\nNumber Of Messages Sent: " + t.getEvaluationManager().getMessageDatabase().getTotalNumberOfUniqueMessagesSent()
                + "\nNumber Of Messages Received: " + t.getEvaluationManager().getMessageDatabase().getTotalMessagesReceived()
                + "\nNumber Of Sender Nodes: " + t.getEvaluationManager().getMessageDatabase().getTotalSenderNodes()
                + "\nNumber Of Covered Nodes: " + t.getEvaluationManager().getMessageDatabase().getTotalCoveredNodes()
                + "\n\nTotal Energy Spent: " + t.getEvaluationManager().getEnergyDatabase().getTotalEnergySpent();

        GUI_Utils.showMessage(msg);
    }

    @Override
    public void startTestExecution(SimulationTestEvent event) {
        System.out.println("Starting test: " + ((AbstractTest) event.getSource()).getName());
    }

//    private void addSourcesToTest() {
//        Vector<GraphicNode> selNodes = null;
//        if (currentTest != null && !currentTest.isBatchMode()) {
//            if (getSimulationPanel().getSimulator().getNodes().size() > 0) {
//                selNodes = getSimulationPanel().getSelectedNodes();
//                if (selNodes != null) {
//                    for (GraphicNode graphicNode : selNodes) {
//                        Node n = graphicNode.getPhysicalNode();
//
//                        if (testAddSources.isSelected()) { // add
//                            if (!n.isSinkNode() && n.getRoutingLayer().isStable()) {
//                                if (!currentTest.getSourceNodes().contains(n)) {
//                                    currentTest.getSourceNodes().add(n);
//                                    n.setSource(true);
//                                }
//                            }
//                        } else {//remove
//                            n.setSource(false);
//                            currentTest.getSourceNodes().remove(n);
//
//                        }
//
//                    }
//                }
//            }
//        }
//        getSimulationPanel().updateLocal();
//    }
//    private void addReceiversToTest() {
//    }
    private void prepareTest() {
        if (currentTest == null) {
            showMessage("No active test to run!");
            return;
        }
        if (!getSimulationPanel().getSimulation().isStarted()) {
            showMessage("Simulation must have been started!");
            return;
        }
        if (getSimulationPanel().getSimulator().getNodes().size() > 0) {
            currentTest.prepare();
        } else {
            showMessage("No nodes deployed!");
            return;
        }

    }

    private void showLogPane(boolean selected) {
        if (!selected) {
            workbenchSplit.setDividerLocation(.99);
        } else {
            workbenchSplit.setDividerLocation(.8);
        }
    }

    private void initOutputWindow() {
        redirectSystemStreams();
    }

    private void clearLogArea() {
        logOutputArea.setText("");
    }

    private void saveLogArea() {
        String output = "";
        output = logOutputArea.getText();
        if (output.trim().length() > 0) {
            saveOutputToFile(output);
        }

    }

    private void saveOutputToFile(String output) {
        try {
            String f = GUI_Utils.showSaveDialog(new FileFilter[]{}, "Save output");
            if (f != null) {
                FileWriter fw = new FileWriter(f);
                fw.write(output);
                fw.flush();
                GUI_Utils.showinfoMessage("File saved!");
            }
        } catch (IOException ex) {
            GUI_Utils.showException(ex);
        }

    }

    private void saveLogErrorArea() {
        String output = "";
        output = logOutputErrorArea.getText();
        if (output.trim().length() > 0) {
            saveOutputToFile(output);
        }
    }

    private void clearLogErrorArea() {
        logOutputErrorArea.setText("");
    }

    private void saveTestTopologyAction() {
        try {
            String file = GUI_Utils.showSaveDialog(new FileFilter[]{GUI_Utils.TTO_FILTER()}, "Save Test Topology ");
            if (file != null) {
                GUI_Utils.mouseWait(this);
                if (!file.endsWith(".tto")) {
                    file += ".tto";
                }
                PlatformManager.getInstance().getActiveSimulation().saveTestTopology(file);

                showMessage("Test Topology saved!");
                GUI_Utils.mouseDefault(this);
            }
        } catch (Exception ex) {
            GUI_Utils.mouseDefault(this);
            GUI_Utils.showException(ex);
        }

    }

    private void loadTestTopologyAction() {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            if (PlatformManager.getInstance().getActiveSimulation().isNetworkDeployed()) {
                try {
                    String file = GUI_Utils.showOpenDialog(new FileFilter[]{GUI_Utils.TTO_FILTER()}, "Open Test Topology File");
                    if (file != null) {
                        String valid = Utilities.testTopologyFileIsValid(file);
                        if (valid != null) {
                            GUI_Utils.showWarningMessage(valid);
                        } else {
                            GUI_Utils.mouseWait(this);
                            PlatformManager.getInstance().getActiveSimulation().loadTestTopology(file);
                            simulationPanel1.updateDisplay();
                            GUI_Utils.mouseDefault(this);
                            PlatformManager.getInstance().getActiveSimulation().buildNetwork();

                        }
                        updateSimulationInfo();
                    }
                } catch (Exception ex) {
                    GUI_Utils.mouseDefault(this);
                    GUI_Utils.showException(ex);
                }

            }
        }
    }

    private void clearTestTopologyAction() {
        try {

            Collection<Node> nodes = null;
            if (PlatformManager.getInstance().haveActiveSimulation()) {
                if (PlatformManager.getInstance().getActiveSimulation().isNetworkDeployed()) {
                    GUI_Utils.mouseWait(this);
                    nodes = PlatformManager.getInstance().getActiveSimulation().getSimulator().getNodes();
                    for (Node node : nodes) {
                        node.setSource(false);
                        node.setReceiver(false);
                        node.getRoutingLayer().setUnderAttack(false);
                    }
                }
            }

        } catch (Exception e) {
            GUI_Utils.showException(e);
        } finally {
            updateSimulationInfo();
            getSimulationPanel().updateLocal();
            GUI_Utils.mouseDefault(this);
        }

    }

    private void showAdhocTestInfoAction() {
        String message = AdHocTestInfo.getInstance().retrieveInfo(getSimulationPanel().getSimulation()).toString();
        GUI_Utils.showMessage(message);
    }

    private void createAdhocTestAction() {
        buildAdHocTest();

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
        PlatformManager.getInstance().getPlatformView().showMessage(message);


    }

    public void updateSimulationInfo() {
        showFieldArea();
        loadSimulationAttacks();
        PlatformManager.getInstance().getPlatformView().updateAverageNeighborsPerNode();
        PlatformManager.getInstance().getPlatformView().updateSelectedNodes("" + getSimulationPanel().getSelectedNodes().size());
        PlatformManager.getInstance().getPlatformView().updateSimulationNrNodes(getSimulationPanel().getSimulator().getNodes().size());



    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }

            private void updateTextArea(final String s) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        logOutputArea.append(s);
                    }
                });

            }
        };

        OutputStream err = new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                updateTextAreaErr(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextAreaErr(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }

            private void updateTextAreaErr(final String s) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        logOutputErrorArea.append(s);
                    }
                });

            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(err, true));
    }

    void showHeatMap() {
        try {
            EnergyHeatMap hm = new EnergyHeatMap();
            BufferedImage i = getSimulationPanel().getNetworkImage();
            EnergyHeatMapFrame ehmf = new EnergyHeatMapFrame();





            if (i != null) {
                ehmf.loadImage(i);
                //                btnSnapshotActionPerformed(evt);
                ehmf.displayHeat(getSimulationPanel().getSimulator().getNodes());
                ehmf.setVisible(true);

            }
        } catch (IOException ex) {
            Logger.getLogger(WorkbenchPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
