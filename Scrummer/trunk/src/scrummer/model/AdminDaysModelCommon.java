package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.AdminDaysOperation;
import scrummer.enumerator.DataOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * This model features common impediment related functionality
 */
public class AdminDaysModelCommon 
{	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public AdminDaysModelCommon(ConnectionModel connectionModel, Operations.AdminDaysOperation operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Add administrative day
	 * @param employee_id employee id
	 * @param absence_type_id absence type
	 * @param hours_not_worked number of hours not worked
	 */
	public void add(int employee_id, int absence_type_id, int hours_not_worked, int measure_day)
	{
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO Administrative_days (" +
				DBSchemaModel.EmployeeId + ", " + DBSchemaModel.AbsenceTypeId +
				", " + DBSchemaModel.HoursNotWorked + ", " + DBSchemaModel.measureDay + ") " +
			 	"VALUES (?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, employee_id);
			 st.setInt(2, absence_type_id);
			 st.setInt(3, hours_not_worked);
			 st.setInt(4, measure_day);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, AdminDaysOperation.Administrative_days, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, AdminDaysOperation.Administrative_days, e.getMessage());
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
	 * Fetch entire administrative days table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchAdminDaysTable()
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
		
		q.queryResult("SELECT CONCAT(Employee_name, ' ', Employee_surname), Absence_type_description, Hours_not_worked, Measure_Day " +
				"FROM (" + DBSchemaModel.EmployeeTable + " JOIN " + DBSchemaModel.AbsenceTypeTable + ") JOIN " 
				+ DBSchemaModel.AdminDaysTable + " WHERE Employee.Employee_id = Administrative_days.Employee_id AND " +
				"Absence_type.Absence_type_id = Administrative_days.Absence_type_id");
		if (q.getResult() == null)
		{
			return new Vector<ObjectRow>();
		}
		else
		{
			return q.getResult();
		}
	}
	
	public Vector<IdValue> fetchAbsenceTypeNames() 
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
			"SELECT * FROM "   + 
			DBSchemaModel.AbsenceTypeTable);
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
	 * Remove administrative day by employee id and measure day
	 * 
	 * @param id employee id
	 * @param day measure day
	 * @return true if administrative day was removed, false otherwise
	 */
	public boolean removeImpediment(int id, int day)
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
	        	_operation.operationFailed(DataOperation.Remove, AdminDaysOperation.Administrative_days, 
	        		i18n.tr("Could not remove administrative day."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.AdminDaysTable + 
				" WHERE " + DBSchemaModel.EmployeeId + "=" + id + " AND " +
				DBSchemaModel.measureDay + "=" + day);
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
	private Operations.AdminDaysOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
