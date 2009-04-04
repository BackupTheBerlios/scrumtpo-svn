package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;
import scrummer.util.Query;
import scrummer.util.ResultQuery;

/**
 * This model features common sprint backlog related functionality
 */
public class SprintBacklogModelCommon 
{
	/**
	 * Id and name struct
	 */
	public static class IdName 
	{
		/**
		 * Constructor
		 * @param id id
		 * @param name name
		 */
		public IdName(int id, String name)
		{
			Id   = id;
			Name = name;
		}
		
		public int Id;
		public String Name;
	}
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public SprintBacklogModelCommon(ConnectionModel connectionModel, Operation<SprintBacklogOperation> operation)
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
        	 String query1 = 
        		 "INSERT INTO " + DBSchemaModel.TaskTable + " " +
        		 "(" + DBSchemaModel.EmployeeId + "," + 
        		 DBSchemaModel.TaskStatusId + "," + 
        		 DBSchemaModel.TaskTypeId + "," +
        		 DBSchemaModel.TaskDescription + "," +
        		 DBSchemaModel.TaskDate + "," +
        		 DBSchemaModel.TaskActive + ") " +
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
        		 "SELECT MAX(Task_id) AS 'maxid' FROM Task";
        	 st = conn.prepareStatement(query2);
        	 st.execute();
        	 int task_id = st.getResultSet().findColumn("maxid");
		 
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
        	 st.setInt(3, task_id);
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
		q.queryResult("SELECT Task_id, Task_type, Task_description, Task_status, Task_date, Task_active, Employee_id, Sprint_id, PBI_id, Measure_day, Hours_spent, Hours_remaining, NbOpenImped, NbClosedImped FROM " + DBSchemaModel.Sprint_PBITable + " JOIN " + DBSchemaModel.TaskTable);
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
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operation<SprintBacklogOperation> _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
