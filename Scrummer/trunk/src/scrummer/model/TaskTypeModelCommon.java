package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskTypeOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * This model features common task type related functionality
 */
public class TaskTypeModelCommon 
{	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public TaskTypeModelCommon(ConnectionModel connectionModel, Operations.TaskTypeOperation operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Add task type
	 * @param description task type description */
	public void add(String description)
	{
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.TaskTypeTable +
			 	"(Task_type_description) " +
			 	"VALUES (?)";
			 st = conn.prepareStatement(query);
			 st.setString(1, description);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, TaskTypeOperation.TaskType, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, TaskTypeOperation.TaskType, e.getMessage());
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
	 * Fetch task types and return full descriptions + ids
	 * 
	 * @return identified task types
	 */
	public Vector<IdValue> fetchTaskTypeNames()
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
						String name = DBSchemaModel.convertEnum(DBSchemaModel.TaskTypeTable, id);
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
			"SELECT " + DBSchemaModel.TaskTypeId + ", " +
			DBSchemaModel.TaskTypeDesc +
			" FROM "   + DBSchemaModel.TaskTypeTable);
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
	 * Fetch entire task type table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchTaskTypeTable()
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
		q.queryResult("SELECT * FROM " + DBSchemaModel.TaskTypeTable);
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
	 * Remove task type by id
	 * 
	 * @param id task type id
	 * @return true if task type was removed, false otherwise
	 */
	public boolean removeTaskType(String id)
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
	        	_operation.operationFailed(DataOperation.Remove, TaskTypeOperation.TaskType, 
	        		i18n.tr("Could not remove task type."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.TaskTypeTable + 
				" WHERE " + DBSchemaModel.TaskTypeId + "='" + id + "'");
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
	 * Set task type description
	 * 
	 * @param id task type id
	 * @param name new task type description
	 */
	public boolean setTaskTypeDesc(String value, String id, String name)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, TaskTypeOperation.TaskType, "");
				setResult(true);
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, TaskTypeOperation.TaskType, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.TaskTypeTable + " " +
			"SET " + DBSchemaModel.TaskTypeDesc + "='" + value + "' " +
			"WHERE " + DBSchemaModel.TaskTypeId+ "='" + id + "'");
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
				_operation.operationSucceeded(DataOperation.Update, TaskTypeOperation.TaskType, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, TaskTypeOperation.TaskType, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.TaskTypeTable + " " +
			"SET " + DBSchemaModel.TaskTypeDesc + "='" + desc + "' " +
			"WHERE " + DBSchemaModel.TaskTypeId+ "='" + id + "'");
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// task type data operation notifier
	private Operations.TaskTypeOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
