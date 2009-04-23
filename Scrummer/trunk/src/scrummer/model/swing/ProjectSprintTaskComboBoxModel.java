package scrummer.model.swing;

import scrummer.model.TaskModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * A list of all tasks(their descriptions) on current project and sprint.
 */
public class ProjectSprintTaskComboBoxModel extends IdValueComboBoxModel {

	/**
	 * Constructor
	 */
	public ProjectSprintTaskComboBoxModel(TaskModelCommon taskModelCommon) {
		_taskModel = taskModelCommon;
	}
	
	/**
	 * Set current project
	 * @param value project to set
	 */
	public void setProject(int value)
	{
		_projectId = value;		
	}
	
	/**
	 * Set current sprint
	 * @param value sprint to sets
	 */
	public void setSprint(int value)
	{
		_sprintId = value;
	}
	
	@Override
	public void refresh() {
		setValues(_taskModel.fetchTaskNames(_projectId, _sprintId));
		super.refresh();
	}

	/// project for which to fetch tasks
	private int _projectId;
	/// sprint for which to fetch tasks
	private int _sprintId;
	/// task model
	private TaskModelCommon _taskModel;
	/// serialization id
	private static final long serialVersionUID = -1108211890709304723L;
}
