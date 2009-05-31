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
import scrummer.model.SprintBacklogModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Dialog that asks for customer question and related sprint
 */
public class GraphCustomerPollDialog 
	extends TwoButtonDialog 
	implements MetricListener {

	public GraphCustomerPollDialog(JFrame owner) {
		super(owner);
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
		setTitle(i18n.tr("Customer Poll"));
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
			k, k, k, k, 
			i18n.tr("Release/Sprint Information"), 
			2, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);		
		_sprintInput = 
			fb.addComboBoxInput(i18n.tr("Sprint") + ":");
		_questionInput =
			fb.addComboBoxInput(i18n.tr("Question") + ":");
				
		SprintBacklogModel sbmodel = m.getSprintBacklogModel();
		_sprintInput.setIVModel(sbmodel.getSprintProjectComboBoxModel());		
		
		_questionInput.setIVModel(_metricModel.getQuestionCustomerComboBoxModel());
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 3));
		setTitle(i18n.tr("Plot Customer Question Poll"));
		setSize(450, 180);
	}
	
	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Custom) && (identifier == MetricOperation.Graph)) {
			setVisible(false);
		}
	}

	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {}	
		
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			_metricModel.calculateCustomerQuestionMark(_sprintInput.getSelectedId(), _questionInput.getSelectedId());
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

	/// inputs
	private StandardComboBox _sprintInput, _questionInput;
	/// metric model
	private MetricModel _metricModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 9056668221303727688L;
	
}
