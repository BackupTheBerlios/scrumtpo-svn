package scrummer.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import scrummer.Scrummer;
import scrummer.model.MetricModel;
import scrummer.model.Models;
import scrummer.model.swing.MetricTableModel;
import scrummer.model.swing.base.IdValueComboBoxModel;
import scrummer.uicomponents.AddEditRemovePanel;
import scrummer.uicomponents.StandardComboBox;

/**
 * Toolbar that displays either Sprint or Task input measurements.
 * It also handles addition/edit/removal of measurements.
 */
public class MetricPageToolbar 
	extends JPanel {
	
	public MetricPageToolbar() {
		setLayout(new GridLayout(1,2));
	
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricTableModel = _metricModel.getMetricTableModel();
		
		JPanel left = new JPanel();
		left.setBackground(Color.WHITE);
		FormBuilder fb = new FormBuilder(left, FormBuilder.Layout.LeftToRight);
		fb.setCellSpacing(5, 5);
		_metricInput =
			fb.addComboBoxInput(i18n.tr("Metric") + ":");		
		_metricSelectionInput = 
			fb.addComboBoxInput(i18n.tr("Metric Type") + ":");
		_specificMetricInput =
			fb.addComboBoxInput("");
		add(left);
		
		_addEditRemovePanel = new AddEditRemovePanel();		
		add(_addEditRemovePanel);
		
		_metricSelectionInput.addItem("Task");
		_metricSelectionInput.addItem("Sprint");
		_metricSelectionInput.addItem("PBI");
		_metricSelectionInput.addItem("Release");
		
		_metricSelectionInput.setEnabled(true);
		
		_metricInput.setIVModel(_metricModel.getMetricComboBoxModel());		
		
		_metricSelectionInput.setSelectedIndex(0);
	}	
	
	/**
	 * Select metric domain Sprint, Task, ...
	 * @param index index to set
	 */
	public void selectMetricDomain(int index) {
		_metricSelectionInput.setSelectedIndex(index);
	}
	
	public void selectMetricType(int index) {
		_metricInput.setSelectedIndex(index);
	}
	
	/**
	 * Add item listener to all combo boxes
	 * @param listener listener
	 */
	public void addItemListener(ItemListener listener) {
		_specificMetricInput.addItemListener(listener);
		_metricSelectionInput.addItemListener(listener);
		_metricInput.addItemListener(listener);
	}
	
	/**
	 * Remove listener from all combo boxes
	 * @param listener
	 */
	public void removeItemListener(ItemListener listener) {
		_specificMetricInput.removeItemListener(listener);
		_metricSelectionInput.removeItemListener(listener);
		_metricInput.removeItemListener(listener);
	}
	
	/**
	 * Add action listener to add/edit/remove button events
	 * @param listener listener
	 */
	public void addActionListener(ActionListener listener) {
		_addEditRemovePanel.addActionListener(listener);
	}
	
	/**
	 * Remove action listener from add/edit/remove button events
	 * @param listener listener
	 */
	public void removeActionListener(ActionListener listener) {
		_addEditRemovePanel.removeActionListener(listener);
	}
	
	/**
	 * @param object any object
	 * @return true if object is metric selection input
	 */
	public boolean isMetricSelectionInput(Object object) {
		return (_metricSelectionInput == object);
	}
	
	/**
	 * @param object any object
	 * @return true if object is metric type input
	 */
	public boolean isMetricInput(Object object) {
		return (_metricInput == object);
	}
	
	/**
	 * @param object object to compare
	 * @return true if object is the control that further refines which metrics to show(task id, sprint id, ...)
	 */
	public boolean isMetricId(Object object) {
		return (_specificMetricInput == object);
	}
	
	/**
	 * @return currently selected metric domain(Sprint, Task, ...)
	 */
	public MetricTableModel.MetricType getSelectedMetricType() {
		return MetricTableModel.MetricType.values()[_metricSelectionInput.getSelectedIndex()];
	}
	
	/**
	 * @return metric type
	 */
	public int getMetricId() {
		return _metricInput.getSelectedId();
	}
	
	/**
	 * @return task, sprint id, etc.
	 */
	public int getSpecificId() {
		if (_specificMetricInput.isSelected()) {
			return _specificMetricInput.getSelectedId();
		} else {
			return -1;
		}
	}
	
	/**
	 * Update box with objects
	 * @param model model to set
	 */
	public void updateObjectBox(IdValueComboBoxModel model) {
		_specificMetricInput.setIVModel(model);
	}
	
	/// metric model
	private MetricModel _metricModel;
	/// metric table model
	private MetricTableModel _metricTableModel;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
	/// this combo box controls what kind of information will be available next
	private JComboBox _metricSelectionInput;
	/// user can select either a sprint or a task, etc. from this list
	private StandardComboBox _specificMetricInput;
	/// metric type combo box
	private StandardComboBox _metricInput;
	/// add/edit/remove panel
	private AddEditRemovePanel _addEditRemovePanel;
	/// serialization id
	private static final long serialVersionUID = -8207088791556687634L;

}
