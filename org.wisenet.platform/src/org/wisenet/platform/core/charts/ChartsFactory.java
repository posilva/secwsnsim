/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.platform.core.charts;

import java.awt.Font;
import java.util.Hashtable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.wisenet.simulator.core.energy.GlobalEnergyDatabase;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ChartsFactory {

    /**
     * Creates a energyByEventChart
     * @param test
     * @return
     */
    public static SimulationChart createEnergybyEventChart(final GlobalEnergyDatabase database, String title) {

        SimulationChart chart = new SimulationChart() {

            @Override
            protected Dataset createDataset() {
                DefaultPieDataset ds = new DefaultPieDataset();
                Double value = 0.0;
                if (database != null) {
                    Hashtable<String, Double> ee = database.getEventsEnergy();
                    for (String key : ee.keySet()) {
                        value = ee.get(key);
                        ds.setValue(key, value);
                    }
                } else {
                    ds = null;
                }
                return ds;
            }

            @Override
            protected JFreeChart createChart() {

                JFreeChart c = null;
                if (dataset != null) {
                    c = ChartFactory.createPieChart(
                            getTitle(), (PieDataset) dataset, // data
                            true, // include legend
                            true,
                            false);

                    PiePlot plot = (PiePlot) c.getPlot();
                    plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 7));
                    plot.setNoDataMessage("No data available");
                    plot.setCircular(false);
                    plot.setLabelGap(0.02);
                    return c;
                }
                return c;
            }
        };
        chart.setTitle(title);
        chart.create();
        return chart;
    }
}
