package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	 */
	public void add(int emp, int team, int status, int type, String desc, java.sql.Date date, String active)
	{
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.TaskTable +
			 	"(Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_date, Task_active) " +
			 	"VALUES (?, ?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, emp);
			 st.setInt(2, team);
			 st.setInt(3, status);
			 st.setInt(4, type);
			 st.setString(5, desc);
			 st.setDate(6, date);
			 st.setString(7, active);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, TaskOperation.Task, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, TaskOperation.Task, e.getMessage());
			e.printStackTrace();
		}
		finally
		{
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
		
	/// connection model
	private ConnectionModel _connectionModel;
	/// task data operation notifier
	private Operations.TaskOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
