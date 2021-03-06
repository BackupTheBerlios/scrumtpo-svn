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
import scrummer.model.SprintBacklogModel;
import scrummer.ui.dialog.DateDialog;

/**
 * Dialog for calculation of spi value
 */
public class SPIDialog 
	extends DateDialog 
	implements MetricListener {

	/**
	 * Constructor
	 * 
	 * @param parent parent frame
	 * @param sprintId sprint
	 */
	public SPIDialog(JFrame parent, int sprintId) {
		super(parent);

		setTitle(i18n.tr("Calculate SPI"));
		
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
				Models m = Scrummer.getModels();
				SprintBacklogModel sprintBacklogModel = m.getSprintBacklogModel();
				_metricModel.calculateSchedulePerformanceIndex(_sprintId, sprintBacklogModel.getBeginDate(_sprintId), getDate());
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
	/// serialization id
	private static final long serialVersionUID = -4626533630643632980L;
}
