package scrummer.model.swing;

import scrummer.model.SprintBacklogModelCommon;
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
	public TaskComboBoxModel(SprintBacklogModelCommon taskModelCommon)
	{
		_taskModelCommon = taskModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshSBIs();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshSBIs()
	{
		setValues(_taskModelCommon.fetchSBIsNames());
	}
	
	/// common impediment operations
	private SprintBacklogModelCommon _taskModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}