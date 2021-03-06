/*
 * PlatformView.java
 */
package org.mei.securesim.platform;

import java.awt.Dimension;
import java.util.EventObject;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
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
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.components.instruments.coverage.CoverageInstrument;
import org.mei.securesim.components.instruments.coverage.CoverageListener;
import org.mei.securesim.components.instruments.coverage.listeners.SignalUpdateEvent;
import org.mei.securesim.components.simulation.ISimulationPlatform;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.platform.core.PlatformController;
import org.mei.securesim.platform.core.instruments.coverage.CoverageInstrumentControlPanel;
import org.mei.securesim.platform.core.instruments.reliability.ReliabilityInstrumentControlPanel;
import org.mei.securesim.platform.ui.WorkbenchPanel;
import org.mei.securesim.platform.ui.panels.ApplicationOutputPanel;
import org.mei.securesim.platform.ui.frames.InstrumentDialog;
import org.mei.securesim.platform.ui.frames.InstrumentFrame;
import org.mei.securesim.platform.ui.panels.RoutingInfoPanel;
import org.mei.securesim.platform.ui.frames.SimulationWizardDialog;
import org.mei.securesim.platform.ui.panels.EnergyEvaluationPanel;
import org.mei.securesim.platform.ui.panels.EvaluationPanel;
import org.mei.securesim.platform.ui.panels.RoutingOutputPanel;
import org.mei.securesim.platform.ui.panels.SimulationPropertiesPanel;
import org.mei.securesim.platform.utils.PlatformUtils;
import org.mei.securesim.platform.utils.gui.ClockCounter;
import org.mei.securesim.platform.utils.gui.GUI_Utils;
import org.mei.securesim.platform.utils.gui.IClockDisplay;

/**
 * The application's main frame.
 */
public class PlatformView extends FrameView implements ISimulationPlatform, ExitListener, IClockDisplay, CoverageListener {

    private boolean workbenchVisible;
    protected ClockCounter clockCounter;
    private InstrumentFrame energyControlPanelFrame;
    private InstrumentDialog evaluationSettingsDialog;
    private InstrumentDialog simulationPropertiesDialog;
    private InstrumentFrame reliabilityControlPanelFrame;
    private InstrumentFrame coverageControlPanelFrame;
    private InstrumentFrame showRoutingInfoFrame;
    private InstrumentFrame showRoutingOutputFrame;
    private InstrumentFrame showAplicationOutputFrame;
    private java.util.Timer simulationTimer;

    public PlatformView(SingleFrameApplication app) {
        super(app);

        initComponents();

        PlatformApp.loadClasses();
        getComponent().setVisible(false);
        PlatformController.getInstance().setPlatformView(this);

        clockCounter = new ClockCounter();
        clockCounter.setDisplay(this);

        getApplication().addExitListener(this);

        SimulationController.getInstance().registerPlatform(this);
        ((CoverageInstrument) CoverageInstrument.getInstance()).addCoverageListener(this);

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

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
        instance = this;
        workbenchPanel1.setVisible(false);
        //mainSplitPane.setVisible(false);
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

    /** This method is called from within the constructor tojbu
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        workbenchPanel1 = new org.mei.securesim.platform.ui.WorkbenchPanel();
        mainToolbar = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnProperties = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        adjustRadioStrengthPanel = new javax.swing.JPanel();
        adjustRadioStrengthButton = new javax.swing.JButton();
        adjustRadioStrengthSlider = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        tgbMarkStableNodes = new javax.swing.JToggleButton();
        tgbSelectStableNodes = new javax.swing.JToggleButton();
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
        viewRoutingInfo = new javax.swing.JMenuItem();
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

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getActionMap(PlatformView.class, this);
        btnNew.setAction(actionMap.get("newSimulation")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(PlatformView.class);
        btnNew.setIcon(resourceMap.getIcon("btnNew.icon")); // NOI18N
        btnNew.setText(resourceMap.getString("btnNew.text")); // NOI18N
        btnNew.setToolTipText(resourceMap.getString("btnNew.toolTipText")); // NOI18N
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setName("btnNew"); // NOI18N
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolbar.add(btnNew);

        btnOpen.setIcon(resourceMap.getIcon("btnOpen.icon")); // NOI18N
        btnOpen.setText(resourceMap.getString("btnOpen.text")); // NOI18N
        btnOpen.setToolTipText(resourceMap.getString("btnOpen.toolTipText")); // NOI18N
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setName("btnOpen"); // NOI18N
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolbar.add(btnOpen);

        btnSave.setAction(actionMap.get("SaveSimulation")); // NOI18N
        btnSave.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setToolTipText(resourceMap.getString("btnSave.toolTipText")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolbar.add(btnSave);

        btnProperties.setIcon(resourceMap.getIcon("btnProperties.icon")); // NOI18N
        btnProperties.setText(resourceMap.getString("btnProperties.text")); // NOI18N
        btnProperties.setToolTipText(resourceMap.getString("btnProperties.toolTipText")); // NOI18N
        btnProperties.setEnabled(workbenchPanel1.isVisible());
        btnProperties.setFocusable(false);
        btnProperties.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProperties.setName("btnProperties"); // NOI18N
        btnProperties.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolbar.add(btnProperties);

        jSeparator7.setName("jSeparator7"); // NOI18N
        mainToolbar.add(jSeparator7);

        adjustRadioStrengthPanel.setMaximumSize(new java.awt.Dimension(150, 25));
        adjustRadioStrengthPanel.setMinimumSize(new java.awt.Dimension(150, 25));
        adjustRadioStrengthPanel.setName("adjustRadioStrengthPanel"); // NOI18N
        adjustRadioStrengthPanel.setPreferredSize(new java.awt.Dimension(150, 25));
        adjustRadioStrengthPanel.setLayout(new java.awt.BorderLayout());

        adjustRadioStrengthButton.setAction(actionMap.get("ApplyRadioStrength")); // NOI18N
        adjustRadioStrengthButton.setText(resourceMap.getString("adjustRadioStrengthButton.text")); // NOI18N
        adjustRadioStrengthButton.setFocusable(false);
        adjustRadioStrengthButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        adjustRadioStrengthButton.setName("adjustRadioStrengthButton"); // NOI18N
        adjustRadioStrengthButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        adjustRadioStrengthPanel.add(adjustRadioStrengthButton, java.awt.BorderLayout.LINE_END);

        adjustRadioStrengthSlider.setModel(new javax.swing.SpinnerNumberModel(100, 100, 100000, 100));
        adjustRadioStrengthSlider.setToolTipText(resourceMap.getString("adjustRadioStrengthSlider.toolTipText")); // NOI18N
        adjustRadioStrengthSlider.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        adjustRadioStrengthSlider.setEnabled(workbenchPanel1.isVisible());
        adjustRadioStrengthSlider.setName("adjustRadioStrengthSlider"); // NOI18N
        adjustRadioStrengthPanel.add(adjustRadioStrengthSlider, java.awt.BorderLayout.CENTER);

        mainToolbar.add(adjustRadioStrengthPanel);

        jPanel1.setEnabled(workbenchPanel1.isVisible());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1085, 10));

        tgbMarkStableNodes.setAction(actionMap.get("MarkStableNodes")); // NOI18N
        tgbMarkStableNodes.setText(resourceMap.getString("tgbMarkStableNodes.text")); // NOI18N
        tgbMarkStableNodes.setName("tgbMarkStableNodes"); // NOI18N

        tgbSelectStableNodes.setAction(actionMap.get("SelectStableNodes")); // NOI18N
        tgbSelectStableNodes.setText(resourceMap.getString("tgbSelectStableNodes.text")); // NOI18N
        tgbSelectStableNodes.setName("tgbSelectStableNodes"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tgbMarkStableNodes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgbSelectStableNodes)
                .addContainerGap(657, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tgbMarkStableNodes)
                    .addComponent(tgbSelectStableNodes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        menuNewSimulation.setAction(actionMap.get("newSimulation")); // NOI18N
        menuNewSimulation.setIcon(resourceMap.getIcon("menuNewSimulation.icon")); // NOI18N
        menuNewSimulation.setText(resourceMap.getString("menuNewSimulation.text")); // NOI18N
        menuNewSimulation.setName("menuNewSimulation"); // NOI18N
        simulationMenu.add(menuNewSimulation);

        menuOpenSImulation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuOpenSImulation.setIcon(resourceMap.getIcon("menuOpenSImulation.icon")); // NOI18N
        menuOpenSImulation.setMnemonic('O');
        menuOpenSImulation.setText(resourceMap.getString("menuOpenSImulation.text")); // NOI18N
        menuOpenSImulation.setName("menuOpenSImulation"); // NOI18N
        simulationMenu.add(menuOpenSImulation);

        menuSaveSimulation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSaveSimulation.setIcon(resourceMap.getIcon("menuSaveSimulation.icon")); // NOI18N
        menuSaveSimulation.setMnemonic('S');
        menuSaveSimulation.setText(resourceMap.getString("menuSaveSimulation.text")); // NOI18N
        menuSaveSimulation.setName("menuSaveSimulation"); // NOI18N
        simulationMenu.add(menuSaveSimulation);

        jSeparator3.setName("jSeparator3"); // NOI18N
        simulationMenu.add(jSeparator3);

        simPropertiesSubMenu.setAction(actionMap.get("ShowSimulationProperties")); // NOI18N
        simPropertiesSubMenu.setText(resourceMap.getString("simPropertiesSubMenu.text")); // NOI18N
        simPropertiesSubMenu.setName("simPropertiesSubMenu"); // NOI18N
        simulationMenu.add(simPropertiesSubMenu);

        jSeparator1.setName("jSeparator1"); // NOI18N
        simulationMenu.add(jSeparator1);

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

        viewRoutingInfo.setAction(actionMap.get("ShowRoutingInfoPanelAction")); // NOI18N
        viewRoutingInfo.setText(resourceMap.getString("viewRoutingInfo.text")); // NOI18N
        viewRoutingInfo.setName("viewRoutingInfo"); // NOI18N
        jMenu5.add(viewRoutingInfo);

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

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addComponent(statusMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SimulationStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(NrSimulationNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NrSelectedNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NrEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SimulationRealTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SimulationTime, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FieldSize, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        statusPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {SimulationRealTime, SimulationTime});

        statusPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {NrSelectedNodes, NrSimulationNodes});

        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(statusAnimationLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NrSelectedNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NrEvents, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                            .addComponent(SimulationRealTime)
                            .addComponent(SimulationTime)
                            .addComponent(SimulationStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                            .addComponent(NrSimulationNodes))
                        .addGap(1, 1, 1))
                    .addComponent(FieldSize, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addComponent(statusMessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addContainerGap())
        );

        statusPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {NrEvents, NrSimulationNodes, SimulationStatus});

        statusPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {SimulationRealTime, SimulationTime});

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N
        jPopupMenu1.add(jMenu2);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        jPopupMenu1.add(jMenu1);

        jPanel2.setName("jPanel2"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        setToolBar(mainToolbar);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void newSimulation() {
        SimulationWizardDialog sw = new SimulationWizardDialog(null, true);
        sw.setVisible(true);
        boolean status = sw.isOk();
        if (status) //           sw.getSimulationFactory();
        {
            resetWorkbench();
            mainPanel.add(workbenchPanel1, java.awt.BorderLayout.CENTER);
            workbenchPanel1.setSimulationFactory(sw.getSimulationFactory());
        }
        sw.dispose();
        if (status) {
            SimulationController.getInstance().reset();

            getComponent().setVisible(true);
            workbenchVisible = true;
            workbenchPanel1.setVisible(true);
            mainPanel.setVisible(true);
            PlatformController.getInstance().setNewSimulation(true);
            PlatformController.getInstance().showSimulationName("");
            ((CoverageInstrument) CoverageInstrument.getInstance()).updateNetworkSize();
        }
    }

    private void resetWorkbench() {
        SimulationController.getInstance().reset();
        workbenchPanel1.setVisible(false);
        workbenchPanel1 = null;
        workbenchPanel1 = new WorkbenchPanel();
        clockCounter.stop();
        setSelectedNodes("0");
        setSimulationEvents(0);
        setSimulationTime("0");
        setSimulationNrNodes(0);
        updateAverageNeighborsPerNode();
        updateClock("0");
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
    protected javax.swing.JButton adjustRadioStrengthButton;
    protected javax.swing.JPanel adjustRadioStrengthPanel;
    protected javax.swing.JSpinner adjustRadioStrengthSlider;
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
    protected javax.swing.JToggleButton tgbMarkStableNodes;
    protected javax.swing.JToggleButton tgbSelectStableNodes;
    protected javax.swing.JMenuItem viewApplicationOutput;
    protected javax.swing.JMenuItem viewCoverageControlPanel;
    protected javax.swing.JMenuItem viewEnergyControlPanel;
    protected javax.swing.JMenu viewInstruments;
    protected javax.swing.JMenuItem viewLatencyControlPanel;
    protected javax.swing.JMenuItem viewNodeInfo;
    protected javax.swing.JMenuItem viewReliabilityControlPanel;
    protected javax.swing.JMenuItem viewRoutingInfo;
    protected javax.swing.JMenuItem viewRoutingOutput;
    protected org.mei.securesim.platform.ui.WorkbenchPanel workbenchPanel1;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

    public void showCoverage(String value) {
    }
    protected static PlatformView instance;

    public static PlatformView getInstance() {
        return instance;
    }

    public boolean isWorkbenchVisible() {
        return workbenchVisible;
    }

    public void setWorkbenchVisible(boolean workbenchVisible) {
        this.workbenchVisible = workbenchVisible;
    }

//    public void addTab(String title, JComponent component) {
//        tabbedTools.setTabPlacement(BestTabbedPane.RIGHT);
//        tabbedTools.addTab(title, component);
//        tabbedTools.setTabEditingAllowed(true);
//        tabbedTools.setShowCloseButton(true);
//        tabbedTools.setShowCloseButtonOnSelectedTab(true);
//        tabbedTools.setShowCloseButtonOnTab(true);
//        tabbedTools.setBoldActiveTab(true);
//
//
//
//    }
    public void setSimulationNrNodes(int value) {
        NrSimulationNodes.setText(value + " nodes");

    }

    public void setSimulationTime(String value) {
        SimulationRealTime.setText(value);
    }

    public void setSelectedNodes(String value) {
        NrSelectedNodes.setText(value + " selected");
    }

    public void setStatusMessage(String message) {
        statusMessageLabel.setText(message);
    }

    public void setSimulationEvents(int value) {
        NrEvents.setText("" + value);
    }

    public void updateSimulationFieldSize() {
        Dimension d = SimulationController.getInstance().fieldSize();
        FieldSize.setText("" + d.getWidth() + " , " + d.getHeight());
    }

    public void showLogMessage(String message) {
    }

    public void onStartSimulation() {
        clockCounter.start();
        simulationTimer =new java.util.Timer();
        (simulationTimer).schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    long time = SimulationController.getInstance().getSimulation().getSimulator().getSimulationTimeInMillisec();
                    DateFormat df = new SimpleDateFormat("HH':'mm':'ss");
                    df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                    SimulationTime.setText(df.format(new Date(time)));
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }

            }
        }, 0, 1000);
    }

    public void onPauseSimulation() {
    }

    public void onStopSimulation() {
    }

    public void updateSimulationState(String state) {
        this.SimulationStatus.setText(state.toUpperCase());
    }

    public boolean canExit(EventObject event) {
        boolean return_value = GUI_Utils.confirm("Confirm Simulation Platform Exit?");
        if (return_value) {
            SimulationController.getInstance().exitPlatform();
        }
        return return_value;
    }

    public void willExit(EventObject event) {
//        SimulationController.getInstance().exitPlatform();
    }

    public void onStartPlatform() {
    }

    public void onExitPlatform() {
    }

    public void updateClock(String time) {
        SimulationRealTime.setText(time);
    }

    @Action
    public void ControlCoverageController() {
    }

    @Action
    public void ControlReliabilityController() {
    }

    public void configureMenuSimulation() {
    }

    public void configureMenuInstruments() {
    }

    @Action
    public void SaveSimulation() {
        PlatformUtils.saveSimulation(SimulationController.getInstance().getSimulation());
    }

    @Action
    public void ShowRadioCoverageValue() {
        double value = ((CoverageInstrument) CoverageInstrument.getInstance()).getCoverageValueByModel(CoverageInstrument.CoverageModelEnum.RADIO);
        JOptionPane.showMessageDialog(this.getFrame(), value);
    }

    public void onSignalUpdate(SignalUpdateEvent event) {
        if (event.getModel() == CoverageInstrument.CoverageModelEnum.RADIO) {
            lblRadioCoverageValue.setText("" + ((CoverageInstrument) CoverageInstrument.getInstance()).getCoverageValueByModel(CoverageInstrument.CoverageModelEnum.RADIO) + "%");
        } else if (event.getModel() == CoverageInstrument.CoverageModelEnum.ROUTING) {
            lblRoutingCoverageValue.setText("" + ((CoverageInstrument) CoverageInstrument.getInstance()).getCoverageValueByModel(CoverageInstrument.CoverageModelEnum.ROUTING) + "%");
        }
    }

//    boolean tabShowned(String title) {
//        for (int i = 0; i < tabbedTools.getTabCount(); i++) {
//            String sTitle = tabbedTools.getTitleAt(i);
//            if (sTitle.equals(title)) {
//                return true;
//            }
//        }
//        return false;
//    }
    @Action
    public void RunLatency() {
//        LatencyController.getInstance().startAnalysis();
    }

    @Action
    public void ApplyRadioStrength() {
        if (isSimulationValid()) {
            if (SimulationController.getInstance().getSimulation().getSimulator().getNodes().size() > 0) {
                long radioStrenght = (Integer) adjustRadioStrengthSlider.getModel().getValue();
                SimulationController.getInstance().applyRadioStrength(radioStrenght);
            } else {
                GUI_Utils.showMessage("Must deploy nodes before apply new radio strength", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean isSimulationValid() {
        return SimulationController.getInstance().getSimulation() != null;
    }

    public void updateAverageNeighborsPerNode() {
        lblAverageNeighborsPerNode.setText("" + SimulationController.getInstance().getAverageNeighborsPerNode());
    }

    public WorkbenchPanel getWorkbenchPanel() {
        return workbenchPanel1;
    }

    @Action
    public void MarkStableNodes() {
        Collection<Node> nodes = SimulationController.getInstance().getSimulation().getSimulator().getNodes();
        for (Node node : nodes) {
            if (node.getRoutingLayer().isStable()) {
                if (tgbMarkStableNodes.isSelected()) {
                    node.getGraphicNode().mark();
                } else {
                    node.getGraphicNode().unmark();
                }
            }
        }
    }

    @Action
    public void SelectStableNodes() {
        Collection<Node> nodes = SimulationController.getInstance().getSimulation().getSimulator().getNodes();
        for (Node node : nodes) {
            if (node.getRoutingLayer().isStable()) {
                if (!node.isSinkNode()) {
                    node.getGraphicNode().select(tgbSelectStableNodes.isSelected());
                }
            }
        }
    }

    @Action
    public void ShowSimulationProperties() {
        if (simulationPropertiesDialog == null) {
            simulationPropertiesDialog = new InstrumentDialog("Simulation Properties");
        }
        simulationPropertiesDialog.addContentPanel(new SimulationPropertiesPanel());
        simulationPropertiesDialog.display();
    }

    @Action
    public void ShowEvaluationPanel() {
        if (evaluationSettingsDialog == null) {
            evaluationSettingsDialog = new InstrumentDialog("Evaluation Settings");
        }
        evaluationSettingsDialog.addContentPanel(EvaluationPanel.getInstance());
        evaluationSettingsDialog.display();
    }

    @Action
    public void viewReliabilityControlPanelAction() {
        if (reliabilityControlPanelFrame == null) {
            reliabilityControlPanelFrame = new InstrumentFrame("Reliability Control Panel");
        }
        reliabilityControlPanelFrame.addContentPanel(new ReliabilityInstrumentControlPanel());
        reliabilityControlPanelFrame.display();
    }

    @Action
    public void viewCoverageControlPanelAction() {
        if (coverageControlPanelFrame == null) {
            coverageControlPanelFrame = new InstrumentFrame("Coverage Control Panel");
        }
        coverageControlPanelFrame.addContentPanel(new CoverageInstrumentControlPanel());
        coverageControlPanelFrame.display();
    }

    @Action
    public void viewLatencyControlPanelAction() {
        GUI_Utils.showMessage("Feature not implemented yet!", GUI_Utils.WARNING_MESSAGE);
    }

    @Action
    public void viewEnergyControlPanelAction() {
        if (energyControlPanelFrame == null) {
            energyControlPanelFrame = new InstrumentFrame("Energy Evaluation ");
        }
        energyControlPanelFrame.addContentPanel(new EnergyEvaluationPanel());
        energyControlPanelFrame.display();
    }

    public void update() {
        getWorkbenchPanel().getSimulationPanel().update();
    }

    @Action
    public void ShowRoutingInfoPanelAction() {
        if (showRoutingInfoFrame == null) {
            showRoutingInfoFrame = new InstrumentFrame("Routing Information");
        }
        showRoutingInfoFrame.addContentPanel(new RoutingInfoPanel());
        showRoutingInfoFrame.display();

    }

    @Action
    public void ShowNodeInfo() {
        GUI_Utils.showMessage("Feature not implemented yet!", GUI_Utils.WARNING_MESSAGE);
    }

    @Action
    public void ShowApplicationOutputPanelAction() {
        if (showAplicationOutputFrame == null) {
            showAplicationOutputFrame = new InstrumentFrame("Application Layer Output");
        }
        showAplicationOutputFrame.addContentPanel(new ApplicationOutputPanel());
        showAplicationOutputFrame.display();
    }

    @Action
    public void ShowRoutingOutputPanelAction() {
        if (showRoutingOutputFrame == null) {
            showRoutingOutputFrame = new InstrumentFrame("Routing Layer Output");
        }
        showRoutingOutputFrame.addContentPanel(new RoutingOutputPanel());
        showRoutingOutputFrame.display();
    }

    @Action
    public void EnableRoutingLayerDebug() {
        SimulationController.getInstance().enableRoutingLayerDebug(chkRoutingLayerDebug.isSelected());
    }

    @Action
    public void EnableMacLayerDebug() {
        SimulationController.getInstance().enableMACLayerDebug(chkMACLayerDebug.isSelected());
    }
}
