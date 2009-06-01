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
import scrummer.ui.FormBuilder;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Dialog for asking about all sprints customer poll
 */
public class GraphAllCustomerPollDialog 
	extends TwoButtonDialog 
	implements MetricListener {	

	public GraphAllCustomerPollDialog(JFrame owner) {
		super(owner);
		int k = 10;
		Panel.setBorder(
			scrummer.ui.Util.createSpacedTitleBorder(
			k, k, k, k, 
			i18n.tr("Question Information"), 
			2, k, k, k));
		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
		FormBuilder	fb = new FormBuilder(Panel);		
		_questionInput = fb.addComboBoxInput(i18n.tr("Question"));
				
		_questionInput.setIVModel(_metricModel.getQuestionDeveloperComboBoxModel());
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 3));
		
		setSize(320, 150);
		setTitle(i18n.tr("Plot All Sprint Customer Poll"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			_metricModel.calculateAllSprintCustomerPoll(_questionInput.getSelectedId());
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
			scrummer.ui.Util.showError(this, i18n.tr("Error while calculating all developer marks poll: " + message), i18n.tr("Error"));
		}
	}
	
	@Override
	public void setVisible(boolean b) {	
		if (!b) {
			_metricModel.removeMetricListener(this);
		}
		super.setVisible(b);
	}

	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// metric model
	private MetricModel _metricModel;
	/// question input
	private StandardComboBox _questionInput;
	/// serialization id
	private static final long serialVersionUID = 3375207255648355217L;
}
