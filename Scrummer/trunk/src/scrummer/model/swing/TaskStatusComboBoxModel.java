package scrummer.model.swing;

import scrummer.model.TaskStatusModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Absence type combo box model contains id's + absence type descriptions
 */
public class TaskStatusComboBoxModel extends IdValueComboBoxModel 
{
	/**
	 * Constructor
	 * @param taskstatusModelCommon common task status operations
	 */
	public TaskStatusComboBoxModel(TaskStatusModelCommon taskstatusModelCommon)
	{
		_taskstatusModelCommon = taskstatusModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshTaskStatus();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshTaskStatus()
	{
		setValues(_taskstatusModelCommon.fetchTaskStatusNames());		
	}

	/// common task status operations
	private TaskStatusModelCommon _taskstatusModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
