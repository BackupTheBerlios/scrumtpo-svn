package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskStatusOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * This model features common task status related functionality
 */
public class TaskStatusModelCommon 
{	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public TaskStatusModelCommon(ConnectionModel connectionModel, Operations.TaskStatusOperation operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Add task status
	 * @param description task status description */
	public void add(String description)
	{
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.TaskStatusTable +
			 	"(Task_status_description) " +
			 	"VALUES (?)";
			 st = conn.prepareStatement(query);
			 st.setString(1, description);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, TaskStatusOperation.TaskStatus, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, TaskStatusOperation.TaskStatus, e.getMessage());
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
	 * Fetch task statuses and return full descriptions + ids
	 * 
	 * @return identified task statuses
	 */
	public Vector<IdValue> fetchTaskStatusNames()
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
						int id = result.getInt(1);
						String name = DBSchemaModel.convertEnum(DBSchemaModel.TaskStatusTable, id);
						res.add(new IdValue(id, (name == null) ? result.getString(2) : name));
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
			"SELECT " + DBSchemaModel.TaskStatusId + ", " +
			DBSchemaModel.TaskStatusDesc +
			" FROM "   + DBSchemaModel.TaskStatusTable);
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
	 * Fetch entire task status table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchTaskStatusTable()
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
		q.queryResult("SELECT * FROM " + DBSchemaModel.TaskStatusTable);
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
	 * Remove task status by id
	 * 
	 * @param id task status id
	 * @return true if task status was removed, false otherwise
	 */
	public boolean removeTaskStatus(String id)
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
	        	_operation.operationFailed(DataOperation.Remove, TaskStatusOperation.TaskStatus, 
	        		i18n.tr("Could not remove task status."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.TaskStatusTable + 
				" WHERE " + DBSchemaModel.TaskStatusId + "='" + id + "'");
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
	 * Set absence type description
	 * 
	 * @param id task status id
	 * @param name new task status description
	 */
	public boolean setTaskStatusDesc(String value, String id, String name)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, TaskStatusOperation.TaskStatus, "");
				setResult(true);
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, TaskStatusOperation.TaskStatus, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.TaskStatusTable + " " +
			"SET " + DBSchemaModel.TaskStatusDesc + "='" + value + "' " +
			"WHERE " + DBSchemaModel.TaskStatusId+ "='" + id + "'");
		if (q.getResult() == null)
		{
			return false;
		}
		else
		{
			return q.getResult();
		}
	}
	
	public void setNewDesc(int id, String desc) 
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, TaskStatusOperation.TaskStatus, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, TaskStatusOperation.TaskStatus, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.TaskStatusTable + " " +
			"SET " + DBSchemaModel.TaskStatusDesc + "='" + desc + "' " +
			"WHERE " + DBSchemaModel.TaskStatusId+ "='" + id + "'");
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// task status data operation notifier
	private Operations.TaskStatusOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
