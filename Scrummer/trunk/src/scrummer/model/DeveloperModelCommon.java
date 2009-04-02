package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;
import scrummer.util.Query;
import scrummer.util.ResultQuery;

/**
 * This model features common developer related functionality
 */
public class DeveloperModelCommon {
	
	/**
	 * Id and name struct
	 */
	public static class IdName 
	{
		/**
		 * Constructor
		 * @param id id
		 * @param name name
		 */
		public IdName(int id, String name)
		{
			Id   = id;
			Name = name;
		}
		
		public int Id;
		public String Name;
	}
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public DeveloperModelCommon(ConnectionModel connectionModel, Operation<DeveloperOperation> operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Add developer
	 * @param name developer name
	 * @param surname developer surname
	 * @param address developer address
	 */
	public void add(String name, String surname, String address)
	{
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         try {
                  conn = _connectionModel.getConnection();
                  String query =
                 	"INSERT INTO " + DBSchemaModel.EmployeeTable + " " +
                 	"(" + DBSchemaModel.EmployeeName + "," + 
                 		  DBSchemaModel.EmployeeSurname + "," + 
                 		  DBSchemaModel.EmployeeAddress + ") " +
                    "VALUES (?, ?, ?)";

                  st = conn.prepareStatement(query);
                  st.setString(1, name);
                  st.setString(2, surname);
                  st.setString(3, address);
               
                  st.execute();
                   
                  _operation.operationSucceeded(DataOperation.Insert, DeveloperOperation.Developer, "");

         } catch (SQLException e) {
                 e.printStackTrace();
                 _operation.operationFailed(DataOperation.Insert, DeveloperOperation.Developer, e.getMessage());
         }
         finally
         {
        	 _connectionModel.close(res);
        	 _connectionModel.close(st);
        	 _connectionModel.close(conn);
         }
	}
	
	/**
	 * Fetch entire developer table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchDeveloperTable()
	{
		ResultQuery<Vector<ObjectRow>> q = new ResultQuery<Vector<ObjectRow>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) {
				setResult(ObjectRow.fetchRows(result)); 
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult("SELECT * FROM " + DBSchemaModel.EmployeeTable);
		if (q.getResult() == null)
		{
			return new Vector<ObjectRow>();
		}
		else
		{
			return q.getResult();
		}
	}
	
	/**
	 * Set data for given column on developer
	 * 
	 * @return true if developer was set, false if error occurred
	 */
	public boolean setDeveloper(String id, String column, String value)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{	
			@Override
			public void process() {
	            setResult(true);
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Update, DeveloperOperation.Developer, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		
		q.query("UPDATE " + DBSchemaModel.EmployeeTable + 
				" SET " + column + "='" + value + "' " +
				"WHERE " + DBSchemaModel.EmployeeId + "='" + id + "'");
		
		if (q.getResult() == null)
		{
			return false;
		}
		else
		{
			return q.getResult();
		}
	}
    
	/**
	 * Remove developer by id
	 * 
	 * @param id developer id
	 * @return true if developer was removed, false otherwise
	 */
	public boolean removeDeveloper(String id)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{	
			@Override
			public void process() {
				setResult(true);
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, DeveloperOperation.Developer, 
	        		i18n.tr("Could not remove developer."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.EmployeeTable + 
				" WHERE " + DBSchemaModel.EmployeeId + "='" + id + "'");
		if (q.getResult() == null)
		{
			return false;
		}
		else
		{
			return q.getResult();
		}
	}
	
	/**
	 * Fetch all developers on project with specified id 
	 * @param projectId project id
	 * @return devs
	 */
	public Vector<IdName> fetchProjectDevelopers(int projectId)
	{
		/*
		ResultQuery<Vector<IdName>> q = new ResultQuery<Vector<IdName>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) throws SQLException {
				
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult("");
		
		return q.getResult();
		*/
		return null;
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operation<DeveloperOperation> _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
