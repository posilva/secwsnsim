/*
 * PlatformView.java
 */
package org.wisenet.platform;

import javax.swing.*;
import java.awt.Dimension;
import java.util.EventObject;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.Timer;
import java.util.TimerTask;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application.ExitListener;
import org.jdesktop.application.ResourceMap;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.core.instruments.coverage.CoverageInstrumentControlPanel;
import org.wisenet.platform.core.instruments.reliability.ReliabilityInstrumentControlPanel;
import org.wisenet.platform.gui.WorkbenchPanel;
import org.wisenet.platform.gui.listeners.DeployEvent;
import org.wisenet.platform.gui.panels.ApplicationOutputPanel;
import org.wisenet.platform.common.ui.PlatformDialog;
import org.wisenet.platform.common.ui.PlatformFrame;
import org.wisenet.platform.gui.panels.SimulationWizardPanel;
import org.wisenet.platform.gui.listeners.SimulationPanelEventListener;
import org.wisenet.platform.gui.panels.EnergyEvaluationPanel;
import org.wisenet.platform.gui.panels.RoutingOutputPanel;
import org.wisenet.platform.gui.panels.SimulationPropertiesPanel;
import org.wisenet.platform.gui.panels.TestResultsPanel;
import org.wisenet.platform.utils.PlatformUtils;
import org.wisenet.platform.utils.gui.ClockCounter;
import org.wisenet.platform.utils.GUI_Utils;
import org.wisenet.platform.utils.gui.IClockDisplay;
import org.wisenet.simulator.components.evaluation.tests.AbstractTest;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.components.instruments.coverage.CoverageInstrument;
import org.wisenet.simulator.components.instruments.coverage.CoverageListener;
import org.wisenet.simulator.components.instruments.coverage.listeners.SignalUpdateEvent;
import org.wisenet.simulator.components.simulation.Simulation;
import org.wisenet.simulator.components.simulation.listeners.SimulationEvent;
import org.wisenet.simulator.components.simulation.listeners.SimulationListener;
import org.wisenet.simulator.components.simulation.listeners.SimulationTestEvent;
import org.wisenet.simulator.core.Simulator;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.utilities.Utilities;
import org.wisenet.simulator.utilities.listeners.ExceptionEvent;
import org.wisenet.simulator.utilities.listeners.ExceptionListener;

/**
 * The application's main frame.
 */
public class PlatformView extends FrameView implements ExitListener, IClockDisplay, CoverageListener, SimulationListener, SimulationPanelEventListener, ExceptionListener {

    private boolean workbenchVisible;
    protected ClockCounter clockCounter;
    private java.util.Timer simulationTimer;

    public PlatformView(SingleFrameApplication app) {
        super(app);
        initComponents();
        Utilities.addExceptionListener(this);


        getComponent().setVisible(false);
        PlatformManager.getInstance().setPlatformView(this);
        clockCounter = new ClockCounter();
        clockCounter.setDisplay(this);
        getApplication().addExitListener(this);
        ResourceMap resourceMap = getResourceMap();

        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");

        messageTimer = new Timer(messageTimeout, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        applyLookAndFeel();
//        getMenuBar().add(LookAndFeelPrefs.createLookAndFeelMenu(this.getClass(), new ActionListener() {
//
//            public void actionPerformed(ActionEvent arg0) {
//                SwingUtilities.updateComponentTreeUI(PlatformView.this.getFrame());
//            }
//        }));

        workbenchPanel1.setVisible(false);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = PlatformApp.getApplication().getMainFrame();
            aboutBox = new PlatformAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        PlatformApp.getApplication().show(aboutBox);
    }

    public void showWorkbench() {
        workbenchVisible = true;
        workbenchPanel1.setVisible(true);
        mainPanel.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        workbenchPanel1 = new org.wisenet.platform.gui.WorkbenchPanel();
        mainToolbar = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnProperties = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        lblRadioCoverageValue = new javax.swing.JLabel();
        lblRoutingCoverageValue = new javax.swing.JLabel();
        lblAverageNeighborsPerNode = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu simulationMenu = new javax.swing.JMenu();
        menuNewSimulation = new javax.swing.JMenuItem();
        menuOpenSImulation = new javax.swing.JMenuItem();
        menuSaveSimulation = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        simPropertiesSubMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        ViewMenu = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        chkRoutingLayerDebug = new javax.swing.JCheckBoxMenuItem();
        chkMACLayerDebug = new javax.swing.JCheckBoxMenuItem();
        Output = new javax.swing.JMenu();
        viewApplicationOutput = new javax.swing.JMenuItem();
        viewRoutingOutput = new javax.swing.JMenuItem();
        viewInstruments = new javax.swing.JMenu();
        viewReliabilityControlPanel = new javax.swing.JMenuItem();
        viewCoverageControlPanel = new javax.swing.JMenuItem();
        viewEnergyControlPanel = new javax.swing.JMenuItem();
        viewLatencyControlPanel = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu5 = new javax.swing.JMenu();
        viewNodeInfo = new javax.swing.JMenuItem();
        evaluationMenu = new javax.swing.JMenu();
        evalSettingsMenu = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        statusAnimationLabel = new javax.swing.JLabel();
        statusMessageLabel = new javax.swing.JLabel();
        SimulationRealTime = new javax.swing.JLabel();
        NrSimulationNodes = new javax.swing.JLabel();
        SimulationStatus = new javax.swing.JLabel();
        NrEvents = new javax.swing.JLabel();
        SimulationTime = new javax.swing.JLabel();
        NrSelectedNodes = new javax.swing.JLabel();
        FieldSize = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jPanel2 = new javax.swing.JPanel();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.BorderLayout());
        mainPanel.add(workbenchPanel1, java.awt.BorderLayout.CENTER);

        mainToolbar.setFloatable(false);
        mainToolbar.setRollover(true);
        mainToolbar.setName("mainToolbar"); // NOI18N

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/new-icon.png"))); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(PlatformView.class);
        btnNew.setText(resourceMap.getString("btnNew.text")); // NOI18N
        btnNew.setToolTipText(resourceMap.getString("btnNew.toolTipText")); // NOI18N
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setName("btnNew"); // NOI18N
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        mainToolbar.add(btnNew);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/open-icon.png"))); // NOI18N
        btnOpen.setToolTipText("Open Existing Simulation"); // NOI18N
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setName("btnOpen"); // NOI18N
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        mainToolbar.add(btnOpen);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/save-icon.png"))); // NOI18N
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setToolTipText("Save Current Simulation"); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        mainToolbar.add(btnSave);

        btnProperties.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Panel-Setting-icon.png"))); // NOI18N
        btnProperties.setToolTipText("Simulation Settings"); // NOI18N
        btnProperties.setEnabled(workbenchPanel1.isVisible());
        btnProperties.setFocusable(false);
        btnProperties.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProperties.setName("btnProperties"); // NOI18N
        btnProperties.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropertiesActionPerformed(evt);
            }
        });
        mainToolbar.add(btnProperties);

        jSeparator7.setName("jSeparator7"); // NOI18N
        mainToolbar.add(jSeparator7);

        jPanel1.setEnabled(workbenchPanel1.isVisible());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(780, 25));

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 780, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 25, Short.MAX_VALUE)
        );

        mainToolbar.add(jPanel1);

        lblRadioCoverageValue.setFont(resourceMap.getFont("lblRadioCoverageValue.font")); // NOI18N
        lblRadioCoverageValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRadioCoverageValue.setText(resourceMap.getString("lblRadioCoverageValue.text")); // NOI18N
        lblRadioCoverageValue.setToolTipText(resourceMap.getString("lblRadioCoverageValue.toolTipText")); // NOI18N
        lblRadioCoverageValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblRadioCoverageValue.setMaximumSize(new java.awt.Dimension(21, 20));
        lblRadioCoverageValue.setMinimumSize(new java.awt.Dimension(21, 20));
        lblRadioCoverageValue.setName("lblRadioCoverageValue"); // NOI18N
        lblRadioCoverageValue.setPreferredSize(new java.awt.Dimension(100, 20));
        mainToolbar.add(lblRadioCoverageValue);
        lblRadioCoverageValue.getAccessibleContext().setAccessibleName(resourceMap.getString("lblRadioCoverageValue.AccessibleContext.accessibleName")); // NOI18N

        lblRoutingCoverageValue.setFont(resourceMap.getFont("lblRoutingCoverageValue.font")); // NOI18N
        lblRoutingCoverageValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRoutingCoverageValue.setText(resourceMap.getString("lblRoutingCoverageValue.text")); // NOI18N
        lblRoutingCoverageValue.setToolTipText(resourceMap.getString("lblRoutingCoverageValue.toolTipText")); // NOI18N
        lblRoutingCoverageValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblRoutingCoverageValue.setName("lblRoutingCoverageValue"); // NOI18N
        lblRoutingCoverageValue.setPreferredSize(new java.awt.Dimension(100, 20));
        mainToolbar.add(lblRoutingCoverageValue);

        lblAverageNeighborsPerNode.setFont(resourceMap.getFont("lblAverageNeighborsPerNode.font")); // NOI18N
        lblAverageNeighborsPerNode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAverageNeighborsPerNode.setText(resourceMap.getString("lblAverageNeighborsPerNode.text")); // NOI18N
        lblAverageNeighborsPerNode.setToolTipText(resourceMap.getString("lblAverageNeighborsPerNode.toolTipText")); // NOI18N
        lblAverageNeighborsPerNode.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblAverageNeighborsPerNode.setName("lblAverageNeighborsPerNode"); // NOI18N
        lblAverageNeighborsPerNode.setPreferredSize(new java.awt.Dimension(100, 20));
        mainToolbar.add(lblAverageNeighborsPerNode);

        mainPanel.add(mainToolbar, java.awt.BorderLayout.NORTH);

        menuBar.setName("menuBar"); // NOI18N

        simulationMenu.setText(resourceMap.getString("simulationMenu.text")); // NOI18N
        simulationMenu.setName("simulationMenu"); // NOI18N

        menuNewSimulation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/new-icon.png"))); // NOI18N
        menuNewSimulation.setText("New"); // NOI18N
        menuNewSimulation.setToolTipText(resourceMap.getString("menuNewSimulation.toolTipText")); // NOI18N
        menuNewSimulation.setName("menuNewSimulation"); // NOI18N
        menuNewSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewSimulationActionPerformed(evt);
            }
        });
        simulationMenu.add(menuNewSimulation);

        menuOpenSImulation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuOpenSImulation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/open-icon.png"))); // NOI18N
        menuOpenSImulation.setMnemonic('O');
        menuOpenSImulation.setText("Open"); // NOI18N
        menuOpenSImulation.setName("menuOpenSImulation"); // NOI18N
        menuOpenSImulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenSImulationActionPerformed(evt);
            }
        });
        simulationMenu.add(menuOpenSImulation);

        menuSaveSimulation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/save-icon.png"))); // NOI18N
        menuSaveSimulation.setText(resourceMap.getString("menuSaveSimulation.text")); // NOI18N
        menuSaveSimulation.setToolTipText("Save Simulation"); // NOI18N
        menuSaveSimulation.setName("menuSaveSimulation"); // NOI18N
        menuSaveSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSaveSimulationActionPerformed(evt);
            }
        });
        simulationMenu.add(menuSaveSimulation);

        jSeparator3.setName("jSeparator3"); // NOI18N
        simulationMenu.add(jSeparator3);

        simPropertiesSubMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Panel-Setting-icon.png"))); // NOI18N
        simPropertiesSubMenu.setText("Properties"); // NOI18N
        simPropertiesSubMenu.setToolTipText("Show Simulation Properties"); // NOI18N
        simPropertiesSubMenu.setName("simPropertiesSubMenu"); // NOI18N
        simPropertiesSubMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simPropertiesSubMenuActionPerformed(evt);
            }
        });
        simulationMenu.add(simPropertiesSubMenu);

        jSeparator1.setName("jSeparator1"); // NOI18N
        simulationMenu.add(jSeparator1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(PlatformView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setIcon(resourceMap.getIcon("exitMenuItem.icon")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        simulationMenu.add(exitMenuItem);

        menuBar.add(simulationMenu);

        ViewMenu.setText(resourceMap.getString("ViewMenu.text")); // NOI18N
        ViewMenu.setName("ViewMenu"); // NOI18N

        jMenu3.setText(resourceMap.getString("jMenu3.text")); // NOI18N
        jMenu3.setName("jMenu3"); // NOI18N

        chkRoutingLayerDebug.setAction(actionMap.get("EnableRoutingLayerDebug")); // NOI18N
        chkRoutingLayerDebug.setSelected(true);
        chkRoutingLayerDebug.setText(resourceMap.getString("chkRoutingLayerDebug.text")); // NOI18N
        chkRoutingLayerDebug.setName("chkRoutingLayerDebug"); // NOI18N
        jMenu3.add(chkRoutingLayerDebug);

        chkMACLayerDebug.setAction(actionMap.get("EnableMacLayerDebug")); // NOI18N
        chkMACLayerDebug.setSelected(true);
        chkMACLayerDebug.setText(resourceMap.getString("chkMACLayerDebug.text")); // NOI18N
        chkMACLayerDebug.setName("chkMACLayerDebug"); // NOI18N
        jMenu3.add(chkMACLayerDebug);

        ViewMenu.add(jMenu3);

        Output.setText(resourceMap.getString("Output.text")); // NOI18N
        Output.setName("Output"); // NOI18N

        viewApplicationOutput.setAction(actionMap.get("ShowApplicationOutputPanelAction")); // NOI18N
        viewApplicationOutput.setText(resourceMap.getString("viewApplicationOutput.text")); // NOI18N
        viewApplicationOutput.setName("viewApplicationOutput"); // NOI18N
        Output.add(viewApplicationOutput);

        viewRoutingOutput.setAction(actionMap.get("ShowRoutingOutputPanelAction")); // NOI18N
        viewRoutingOutput.setText(resourceMap.getString("viewRoutingOutput.text")); // NOI18N
        viewRoutingOutput.setName("viewRoutingOutput"); // NOI18N
        Output.add(viewRoutingOutput);

        ViewMenu.add(Output);

        viewInstruments.setText(resourceMap.getString("viewInstruments.text")); // NOI18N
        viewInstruments.setName("viewInstruments"); // NOI18N

        viewReliabilityControlPanel.setAction(actionMap.get("viewReliabilityControlPanelAction")); // NOI18N
        viewReliabilityControlPanel.setText(resourceMap.getString("viewReliabilityControlPanel.text")); // NOI18N
        viewReliabilityControlPanel.setName("viewReliabilityControlPanel"); // NOI18N
        viewInstruments.add(viewReliabilityControlPanel);

        viewCoverageControlPanel.setAction(actionMap.get("viewCoverageControlPanelAction")); // NOI18N
        viewCoverageControlPanel.setText(resourceMap.getString("viewCoverageControlPanel.text")); // NOI18N
        viewCoverageControlPanel.setName("viewCoverageControlPanel"); // NOI18N
        viewInstruments.add(viewCoverageControlPanel);

        viewEnergyControlPanel.setAction(actionMap.get("viewEnergyControlPanelAction")); // NOI18N
        viewEnergyControlPanel.setText(resourceMap.getString("viewEnergyControlPanel.text")); // NOI18N
        viewEnergyControlPanel.setName("viewEnergyControlPanel"); // NOI18N
        viewInstruments.add(viewEnergyControlPanel);

        viewLatencyControlPanel.setAction(actionMap.get("viewLatencyControlPanelAction")); // NOI18N
        viewLatencyControlPanel.setText(resourceMap.getString("viewLatencyControlPanel.text")); // NOI18N
        viewLatencyControlPanel.setName("viewLatencyControlPanel"); // NOI18N
        viewInstruments.add(viewLatencyControlPanel);

        ViewMenu.add(viewInstruments);

        jSeparator2.setName("jSeparator2"); // NOI18N
        ViewMenu.add(jSeparator2);

        jMenu5.setText(resourceMap.getString("jMenu5.text")); // NOI18N
        jMenu5.setName("jMenu5"); // NOI18N

        viewNodeInfo.setText(resourceMap.getString("viewNodeInfo.text")); // NOI18N
        viewNodeInfo.setName("viewNodeInfo"); // NOI18N
        jMenu5.add(viewNodeInfo);

        ViewMenu.add(jMenu5);

        menuBar.add(ViewMenu);

        evaluationMenu.setText(resourceMap.getString("evaluationMenu.text")); // NOI18N
        evaluationMenu.setName("evaluationMenu"); // NOI18N

        evalSettingsMenu.setAction(actionMap.get("ShowEvaluationPanel")); // NOI18N
        evalSettingsMenu.setText(resourceMap.getString("evalSettingsMenu.text")); // NOI18N
        evalSettingsMenu.setName("evalSettingsMenu"); // NOI18N
        evaluationMenu.add(evalSettingsMenu);

        menuBar.add(evaluationMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N
        helpMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuActionPerformed(evt);
            }
        });

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(508, 20));
        statusPanel.setRequestFocusEnabled(false);

        progressBar.setName("progressBar"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        statusMessageLabel.setFont(resourceMap.getFont("statusMessageLabel.font")); // NOI18N
        statusMessageLabel.setText(resourceMap.getString("statusMessageLabel.text")); // NOI18N
        statusMessageLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        statusMessageLabel.setName("statusMessageLabel"); // NOI18N
        statusMessageLabel.setPreferredSize(new java.awt.Dimension(950, 12));

        SimulationRealTime.setBackground(resourceMap.getColor("SimulationRealTime.background")); // NOI18N
        SimulationRealTime.setFont(resourceMap.getFont("SimulationRealTime.font")); // NOI18N
        SimulationRealTime.setForeground(resourceMap.getColor("SimulationRealTime.foreground")); // NOI18N
        SimulationRealTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SimulationRealTime.setText(resourceMap.getString("SimulationRealTime.text")); // NOI18N
        SimulationRealTime.setToolTipText(resourceMap.getString("SimulationRealTime.toolTipText")); // NOI18N
        SimulationRealTime.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SimulationRealTime.setName("SimulationRealTime"); // NOI18N
        SimulationRealTime.setOpaque(true);

        NrSimulationNodes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NrSimulationNodes.setText(resourceMap.getString("NroNodes.text")); // NOI18N
        NrSimulationNodes.setToolTipText(resourceMap.getString("NroNodes.toolTipText")); // NOI18N
        NrSimulationNodes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        NrSimulationNodes.setName("NroNodes"); // NOI18N

        SimulationStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SimulationStatus.setText(resourceMap.getString("SimulationStatus.text")); // NOI18N
        SimulationStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SimulationStatus.setName("SimulationStatus"); // NOI18N

        NrEvents.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NrEvents.setText(resourceMap.getString("NrEvents.text")); // NOI18N
        NrEvents.setToolTipText(resourceMap.getString("NrEvents.toolTipText")); // NOI18N
        NrEvents.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        NrEvents.setName("NrEvents"); // NOI18N

        SimulationTime.setBackground(resourceMap.getColor("SimulationTime.background")); // NOI18N
        SimulationTime.setFont(resourceMap.getFont("SimulationTime.font")); // NOI18N
        SimulationTime.setForeground(resourceMap.getColor("SimulationTime.foreground")); // NOI18N
        SimulationTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SimulationTime.setText(resourceMap.getString("SimulationTime.text")); // NOI18N
        SimulationTime.setToolTipText(resourceMap.getString("SimulationTime.toolTipText")); // NOI18N
        SimulationTime.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SimulationTime.setName("SimulationTime"); // NOI18N
        SimulationTime.setOpaque(true);

        NrSelectedNodes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NrSelectedNodes.setText(resourceMap.getString("NrSelectedNodes.text")); // NOI18N
        NrSelectedNodes.setToolTipText(resourceMap.getString("NrSelectedNodes.toolTipText")); // NOI18N
        NrSelectedNodes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        NrSelectedNodes.setName("NrSelectedNodes"); // NOI18N

        FieldSize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FieldSize.setText(resourceMap.getString("FieldSize.text")); // NOI18N
        FieldSize.setToolTipText(resourceMap.getString("FieldSize.toolTipText")); // NOI18N
        FieldSize.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FieldSize.setName("FieldSize"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, statusPanelLayout.createSequentialGroup()
                .add(statusMessageLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 458, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(SimulationStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 115, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(2, 2, 2)
                .add(NrSimulationNodes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 111, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(NrSelectedNodes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 124, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(NrEvents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(SimulationRealTime)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(SimulationTime, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(FieldSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusAnimationLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        statusPanelLayout.linkSize(new java.awt.Component[] {SimulationRealTime, SimulationTime}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        statusPanelLayout.linkSize(new java.awt.Component[] {NrSelectedNodes, NrSimulationNodes}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, statusAnimationLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .add(FieldSize, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, statusPanelLayout.createSequentialGroup()
                        .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, statusMessageLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, NrSimulationNodes, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, NrSelectedNodes, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(NrEvents, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                .add(SimulationRealTime)
                                .add(SimulationTime)
                                .add(SimulationStatus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)))
                        .add(1, 1, 1)))
                .addContainerGap())
        );

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N
        jPopupMenu1.add(jMenu2);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        jPopupMenu1.add(jMenu1);

        jPanel2.setName("jPanel2"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        setToolBar(mainToolbar);
    }// </editor-fold>//GEN-END:initComponents

    private void helpMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_helpMenuActionPerformed

    private void btnPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropertiesActionPerformed
        ShowSimulationProperties();
    }//GEN-LAST:event_btnPropertiesActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        SaveSimulation();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        OpenSimulation();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        newSimulation();        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewActionPerformed

    private void menuNewSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewSimulationActionPerformed
        newSimulation();        // TODO add your handling code here:
    }//GEN-LAST:event_menuNewSimulationActionPerformed

    private void menuOpenSImulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpenSImulationActionPerformed
        OpenSimulation();
    }//GEN-LAST:event_menuOpenSImulationActionPerformed

    private void menuSaveSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveSimulationActionPerformed
        SaveSimulation();        // TODO add your handling code here:
    }//GEN-LAST:event_menuSaveSimulationActionPerformed

    private void simPropertiesSubMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simPropertiesSubMenuActionPerformed
        ShowSimulationProperties();
    }//GEN-LAST:event_simPropertiesSubMenuActionPerformed

    @Action
    public void newSimulation() {
        SimulationWizardPanel sw = new SimulationWizardPanel();
        PlatformDialog.display(sw, "Create a simulation", PlatformFrame.OKCANCEL_MODE);
        boolean status = sw.isOk();
        if (status) {
            resetWorkbench();
            mainPanel.add(workbenchPanel1, java.awt.BorderLayout.CENTER);
            workbenchPanel1.getSimulationPanel().initSimulation(sw.getSettings());
        }
        if (status) {
            if (PlatformManager.getInstance().getActiveSimulation() != null) {
                workbenchPanel1.getSimulationPanel().addSimulationPanelListerner(this);
                ((Simulation) PlatformManager.getInstance().getActiveSimulation()).addSimulationListener(this);
                getComponent().setVisible(true);
                showWorkbench();
                PlatformManager.getInstance().setNewSimulation(true);
                PlatformManager.getInstance().showSimulationName("");
                PlatformManager.getInstance().getActiveSimulation().getCoverageInstrument().updateNetworkSize();
                ((CoverageInstrument) getCoverageInstrument()).addCoverageListener(this);
            }
        }
    }

    private void resetWorkbench() {
        workbenchPanel1.setVisible(false);
        workbenchPanel1 = null;
        workbenchPanel1 = new WorkbenchPanel();
        clockCounter.stop();
        updateSelectedNodes("0");
        updateSimulationEvents(0);
        setSimulationTime("0");
        updateSimulationNrNodes(0);
        updateAverageNeighborsPerNode();
        updateClock("00:00:00");
        updateSimulationFieldSize();
        updateSimulationState("DEPLOY");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel FieldSize;
    protected javax.swing.JLabel NrEvents;
    protected javax.swing.JLabel NrSelectedNodes;
    protected javax.swing.JLabel NrSimulationNodes;
    protected javax.swing.JMenu Output;
    protected javax.swing.JLabel SimulationRealTime;
    protected javax.swing.JLabel SimulationStatus;
    protected javax.swing.JLabel SimulationTime;
    protected javax.swing.JMenu ViewMenu;
    protected javax.swing.JButton btnNew;
    protected javax.swing.JButton btnOpen;
    protected javax.swing.JButton btnProperties;
    protected javax.swing.JButton btnSave;
    protected javax.swing.JCheckBoxMenuItem chkMACLayerDebug;
    protected javax.swing.JCheckBoxMenuItem chkRoutingLayerDebug;
    protected javax.swing.JMenuItem evalSettingsMenu;
    protected javax.swing.JMenu evaluationMenu;
    protected javax.swing.JMenu jMenu1;
    protected javax.swing.JMenu jMenu2;
    protected javax.swing.JMenu jMenu3;
    protected javax.swing.JMenu jMenu5;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel2;
    protected javax.swing.JPopupMenu jPopupMenu1;
    protected javax.swing.JPopupMenu.Separator jSeparator1;
    protected javax.swing.JPopupMenu.Separator jSeparator2;
    protected javax.swing.JPopupMenu.Separator jSeparator3;
    protected javax.swing.JToolBar.Separator jSeparator7;
    protected javax.swing.JLabel lblAverageNeighborsPerNode;
    protected javax.swing.JLabel lblRadioCoverageValue;
    protected javax.swing.JLabel lblRoutingCoverageValue;
    protected javax.swing.JPanel mainPanel;
    protected javax.swing.JToolBar mainToolbar;
    protected javax.swing.JMenuBar menuBar;
    protected javax.swing.JMenuItem menuNewSimulation;
    protected javax.swing.JMenuItem menuOpenSImulation;
    protected javax.swing.JMenuItem menuSaveSimulation;
    private javax.swing.JProgressBar progressBar;
    protected javax.swing.JMenuItem simPropertiesSubMenu;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    protected javax.swing.JPanel statusPanel;
    protected javax.swing.JMenuItem viewApplicationOutput;
    protected javax.swing.JMenuItem viewCoverageControlPanel;
    protected javax.swing.JMenuItem viewEnergyControlPanel;
    protected javax.swing.JMenu viewInstruments;
    protected javax.swing.JMenuItem viewLatencyControlPanel;
    protected javax.swing.JMenuItem viewNodeInfo;
    protected javax.swing.JMenuItem viewReliabilityControlPanel;
    protected javax.swing.JMenuItem viewRoutingOutput;
    protected org.wisenet.platform.gui.WorkbenchPanel workbenchPanel1;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

    public void showCoverage(String value) {
    }

    public boolean isWorkbenchVisible() {
        return workbenchVisible;
    }

    public void setWorkbenchVisible(boolean workbenchVisible) {
        this.workbenchVisible = workbenchVisible;
    }

    public void updateSimulationNrNodes(int value) {
        NrSimulationNodes.setText(value + " nodes");

    }

    public void setSimulationTime(String value) {
        SimulationRealTime.setText(value);
    }

    public void updateSelectedNodes(String value) {
        NrSelectedNodes.setText(value + " selected");
    }

    public void setStatusMessage(String message) {
        statusMessageLabel.setText(message);
    }

    public void updateSimulationEvents(int value) {
        NrEvents.setText("" + value);
    }

    public void updateSimulationFieldSize() {
        if (!isActiveSimulationValid()) {
            return;
        }
        Dimension d = PlatformManager.getInstance().getActiveSimulation().fieldSize();
        FieldSize.setText("" + d.getWidth() + " , " + d.getHeight());
    }

    public void showLogMessage(String message) {
    }

    public void updateSimulationState(String state) {
        this.SimulationStatus.setText(state.toUpperCase());
    }

    @Override
    public boolean canExit(EventObject event) {
        boolean return_value = GUI_Utils.confirm("Confirm Simulation Platform Exit?");
        if (return_value) {
            if (isActiveSimulationValid()) {
                PlatformManager.getInstance().getActiveSimulation().exitPlatform();
            }
        }
        return return_value;
    }

    public void updateClock(String time) {
        SimulationRealTime.setText(time);
    }

    @Action
    public void SaveSimulation() {
        if (isActiveSimulationValid()) {
            PlatformUtils.saveSimulation(PlatformManager.getInstance().getActiveSimulation());
        }
    }

    @Action
    public void ShowRadioCoverageValue() {
        double value = ((CoverageInstrument) getCoverageInstrument()).getCoverageValueByModel(CoverageInstrument.CoverageModelEnum.RADIO);
        JOptionPane.showMessageDialog(this.getFrame(), value);
    }

    public void onSignalUpdate(SignalUpdateEvent event) {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            if (event.getModel() == CoverageInstrument.CoverageModelEnum.RADIO) {
            } else if (event.getModel() == CoverageInstrument.CoverageModelEnum.ROUTING) {
                lblRoutingCoverageValue.setText("" + ((CoverageInstrument) getCoverageInstrument()).getCoverageValueByModel(CoverageInstrument.CoverageModelEnum.ROUTING) + "%");
            }
        }
    }

    public void updateAverageNeighborsPerNode() {
        if (isActiveSimulationValid()) {
            lblAverageNeighborsPerNode.setText("" + PlatformManager.getInstance().getActiveSimulation().getAverageNeighborsPerNode());
        }
    }

    public WorkbenchPanel getWorkbenchPanel() {
        return workbenchPanel1;
    }

    @Action
    public void ShowSimulationProperties() {
        PlatformDialog.display(this.getFrame(), new SimulationPropertiesPanel(), "Simulation Properties", PlatformDialog.OK_MODE);
    }

    @Action
    public void ShowEvaluationPanel() {
//        PlatformFrame.display(EvaluationPanel.getInstance(), "Evaluation Settings", PlatformFrame.OK_MODE);
    }

    @Action
    public void viewReliabilityControlPanelAction() {
        PlatformFrame.display(new ReliabilityInstrumentControlPanel(), "Reliability Control Panel", PlatformFrame.OK_MODE);
    }

    @Action
    public void viewCoverageControlPanelAction() {
        PlatformFrame.display(new CoverageInstrumentControlPanel(), "Coverage Control Panel", PlatformFrame.OK_MODE);
    }

    @Action
    public void viewLatencyControlPanelAction() {
        GUI_Utils.showMessage("Feature not implemented yet!", GUI_Utils.WARNING_MESSAGE);
    }

    @Action
    public void viewEnergyControlPanelAction() {
        PlatformFrame.display(new EnergyEvaluationPanel(), "Energy Evaluation ", PlatformFrame.OK_MODE);
    }

    public void update() {
        getWorkbenchPanel().getSimulationPanel().updateDisplay();
    }

    @Action
    public void ShowNodeInfo() {
        GUI_Utils.showMessage("Feature not implemented yet!", GUI_Utils.WARNING_MESSAGE);
    }

    @Action
    public void ShowApplicationOutputPanelAction() {
        PlatformFrame.display(new ApplicationOutputPanel(), "Application Layer Output", PlatformFrame.OK_MODE);
    }

    @Action
    public void ShowRoutingOutputPanelAction() {
        PlatformFrame.display(new RoutingOutputPanel(), "Routing Layer Output", PlatformFrame.OK_MODE);
    }

    @Action
    public void EnableRoutingLayerDebug() {
        if (isActiveSimulationValid()) {
            PlatformManager.getInstance().getActiveSimulation().enableRoutingLayerDebug(chkRoutingLayerDebug.isSelected());
        }
    }

    @Action
    public void EnableMacLayerDebug() {
        if (isActiveSimulationValid()) {
            PlatformManager.getInstance().getActiveSimulation().enableMACLayerDebug(chkMACLayerDebug.isSelected());
        }
    }

    private boolean isActiveSimulationValid() {

        return PlatformManager.getInstance().getActiveSimulation() != null;
    }

    private CoverageInstrument getCoverageInstrument() {
        return PlatformManager.getInstance().getActiveSimulation().getCoverageInstrument();
    }

    @Action
    public void OpenSimulation() {
        PlatformUtils.openSimulation();
    }

    public void willExit(EventObject arg0) {
    }

    public void beforeStart(SimulationEvent event) {
        event.setCancel(false);
    }

    public void afterStart(SimulationEvent event) {
        if (!isActiveSimulationValid()) {
            return;
        }

        clockCounter.start();

        simulationTimer = new java.util.Timer();
        (simulationTimer).schedule(new TimerTask() {

            @Override
            public void run() {
                try {

                    long time = Simulator.getSimulationTimeInMillisec();
                    DateFormat df = new SimpleDateFormat("HH':'mm':'ss");
                    df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                    SimulationTime.setText(df.format(new Date(time)));
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }

            }
        }, 0, 1000);
        updateSimulationState("START");

    }

    @Override
    public void beforeStop(SimulationEvent event) {
    }

    @Override
    public void afterStop(SimulationEvent event) {
        updateSimulationState("STOP");
    }

    @Override
    public void beforeBuildNetwork(SimulationEvent event) {
    }

    @Override
    public void afterBuildNetwork(SimulationEvent event) {
        try {
            updateAverageNeighborsPerNode();
            lblRadioCoverageValue.setText("" + ((CoverageInstrument) getCoverageInstrument()).getCoverageValueByModel(CoverageInstrument.CoverageModelEnum.RADIO) + "%");
        } catch (Exception e) {
            GUI_Utils.showException(e);
        }

    }

    @Override
    public void onStartFailure(SimulationEvent event) {
        GUI_Utils.showWarningMessage("Simulation Start failed: " + event.getReason());
    }

    @Override
    public void onStopFailure(SimulationEvent event) {
        GUI_Utils.showWarningMessage("Simulation stop failed: " + event.getReason());
    }

    public void onBuildNetworkFailure(SimulationEvent event) {
    }

    public void onEmptyQueue(SimulationEvent event) {
        System.out.println("Queue is empty");
    }

    public void afterNodeDeploy(DeployEvent event) {
        if (isActiveSimulationValid()) {
            updateSimulationNrNodes(PlatformManager.getInstance().getActiveSimulation().getSimulator().getNodes().size());
        }
    }

    public void beforeNodeDeploy(DeployEvent event) {
    }

    public void onNewSimulatorRound(SimulationEvent event) {
        updateSimulationEvents(event.getSimulation().getSimulator().getNumberOfRemainEvents());
    }

    public void onError(ExceptionEvent event) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        event.getThrowable().printStackTrace(pw);
        pw.flush();
        sw.flush();
        System.out.println(sw.toString());


    }

    @Action
    public void SelectStableNodes() {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            PlatformManager.getInstance().getActiveSimulation().selectNodes(true, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    return node.getRoutingLayer().isStable();
                }
            });
        }
    }

    @Action
    public void MarkStableNodes() {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            PlatformManager.getInstance().getActiveSimulation().markStableNodes(true, new NodeSelectionCondition() {

                public boolean select(Node node) {
                    return node.getRoutingLayer().isStable();
                }
            });
        }
    }

    public static void applyLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

    }

    public void showMessage(String msg) {
        int messageTimeout = 3000;
        statusMessageLabel.setText(msg);

        final Timer mt = new Timer(messageTimeout, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        mt.setRepeats(false);
        mt.start();

    }

    @Override
    public synchronized void afterTestExecution(final SimulationTestEvent event) {

        final AbstractTest t = (AbstractTest) event.getSource();
        final String msg = "TestName: " + t.getName()
                + "\n\nNumber Of Messages Sent: " + t.getEvaluationManager().getMessageDatabase().getTotalNumberOfUniqueMessagesSent()
                + "\nNumber Of Messages Received: " + t.getEvaluationManager().getMessageDatabase().getTotalMessagesReceived()
                + "\nNumber Of Sender Nodes: " + t.getEvaluationManager().getMessageDatabase().getTotalSenderNodes()
                + "\nNumber Of Covered Nodes: " + t.getEvaluationManager().getMessageDatabase().getTotalCoveredNodes()
                + "\n\nTotal Energy Spent: " + t.getEvaluationManager().getEnergyDatabase().getTotalEnergySpent();
        System.out.println(msg);

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

//                GUI_Utils.showMessage(msg);
                TestResultsPanel c = new TestResultsPanel();
                c.setTest(t);
                PlatformFrame.display(c, "Test Results", PlatformFrame.OK_MODE);
            }
        });

    }

    @Override
    public void startTestExecution(SimulationTestEvent event) {
        System.out.println("Starting test: " + ((AbstractTest)event.getSource()).getName());
    }
}
