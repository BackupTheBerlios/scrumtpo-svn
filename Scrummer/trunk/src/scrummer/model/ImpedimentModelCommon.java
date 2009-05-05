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
	 * @param sprintId sprint id
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchImpedimentTable(int sprintId)
	{
		ResultQuery<Vector<ObjectRow>> q = new ResultQuery<Vector<ObjectRow>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) {
				Vector<ObjectRow> rows = ObjectRow.fetchRows(result); 
				for (int i = 0; i < rows.size(); i++)
				{
					ObjectRow current = rows.get(i);
					current.set(
						5, 
						DBSchemaModel.convertEnum(
							DBSchemaModel.ImpedimentTypeTable, 
							(Integer)current.get(5)));
					current.set(
						6, 
						DBSchemaModel.convertEnum(
							DBSchemaModel.ImpedimentStatusTable, 
							(Integer)current.get(6)));
				}					
				setResult(rows); 
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " +
			"Impediment_id, " +
			"Team_description, " +			
			"CONCAT(Employee_name, ' ', Employee_surname), " +
			"Task_description, " +
			"Impediment_description, " +
			DBSchemaModel.ImpedimentTable + "." + DBSchemaModel.ImpedimentTypeId + ", " +
			DBSchemaModel.ImpedimentTable + "." + DBSchemaModel.ImpedimentStatusId + ", " +
			"Impediment_start, " +
			"Impediment_end, " +
			"Impediment_age " +
			"FROM ((((((" + 
			DBSchemaModel.ImpedimentTable + " JOIN " + 
			DBSchemaModel.TeamTable +
			") JOIN " + 
			DBSchemaModel.EmployeeTable + ") JOIN " + 
			DBSchemaModel.SprintTable + ") JOIN " + 
			DBSchemaModel.TaskTable + ") JOIN " +
			DBSchemaModel.ImpedimentTypeTable + ") JOIN " +
			DBSchemaModel.ImpedimentStatusTable + ") " +
			" WHERE " +
			"Team.Team_id = Impediment.Team_id AND " +
			"Employee.Employee_id = Impediment.Employee_id AND " +
			"Sprint.Sprint_id = Impediment.Sprint_id AND " +
			"Task.Task_id = Impediment.Task_id AND " +
			"Sprint.Sprint_id=" + sprintId + " AND " +
			DBSchemaModel.ImpedimentTable + "." + DBSchemaModel.ImpedimentStatusId + "=" +
			DBSchemaModel.ImpedimentStatusTable + "." + DBSchemaModel.ImpedimentStatusId +
			" AND " +
			DBSchemaModel.ImpedimentTable + "." + DBSchemaModel.ImpedimentTypeId + "=" +
			DBSchemaModel.ImpedimentTypeTable + "." + DBSchemaModel.ImpedimentTypeId);
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
	 * Add impediment status type
	 * 
	 * @param description impediment status description
	 */
	public boolean addStatus(String description) {
		 boolean ret = false;
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.ImpedimentStatusTable + " " +
			 	"(" +DBSchemaModel.ImpedimentStatusDescription + ") " +
			 	"VALUES (?)";
			 st = conn.prepareStatement(query);
			 st.setString(1, description);			 
			 st.execute();
			 _operation.operationSucceeded(DataOperation.Insert, ImpedimentOperation.ImpedimentStatus, "");
			 ret = true;
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, ImpedimentOperation.ImpedimentStatus, e.getMessage());
			e.printStackTrace();
		} finally {
			res  = _connectionModel.close(res);
			st   = _connectionModel.close(st);
			conn = _connectionModel.close(conn);
		}
		return ret;
	}
	
	/**
	 * Add type
	 * @param description type name
	 */
	public boolean addType(String description) {
		boolean ret = false;
		java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.ImpedimentTypeTable + " " +
			 	"(" +DBSchemaModel.ImpedimentTypeDescription + ") " +
			 	"VALUES (?)";
			 st = conn.prepareStatement(query);
			 st.setString(1, description);			 
			 st.execute();
			 _operation.operationSucceeded(DataOperation.Insert, ImpedimentOperation.ImpedimentType, "");
			 ret = true;
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, ImpedimentOperation.ImpedimentType, e.getMessage());
			e.printStackTrace();
		} finally {
			res  = _connectionModel.close(res);
			st   = _connectionModel.close(st);
			conn = _connectionModel.close(conn);
		}		
		return ret;
	}
	
	/**
	 * Fetch all impediment types from db
	 * 
	 * @return project id's and names
	 */
	public Vector<IdValue> fetchTypes() {
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				Vector<IdValue> idvals = IdValue.fetchValues(result);
				for (int i = 0; i < idvals.size(); i++)
				{
					IdValue current = idvals.get(i);
					current.Value = DBSchemaModel.convertEnum(DBSchemaModel.ImpedimentTypeTable, current.Id);
				}
				setResult(idvals);
			}
			
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}					
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.ImpedimentTypeId + ", " +
						DBSchemaModel.ImpedimentTypeDescription + 
			" FROM " + DBSchemaModel.ImpedimentTypeTable);
		return q.getResult();
	}
	
	/**
	 * Fetch all impediment statuses from db
	 * 
	 * @return project id's and names
	 */
	public Vector<IdValue> fetchStatuses() {
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				Vector<IdValue> idvals = IdValue.fetchValues(result);
				for (int i = 0; i < idvals.size(); i++)
				{
					IdValue current = idvals.get(i);
					current.Value = DBSchemaModel.convertEnum(DBSchemaModel.ImpedimentStatusTable, current.Id);
				}
				setResult(idvals);
			}
			
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}					
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.ImpedimentStatusId + ", " +
						DBSchemaModel.ImpedimentStatusDescription + 
			" FROM " + DBSchemaModel.ImpedimentStatusTable);
		return q.getResult();
	}
		
	/**
	 * Change impediment status description
	 * @param statusId status id
	 * @param description description
	 */
	public boolean changeStatus(int statusId, String description) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {	
			@Override
			public void process() {
	            setResult(true);
	            _operation.operationSucceeded(
	            	DataOperation.Update, ImpedimentOperation.ImpedimentStatus, "");
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.ImpedimentStatus, 
		        		i18n.tr("Could not set parameter."));
				ex.printStackTrace();	        	
			}
		};
		
		q.query("UPDATE " + DBSchemaModel.ImpedimentStatusTable + 
				" SET " + DBSchemaModel.ImpedimentStatusDescription + "='" + description + "' " +
				"WHERE " + DBSchemaModel.ImpedimentStatusId + "='" + statusId + "'");
		return q.getResult();
	}
	
	/**
	 * Change impediment type description
	 * @param typeId type id
	 * @param description description
	 */
	public boolean changeType(int typeId, String description) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {	
			@Override
			public void process() {
	            setResult(true);
	            _operation.operationSucceeded(
	            	DataOperation.Update, ImpedimentOperation.ImpedimentType, "");
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				_operation.operationFailed(DataOperation.Update, ImpedimentOperation.ImpedimentType, 
		        		i18n.tr("Could not set parameter."));
				ex.printStackTrace();	        	
			}
		};
		q.query("UPDATE " + DBSchemaModel.ImpedimentTypeTable + 
				" SET " + DBSchemaModel.ImpedimentTypeDescription + "='" + description + "' " +
				"WHERE " + DBSchemaModel.ImpedimentTypeId + "='" + typeId + "'");
		return q.getResult();
	}
	
	/**
	 * Remove impediment status
	 * @param statusId id
	 */
	public boolean removeStatus(int statusId) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {	
			@Override
			public void process() {
				setResult(true);
				_operation.operationSucceeded(DataOperation.Remove, ImpedimentOperation.ImpedimentStatus, "");
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, ImpedimentOperation.ImpedimentStatus, 
	        		i18n.tr("Could not remove impediment."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.ImpedimentStatusTable + 
				" WHERE " + DBSchemaModel.ImpedimentStatusId + "='" + statusId + "'");
		return q.getResult();
	}
	
	/**
	 * Remove impediment type
	 * @param typeId type
	 */
	public boolean removeType(int typeId) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {	
			@Override
			public void process() {
				setResult(true);
				_operation.operationSucceeded(DataOperation.Remove, ImpedimentOperation.ImpedimentType, "");
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, ImpedimentOperation.ImpedimentType, 
	        		i18n.tr("Could not remove impediment."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.ImpedimentTypeTable + 
				" WHERE " + DBSchemaModel.ImpedimentTypeId + "='" + typeId + "'");
		return q.getResult();
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
	
	/**
	 * Update impediment
	 * 
	 * @param impediment impediment id
	 * @param sprint sprint id
	 * @param team team 
	 * @param employee employee id
	 * @param task related task
	 * @param description impediment description
	 * @param impedimentType impediment type
	 * @param impedimentStatus impediment status
	 * @param start starting date
	 * @param end ending date
	 * @param age impediment age
	 * @return true if impediment was changed, false otherwise
	 */
	public boolean update(int impediment, int sprint, int team, int employee, Integer task, String description, int impedimentType, int impedimentStatus, java.sql.Date start, java.sql.Date end, int age) {
	
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
	        	_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Impediment, 
	        		i18n.tr("Could not set parameters: ") + ex.getMessage());
			}
		};
		
		q.query("UPDATE " + DBSchemaModel.ImpedimentTable + 
				" SET " + 
				DBSchemaModel.ImpedimentTeam + "='" + team + "', " +
				DBSchemaModel.ImpedimentSprint + "='" + sprint + "', " +
				DBSchemaModel.ImpedimentEmployee + "='" + employee + "', " +
				DBSchemaModel.ImpedimentTask + "='" + task + "', " +
				DBSchemaModel.ImpedimentDesc + "='" + description + "', " +
				DBSchemaModel.ImpedimentTypeId + "='" + impedimentType + "', " +
				DBSchemaModel.ImpedimentStatusId + "='" + impedimentStatus + "', " +
				DBSchemaModel.ImpedimentStart + "='" + start + "', " +
				DBSchemaModel.ImpedimentEnd + "='" + end + "', " +
				DBSchemaModel.ImpedimentAge + "='" + age + "' " +	
				"WHERE " + DBSchemaModel.ImpedimentId + "='" + impediment + "'");
		
		return q.getResult();
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operations.ImpedimentOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
