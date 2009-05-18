package scrummer.ui.page;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.ui.MainFrame;

/**
 * Graph display page
 * 
 * Graph can be either connected or "columnar". It can show arbitrary set of points.
 * Several kinds of metrics can be displayed(Sprint, Task, Release related), they can range
 * from/to different dates and id's. It should also be able to display a specifically specified set
 * of measures.
 */
public class MetricGraphPage extends BasePage {

	public MetricGraphPage(MainFrame mainFrame) {
		super(mainFrame);

		// setLa
		DefaultXYDataset dataset = new DefaultXYDataset();

		// dataset.setValue(new Float(1.0f),	1, 10)
		
		double [][] d = new double[][] {
			new double[] {0,1,2,3,4,5,6},
			new double[] {0,2,1,6,0,3,2}
		};
		
		dataset.addSeries(1, d);
		
		JFreeChart chart = 
			ChartFactory.createXYLineChart(
				i18n.tr("Graf"), 
				"1", "10", 
				dataset, 
				PlotOrientation.VERTICAL, true, true, true);
		
		chart.setBackgroundPaint(Color.WHITE);
		chart.getPlot().setBackgroundPaint(Color.BLUE);
		// chart.getPlot().setOutlinePaint()
		chart.getPlot().setOutlinePaint(Color.RED);
		chart.getXYPlot().setDomainGridlinePaint(Color.red);
		chart.setBorderPaint(Color.LIGHT_GRAY);
		
		
		ChartPanel cp = new ChartPanel(chart);
		add(cp);
		
	}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5832667024364710534L;
}
