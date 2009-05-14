package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.listener.MetricListener;
import scrummer.model.MetricModel;
import scrummer.model.Models;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.ui.Validate;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Change any metric name or description
 */
public class MetricChangeDialog 
	extends TwoButtonDialog 
	implements MetricListener, ItemListener {

	public MetricChangeDialog(JFrame parent) {
		super(parent);
		
		setSize(320, 200);
		setTitle(i18n.tr("Change Metric"));
		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
		
		int k = 10;
		Panel.setBorder (	
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Metric"), 
				k - 6, k, k - 2, k)	
			);
		
		FormBuilder fb = new FormBuilder(Panel);
		fb.setCellSpacing(5, 5);
		_metricInput = 
			fb.addComboBoxInput(i18n.tr("Metric") + ":");
		_nameInput =
			fb.addSelectedTextInput(i18n.tr("Name") + ":", "Name");
		_descriptionInput =
			fb.addSelectedTextInput(i18n.tr("Description") + ":", "Description");
		
		_metricInput.setIVModel(_metricModel.getMetricComboBoxModel());
		_metricInput.addItemListener(this);
		
		int id = _metricInput.getSelectedId();
		if (id != -1) {
			updateTextData(id);
		} else {
			OK.setEnabled(false);			
		}
		
		k = 10;
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k + 4, k - 3));
		
		OK.setText(i18n.tr("Update"));
	}
	
	/**
	 * Update text data
	 * @param id metric id 
	 */
	private void updateTextData(int id) {
		_nameInput.setText(_metricModel.getMeasureName(id));
		_descriptionInput.setText(_metricModel.getMeasureDescription(id));
	}
	
	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {	
		if ((type == DataOperation.Update) && (identifier == MetricOperation.Measure)) {
			setVisible(false);
		}
	}
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Update) && (identifier == MetricOperation.Measure)) {
			Util.showError(this, i18n.tr("Error while updating measure: ") + message, i18n.tr("Error"));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "StandardDialog.OK") {
			if (!Validate.empty(_nameInput, this)) return;
			if (!Validate.empty(_descriptionInput, this)) return;
			
			_metricModel.updateMeasure(
				_metricInput.getSelectedId(), 
				_nameInput.getText(), _descriptionInput.getText());			
		} else {
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int id = _metricInput.getSelectedId();
			updateTextData(id);
		}
	}	 
	
	@Override
	public void setVisible(boolean b) {
		if (!b) {
			_metricModel.removeMetricListener(this);
		}
		
		super.setVisible(b);
	}
	
	/// metric model
	private MetricModel _metricModel;
	/// metric selection combo box
	private StandardComboBox _metricInput;
	/// form fields
	private SelectedTextField _nameInput, _descriptionInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 6620339086504727227L;
	
}
