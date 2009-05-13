// vstavljanje v del sprint backloga
// najprej izbere� ustrezni task (glede na task_description)
// nato izbere� dan za katerega vna�a� porabljene/preostale ure ter ovire
// nato ima� mo�nost vnesti "spent_hours", "remaining_hours", "nb_open_imped", "nb_closed_imped"
// na koncu vse shrani�

package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.listener.SprintBacklogListener;
import scrummer.model.Models;
import scrummer.model.PropertyModel;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.TaskComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.ui.Validate;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

public class DailyScrumMeetingDialog 
	extends TwoButtonDialog
	implements SprintBacklogListener, FocusListener {

	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public DailyScrumMeetingDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Daily scrum meeting"));

		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Daily Scrum"), 
				4, k, k, k + 2));
		
		Models m = Scrummer.getModels();
		_sprintbacklogModel = m.getSprintBacklogModel();
		_sprintbacklogModel.addSprintBacklogListener(this);
		_taskComboModel = m.getTaskModel().getTaskComboBoxModel();
		_propertyModel = m.getPropertyModel();
	
		FormBuilder fb = new FormBuilder(Panel);
		
		_sprintInput =
			fb.addComboBoxInput(i18n.tr("Sprint") + ":");
		_employeeInput = 
			fb.addComboBoxInput(i18n.tr("Employee") + ":");
		_taskInput =
			fb.addComboBoxInput(i18n.tr("Task") + ":");
		
		String defaultMeasureDay = _propertyModel.getProperty("uidefault.DailyScrumDialog.lastMeasureDay");
		_measuredayInput = 
			fb.addSelectedTextInput(i18n.tr("Measure day") + ":", "MeasureDay", defaultMeasureDay);
		_hoursspentInput = 
			fb.addSelectedTextInput(i18n.tr("Spent hours") + ":", "HoursSpent", "0");
		_hoursremainInput = 
			fb.addSelectedTextInput(i18n.tr("Remaining hours") + ":", "HoursRemain", "0");
		_nbopenimpedInput = 
			fb.addSelectedTextInput(i18n.tr("Number of open impediments") + ":", "NbOpenImped", "0");
		_nbclosedimpedInput = 
			fb.addSelectedTextInput(i18n.tr("Number of closed impediments") + ":", "NbClosedImped", "0");
		
		fb.setCellSpacing(5, 5);
		
		_sprintInput.setIVModel(m.getSprintBacklogModel().getSprintProjectComboBoxModel());
	
		// fetch last selected sprint
		String lastSprint = _propertyModel.getProperty("uidefault.DailyScrumDialog.lastSprint"); 
		if (!(lastSprint.length() == 0)) {
			Integer i = Integer.parseInt(lastSprint);
			if (i < _sprintInput.getItemCount()) {
				_sprintInput.setSelectedIndex(i);
			}
		}
		
		_employeeInput.setIVModel(m.getDeveloperModel().getEmployeeComboBoxModel());
		_taskInput.setIVModel(_taskComboModel);		
		
		_sprintInput.addFocusListener(this);
		_measuredayInput.addFocusListener(this);
		
		// ret.setProperty("uidefault.DailyScrumDialog.lastSprint", "");
		// last input measure day - so it doesn't have to be selected for each user every time 
		// ret.setProperty("uidefault.DailyScrumDialog.lastMeasureDay", "1");		
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 5, k - 3));
		
		OK.setText("Save");
		setSize(new Dimension(460, 330));
	}	
	
	@Override
	public void actionPerformed(ActionEvent e)  {	
		if (e.getActionCommand() == "StandardDialog.OK") {
			int currentSprint = _sprintInput.getSelectedId();
			if (!Validate.inrange(
					_measuredayInput, 1, 
					currentSprint, 
					i18n.tr("Sprint day is out sprint days range."), this)) return;				
		
			if (!Validate.empty(_measuredayInput, this)) _measuredayInput.setText("1");
			if (!Validate.empty(_hoursspentInput, this)) _hoursspentInput.setText("0");
			if (!Validate.empty(_hoursremainInput, this)) _hoursremainInput.setText("0");
			if (!Validate.empty(_nbopenimpedInput, this)) _nbopenimpedInput.setText("0");
			if (!Validate.empty(_nbclosedimpedInput, this)) _nbclosedimpedInput.setText("0");
			
			Integer hoursSpent = 
				Integer.parseInt(_hoursspentInput.getText().trim());
			Integer hoursRemaining = 
				Integer.parseInt(_hoursremainInput.getText().trim());
			Integer nbOpenImped = 
				Integer.parseInt(_nbopenimpedInput.getText().trim());
			Integer nbClosedImped = 
				Integer.parseInt(_nbclosedimpedInput.getText().trim());
			
			Integer measureDay = 
				Integer.parseInt(_measuredayInput.getText()); 			
			
			if (_sprintbacklogModel.existsSprintPBI(
					measureDay, _taskInput.getSelectedId(), currentSprint, _employeeInput.getSelectedId())) {
				_sprintbacklogModel.updateDailyMeasure(
					currentSprint, _taskInput.getSelectedId(), measureDay, 
					_employeeInput.getSelectedId(), 
					hoursSpent, hoursRemaining, nbOpenImped, nbClosedImped);
			} else {
				_sprintbacklogModel.addDailyEntry(
					currentSprint, 
					_taskInput.getSelectedId(), 
					measureDay, 
					_employeeInput.getSelectedId(), 
					hoursSpent, hoursRemaining, nbOpenImped, nbClosedImped);
			}	
		} else {
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == _sprintInput) {
			// sprint is always a valid selection, store it
			_propertyModel.setProperty(
				"uidefault.DailyScrumDialog.lastSprint", 
				new Integer(_sprintInput.getSelectedIndex()).toString());
		} else if (e.getSource() == _measuredayInput) {
			// check if not empty and is a number
			if (Validate.empty(_measuredayInput, this)) {
				_propertyModel.setProperty(
					"uidefault.DailyScrumDialog.lastMeasureDay", 
					_measuredayInput.getText().trim());
			}
		}
	}	

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {
		if (((type == DataOperation.Insert) ||
			 (type == DataOperation.Update)) && 
			(identifier == SprintBacklogOperation.SprintPBI)) {
			_taskComboModel.refresh();
			_taskInput.setSelectedIndex(0);
			setVisible(false);
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {
		if (((type == DataOperation.Insert) || 
			 (type == DataOperation.Update)) && 
			(identifier == SprintBacklogOperation.SprintPBI)) {
			Util.showError(this, 
				i18n.tr("An error has occurred when setting team name") + ": " + message, 
				i18n.tr("Error"));
		}
	}

	@Override
	public void setVisible(boolean b) {
		if (!b) {
			_sprintbacklogModel.removeSprintBacklogListener(this);
		}
		super.setVisible(b);
	}

	/// sprint backlog model
	private SprintBacklogModel _sprintbacklogModel;
	/// property model
	private PropertyModel _propertyModel;
	/// all SBI in combo box
	private TaskComboBoxModel _taskComboModel;
	/// team new name input
	private SelectedTextField _measuredayInput, _hoursspentInput, _hoursremainInput, _nbopenimpedInput, _nbclosedimpedInput;
	/// team input combo box
	private StandardComboBox _taskInput, _sprintInput, _employeeInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;
	
}
