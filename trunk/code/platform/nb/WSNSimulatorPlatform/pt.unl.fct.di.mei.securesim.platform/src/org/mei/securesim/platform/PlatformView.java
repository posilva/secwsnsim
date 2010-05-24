/*
 * PlatformView.java
 */
package org.mei.securesim.platform;

import java.util.EventObject;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
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
import org.jdesktop.application.Application.ExitListener;
import org.mei.securesim.components.SimulationController;
import org.mei.securesim.components.simulation.ISimulationPlatform;
import org.mei.securesim.platform.ui.frames.SimulationWizardDialog;
import org.mei.securesim.platform.ui.uiextended.BestTabbedPane;
import org.mei.securesim.platform.utils.gui.ClockCounter;
import org.mei.securesim.platform.utils.gui.GUI_Utils;
import org.mei.securesim.platform.utils.gui.IClockDisplay;

/**
 * The application's main frame.
 */
public class PlatformView extends FrameView implements ISimulationPlatform, ExitListener, IClockDisplay {

    private boolean workbenchVisible;
    protected ClockCounter clockCounter;

    public PlatformView(SingleFrameApplication app) {
        super(app);

        initComponents();
        clockCounter = new ClockCounter();
        clockCounter.setDisplay(this);

        getApplication().addExitListener(this);

        SimulationController.getInstance().registerPlatform(this);

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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        mainPanel = new javax.swing.JPanel();
        mainToolbar = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnProperties = new javax.swing.JButton();
        mainSplitPane = new javax.swing.JSplitPane();
        workbenchPanel1 = new org.mei.securesim.platform.ui.WorkbenchPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        tabbedTools = new org.mei.securesim.platform.ui.uiextended.BestTabbedPane();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        menuNewSimulation = new javax.swing.JMenuItem();
        menuOpenSImulation = new javax.swing.JMenuItem();
        menuSaveSimulation = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        statusAnimationLabel = new javax.swing.JLabel();
        statusMessageLabel = new javax.swing.JLabel();
        SimulationRealTime = new javax.swing.JLabel();
        NrSimulationNodes = new javax.swing.JLabel();
        simulationStatus = new javax.swing.JLabel();
        NrEvents = new javax.swing.JLabel();
        SimulationTime = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.BorderLayout());

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

        mainPanel.add(mainToolbar, java.awt.BorderLayout.PAGE_START);

        mainSplitPane.setDividerLocation(200);
        mainSplitPane.setDividerSize(10);
        mainSplitPane.setAutoscrolls(true);
        mainSplitPane.setMinimumSize(new java.awt.Dimension(0, 0));
        mainSplitPane.setName("mainSplitPane"); // NOI18N
        mainSplitPane.setOneTouchExpandable(true);

        workbenchPanel1.setName("workbenchPanel1"); // NOI18N
        mainSplitPane.setLeftComponent(workbenchPanel1);

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N
        jSplitPane1.setOneTouchExpandable(true);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextPane1.setEditable(false);
        jTextPane1.setName("jTextPane1"); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        jSplitPane1.setRightComponent(jScrollPane1);

        tabbedTools.setName("tabbedTools"); // NOI18N
        jSplitPane1.setLeftComponent(tabbedTools);

        mainSplitPane.setRightComponent(jSplitPane1);

        mainPanel.add(mainSplitPane, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, workbenchPanel1, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), fileMenu, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuNewSimulation.setAction(actionMap.get("newSimulation")); // NOI18N
        menuNewSimulation.setIcon(resourceMap.getIcon("menuNewSimulation.icon")); // NOI18N
        menuNewSimulation.setText(resourceMap.getString("menuNewSimulation.text")); // NOI18N
        menuNewSimulation.setName("menuNewSimulation"); // NOI18N
        fileMenu.add(menuNewSimulation);

        menuOpenSImulation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuOpenSImulation.setIcon(resourceMap.getIcon("menuOpenSImulation.icon")); // NOI18N
        menuOpenSImulation.setMnemonic('O');
        menuOpenSImulation.setText(resourceMap.getString("menuOpenSImulation.text")); // NOI18N
        menuOpenSImulation.setName("menuOpenSImulation"); // NOI18N
        fileMenu.add(menuOpenSImulation);

        menuSaveSimulation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSaveSimulation.setIcon(resourceMap.getIcon("menuSaveSimulation.icon")); // NOI18N
        menuSaveSimulation.setMnemonic('S');
        menuSaveSimulation.setText(resourceMap.getString("menuSaveSimulation.text")); // NOI18N
        menuSaveSimulation.setName("menuSaveSimulation"); // NOI18N
        fileMenu.add(menuSaveSimulation);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setIcon(resourceMap.getIcon("exitMenuItem.icon")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

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
        SimulationRealTime.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SimulationRealTime.setName("SimulationRealTime"); // NOI18N
        SimulationRealTime.setOpaque(true);

        NrSimulationNodes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NrSimulationNodes.setText(resourceMap.getString("NroNodes.text")); // NOI18N
        NrSimulationNodes.setToolTipText(resourceMap.getString("NroNodes.toolTipText")); // NOI18N
        NrSimulationNodes.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        NrSimulationNodes.setName("NroNodes"); // NOI18N

        simulationStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        simulationStatus.setText(resourceMap.getString("simulationStatus.text")); // NOI18N
        simulationStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        simulationStatus.setName("simulationStatus"); // NOI18N

        NrEvents.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NrEvents.setText(resourceMap.getString("NrEvents.text")); // NOI18N
        NrEvents.setToolTipText(resourceMap.getString("NrEvents.toolTipText")); // NOI18N
        NrEvents.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        NrEvents.setName("NrEvents"); // NOI18N

        SimulationTime.setBackground(resourceMap.getColor("SimulationTime.background")); // NOI18N
        SimulationTime.setFont(resourceMap.getFont("SimulationTime.font")); // NOI18N
        SimulationTime.setForeground(resourceMap.getColor("SimulationTime.foreground")); // NOI18N
        SimulationTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SimulationTime.setText(resourceMap.getString("SimulationTime.text")); // NOI18N
        SimulationTime.setToolTipText(resourceMap.getString("SimulationTime.toolTipText")); // NOI18N
        SimulationTime.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SimulationTime.setName("SimulationTime"); // NOI18N
        SimulationTime.setOpaque(true);

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addComponent(statusMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(simulationStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NrSimulationNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NrEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SimulationRealTime, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SimulationTime, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        statusPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {NrSimulationNodes, SimulationRealTime});

        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(simulationStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(NrSimulationNodes)
                    .addComponent(NrEvents, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(SimulationRealTime, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addComponent(SimulationTime, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap())
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusMessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                .addGap(3, 3, 3))
            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusAnimationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );

        statusPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {NrEvents, NrSimulationNodes, simulationStatus, statusMessageLabel});

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

        bindingGroup.bind();
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
            workbenchVisible = true;
            workbenchPanel1.setVisible(true);
            mainSplitPane.setVisible(true);
            mainSplitPane.setDividerLocation(.80);

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel NrEvents;
    protected javax.swing.JLabel NrSimulationNodes;
    protected javax.swing.JLabel SimulationRealTime;
    protected javax.swing.JLabel SimulationTime;
    protected javax.swing.JButton btnNew;
    protected javax.swing.JButton btnOpen;
    protected javax.swing.JButton btnProperties;
    protected javax.swing.JButton btnSave;
    protected javax.swing.JMenu jMenu1;
    protected javax.swing.JMenu jMenu2;
    protected javax.swing.JPopupMenu jPopupMenu1;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JPopupMenu.Separator jSeparator1;
    protected javax.swing.JSplitPane jSplitPane1;
    protected javax.swing.JTextPane jTextPane1;
    protected javax.swing.JPanel mainPanel;
    protected javax.swing.JSplitPane mainSplitPane;
    protected javax.swing.JToolBar mainToolbar;
    protected javax.swing.JMenuBar menuBar;
    protected javax.swing.JMenuItem menuNewSimulation;
    protected javax.swing.JMenuItem menuOpenSImulation;
    protected javax.swing.JMenuItem menuSaveSimulation;
    private javax.swing.JProgressBar progressBar;
    protected javax.swing.JLabel simulationStatus;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    protected javax.swing.JPanel statusPanel;
    protected org.mei.securesim.platform.ui.uiextended.BestTabbedPane tabbedTools;
    protected org.mei.securesim.platform.ui.WorkbenchPanel workbenchPanel1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
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
        simulationStatus.setText(value);
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
        this.simulationStatus.setText(state.toUpperCase());
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
}
