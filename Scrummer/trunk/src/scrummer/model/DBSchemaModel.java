package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import scrummer.util.Query;
import scrummer.util.ResultQuery;

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
		
		/**
		 * Shorthand for getting id/value pairs from some table
		 * @param table table name 
		 * @param idColumn id column name
		 * @param valueColumn value column name
		 * @param model connection model
		 * @return list of id, value pairs
		 */
		public static Vector<IdValue> fetchValues(String table, String idColumn, String valueColumn, ConnectionModel model) {
			ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(model) {	
				@Override
				public void processResult(ResultSet result) throws SQLException {
					setResult(IdValue.fetchValues(result));
				}
				@Override
				public void handleException(SQLException ex) {
					ex.printStackTrace();
				}					
			};
			q.queryResult(
				"SELECT " + idColumn + ", " +
							valueColumn + 
				" FROM " + table);
			return q.getResult();
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
			q.queryResult("SHOW COLUMNS FROM " + table);
			 
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
	
	/**
	 * Set translation object
	 * @param value vlaue to set
	 */
	public static void setI18n(org.xnap.commons.i18n.I18n value)
	{
		i18n = value;
	}
	
	/// mapping from table names to table column names
	private HashMap<String, Vector<String>> _tableColumnsMap = new HashMap<String, Vector<String>>();
	/// connection model
	private ConnectionModel _connectionModel;
	/// translation class field
	private static org.xnap.commons.i18n.I18n i18n;
	
	/**
	 * Convert enum to it's string representation(includes translations)
	 * @param tableName enum table name
	 * @param id enum id
	 */
	public static String convertEnum(String tableName, int id)
	{
		if (tableName == TaskTypeTable)
		{
			switch (id)
			{
			case 1: return i18n.tr("analysis");
			case 2: return i18n.tr("design");
			case 3: return i18n.tr("coding");
			case 4: return i18n.tr("testing");
			case 5: return i18n.tr("documentation");
			case 6: return i18n.tr("rework due to error reported by the customer");
			case 7: return i18n.tr("rework due to change in requirements");			
			case 8: return i18n.tr("rework due to inaccurat specifications");
			case 9: return i18n.tr("rework due to incomplete impact assessment");
			case 10: return i18n.tr("rework due to inadequate change specifications");
			case 11: return i18n.tr("rework due to inadequate testing");
			case 12: return i18n.tr("other");
			}
			return null;
		}
		else if (tableName == TaskStatusTable)
		{
			switch (id)
			{
			case 1: return i18n.tr("not started");
			case 2: return i18n.tr("in progress");
			case 3: return i18n.tr("completed");
			case 4: return i18n.tr("omitted");
			case 5: return i18n.tr("moved into next sprint");
			case 6: return i18n.tr("split/divided");
			case 7: return i18n.tr("not completed due to incorrect feasibility assumptions");			
			case 8: return i18n.tr("other");
			}
			return null;
		}
		else if (tableName.equals(DBSchemaModel.ImpedimentTypeTable))
		{
			switch (id)
			{
			case 1: return i18n.tr("Specification problems");
			case 2: return i18n.tr("Hardware problems");
			case 3: return i18n.tr("Software problems");
			case 4: return i18n.tr("Security problems");
			case 5: return i18n.tr("Teamwork problems");
			case 6: return i18n.tr("Other");
			}
			return null;
		}
		else if (tableName.equals(DBSchemaModel.ImpedimentStatusTable))
		{
			switch (id)
			{
			case 1: return i18n.tr("Open");
			case 2: return i18n.tr("Pending");
			case 3: return i18n.tr("In Progress");
			case 4: return i18n.tr("Closed");
			case 5: return i18n.tr("Other");
			}
			return null;
		}
		else
		{
			return null; 
		}
	}
	
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
	
	public static final String SprintTable = "Sprint";
	public static final String SprintId = "Sprint_id";
	public static final String SprintProjectId = "Project_id";
	public static final String SprintTeamId = "Team_id";
	public static final String SprintDescription = "Sprint_description";
	public static final String SprintBegin = "Sprint_begin_date";
	public static final String SprintEnd = "Sprint_end_date";
	public static final String SprintLength = "Sprint_length";
	public static final String SprintEstimated = "Sprint_estimated_date";
	
	public static final String TaskTable = "Task";
	public static final String TaskStatusId = "Task_status_id";
	public static final String TaskTypeId = "Task_type_id";
	public static final String TaskDescription = "Task_description";
	public static final String TaskEngineeringHour = "Task_engineering_hour";
	public static final String TaskDate = "Task_date";
	public static final String TaskActive = "Task_active";
	public static final String TaskId = "Task_id";
	public static final String TaskParentId = "Task_parent_id";
	public static final String TaskPBIId = "PBI_id";
	public static final String TaskEmployeeId = EmployeeId;
	public static final String TaskTeamId = TeamId;
	
	public static final String HoursSpent = "Hours_spent";
	public static final String HoursRemaining = "Hours_remaining";
	public static final String NbOpenImped = "NbOpenImped";
	public static final String NbClosedImped = "NbClosedImped";
	public static final String MeasureDay = "Measure_day";
	
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
	public static final String ImpedimentType = "Impediment_type_id";
	public static final String ImpedimentStatus = "Impediment_status_id";
	public static final String ImpedimentStart = "Impediment_start";
	public static final String ImpedimentEnd = "Impediment_end";
	public static final String ImpedimentAge = "Impediment_age";
	
	public static final String ImpedimentStatusTable = "Impediment_status";
	public static final String ImpedimentStatusId = "Impediment_status_id";
	public static final String ImpedimentStatusDescription = "Impediment_status_description";
		
	public static final String ImpedimentTypeTable = "Impediment_type";
	public static final String ImpedimentTypeId = "Impediment_type_id";
	public static final String ImpedimentTypeDescription = "Impediment_type_description";
	
	public static final String SprintPBITable = "Sprint_PBI";
	public static final String SprintPBIMeasureDay = "Measure_day";
	public static final String SprintPBIPBIId = PBIId;
	public static final String SprintPBITaskId = TaskId;
	public static final String SprintPBISprintId = SprintId;
	public static final String SprintPBIEmployeeId = EmployeeId;
	public static final String SprintPBIHourseSpent = "Hours_spent";
	public static final String SprintPBIHoursRemaining = "Hours_remaining";
	public static final String SprintPBINbOpenImped	= "NbOpenImped";
	public static final String SprintPBINbClosedImped = "NbClosedImped";
	
	public static final String AdminDaysTable = "Administrative_days";
	public static final String HoursNotWorked = "Hours_not_worked";
	
	public static final String AbsenceTypeTable = "Absence_type";
	public static final String AbsenceTypeId = "Absence_type_id";
	public static final String AbsenceTypeDesc = "Absence_type_description";
	
	public static final String TaskStatusTable = "Task_status";
	public static final String TaskStatusDesc = "Task_status_description";
	
	public static final String TaskTypeTable = "Task_type";
	public static final String TaskTypeDesc = "Task_type_description";	
	
	public static final String MeasureTable = "Measure";
	public static final String MeasureId = "Measure_id";
	public static final String MeasureName = "Measure_name";
	public static final String MeasureDescription = "Measure_description";
	
	public static final String FinalReleaseTable = "FinalRelease";
	public static final String ReleaseId = "Release_id";
	public static final String ReleaseDescription = "Release_description";
	
	public static final String ReleasePBITable = "Release_PBI";
	
	public static final String TaskMeasurementResultTable = "Task_measurement_result";
	public static final String TaskMeasurementResultId = "Measure_id";
	public static final String TaskMeasurementTaskId = "Task_id";
	public static final String TaskMeasurementResultResult = "Measurement_result";
	public static final String TaskMeasurementResultDatum = "Datum";
	
	public static final String SprintMeasurementResultTable = "Sprint_measurement_result";
	public static final String SprintMeasurementResultId = "Measure_id";
	public static final String SprintMeasurementSprintId = "Sprint_id";
	public static final String SprintMeasurementResultResult = "Measurement_result";
	public static final String SprintMeasurementResultDate = "Datum";
	
	public static final String ReleaseMeasurementResultTable = "Release_measurement_result";
	public static final String ReleaseMeasurementResultId = "Measure_id";
	public static final String ReleaseMeasurementReleaseId = "Release_id";
	public static final String ReleaseMeasurementResultResult = "Measurement_result";
	public static final String ReleaseMeasurementResultDate = "Datum";
	
	public static final String PBIMeasurementResultTable = "PBI_measurement_result";
	public static final String PBIMeasurementResultId = "Measure_id";
	public static final String PBIMeasurementPBIId = "PBI_id";
	public static final String PBIMeasurementResultResult = "Measurement_result";
	public static final String PBIMeasurementResultDate = "Datum";
	
	public static final String CustomerPollMeasurementResultTable = "CustomerPoll_measurement_result";
	public static final String CustomerPollMeasurementResultId = "Measure_id";
	public static final String CustomerPollMeasurementSprintId = "Sprint_id";
	public static final String CustomerPollMeasurementResultResult = "Measurement_result";
	public static final String CustomerPollMeasurementResultDate = "Datum";
	public static final String CustomerPollMeasurementResultCustomerName = "Customer_name";
	
	public static final String DeveloperPollMeasurementResultTable = "DeveloperPoll_measurement_result";
	public static final String DeveloperPollMeasurementResultId = "Measure_id";
	public static final String DeveloperPollMeasurementSprintId = "Sprint_id";
	public static final String DeveloperPollMeasurementResultResult = "Measurement_result";
	public static final String DeveloperPollMeasurementResultDate = "Datum";
	public static final String DeveloperPollMeasurementResulEmployeeId = "Employee_id";
		
	// these enumerations were generated using extract.py script
	public enum AbsenceTypeEnum { AbsenceTypeId, AbsenceTypeDescription }
	public enum AdministrativeDaysEnum { EmployeeId, AbsenceTypeId, HoursNotWorked, MeasureDay }
	public enum EmployeeEnum { EmployeeId, EmployeeName, EmployeeSurname, EmployeeAddress }
	public enum ImpedimentEnum { ImpedimentId, TeamId, SprintId, EmployeeId, TaskId, ImpedimentDescription, ImpedimentType, ImpedimentStatus, ImpedimentStart, ImpedimentEnd, ImpedimentAge }
	public enum ImpedimentStatusEnum { StatusId, Description }; 
	public enum ImpedimentTypeEnum { TypeId, Description };
	public enum MeasureEnum { MeasureId, MeasureName, MeasureDescription }
	public enum PBIEnum { PBIId, ProjectId, SprintId, PBIDescription, PBIPriority, PBIInitialEstimate, PBIAdjustmentFactor }
	public enum PBIMeasurementResultEnum { MeasureId, PBIId, MeasurementResult, Datum }
	public enum ProjectEnum { ProjectId, ProjectName, ProjectDescription }
	public enum FinalReleaseEnum { ReleaseId, ReleaseDescription }
	public enum ReleasePBIEnum { PBIId, ReleaseId }
	public enum ReleaseMeasurementResultEnum { MeasureId, ReleaseId, MeasurementResult, Datum }
	public enum SprintEnum { SprintId, ProjectId, TeamId, SprintDescription, SprintBeginDate, SprintEndDate, SprintLength, SprintEstimatedDate }
	public enum SprintPBIEnum { MeasureDay, PBIId, TaskId, SprintId, EmployeeId, HoursSpent, HoursRemaining, NbOpenImped, NbClosedImped }
	public enum SprintMeasurementResultEnum { SprintId, MeasureId, MeasurementResult, Datum }
	public enum SprintTeamEnum { TeamId, SprintId }
	public enum TaskEnum { TaskId, EmployeeId, PBIId, TeamId, TaskParentId, TaskStatusId, TaskTypeId, TaskDescription, EngineeringHour, TaskDate, TaskActive }
	public enum TaskMeasurementResultEnum { MeasureId, TaskId, MeasurementResult, Datum }
	public enum TaskStatusEnum { TaskStatusId, TaskStatusDescription }
	public enum TaskTypeEnum { TaskTypeId, TaskTypeDescription }
	public enum TeamEnum { TeamId, TeamDescription }
	public enum TeamMemberEnum { EmployeeId, TeamId }
	public enum CustomerPollEnum { customerName, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, comment, q11}


}
