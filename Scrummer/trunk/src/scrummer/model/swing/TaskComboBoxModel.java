package scrummer.model.swing;

import scrummer.model.TaskModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Task combo box model contains id's + task descriptions
 */
public class TaskComboBoxModel extends IdValueComboBoxModel 
{
	/**
	 * Constructor
	 * @param taskModelCommon common task operations
	 */
	public TaskComboBoxModel(TaskModelCommon taskModelCommon)
	{
		_taskModelCommon = taskModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshTasks();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshTasks()
	{
		setValues(_taskModelCommon.fetchTaskNames());		
	}
	
	/// common task operations
	private TaskModelCommon _taskModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}