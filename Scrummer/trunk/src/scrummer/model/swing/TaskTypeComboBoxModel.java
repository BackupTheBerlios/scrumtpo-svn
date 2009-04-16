package scrummer.model.swing;

import scrummer.model.TaskTypeModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Task type combo box model contains id's + task type descriptions
 */
public class TaskTypeComboBoxModel extends IdValueComboBoxModel 
{
	/**
	 * Constructor
	 * @param tasktypeModelCommon common task type operations
	 */
	public TaskTypeComboBoxModel(TaskTypeModelCommon tasktypeModelCommon)
	{
		_tasktypeModelCommon = tasktypeModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshTaskType();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshTaskType()
	{
		setValues(_tasktypeModelCommon.fetchTaskTypeNames());		
	}

	/// common administrative days operations
	private TaskTypeModelCommon _tasktypeModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
