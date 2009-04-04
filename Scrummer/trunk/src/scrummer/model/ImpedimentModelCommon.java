package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;
import scrummer.util.Query;
import scrummer.util.ResultQuery;

/**
 * This model features common impediment related functionality
 */
public class ImpedimentModelCommon 
{	
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
	public ImpedimentModelCommon(ConnectionModel connectionModel, Operation<ImpedimentOperation> operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Add impediment
	 * @param team employee's team
	 * @param sprint sprint when impediment occurred
	 * @param employee employee who experienced impediment
	 * @param task task which has to do with impediment
	 * @param desc description of impediment
	 * @param type impediment type
	 * @param status impediment status
	 * @param start date when impediment started
	 * @param end date when impediment was resolved
	 * @param age number of days when impediment was active
	 */
	public void add(int team, int sprint, int employee, int task, String desc, String type, String status, String start, String end, int age)
	{
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO Impediment " +
			 	"(Team_id, Sprint_id, Employee_id, Task_id, Impediment_description, Impediment_type, Impediment_status, Impediment_start, Impediment_end, Impediment_age) " +
			 	"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, team);
			 st.setInt(2,sprint);
			 st.setInt(3,employee);
			 st.setInt(4,task);
			 st.setString(5, desc);
			 st.setString(6, type);
			 st.setString(7, status);
			 st.setString(8, start);
			 st.setString(9, end);
			 st.setInt(10, age);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, ImpedimentOperation.Impediment, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, ImpedimentOperation.Impediment, e.getMessage());
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
	 * Fetch entire impediment table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchImpedimentTable()
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
		q.queryResult("SELECT * FROM " + DBSchemaModel.ImpedimentTable);
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
	 * Set data for given column on impediment
	 * 
	 * @return true if impediment was set, false if error occurred
	 */
	public boolean setImpediment(String id, String column, String value)
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
	        	_operation.operationFailed(DataOperation.Update, ImpedimentOperation.Impediment, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		
		q.query("UPDATE " + DBSchemaModel.ImpedimentTable + 
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
	 * Remove impediment by id
	 * 
	 * @param id impediment id
	 * @return true if impediment was removed, false otherwise
	 */
	public boolean removeImpediment(String id)
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
	        	_operation.operationFailed(DataOperation.Remove, ImpedimentOperation.Impediment, 
	        		i18n.tr("Could not remove impediment."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.ImpedimentTable + 
				" WHERE " + DBSchemaModel.ImpedimentId + "='" + id + "'");
		if (q.getResult() == null)
		{
			return false;
		}
		else
		{
			return q.getResult();
		}
	}
	
		/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operation<ImpedimentOperation> _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
