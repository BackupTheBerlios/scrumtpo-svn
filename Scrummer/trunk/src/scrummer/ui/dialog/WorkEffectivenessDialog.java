package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.listener.MetricListener;
import scrummer.model.MetricModel;
import scrummer.model.Models;

/**
 * A dialog that calculates work effectiveness
 */
public class WorkEffectivenessDialog 
	extends DateRangeDialog
	implements MetricListener {

	/**
	 * Constructor
	 * @param parent parent frame
	 */
	public WorkEffectivenessDialog(JFrame parent) {
		super(parent);
		
		setTitle(i18n.tr("Calculate Work Effectiveness"));
		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK") {
			if (valid())
				_metricModel.calculateWorkEffectiveness(getFirstDate(), getSecondDate());
		} else if (cmd == "StandardDialog.Cancel") {
			_metricModel.failCalculatingWorkEffectiveness();
			super.actionPerformed(e);
		} else {
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Custom) && (identifier == MetricOperation.WorkEffectivenessCalculated)) {
			setVisible(false);
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		if (!b) {
			_metricModel.removeMetricListener(this);
		}
		super.setVisible(b);
	}
	
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// metric model
	private MetricModel _metricModel;
	/// serialization id
	private static final long serialVersionUID = 8273460425093555118L;
	
}
