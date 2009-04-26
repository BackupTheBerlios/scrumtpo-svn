package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import scrummer.enumerator.AbsenceTypeOperation;
import scrummer.enumerator.DataOperation;
import scrummer.listener.AbsenceTypeListener;
import scrummer.model.swing.AbsenceTypeComboBoxModel;
import scrummer.model.swing.AbsenceTypeTableModel;
import scrummer.util.Operations;

/**
 * Absence type model
 *
 * Takes care of adding, removing and modifying absence types.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class AbsenceTypeModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public AbsenceTypeModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_absencetypeModelCommon = new AbsenceTypeModelCommon(_connectionModel, _operation);
		_absencetypeTableModel = new AbsenceTypeTableModel(connectionModel, _absencetypeModelCommon);
		_absencetypeComboBoxModel = new AbsenceTypeComboBoxModel(_absencetypeModelCommon);
	}
	
	/**
	 * Add absence type
	 * @param desc absence type description
	 */
	public void add(String desc)
	{
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO Absence_type " +
			 	"(Absence_type_description) " +
			 	"VALUES (?)";
			 st = conn.prepareStatement(query);
			 st.setString(1, desc);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, AbsenceTypeOperation.Description, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, AbsenceTypeOperation.Description, e.getMessage());
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
	 * Absence type table model
	 * 
	 * @return absence type table model
	 */
	public AbsenceTypeTableModel getAbsenceTypeTableModel()
	{
		return _absencetypeTableModel;
	}
	
	/**
	 * Add absence type data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addAbsenceTypeListener(AbsenceTypeListener listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove absence type data change listener
	 * @param listener listener to remove
	 */
	public void removeAbsenceTypeListener(AbsenceTypeListener listener)
	{
		_operation.removeListener(listener);
	}
	

	/**
	 * Fetch team combo box model(contains all team names)
	 * @return model
	 */
	public AbsenceTypeComboBoxModel getAbsenceTypeComboBoxModel()
	{
		return _absencetypeComboBoxModel;
	}
	
	public void setNewDesc(int id, String desc) 
	{
		_absencetypeModelCommon.setNewDesc(id, desc);
	}
	
	public void removeAbsenceType(String selectedId) 
	{
		_absencetypeModelCommon.removeAbsenceType(selectedId);
	}
		
	/// common absence type related functionality
	private AbsenceTypeModelCommon _absencetypeModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// absence type combo box model
	private AbsenceTypeComboBoxModel _absencetypeComboBoxModel;
	/// absence type table model
	private AbsenceTypeTableModel _absencetypeTableModel;
	/// absence type operation
	private Operations.AbsenceTypeOperation _operation = new Operations.AbsenceTypeOperation();
}
