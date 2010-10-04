/*
 * WorkbenchPanel.java
 *
 * Created on Jan 23, 2010, 12:46:23 AM
 */
package org.wisenet.platform.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.wisenet.platform.common.ui.PlatformDialog;
import org.wisenet.platform.common.ui.PlatformFrame;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.platform.gui.listeners.DeployEvent;
import org.wisenet.platform.gui.listeners.SimulationPanelEventListener;
import org.wisenet.platform.gui.panels.ImageViewerPanel;
import org.wisenet.platform.gui.panels.NodeSettingsPanel;
import org.wisenet.platform.gui.panels.RoutingInfoPanel;
import org.wisenet.platform.utils.GUI_Utils;
import org.wisenet.simulator.components.instruments.NodeSelectionCondition;
import org.wisenet.simulator.components.simulation.Simulation;

import org.wisenet.simulator.utilities.DebugConsole;
import org.wisenet.simulator.components.simulation.SimulationFactory;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.utilities.Utilities;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class WorkbenchPanel extends javax.swing.JPanel implements SimulationPanelEventListener {

    /** Creates new form WorkbenchPanel */
    public WorkbenchPanel() {
        initComponents();
        simulationPanel1.addSimulationPanelListerner(this);

        jScrollPane1.setPreferredSize(new Dimension(1280, 800));
        jScrollPane1.setAutoscrolls(true);
        btnDeployNodesMode.setSelected(true);
        updateSelectionGroup();
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
        btnLoadTopology = new javax.swing.JButton();
        btnSaveTopology = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnStableMark = new javax.swing.JToggleButton();
        btnStableSelect = new javax.swing.JToggleButton();
        btnRoutingInfo = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        tgbRoutingAttackMode = new javax.swing.JToggleButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        searchPanel1 = new javax.swing.JPanel();
        searchPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtSearchNode = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        lblField = new javax.swing.JLabel();

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

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(WorkbenchPanel.class, this);
        btnRebuildNetwork.setAction(actionMap.get("RebuildNetwork")); // NOI18N
        btnRebuildNetwork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/refresh.png"))); // NOI18N
        btnRebuildNetwork.setFocusable(false);
        btnRebuildNetwork.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRebuildNetwork.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnRebuildNetwork);
        jToolBar1.add(jSeparator4);

        verVizinhos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Neighboors16.png"))); // NOI18N
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
        jToolBar1.add(verOsQueConhecem);
        jToolBar1.add(jSeparator1);

        btnSimulationStart.setAction(actionMap.get("StartSimulation")); // NOI18N
        OperationBG.add(btnSimulationStart);
        btnSimulationStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Play16.png"))); // NOI18N
        btnSimulationStart.setFocusable(false);
        btnSimulationStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationStart);

        btnSimulationPause.setAction(actionMap.get("PauseSimulation")); // NOI18N
        OperationBG.add(btnSimulationPause);
        btnSimulationPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Pause16.png"))); // NOI18N
        btnSimulationPause.setFocusable(false);
        btnSimulationPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationPause);

        btnSimulationStop.setAction(actionMap.get("StopSimulation")); // NOI18N
        OperationBG.add(btnSimulationStop);
        btnSimulationStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Stop16.png"))); // NOI18N
        btnSimulationStop.setFocusable(false);
        btnSimulationStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationStop);

        btnSimulationReset.setAction(actionMap.get("ResetSimulation")); // NOI18N
        btnSimulationReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Reset16.png"))); // NOI18N
        btnSimulationReset.setFocusable(false);
        btnSimulationReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimulationReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSimulationReset);
        jToolBar1.add(jSeparator2);

        btnDeployNodesMode.setAction(actionMap.get("selectedNodeDeployMode")); // NOI18N
        SelectionBG.add(btnDeployNodesMode);
        btnDeployNodesMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/share_this.png"))); // NOI18N
        btnDeployNodesMode.setFocusable(false);
        btnDeployNodesMode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeployNodesMode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnDeployNodesMode);

        SelectionBG.add(btnSelectionTool);
        btnSelectionTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/arrow_expand.png"))); // NOI18N
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
        selectionPointerTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/018.png"))); // NOI18N
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

        topToolbar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topToolbar.setFloatable(false);
        topToolbar.setRollover(true);

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
        topToolbar.add(showDebugWindow);

        showMouseCoordinates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/coordinates-icon.png"))); // NOI18N
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
        topToolbar.add(viewNodesInfo);
        topToolbar.add(jSeparator6);

        selRandomNodes.setAction(actionMap.get("RandomNodeSelection")); // NOI18N
        selRandomNodes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/random.png"))); // NOI18N
        selRandomNodes.setFocusable(false);
        selRandomNodes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selRandomNodes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        topToolbar.add(selRandomNodes);

        btnSnapshot.setAction(actionMap.get("TakeSnapshot")); // NOI18N
        btnSnapshot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/camera.png"))); // NOI18N
        btnSnapshot.setFocusable(false);
        btnSnapshot.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSnapshot.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        topToolbar.add(btnSnapshot);

        btnColorSettings.setAction(actionMap.get("ColorSettingsAction")); // NOI18N
        btnColorSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/ColorSettings.png"))); // NOI18N
        btnColorSettings.setFocusable(false);
        btnColorSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColorSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        topToolbar.add(btnColorSettings);
        topToolbar.add(jSeparator5);

        gmaps.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/GMaps.png"))); // NOI18N
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
        topToolbar.add(jSeparator3);

        btnLoadTopology.setText("LT");
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

        btnSaveTopology.setText("ST");
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
        topToolbar.add(jSeparator8);

        tgbRoutingAttackMode.setText("Routing Attack Mode"); // NOI18N
        topToolbar.add(tgbRoutingAttackMode);
        topToolbar.add(jSeparator9);

        searchPanel1.setPreferredSize(new java.awt.Dimension(100, 25));
        searchPanel1.setLayout(new java.awt.BorderLayout());
        topToolbar.add(searchPanel1);

        searchPanel.setPreferredSize(new java.awt.Dimension(100, 25));
        searchPanel.setLayout(new java.awt.BorderLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wisenet/platform/resources/images/Search16.png"))); // NOI18N
        jButton1.setToolTipText("Search a node by ID");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        searchPanel.add(jButton1, java.awt.BorderLayout.LINE_START);

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

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(1137, 25));

        lblField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblField.setToolTipText("Covered field size");
        lblField.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblField.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(911, Short.MAX_VALUE)
                .addComponent(lblField, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblField, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
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
        ImageViewerPanel ivp = new ImageViewerPanel();
        PlatformDialog f = PlatformDialog.display(ivp, "Background image", PlatformFrame.OKCANCEL_MODE);
        if (ivp.getImage() != null) {
            getSimulationPanel().updateImage(ivp.getImage(), ivp.strechImage());
        }


        //        MapViewer mapViewer = new MapViewer(null);
//        mapViewer.setModal(true);
//        mapViewer.setVisible(true);
//        BufferedImage image = mapViewer.getImage();
//        if (mapViewer.isApplyOk()) {
//            getSimulationPanel().updateImage(image);
//        }


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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup OperationBG;
    private javax.swing.ButtonGroup SelectionBG;
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
    private javax.swing.JButton gmaps;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblField;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JPanel searchPanel1;
    private javax.swing.JButton selRandomNodes;
    private javax.swing.JToggleButton selectionPointerTool;
    private javax.swing.JToggleButton showDebugWindow;
    private javax.swing.JToggleButton showMouseCoordinates;
    private org.wisenet.platform.gui.SimulationPanel simulationPanel1;
    private javax.swing.JToggleButton tgbRoutingAttackMode;
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

    private void loadNetworkTopology() {
        try {
            String file = GUI_Utils.showOpenDialog(new FileFilter[]{GUI_Utils.XML()}, "Open Network Topology File");
            if (file != null) {
                String valid = Utilities.networkTopologyFileIsValid(file);
                if (valid != null) {
                    GUI_Utils.showWarningMessage(valid);
                } else {
                    GUI_Utils.mouseWait(this);
                    PlatformManager.getInstance().getActiveSimulation().loadNetworkTopology(file);
                    GUI_Utils.mouseDefault(this);
                    PlatformManager.getInstance().getActiveSimulation().buildNetwork();
                }
            }
        } catch (Exception ex) {
            GUI_Utils.mouseDefault(this);
            GUI_Utils.showException(ex);
        }
    }

    private void saveNetworkTopology() {
        try {
            String file = GUI_Utils.showSaveDialog(new FileFilter[]{GUI_Utils.XML()}, "Save Network Topology ");
            if (file != null) {
                GUI_Utils.mouseWait(this);
                PlatformManager.getInstance().getActiveSimulation().saveNetworkTopology(file);
                GUI_Utils.showMessage("Network Topology saved!");
                GUI_Utils.mouseDefault(this);
            }
        } catch (Exception ex) {
            GUI_Utils.mouseDefault(this);
            GUI_Utils.showException(ex);
        }

    }

    @Override
    public void afterNodeDeploy(DeployEvent event) {

        Dimension d = PlatformManager.getInstance().getActiveSimulation().fieldSize();

        lblField.setText(d.getWidth() + "-" + d.getHeight());
    }

    @Override
    public void beforeNodeDeploy(DeployEvent event) {
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
