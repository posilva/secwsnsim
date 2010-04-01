/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EnergyWatcherDialog.java
 *
 * Created on Mar 13, 2010, 6:58:33 PM
 */

package org.mei.securesim.platform.instruments.energy.ui;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.platform.instruments.energy.EnergyWatcherThread;

/**
 *
 * @author posilva
 */
public class EnergyWatcherDialog extends javax.swing.JDialog {

    /** Creates new form EnergyWatcherDialog */
    public EnergyWatcherDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chartPanel1 = new org.mei.securesim.platform.charts.ui.ChartPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(EnergyWatcherDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        chartPanel1.setName("chartPanel1"); // NOI18N

        javax.swing.GroupLayout chartPanel1Layout = new javax.swing.GroupLayout(chartPanel1);
        chartPanel1.setLayout(chartPanel1Layout);
        chartPanel1Layout.setHorizontalGroup(
            chartPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        chartPanel1Layout.setVerticalGroup(
            chartPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        getContentPane().add(chartPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.mei.securesim.platform.charts.ui.ChartPanel chartPanel1;
    // End of variables declaration//GEN-END:variables



    public void updateChart(double x, double  y){
        chartPanel1.updateChart(x, y);
        chartPanel1.updateUI();
    }
    public static void main (String[] a ){

        EnergyWatcherThread t = new EnergyWatcherThread();
        
        PipedOutputStream pos = t.getOutputStream();
        
        DataOutputStream dos = new DataOutputStream(pos);
t.start();
        for (int i = 0; i < 100000000; i++) {
            try {
                dos.writeDouble(i * .5);
                dos.writeDouble(i * .1);
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnergyWatcherDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EnergyWatcherDialog.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
