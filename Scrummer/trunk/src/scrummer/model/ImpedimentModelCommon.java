package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * This model features common impediment related functionality
 */
public class ImpedimentModelCommon 
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
				
				ImpedimentId = 
					result.getInt(1);
				TeamId = 
					result.getInt(2);
				SprintId = 
					result.getInt(3);
				EmployeeId = 
					result.getInt(4);
				TaskId = 
					result.getInt(5);
				ImpedimentDescription = 
					result.getString(6);
				ImpedimentType =
					result.getInt(7);
				ImpedimentStatus =
					result.getInt(8);
				ImpedimentStart =
					result.getDate(9);
				ImpedimentEnd = 
					result.getDate(10);
				ImpedimentAge = 
					result.getInt(11);
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
		
		public int ImpedimentId, TeamId, SprintId, EmployeeId, TaskId, ImpedimentType, ImpedimentStatus, ImpedimentAge;
		public String ImpedimentDescription;
		public Date ImpedimentStart, ImpedimentEnd;
	}
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public ImpedimentModelCommon(ConnectionModel connectionModel, Operations.ImpedimentOperation operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Add impediment
	 * @param team employee's team
	 * @param sprint sprint when impediment occurred
	 * @param employee employee who experienced impediment
	 * @param task task which has to do with impediment
	 * @param desc description of impediment
	 * @param type impediment type
	 * @param status impediment status
	 * @param start date when impediment started
	 * @param end date when impediment was resolved
	 * @param age number of days when impediment was active
	 */
	public void add(int team, int sprint, int employee, int task, String desc, String type, String status, String start, String end, int age)
	{
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO Impediment " +
			 	"(Team_id, Sprint_id, Employee_id, Task_id, Impediment_description, Impediment_type, Impediment_status, Impediment_start, Impediment_end, Impediment_age) " +
			 	"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, team);
			 st.setInt(2,sprint);
			 st.setInt(3,employee);
			 st.setInt(4,task);
			 st.setString(5, desc);
			 st.setString(6, type);
			 st.setString(7, status);
			 st.setString(8, start);
			 st.setString(9, end);
			 st.setInt(10, age);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, ImpedimentOperation.Impediment, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, ImpedimentOperation.Impediment, e.getMessage());
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
	 * Fetch impediments and return full descriptions + ids
	 * 
	 * @return identified imps
	 */
	public Vector<IdValue> fetchImpedimentNames()
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
			"SELECT " + DBSchemaModel.ImpedimentId + ", " +
			DBSchemaModel.ImpedimentDesc +
			" FROM "   + DBSchemaModel.ImpedimentTable);
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
	 * Fetch entire impediment table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchImpedimentTable()
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
		q.queryResult("SELECT * FROM " + DBSchemaModel.ImpedimentTable);
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
	 * Set data for given column on impediment
	 * 
	 * @return true if impediment was set, false if error occurred
	 */
	public boolean setImpediment(String id, String column, String value)
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
	        	_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Impediment, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		
		q.query("UPDATE " + DBSchemaModel.ImpedimentTable + 
				" SET " + column + "='" + value + "' " +
				"WHERE " + DBSchemaModel.EmployeeId + "='" + id + "'");
		
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
	 * Remove impediment by id
	 * 
	 * @param id impediment id
	 * @return true if impediment was removed, false otherwise
	 */
	public boolean removeImpediment(int id)
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
	        	_operation.operationFailed(DataOperation.Remove, ImpedimentOperation.Impediment, 
	        		i18n.tr("Could not remove impediment."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.ImpedimentTable + 
				" WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
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
	 * Set impediment team
	 * 
	 * @param id impediment id
	 * @param name new impediment team
	 */
	public void setImpedimentTeam(int id, String name)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentTeam + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment sprint
	 * 
	 * @param id impediment id
	 * @param name new impediment sprint
	 */
	public void setImpedimentSprint(int id, String name) 
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentSprint + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment employee
	 * 
	 * @param id impediment id
	 * @param name new impediment employee
	 */
	public void setImpedimentEmployee(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentEmployee + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment task
	 * 
	 * @param id impediment id
	 * @param name new impediment task
	 */
	public void setImpedimentTask(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentTask + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment description
	 * 
	 * @param id impediment id
	 * @param name new impediment description
	 */
	public void setImpedimentDesc(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentDesc + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment type
	 * 
	 * @param id impediment id
	 * @param name new impediment type
	 */
	public void setImpedimentType(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentType + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment status
	 * 
	 * @param id impediment id
	 * @param name new impediment status
	 */
	public void setImpedimentStatus(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentStatus + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment start
	 * 
	 * @param id impediment id
	 * @param name new impediment start
	 */
	public void setImpedimentStart(int id, java.sql.Date name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentStart + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment end
	 * 
	 * @param id impediment id
	 * @param name new impediment end
	 */
	public void setImpedimentEnd(int id, java.sql.Date name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentEnd + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Set impediment age
	 * 
	 * @param id impediment id
	 * @param name new impediment age
	 */
	public void setImpedimentAge(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ImpedimentOperation.Team, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Team, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.ImpedimentTable + " " +
			"SET " + DBSchemaModel.ImpedimentAge + "='" + name + "' " +
			"WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
	}
	
	/**
	 * Fetch impediment row
	 */
	public Row getRow(int impId)
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
	        	_operation.operationFailed(DataOperation.Remove, ImpedimentOperation.Impediment, 
	        		i18n.tr("Could not remove impediment."));
			}
		};
		q.queryResult(
			"SELECT * FROM " + DBSchemaModel.ImpedimentTable + 
			" WHERE " + DBSchemaModel.ImpedimentId + "=" + impId);
		return q.getResult();
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operations.ImpedimentOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
