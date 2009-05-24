package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.listener.MetricListener;
import scrummer.model.MetricModel;
import scrummer.model.Models;

/**
 * Dialog that asks for a date until which to calculate earned value
 */
public class EarnedValueDialog 
	extends DateDialog 
	implements MetricListener {

	/**
	 * Constructor
	 * @param parent parent frame
	 * @param sprintId sprint id
	 */
	public EarnedValueDialog(JFrame parent, int sprintId) {
		super(parent);
		setTitle(i18n.tr("Calculate Earned Value"));
		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
		
		_sprintId = sprintId;
		_sprintStart = m.getSprintBacklogModel().getBeginDate(sprintId);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK") {
			if (valid()) {
				_metricModel.calculateEarnedValue(_sprintId, _sprintStart, getDate());
			}
		} else if (cmd == "StandardDialog.Cancel") {
			_metricModel.failCalculatingEarnedValue();			
		} else {
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Custom) && (identifier == MetricOperation.EarnedValueCalculated)) {
			setVisible(false);
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {
		// if ((type == DataOperation.Custom) && (identifier == MetricOperation.EarnedValueCalculated)) {
		// 	Util.showError(this, message, i18n.tr("Error"));
		// }
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
	/// sprint id
	private int _sprintId;
	/// start of sprint
	private Date _sprintStart;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -6349541479553102303L;	
}