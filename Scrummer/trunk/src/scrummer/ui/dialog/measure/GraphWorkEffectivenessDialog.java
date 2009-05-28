package scrummer.ui.dialog.measure;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.listener.MetricListener;
import scrummer.model.MetricModel;
import scrummer.model.Models;
import scrummer.ui.SprintDialog;
import scrummer.ui.Util;

/**
 * Calculate work effectiveness for this sprint and set it as current graph
 */
public class GraphWorkEffectivenessDialog 
	extends SprintDialog 
	implements MetricListener {

	public GraphWorkEffectivenessDialog(JFrame owner) {
		super(owner);				
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();	
		setTitle(i18n.tr("Work effectiveness"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			_metricModel.calculateMonthlyEarnedValue(_sprintInput.getSelectedId());
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
			i18n.tr("Error occurred while calculating work effectiveness."), 
			i18n.tr("Error"));
		}
	}
	
	/// metric model
	private MetricModel _metricModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 8543571106546856980L;

}
