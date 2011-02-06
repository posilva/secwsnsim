/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */

/*
 * EnergyChartsDialog.java
 *
 * Created on Feb 6, 2011, 1:25:41 AM
 */
package org.wisenet.platform.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.Set;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;
import org.wisenet.platform.core.PlatformManager;
import org.wisenet.simulator.core.energy.EnergyController;

/**
 *
 * @author posilva
 */
public class EnergyChartsDialog extends javax.swing.JFrame {

    static EnergyChartsDialog instance;

    public static EnergyChartsDialog getInstance() {
        if (instance == null) {
            instance = new EnergyChartsDialog();
        }
        return instance;
    }
    private JFreeChart chart1;
    private JFreeChart chart2;

    /** Creates new form EnergyChartsDialog */
    public EnergyChartsDialog() {
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

        jPanel1 = new javax.swing.JPanel();
        cp1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        refreshButton = new javax.swing.JButton();
        cp2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        cp1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cp1.setName("cp1"); // NOI18N
        cp1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setName("jToolBar1"); // NOI18N

        refreshButton.setText("Refresh");
        refreshButton.setFocusable(false);
        refreshButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshButton.setName("refreshButton"); // NOI18N
        refreshButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(refreshButton);

        cp1.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(cp1);

        cp2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cp2.setName("cp2"); // NOI18N
        cp2.setLayout(new java.awt.BorderLayout());
        jPanel1.add(cp2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refreshCharts();
    }//GEN-LAST:event_refreshButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cp1;
    private javax.swing.JPanel cp2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables
    PieDataset dataset1;
    PieDataset dataset2;

    protected void createChart1() {
        dataset1 = createDataset1();
        chart1 = ChartFactory.createPieChart(
                "Energy consumption by protocol event (operations)", // chart title
                dataset1, // dataset
                false, // include legend
                true,
                false);

        chart1.setBackgroundPaint(new Color(222, 222, 255));


        TextTitle t = new TextTitle("Energy consumption by protocol event (operations)");
        t.setPaint(new Color(1.0f, 0.549f, 0.0f));

        /* Expanding the title to the entire width of the pie chart image */
        t.setExpandToFitSpace(true);
        final PiePlot plot = (PiePlot) chart1.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setCircular(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} = ({1}){2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
        plot.setNoDataMessage("No data available");

        // add the chart to a panel...
        final ChartPanel chartPanel1 = new ChartPanel(chart1);
        chartPanel1.setPreferredSize(new java.awt.Dimension(500, 270));
        cp1.add(chartPanel1, BorderLayout.CENTER);
        jPanel1.updateUI();
    }

    protected void createChart2() {
        dataset2 = createDataset2();
        chart2 = ChartFactory.createPieChart(
                "Energy consumption by protocol phases", // chart title
                dataset2, // dataset
                false, // include legend
                true,
                false);

        chart2.setBackgroundPaint(new Color(222, 222, 255));
        final PiePlot plot = (PiePlot) chart2.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setCircular(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} = ({1}){2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
        plot.setNoDataMessage("No data available");

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart2);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        cp2.add(chartPanel, BorderLayout.CENTER);
        jPanel1.updateUI();
    }

    private PieDataset createDataset1() {
        final DefaultPieDataset result = new DefaultPieDataset();
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            EnergyController ec = PlatformManager.getInstance().getActiveSimulation().getEnergyController();
            Set<String> ev = ec.getDatabase().getEventsEnergy().keySet();
            for (String key : ev) {
                result.setValue(key, ec.getDatabase().getEventsEnergy().get(key));
            }
        }
        return result;
    }

    public void display() {
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            createCharts();
            setPreferredSize(new Dimension(400, 600));

            RefineryUtilities.centerFrameOnScreen(this);
            pack();
            setVisible(true);
        }
    }

    private PieDataset createDataset2() {
        final DefaultPieDataset result = new DefaultPieDataset();
        if (PlatformManager.getInstance().haveActiveSimulation()) {
            refreshChart2(result);
        }
        return result;

    }

    private void refreshChart2(final DefaultPieDataset result) {
        EnergyController ec = PlatformManager.getInstance().getActiveSimulation().getEnergyController();
        Set<String> ev = ec.getDatabase().getStatesEnergy().keySet();
        for (String key : ev) {
            result.setValue(key, ec.getDatabase().getStatesEnergy().get(key));
        }
    }

    private void createCharts() {
        createChart1();
        createChart2();

    }

    private void refreshCharts() {
        createCharts();
        jPanel1.updateUI();
    }
}
