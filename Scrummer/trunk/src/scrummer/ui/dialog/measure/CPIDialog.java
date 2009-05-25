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
import scrummer.ui.dialog.DateDialog;

/**
 * CPI calculation dialog 
 */
public class CPIDialog 
	extends DateDialog 
	implements MetricListener {

	/**
	 * Constructor
	 * 
	 * @param parent parent frame
	 * @param sprintId sprint id
	 */
	public CPIDialog(JFrame parent, int sprintId) {
		super(parent);

		setTitle(i18n.tr("Calculate CPI"));
	
		_sprintId = sprintId;
		
		Models m = Scrummer.getModels();
		_metricModel = m.getMetricModel();
		_metricModel.addMetricListener(this);
	}

	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {
		if ((type == DataOperation.Custom) && (identifier == MetricOperation.CPICalculated)) {
			setVisible(false);
		}		
	}
	
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			if (valid()) {
				// _metricModel.calculateCPI()
			}
		} else if (cmd.equals("StandardDialog.Cancel")) {
			super.actionPerformed(e);
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

	/// sprint id
	private int _sprintId;
	/// metric model
	private MetricModel _metricModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization i
	private static final long serialVersionUID = -2270345477050818129L;
	
}
