package scrummer.model;

import scrummer.listener.SprintBacklogListener;
import scrummer.model.swing.EmployeeComboBoxModel;
import scrummer.model.swing.PBIComboBoxModel;
import scrummer.model.swing.SBIComboBoxModel;
import scrummer.model.swing.SprintBacklogTableModel;
import scrummer.model.swing.TaskComboBoxModel;
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
	public SprintBacklogModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_sprintbacklogModelCommon = new SprintBacklogModelCommon(_connectionModel, _operation);
		_sprintbacklogTableModel = new SprintBacklogTableModel(connectionModel, _sprintbacklogModelCommon);
		_sbiComboBoxModel = new SBIComboBoxModel(_sprintbacklogModelCommon);
		_taskComboBoxModel = new TaskComboBoxModel(_sprintbacklogModelCommon);
		
		_devModelCommon = new DeveloperModelCommon(_connectionModel, _devOperation);
		
		_pbiModelCommon = new ProductBacklogModelCommon(_connectionModel, _pbiOperation);
		
		_empComboBoxModel = new EmployeeComboBoxModel(_devModelCommon);
		_pbiComboBoxModel = new PBIComboBoxModel(_pbiModelCommon);
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
		/*java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query1 =
				"INSERT INTO Task " +
			 	"(Employee_id, Task_status_id, Task_type_id, Task_description, Task_date, Task_active) " +
			 	"VALUES (?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query1);
			 st.setInt(1, employee);
			 st.setInt(2, task_status);
			 st.setInt(3, task_type);
			 st.setString(4, task_desc);
			 st.setString(5, task_date);
			 st.setString(6, task_active);
			 st.execute();
			 
			 st = null;
			 String query2 = 
				 "SELECT MAX(Task_id) FROM Task";
			 st = conn.prepareStatement(query2);
			 int task_id = Integer.parseInt(st.getResultSet().toString());
			 
			 st = null;
			 String query3 =
				 "INSERT INTO Sprint_PBI " +
				 "(PBI_id, Task_id, Sprint_id, Employee_id, Hours_spent, Hours_remaining, NbOpenImped, NbClosedImped)" +
				 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query3);
			 st.setInt(1, pbi);
			 st.setInt(2, task_id);
			 st.setInt(3, sprint);
			 st.setInt(4, employee);
			 st.setInt(5, hours_spent);
			 st.setInt(6, hours_remain);
			 st.setInt(7, nbopenimped);
			 st.setInt(8, nbclosedimped);
			 
			 _sprintbacklogOp.operationSucceeded(DataOperation.Insert, SprintBacklogOperation.SprintBacklog, "");
		} catch (SQLException e) {
			_sprintbacklogOp.operationFailed(DataOperation.Insert, SprintBacklogOperation.SprintBacklog, e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			res  = _connectionModel.close(res);
			st   = _connectionModel.close(st);
			conn = _connectionModel.close(conn);
		}*/
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
	
	public TaskComboBoxModel getTaskComboBoxModel() {
		return _taskComboBoxModel;
	}
	
	public EmployeeComboBoxModel getEmpComboBoxModel() {
		return _empComboBoxModel;
	}
	
	public PBIComboBoxModel getPbiComboBoxModel() {
		return _pbiComboBoxModel;
	}
	
	public void setTaskProp(int taskId, int pbi_id, String newSprint, int emp_id) {
		_sprintbacklogModelCommon.setTaskProp(taskId, pbi_id, newSprint, emp_id);	
	}
	
	public void setTaskMeasures(int id, int day, int sh, int rh, int oi, int ci) {
		_sprintbacklogModelCommon.setTaskMeasures(id, day , sh, rh, oi, ci);
	}
	
	/// common sprint backlog related functionality
	private SprintBacklogModelCommon _sprintbacklogModelCommon;
	private ProductBacklogModelCommon _pbiModelCommon;
	private DeveloperModelCommon _devModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// SBI combo box model
	private SBIComboBoxModel _sbiComboBoxModel;
	/// task combo box model
	private TaskComboBoxModel _taskComboBoxModel;
	/// pbi combo box model
	private PBIComboBoxModel _pbiComboBoxModel;
	/// employee combo box model
	private EmployeeComboBoxModel _empComboBoxModel;
	/// developer table model
	private SprintBacklogTableModel _sprintbacklogTableModel;
	/// developer operation
	private Operations.SprintBacklogOperation _operation = new Operations.SprintBacklogOperation();
	private Operations.DeveloperOperation _devOperation = new Operations.DeveloperOperation();
	private Operations.ProductBacklogOperation _pbiOperation = new Operations.ProductBacklogOperation();
}
