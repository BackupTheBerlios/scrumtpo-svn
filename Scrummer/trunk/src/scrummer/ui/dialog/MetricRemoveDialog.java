package scrummer.ui.dialog;

import java.awt.event.ActionEvent;

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
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;
import sun.font.StandardTextSource;

/**
 * Remove metrc dialog
 */
public class MetricRemoveDialog 
	extends TwoButtonDialog 
	implements MetricListener {

	public MetricRemoveDialog(JFrame parent) {
		super(parent);
		
		setSize(320, 176);
		
		setTitle(i18n.tr("Add Metric"));
		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
		
		int k = 10;
		Panel.setBorder (	
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Metric"), 
				k - 4, k, k, k)	
			);

		FormBuilder fb = new FormBuilder(Panel);
		_metricInput = fb.addComboBoxInput(i18n.tr("Metric") + ":");
		_metricInput.setIVModel(_metricModel.getMetricComboBoxModel());

		k = 10;
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 3));
		
		OK.setText(i18n.tr("Remove"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "StandardDialog.OK") {
			if (_metricInput.isSelected()) {
				_metricModel.removeMeasure(_metricInput.getSelectedId());
			}
		} else {		
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Remove) && (identifier == MetricOperation.Measure)) {
			setVisible(false);
			
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Remove) && (identifier == MetricOperation.Measure)) {
			Util.showError(this, i18n.tr("Error while removing metric") + ":" + message, i18n.tr("Error"));
		}
	}

	/// metric input combo box
	private StandardComboBox _metricInput;	
	/// metric model
	private MetricModel _metricModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -9109242627754296589L;
}