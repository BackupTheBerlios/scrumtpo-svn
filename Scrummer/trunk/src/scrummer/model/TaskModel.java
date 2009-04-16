package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskOperation;
import scrummer.listener.TaskListener;
import scrummer.model.swing.TaskComboBoxModel;
import scrummer.model.swing.TaskTableModel;
import scrummer.util.Operations;

/**
 * Task model
 *
 * Takes care of adding, removing and modifying tasks.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class TaskModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public TaskModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_taskModelCommon = new TaskModelCommon(_connectionModel, _operation);
		_taskTableModel = new TaskTableModel(connectionModel, _taskModelCommon);
		_taskComboBoxModel = new TaskComboBoxModel(_taskModelCommon);
	}
	
	/**
	 * Add task
	 */
	public void add(int emp, int team, int status, int type, String desc, java.sql.Date date, String active)
	{
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.TaskTable +
			 	"(Employee_id, Team_id, Task_status_id, Task_type_id, Task_description, Task_date, Task_active) " +
			 	"VALUES (?, ?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, emp);
			 st.setInt(2, team);
			 st.setInt(3, status);
			 st.setInt(4, type);
			 st.setString(5, desc);
			 st.setDate(6, date);
			 st.setString(7, active);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, TaskOperation.Task, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, TaskOperation.Task, e.getMessage());
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
	 * Task table model
	 * 
	 * @return task table model
	 */
	public TaskTableModel getTaskTableModel()
	{
		return _taskTableModel;
	}
	
	/**
	 * Add task data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addTaskListener(TaskListener listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove task data change listener
	 * @param listener listener to remove
	 */
	public void removeTaskListener(TaskListener listener)
	{
		_operation.removeListener(listener);
	}
	

	/**
	 * Fetch task combo box model
	 * @return model
	 */
	public TaskComboBoxModel getTaskComboBoxModel()
	{
		return _taskComboBoxModel;
	}
		
	/// common task related functionality
	private TaskModelCommon _taskModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// task combo box model
	private TaskComboBoxModel _taskComboBoxModel;
	/// task table model
	private TaskTableModel _taskTableModel;
	/// task operation
	private Operations.TaskOperation _operation = new Operations.TaskOperation();
}
