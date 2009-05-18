package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.model.swing.MetricTableModel;
import scrummer.ui.Util;
import scrummer.ui.Validate;

/**
 * Add measure dialog
 */
public class MeasureAddDialog extends MeasureDialog {

	/**
	 * Constructor
	 * 
	 * @param parent parent control
	 * @param metricType metric type
	 * @param metricId which metric is being added
	 * @param objectId object id(task id, sprint id, ...)
	 */
	public MeasureAddDialog(JFrame parent, MetricTableModel.MetricType metricType, int metricId, int objectId) {
		super(parent);
		
		_metricType = metricType;
		_metricId = metricId;
		_objectId = objectId;
		
		switch (_metricType) {
		case Sprint:
			setTitle(i18n.tr("Add Sprint Measurement"));	
			break;
		case Task:
			setTitle(i18n.tr("Add Task Measurement"));
			break;
		}
		
		_dateInput.setText(Util.today());
		_resultInput.setText("0");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			
			if (!Validate.empty(_dateInput, this)) return;
			if (!Validate.empty(_resultInput, this)) return;
			
			Date d = Validate.date(_dateInput, i18n.tr("Wrong date format."), this); if (d == null) return;			
			// convert to date
			BigDecimal num = new BigDecimal(_resultInput.getText());
			
			switch (_metricType) {
			case Sprint:
				_metricModel.addSprintMeasurement(_metricId, _objectId, d, num);	
				break;
			case Task:
				_metricModel.addTaskMeasurement(_metricId, _objectId, d, num);
				break;
			}
			
		} else if (cmd.equals("Calculate")) {
			
		} else {
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {
		switch (identifier) {
		case ReleaseMeasure:
		case SprintMeasure:
		case TaskMeasure:
			if (type == DataOperation.Insert) {
				setVisible(false);
			}
			break;
		}		
	}
	
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {
		switch (identifier) {
		case ReleaseMeasure:
		case SprintMeasure:
		case TaskMeasure:
			if (type == DataOperation.Insert) {
				Util.showError(this, 
					i18n.tr("Error while adding new measure: ") + message, i18n.tr("Error"));
			}
			break;
		}
	}
	
	/// metric type
	private MetricTableModel.MetricType _metricType;
	/// metric kind
	private int _metricId;
	/// object id
	private int _objectId;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2322146297584720409L;	
}
