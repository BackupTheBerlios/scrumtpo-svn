package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.OperationListener;
import scrummer.model.swing.ProjectListModel;
import scrummer.util.Operation;

/**
 * Project creation and loading data model 
 */
public class ProjectModel {

	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public ProjectModel(ConnectionModel connectionModel)
	{
		_connectionModel = connectionModel;
		_projectModelCommon = new ProjectModelCommon(connectionModel, _operation);
		_projectListModel = new ProjectListModel(_projectModelCommon);
	}
	
	/**
	 * Check for project existance
	 * 
	 * If project doesn't exist unload it.
	 */
	public void refresh()
	{
		
	}
	
	/**
	 * Add project to database
	 * 
	 * @param name project name
	 * @param description project description
	 */
	public void addProject(String name, String description)
	{
		_projectModelCommon.addProject(name, description);
	}
	
	/**
	 * Remove project
	 * 
	 * @param id project id
	 */
	public void removeProject(int id)
	{
		if (_project == id)
		{
			closeProject();
		}
		
	}
	
	/**
	 * Open project
	 * 
	 * @param id project id
	 */
	public void openProject(int id)
	{
		_project = id;
		_projectName = _projectModelCommon.fetchProjectName(id);
		_operation.operationSucceeded(DataOperation.Custom, ProjectOperation.Open, "");
	}
	
	/**
	 * Close project
	 * 
	 * @param id project id
	 */
	public void closeProject()
	{
		_project = -1;
		_operation.operationSucceeded(DataOperation.Custom, ProjectOperation.Close, "");	
	}
	
	/**
	 * Set project name
	 * 
	 * @param name project name
	 */
	public void setProjectName(String name)
	{
		
	}
	
	/**
	 * Set project description
	 * 
	 * @param description project description
	 */
	public void setProjectDescription(String description)
	{
		
	}
	
	/**
	 * Fetch currently loaded project name
	 * @return loaded project name
	 */
	public String getCurrentProjectName()
	{
		return _projectName;
	}
	
	public ProjectListModel getProjectListModel()
	{
		return _projectListModel;
	}
	
	/**
	 * Add listener to project change related events
	 * @param listener listener
	 */
	public void addProjectListener(OperationListener<ProjectOperation> listener) {
		_operation.addListener(listener);
	}
	
	/**
	 * Remove listener project change related events
	 * @param listener listener
	 */
	public void removeProjectListener(OperationListener<ProjectOperation> listener)
	{
		_operation.removeListener(listener);
	}
	
	/// project name
	private String _projectName = "";
	/// is any project currently opened
	private boolean _opened = false;
	/// common project related operations
	private ProjectModelCommon _projectModelCommon;
	/// project list model
	private ProjectListModel _projectListModel;
	/// project id
	private int _project = 0;
	/// connection model
	private ConnectionModel _connectionModel;
	/// project event listeners
	private Operation<ProjectOperation> _operation = new Operation<ProjectOperation>();
	/// project tablename
	private static final String Project = "Project";
}
