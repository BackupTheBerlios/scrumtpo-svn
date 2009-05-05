package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * This model features common task related functionality
 */
public class TaskModelCommon 
{	
	/**
	 * Data row for updating data
	 */
	public static class Row extends DataRow 
	{
		/**
		 * Constructor
		 * 
		 * @param result result from which to get data
		 */
		public Row(ResultSet result) {
			try {
				result.beforeFirst(); result.next();
				
				TaskId = 
					result.getInt(1);
				EmployeeId = 
					result.getInt(2);
				PBIId = 
					result.getInt(3);
				TeamId = 
					result.getInt(4);
				TaskParentId = 
					result.getInt(5);
				TaskStatus = 
					result.getInt(6);
				TaskType =
					result.getInt(7);
				TaskDescription =
					result.getString(8);
				TaskDate =
					result.getDate(9);
				TaskActive = 
					result.getBoolean(10);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Does key equal 
		 * @param taskId
		 * @return
		 */
		public boolean keyEquals(int taskId)
		{
			return (TaskId == taskId);
		}
		
		public int TaskId, EmployeeId, PBIId, TeamId, TaskParentId, TaskStatus, TaskType;
		public String TaskDescription;
		public Date TaskDate;
		public boolean TaskActive;
	}
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public TaskModelCommon(ConnectionModel connectionModel, Operations.TaskOperation operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
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
	 * @return true if task added, false otherwise
	 */
	public boolean add(String description, Integer parent, Integer pbi, int employee, int team, int status, int type, Date date, boolean active)
	{
		boolean ret = false;
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.TaskTable +
			 	"(" + DBSchemaModel.TaskEmployeeId + ", " +
			 		  DBSchemaModel.TaskPBIId + ", " +
			 		  DBSchemaModel.TaskTeamId + ", " + 
			 		  DBSchemaModel.TaskParentId + ", " +
			 		  DBSchemaModel.TaskStatusId + ", " +
			 		  DBSchemaModel.TaskTypeId + ", " + 
			 		  DBSchemaModel.TaskDescription + ", " +
			 		  DBSchemaModel.TaskDate + ", " +
			 		  DBSchemaModel.TaskActive + ") " +
			 	"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, employee);
			 if (pbi == null) { st.setNull(2, java.sql.Types.INTEGER); } else { st.setInt(2, pbi); }
			 st.setInt(3, team);
			 if (parent == null) { st.setNull(4, java.sql.Types.INTEGER); } else { st.setInt(4, parent); }
			 st.setInt(5, status);
			 st.setInt(6, type);
			 st.setString(7, description);
			 st.setDate(8, new java.sql.Date(date.getTime()));
			 st.setBoolean(9, active);
			 st.execute();
			 
			 ret = true;
			 _operation.operationSucceeded(DataOperation.Insert, TaskOperation.Task, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, TaskOperation.Task, e.getMessage());
			e.printStackTrace();
		} finally {
			res  = _connectionModel.close(res);
			st   = _connectionModel.close(st);
			conn = _connectionModel.close(conn);
		}
		return ret;
	}
	
	/**
	 * Fetch task and return full descriptions + ids
	 * 
	 * @return identified task statuses
	 */
	public Vector<IdValue> fetchTaskNames()
	{
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) 
			{
				setResult(IdValue.fetchValues(result));
			}
			@Override
			public void handleException(SQLException ex) 
			{
				setResult(new Vector<IdValue>());
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.TaskId + ", " +
			DBSchemaModel.TaskDescription +
			" FROM "   + DBSchemaModel.TaskTable);
		return q.getResult();
	}
	
	/**
	 * Fetch entire task table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchTaskTable()
	{
		ResultQuery<Vector<ObjectRow>> q = new ResultQuery<Vector<ObjectRow>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) {
				setResult(ObjectRow.fetchRows(result)); 
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(new Vector<ObjectRow>());
				ex.printStackTrace();
			}
		};
		q.queryResult("SELECT Task_id, CONCAT(Employee_name, ' ', Employee_surname), Team_description " +
				"Task_status_description, Task_type_description, Task_date, Task_active FROM ((((" + 
				DBSchemaModel.TaskTable + ") JOIN " + DBSchemaModel.EmployeeTable + ") JOIN " + DBSchemaModel.TeamTable +
				") JOIN " + DBSchemaModel.TaskTypeTable + ") JOIN " + DBSchemaModel.TaskStatusTable + " WHERE " +
				"Task.Employee_id = Employee.Employee_id AND Task.Team_id = Team.Team_id AND " +
				"Task.Task_type_id = Task_type.Task_type_id AND Task.Task_status_id = Task_status.Task_status_id");
		return q.getResult();
	}
	
	public boolean removeTask(int taskId) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{	
			@Override
			public void process() {
				setResult(true);
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, TaskOperation.Task, 
	        		i18n.tr("Could not remove task."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.TaskTable + 
				" WHERE " + DBSchemaModel.TaskId + "='" + taskId + "'");
		return q.getResult();
	}
	
	/**
	 * Fetch task row
	 */
	public Row getRow(int taskId)
	{	
		ResultQuery<Row> q = new ResultQuery<Row>(_connectionModel)
		{	
			@Override
			public void processResult(ResultSet result) {
				setResult(new Row(result));
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(null);
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, TaskOperation.Task, 
	        		i18n.tr("Could not remove task."));
			}
		};
		q.queryResult(
			"SELECT * FROM " + DBSchemaModel.TaskTable + 
			" WHERE " + DBSchemaModel.TaskId + "=" + taskId);
		return q.getResult();
	}
	
	/**
	 * Fetch task names for given sprint and project
	 * 
	 * @param project project id
	 * @param sprint sprint id
	 * 
	 * @return task names
	 */
	public Vector<IdValue> fetchTaskNames(int project, int sprint) {
		
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) {
				setResult(IdValue.fetchValues(result));
			}
			@Override
			public void handleException(SQLException ex) 
			{
				setResult(new Vector<IdValue>());
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.TaskId + ", " +
						DBSchemaModel.TaskDescription +
			" FROM "   + DBSchemaModel.TaskTable + 
			" JOIN " +
			DBSchemaModel.PBITable + " ON " + 
			DBSchemaModel.PBITable + "." + DBSchemaModel.PBIId + "=" +
			DBSchemaModel.TaskTable + "." + DBSchemaModel.TaskPBIId + 
			" AND " +
			DBSchemaModel.PBIProject + "=" + project + 
			" AND " +
			DBSchemaModel.PBISprint + "=" + sprint);
		return q.getResult();
	}
	
	/**
	 * Update task
	 * 
	 * @param taskId task id
	 * @param description task description
	 * @param parentId parent
	 * @param pbiId pbi id
	 * @param employeeId employee
	 * @param teamId team 
	 * @param taskTypeId task type
	 * @param taskStatusId task status
	 * @param date task end date
	 * @param active is task active?
	 * 
	 * @return true if task update, false otherwise
	 */
	public boolean updateTask(
		int taskId,
		String description, int parentId, int pbiId, int employeeId, int teamId,
		int taskTypeId, int taskStatusId, Date date, boolean active) {

		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, TaskOperation.Task, "");
				setResult(true);
			}

			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, TaskOperation.Task, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + 
			DBSchemaModel.TaskTable + " " +
			"SET " + 
			DBSchemaModel.TaskDescription + "='" + description + "', " +
			DBSchemaModel.TaskPBIId + "='" + pbiId + "', " +
			DBSchemaModel.TaskParentId + "='" + parentId + "', " +
			DBSchemaModel.TaskEmployeeId + "='" + employeeId + "', " +
			DBSchemaModel.TaskTeamId + "='" + teamId + "', " +
			DBSchemaModel.TaskTypeId + "='" + taskTypeId + "', " +
			DBSchemaModel.TaskStatusId + "='" + taskStatusId + "', " +
			DBSchemaModel.TaskDate + "='" + new java.sql.Date(date.getTime()) + "', " +
			DBSchemaModel.TaskActive + "='" + (active ? 1 : 0) + "' " +
			"WHERE " + 
			DBSchemaModel.TaskId+ "='" + taskId + "'");
		
		return q.getResult();		
	}

	
	/// last gotten row
	private Row _lastRow = null;
	/// connection model
	private ConnectionModel _connectionModel;
	/// task data operation notifier
	private Operations.TaskOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());

}
