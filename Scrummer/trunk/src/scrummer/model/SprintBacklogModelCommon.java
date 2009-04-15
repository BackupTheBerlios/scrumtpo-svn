package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * This model features common sprint backlog related functionality
 */
public class SprintBacklogModelCommon 
{	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public SprintBacklogModelCommon(ConnectionModel connectionModel, Operations.SprintBacklogOperation operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Insert into Sprint Backlog
	 * @param task_desc task description
	 * @param task_type task type
	 * @param task_status task status
	 * @param task_date task date
	 * @param task_active is task valid?
	 * @param pbi product backlog item id
	 * @param sprint sprint id
	 * @param employee employee id
	 * @param hours_spent hours spent
	 * @param hours_remain hours remaining
	 * @param nbopenimped number of open impediments
	 * @param nbclosedimped number of closed impediments
	 */
	public void add(String task_desc, int task_type, int task_status, String task_date, String task_active, int day, int pbi, int sprint, int employee, int hours_spent, int hours_remain, int nbopenimped, int nbclosedimped)
	{
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         
         try {
        	 conn = _connectionModel.getConnection();        	 
        	 String query2 = 
        		 "SELECT MAX(Task_id) AS 'maxid' FROM Task";
        	 st = conn.prepareStatement(query2);
        	 st.execute();
        	 int taskId = st.getResultSet().findColumn("maxid");
		 
        	 st = null;
        	 String query3 =
        		 "INSERT INTO " + DBSchemaModel.Sprint_PBITable + " " +
        		 "(" + DBSchemaModel.measureDay + "," + 
        		 DBSchemaModel.PBIId + "," +
        		 DBSchemaModel.TaskId + "," +
        		 DBSchemaModel.SprintId + "," +
        		 DBSchemaModel.EmployeeId + "," +
        		 DBSchemaModel.HoursSpent + "," +
        		 DBSchemaModel.HoursRemaining + "," +
        		 DBSchemaModel.NbOpenImped + "," +
        		 DBSchemaModel.NbClosedImped + ") " +
        		 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		 
        	 st = conn.prepareStatement(query3);
        	 st.setInt(1, day);
        	 st.setInt(2, pbi);
        	 st.setInt(3, taskId);
        	 st.setInt(4, sprint);
        	 st.setInt(5, employee);
        	 st.setInt(6, hours_spent);
        	 st.setInt(7, hours_remain);
        	 st.setInt(8, nbopenimped);
        	 st.setInt(9, nbclosedimped);
        	 st.execute();
        	 
        	 _operation.operationSucceeded(DataOperation.Insert, SprintBacklogOperation.SprintBacklog, "");
	
         } catch (SQLException e) 
         {
        	 _operation.operationFailed(DataOperation.Insert, SprintBacklogOperation.SprintBacklog, e.getMessage());
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
	 * Fetch SBIs and return full descriptions + ids
	 * 
	 * @return identified sbis
	 */
	public Vector<IdValue> fetchSBIsNames()
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
				"SELECT Task_id, Task_description " +
				"FROM Task");
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
	 * Fetch entire developer table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchSprintBacklogTable()
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
		q.queryResult("SELECT Measure_day, PBI_id, Sprint_id, Task.Task_id, Task_description, Task_type_id, Task_status_id, Task_date, Task_active, Task.Employee_id, Hours_spent, Hours_remaining, NbOpenImped, NbClosedImped FROM " + DBSchemaModel.Sprint_PBITable + " JOIN " + DBSchemaModel.TaskTable);
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
	 * Set data for given column on sprint backlog
	 * 
	 * @return true if data was set, false if error occurred
	 */
	public boolean setSprintBacklog(int pbiid, int taskid, int sprintid, int day, String column, String value)
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
	        	_operation.operationFailed(DataOperation.Update, SprintBacklogOperation.SprintBacklog, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		
		q.query("UPDATE " + DBSchemaModel.Sprint_PBITable + 
				" SET " + column + "='" + value + "' " +
				"WHERE " + DBSchemaModel.PBIId + "='" + pbiid + "' AND " + DBSchemaModel.TaskId + "='" + taskid + "' AND " + DBSchemaModel.SprintId + "='" + sprintid + "' AND" + DBSchemaModel.measureDay + "='" + day + "'");
		
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
	 * Remove from Sprint Backlog by Task id
	 * 
	 * @param id Task id
	 * @return true if developer was removed, false otherwise
	 */
	public boolean removeSprintBacklog(String id)
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
	        	_operation.operationFailed(DataOperation.Remove, SprintBacklogOperation.SprintBacklog, 
	        		i18n.tr("Could not remove Sprint Backlog item."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.Sprint_PBITable + 
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
	
	public void setSBIDay(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, SprintBacklogOperation.MeasureDay, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, SprintBacklogOperation.MeasureDay, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.Sprint_PBITable + " " +
			"SET " + DBSchemaModel.measureDay + "='" + name + "' " +
			"WHERE " + DBSchemaModel.TaskId + "='" + id + "'");
	}
	
	public void setSBIHoursSpent(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, SprintBacklogOperation.HoursSpent, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, SprintBacklogOperation.HoursSpent, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.Sprint_PBITable + " " +
			"SET " + DBSchemaModel.HoursSpent + "='" + name + "' " +
			"WHERE " + DBSchemaModel.TaskId + "='" + id + "'");
	}
	
	public void setSBIHoursRemain(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, SprintBacklogOperation.HoursRemaining, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, SprintBacklogOperation.HoursRemaining, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.Sprint_PBITable + " " +
			"SET " + DBSchemaModel.HoursRemaining + "='" + name + "' " +
			"WHERE " + DBSchemaModel.TaskId + "='" + id + "'");
	}
	
	public void setSBINbOpenImped(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, SprintBacklogOperation.NbOpenImped, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, SprintBacklogOperation.NbOpenImped, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.Sprint_PBITable + " " +
			"SET " + DBSchemaModel.NbOpenImped + "='" + name + "' " +
			"WHERE " + DBSchemaModel.TaskId + "='" + id + "'");
	}
	
	public void setSBINbClosedImped(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, SprintBacklogOperation.NbClosedImped, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, SprintBacklogOperation.NbClosedImped, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.Sprint_PBITable + " " +
			"SET " + DBSchemaModel.NbClosedImped + "='" + name + "' " +
			"WHERE " + DBSchemaModel.TaskId + "='" + id + "'");
	}
	
	public void setTaskProp(int taskId, int pbi_id, String newSprint, int emp_id) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Insert, SprintBacklogOperation.NbClosedImped, "");
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Insert, SprintBacklogOperation.NbClosedImped, ex.getMessage());
			}
				
			};
			q.query(
				"INSERT INTO " + DBSchemaModel.Sprint_PBITable + " " +
				"(Measure_day, PBI_id, Task_id, Sprint_id, Employee_id) VALUES(" +
				1 + ", " + pbi_id + ", " + taskId + ", " + newSprint + ", " + emp_id + ") ");
	}
	
	public void setTaskMeasures(int id, int day, int sh, int rh, int oi, int ci) 
	{
        java.sql.PreparedStatement st = null;
		String q1 = 
			"SELECT " + DBSchemaModel.EmployeeId + " AS empid, " + DBSchemaModel.SprintId + " AS sprintid, " + DBSchemaModel.PBIId +
			" AS pbiid FROM " + DBSchemaModel.Sprint_PBITable + " WHERE " + DBSchemaModel.TaskId + "=" + id;
   	 	
			try {
				st = _connectionModel.getConnection().prepareStatement(q1);
				st.execute();
		   	 	int emp_id = st.getResultSet().findColumn("empid");
		   	 	int sprint_id = st.getResultSet().findColumn("sprintid");
		   	 	int pbi_id = st.getResultSet().findColumn("pbiid");
		   	 		
				ResultQuery<Boolean> q2 = new ResultQuery<Boolean>(_connectionModel)
				{
					@Override
					public void process() {
						_operation.operationSucceeded(DataOperation.Insert, null, "");
					}
					@Override
					public void handleException(SQLException ex) {
						ex.printStackTrace();
						_operation.operationFailed(DataOperation.Insert, null, ex.getMessage());
					}
						
				};
				
				q2.query("INSERT INTO " + DBSchemaModel.Sprint_PBITable +
						" (" + DBSchemaModel.measureDay + "," + 
		       		 	DBSchemaModel.PBIId + "," +
		       		 	DBSchemaModel.TaskId + "," +
		       		 	DBSchemaModel.SprintId + "," +
		       		 	DBSchemaModel.EmployeeId + "," +
		       		 	DBSchemaModel.HoursSpent + "," +
		       		 	DBSchemaModel.HoursRemaining + "," +
		       		 	DBSchemaModel.NbOpenImped + "," +
		       		 	DBSchemaModel.NbClosedImped + ") " +
						"VALUES (" + day + ", " + pbi_id +
						", " + id + ", " + sprint_id +
						", " + emp_id + ", " + sh +
						", " + rh + ", " + oi + ", " + ci + ")" );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean existsTaskInSBI(int id) 
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Select, SprintBacklogOperation.Task, "");
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Select, SprintBacklogOperation.Task, ex.getMessage());
			}		
		};
		q.query("SELECT * FROM " + DBSchemaModel.Sprint_PBITable +
				" WHERE " + DBSchemaModel.TaskId + " = " + id);
		if(q.equals(null))
			return false;
		else
			return true;
	}
	
	/**
	 * Fetch all sprints on some project
	 * @param projectId project id
	 * @return all sprint
	 */
	public Vector<Integer> fetchSprints(int projectId)
	{
		ResultQuery<Vector<Integer>> q = new ResultQuery<Vector<Integer>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) throws SQLException {
				Vector<Integer> res = new Vector<Integer>();
				result.beforeFirst();
				while (result.next())
				{
					res.add(result.getInt(1));
				}
				setResult(res);
				_operation.operationFailed(DataOperation.Select, SprintBacklogOperation.Sprint, "");
			}

			@Override
			public void handleException(SQLException ex) {
				setResult(null);
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Select, SprintBacklogOperation.Sprint, ex.getMessage());
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.SprintId + " FROM " + DBSchemaModel.SprintTable + " " +
			"WHERE " + DBSchemaModel.SprintProjectId + "='" + projectId + "'");
		return q.getResult();
	}
	
	/**
	 * Fetch sprint descriptions
	 * 
	 * @param projectId project id
	 * @return id/description pairs
	 */
	public Vector<IdValue> fetchSprintDescriptions(int projectId) {
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) throws SQLException {
				setResult(IdValue.fetchValues(result));
				_operation.operationFailed(DataOperation.Select, SprintBacklogOperation.Sprint, "");
			}

			@Override
			public void handleException(SQLException ex) {
				setResult(null);
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Select, SprintBacklogOperation.Sprint, ex.getMessage());
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.SprintId + ", " + DBSchemaModel.SprintDescription + " FROM " + DBSchemaModel.SprintTable + " " +
			"WHERE " + DBSchemaModel.SprintProjectId + "='" + projectId + "'");
		return q.getResult();
	}
	
	/**
	 * Add sprint to project
	 * 
	 * @param projectId project id
	 * @param description sprint description
	 * @param teamId team id
	 * @param startDate starting date
	 * @param endDate ending date
	 * @param length sprint length
	 * @param estimated estimated sprint end
	 */
	public boolean addSprint(int projectId, String description, int teamId, Date startDate, Date endDate, int length, Date estimated) {
		
		boolean ret = false;
		java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
         
        try {
        	conn = _connectionModel.getConnection();        	         	

        	String query =
        		"INSERT INTO " + DBSchemaModel.SprintTable + " " +
        		"(" + DBSchemaModel.SprintProjectId + "," + 
        		 	  DBSchemaModel.TeamId + "," +
        		 	  DBSchemaModel.SprintDescription + "," +
        		 	  DBSchemaModel.SprintBegin + "," +
        		 	  DBSchemaModel.SprintEnd + "," +
        		 	  DBSchemaModel.SprintLength + "," +
        		 	  DBSchemaModel.SprintEstimated + ") " +
        		"VALUES (?, ?, ?, ?, ?, ?, ?)";
		 
        	st = conn.prepareStatement(query);
        	st.setInt(1, projectId);
        	st.setInt(2, teamId);
        	st.setString(3, description);
        	st.setDate(4, new java.sql.Date(startDate.getTime()));
        	st.setDate(5, new java.sql.Date(endDate.getTime()));
        	st.setInt(6, length);
        	st.setDate(7, new java.sql.Date(estimated.getTime()));
        	st.execute();
        	 
        	ret = true;
        	_operation.operationSucceeded(DataOperation.Insert, SprintBacklogOperation.Sprint, "");
	
        } catch (SQLException e) {
        	_operation.operationFailed(DataOperation.Insert, SprintBacklogOperation.Sprint, e.getMessage());
        	e.printStackTrace();
        } finally {
        	res  = _connectionModel.close(res);
        	st   = _connectionModel.close(st);
        	conn = _connectionModel.close(conn); 
        }
        return ret;
	}	
	
	/**
	 * Remove sprint from project
	 * 
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @return true if sprint removed, false otherwise
	 */
	public boolean removeSprint(int projectId, int sprintId)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{	
			@Override
			public void process() {
				setResult(true);
				_operation.operationSucceeded(DataOperation.Remove, SprintBacklogOperation.Sprint, "");
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, SprintBacklogOperation.Sprint, 
	        		i18n.tr("Could not remove Sprint Backlog item."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.SprintTable + " " + 
				"WHERE " + DBSchemaModel.SprintProjectId + "='" + projectId + "'" + " " +
				"AND " + DBSchemaModel.SprintId + "='" + sprintId + "'");
		return q.getResult();
	}
	
	/**
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @retur sprint description
	 */
	public String getSprintDescription(int projectId, int sprintId)
	{
		return getSprintCell(projectId, sprintId, DBSchemaModel.SprintDescription);
	}
	
	/**
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @return team id
	 */
	public int getTeam(int projectId, int sprintId)
	{
		return Integer.parseInt(getSprintCell(projectId, sprintId, DBSchemaModel.SprintTeamId));
	}
	
	/**
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @return starting date
	 */
	public Date getBeginDate(int projectId, int sprintId) {		
		java.sql.Date ret = getDateSprintCell(projectId, sprintId, DBSchemaModel.SprintBegin);
		return (ret != null) ? new Date(ret.getTime()) : null;
	}
	
	/**
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @return sprint end date
	 */
	public Date getEndDate(int projectId, int sprintId) {
		java.sql.Date ret = getDateSprintCell(projectId, sprintId, DBSchemaModel.SprintEnd);
		return (ret != null) ? new Date(ret.getTime()) : null;
	}
	
	/**
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @return sprint length
	 */
	public int getSprintLength(int projectId, int sprintId)
	{
		return Integer.parseInt(getSprintCell(projectId, sprintId, DBSchemaModel.SprintLength));
	}
	
	/**
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @return sprint estimated end date
	 */
	public Date getSprintEstimated(int projectId, int sprintId) {		
		java.sql.Date ret = getDateSprintCell(projectId, sprintId, DBSchemaModel.SprintEstimated);
		return (ret != null) ? new Date(ret.getTime()) : null;
	}
	
	/**
	 * Fetch sprint cell given a primary key and column
	 * 
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @param column column name
	 * @return cell
	 */
	public String getSprintCell(int projectId, int sprintId, String column)
	{
		ResultQuery<String> q = new ResultQuery<String>(_connectionModel)
		{				
			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next())
				{
					setResult(result.getString(1));
				}
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Select, SprintBacklogOperation.Sprint, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		
		q.queryResult(
			"SELECT " + column + " " + 
			"FROM " + DBSchemaModel.SprintTable + " " +
			"WHERE " + DBSchemaModel.SprintProjectId + "='" + projectId + "'" + " " +
			"AND " + DBSchemaModel.SprintId + "='" + sprintId + "'");
		
		return q.getResult();
	}
	
	/**
	 * Fetch sprint cell given a primary key and column
	 * 
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @param column column name
	 * @return cell
	 */
	public java.sql.Date getDateSprintCell(int projectId, int sprintId, String column)
	{
		ResultQuery<java.sql.Date> q = new ResultQuery<java.sql.Date>(_connectionModel)
		{				
			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next())
				{
					setResult(result.getDate(1));
				}
			}

			@Override
			public void handleException(SQLException ex) {
				setResult(null);
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Select, SprintBacklogOperation.Sprint, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		
		q.queryResult(
			"SELECT " + column + " " + 
			"FROM " + DBSchemaModel.SprintTable + " " +
			"WHERE " + DBSchemaModel.SprintProjectId + "='" + projectId + "'" + " " +
			"AND " + DBSchemaModel.SprintId + "='" + sprintId + "'");
		
		return q.getResult();
	}
	
	/**
	 * Update sprint information
	 * 
	 * @param projectId project
	 * @param sprintId sprint id
	 * @param teamId team id
	 * @param description sprint description
	 * @param startDate sprint starting date
	 * @param endDate sprint ending date
	 * @param estimated sprint estimated end date
	 * @param length sprint length
	 */
	public boolean updateSprint(int projectId, int sprintId, int teamId, String description, Date startDate, Date endDate, Date estimated, int length) {
	
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				setResult(true);
				_operation.operationSucceeded(DataOperation.Update, SprintBacklogOperation.Sprint, "");
			}

			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, SprintBacklogOperation.Sprint, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.SprintTable + " " +
			"SET " + DBSchemaModel.SprintDescription + "='" + description + "', " +
					 DBSchemaModel.SprintTeamId + "='" + teamId + "', " +
					 DBSchemaModel.SprintBegin + "='" + new java.sql.Date(startDate.getTime()) + "', " +
					 DBSchemaModel.SprintEnd + "='" + new java.sql.Date(endDate.getTime()) + "', " +
					 DBSchemaModel.SprintEstimated + "='" + new java.sql.Date(estimated.getTime()) + "', " +
					 DBSchemaModel.SprintLength + "='" + length + "' " +
			"WHERE " + DBSchemaModel.ProjectId + "='" + projectId + "'" + " AND " +
					   DBSchemaModel.SprintId + "='" + sprintId + "'");
		
		return q.getResult();
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operations.SprintBacklogOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
	
	
}

