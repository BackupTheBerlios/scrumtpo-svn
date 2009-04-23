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
		public Row(ResultSet result)
		{
			try {
				result.beforeFirst();
				
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
		
		public int TaskId;
		public int EmployeeId;
		public int PBIId;
		public int TeamId;
		public int TaskParentId;
		public int TaskStatus;
		public int TaskType;
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
	 */
	public void add(String description, Integer parent, Integer pbi, int employee, int team, int status, int type, Date date, boolean active)
	{
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
			 
			 _operation.operationSucceeded(DataOperation.Insert, TaskOperation.Task, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, TaskOperation.Task, e.getMessage());
			e.printStackTrace();
		} finally {
			res  = _connectionModel.close(res);
			st   = _connectionModel.close(st);
			conn = _connectionModel.close(conn);
		}		
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
				Vector<IdValue> res = new Vector<IdValue>();
				try {
					result.beforeFirst();
					while (result.next())
					{
						res.add(new IdValue(result.getInt(1), result.getString(2)));
					}
					setResult(res);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void handleException(SQLException ex) 
			{
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.TaskId + ", " +
			DBSchemaModel.TaskDescription +
			" FROM "   + DBSchemaModel.TaskTable);
		if (q.getResult() == null)
		{
			return new Vector<IdValue>();
		}
		else
		{
			return q.getResult();
		}
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
				ex.printStackTrace();
			}
		};
		q.queryResult("SELECT * FROM " + DBSchemaModel.TaskTable);
		if (q.getResult() == null)
		{
			return new Vector<ObjectRow>();
		}
		else
		{
			return q.getResult();
		}
	}
	
	/**
	 * Remove task by id
	 * 
	 * @param id task id
	 * @return true if task was removed, false otherwise
	 */
	public boolean removeTask(String id)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{	
			@Override
			public void process() {
				setResult(true);
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, TaskOperation.Task, 
	        		i18n.tr("Could not remove task."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.TaskTable + 
				" WHERE " + DBSchemaModel.TaskId + "='" + id + "'");
		if (q.getResult() == null)
		{
			return false;
		}
		else
		{
			return q.getResult();
		}
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
		q.queryResult("SELECT * FROM " + DBSchemaModel.TaskTable);
		
		if (q.getResult() == null)
		{
			return null;
		}
		else
		{
			return q.getResult();
		}
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
			public void processResult(ResultSet result) 
			{
				Vector<IdValue> res = new Vector<IdValue>();
				try {
					result.beforeFirst();
					while (result.next())
					{
						res.add(new IdValue(result.getInt(1), result.getString(2)));
					}
					setResult(res);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void handleException(SQLException ex) 
			{
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
			DBSchemaModel.TaskTable + "." + DBSchemaModel.TaskId + 
			" AND " +
			DBSchemaModel.PBIProject + "=" + project + 
			" AND " +
			DBSchemaModel.PBISprint + "=" + sprint);
		if (q.getResult() == null)
		{
			return new Vector<IdValue>();
		}
		else
		{
			return q.getResult();
		}
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
