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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application.ExitListener;
import org.jdesktop.application.ResourceMap;
import org.mei.securesim.components.instruments.coverage.CoverageController;
import org.mei.securesim.components.instruments.ReliabilityInstrument;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.components.instruments.coverage.CoverageListener;
import org.mei.securesim.components.instruments.latency.LatencyController;
import org.mei.securesim.components.instruments.listeners.SignalUpdateEvent;
import org.mei.securesim.components.simulation.ISimulationPlatform;
import org.mei.securesim.core.nodes.Node;
import org.mei.securesim.platform.core.PlatformController;
import org.mei.securesim.platform.core.instruments.InstrumentsControlPanel;
import org.mei.securesim.platform.core.instruments.coverage.ui.CoverageControllerPanel;
import org.mei.securesim.platform.ui.WorkbenchPanel;
import org.mei.securesim.platform.ui.frames.RoutingInfoPanel;

import org.mei.securesim.platform.ui.frames.SimulationWizardDialog;
import org.mei.securesim.platform.ui.uiextended.BestTabbedPane;
import org.mei.securesim.platform.utils.PlatformUtils;
import org.mei.securesim.platform.utils.gui.ClockCounter;
import org.mei.securesim.platform.utils.gui.GUI_Utils;
import org.mei.securesim.platform.utils.gui.IClockDisplay;

/**
 * The application's main frame.
 */
public class PlatformView extends FrameView implements ISimulationPlatform, ExitListener, IClockDisplay, CoverageListener {

    public static final String TAB_COVERAGE_CONTROLLER_INFO_TITLE = "Coverage Controller Info";
    private boolean workbenchVisible;
    protected ClockCounter clockCounter;

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
        CoverageController.getInstance().addCoverageListener(this);

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
        showInstrumentPanel = new javax.swing.JButton();
        tgbMarkStableNodes = new javax.swing.JToggleButton();
        tgbSelectStableNodes = new javax.swing.JToggleButton();
        showRoutingInfo = new javax.swing.JButton();
        lblRadioCoverageValue = new javax.swing.JLabel();
        lblRoutingCoverageValue = new javax.swing.JLabel();
        lblAverageNeighborsPerNode = new javax.swing.JLabel();
        mainSplitPane = new javax.swing.JSplitPane();
        tabbedTools = new org.mei.securesim.platform.ui.uiextended.BestTabbedPane();
        workbenchPanel1 = new org.mei.securesim.platform.ui.WorkbenchPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu simulationMenu = new javax.swing.JMenu();
        menuNewSimulation = new javax.swing.JMenuItem();
        menuOpenSImulation = new javax.swing.JMenuItem();
        menuSaveSimulation = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        simPropertiesSubMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        instrumentsMenu = new javax.swing.JMenu();
        coverageCtlPanelMenu = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        energyCtlMenu = new javax.swing.JMenu();
        energyCtlStatusMenu = new javax.swing.JCheckBoxMenuItem();
        coverageCtlMenu = new javax.swing.JMenu();
        reliabilityCtlMenu = new javax.swing.JMenu();
        reliabilityCtlStatusMenu = new javax.swing.JCheckBoxMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        instrumentsConfigMenu = new javax.swing.JMenuItem();
        latencyCtlMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        ViewMenu = new javax.swing.JMenu();
        SimInfoMenu = new javax.swing.JMenuItem();
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
        adjustRadioStrengthSlider.setName("adjustRadioStrengthSlider"); // NOI18N
        adjustRadioStrengthPanel.add(adjustRadioStrengthSlider, java.awt.BorderLayout.CENTER);

        mainToolbar.add(adjustRadioStrengthPanel);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1085, 10));

        showInstrumentPanel.setAction(actionMap.get("ShowInstrumentsPanel")); // NOI18N
        showInstrumentPanel.setText(resourceMap.getString("showInstrumentPanel.text")); // NOI18N
        showInstrumentPanel.setName("showInstrumentPanel"); // NOI18N

        tgbMarkStableNodes.setAction(actionMap.get("MarkStableNodes")); // NOI18N
        tgbMarkStableNodes.setText(resourceMap.getString("tgbMarkStableNodes.text")); // NOI18N
        tgbMarkStableNodes.setName("tgbMarkStableNodes"); // NOI18N

        tgbSelectStableNodes.setAction(actionMap.get("SelectStableNodes")); // NOI18N
        tgbSelectStableNodes.setText(resourceMap.getString("tgbSelectStableNodes.text")); // NOI18N
        tgbSelectStableNodes.setName("tgbSelectStableNodes"); // NOI18N

        showRoutingInfo.setAction(actionMap.get("ShowRoutingInfo")); // NOI18N
        showRoutingInfo.setText(resourceMap.getString("showRoutingInfo.text")); // NOI18N
        showRoutingInfo.setName("showRoutingInfo"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showInstrumentPanel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgbMarkStableNodes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgbSelectStableNodes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showRoutingInfo)
                .addContainerGap(391, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showInstrumentPanel)
                    .addComponent(tgbMarkStableNodes)
                    .addComponent(tgbSelectStableNodes)
                    .addComponent(showRoutingInfo))
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

        mainPanel.add(mainToolbar, java.awt.BorderLayout.PAGE_START);

        mainSplitPane.setDividerLocation(200);
        mainSplitPane.setDividerSize(10);
        mainSplitPane.setAutoscrolls(true);
        mainSplitPane.setMinimumSize(new java.awt.Dimension(0, 0));
        mainSplitPane.setName("mainSplitPane"); // NOI18N
        mainSplitPane.setOneTouchExpandable(true);

        tabbedTools.setName("tabbedTools"); // NOI18N
        mainSplitPane.setRightComponent(tabbedTools);

        workbenchPanel1.setName("workbenchPanel1"); // NOI18N
        mainSplitPane.setLeftComponent(workbenchPanel1);

        mainPanel.add(mainSplitPane, java.awt.BorderLayout.CENTER);

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

        simPropertiesSubMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
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

        instrumentsMenu.setText(resourceMap.getString("instrumentsMenu.text")); // NOI18N
        instrumentsMenu.setName("instrumentsMenu"); // NOI18N

        coverageCtlPanelMenu.setAction(actionMap.get("ShowCoverageControllerInfo")); // NOI18N
        coverageCtlPanelMenu.setText(resourceMap.getString("coverageCtlPanelMenu.text")); // NOI18N
        coverageCtlPanelMenu.setName("coverageCtlPanelMenu"); // NOI18N
        instrumentsMenu.add(coverageCtlPanelMenu);

        jSeparator4.setName("jSeparator4"); // NOI18N
        instrumentsMenu.add(jSeparator4);

        energyCtlMenu.setText(resourceMap.getString("energyCtlMenu.text")); // NOI18N
        energyCtlMenu.setEnabled(false);
        energyCtlMenu.setName("energyCtlMenu"); // NOI18N
        energyCtlMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                energyCtlMenuActionPerformed(evt);
            }
        });

        energyCtlStatusMenu.setSelected(true);
        energyCtlStatusMenu.setText(resourceMap.getString("energyCtlStatusMenu.text")); // NOI18N
        energyCtlStatusMenu.setName("energyCtlStatusMenu"); // NOI18N
        energyCtlStatusMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                energyCtlStatusMenuActionPerformed(evt);
            }
        });
        energyCtlMenu.add(energyCtlStatusMenu);

        instrumentsMenu.add(energyCtlMenu);

        coverageCtlMenu.setText(resourceMap.getString("coverageCtlMenu.text")); // NOI18N
        coverageCtlMenu.setEnabled(false);
        coverageCtlMenu.setName("coverageCtlMenu"); // NOI18N
        coverageCtlMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                coverageCtlMenuMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                coverageCtlMenuMousePressed(evt);
            }
        });
        coverageCtlMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coverageCtlMenuActionPerformed(evt);
            }
        });
        instrumentsMenu.add(coverageCtlMenu);

        reliabilityCtlMenu.setText(resourceMap.getString("reliabilityCtlMenu.text")); // NOI18N
        reliabilityCtlMenu.setEnabled(false);
        reliabilityCtlMenu.setName("reliabilityCtlMenu"); // NOI18N

        reliabilityCtlStatusMenu.setSelected(true);
        reliabilityCtlStatusMenu.setText(resourceMap.getString("reliabilityCtlStatusMenu.text")); // NOI18N
        reliabilityCtlStatusMenu.setName("reliabilityCtlStatusMenu"); // NOI18N
        reliabilityCtlStatusMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reliabilityCtlStatusMenuActionPerformed(evt);
            }
        });
        reliabilityCtlMenu.add(reliabilityCtlStatusMenu);

        jSeparator6.setName("jSeparator6"); // NOI18N
        reliabilityCtlMenu.add(jSeparator6);

        instrumentsMenu.add(reliabilityCtlMenu);

        jSeparator2.setName("jSeparator2"); // NOI18N
        instrumentsMenu.add(jSeparator2);

        instrumentsConfigMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        instrumentsConfigMenu.setText(resourceMap.getString("instrumentsConfigMenu.text")); // NOI18N
        instrumentsConfigMenu.setName("instrumentsConfigMenu"); // NOI18N
        instrumentsMenu.add(instrumentsConfigMenu);

        latencyCtlMenu.setText(resourceMap.getString("latencyCtlMenu.text")); // NOI18N
        latencyCtlMenu.setName("latencyCtlMenu"); // NOI18N

        jMenuItem1.setAction(actionMap.get("RunLatency")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        latencyCtlMenu.add(jMenuItem1);

        instrumentsMenu.add(latencyCtlMenu);

        menuBar.add(instrumentsMenu);

        ViewMenu.setText(resourceMap.getString("ViewMenu.text")); // NOI18N
        ViewMenu.setName("ViewMenu"); // NOI18N

        SimInfoMenu.setAction(actionMap.get("ShowRoutingInfo")); // NOI18N
        SimInfoMenu.setText(resourceMap.getString("SimInfoMenu.text")); // NOI18N
        SimInfoMenu.setName("SimInfoMenu"); // NOI18N
        ViewMenu.add(SimInfoMenu);

        menuBar.add(ViewMenu);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NrSimulationNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                            .addComponent(SimulationStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                            .addComponent(NrSimulationNodes)
                            .addComponent(NrSelectedNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NrEvents, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                            .addComponent(SimulationRealTime)
                            .addComponent(SimulationTime))
                        .addGap(1, 1, 1))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(statusMessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addComponent(FieldSize, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addContainerGap())
        );

        statusPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {NrEvents, NrSimulationNodes, SimulationStatus, statusMessageLabel});

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

    private void energyCtlMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_energyCtlMenuActionPerformed
        // TODO add your handling code here:
        //CoverageController.getInstance().setEnable(ener);
    }//GEN-LAST:event_energyCtlMenuActionPerformed

    private void energyCtlStatusMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_energyCtlStatusMenuActionPerformed
    }//GEN-LAST:event_energyCtlStatusMenuActionPerformed

    private void reliabilityCtlStatusMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reliabilityCtlStatusMenuActionPerformed
        // TODO add your handling code here:
        ReliabilityInstrument.getInstance().setEnable(reliabilityCtlStatusMenu.isSelected());
    }//GEN-LAST:event_reliabilityCtlStatusMenuActionPerformed

    private void coverageCtlMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coverageCtlMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_coverageCtlMenuActionPerformed

    private void coverageCtlMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coverageCtlMenuMousePressed
        // TODO add your handling code here:
}//GEN-LAST:event_coverageCtlMenuMousePressed

    private void coverageCtlMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coverageCtlMenuMouseEntered
        // TODO add your handling code here:
}//GEN-LAST:event_coverageCtlMenuMouseEntered

    @Action
    public void newSimulation() {
        SimulationWizardDialog sw = new SimulationWizardDialog(null, true);
        sw.setVisible(true);
        boolean status = sw.isOk();
        if (status) //           sw.getSimulationFactory();
        {
            workbenchPanel1.setSimulationFactory(sw.getSimulationFactory());
        }
        sw.dispose();
        if (status) {
            SimulationController.getInstance().reset();

            getComponent().setVisible(true);
            workbenchVisible = true;
            workbenchPanel1.setVisible(true);
            mainSplitPane.setVisible(true);
            mainPanel.setVisible(true);
            mainSplitPane.setDividerLocation(1280);
            PlatformController.getInstance().setNewSimulation(true);
            PlatformController.getInstance().showSimulationName("");
            CoverageController.getInstance().updateNetworkSize();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel FieldSize;
    protected javax.swing.JLabel NrEvents;
    protected javax.swing.JLabel NrSelectedNodes;
    protected javax.swing.JLabel NrSimulationNodes;
    protected javax.swing.JMenuItem SimInfoMenu;
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
    protected javax.swing.JMenu coverageCtlMenu;
    protected javax.swing.JMenuItem coverageCtlPanelMenu;
    protected javax.swing.JMenu energyCtlMenu;
    protected javax.swing.JCheckBoxMenuItem energyCtlStatusMenu;
    protected javax.swing.JMenuItem instrumentsConfigMenu;
    protected javax.swing.JMenu instrumentsMenu;
    protected javax.swing.JMenu jMenu1;
    protected javax.swing.JMenu jMenu2;
    protected javax.swing.JMenuItem jMenuItem1;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel2;
    protected javax.swing.JPopupMenu jPopupMenu1;
    protected javax.swing.JPopupMenu.Separator jSeparator1;
    protected javax.swing.JPopupMenu.Separator jSeparator2;
    protected javax.swing.JPopupMenu.Separator jSeparator3;
    protected javax.swing.JPopupMenu.Separator jSeparator4;
    protected javax.swing.JPopupMenu.Separator jSeparator6;
    protected javax.swing.JToolBar.Separator jSeparator7;
    protected javax.swing.JMenu latencyCtlMenu;
    protected javax.swing.JLabel lblAverageNeighborsPerNode;
    protected javax.swing.JLabel lblRadioCoverageValue;
    protected javax.swing.JLabel lblRoutingCoverageValue;
    protected javax.swing.JPanel mainPanel;
    protected javax.swing.JSplitPane mainSplitPane;
    protected javax.swing.JToolBar mainToolbar;
    protected javax.swing.JMenuBar menuBar;
    protected javax.swing.JMenuItem menuNewSimulation;
    protected javax.swing.JMenuItem menuOpenSImulation;
    protected javax.swing.JMenuItem menuSaveSimulation;
    private javax.swing.JProgressBar progressBar;
    protected javax.swing.JMenu reliabilityCtlMenu;
    protected javax.swing.JCheckBoxMenuItem reliabilityCtlStatusMenu;
    protected javax.swing.JButton showInstrumentPanel;
    protected javax.swing.JButton showRoutingInfo;
    protected javax.swing.JMenuItem simPropertiesSubMenu;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    protected javax.swing.JPanel statusPanel;
    protected org.mei.securesim.platform.ui.uiextended.BestTabbedPane tabbedTools;
    protected javax.swing.JToggleButton tgbMarkStableNodes;
    protected javax.swing.JToggleButton tgbSelectStableNodes;
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

    public void addTab(String title, JComponent component) {
        tabbedTools.setTabPlacement(BestTabbedPane.RIGHT);
        tabbedTools.addTab(title, component);
        tabbedTools.setTabEditingAllowed(true);
        tabbedTools.setShowCloseButton(true);
        tabbedTools.setShowCloseButtonOnSelectedTab(true);
        tabbedTools.setShowCloseButtonOnTab(true);
        tabbedTools.setBoldActiveTab(true);



    }

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
        (new java.util.Timer()).schedule(new TimerTask() {

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
        double value = CoverageController.getInstance().getCoverageValueByModel(CoverageController.CoverageModelEnum.RADIO);
        JOptionPane.showMessageDialog(this.getFrame(), value);
    }

    public void onSignalUpdate(SignalUpdateEvent event) {
        if (event.getModel() == CoverageController.CoverageModelEnum.RADIO) {
            lblRadioCoverageValue.setText("" + CoverageController.getInstance().getCoverageValueByModel(CoverageController.CoverageModelEnum.RADIO) + "%");
        } else if (event.getModel() == CoverageController.CoverageModelEnum.ROUTING) {
            lblRoutingCoverageValue.setText("" + CoverageController.getInstance().getCoverageValueByModel(CoverageController.CoverageModelEnum.ROUTING) + "%");
        }
    }

    @Action
    public void ShowCoverageControllerInfo() {
        if (!tabShowned(TAB_COVERAGE_CONTROLLER_INFO_TITLE)) {
            addTab(TAB_COVERAGE_CONTROLLER_INFO_TITLE, CoverageControllerPanel.getInstance());
            mainSplitPane.setDividerLocation(.8);
        }
    }

    boolean tabShowned(String title) {
        for (int i = 0; i < tabbedTools.getTabCount(); i++) {
            String sTitle = tabbedTools.getTitleAt(i);
            if (sTitle.equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Action
    public void RunLatency() {
        LatencyController.getInstance().startAnalysis();
    }

    @Action
    public void ApplyRadioStrength() {
        if (SimulationController.getInstance().getSimulation().getSimulator().getNodes().size() > 0) {
            long radioStrenght = (Integer) adjustRadioStrengthSlider.getModel().getValue();
            SimulationController.getInstance().applyRadioStrength(radioStrenght);
        } else {
            GUI_Utils.showMessage("Must deploy nodes before apply new radio strength", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void updateAverageNeighborsPerNode() {
        lblAverageNeighborsPerNode.setText("" + SimulationController.getInstance().getAverageNeighborsPerNode());
    }

    @Action
    public void ShowRoutingInfo() {
        PlatformController.getInstance().getPlatformView().addTab("Routing Info", RoutingInfoPanel.getInstance());



    }

    public WorkbenchPanel getWorkbenchPanel() {
        return workbenchPanel1;
    }

    @Action
    public void ShowInstrumentsPanel() {
        addTab("instruments", new InstrumentsControlPanel());
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
}
