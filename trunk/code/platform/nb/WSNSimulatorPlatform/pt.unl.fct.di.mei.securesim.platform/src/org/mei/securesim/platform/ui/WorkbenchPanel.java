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
import org.mei.securesim.components.instruments.SimulationController;
import org.mei.securesim.components.simulation.SimulationFactory;
import org.mei.securesim.utils.DebugConsole;

/**
 *
 * @author posilva
 */
public class WorkbenchPanel extends javax.swing.JPanel  {

    /** Creates new form WorkbenchPanel */
    public WorkbenchPanel() {
        initComponents();
        
                jScrollPane1.setPreferredSize(new Dimension(1280,800));
        jScrollPane1.setAutoscrolls(true);
        btnDeployNodesMode.setSelected(true);
        updateSelectionGroup();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SelectionBG = new javax.swing.ButtonGroup();
        OperationBG = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        simulationPanel1 = new org.mei.securesim.platform.ui.SimulationPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnRebuildNetwork = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        verVizinhos = new javax.swing.JToggleButton();
        verOsQueConhecem = new javax.swing.JToggleButton();
        searchNode = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSimulationStart = new javax.swing.JToggleButton();
        btnSimulationPause = new javax.swing.JToggleButton();
        btnSimulationStop = new javax.swing.JToggleButton();
        btnSimulationReset = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDeployNodesMode = new javax.swing.JToggleButton();
        btnSelectionTool = new javax.swing.JToggleButton();
        selectionPointerTool = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        showDebugWindow = new javax.swing.JToggleButton();
        showMouseCoordinates = new javax.swing.JToggleButton();
        viewNodesInfo = new javax.swing.JToggleButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        selRandomNodes = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout simulationPanel1Layout = new javax.swing.GroupLayout(simulationPanel1);
        simulationPanel1.setLayout(simulationPanel1Layout);
        simulationPanel1Layout.setHorizontalGroup(
            simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1018, Short.MAX_VALUE)
        );
        simulationPanel1Layout.setVerticalGroup(
            simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(simulationPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getActionMap(WorkbenchPanel.class, this);
        btnRebuildNetwork.setAction(actionMap.get("RebuildNetwork")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(WorkbenchPanel.class);
        btnRebuildNetwork.setIcon(resourceMap.getIcon("btnRebuildNetwork.icon")); // NOI18N
        btnRebuildNetwork.setToolTipText(resourceMap.getString("btnRebuildNetwork.toolTipText")); // NOI18N
        btnRebuildNetwork.setFocusable(false);
        btnRebuildNetwork.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRebuildNetwork.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnRebuildNetwork);
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
        btnSimulationStart.setIcon(resourceMap.getIcon("btnSimulationStart.icon")); // NOI18N
        btnSimulationStart.setToolTipText(resourceMap.getString("btnSimulationStart.toolTipText")); // NOI18N
        btnSimulationStart.setFocusable(false);
        btnSimulationStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationStart);

        btnSimulationPause.setAction(actionMap.get("PauseSimulation")); // NOI18N
        OperationBG.add(btnSimulationPause);
        btnSimulationPause.setIcon(resourceMap.getIcon("btnSimulationPause.icon")); // NOI18N
        btnSimulationPause.setToolTipText(resourceMap.getString("btnSimulationPause.toolTipText")); // NOI18N
        btnSimulationPause.setFocusable(false);
        btnSimulationPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationPause);

        btnSimulationStop.setAction(actionMap.get("StopSimulation")); // NOI18N
        OperationBG.add(btnSimulationStop);
        btnSimulationStop.setIcon(resourceMap.getIcon("btnSimulationStop.icon")); // NOI18N
        btnSimulationStop.setToolTipText(resourceMap.getString("btnSimulationStop.toolTipText")); // NOI18N
        btnSimulationStop.setFocusable(false);
        btnSimulationStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationStop);

        btnSimulationReset.setAction(actionMap.get("ResetSimulation")); // NOI18N
        btnSimulationReset.setIcon(resourceMap.getIcon("btnSimulationReset.icon")); // NOI18N
        btnSimulationReset.setToolTipText(resourceMap.getString("btnSimulationReset.toolTipText")); // NOI18N
        btnSimulationReset.setFocusable(false);
        btnSimulationReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationReset);
        jToolBar1.add(jSeparator2);

        btnDeployNodesMode.setAction(actionMap.get("selectedNodeDeployMode")); // NOI18N
        SelectionBG.add(btnDeployNodesMode);
        btnDeployNodesMode.setIcon(resourceMap.getIcon("btnDeployNodesMode.icon")); // NOI18N
        btnDeployNodesMode.setToolTipText(resourceMap.getString("btnDeployNodesMode.toolTipText")); // NOI18N
        btnDeployNodesMode.setFocusable(false);
        btnDeployNodesMode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeployNodesMode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnDeployNodesMode);

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
        jToolBar1.add(jSeparator5);

        selRandomNodes.setAction(actionMap.get("RandomNodeSelection")); // NOI18N
        selRandomNodes.setText("R");
        selRandomNodes.setFocusable(false);
        selRandomNodes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selRandomNodes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(selRandomNodes);

        add(jToolBar1, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void verVizinhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verVizinhosActionPerformed

        simulationPanel1.viewVizinhos(verVizinhos.isSelected());
    }//GEN-LAST:event_verVizinhosActionPerformed

    private void btnSelectionToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectionToolActionPerformed
        updateSelectionGroup();
    }//GEN-LAST:event_btnSelectionToolActionPerformed

    private void verOsQueConhecemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verOsQueConhecemActionPerformed
        simulationPanel1.viewOsQueConhecem(verOsQueConhecem.isSelected());
    }//GEN-LAST:event_verOsQueConhecemActionPerformed

    private void searchNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchNodeActionPerformed

        String s = JOptionPane.showInputDialog("Insert Sensor ID:");
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

        simulationPanel1.setViewNodeInfo(viewNodesInfo.isSelected());
    }//GEN-LAST:event_viewNodesInfoActionPerformed

    private void selectionPointerToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectionPointerToolActionPerformed
        updateSelectionGroup();
    }//GEN-LAST:event_selectionPointerToolActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup OperationBG;
    private javax.swing.ButtonGroup SelectionBG;
    private javax.swing.JToggleButton btnDeployNodesMode;
    private javax.swing.JButton btnRebuildNetwork;
    private javax.swing.JToggleButton btnSelectionTool;
    private javax.swing.JToggleButton btnSimulationPause;
    private javax.swing.JButton btnSimulationReset;
    private javax.swing.JToggleButton btnSimulationStart;
    private javax.swing.JToggleButton btnSimulationStop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton searchNode;
    private javax.swing.JButton selRandomNodes;
    private javax.swing.JToggleButton selectionPointerTool;
    private javax.swing.JToggleButton showDebugWindow;
    private javax.swing.JToggleButton showMouseCoordinates;
    private org.mei.securesim.platform.ui.SimulationPanel simulationPanel1;
    private javax.swing.JToggleButton verOsQueConhecem;
    private javax.swing.JToggleButton verVizinhos;
    private javax.swing.JToggleButton viewNodesInfo;
    // End of variables declaration//GEN-END:variables

    private void updateSelectionGroup() {
        simulationPanel1.deployNodesToolSelected(btnDeployNodesMode.isSelected());
        simulationPanel1.selectionToolSelected(btnSelectionTool.isSelected());
        simulationPanel1.selectionPointerSelected(selectionPointerTool.isSelected());

    }

    public void setSimulationFactory(SimulationFactory simulationFactory) {
    simulationPanel1.settingSimulation(simulationFactory);
    }

    @Action
    public void StartSimulation() {
        simulationPanel1.startSimulation();
        SimulationController.getInstance().getSimulationPlatform().updateSimulationState("START");
    }

    @Action
    public void PauseSimulation() {
        simulationPanel1.pauseSimulation();
        SimulationController.getInstance().getSimulationPlatform().updateSimulationState("PAUSE");
    }

    @Action
    public void StopSimulation() {
        simulationPanel1.stopSimulation();
        SimulationController.getInstance().getSimulationPlatform().updateSimulationState("STOP");

    }

    @Action
    public void ResetSimulation() {
        simulationPanel1.clearSimulation();
        SimulationController.getInstance().getSimulationPlatform().updateSimulationState("RESET");
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
            long start = System.currentTimeMillis();
            setMessage("Building Network...");
            simulationPanel1.buildNetwork();
            setMessage("Building Network... done in " +(System.currentTimeMillis() -start ) + " milliseconds");
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    @Action
    public void selectedNodeDeployMode() {
        updateSelectionGroup();
    }

    @Action
    public void RandomNodeSelection() {
        simulationPanel1.selectRandomNodes(10);

    }
}
