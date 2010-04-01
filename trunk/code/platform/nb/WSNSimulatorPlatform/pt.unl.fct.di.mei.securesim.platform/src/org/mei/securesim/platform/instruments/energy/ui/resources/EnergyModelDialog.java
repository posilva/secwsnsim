/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EnergyModelDialog.java
 *
 * Created on Mar 30, 2010, 1:07:46 AM
 */
package org.mei.securesim.platform.instruments.energy.ui.resources;

import java.awt.Window;
import org.jdesktop.application.Action;
import org.mei.securesim.core.energy.EnergyModel;
import org.mei.securesim.platform.utils.GUI_Utils;

/**
 *
 * @author posilva
 */
public class EnergyModelDialog extends javax.swing.JDialog {

    private EnergyModel energyModel;
    private boolean ok = false;

    /** Creates new form EnergyModelDialog */
    public EnergyModelDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        GUI_Utils.centerOnScreen((Window) this);
    }

    public boolean isOk() {
        return ok;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        energyModelPanel1 = new org.mei.securesim.platform.instruments.energy.ui.EnergyModelPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout());

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getActionMap(EnergyModelDialog.class, this);
        jButton1.setAction(actionMap.get("ConfirmEnergyModel")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(EnergyModelDialog.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jPanel1.add(jButton1);

        jButton2.setAction(actionMap.get("CancelEnergyModel")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setToolTipText(resourceMap.getString("jButton2.toolTipText")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        energyModelPanel1.setName("energyModelPanel1"); // NOI18N
        getContentPane().add(energyModelPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                EnergyModelDialog dialog = new EnergyModelDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    @Action
    public void ConfirmEnergyModel() {
        energyModel = energyModelPanel1.createEnergyModelInstance();
        ok = true;
        setVisible(false);
    }

    @Action
    public void CancelEnergyModel() {
        ok = false;
        setVisible(false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.mei.securesim.platform.instruments.energy.ui.EnergyModelPanel energyModelPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    public EnergyModel getEnergyModel() {
        return energyModel;
    }
}
