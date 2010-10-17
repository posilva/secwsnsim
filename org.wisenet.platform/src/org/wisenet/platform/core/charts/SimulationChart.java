/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.platform.core.charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public abstract class SimulationChart {

    protected String title;
    /**
     *
     */
    protected Dataset dataset = null;
    /**
     *
     */
    protected JFreeChart chart = null;
    /**
     *
     */
    protected SimulationChartUpdateAction updateAction;

    /**
     *
     * @return
     */
    public JFreeChart getChart() {
        return chart;
    }

    /**
     *
     * @param chart
     */
    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }

    /**
     *
     * @return
     */
    public Dataset getDataset() {
        return dataset;
    }

    /**
     *
     * @param dataset
     */
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    /**
     *
     * @param action
     */
    protected void update(SimulationChartUpdateAction action) {
        if (dataset != null) {
            action.update(dataset);
        }
    }

    protected void update() {
        if (dataset != null) {
            if (updateAction != null) {
                updateAction.update(dataset);
            }
        }
    }

    /**
     *
     * @return
     */
    public SimulationChartUpdateAction getUpdateAction() {
        return updateAction;
    }

    /**
     *
     * @param updateAction
     */
    public void setUpdateAction(SimulationChartUpdateAction updateAction) {
        this.updateAction = updateAction;
    }

    public void create() {
        dataset = createDataset();
        chart = createChart();
    }

    /**
     *
     * @return
     */
    protected abstract Dataset createDataset();

    /**
     *
     * @return
     */
    protected abstract JFreeChart createChart();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChartPanel getPanel() {
        return new ChartPanel(chart);
    }
}
