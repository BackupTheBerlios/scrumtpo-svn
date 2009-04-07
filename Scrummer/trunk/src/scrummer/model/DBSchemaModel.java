package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import scrummer.util.Query;

/**
 * Database schema model can be used to get all database table structure data.
 * 
 * Currently this hold mostly just for column names.
 */
public class DBSchemaModel {

	/**
	 * Class designed to return all columns in a table
	 */
	private static class ColumnQuery extends Query
	{
		/**
		 * Constructor
		 * @param connectionModel connection model
		 */
		public ColumnQuery(ConnectionModel connectionModel, String table) {
			super(connectionModel);
			_table = table;
		}
		
		public Vector<String> getColumns()
		{
			return _columns;
		}	
		
		/**
		 * Fetch table name
		 * @return table
		 */
		protected String getTable()
		{
			return _table;
		}
		
		/**
		 * Add column
		 * @param value column name
		 */
		protected void addColumns(String value)
		{
			_columns.add(value);
		}
		
		/// column names
		private Vector<String> _columns = new Vector<String>();;
		/// table name
		private String _table;
	}
	
	/**
	 * Id and string value struct
	 */
	public static class IdValue 
	{
		/**
		 * Constructor
		 * @param id id
		 * @param name value
		 */
		public IdValue(int id, String value)
		{
			Id   = id;
			Value = value;
		}
		
		/**
		 * Fetch id and values pairs from result set
		 * @param result
		 * @return
		 */
		public static Vector<IdValue> fetchValues(ResultSet result)
		{
			Vector<IdValue> ret = new Vector<IdValue>();
			try {
				result.beforeFirst();
				while (result.next())
				{
					ret.add(new IdValue(result.getInt(1), result.getString(2)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ret = null;
			}
			
			return ret;
		}
		
		public int Id;
		public String Value;
	}
	
	/**
	 * Id and string value struct
	 */
	public static class IdsValue 
	{
		/**
		 * Constructor
		 * @param id1 id1
		 * @param id2 id2
		 * @param name value
		 */
		public IdsValue(int id1, int id2, String value)
		{
			Id1   = id1;
			Id2   = id2;
			Value = value;
		}
		
		/**
		 * Fetch id's and values pairs from result set
		 * @param result
		 * @return
		 */
		public static Vector<IdsValue> fetchValues(ResultSet result)
		{
			Vector<IdsValue> ret = new Vector<IdsValue>();
			try {
				result.beforeFirst();
				while (result.next())
				{
					ret.add(new IdsValue(result.getInt(1), result.getInt(2), result.getString(3)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ret = null;
			}
			
			return ret;
		}
		
		public int Id1, Id2;
		public String Value;
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public DBSchemaModel(ConnectionModel connectionModel) {
		_connectionModel = connectionModel;
	}
	
	/**
	 * Get all column names for given table
	 * 
	 * This uses lazy loading, tables are loaded once, then they don't change anymore.
	 * 
	 * @param table table name
	 * @return column names
	 */
	public Vector<String> getColumns(String table)
	{
		if (_tableColumnsMap.containsKey(table))
		{
			return _tableColumnsMap.get(table);
		}
		else
		{
			ColumnQuery q = new ColumnQuery(_connectionModel, table)
			{
				@Override
				public void processResult(ResultSet result) throws java.sql.SQLException {
		            int i = 0; result.beforeFirst();				
		            while (result.next())
		            {
		            	addColumns(result.getString(1));
		            	i++;
		            }
		            _tableColumnsMap.put(getTable(), getColumns());
				}
				@Override
				public void handleException(SQLException ex) {
					if (_tableColumnsMap.containsKey(getTable()))
					{
						_tableColumnsMap.remove(getTable());
					}
				}
			};
			q.queryResult("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='" + table + "'");
			
			if (_tableColumnsMap.containsKey(table))
			{
				return _tableColumnsMap.get(table);
			}
			else
			{
				return null;
			}
		}
	}

	/// mapping from table names to table column names
	private HashMap<String, Vector<String>> _tableColumnsMap = new HashMap<String, Vector<String>>();
	/// connection model
	private ConnectionModel _connectionModel;
	
	public static final String ProjectTable = "Project";
	public static final String ProjectId = "Project_id";
	public static final String ProjectName = "Project_name";
	public static final String ProjectDescription = "Project_description";
	
	public static final String EmployeeTable   = "Employee";
	public static final String EmployeeId	   = "Employee_id";
	public static final String EmployeeName    = "Employee_name";
	public static final String EmployeeSurname = "Employee_surname";
	public static final String EmployeeAddress = "Employee_address";
	
	public static final String TeamTable = "Team";
	public static final String TeamId	 = "Team_id";
	public static final String TeamName	 = "Team_description";
	
	public static final String TeamMemberTable      = "Team_member";
	public static final String TeamMemberEmployeeId = EmployeeId;
	public static final String TeamMemberTeamId		= TeamId;
	
	public static final String Sprint_PBITable = "Sprint_PBI";
	public static final String TaskTable = "Task";
	public static final String TaskStatusId = "Task_status_id";
	public static final String TaskTypeId = "Task_type_id";
	public static final String TaskDescription = "Task_description";
	public static final String TaskDate = "Task_date";
	public static final String TaskActive = "Task_active";
	public static final String TaskId = "Task_id";
	public static final String SprintId = "Sprint_id";
	public static final String HoursSpent = "Hours_spent";
	public static final String HoursRemaining = "Hours_remaining";
	public static final String NbOpenImped = "NbOpenImped";
	public static final String NbClosedImped = "NbClosedImped";
	public static final String measureDay = "Measure_day";
	
	public static final String PBITable = "PBI";
	public static final String PBIId = "PBI_id";
	public static final String PBIDesc = "PBI_description";
	public static final String PBIProject = "Project_id";
	public static final String PBISprint = "Sprint_id";
	public static final String PBIPriority = "PBI_priority";
	public static final String PBIIniEstimate = "PBI_initial_estimate";
	public static final String PBIAdjFactor = "PBI_adjustment_factor";
	public static final String PBIAdjEstimate = "PBI_adjusted_estimate";
	
	public static final String ImpedimentTable = "Impediment";
	public static final String ImpedimentId = "Impediment_id";
	public static final String ImpedimentDesc = "Impediment_description";
	public static final String ImpedimentTeam = "Team_id";
	public static final String ImpedimentSprint = "Sprint_id";
	public static final String ImpedimentEmployee = "Employee_id";
	public static final String ImpedimentTask = "Task_id";
	public static final String ImpedimentType = "Impediment_type";
	public static final String ImpedimentStatus = "Impediment_status";
	public static final String ImpedimentStart = "Impediment_start";
	public static final String ImpedimentEnd = "Impediment_end";
	public static final String ImpedimentAge = "Impediment_age";
}
