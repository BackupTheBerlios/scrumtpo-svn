package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;
import scrummer.util.Query;
import scrummer.util.ResultQuery;
import sun.security.pkcs11.Secmod.DbMode;

/**
 * This model features common developer related functionality
 */
public class DeveloperModelCommon {
	
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
	 * Fetch developers and return full names + ids
	 * 
	 * @return identified devs
	 */
	public Vector<IdValue> fetchDeveloperNames()
	{
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) {
				Vector<IdValue> res = new Vector<IdValue>();
				try {
					result.beforeFirst();
					while (result.next())
					{
						res.add(new IdValue(result.getInt(1), result.getString(2)));
					}
					setResult(res);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.EmployeeId + ", " + 
			"CONCAT(" + DBSchemaModel.EmployeeName + " , ' ', " +
						DBSchemaModel.EmployeeSurname + ") " +
			"FROM "   + DBSchemaModel.EmployeeTable);
		if (q.getResult() == null)
		{
			return new Vector<IdValue>();
		}
		else
		{
			return q.getResult();
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
	 * Add team into database given following data
	 * 
	 * @param name team name
	 * @param employeeIds employee id's
	 */
	public void addTeam(String name, Vector<Integer> employeeIds)
	{
		// TODO: this naively doesn't use transactions, implement it
		boolean cont = true;
		
		java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
        	
            conn = _connectionModel.getConnection();
            
            String query =
            	"INSERT INTO " + DBSchemaModel.TeamTable + " " +
            	"(" + DBSchemaModel.TeamName + ") " +
                "VALUES (?)";
            st = conn.prepareStatement(query);
            st.setString(1, name);
            st.execute();
              
            _connectionModel.close(st);      
        } catch (SQLException e) {
            e.printStackTrace();
            _operation.operationFailed(DataOperation.Insert, DeveloperOperation.Team, e.getMessage());
            cont = false;
        }
        finally {
	       	_connectionModel.close(res);
	       	_connectionModel.close(st);
	       	_connectionModel.close(conn);
        }
		
        if (cont)
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
					setResult(null);
					_operation.operationFailed(DataOperation.Insert, DeveloperOperation.Team, ex.getMessage());
				}	
			};
			q.queryResult(
				"SELECT " + DBSchemaModel.TeamId    + " "  + 
				"FROM "   + DBSchemaModel.TeamTable + " "  +
				"WHERE "  + DBSchemaModel.TeamName  + "='" + name + "'");
	        
			if (q.getResult() != null)
			{
				conn = null;
		        st   =  null;
		        res  = null;
		        try {
		        	
		            conn = _connectionModel.getConnection();
		            String query = 
		            	"INSERT INTO " + DBSchemaModel.TeamMemberTable + " " +
		            	"(" + DBSchemaModel.TeamMemberEmployeeId + ", " + 
		            		  DBSchemaModel.TeamMemberTeamId + ") " +
		            	"VALUES (?, ?)";
		            
		            st = conn.prepareStatement(query);
		            for (int i = 0; i < employeeIds.size(); i++)
		            {
		            	st.setString(1, q.getResult());	
		            	st.setInt(2, employeeIds.get(i));
		            	st.execute();
		            }
		        
		            _connectionModel.close(st);
		            _operation.operationSucceeded(DataOperation.Insert, DeveloperOperation.Team, "");
		        } catch (SQLException e) {
		            e.printStackTrace();
		            _operation.operationFailed(DataOperation.Insert, DeveloperOperation.Team, e.getMessage());
		        }
		        finally {
			       	_connectionModel.close(res);
			       	_connectionModel.close(st);
			       	_connectionModel.close(conn);
		        }
			}
        }
	}
	
	/**
	 * Set team name
	 * 
	 * @param id team id
	 * @param name new team name
	 */
	public void setTeamName(int id, String name)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, DeveloperOperation.TeamName, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, DeveloperOperation.TeamName, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.TeamTable + " " +
			"SET " + DBSchemaModel.TeamName + "='" + name + "' " +
			"WHERE " + DBSchemaModel.TeamId + "='" + id + "'");
	}
	
	/**
	 * Fetch all teams
	 * 
	 * @return identified teams
	 */
	public Vector<IdValue> fetchTeamNames()
	{
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) {
				setResult(IdValue.fetchValues(result));
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.TeamId + ", " + DBSchemaModel.TeamName + " " +
			"FROM "   + DBSchemaModel.TeamTable);
		
		if (q.getResult() == null)
		{
			return new Vector<IdValue>();
		}
		else
		{
			return q.getResult();
		}
	}
	
	/**
	 * Remove team
	 * 
	 * @param id team id
	 */
	public void removeTeam(int id)
	{
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
				_operation.operationFailed(DataOperation.Remove, DeveloperOperation.Team, ex.getMessage());
			}
		};
		q.query(
			"DELETE FROM " + DBSchemaModel.TeamMemberTable + " " +
			" WHERE " + DBSchemaModel.TeamMemberTeamId + "='" + id + "'"); 
		
		if (q.getResult())
		{
			
			q = new ResultQuery<Boolean>(_connectionModel)
			{
				@Override
				public void process() {
					setResult(true);
				}
				@Override
				public void handleException(SQLException ex) {
					setResult(false);
					ex.printStackTrace();
					_operation.operationFailed(DataOperation.Remove, DeveloperOperation.Team, ex.getMessage());
				}
			};
			q.query(
				"DELETE FROM " + DBSchemaModel.TeamTable + " " +
				" WHERE " + DBSchemaModel.TeamId + "='" + id + "'");			
		}
		if (q.getResult())
		{
			_operation.operationSucceeded(DataOperation.Remove, DeveloperOperation.Team, "");
		}
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operation<DeveloperOperation> _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
