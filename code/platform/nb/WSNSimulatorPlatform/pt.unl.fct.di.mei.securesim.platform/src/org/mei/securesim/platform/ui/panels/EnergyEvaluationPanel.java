/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EnergyEvaluationPanel.java
 *
 * Created on 15/Jul/2010, 2:43:17
 */
package org.mei.securesim.platform.ui.panels;

import java.awt.BorderLayout;
import java.util.Hashtable;
import org.jdesktop.application.Action;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.mei.securesim.components.instruments.energy.EnergyInstrument;

/**
 *
 * @author CIAdmin
 */
public class EnergyEvaluationPanel extends javax.swing.JPanel {

    final Object refreshMonitor = new Object();
    private boolean end = false;

    /** Creates new form EnergyEvaluationPanel */
    public EnergyEvaluationPanel() {
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

        titleArea = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelEnergyByState = new javax.swing.JPanel();
        panelEnergyByEvent = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        tgbAutorefresh = new javax.swing.JToggleButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getResourceMap(EnergyEvaluationPanel.class);
        titleArea.setBackground(resourceMap.getColor("titleArea.background")); // NOI18N
        titleArea.setFont(resourceMap.getFont("titleArea.font")); // NOI18N
        titleArea.setText(resourceMap.getString("titleArea.text")); // NOI18N
        titleArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        titleArea.setMaximumSize(new java.awt.Dimension(200, 30));
        titleArea.setMinimumSize(new java.awt.Dimension(200, 30));
        titleArea.setName("titleArea"); // NOI18N
        titleArea.setOpaque(true);
        titleArea.setPreferredSize(new java.awt.Dimension(200, 40));
        add(titleArea, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        panelEnergyByState.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEnergyByState.setName("panelEnergyByState"); // NOI18N
        panelEnergyByState.setLayout(new java.awt.BorderLayout());

        panelEnergyByEvent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelEnergyByEvent.setName("panelEnergyByEvent"); // NOI18N
        panelEnergyByEvent.setLayout(new java.awt.BorderLayout());

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.mei.securesim.platform.PlatformApp.class).getContext().getActionMap(EnergyEvaluationPanel.class, this);
        jButton1.setAction(actionMap.get("ShowCharts")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        tgbAutorefresh.setAction(actionMap.get("AutoRefreshAction")); // NOI18N
        tgbAutorefresh.setText(resourceMap.getString("tgbAutorefresh.text")); // NOI18N
        tgbAutorefresh.setName("tgbAutorefresh"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelEnergyByEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelEnergyByState, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(26, 26, 26)
                        .addComponent(tgbAutorefresh)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelEnergyByState, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEnergyByEvent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(tgbAutorefresh))
                .addGap(44, 44, 44))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelEnergyByEvent;
    private javax.swing.JPanel panelEnergyByState;
    private javax.swing.JToggleButton tgbAutorefresh;
    private javax.swing.JLabel titleArea;
    // End of variables declaration//GEN-END:variables

    void createEnergyByStateChart() {
        JFreeChart chart = createChart(0);
        ChartPanel panel = new ChartPanel(chart);
        panel.updateUI();
        panelEnergyByState.add(panel, BorderLayout.CENTER);
        panelEnergyByState.updateUI();
    }

    private JFreeChart createChart(int type) {
        switch (type) {
            case 0:
                return ChartFactory.createPieChart("Energy by Routing Phases", (PieDataset) createInitialDataset(type), true, true, true);
            case 1:
                return ChartFactory.createPieChart("Energy by Events", (PieDataset) createInitialDataset(type), true, true, true);
        }
        return null;
    }

    Dataset createInitialDataset(int type) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        switch (type) {
            case 0:
                Hashtable<String, Double> stateEnergyEntries = EnergyInstrument.getInstance().getDatabase().getStatesEnergy();
                for (String state : stateEnergyEntries.keySet()) {
                    dataset.setValue(state, stateEnergyEntries.get(state));
                }
                return dataset;
            case 1:
                Hashtable<String, Double> energyEntries = EnergyInstrument.getInstance().getDatabase().getEventsEnergy();
                for (String state : energyEntries.keySet()) {
                    dataset.setValue(state, energyEntries.get(state));
                }
                return dataset;
        }
        return dataset;
    }

    @Action
    public void ShowCharts() {
        createEnergyByStateChart();
        createEnergyByEventsChart();
    }

    private void createEnergyByEventsChart() {
        JFreeChart chart = createChart(1);
        ChartPanel panel = new ChartPanel(chart);
        panel.updateUI();
        panelEnergyByEvent.add(panel, BorderLayout.CENTER);
        panelEnergyByEvent.updateUI();
    }
}
