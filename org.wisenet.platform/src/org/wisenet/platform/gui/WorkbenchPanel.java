/*
 * WorkbenchPanel.java
 *
 * Created on Jan 23, 2010, 12:46:23 AM
 */
package org.wisenet.platform.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.wisenet.platform.common.ui.PlatformDialog;
import org.wisenet.platform.common.ui.PlatformFrame;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.gui.panels.NodeSettingsPanel;
import org.wisenet.platform.gui.panels.RoutingInfoPanel;
import org.wisenet.platform.utils.GUI_Utils;
import org.wisenet.platform.utils.others.MapViewer;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.components.simulation.Simulation;

import org.wisenet.simulator.utilities.DebugConsole;
import org.wisenet.simulator.components.simulation.SimulationFactory;
import org.wisenet.simulator.core.node.Node;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class WorkbenchPanel extends javax.swing.JPanel {

    /** Creates new form WorkbenchPanel */
    public WorkbenchPanel() {
        initComponents();
        jScrollPane1.setPreferredSize(new Dimension(1280, 800));
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
        btnDeployNodesMode = new javax.swing.JToggleButton();
        btnSelectionTool = new javax.swing.JToggleButton();
        selectionPointerTool = new javax.swing.JToggleButton();
        topToolbar = new javax.swing.JToolBar();
        showDebugWindow = new javax.swing.JToggleButton();
        showMouseCoordinates = new javax.swing.JToggleButton();
        viewNodesInfo = new javax.swing.JToggleButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        selRandomNodes = new javax.swing.JButton();
        btnSnapshot = new javax.swing.JButton();
        btnColorSettings = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        gmaps = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnStableMark = new javax.swing.JToggleButton();
        btnStableSelect = new javax.swing.JToggleButton();
        btnRoutingInfo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        searchPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtSearchNode = new javax.swing.JFormattedTextField();

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

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(WorkbenchPanel.class, this);
        btnRebuildNetwork.setAction(actionMap.get("RebuildNetwork")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(WorkbenchPanel.class);
        btnRebuildNetwork.setIcon(resourceMap.getIcon("btnRebuildNetwork.icon")); // NOI18N
        btnRebuildNetwork.setToolTipText(resourceMap.getString("btnRebuildNetwork.toolTipText")); // NOI18N
        btnRebuildNetwork.setFocusable(false);
        btnRebuildNetwork.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRebuildNetwork.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnRebuildNetwork);
        jToolBar1.add(jSeparator4);

        verVizinhos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/Neighboors16.png"))); // NOI18N
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

        verOsQueConhecem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/OthersNeighboor16.png"))); // NOI18N
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
        btnSelectionTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/arrow_expand.png"))); // NOI18N
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
        selectionPointerTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/018.png"))); // NOI18N
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

        add(jToolBar1, java.awt.BorderLayout.LINE_START);

        topToolbar.setFloatable(false);
        topToolbar.setRollover(true);

        showDebugWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/terminal-icon.png"))); // NOI18N
        showDebugWindow.setToolTipText("Debug Console");
        showDebugWindow.setFocusable(false);
        showDebugWindow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showDebugWindow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showDebugWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDebugWindowActionPerformed(evt);
            }
        });
        topToolbar.add(showDebugWindow);

        showMouseCoordinates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/coordinates-icon.png"))); // NOI18N
        showMouseCoordinates.setToolTipText("Show Mouse Coordinates");
        showMouseCoordinates.setFocusable(false);
        showMouseCoordinates.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showMouseCoordinates.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showMouseCoordinates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMouseCoordinatesActionPerformed(evt);
            }
        });
        topToolbar.add(showMouseCoordinates);

        viewNodesInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/about-icon.png"))); // NOI18N
        viewNodesInfo.setToolTipText("View Nodes Info");
        viewNodesInfo.setFocusable(false);
        viewNodesInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        viewNodesInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        viewNodesInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewNodesInfoActionPerformed(evt);
            }
        });
        topToolbar.add(viewNodesInfo);
        topToolbar.add(jSeparator6);

        selRandomNodes.setAction(actionMap.get("RandomNodeSelection")); // NOI18N
        selRandomNodes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/random.png"))); // NOI18N
        selRandomNodes.setFocusable(false);
        selRandomNodes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selRandomNodes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        topToolbar.add(selRandomNodes);

        btnSnapshot.setAction(actionMap.get("TakeSnapshot")); // NOI18N
        btnSnapshot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/camera.png"))); // NOI18N
        btnSnapshot.setToolTipText(resourceMap.getString("btnSnapshot.toolTipText")); // NOI18N
        btnSnapshot.setFocusable(false);
        btnSnapshot.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSnapshot.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        topToolbar.add(btnSnapshot);

        btnColorSettings.setAction(actionMap.get("ColorSettingsAction")); // NOI18N
        btnColorSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/ColorSettings.png"))); // NOI18N
        btnColorSettings.setFocusable(false);
        btnColorSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColorSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        topToolbar.add(btnColorSettings);
        topToolbar.add(jSeparator5);

        gmaps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/GMaps.png"))); // NOI18N
        gmaps.setToolTipText("Insert Google Maps");
        gmaps.setFocusable(false);
        gmaps.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gmaps.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gmaps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gmapsActionPerformed(evt);
            }
        });
        topToolbar.add(gmaps);
        topToolbar.add(jSeparator3);

        btnStableMark.setText("Stable Mark");
        btnStableMark.setFocusable(false);
        btnStableMark.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStableMark.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStableMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStableMarkActionPerformed(evt);
            }
        });
        topToolbar.add(btnStableMark);

        btnStableSelect.setText("Stable Select");
        btnStableSelect.setFocusable(false);
        btnStableSelect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStableSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStableSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStableSelectActionPerformed(evt);
            }
        });
        topToolbar.add(btnStableSelect);

        btnRoutingInfo.setText("Routing Info");
        btnRoutingInfo.setFocusable(false);
        btnRoutingInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRoutingInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRoutingInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoutingInfoActionPerformed(evt);
            }
        });
        topToolbar.add(btnRoutingInfo);

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        topToolbar.add(jPanel1);

        searchPanel.setPreferredSize(new java.awt.Dimension(200, 25));
        searchPanel.setLayout(new java.awt.BorderLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/gui/resources/Search16.png"))); // NOI18N
        jButton1.setToolTipText("Search a node by ID");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        searchPanel.add(jButton1, java.awt.BorderLayout.LINE_END);

        txtSearchNode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtSearchNode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        searchPanel.add(txtSearchNode, java.awt.BorderLayout.CENTER);

        topToolbar.add(searchPanel);

        add(topToolbar, java.awt.BorderLayout.PAGE_START);
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
        MapViewer mapViewer = new MapViewer(null);
        mapViewer.setModal(true);
        mapViewer.setVisible(true);
        BufferedImage image = mapViewer.getImage();
        if (mapViewer.isApplyOk()) {
            getSimulationPanel().updateImage(image);
        }
}//GEN-LAST:event_gmapsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String s = txtSearchNode.getText();
        if (s != null) {
            try {
                simulationPanel1.searchNode(Integer.valueOf(s).intValue());
            } catch (Exception e) {
                GUI_Utils.showException(e);
            }

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup OperationBG;
    private javax.swing.ButtonGroup SelectionBG;
    private javax.swing.JButton btnColorSettings;
    private javax.swing.JToggleButton btnDeployNodesMode;
    private javax.swing.JButton btnRebuildNetwork;
    private javax.swing.JButton btnRoutingInfo;
    private javax.swing.JToggleButton btnSelectionTool;
    private javax.swing.JToggleButton btnSimulationPause;
    private javax.swing.JButton btnSimulationReset;
    private javax.swing.JToggleButton btnSimulationStart;
    private javax.swing.JToggleButton btnSimulationStop;
    private javax.swing.JButton btnSnapshot;
    private javax.swing.JToggleButton btnStableMark;
    private javax.swing.JToggleButton btnStableSelect;
    private javax.swing.JButton gmaps;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JButton selRandomNodes;
    private javax.swing.JToggleButton selectionPointerTool;
    private javax.swing.JToggleButton showDebugWindow;
    private javax.swing.JToggleButton showMouseCoordinates;
    private org.wisenet.platform.gui.SimulationPanel simulationPanel1;
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

    @Action
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

    @Action
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

    @Action
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

    @Action
    public void ResetSimulation() {
        try {
            if (getSimulationPanel().getSimulation().getSimulator().getNodes().size() > 0) {
                simulationPanel1.resetSimulation();
            }
            btnSimulationStart.setSelected(false);
            btnSimulationPause.setSelected(false);
            btnSimulationStop.setSelected(false);
        } catch (Exception e) {
        }
    }

    @Action(block = Task.BlockingScope.COMPONENT)
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

    @Action
    public void selectedNodeDeployMode() {
        updateSelectionGroup();

    }

    @Action
    public void RandomNodeSelection() {
        simulationPanel1.selectRandomNodes(10);

    }

    @Action
    public void TakeSnapshot() {
        simulationPanel1.takeSnapshot();
    }

    @Action
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
}
