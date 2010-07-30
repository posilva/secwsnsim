package org.wisenet.platform.core.charts.core;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.data.general.Series;
import org.jfree.data.xy.AbstractXYDataset;



import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import org.wisenet.platform.core.charts.IChartDisplay;
import org.wisenet.platform.core.charts.IChartDisplayable;

/**
 * A convenience wrapper class for using simple XYLineChart charts of the JFreeChart package. 
 * 
 * @author Sérgio Duarte (smd@di.fct.unl.pt)
 *
 */
abstract public class XYChart<T extends Series> implements IChartDisplayable{

	private static RGB[] colors = { RGB.RED, RGB.BLUE, RGB.GREEN, RGB.MAGENTA, RGB.CYAN, RGB.ORANGE } ;

	protected String name ;
	protected JFreeChart chart;

    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }
	protected AbstractXYDataset data ;
	protected AbstractXYItemRenderer renderer ;
	protected String xAxisLabel, yAxisLabel ;

	protected Map<String, T> series ;
	
	
	public XYChart( String frame, double fps, String xAxisLabel, String yAxisLabel ) {
		this.name = frame ;
		this.xAxisLabel = xAxisLabel ;
		this.yAxisLabel = yAxisLabel ;
		init();		
		if( fps > 0 ) { 
//			Gui.addDisplayable(frame, this, fps) ;
//			Gui.setFrameTransform(frame, 500, 500, 0, false) ;
		}
	}

	public void setXRange( boolean auto, double min, double max ) {
		chart.getXYPlot().getDomainAxis().setRange( min, max);
		chart.getXYPlot().getDomainAxis().setAutoRange(auto);
	}

	public void setYRange( boolean auto, double min, double max ) {
		chart.getXYPlot().getRangeAxis().setRange( min, max);
		chart.getXYPlot().getRangeAxis().setAutoRange(auto);
	}


	
	public T getSeries( String name ) {
		T s = series.get( name) ;
		if( s == null ) {	
			s = createSeries(name) ;
			series.put( name, s) ;
		}
		return s ;
	}
	
	protected int getSeriesIndex( String name ) {
		Series s = getSeries( name ) ;
		for( int i = 0 ; i < data.getSeriesCount() ; i++ )
			if( s.getKey().equals( data.getSeriesKey(i) ) )
					return i ;
		return -1;
	}
	
	public JFreeChart chart() { 
		if( chart == null ) {
			createChart() ;
		}
		return chart ;
	}
	
	protected abstract void createChart() ;
	protected abstract T createSeries( String name) ;
	/**
	 * Initializes all the FreeChart stuff..
	 */
	public void init() {
		createChart() ;
		
		XYPlot plot = (XYPlot) chart.getPlot() ;
		plot.setBackgroundPaint( Color.WHITE );
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint( Color.GRAY);

        int j = 0 ;
        for( RGB i : colors ) {
        	Pen p = new Pen( i, 2) ;
        	renderer.setSeriesStroke(j, p.stroke) ;
            renderer.setSeriesPaint(j, p.color.darker() );
            renderer.setSeriesFillPaint(j, p.color.brighter());
            j++ ;
        }
        
        chart.setAntiAlias(true) ;
        chart.setTextAntiAlias(true) ;
	}

	
	
	public void displayOn( IChartDisplay canvas ) {
		
		try {
			chart.draw( (Graphics2D) canvas.getG(), canvas.getRectangle());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	
	/**
	* Save chart as PDF file. Requires iText library.
	*
	* @param chart JFreeChart to save.
	* @param fileName Name of file to save chart in.
	* @param width Width of chart graphic.
	* @param height Height of chart graphic.
	* @throws Exception if failed.
	* @see <a href="http://www.lowagie.com/iText">iText</a>
	*/
	public void saveChartToPDF(String fileName, int width, int height) throws Exception {
	    if (chart != null) {
	        BufferedOutputStream out = null;
	        try {
	            out = new BufferedOutputStream(new FileOutputStream(fileName));
	               
	            //convert chart to PDF with iText:
	            com.lowagie.text.Rectangle pagesize = new com.lowagie.text.Rectangle(width, height);
	            com.lowagie.text.Document document = new com.lowagie.text.Document(pagesize, 50,50,50,50) ;
	            try {
	                PdfWriter writer = PdfWriter.getInstance(document, out);
	                document.addAuthor("JFreeChart");
	                document.open();
	       
	                PdfContentByte cb = writer.getDirectContent();
	                PdfTemplate tp = cb.createTemplate(width, height);
	                Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
	       
	                Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
	                chart.draw(g2, r2D, null);
	                g2.dispose();
	                cb.addTemplate(tp, 0, 0);
	            } finally {
	                document.close();
	            }
	        } finally {
	            if (out != null) {
	                out.close();
	            }
	        }
	    }//else: input values not availabel
	}//saveChartToPDF()
	
	
}