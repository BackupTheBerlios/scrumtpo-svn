package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskTypeOperation;
import scrummer.listener.TaskTypeListener;
import scrummer.model.swing.TaskTypeComboBoxModel;
import scrummer.model.swing.TaskTypeTableModel;
import scrummer.util.Operations;

/**
 * Task type model
 *
 * Takes care of adding, removing and modifying task types.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class TaskTypeModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public TaskTypeModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_tasktypeModelCommon = new TaskTypeModelCommon(_connectionModel, _operation);
		_tasktypeTableModel = new TaskTypeTableModel(connectionModel, _tasktypeModelCommon);
		_tasktypeComboBoxModel = new TaskTypeComboBoxModel(_tasktypeModelCommon);
	}
	
	/**
	 * Add task type
	 * @param desc task type description
	 */
	public void add(String desc)
	{
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.TaskTypeTable +
			 	"(Task_type_description) " +
			 	"VALUES (?)";
			 st = conn.prepareStatement(query);
			 st.setString(1, desc);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, TaskTypeOperation.Description, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, TaskTypeOperation.Description, e.getMessage());
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
	 * Task type table model
	 * 
	 * @return task type table model
	 */
	public TaskTypeTableModel getTaskTypeTableModel()
	{
		return _tasktypeTableModel;
	}
	
	/**
	 * Add task type data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addTaskTypeListener(TaskTypeListener listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove task type data change listener
	 * @param listener listener to remove
	 */
	public void removeTaskTypeListener(TaskTypeListener listener)
	{
		_operation.removeListener(listener);
	}
	
	/**
	 * Fetch task type combo box model
	 * @return model
	 */
	public TaskTypeComboBoxModel getTaskTypeComboBoxModel()
	{
		return _tasktypeComboBoxModel;
	}
	
	public void setNewDesc(int id, String desc) 
	{
		_tasktypeModelCommon.setNewDesc(id, desc);
	}
	
	public void removeTaskType(String selectedId) 
	{
		_tasktypeModelCommon.removeTaskType(selectedId);
	}
		
	/// common task type related functionality
	private TaskTypeModelCommon _tasktypeModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// task type combo box model
	private TaskTypeComboBoxModel _tasktypeComboBoxModel;
	/// task type table model
	private TaskTypeTableModel _tasktypeTableModel;
	/// task type operation
	private Operations.TaskTypeOperation _operation = new Operations.TaskTypeOperation();
}
