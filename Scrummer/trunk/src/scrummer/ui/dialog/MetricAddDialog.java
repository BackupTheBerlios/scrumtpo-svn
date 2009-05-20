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
import scrummer.ui.Validate;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add metric dialog
 */
public class MetricAddDialog 
	extends TwoButtonDialog 
	implements MetricListener {
	
	public MetricAddDialog(JFrame owner) {
		super(owner);
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
		_nameInput = 
			fb.addSelectedTextInput(i18n.tr("Name") + ":", "Name");
		_descriptionInput =
			fb.addSelectedTextInput(i18n.tr("Description"), "Description");		
		fb.setCellSpacing(5, 5);

		k = 10;
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 3));
		
		OK.setText(i18n.tr("Add"));
	}
	
	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Insert) && 
			(identifier == MetricOperation.Measure)) {
			setVisible(false);
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Insert) && 
			(identifier == MetricOperation.Measure)) {
			Util.showError(this, 
				i18n.tr("Error while adding new measure: ") + message, 
				i18n.tr("Error"));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "StandardDialog.OK") {
			if (!Validate.empty(_nameInput, this)) return;
			if (!Validate.empty(_descriptionInput, this)) return;
			_metricModel.addMeasure(
				_nameInput.getText(), 
				_descriptionInput.getText());
		} else {
			super.actionPerformed(e);
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
	/// form fields
	private SelectedTextField _nameInput, _descriptionInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5559526398555206961L;
	
}
