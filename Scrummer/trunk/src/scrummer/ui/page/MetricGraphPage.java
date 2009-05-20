package scrummer.ui.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.MetricModel;
import scrummer.model.Models;
import scrummer.model.graph.MetricDataSet;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.MetricPlotAddDialog;
import scrummer.uicomponents.AddEditRemovePanel;

/**
 * Graph display page
 * 
 * Graph can be either connected or "columnar". It can show arbitrary set of points.
 * Several kinds of metrics can be displayed(Sprint, Task, Release related), they can range
 * from/to different dates and id's. It should also be able to display a specifically specified set
 * of measures.
 */
public class MetricGraphPage 
	extends BasePage 
	implements ActionListener {

	public MetricGraphPage(MainFrame mainFrame) {
		super(mainFrame);

		setBackground(Color.WHITE);
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricDataSet = _metricModel.getMetricDataSet();
		
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date d1 = null, d2 = null;
		try {
			d1 = sdf.parse("1.1.1990");
			d2 = sdf.parse("1.1.2010");
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		*/	
		// _metricDataSet.addObjectMeasure("Test", "Test3", MetricType.Sprint, 3, d1, d2);
		
		int k = 10;
		Box leftPanel = new Box(BoxLayout.Y_AXIS);
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setMinimumSize(new Dimension(250, 1000));
		leftPanel.setMaximumSize(new Dimension(250, 1000));
		leftPanel.setSize(new Dimension(250, 1000));
		leftPanel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
					i18n.tr("Metric plots")),
				BorderFactory.createEmptyBorder(0,k,0,k)));
		
		JPanel leftListPanel = new JPanel();
		leftListPanel.setLayout(new GridLayout(1,1));
		leftListPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));		
		
		JList list = new JList();
		leftListPanel.add(list);
		
		AddEditRemovePanel leftToolPanel = new AddEditRemovePanel();
		leftToolPanel.addActionListener(this);
		leftToolPanel.setMaximumSize(new Dimension(1000, 30));
		
		leftPanel.add(leftListPanel);
		leftPanel.add(leftToolPanel);
		
		JFreeChart chart = 
			ChartFactory.createXYLineChart(
				i18n.tr("Graf"), 
				"1", "10", 
				_metricDataSet, 
				PlotOrientation.VERTICAL, true, true, true);
		
		chart.setBackgroundPaint(Color.WHITE);
		chart.getPlot().setOutlinePaint(Color.RED);
		chart.getXYPlot().setDomainGridlinePaint(Color.red);
		chart.getXYPlot().setBackgroundPaint(Color.WHITE);
		chart.setBorderPaint(Color.LIGHT_GRAY);
		
		ChartPanel cp = new ChartPanel(chart);
		cp.setMaximumSize(new Dimension(500,1000));
		
		Box box = new Box(BoxLayout.X_AXIS);
		box.setMinimumSize(new Dimension(800, 480));
		box.setSize(new Dimension(800, 480));
		box.add(leftPanel);
		box.add(cp);
		
		add(box);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Add")) {
			MetricPlotAddDialog dialog = new MetricPlotAddDialog(getMainFrame());
			Util.centre(dialog);
			dialog.setVisible(true);
		} else if (cmd.equals("Edit")) {
			
		} else if (cmd.equals("Remove")) {
			
		}
	}
	
	/// metric model
	private MetricModel _metricModel;
	/// metric data set
	private MetricDataSet _metricDataSet;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5832667024364710534L;

}
