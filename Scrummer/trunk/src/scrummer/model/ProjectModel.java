package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.OperationListener;
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
		java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            	"INSERT INTO " + Project + " " +
            	"(Project_name, Project_description) " +
                "VALUES (?, ?)";
            st = conn.prepareStatement(query);
            st.setString(1, /* Util.toutf8(*/ name);
            st.setString(2, /*Util.toutf8( */description);
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
	 * Remove project
	 * 
	 * @param id project id
	 */
	public void removeProject(int id)
	{
		
	}
	
	/**
	 * Open project
	 * 
	 * @param id project id
	 */
	public void openProject(int id)
	{
		
	}
	
	/**
	 * Close project
	 * 
	 * @param id project id
	 */
	public void closeProject(int id)
	{
		
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
	 * Add listener to connection related events
	 * @param listener listener
	 */
	public void addConnectionListener(OperationListener<ProjectOperation> listener) {
		_operation.addListener(listener);
	}
	
	/**
	 * Remove listener to connection related events
	 * @param listener listener
	 */
	public void removeConnectionListener(OperationListener<ProjectOperation> listener)
	{
		_operation.removeListener(listener);
	}
	
	/// is any project currently opened
	private boolean _opened = false;
	/// project id
	private int _project = 0;
	/// connection model
	private ConnectionModel _connectionModel;
	/// project event listeners
	private Operation<ProjectOperation> _operation = new Operation<ProjectOperation>();
	/// project tablename
	private static final String Project = "Project";
}
