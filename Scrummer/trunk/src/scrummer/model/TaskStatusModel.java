package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskStatusOperation;
import scrummer.listener.TaskStatusListener;
import scrummer.model.swing.TaskStatusComboBoxModel;
import scrummer.model.swing.TaskStatusTableModel;
import scrummer.util.Operations;

/**
 * Task status model
 *
 * Takes care of adding, removing and modifying task statuses.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class TaskStatusModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public TaskStatusModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_taskstatusModelCommon = new TaskStatusModelCommon(_connectionModel, _operation);
		_taskstatusTableModel = new TaskStatusTableModel(connectionModel, _taskstatusModelCommon);
		_taskstatusComboBoxModel = new TaskStatusComboBoxModel(_taskstatusModelCommon);
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
				"INSERT INTO " + DBSchemaModel.TaskStatusTable +
			 	" (Task_status_description) " +
			 	"VALUES (?)";
			 st = conn.prepareStatement(query);
			 st.setString(1, desc);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, TaskStatusOperation.TaskStatus, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, TaskStatusOperation.TaskStatus, e.getMessage());
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
	 * Task status table model
	 * 
	 * @return task status table model
	 */
	public TaskStatusTableModel getTaskStatusTableModel()
	{
		return _taskstatusTableModel;
	}
	
	/**
	 * Add task status data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addTaskStatusListener(TaskStatusListener listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove task status data change listener
	 * @param listener listener to remove
	 */
	public void removeTaskStatusListener(TaskStatusListener listener)
	{
		_operation.removeListener(listener);
	}
	

	/**
	 * Fetch task status combo box model
	 * @return model
	 */
	public TaskStatusComboBoxModel getTaskStatusComboBoxModel()
	{
		return _taskstatusComboBoxModel;
	}
	
	public void setNewDesc(int id, String desc) 
	{
		_taskstatusModelCommon.setNewDesc(id, desc);
	}
	
	public void removeTaskStatus(String selectedId) 
	{
		_taskstatusModelCommon.removeTaskStatus(selectedId);
	}
		
	/// common absence type related functionality
	private TaskStatusModelCommon _taskstatusModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// task status combo box model
	private TaskStatusComboBoxModel _taskstatusComboBoxModel;
	/// task status table model
	private TaskStatusTableModel _taskstatusTableModel;
	/// task status operation
	private Operations.TaskStatusOperation _operation = new Operations.TaskStatusOperation();
}
