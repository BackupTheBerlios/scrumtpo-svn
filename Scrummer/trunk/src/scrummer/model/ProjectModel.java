package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.OperationListener;
import scrummer.model.swing.ProjectComboBoxModel;
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
		_projectModelCommon = 
			new ProjectModelCommon(connectionModel, _operation);
		_projectListModel = 
			new ProjectListModel(_projectModelCommon);
		_projectComboBoxModel = 
			new ProjectComboBoxModel(_projectModelCommon);
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
		
		_projectModelCommon.removeProject(id);
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
	
	/**
	 * Fetch project list model
	 * 
	 * @return model
	 */
	public ProjectListModel getProjectListModel()
	{
		return _projectListModel;
	}
	
	/**
	 * Fetch project list combo box model
	 * @return model
	 */
	public ProjectComboBoxModel getProjectComboBoxModel()
	{
		return _projectComboBoxModel;
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
	/// a list of all projects for use with combo boxes
	private ProjectComboBoxModel _projectComboBoxModel;
	/// project id
	private int _project = 0;
	/// project event listeners
	private Operation<ProjectOperation> _operation = new Operation<ProjectOperation>();
}
