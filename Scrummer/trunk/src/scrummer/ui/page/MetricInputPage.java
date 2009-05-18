package scrummer.ui.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.MetricModel;
import scrummer.model.Models;
import scrummer.model.swing.MetricTableModel;
import scrummer.model.swing.SprintProjectComboBoxModel;
import scrummer.model.swing.TaskComboBoxModel;
import scrummer.ui.MainFrame;
import scrummer.ui.MetricPageToolbar;
import scrummer.ui.Util;
import scrummer.ui.dialog.MeasureAddDialog;
import scrummer.ui.dialog.MeasureChangeDialog;
import scrummer.uicomponents.NiceTable;

/**
 * Metric input page shows a table of metrics and allows input/edit of them.
 * There are also options for calculating known metrics.
 */
public class MetricInputPage 
	extends BasePage 
	implements ActionListener, ItemListener {

	public MetricInputPage(MainFrame mainFrame) {
		super(mainFrame);
		setLayout(new GridLayout(1,1));
		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricTableModel = _metricModel.getMetricTableModel();
		_taskComboBoxModel = m.getTaskModel().getTaskComboBoxModel();
		_sprintProjectComboBoxModel = m.getSprintBacklogModel().getSprintProjectComboBoxModel();
		
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		_toolbar = new MetricPageToolbar();
		_toolbar.addActionListener(this);
		_toolbar.addItemListener(this);
		
		_metricTable = new NiceTable();
		_metricTable.setBackground(Color.WHITE);
		_metricTable.setModel(_metricTableModel);
		
		JScrollPane scrollPane = new JScrollPane(_metricTable);				
		scrollPane.setBackground(Color.WHITE);
		
		setBackground(Color.WHITE);
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		box.add(_toolbar);
		box.add(scrollPane);
		
		add(box);
		
		updateObjectBox(_toolbar);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Add")) {
			MeasureAddDialog dialog = 
				new MeasureAddDialog(
					getMainFrame(),
					_toolbar.getSelectedMetricType(),
					_toolbar.getMetricId(),
					_toolbar.getSpecificId());
			Util.centre(dialog);
			dialog.setVisible(true);
		} else if (cmd.equals("Edit")) {
			int selected = _metricTable.getSelectedRow(); 
			if (selected != -1) {
				Date d = _metricTableModel.getDate(selected);
				BigDecimal value = _metricTableModel.getValue(selected);				
				MeasureChangeDialog dialog = 
					new MeasureChangeDialog(
						getMainFrame(), 
						_toolbar.getSelectedMetricType(),
						_toolbar.getMetricId(),
						_toolbar.getSpecificId(), d, value);
				Util.centre(dialog);
				dialog.setVisible(true);
			} else {
				Util.showError(this,
					i18n.tr("Could not open dialog because a measure is not selected. " +
							"Please select it and then click Edit"), 
					i18n.tr("Error"));
			}
		} else if (cmd.equals("Remove")) {
			int selected = _metricTable.getSelectedRow(); 
			if (selected != -1) {
				int id = _metricTableModel.getId(selected);
				switch (_toolbar.getSelectedMetricType()) {
				case Sprint:
					_metricModel.removeSprintMeasurement(_toolbar.getMetricId(), id,  
						_metricTableModel.getDate(selected));
					break;
				case Task: 					
					_metricModel.removeTaskMeasurement(_toolbar.getMetricId(), id, 
						_metricTableModel.getDate(selected));
					break;
				}
			}			
		}			
	}
	
	/**
	 * Update object drowpdown box contents(sprints, tasks, ...)
	 * @param toolbar toolbar
	 */
	private void updateObjectBox(MetricPageToolbar toolbar) {
		switch (_toolbar.getSelectedMetricType()) {
		case Sprint:
			toolbar.updateObjectBox(_sprintProjectComboBoxModel);
			break;
		case Task:
			toolbar.updateObjectBox(_taskComboBoxModel);
			break;
		}	
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {			
			if (_toolbar.isMetricSelectionInput(e.getSource())) {
				updateObjectBox(_toolbar);
			} else if (_toolbar.isMetricInput(e.getSource())) {
				
			} else if (_toolbar.isMetricId(e.getSource())) {
				
			}
			
			_metricTableModel.setMetricType(
				_toolbar.getSelectedMetricType(),
				_toolbar.getMetricId(), 
				_toolbar.getSpecificId());
		}
	}
	
	/// metric table ui
	private NiceTable _metricTable;
	/// all sprints on current project
	private SprintProjectComboBoxModel _sprintProjectComboBoxModel;
	/// all tasks
	// private ProjectTaskComboBoxModel ZA narest: vse naloge na projektu
	private TaskComboBoxModel _taskComboBoxModel;	
	/// metric model
	private MetricModel _metricModel;
	/// metric table model
	private MetricTableModel _metricTableModel;
	/// toolbar
	private MetricPageToolbar _toolbar;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -4897261591430383488L;
}
