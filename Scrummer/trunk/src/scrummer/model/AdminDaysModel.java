package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import scrummer.enumerator.AdminDaysOperation;
import scrummer.enumerator.DataOperation;
import scrummer.listener.AdminDaysListener;
import scrummer.model.swing.AbsenceTypeComboBoxModel;
import scrummer.model.swing.AdminDaysTableModel;
import scrummer.util.Operations;

/**
 * Administrative days model
 *
 * Takes care of adding and modifying administrative days.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class AdminDaysModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public AdminDaysModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_admindaysModelCommon = new AdminDaysModelCommon(_connectionModel, _adminoperation);
		_admindaysTableModel = new AdminDaysTableModel(connectionModel, _admindaysModelCommon);
		
		_absencetypeModelCommon = new AbsenceTypeModelCommon(_connectionModel, _absenceoperation);
		_absComboBoxModel = new AbsenceTypeComboBoxModel(_absencetypeModelCommon);
	}
	
	/**
	 * Add administrative day
	 * @param employee_id employee id
	 * @param absence_type_id type of absence
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
			 
			 _adminoperation.operationSucceeded(DataOperation.Insert, AdminDaysOperation.Administrative_days, "");
		} catch (SQLException e) {
			_adminoperation.operationFailed(DataOperation.Insert, AdminDaysOperation.Administrative_days, e.getMessage());
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
	 * Administrative days table model
	 * 
	 * @return administrative days table model
	 */
	public AdminDaysTableModel getAdminDaysTableModel()
	{
		return _admindaysTableModel;
	}
	
	/**
	 * Add administrative days data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addAdminDaysListener(AdminDaysListener listener)
	{
		_adminoperation.addListener(listener);
	}
	
	/**
	 * Remove administrative days data change listener
	 * @param listener listener to remove
	 */
	public void removeAdminDaysListener(AdminDaysListener listener)
	{
		_adminoperation.removeListener(listener);
	}
	
	public AbsenceTypeComboBoxModel getAbsenceTypeComboBoxModel() {
		return _absComboBoxModel;
	}
		
	/// common administrative days related functionality
	private AdminDaysModelCommon _admindaysModelCommon;
	private AbsenceTypeModelCommon _absencetypeModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// administrative days table model
	private AdminDaysTableModel _admindaysTableModel;
	/// absence type combobox model
	private AbsenceTypeComboBoxModel _absComboBoxModel;
	/// administrative days operation
	private Operations.AdminDaysOperation _adminoperation = new Operations.AdminDaysOperation();
	/// absence type operation
	private Operations.AbsenceTypeOperation _absenceoperation = new Operations.AbsenceTypeOperation();
}
