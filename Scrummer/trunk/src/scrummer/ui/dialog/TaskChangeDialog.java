package scrummer.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskOperation;
import scrummer.listener.TaskListener;
import scrummer.model.DBSchemaModel.TaskEnum;
import scrummer.ui.Util;
import scrummer.ui.Validate;

/**
 * Task modification dialog
 */
public class TaskChangeDialog
	extends TaskDialog 
	implements TaskListener {

	/**
	 * Constructor
	 * @param owner owning frame
	 * @param taskId task id
	 */
	public TaskChangeDialog(Frame owner, int taskId) 
	{
		super(owner);
	
		_taskId = taskId;
		setTitle(i18n.tr("Change Task"));
		_taskModel.addTaskListener(this);
		
		_descriptionInput.setText(
			_taskModel.getString(TaskEnum.TaskDescription, taskId));
		_pbiInput.selectId(_taskModel.getInteger(TaskEnum.PBIId, taskId));
		_parentInput.selectId(_taskModel.getInteger(TaskEnum.TaskParentId, taskId));
		_taskEmployeeInput.selectId(_taskModel.getInteger(TaskEnum.EmployeeId, taskId));
		_taskTeamInput.selectId(_taskModel.getInteger(TaskEnum.TeamId, taskId));
		_taskTypeInput.selectId(_taskModel.getInteger(TaskEnum.TaskTypeId, taskId));
		_taskStatusInput.selectId(_taskModel.getInteger(TaskEnum.TaskStatusId, taskId));
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date d = _taskModel.getDate(TaskEnum.TaskDate, taskId);
		_dateInput.setText((d == null) ? "" : df.format(d));
		_taskActiveInput.setSelectedIndex((_taskModel.getBoolean(TaskEnum.TaskActive, taskId)) ? 0 : 1);		
	}

	@Override
	public void operationFailed(DataOperation type, TaskOperation identifier, String message) {
		if ((type == DataOperation.Update) && (identifier == TaskOperation.Task))
		{
			Util.showError(this, i18n.tr("Error while updating task: " + message), i18n.tr("Error"));
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, TaskOperation identifier, String message) {
		if ((type == DataOperation.Update) && (identifier == TaskOperation.Task))
		{
			setVisible(false);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand(); 
		if (cmd == "StandardDialog.OK")
		{	
			if (!Validate.empty(_descriptionInput, this)) { return; }
			
			int pbiId = 
				_pbiInput.getSelectedId();
			int parentId =
				_parentInput.getSelectedId();
			int employeeId = 
				_taskEmployeeInput.getSelectedId();
			int teamId = 
				_taskTeamInput.getSelectedId();
			int taskTypeId = 
				_taskTypeInput.getSelectedId();
			int taskStatusId = 
				_taskStatusInput.getSelectedId();
			Date date = 
				Validate.date(_dateInput, i18n.tr("Wrong task end date formatting."), this);
			if (date == null) { return; }
			boolean active = 
				_taskActiveInput.getSelectedIndex() == 0;
			
			_taskModel.updateTask(
				_taskId, 
				_descriptionInput.getText(),
				parentId,
				pbiId,
				employeeId,
				teamId,
				taskTypeId,
				taskStatusId,
				date,
				active);
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	/// task id
	private int _taskId;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2229297107817158452L;
}
