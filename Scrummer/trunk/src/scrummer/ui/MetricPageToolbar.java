package scrummer.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;

import scrummer.Scrummer;
import scrummer.model.MetricModel;
import scrummer.model.Models;
import scrummer.model.swing.MetricTableModel;
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
		
		AddEditRemovePanel right = new AddEditRemovePanel();		
		add(right);
		
		_metricSelectionInput.addItem("Sprint");
		_metricSelectionInput.addItem("Task");
		
		_metricInput.setIVModel(_metricModel.getMetricComboBoxModel());		
		
		_metricSelectionInput.setSelectedIndex(0);
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
	
	/// metric model
	private MetricModel _metricModel;
	/// metric table model
	private MetricTableModel _metricTableModel;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
	/// this combo box controls what kind of information will be available next
	private StandardComboBox _metricSelectionInput;
	/// user can select either a sprint or a task, etc. from this list
	private StandardComboBox _specificMetricInput;
	/// metric type cmbo box
	private StandardComboBox _metricInput;
	/// add/edit/remove panel
	private AddEditRemovePanel _addEditRemovePanel;
	/// serialization id
	private static final long serialVersionUID = -8207088791556687634L;

}
