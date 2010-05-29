/*
 * PlatformView.java
 */
package org.mei.securesim.platform;

import java.util.EventObject;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.mei.securesim.components.instruments.CoverageController;
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.components.instruments.listeners.CoverageListener;
import org.mei.securesim.components.instruments.listeners.SignalUpdateEvent;
import org.mei.securesim.components.simulation.ISimulationPlatform;
import org.mei.securesim.platform.core.PlatformController;
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
        jPanel1 = new javax.swing.JPanel();
        lblRadioCoverageValue = new javax.swing.JLabel();
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
        energyCtlMenu = new javax.swing.JMenu();
        energyCtlStatusMenu = new javax.swing.JCheckBoxMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        coverageCtlMenu = new javax.swing.JMenu();
        coverageCtlStatusMenu = new javax.swing.JCheckBoxMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        reliabilityCtlMenu = new javax.swing.JMenu();
        reliabilityCtlStatusMenu = new javax.swing.JCheckBoxMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        instrumentsConfigMenu = new javax.swing.JMenuItem();
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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();

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

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1085, 10));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1008, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
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

        energyCtlMenu.setText(resourceMap.getString("energyCtlMenu.text")); // NOI18N
        energyCtlMenu.setName("energyCtlMenu"); // NOI18N

        energyCtlStatusMenu.setSelected(true);
        energyCtlStatusMenu.setText(resourceMap.getString("energyCtlStatusMenu.text")); // NOI18N
        energyCtlStatusMenu.setName("energyCtlStatusMenu"); // NOI18N
        energyCtlMenu.add(energyCtlStatusMenu);

        jSeparator4.setName("jSeparator4"); // NOI18N
        energyCtlMenu.add(jSeparator4);

        instrumentsMenu.add(energyCtlMenu);

        coverageCtlMenu.setText(resourceMap.getString("coverageCtlMenu.text")); // NOI18N
        coverageCtlMenu.setName("coverageCtlMenu"); // NOI18N

        coverageCtlStatusMenu.setSelected(true);
        coverageCtlStatusMenu.setText(resourceMap.getString("coverageCtlStatusMenu.text")); // NOI18N
        coverageCtlStatusMenu.setName("coverageCtlStatusMenu"); // NOI18N
        coverageCtlMenu.add(coverageCtlStatusMenu);

        jSeparator5.setName("jSeparator5"); // NOI18N
        coverageCtlMenu.add(jSeparator5);

        instrumentsMenu.add(coverageCtlMenu);

        reliabilityCtlMenu.setText(resourceMap.getString("reliabilityCtlMenu.text")); // NOI18N
        reliabilityCtlMenu.setName("reliabilityCtlMenu"); // NOI18N

        reliabilityCtlStatusMenu.setSelected(true);
        reliabilityCtlStatusMenu.setText(resourceMap.getString("reliabilityCtlStatusMenu.text")); // NOI18N
        reliabilityCtlStatusMenu.setName("reliabilityCtlStatusMenu"); // NOI18N
        reliabilityCtlMenu.add(reliabilityCtlStatusMenu);

        jSeparator6.setName("jSeparator6"); // NOI18N
        reliabilityCtlMenu.add(jSeparator6);

        instrumentsMenu.add(reliabilityCtlMenu);

        jSeparator2.setName("jSeparator2"); // NOI18N
        instrumentsMenu.add(jSeparator2);

        instrumentsConfigMenu.setText(resourceMap.getString("instrumentsConfigMenu.text")); // NOI18N
        instrumentsConfigMenu.setName("instrumentsConfigMenu"); // NOI18N
        instrumentsMenu.add(instrumentsConfigMenu);

        menuBar.add(instrumentsMenu);

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

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addComponent(statusMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(SimulationStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(NrSimulationNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(NrSelectedNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(NrEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(SimulationRealTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SimulationTime, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(statusAnimationLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(SimulationTime)
                        .addGap(2, 2, 2))
                    .addComponent(SimulationRealTime)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(NrEvents, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(NrSelectedNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(NrSimulationNodes)
                        .addGap(1, 1, 1))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(SimulationStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(statusMessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
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
            workbenchPanel1.setSimulationFactory(sw.getSimulationFactory());
        }
        sw.dispose();
        if (status) {
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
    protected javax.swing.JLabel NrEvents;
    protected javax.swing.JLabel NrSelectedNodes;
    protected javax.swing.JLabel NrSimulationNodes;
    protected javax.swing.JLabel SimulationRealTime;
    protected javax.swing.JLabel SimulationStatus;
    protected javax.swing.JLabel SimulationTime;
    protected javax.swing.JButton btnNew;
    protected javax.swing.JButton btnOpen;
    protected javax.swing.JButton btnProperties;
    protected javax.swing.JButton btnSave;
    protected javax.swing.JMenu coverageCtlMenu;
    protected javax.swing.JCheckBoxMenuItem coverageCtlStatusMenu;
    protected javax.swing.JMenu energyCtlMenu;
    protected javax.swing.JCheckBoxMenuItem energyCtlStatusMenu;
    protected javax.swing.JMenuItem instrumentsConfigMenu;
    protected javax.swing.JMenu instrumentsMenu;
    protected javax.swing.JMenu jMenu1;
    protected javax.swing.JMenu jMenu2;
    protected javax.swing.JPanel jPanel1;
    protected javax.swing.JPopupMenu jPopupMenu1;
    protected javax.swing.JPopupMenu.Separator jSeparator1;
    protected javax.swing.JPopupMenu.Separator jSeparator2;
    protected javax.swing.JPopupMenu.Separator jSeparator3;
    protected javax.swing.JPopupMenu.Separator jSeparator4;
    protected javax.swing.JPopupMenu.Separator jSeparator5;
    protected javax.swing.JPopupMenu.Separator jSeparator6;
    protected javax.swing.JLabel lblRadioCoverageValue;
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
    protected javax.swing.JMenuItem simPropertiesSubMenu;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    protected javax.swing.JPanel statusPanel;
    protected org.mei.securesim.platform.ui.uiextended.BestTabbedPane tabbedTools;
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

    public void showLogMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onStopSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateSimulationState(String state) {
        this.SimulationStatus.setText(state.toUpperCase());
    }

    public boolean canExit(EventObject event) {
        boolean return_value = GUI_Utils.confirm("Confirm Simulation Platform Exit?");
        if (return_value){
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
        throw new UnsupportedOperationException("Not supported yet.");
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


    public void configureMenuSimulation(){
    }
    public void configureMenuInstruments(){
        
    }

    @Action
    public void SaveSimulation() {
        PlatformUtils.saveSimulation(SimulationController.getInstance().getSimulation());
    }

    @Action
    public void ShowRadioCoverageValue() {
        double value=CoverageController.getInstance().getCoverageValueByModel(CoverageController.CoverageModelEnum.RADIO);
        JOptionPane.showMessageDialog(this.getFrame(), value);
    }

    public void onSignalUpdate(SignalUpdateEvent event) {
        if (event.getModel()==CoverageController.CoverageModelEnum.RADIO){
            lblRadioCoverageValue.setText(""+CoverageController.getInstance().getCoverageValueByModel(CoverageController.CoverageModelEnum.RADIO)+"%");
        }
    }



}
