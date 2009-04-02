package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.ProjectListener;
import scrummer.util.Operation;
import scrummer.util.Query;

/**
 * Common project model operations(sql queries) that are used in several project related models
 */
public class ProjectModelCommon {

	/**
	 * Project id and name combination
	 */
	public static class ProjectIdName
	{
		/**
		 * Constructor
		 * @param id project id
		 * @param name project name
		 */
		public ProjectIdName(int id, String name)
		{
			Id   = id;
			Name = name;
		}
		
		public int Id;
		public String Name;
	}

	/**
	 * Project name query has method for returning name as result
	 */
	private static class ProjectNameQuery extends Query
	{
		public ProjectNameQuery(ConnectionModel connectionModel) {
			super(connectionModel);
		}

		public String getResult() { return ""; }
	}
	
	/**
	 * Class that can return result in form of ProjectIdName 
	 */
	private static class ProjectIdNameQuery extends Query
	{
		public ProjectIdNameQuery(ConnectionModel connectionModel) {
			super(connectionModel);
		}

		public Vector<ProjectIdName> getResult()
		{
			return null;
		}	
	}
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public ProjectModelCommon(ConnectionModel connectionModel, Operation<ProjectOperation> operation)
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
            	"INSERT INTO " + Project + " " +
            	"(" + ProjectName + ", " + ProjectDescription + ") " +
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
		ProjectNameQuery q = new ProjectNameQuery(_connectionModel)
		{
			private String _result = "";

			@Override
			public String getResult() {
				return _result;
			}

			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next())
				{
					_result = result.getString(1);
				}
			}
			
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult("SELECT " + ProjectName + " FROM " + Project + " WHERE " + ProjectId + "='" + id + "'");
		return q.getResult();
	}
	
	/**
	 * Fetch all projects from db
	 * @return project id's and names
	 */
	public Vector<ProjectIdName> fetchProjects()
	{
		ProjectIdNameQuery q = new ProjectIdNameQuery(_connectionModel) {	
			private Vector<ProjectIdName> _result = new Vector<ProjectIdName>();
			
			@Override
			public Vector<ProjectIdName> getResult() {
				return _result;
			}

			@Override
			public void processResult(ResultSet result) throws SQLException {
				try {
					result.beforeFirst();
			        while (result.next())
			        {
			        	ProjectIdName pidn = new ProjectIdName(result.getInt(1), result.getString(2)); 
			        	_result.add(pidn);
			        }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}					
		};
		q.queryResult("SELECT " + ProjectId + ", " + ProjectName + " FROM " + Project);
		
		return q.getResult();
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// data operation notifier
	private Operation<ProjectOperation> _operation;
	
	private static final String Project = "Project";
	private static final String ProjectId = "Project_id";
	private static final String ProjectName = "Project_name";
	private static final String ProjectDescription = "Project_description";
}
