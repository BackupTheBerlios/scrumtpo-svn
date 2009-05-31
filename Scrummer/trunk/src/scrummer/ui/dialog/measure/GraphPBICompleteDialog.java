package scrummer.ui.dialog.measure;

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
import scrummer.model.ReleaseModel;
import scrummer.model.SprintBacklogModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.SprintDialog;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Dialog for selection of sprint and release for pbi sprint completion indicator
 */
public class GraphPBICompleteDialog 
	extends TwoButtonDialog 
	implements MetricListener {

	public GraphPBICompleteDialog(JFrame owner) {
		super(owner);		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
		setTitle(i18n.tr("PBI Completion"));
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
			k, k, k, k, 
			i18n.tr("Release/Sprint Information"), 
			2, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);		
		_sprintInput = 
			fb.addComboBoxInput(i18n.tr("Sprint") + ":");
		_releaseInput = 
			fb.addComboBoxInput(i18n.tr("Release") + ":");
				
		SprintBacklogModel sbmodel = m.getSprintBacklogModel();
		_sprintInput.setIVModel(sbmodel.getSprintProjectComboBoxModel());
		
		ReleaseModel releaseModel = m.getReleaseModel();
		_releaseInput.setIVModel(releaseModel.getReleaseComboBoxModel());
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 3));
		
		setSize(320, 180);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			_metricModel.calculatePBIComplete(_sprintInput.getSelectedId(), _releaseInput.getSelectedId());
		} else {
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Custom) && (identifier == MetricOperation.Graph)) {
			setVisible(false);
		}
	}

	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Custom) && (identifier == MetricOperation.Graph)) {
			Util.showError(
			this, 
			i18n.tr("Error occurred while calculating pbi completed in sprint: ") + message, 
			i18n.tr("Error"));
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		if (!b) {
			_metricModel.removeMetricListener(this);
		}
		super.setVisible(b);
	}

	/// sprint input combo box
	protected StandardComboBox _sprintInput, _releaseInput;
	/// metric model
	private MetricModel _metricModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 8261531596582713797L;
}
