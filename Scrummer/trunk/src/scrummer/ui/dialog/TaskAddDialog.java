package scrummer.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.Date;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskOperation;
import scrummer.listener.TaskListener;
import scrummer.ui.Util;
import scrummer.ui.Validate;

/**
 * Task add dialog
 */
public class TaskAddDialog 
	extends TaskDialog 
	implements TaskListener {

	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public TaskAddDialog(Frame owner) {
		super(owner);
		setTitle(i18n.tr("Add Task"));
		
		_taskModel.addTaskListener(this);
		_dateInput.setText(Util.today());
		
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

	@Override
	public void operationFailed(DataOperation type, TaskOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			switch (identifier)
			{
			case Task:
				Util.showError(this, i18n.tr("Error while adding task: " + message), i18n.tr("Error"));
				break;
			}
		break;
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, TaskOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			switch (identifier)
			{
			case Task:
				setVisible(false);				
				break;
			}
			break;
		}
	}
	
	@Override
	public void setVisible(boolean b) {

		if (!b)
		{
			_taskModel.removeTaskListener(this);
		}
		
		super.setVisible(b);
	}

	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -678805712571722387L;
	
}
