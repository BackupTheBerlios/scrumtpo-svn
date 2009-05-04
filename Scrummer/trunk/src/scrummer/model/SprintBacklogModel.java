package scrummer.model;

import java.util.Date;

import scrummer.listener.SprintBacklogListener;
import scrummer.model.swing.AllTaskTableModel;
import scrummer.model.swing.ImpedimentTableModel;
import scrummer.model.swing.SBIComboBoxModel;
import scrummer.model.swing.SprintBacklogTableModel;
import scrummer.model.swing.SprintDescriptionListModel;
import scrummer.model.swing.SprintProjectComboBoxModel;
import scrummer.util.Operations;

/**
 * Sprint Backlog model
 *
 * Takes care of insert into, remove from and modifying Sprint Backlog.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class SprintBacklogModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public SprintBacklogModel(ConnectionModel connectionModel, ProjectModel projectModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_projectModel = projectModel;
		_sprintbacklogModelCommon = 
			new SprintBacklogModelCommon(_connectionModel, _operation);
		_sprintbacklogTableModel = 
			new SprintBacklogTableModel(connectionModel, _sprintbacklogModelCommon);
		_taskModelCommon =
			new TaskModelCommon(_connectionModel, _taskoperation);
		_sbiComboBoxModel = 
			new SBIComboBoxModel(_sprintbacklogModelCommon);
		_devModelCommon = 
			new DeveloperModelCommon(_connectionModel, _devOperation);
		_pbiModelCommon = 
			new ProductBacklogModelCommon(_connectionModel, _pbiOperation);
		_sprintProjectComboBoxModel = 
			new SprintProjectComboBoxModel(_sprintbacklogModelCommon, projectModel);
		_sprintDescriptionListModel =
			new SprintDescriptionListModel(_sprintbacklogModelCommon, projectModel);
		_taskTableModel = 
			new AllTaskTableModel(_connectionModel, _sprintbacklogModelCommon, _projectModel);
	}
	
	/**
	 * Set impediment table model
	 * @param value value to set
	 */
	public void setImpedimentTableModel(ImpedimentTableModel value)
	{
		_impedimentTableModel = value;
	}
	
	/**
	 * Insert into Sprint Backlog
	 * @param task_desc task description
	 * @param task_type type of task
	 * @param task_status task status
	 * @param task_date date when task was closed/divided/...
	 * @param task_active yes or no - is task valid?
	 * @param PBI_id product backlog item id
	 * @param Sprint_id sprint id
	 * @param hours_spent hours spent on task
	 * @param hours_remaining hours remaining to finish task
	 * @param nbopenimped number of open impediments for task
	 * @param nbclosedimped number of closed impediments for task
	 */
	public void add(String task_desc, int task_type, int task_status, String task_date, String task_active, int day, int pbi, int sprint, int employee, int hours_spent, int hours_remain, int nbopenimped, int nbclosedimped)
	{
		_sprintbacklogModelCommon.add(task_desc, task_type, task_status, task_date, task_active, day, pbi, sprint, employee, hours_spent, hours_remain, nbopenimped, nbclosedimped);
	}
	
	/**
	 * Add new sprint to current project
	 * 
	 * @param text sprint description
	 * @param teamId team id
	 * @param startDate sprint start date
	 * @param endDate sprint end date
	 * @param estimated estimated sprint end
	 * @param length sprint length
	 * @param estimated estimated sprint end
	 */
	public void addSprint(String description, int teamId, Date startDate, Date endDate, int length, Date estimated) {
		int projectId = _projectModel.getCurrentProjectId(); 
		if (_sprintbacklogModelCommon.addSprint(projectId, description, teamId, startDate, endDate, length, estimated))
		{
			_sprintDescriptionListModel.refresh();
			_sprintProjectComboBoxModel.refresh();
		}
	}
	
	/**
	 * Remove sprint from project
	 * 
	 * @param sprintId sprint id
	 */
	public void removeSprint(int sprintId)
	{
		int projectId = _projectModel.getCurrentProjectId();
		if (_sprintbacklogModelCommon.removeSprint(projectId, sprintId))
		{
			_sprintDescriptionListModel.refresh();
			_sprintProjectComboBoxModel.refresh();
		}
	}
	
	/**
	 * Sprint Backlog table model
	 * 
	 * @return sprint backlog table model
	 */
	public SprintBacklogTableModel getSprintBacklogTableModel()
	{
		return _sprintbacklogTableModel;
	}
	
	/**
	 * @return model that displays sprint id's on current project
	 */
	public SprintProjectComboBoxModel getSprintProjectComboBoxModel()
	{
		return _sprintProjectComboBoxModel;
	}
	
	/**
	 * @return fetch task table model
	 */
	public AllTaskTableModel getTaskTableModel()
	{
		return _taskTableModel;
	}
	
	/**
	 * Add sprint backlog data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addSprintBacklogListener(SprintBacklogListener listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove sprint backlog data change listener
	 * @param listener listener to remove
	 */
	public void removeSprintBacklogListener(SprintBacklogListener listener)
	{
		_operation.removeListener(listener);
	}
	
	/**
	 * Fetch SBI combo box model(contains all SBIs)
	 * @return model
	 */
	public SBIComboBoxModel getSBIComboBoxModel() 
	{
		return _sbiComboBoxModel;
	}
		
	public SprintDescriptionListModel getSprintDescriptionListModel() {
		return _sprintDescriptionListModel;
	}
	
	public void setTaskProp(int taskId, int pbi_id, String newSprint, int emp_id) {
		_sprintbacklogModelCommon.setTaskProp(taskId, pbi_id, newSprint, emp_id);	
	}
	
	public void setTaskMeasures(int id, int day, int sh, int rh, int oi, int ci) {
		_sprintbacklogModelCommon.setTaskMeasures(id, day , sh, rh, oi, ci);
	}
	
	public boolean existsTaskInSBI(int id) {
		return _sprintbacklogModelCommon.existsTaskInSBI(id);
	}
	
	/**
	 * @param sprintId sprint id
	 * @retur sprint description
	 */
	public String getSprintDescription(int sprintId) {
		int projectId = _projectModel.getCurrentProjectId();
		return _sprintbacklogModelCommon.getSprintDescription(projectId, sprintId);
	}
	
	/**
	 * @param sprintId sprint id
	 * @return team id
	 */
	public int getTeam(int sprintId)
	{
		int projectId = _projectModel.getCurrentProjectId();
		return _sprintbacklogModelCommon.getTeam(projectId, sprintId);
	}
	
	/**
	 * @param sprintId sprint id
	 * @return starting date
	 */
	public Date getBeginDate(int sprintId)
	{
		int projectId = _projectModel.getCurrentProjectId();
		return _sprintbacklogModelCommon.getBeginDate(projectId, sprintId); 
	}
	
	/**
	 * @param sprintId sprint id
	 * @return sprint end date
	 */
	public Date getEndDate(int sprintId)
	{
		int projectId = _projectModel.getCurrentProjectId();
		return _sprintbacklogModelCommon.getEndDate(projectId, sprintId);
	}
	
	/**
	 * @param sprintId sprint id
	 * @return sprint length
	 */
	public int getSprintLength(int sprintId)
	{
		int projectId = _projectModel.getCurrentProjectId();
		return _sprintbacklogModelCommon.getSprintLength(projectId, sprintId);
	}
	
	/**
	 * @param sprintId sprint id
	 * @return sprint estimated end date
	 */
	public Date getSprintEstimated(int sprintId)
	{
		int projectId = _projectModel.getCurrentProjectId();
		return _sprintbacklogModelCommon.getSprintEstimated(projectId, sprintId);
	}
	
	/**
	 * Update sprint information
	 * 
	 * @param sprintId sprint id
	 * @param teamId team id
	 * @param description sprint description
	 * @param startDate sprint starting date
	 * @param endDate sprint ending date
	 * @param estimated sprint estimated end date
	 * @param length sprint length
	 */
	public void updateSprint(int sprintId, int teamId, String description, Date startDate, Date endDate, Date estimated, int length) {
		int projectId = _projectModel.getCurrentProjectId();
		if (_sprintbacklogModelCommon.updateSprint(projectId, sprintId, teamId, description, startDate, endDate, estimated, length))
		{
			_sprintDescriptionListModel.refresh();
			_sprintProjectComboBoxModel.refresh();
		}
	}
	
	/**
	 * Set current sprint 
	 * 
	 * @param sprintId sprint id
	 */
	public void setCurrentSprint(int sprintId)
	{
		_currentSprint = sprintId;
		_taskTableModel.setSprintId(sprintId);
		_impedimentTableModel.setSprintId(sprintId);
	}
	
	/** 
	 * @return Currently active sprint 
	 */
	public int getCurrentSprint()
	{
		return _currentSprint;
	}
	
	/// currently selected sprint
	private int _currentSprint = -1;
	/// common sprint backlog related functionality
	private SprintBacklogModelCommon _sprintbacklogModelCommon;
	/// product backlog model 
	private ProductBacklogModelCommon _pbiModelCommon;
	private DeveloperModelCommon _devModelCommon;
	private TaskModelCommon _taskModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// project model
	private ProjectModel _projectModel;
	/// SBI combo box model
	private SBIComboBoxModel _sbiComboBoxModel;
	/// developer table model
	private SprintBacklogTableModel _sprintbacklogTableModel;
	/// model that displays sprint id's on only one project
	private SprintProjectComboBoxModel _sprintProjectComboBoxModel;
	/// sprint descriptiona list model
	private SprintDescriptionListModel _sprintDescriptionListModel;
	/// impediment table model
	private ImpedimentTableModel _impedimentTableModel;
	/// developer operation
	private Operations.SprintBacklogOperation _operation = new Operations.SprintBacklogOperation();
	/// developer operations
	private Operations.DeveloperOperation _devOperation = new Operations.DeveloperOperation();
	/// product backlog item operations
	private Operations.ProductBacklogOperation _pbiOperation = new Operations.ProductBacklogOperation();
	/// task table model
	private AllTaskTableModel _taskTableModel;
	private Operations.TaskOperation _taskoperation = new Operations.TaskOperation();
}
