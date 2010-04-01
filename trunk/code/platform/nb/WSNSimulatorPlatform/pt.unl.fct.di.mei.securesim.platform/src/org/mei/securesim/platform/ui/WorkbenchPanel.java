/*
 * WorkbenchPanel.java
 *
 * Created on Jan 23, 2010, 12:46:23 AM
 */
package org.mei.securesim.platform.ui;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import org.mei.securesim.components.simulation.SimulationFactory;
import org.mei.securesim.utils.DebugConsole;

/**
 *
 * @author posilva
 */
public class WorkbenchPanel extends javax.swing.JPanel {

    /** Creates new form WorkbenchPanel */
    public WorkbenchPanel() {
        initComponents();
        jScrollPane1.setPreferredSize(new Dimension(100, 100));
        jScrollPane1.setAutoscrolls(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SelectionBG = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        simulationPanel1 = new org.mei.securesim.platform.ui.SimulationPanel();
        toolbarPanel = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        selectionPointerTool = new javax.swing.JToggleButton();
        btnSelectionTool = new javax.swing.JToggleButton();
        deployNodes = new javax.swing.JToggleButton();
        showMouseCoordinates = new javax.swing.JToggleButton();
        showDebugWindow = new javax.swing.JToggleButton();
        viewNodesInfo = new javax.swing.JToggleButton();
        jToolBar1 = new javax.swing.JToolBar();
        verVizinhos = new javax.swing.JToggleButton();
        verOsQueConhecem = new javax.swing.JToggleButton();
        searchNode = new javax.swing.JButton();
        clearSimulation = new javax.swing.JButton();

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

        SelectionBG.add(selectionPointerTool);
        selectionPointerTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/hand-icon.png"))); // NOI18N
        selectionPointerTool.setToolTipText("Select Node");
        selectionPointerTool.setFocusable(false);
        selectionPointerTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectionPointerTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectionPointerTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionPointerToolActionPerformed(evt);
            }
        });
        jToolBar2.add(selectionPointerTool);

        SelectionBG.add(btnSelectionTool);
        btnSelectionTool.setSelected(true);
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
        jToolBar2.add(btnSelectionTool);

        SelectionBG.add(deployNodes);
        deployNodes.setText("DN");
        deployNodes.setFocusable(false);
        deployNodes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deployNodes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deployNodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deployNodesActionPerformed(evt);
            }
        });
        jToolBar2.add(deployNodes);

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
        jToolBar2.add(showMouseCoordinates);

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
        jToolBar2.add(showDebugWindow);

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
        jToolBar2.add(viewNodesInfo);

        add(jToolBar2, java.awt.BorderLayout.LINE_END);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        verVizinhos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/vizinhos-icon.png"))); // NOI18N
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

        verOsQueConhecem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/connect-icon.png"))); // NOI18N
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

        searchNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mei/securesim/platform/ui/resources/search-icon.png"))); // NOI18N
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

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(WorkbenchPanel.class);
        clearSimulation.setIcon(resourceMap.getIcon("clearSimulation.icon")); // NOI18N
        clearSimulation.setToolTipText("Clear Network");
        clearSimulation.setFocusable(false);
        clearSimulation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clearSimulation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clearSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSimulationActionPerformed(evt);
            }
        });
        jToolBar1.add(clearSimulation);

        add(jToolBar1, java.awt.BorderLayout.NORTH);
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

    private void deployNodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deployNodesActionPerformed
        // TODO add your handling code here:

        updateSelectionGroup();



    }//GEN-LAST:event_deployNodesActionPerformed

    private void clearSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSimulationActionPerformed
        simulationPanel1.clearSimulation();
    }//GEN-LAST:event_clearSimulationActionPerformed

    private void viewNodesInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewNodesInfoActionPerformed
        // TODO add your handling code here:
        simulationPanel1.setViewNodeInfo(viewNodesInfo.isSelected());
    }//GEN-LAST:event_viewNodesInfoActionPerformed

    private void selectionPointerToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectionPointerToolActionPerformed
        updateSelectionGroup();
    }//GEN-LAST:event_selectionPointerToolActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup SelectionBG;
    private javax.swing.JToggleButton btnSelectionTool;
    private javax.swing.JButton clearSimulation;
    private javax.swing.JToggleButton deployNodes;
    private javax.swing.JScrollPane jScrollPane1;
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
        simulationPanel1.deployNodesToolSelected(deployNodes.isSelected());
        simulationPanel1.selectionToolSelected(btnSelectionTool.isSelected());
        simulationPanel1.selectionPointerSelected(selectionPointerTool.isSelected());
    }



    public void setSimulationFactory(SimulationFactory simulationFactory) {
        simulationPanel1.settingSimulation(simulationFactory);
    }
}
