package scrummer.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import scrummer.enumerator.AdminDaysOperation;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.listener.AdminDaysListener;
import scrummer.listener.ImpedimentListener;
import scrummer.listener.OperationListener;
import scrummer.model.swing.AdminDaysTableModel;
import scrummer.model.swing.DeveloperTableModel;
import scrummer.model.swing.ImpedimentComboBoxModel;
import scrummer.model.swing.ImpedimentTableModel;
import scrummer.model.swing.ProductBacklogTableModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.util.Operation;
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
		_admindaysModelCommon = new AdminDaysModelCommon(_connectionModel, _operation);
		_admindaysTableModel = new AdminDaysTableModel(connectionModel, _admindaysModelCommon);
	}
	
	/**
	 * Add administrative day
	 * @param employee_id employee id
	 * @param absence_type_id type of absence
	 * @param hours_not_worked number of hours not worked
	 */
	public void add(int employee_id, int absence_type_id, int hours_not_worked)
	{
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO Administrative_days " +
			 	"VALUES (?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, employee_id);
			 st.setInt(2, absence_type_id);
			 st.setInt(3, hours_not_worked);
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
		_operation.addListener(listener);
	}
	
	/**
	 * Remove administrative days data change listener
	 * @param listener listener to remove
	 */
	public void removeAdminDaysListener(AdminDaysListener listener)
	{
		_operation.removeListener(listener);
	}
		
	/// common administrative days related functionality
	private AdminDaysModelCommon _admindaysModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// administrative days table model
	private AdminDaysTableModel _admindaysTableModel;
	/// administrative days operation
	private Operations.AdminDaysOperation _operation = new Operations.AdminDaysOperation();
}
