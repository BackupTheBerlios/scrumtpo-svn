package scrummer.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.ui.Validate;

/**
 * Task add dialog
 */
public class TaskAddDialog extends TaskDialog {

	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public TaskAddDialog(Frame owner) {
		super(owner);
		setTitle(i18n.tr("Add Task"));
		
		GregorianCalendar gc = new GregorianCalendar();
		String todayStr = 
			gc.get(Calendar.DAY_OF_MONTH) + "." + 
			(gc.get(Calendar.MONTH) - Calendar.JANUARY + 1) + "." + 
			gc.get(Calendar.YEAR);
		_dateInput.setText(todayStr.toString());
		
		OK.setText(i18n.tr("Add"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			String description = Validate.empty(_descriptionInput, 
				i18n.tr("Task description must contain at least one nonempty character."), 
				this);
						
			if (description == null) { _descriptionInput.grabFocus(); return; }
			
			Date taskDate = Validate.date(_dateInput, 
				i18n.tr("Task date must be a valid date."), 
				this);
			
			if (taskDate == null) { _dateInput.grabFocus(); return; }
			
			_taskModel.add(
				description,
				(_parentInput.isSelected() ? _parentInput.getSelectedId() : null),
				(_pbiInput.isSelected() ? _pbiInput.getSelectedId() : null),
				_taskEmployeeInput.getSelectedId(),
				_taskTeamInput.getSelectedId(),
				_taskStatusInput.getSelectedId(),
				_taskTypeInput.getSelectedId(),
				taskDate, 
				_taskActiveInput.getSelectedIndex() == 0);
		}	
		else
		{
			super.actionPerformed(e);
		}
	}

	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -678805712571722387L;
}
