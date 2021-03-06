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
 * Schedule performance index dialog
 */
public class GraphSPIDialog 
	extends SprintDialog 
	implements MetricListener {

	public GraphSPIDialog(JFrame owner) {
		super(owner);		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
		setTitle(i18n.tr("Schedule performance index"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			_metricModel.calculateMonthlySPI(_sprintInput.getSelectedId());
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
			i18n.tr("Error occurred while calculating schedule performance indices: ") + message, 
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

	/// metric model
	private MetricModel _metricModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -2727558052204012601L;
}
