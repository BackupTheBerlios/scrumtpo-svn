package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.Operations;
import scrummer.util.Query;
import scrummer.util.ResultQuery;

/**
 * Common project model operations(sql queries) that are used in several project related models
 */
public class ProjectModelCommon {
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public ProjectModelCommon(ConnectionModel connectionModel, Operations.ProjectOperation operation)
	{
		_connectionModel = connectionModel;
		_operation = operation;
	}
	
	/**
	 * Add project to database
	 * 
	 * @param name project name
	 * @param description project description
	 */
	public void addProject(String name, String description)
	{
		java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            	"INSERT INTO " + DBSchemaModel.ProjectTable + " " +
            	"(" + DBSchemaModel.ProjectName + ", " + DBSchemaModel.ProjectDescription + ") " +
                "VALUES (?, ?)";
            st = conn.prepareStatement(query);
            st.setString(1, name);
            st.setString(2, description);
            st.execute();
              
            _operation.operationSucceeded(DataOperation.Insert, ProjectOperation.Project, "");
        } catch (SQLException e) {
            e.printStackTrace();
            _operation.operationFailed(DataOperation.Insert, ProjectOperation.Project, e.getMessage());
        }
        finally {
	       	_connectionModel.close(res);
	       	_connectionModel.close(st);
	       	_connectionModel.close(conn);
        }
	}
	
	/**
	 * Fetch project name given it's id
	 * @param id project id
	 * @return project name
	 */
	public String fetchProjectName(int id)
	{
		ResultQuery<String> q = new ResultQuery<String>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next())
				{
					setResult(result.getString(1));
				}
			}
			
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.ProjectName + 
			" FROM " + DBSchemaModel.ProjectTable + " WHERE " + DBSchemaModel.ProjectId + "='" + id + "'");
		return q.getResult();
	}
	
	/**
	 * Query project description
	 * @param id project id 
	 * @return project description
	 */
	public String fetchProjectDescription(int id) {
		ResultQuery<String> q = new ResultQuery<String>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next())
				{
					setResult(result.getString(1));
				}
			}
			
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.ProjectDescription + 
			" FROM " + DBSchemaModel.ProjectTable + " WHERE " + DBSchemaModel.ProjectId + "='" + id + "'");
		return q.getResult();
	}
	
	/**
	 * Fetch all projects from db
	 * @return project id's and names
	 */
	public Vector<IdValue> fetchProjects()
	{
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel) {	

			@Override
			public void processResult(ResultSet result) throws SQLException {
				setResult(IdValue.fetchValues(result));
			}
			
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}					
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.ProjectId + ", " +
						DBSchemaModel.ProjectName + 
			" FROM " + DBSchemaModel.ProjectTable);
		
		return q.getResult();
	}
	
	/**
	 * Remove project id
	 * 
	 * TODO: Remove everything associated with project
	 * 
	 * @param projectId project id
	 */
	public void removeProject(int projectId)
	{
		Query q = new Query(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Remove, ProjectOperation.Project, "");
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Remove, ProjectOperation.Project, ex.getMessage());
			}
		};
		q.query(
			"DELETE FROM " + DBSchemaModel.ProjectTable + " " +
			"WHERE " + DBSchemaModel.ProjectId + "='" + projectId + "'");		
	}

	/**
	 * Set project name
	 * 
	 * @param projectId project id
	 * @param name new project name
	 */
	public boolean setProjectName(int projectId, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{	
			@Override
			public void process() {
				setResult(true);
	    
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Update, ProjectOperation.Project, 
	        		i18n.tr("Could not set parameter") + ": " + ex.getMessage());
			}
		};
		q.query("UPDATE " + DBSchemaModel.ProjectTable + 
				" SET " + DBSchemaModel.ProjectName + "='" + name + "' " +
				"WHERE " + DBSchemaModel.ProjectId + "='" + projectId + "'");
		
		if (q.getResult())
		{
			_projectName = name;
	        _operation.operationSucceeded(DataOperation.Update, ProjectOperation.Project, "");
		}
		
		return q.getResult();
	}
	
	/**
	 * Set project description
	 *  
	 * @param projectId project id
	 * @param description project description
	 */
	public boolean setProjectDescription(int projectId, String description) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{	
			@Override
			public void process() {
				setResult(true);
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(false);
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Update, ProjectOperation.Project, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		q.query("UPDATE " + DBSchemaModel.ProjectTable + " " +
				"SET " + DBSchemaModel.ProjectDescription + "='" + description + "' " +
				"WHERE " + DBSchemaModel.ProjectId + "='" + projectId + "'");
		if (q.getResult())
		{
			_projectDescription = description;
		    _operation.operationSucceeded(DataOperation.Update, ProjectOperation.Project, "");
		}
		return q.getResult();
	}
	
	/**
	 * Fetch project name
	 * @return project name
	 */
	public String getName()
	{
		return _projectName;
	}

	/**
	 * Set project name
	 */
	public void setName(String value)
	{
		_projectName = value;
	}
	
	/**
	 * Fetch project description
	 * @return orihect description
	 */
	public String getDescription()
	{
		return _projectDescription;
	}
	
	/**
	 * Set project description
	 */
	public void setDescription(String value)
	{
		_projectDescription = value;
	}
	
	/**
	 * Open project
	 * @param projectId project id
	 */
	public void openProject(int projectId)
	{
		_projectName = fetchProjectName(projectId);
		_projectDescription = fetchProjectDescription(projectId);
	}
	
	/// project name
	private String _projectName = "";
	/// project description
	private String _projectDescription = "";
	/// connection model
	private ConnectionModel _connectionModel;
	/// data operation notifier
	private Operations.ProjectOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
