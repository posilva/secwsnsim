/*
 * WorkbenchPanel.java
 *
 * Created on Jan 23, 2010, 12:46:23 AM
 */

package pt.unl.fct.di.mei.securesim.platform.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author posilva
 */
public class WorkbenchPanel extends javax.swing.JPanel {

    /** Creates new form WorkbenchPanel */
    public WorkbenchPanel() {
        initComponents();
        jScrollPane1.setPreferredSize(new Dimension(100,100));
        jScrollPane1.setAutoscrolls(true);
    }
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        simulationPanel1 = new pt.unl.fct.di.mei.securesim.platform.ui.SimulationPanel();
        toolbarPanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        verVizinhos = new javax.swing.JToggleButton();
        saveSimulation = new javax.swing.JButton();
        numberOfNodes = new javax.swing.JTextField();
        jToolBar2 = new javax.swing.JToolBar();
        btnSelectionTool = new javax.swing.JToggleButton();
        individualNodeSelection = new javax.swing.JToggleButton();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout simulationPanel1Layout = new javax.swing.GroupLayout(simulationPanel1);
        simulationPanel1.setLayout(simulationPanel1Layout);
        simulationPanel1Layout.setHorizontalGroup(
            simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1018, Short.MAX_VALUE)
        );
        simulationPanel1Layout.setVerticalGroup(
            simulationPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(simulationPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        toolbarPanel.setLayout(new javax.swing.BoxLayout(toolbarPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jToolBar1.setRollover(true);

        jButton1.setText("Deploy");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("Run");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        verVizinhos.setText("Vizinhos");
        verVizinhos.setFocusable(false);
        verVizinhos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        verVizinhos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        verVizinhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verVizinhosActionPerformed(evt);
            }
        });
        jToolBar1.add(verVizinhos);

        saveSimulation.setText("Save");
        saveSimulation.setFocusable(false);
        saveSimulation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveSimulation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSimulationActionPerformed(evt);
            }
        });
        jToolBar1.add(saveSimulation);

        numberOfNodes.setText("100");
        numberOfNodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberOfNodesActionPerformed(evt);
            }
        });
        jToolBar1.add(numberOfNodes);

        toolbarPanel.add(jToolBar1);

        add(toolbarPanel, java.awt.BorderLayout.PAGE_START);

        jToolBar2.setOrientation(1);
        jToolBar2.setRollover(true);
        jToolBar2.setAutoscrolls(true);

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

        individualNodeSelection.setText("N");
        individualNodeSelection.setFocusable(false);
        individualNodeSelection.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        individualNodeSelection.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        individualNodeSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                individualNodeSelectionActionPerformed(evt);
            }
        });
        jToolBar2.add(individualNodeSelection);

        add(jToolBar2, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        simulationPanel1.deployNetwork(getNumberOfNodes());
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        simulationPanel1.RunSimulation();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void verVizinhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verVizinhosActionPerformed

      simulationPanel1.viewVizinhos(verVizinhos.isSelected());
    }//GEN-LAST:event_verVizinhosActionPerformed

    private void btnSelectionToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectionToolActionPerformed
      simulationPanel1.selectionToolSelected(btnSelectionTool.isSelected());
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSelectionToolActionPerformed

    private void saveSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSimulationActionPerformed
        simulationPanel1.saveSimulation();
    }//GEN-LAST:event_saveSimulationActionPerformed

    private void numberOfNodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberOfNodesActionPerformed

    }//GEN-LAST:event_numberOfNodesActionPerformed

    private void individualNodeSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_individualNodeSelectionActionPerformed
        simulationPanel1.selectIndividualNodeSelect(individualNodeSelection.isSelected());
    }//GEN-LAST:event_individualNodeSelectionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnSelectionTool;
    private javax.swing.JToggleButton individualNodeSelection;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTextField numberOfNodes;
    private javax.swing.JButton saveSimulation;
    private pt.unl.fct.di.mei.securesim.platform.ui.SimulationPanel simulationPanel1;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JToggleButton verVizinhos;
    // End of variables declaration//GEN-END:variables

    public static void main ( String[] args ){
        JFrame f = new JFrame("Simulador");
        WorkbenchPanel s = new WorkbenchPanel();
        
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(s,BorderLayout.CENTER);
        f.pack();
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
    }

    private int getNumberOfNodes() {
        return Integer.valueOf(numberOfNodes.getText()).intValue();
    }

   

}
