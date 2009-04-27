package scrummer.model;

import java.util.Date;
import scrummer.exception.DBMap;
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
		if (_taskModelCommon.add(description, parent, pbi, employee, team, status, type, date, active))
		{
			_taskTableModel.refresh();
			_sprintBacklogModel.getTaskTableModel().refresh();
		}
	}
	
	
	/**
	 * Update task 
	 * 
	 * @param taskId task id
	 * @param description task description 
	 * @param pbiId task parent pbi
	 * @param employeeId task employee
	 * @param teamId task responsible team
	 * @param taskTypeId type of task
	 * @param taskStatusId task status
	 * @param date task date
	 * @param active task active?
	 */
	public void updateTask(int taskId, String description, int parentId, int pbiId, int employeeId, int teamId, int taskTypeId, int taskStatusId, Date date, boolean active) {
		if (_taskModelCommon.updateTask(
			taskId, description, parentId, pbiId, employeeId, 
			teamId, taskTypeId, taskStatusId, date, active))
		{
			_taskTableModel.refresh();
			_sprintBacklogModel.getTaskTableModel().refresh();
		}
	}
	
	public Integer getInteger(DBSchemaModel.TaskEnum enumId, int taskId)
	{
		TaskModelCommon.Row row = _taskModelCommon.getRow(taskId);
		switch (enumId)
		{
		case TaskId: 
			return row.TaskId;
		case EmployeeId: 
			return row.EmployeeId;
		case PBIId:
			return row.PBIId;
		case TaskParentId:
			return row.TaskParentId;
		case TaskStatusId:
			return row.TaskStatus;
		case TaskTypeId:
			return row.TaskType;
		case TeamId:
			return row.TeamId; 
		}
		throw new DBMap(enumId);
	}
	
	public String getString(DBSchemaModel.TaskEnum enumId, int taskId)
	{
		TaskModelCommon.Row row = _taskModelCommon.getRow(taskId);
		switch (enumId)
		{
		case TaskDescription:
			return row.TaskDescription;
		}
		throw new DBMap(enumId);
	}
	
	public Date getDate(DBSchemaModel.TaskEnum enumId, int taskId)
	{
		TaskModelCommon.Row row = _taskModelCommon.getRow(taskId);
		switch (enumId)
		{
		case TaskDate:
			return row.TaskDate;
		}
		throw new DBMap(enumId);
	}
	
	public Boolean getBoolean(DBSchemaModel.TaskEnum enumId, int taskId)
	{
		TaskModelCommon.Row row = _taskModelCommon.getRow(taskId);
		switch (enumId)
		{
		case TaskActive:
			return row.TaskActive;
		}
		throw new DBMap(enumId);
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
	
	/**
	 * Remove task with given id
	 * @param id task id
	 */
	public void remove(int id) {
		if (_taskModelCommon.removeTask(id))
		{
			_taskTableModel.refresh();
			_sprintBacklogModel.getTaskTableModel().refresh();
		}
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
