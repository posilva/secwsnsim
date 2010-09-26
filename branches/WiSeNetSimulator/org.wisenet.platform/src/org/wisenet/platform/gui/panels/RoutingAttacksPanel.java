/*
 **  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */

/*
 * RoutingAttacksPanel.java
 *
 * Created on Sep 4, 2010, 1:49:39 AM
 */
package org.wisenet.platform.gui.panels;

import java.awt.Cursor;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wisenet.platform.PlatformConfiguration;
import org.wisenet.platform.common.ui.PlatformPanel;
import org.wisenet.simulator.core.node.Node;
import org.wisenet.simulator.core.node.layers.routing.attacks.AttacksEntry;
import org.wisenet.tools.jrc.RuntimeCompiler;
import org.wisenet.tools.jrc.SourceFileReader;
import org.wisenet.tools.jrc.editor.SourceEditor;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class RoutingAttacksPanel extends PlatformPanel {

    protected Node node;
    protected static String UnderlineEditText = "<html>"
            + "<font  color=\"blue\" size=\"2\">" + "<u>Edit code...</u></font>";
    protected static String NoUnderlineEditText = "<html>"
            + "<font  color=\"blue\" size=\"2\">" + "Edit code...</font>";

    public RoutingAttacksPanel() {
        initComponents();
        lblEditCode.setText(NoUnderlineEditText);
    }

    private void enableSelectedAttack() {
        if (cboAttacks.getItemCount()>0){
            try {
                ((AttackItem) cboAttacks.getSelectedItem()).entry.getAttack().enable();
            } catch (Exception ex) {
                Logger.getLogger(RoutingAttacksPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private static class AttackItem {

        private final AttacksEntry entry;

        public AttackItem(AttacksEntry entry) {
            this.entry = entry;


        }

        @Override
        public String toString() {
            return entry.getLabel();
        }
    }

    /** Creates new form RoutingAttacksPanel */
    public RoutingAttacksPanel(Node node) {
        initComponents();
        lblEditCode.setText(NoUnderlineEditText);
        this.node = node;
        String nodeInfo = "";
        for (int i = 0; i < node.getInfo().length; i++) {
            String info = node.getInfo()[i];
            nodeInfo = nodeInfo + "\n" + info;

        }
        txtNodeInfo.setText(nodeInfo);
        loadAttacks();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentArea = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboAttacks = new javax.swing.JComboBox();
        lblEditCode = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNodeInfo = new javax.swing.JTextArea();
        cmdEnableAttack = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        contentArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        contentArea.setName("contentArea"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(RoutingAttacksPanel.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        cboAttacks.setName("cboAttacks"); // NOI18N

        lblEditCode.setForeground(resourceMap.getColor("lblEditCode.foreground")); // NOI18N
        lblEditCode.setText(resourceMap.getString("lblEditCode.text")); // NOI18N
        lblEditCode.setName("lblEditCode"); // NOI18N
        lblEditCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditCodeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEditCodeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEditCodeMouseExited(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtNodeInfo.setColumns(20);
        txtNodeInfo.setEditable(false);
        txtNodeInfo.setFont(resourceMap.getFont("txtNodeInfo.font")); // NOI18N
        txtNodeInfo.setRows(5);
        txtNodeInfo.setName("txtNodeInfo"); // NOI18N
        jScrollPane1.setViewportView(txtNodeInfo);

        cmdEnableAttack.setText(resourceMap.getString("cmdEnableAttack.text")); // NOI18N
        cmdEnableAttack.setName("cmdEnableAttack"); // NOI18N
        cmdEnableAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEnableAttackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentAreaLayout = new javax.swing.GroupLayout(contentArea);
        contentArea.setLayout(contentAreaLayout);
        contentAreaLayout.setHorizontalGroup(
            contentAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentAreaLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contentAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmdEnableAttack, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(cboAttacks, javax.swing.GroupLayout.Alignment.LEADING, 0, 209, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEditCode)))
                .addContainerGap())
        );
        contentAreaLayout.setVerticalGroup(
            contentAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentAreaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contentAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboAttacks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdEnableAttack)
                .addGap(31, 31, 31))
        );

        add(contentArea, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void lblEditCodeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditCodeMouseEntered
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditCode.setText(UnderlineEditText);
    }//GEN-LAST:event_lblEditCodeMouseEntered

    private void lblEditCodeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditCodeMouseExited
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        lblEditCode.setText(NoUnderlineEditText);
    }//GEN-LAST:event_lblEditCodeMouseExited

    private void lblEditCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditCodeMouseClicked
        // TODO add your handling code here:

        editSelectedAttackCode();
    }//GEN-LAST:event_lblEditCodeMouseClicked

    private void cmdEnableAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEnableAttackActionPerformed
        // TODO add your handling code here:
        enableSelectedAttack();

    }//GEN-LAST:event_cmdEnableAttackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboAttacks;
    private javax.swing.JButton cmdEnableAttack;
    private javax.swing.JPanel contentArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEditCode;
    private javax.swing.JTextArea txtNodeInfo;
    // End of variables declaration//GEN-END:variables

    public void setNode(Node node) {
        this.node = node;

    }

    public Object getData() {
        return "data";
    }

    @Override
    public boolean onCancel() {
        return true;
    }

    @Override
    public boolean onOK() {
        if (isDataValid()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onApply() {
        System.out.println("Applying Changes");
        return isDataValid();
    }

    protected boolean isDataValid() {
        return true;
    }

    private void editSelectedAttackCode() {
        if (cboAttacks.getItemCount()>0){
            try {
                String selectedAttack = ((AttackItem) cboAttacks.getSelectedItem()).entry.getAttack().getClass().getName();
                SourceFileReader sfr = new SourceFileReader();
                sfr.setSourcesDir(PlatformConfiguration.getInstance().getSourcesDirectory());
                File source = new File(sfr.getFullPathSourceFile(selectedAttack));
                RuntimeCompiler rc = new RuntimeCompiler();
                rc.setClassName(selectedAttack);
                rc.setSourceFile(source);
                SourceEditor editor = new SourceEditor(rc);
                editor.edit();
                
                node.getRoutingLayer().getAttacks().updateAttack(rc.getCompiledObject());
            } catch (Exception ex) {
                Logger.getLogger(RoutingAttacksPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void loadAttacks() {
        cboAttacks.removeAllItems();
        for (AttacksEntry entry : node.getRoutingLayer().getAttacks().getAttacksList()) {
            cboAttacks.addItem(new AttackItem(entry));
        }
    }
}
