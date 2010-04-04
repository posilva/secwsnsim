/*
 * WorkbenchPanel.java
 *
 * Created on Jan 23, 2010, 12:46:23 AM
 */
package org.mei.securesim.platform.ui;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.mei.securesim.components.simulation.SimulationFactory;
import org.mei.securesim.platform.uiextended.DropDownButton;
import org.mei.securesim.utils.DebugConsole;

/**
 *
 * @author posilva
 */
public class WorkbenchPanel extends javax.swing.JPanel {

    /** Creates new form WorkbenchPanel */
    public WorkbenchPanel() {
        initComponents();
        btnDeployMode.setButtonStyle(DropDownButton.TOOLBOX_STYLE);
        btnDeployMode.setButtonSelected(true);
        btnDeployMode.add(deployRandomNodes);
        btnSelectionTool.setSelected(false);
        selectionPointerTool.setSelected(false);
        jScrollPane1.setPreferredSize(new Dimension(100, 100));
        jScrollPane1.setAutoscrolls(true);
        updateSelectionGroup();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deployRandomNodes = new javax.swing.JMenuItem();
        SelectionBG = new javax.swing.ButtonGroup();
        OperationBG = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        simulationPanel1 = new org.mei.securesim.platform.ui.SimulationPanel();
        toolbarPanel = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        verVizinhos = new javax.swing.JToggleButton();
        verOsQueConhecem = new javax.swing.JToggleButton();
        searchNode = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSimulationStart = new javax.swing.JToggleButton();
        btnSimulationPause = new javax.swing.JToggleButton();
        btnSimulationStop = new javax.swing.JToggleButton();
        btnSimulationReset = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDeployMode = new org.mei.securesim.platform.uiextended.DropDownButton();
        btnSelectionTool = new javax.swing.JToggleButton();
        selectionPointerTool = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        showDebugWindow = new javax.swing.JToggleButton();
        showMouseCoordinates = new javax.swing.JToggleButton();
        viewNodesInfo = new javax.swing.JToggleButton();

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getActionMap(WorkbenchPanel.class, this);
        deployRandomNodes.setAction(actionMap.get("DeployInRandomMode")); // NOI18N
        deployRandomNodes.setText("Random Mode");

        setLayout(new java.awt.BorderLayout());

        toolbarPanel.setLayout(new javax.swing.BoxLayout(toolbarPanel, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout simulationPanel1Layout = new javax.swing.GroupLayout(simulationPanel1);
        simulationPanel1.setLayout(simulationPanel1Layout);
        simulationPanel1Layout.setHorizontalGroup(
            simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1018, Short.MAX_VALUE)
            .addGroup(simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(simulationPanel1Layout.createSequentialGroup()
                    .addGap(0, 227, Short.MAX_VALUE)
                    .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 228, Short.MAX_VALUE)))
        );
        simulationPanel1Layout.setVerticalGroup(
            simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
            .addGroup(simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(simulationPanel1Layout.createSequentialGroup()
                    .addGap(0, 314, Short.MAX_VALUE)
                    .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 314, Short.MAX_VALUE)))
        );

        jScrollPane1.setViewportView(simulationPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar2.setOrientation(1);
        jToolBar2.setRollover(true);
        jToolBar2.setAutoscrolls(true);
        add(jToolBar2, java.awt.BorderLayout.LINE_END);

        jToolBar1.setRollover(true);

        jButton1.setAction(actionMap.get("RebuildNetwork")); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/refresh.png"))); // NOI18N
        jButton1.setToolTipText("Rebuild Network");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator4);

        verVizinhos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/Neighboors16.png"))); // NOI18N
        verVizinhos.setToolTipText("Show Neighborhood ");
        verVizinhos.setFocusable(false);
        verVizinhos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        verVizinhos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        verVizinhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verVizinhosActionPerformed(evt);
            }
        });
        jToolBar1.add(verVizinhos);

        verOsQueConhecem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/OthersNeighboor16.png"))); // NOI18N
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

        searchNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/Search16.png"))); // NOI18N
        searchNode.setToolTipText("Search a Node");
        searchNode.setFocusable(false);
        searchNode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        searchNode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        searchNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNodeActionPerformed(evt);
            }
        });
        jToolBar1.add(searchNode);
        jToolBar1.add(jSeparator1);

        btnSimulationStart.setAction(actionMap.get("StartSimulation")); // NOI18N
        OperationBG.add(btnSimulationStart);
        btnSimulationStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/Play16.png"))); // NOI18N
        btnSimulationStart.setToolTipText("Play Simulation");
        btnSimulationStart.setFocusable(false);
        btnSimulationStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationStart);

        btnSimulationPause.setAction(actionMap.get("PauseSimulation")); // NOI18N
        OperationBG.add(btnSimulationPause);
        btnSimulationPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/Pause16.png"))); // NOI18N
        btnSimulationPause.setToolTipText("Pause Simulation");
        btnSimulationPause.setFocusable(false);
        btnSimulationPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationPause);

        btnSimulationStop.setAction(actionMap.get("StopSimulation")); // NOI18N
        OperationBG.add(btnSimulationStop);
        btnSimulationStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/Stop16.png"))); // NOI18N
        btnSimulationStop.setToolTipText("Stop Simulation (End Simulation)");
        btnSimulationStop.setFocusable(false);
        btnSimulationStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationStop);

        btnSimulationReset.setAction(actionMap.get("ResetSimulation")); // NOI18N
        OperationBG.add(btnSimulationReset);
        btnSimulationReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/Reset16.png"))); // NOI18N
        btnSimulationReset.setToolTipText("Reset Simulation");
        btnSimulationReset.setFocusable(false);
        btnSimulationReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationReset);
        jToolBar1.add(jSeparator2);

        btnDeployMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/Hand16.png"))); // NOI18N
        btnDeployMode.setToolTipText("Select Nodes Deploy Modes");
        SelectionBG.add(btnDeployMode);
        btnDeployMode.setButtonSelected(true);
        btnDeployMode.setDelay(1);
        btnDeployMode.setFocusable(false);
        btnDeployMode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeployMode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeployMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeployModeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDeployMode);

        SelectionBG.add(btnSelectionTool);
        btnSelectionTool.setText("S");
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

        SelectionBG.add(selectionPointerTool);
        selectionPointerTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/Arrow16.png"))); // NOI18N
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
        jToolBar1.add(jSeparator3);

        showDebugWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/terminal-icon.png"))); // NOI18N
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

        showMouseCoordinates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/coordinates-icon.png"))); // NOI18N
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

        viewNodesInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/about-icon.png"))); // NOI18N
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

        add(jToolBar1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void verVizinhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verVizinhosActionPerformed

        simulationPanel1.viewVizinhos(verVizinhos.isSelected());
    }//GEN-LAST:event_verVizinhosActionPerformed

    private void btnSelectionToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectionToolActionPerformed
        selectionPointerTool.setSelected(false);
        btnSelectionTool.setSelected(true);
        btnDeployMode.setButtonSelected(false);

        updateSelectionGroup();
    }//GEN-LAST:event_btnSelectionToolActionPerformed

    private void verOsQueConhecemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verOsQueConhecemActionPerformed
        simulationPanel1.viewOsQueConhecem(verOsQueConhecem.isSelected());
    }//GEN-LAST:event_verOsQueConhecemActionPerformed

    private void searchNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchNodeActionPerformed
        // TODO add your handling code here:
        String s = JOptionPane.showInputDialog("Introduza o ID do Sensor:");
        if (s != null) {
            simulationPanel1.searchNode(Integer.valueOf(s).intValue());
        }

    }//GEN-LAST:event_searchNodeActionPerformed

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
        // TODO add your handling code here:
        simulationPanel1.setViewNodeInfo(viewNodesInfo.isSelected());
    }//GEN-LAST:event_viewNodesInfoActionPerformed

    private void selectionPointerToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectionPointerToolActionPerformed
        selectionPointerTool.setEnabled(true);
        btnSelectionTool.setSelected(false);
        btnDeployMode.setButtonSelected(false);

        updateSelectionGroup();
    }//GEN-LAST:event_selectionPointerToolActionPerformed

    private void btnDeployModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeployModeActionPerformed

        selectionPointerTool.setSelected(false);
        btnSelectionTool.setSelected(false);
        btnDeployMode.setButtonSelected(true);
        updateSelectionGroup();



        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeployModeActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup OperationBG;
    private javax.swing.ButtonGroup SelectionBG;
    private org.mei.securesim.platform.uiextended.DropDownButton btnDeployMode;
    private javax.swing.JToggleButton btnSelectionTool;
    private javax.swing.JToggleButton btnSimulationPause;
    private javax.swing.JToggleButton btnSimulationReset;
    private javax.swing.JToggleButton btnSimulationStart;
    private javax.swing.JToggleButton btnSimulationStop;
    private javax.swing.JMenuItem deployRandomNodes;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton searchNode;
    private javax.swing.JToggleButton selectionPointerTool;
    private javax.swing.JToggleButton showDebugWindow;
    private javax.swing.JToggleButton showMouseCoordinates;
    private org.mei.securesim.platform.ui.SimulationPanel simulationPanel1;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JToggleButton verOsQueConhecem;
    private javax.swing.JToggleButton verVizinhos;
    private javax.swing.JToggleButton viewNodesInfo;
    // End of variables declaration//GEN-END:variables

    private void updateSelectionGroup() {
        simulationPanel1.deployNodesToolSelected(btnDeployMode.isButtonSelected());
        simulationPanel1.selectionToolSelected(btnSelectionTool.isSelected());
        simulationPanel1.selectionPointerSelected(selectionPointerTool.isSelected());


//        setDeployModeState();
    }

    public void setSimulationFactory(SimulationFactory simulationFactory) {
        simulationPanel1.settingSimulation(simulationFactory);
    }

    @Action(block = Task.BlockingScope.COMPONENT)
    public Task DeployInRandomMode() {
        return simulationPanel1.deployNodesUsingRandomTopology();
    }

    @Action
    public void StartSimulation() {
        simulationPanel1.startSimulation();
    }

    @Action
    public void PauseSimulation() {
        simulationPanel1.pauseSimulation();
    }

    @Action
    public void StopSimulation() {
        simulationPanel1.stopSimulation();
    }

    @Action
    public void ResetSimulation() {
        simulationPanel1.clearSimulation();
    }

    @Action(block = Task.BlockingScope.COMPONENT)
    public Task RebuildNetwork() {
        return new RebuildNetworkTask(org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class));



    }

    private class RebuildNetworkTask extends org.jdesktop.application.Task<Object, Void> {

        RebuildNetworkTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to RebuildNetworkTask fields, here.
            super(app);



        }

        @Override
        protected Object doInBackground() {
            setMessage("Building Network...");
            simulationPanel1.buildNetwork();
            setMessage("Building Network... done!");
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }
}
