package scrummer.model;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.exception.DBMap;
import scrummer.listener.ImpedimentListener;
import scrummer.model.swing.ImpedimentComboBoxModel;
import scrummer.model.swing.ImpedimentStatusComboBoxModel;
import scrummer.model.swing.ImpedimentTableModel;
import scrummer.model.swing.ImpedimentTypeComboBoxModel;
import scrummer.util.Operations;

/**
 * Impediment model
 *
 * Takes care of adding, removing and modifying impediments.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class ImpedimentModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public ImpedimentModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_impedimentModelCommon = 
			new ImpedimentModelCommon(_connectionModel, _operation);
		_impedimentTableModel = 
			new ImpedimentTableModel(connectionModel, _impedimentModelCommon);
		_impedimentComboBoxModel = 
			new ImpedimentComboBoxModel(_impedimentModelCommon);
		_impedimentStatusComboBoxModel =
			new ImpedimentStatusComboBoxModel(_impedimentModelCommon);
		_impedimentTypeComboBoxModel =
			new ImpedimentTypeComboBoxModel(_impedimentModelCommon);
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
	public void add(int team, int sprint, int employee, int task, String desc, String type, String status, java.sql.Date start, java.sql.Date end, int age)
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
			 st.setDate(8, start);
			 st.setDate(9, end);
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
	 * Impediment table model
	 * 
	 * @return impediment table model
	 */
	public ImpedimentTableModel getImpedimentTableModel()
	{
		return _impedimentTableModel;
	}
	
	/**
	 * Add impediment data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addImpedimentListener(ImpedimentListener listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * @return impediment status combo box model
	 */
	public ImpedimentStatusComboBoxModel getImpedimentStatusComboBoxModel() {
		return _impedimentStatusComboBoxModel;
	}
	
	/**
	 * @return impediment type combo box model
	 */
	public ImpedimentTypeComboBoxModel getImpedimentTypeComboBoxModel() {
		return _impedimentTypeComboBoxModel;
	}
	
	/**
	 * Remove impediment data change listener
	 * @param listener listener to remove
	 */
	public void removeImpedimentListener(ImpedimentListener listener)
	{
		_operation.removeListener(listener);
	}
	

	/**
	 * Fetch team combo box model(contains all team names)
	 * @return model
	 */
	public ImpedimentComboBoxModel getImpedimentComboBoxModel()
	{
		return _impedimentComboBoxModel;
	}
	
	/**
	 * Set new impediment team
	 * 
	 * @param impId impediment id
	 * @param newTeam team to set
	 */
	public void setImpedimentTeam(int impId, String newTeam)
	{
		_impedimentModelCommon.setImpedimentTeam(impId, newTeam);
	}
	
	/**
	 * Set new impediment sprint
	 * 
	 * @param impId impediment id
	 * @param newSprint sprint to set
	 */
	public void setImpedimentSprint(int impId, String newSprint) {
		_impedimentModelCommon.setImpedimentSprint(impId, newSprint);
	}
	
	/**
	 * Set new impediment employee
	 * 
	 * @param impId impediment id
	 * @param newEmployee employee to set
	 */
	public void setImpedimentEmployee(int impId, String newEmployee) {
		_impedimentModelCommon.setImpedimentEmployee(impId, newEmployee);
	}
	
	/**
	 * Set new impediment task
	 * 
	 * @param impId impediment id
	 * @param newTask task to set
	 */
	public void setImpedimentTask(int impId, String newTask) {
		_impedimentModelCommon.setImpedimentTask(impId, newTask);
	}
	
	/**
	 * Set new impediment description
	 * 
	 * @param impId impediment id
	 * @param newDesc description to set
	 */
	public void setImpedimentDesc(int impId, String newDesc) {
		_impedimentModelCommon.setImpedimentDesc(impId, newDesc);
	}
	
	/**
	 * Set new impediment type
	 * 
	 * @param impId impediment id
	 * @param newType type to set
	 */
	public void setImpedimentType(int impId, String newType) {
		_impedimentModelCommon.setImpedimentType(impId, newType);
	}
	
	/**
	 * Set new impediment status
	 * 
	 * @param impId impediment id
	 * @param newStatus status to set
	 */
	public void setImpedimentStatus(int impId, String newStatus) {
		_impedimentModelCommon.setImpedimentStatus(impId, newStatus);
	}
	
	/**
	 * Set new impediment start
	 * 
	 * @param impId impediment id
	 * @param newStart start to set
	 */
	public void setImpedimentStart(int impId, java.sql.Date newStart) {
		_impedimentModelCommon.setImpedimentStart(impId, newStart);
	}

	/**
	 * Set new impediment end
	 * 
	 * @param impId impediment id
	 * @param newEnd end to set
	 */
	public void setImpedimentEnd(int impId, java.sql.Date newEnd) {
		_impedimentModelCommon.setImpedimentEnd(impId, newEnd);
	}
	
	/**
	 * Set new impediment age
	 * 
	 * @param impId impediment id
	 * @param newAge age to set
	 */
	public void setImpedimentAge(int impId, String newAge) {
		_impedimentModelCommon.setImpedimentAge(impId, newAge);
	}
	
	/**
	 * Add impediment status type
	 * @param description status type description
	 */
	public void addStatus(String description) {
		_impedimentModelCommon.addStatus(description);
	}
	
	/**
	 * Change impediment status description
	 * @param statusId status id
	 * @param description description
	 */
	public void changeStatus(int statusId, String description) {
		_impedimentModelCommon.changeStatus(statusId, description);
	}
	
	/**
	 * Change impediment type description
	 * @param typeId type id
	 * @param description description
	 */
	public void changeType(int typeId, String description) {
		_impedimentModelCommon.changeType(typeId, description);
	}

	/**
	 * Remove impediment status
	 * @param statusId id
	 */
	public void removeStatus(int statusId) {
		_impedimentModelCommon.removeStatus(statusId);
	}
	
	/**
	 * Remove impediment type
	 * @param typeId id
	 */
	public void removeType(int typeId) {
		_impedimentModelCommon.removeType(typeId);
	}
	
	public Integer getInteger(DBSchemaModel.ImpedimentEnum enumId, int impId)
	{
		ImpedimentModelCommon.Row row = _impedimentModelCommon.getRow(impId);
		switch (enumId)
		{
		case ImpedimentId: 
			return row.ImpedimentId;
		case TeamId:
			return row.TeamId;
		case EmployeeId: 
			return row.EmployeeId;
		case TaskId:
			return row.TaskId;
		case ImpedimentType:
			return row.ImpedimentType;
		case ImpedimentStatus:
			return row.ImpedimentStatus;
		case ImpedimentAge:
			return row.ImpedimentAge;
		}
		throw new DBMap(enumId);
	}
	
	public String getString(DBSchemaModel.ImpedimentEnum enumId, int impId)
	{
		ImpedimentModelCommon.Row row = _impedimentModelCommon.getRow(impId);
		switch (enumId)
		{
		case ImpedimentDescription:
			return row.ImpedimentDescription;
		}
		throw new DBMap(enumId);
	}
	
	public Date getDate(DBSchemaModel.ImpedimentEnum enumId, int impId)
	{
		ImpedimentModelCommon.Row row = _impedimentModelCommon.getRow(impId);
		switch (enumId)
		{
			case ImpedimentStart:
				return row.ImpedimentStart;
			case ImpedimentEnd:
				return row.ImpedimentEnd;
		}
		throw new DBMap(enumId);
	}
	
	/**
	 * Remove impediment with given id
	 * @param id impediment id
	 */
	public void remove(int id) 
	{
		if (_impedimentModelCommon.removeImpediment(id))
		{
			_impedimentTableModel.refresh();
		}
	}

	/// impediment statuses
	private ImpedimentStatusComboBoxModel _impedimentStatusComboBoxModel;
	/// impediment types
	private ImpedimentTypeComboBoxModel _impedimentTypeComboBoxModel;
	/// common impediment related functionality
	private ImpedimentModelCommon _impedimentModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// impediment combo box model
	private ImpedimentComboBoxModel _impedimentComboBoxModel;
	/// developer table model
	private ImpedimentTableModel _impedimentTableModel;
	/// developer operation
	private Operations.ImpedimentOperation _operation = new Operations.ImpedimentOperation();
}
