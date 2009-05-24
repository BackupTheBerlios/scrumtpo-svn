package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.model.swing.MetricTableModel;
import scrummer.model.swing.MetricTableModel.MetricType;
import scrummer.ui.Util;
import scrummer.ui.Validate;

/**
 * Dialog for modifying currently selected measure
 */
public class MeasureChangeDialog extends MeasureDialog {

	/**
	 * Constructor
	 * @param metric metric type
	 * @param measureId measure id
	 * @param parent parent control
	 * @param date date entered
	 * @param value measure value
	 */
	public MeasureChangeDialog(JFrame parent, MetricTableModel.MetricType metric, int measureId, int objectId, Date date, BigDecimal value) {
		super(parent, objectId);
		setTitle(i18n.tr("Change Measurement"));
		
		_metricType = metric;
		_measureId = measureId;
		_objectId = objectId;
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		_dateInput.setText(df.format(date));
		_resultInput.setSelectedItem(value.toEngineeringString());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "StandardDialog.OK") {
			
			if (!Validate.empty(_dateInput, this)) return;
		
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			Date d = Validate.date(_dateInput, i18n.tr("Wrong date format."), this);
			BigDecimal bd = new BigDecimal(_resultInput.getSelectedItem().toString());
			
			switch (_metricType) {
			case Sprint:
				_metricModel.updateSprintMeasurement(_measureId, _objectId, d, bd);
				break;
			case Task:	
				_metricModel.updateTaskMeasurement(_measureId, _objectId, d, bd); 
				break;
			}
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
			if (type == DataOperation.Update) {
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
			if (type == DataOperation.Update) {
				Util.showError(this, 
					i18n.tr("Error while updating new measure: ") + message, i18n.tr("Error"));
			}
			break;
		}
	}
	
	/// metric type
	private MetricType _metricType;
	/// measure id
	private int _measureId;
	/// object id
	private int _objectId;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 9015009310552580277L;	
}
