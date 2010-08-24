package org.wisenet.platform.core.charts.core;

import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
/**
 * A convenience wrapper class for using simple XYDeviationLineChart charts from the JFreeChart package. 
 * 
 * @author Sérgio Duarte (smd@di.fct.unl.pt)
 *
 */
public class XYDeviationLineChart extends XYChart<YIntervalSeries> {
	
	public XYDeviationLineChart( String frame, double fps, String xAxisLabel, String yAxisLabel ) {
		super( frame, fps, xAxisLabel, yAxisLabel) ;
	}

	protected void createChart() {
		data = new YIntervalSeriesCollection();
		
		DeviationRenderer dr = new DeviationRenderer(true, false);
		renderer = dr ;
		dr.setAlpha(0.5f) ;
		dr.setDrawSeriesLineAsPath(true) ;
		
		series = new HashMap<String, YIntervalSeries>() ;		
		chart = ChartFactory.createXYLineChart( name, yAxisLabel, xAxisLabel, data, PlotOrientation.VERTICAL, true, false, false);
		chart.getXYPlot().setRenderer( renderer);
	}

	protected YIntervalSeries createSeries(String name) {
		YIntervalSeries s = new YIntervalSeries(name) ;
		((YIntervalSeriesCollection)data).addSeries(s) ;
		return s;
	}
	
	
}