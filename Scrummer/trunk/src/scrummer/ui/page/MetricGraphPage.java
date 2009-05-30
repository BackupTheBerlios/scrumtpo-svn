package scrummer.ui.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.MetricModel;
import scrummer.model.Models;
import scrummer.model.MetricModel.MetricEnum;
import scrummer.model.graph.MetricDataSet;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.measure.GraphEarnedValueDialog;
import scrummer.ui.dialog.measure.GraphPBICompleteDialog;
import scrummer.ui.dialog.measure.GraphSPIDialog;
import scrummer.ui.dialog.measure.GraphSpentWorkDialog;
import scrummer.ui.dialog.measure.GraphTaskCompleteDialog;
import scrummer.ui.dialog.measure.GraphWorkEffectivenessDialog;
import scrummer.uicomponents.StandardButton;

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
	implements ActionListener, ItemListener, PlotChangeListener {

	public MetricGraphPage(MainFrame mainFrame) {
		super(mainFrame);

		setBackground(Color.WHITE);
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricDataSet = _metricModel.getMetricDataSet();
		
		int k = 10;
		JFreeChart chart = 
			ChartFactory.createTimeSeriesChart(
				i18n.tr("Graph"), 
				i18n.tr("Time"), 
				"", _metricDataSet, 
				true, true, true);
		/*
			ChartFactory.createXYLineChart(
				i18n.tr("Graf"), 
				"1", "10", 
				_metricDataSet, 
				PlotOrientation.VERTICAL, true, true, true);
		*/
		
		chart.setBackgroundPaint(Color.WHITE);
		chart.getPlot().setOutlinePaint(Color.RED);
		chart.getXYPlot().setDomainGridlinePaint(Color.red);
		chart.getXYPlot().setBackgroundPaint(Color.WHITE);
		chart.setBorderPaint(Color.LIGHT_GRAY);
		chart.getXYPlot().addChangeListener(this);
		_chart = chart;
		
		ChartPanel cp = new ChartPanel(chart);
		cp.setMaximumSize(new Dimension(500,1000));		
		
		Box vertBox = new Box(BoxLayout.Y_AXIS);
		vertBox.setMinimumSize(new Dimension(800, 480));
		vertBox.setSize(new Dimension(800, 480));
		
		JPanel graphSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		graphSelectionPanel.setBackground(Color.WHITE);
		
	    _graphSelectionInput = new JComboBox();
		for (MetricModel.MetricEnum metric : MetricModel.MetricEnum.values())
			_graphSelectionInput.addItem(MetricModel.MetricEnum.convert(metric));
		_graphSelectionInput.addItemListener(this);
		
		JButton showButton = new StandardButton(i18n.tr("Show"));
		showButton.setActionCommand("Show");
		showButton.addActionListener(this);
		
		graphSelectionPanel.add(_graphSelectionInput);
		graphSelectionPanel.add(showButton);
		
		vertBox.add(graphSelectionPanel);
		vertBox.add(cp);
		
		add(vertBox);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			_selectedMetric =  MetricModel.MetricEnum.values()[_graphSelectionInput.getSelectedIndex()];
		}
	}
	
	@Override
	public void plotChanged(PlotChangeEvent arg0) {
		// _chart.getXYPlot()
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Show")) {
			switch (_selectedMetric) {
			case WorkEffectiveness: { 
				GraphWorkEffectivenessDialog dialog = new GraphWorkEffectivenessDialog(getMainFrame());
				Util.centre(dialog);
				dialog.setVisible(true);
				break;
			}
			case EarnedValue: {
				GraphEarnedValueDialog dialog = new GraphEarnedValueDialog(getMainFrame());
				Util.centre(dialog);
				dialog.setVisible(true);
				break;
			}
			case SPI: {
				GraphSPIDialog dialog = new GraphSPIDialog(getMainFrame());
				Util.centre(dialog);
				dialog.setVisible(true);
				break;
			}
			case CPI: break;
			case SprintBurndown: {
				GraphSpentWorkDialog dialog = new GraphSpentWorkDialog(getMainFrame());
				Util.centre(dialog);
				dialog.setVisible(true);
				break;
			}
			case TaskCompleteness: {
				GraphTaskCompleteDialog dialog = new GraphTaskCompleteDialog(getMainFrame());
				Util.centre(dialog);
				dialog.setVisible(true);
				break;
			}
			case PBICompleteness: {
				GraphPBICompleteDialog dialog = new GraphPBICompleteDialog(getMainFrame());
				Util.centre(dialog);
				dialog.setVisible(true);
				break;
			}
			}
			
		} else if (cmd.equals("Add")) {
			// MetricPlotAddDialog dialog = new MetricPlotAddDialog(getMainFrame());
			// Util.centre(dialog);
			// dialog.setVisible(true);
		} else if (cmd.equals("Edit")) {
			
		} else if (cmd.equals("Remove")) {
			
		}
	}
	
	/// combo box for selecting which graph to display
	private JComboBox _graphSelectionInput;
	/// selected metric
	private MetricModel.MetricEnum _selectedMetric = MetricEnum.WorkEffectiveness;
	/// metric model
	private MetricModel _metricModel;
	/// metric data set
	private MetricDataSet _metricDataSet;
	/// chart object
	private JFreeChart _chart;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5832667024364710534L;
}
