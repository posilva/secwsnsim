/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.unl.fct.di.mei.securesim.platform.charts;

/**
 *
 * @author posilva
 */
public class ChartsExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      // Create a chart to monitor the infection progress rate
		final XYLineChart chart = new XYLineChart("Infection Rate", 5.0, "Infected Nodes (%)", "time(s)") ;
		chart.setYRange( false, 0, 100 ) ;
		chart.setSeriesLinesAndShapes("s0", true, true) ;

        //chart.getSeries("s0").add( currentTime(), 100.0 * T / N ) ;
    }

}
