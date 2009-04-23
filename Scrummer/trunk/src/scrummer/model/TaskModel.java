package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskOperation;
import scrummer.listener.TaskListener;
import scrummer.model.swing.ProjectSprintTaskComboBoxModel;
import scrummer.model.swing.TaskComboBoxModel;
import scrummer.model.swing.TaskTableModel;
import scrummer.util.Operations;

/**
 * Task model
 *
 * Takes care of adding, removing and modifying tasks.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class TaskModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 * @param projectModel project model
	 * @param sprintModel sprint model
	 */
	public TaskModel(ConnectionModel connectionModel, ProjectModel projectModel, SprintBacklogModel sprintModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_projectModel = projectModel;
		_sprintBacklogModel = sprintModel;
		_taskModelCommon = 
			new TaskModelCommon(_connectionModel, _operation);
		_taskTableModel = 
			new TaskTableModel(connectionModel, _taskModelCommon);
		_taskComboBoxModel = 
			new TaskComboBoxModel(_taskModelCommon);
		_projectSprintTaskComboBoxModel = 
			new ProjectSprintTaskComboBoxModel(_taskModelCommon);
	}
	
	/**
	 * Add task
	 * 
	 * @param description task description
	 * @param parent parent task (if any)
	 * @param pbi related pbi(a task does not exist without one)
	 * @param employee responsible employee
	 * @param team team
	 * @param status task status
	 * @param type task type
	 * @param date end date
	 * @param active is task active
	 */
	public void add(String description, Integer parent, Integer pbi, int employee, int team, int status, int type, Date date, boolean active)
	{
		_taskModelCommon.add(description, parent, pbi, employee, team, status, type, date, active);
	}
	
	/**
	 * Task table model
	 * 
	 * @return task table model
	 */
	public TaskTableModel getTaskTableModel()
	{
		return _taskTableModel;
	}	
	
	/**
	 * Add task data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addTaskListener(TaskListener listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove task data change listener
	 * @param listener listener to remove
	 */
	public void removeTaskListener(TaskListener listener)
	{
		_operation.removeListener(listener);
	}
	
	/**
	 * Fetch task combo box model
	 * @return model
	 */
	public TaskComboBoxModel getTaskComboBoxModel()
	{
		return _taskComboBoxModel;
	}
	
	/**
	 * @return project/sprint based task combo box model
	 */
	public ProjectSprintTaskComboBoxModel getProjectSprintTaskComboBoxModel()
	{
		_projectSprintTaskComboBoxModel.setProject(_projectModel.getCurrentProjectId());
		_projectSprintTaskComboBoxModel.setSprint(_sprintBacklogModel.getCurrentSprint());
		return _projectSprintTaskComboBoxModel;
	}
			
	/// project model
	private ProjectModel _projectModel;
	/// sprint backlog model
	private SprintBacklogModel _sprintBacklogModel;
	/// common task related functionality
	private TaskModelCommon _taskModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// task combo box model
	private TaskComboBoxModel _taskComboBoxModel;
	/// all task descriptions on given project and sprint
	private ProjectSprintTaskComboBoxModel _projectSprintTaskComboBoxModel;
	/// task table model
	private TaskTableModel _taskTableModel;
	/// task operation
	private Operations.TaskOperation _operation = new Operations.TaskOperation();
}
