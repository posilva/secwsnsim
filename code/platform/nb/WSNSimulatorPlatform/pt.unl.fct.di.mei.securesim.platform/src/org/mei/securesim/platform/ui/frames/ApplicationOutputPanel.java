/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RoutingInfoPanel.java
 *
 * Created on 7/Jul/2010, 0:53:33
 */
package org.mei.securesim.platform.ui.frames;

import org.mei.securesim.components.ApplicationOutput;
import org.mei.securesim.components.IApplicationOutputDisplay;

/**
 *
 * @author CIAdmin
 */
public class ApplicationOutputPanel extends javax.swing.JPanel implements IApplicationOutputDisplay{
    static boolean hasInstance=false;
    /** Creates new form RoutingInfoPanel */
    public ApplicationOutputPanel() {
        if (hasInstance)
            throw new IllegalStateException("Only one instance of ApplicationOutputPanel can exist");
        initComponents();
        ApplicationOutput.getInstance().subscribe(this);
        hasInstance=true;
    }
    private static ApplicationOutputPanel instance;

    public static ApplicationOutputPanel getInstance() {
        if (instance == null) {
            instance = new ApplicationOutputPanel();
        }
        return instance;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaOutput = new javax.swing.JTextArea();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(ApplicationOutputPanel.class);
        jLabel1.setBackground(resourceMap.getColor("jLabel1.background")); // NOI18N
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel1.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 40));
        add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtAreaOutput.setColumns(20);
        txtAreaOutput.setEditable(false);
        txtAreaOutput.setFont(resourceMap.getFont("txtAreaOutput.font")); // NOI18N
        txtAreaOutput.setRows(5);
        txtAreaOutput.setName("txtAreaOutput"); // NOI18N
        jScrollPane1.setViewportView(txtAreaOutput);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAreaOutput;
    // End of variables declaration//GEN-END:variables

    public void showOutput(String text) {
        txtAreaOutput.append(text+"\n");
    }
}