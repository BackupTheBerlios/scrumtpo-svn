package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;
import scrummer.util.Query;
import scrummer.util.ResultQuery;

/**
 * This model features common developer related functionality
 */
public class ProductBacklogModelCommon {
	
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
	public ProductBacklogModelCommon(ConnectionModel connectionModel, Operation<ProductBacklogOperation> operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Insert into product backlog
	 * @param project current project
	 * @param sprint current sprint
	 * @param description description of product backlog item
	 * @param priority priority of product backlog item
	 * @param initial_estimate how many hour should be spent on item
	 * @param adjustment_factor ratio of extra hours for item
	 * @param adjusted_estimate max hours spent on item
	 */
	public void add(int project, int sprint, String description, int priority, int initial_estimate, float adjustment_factor, int adjusted_estimate)
	{
		
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO PBI " +
			 	"(Project_id, Sprint_id, PBI_description, PBI_priority, PBI_initial_estimate, PBI_adjustment_factor, PBI_adjusted_estimate) " +
			 	"VALUES (?, ?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, project);
			 st.setInt(2, sprint);
			 st.setString(3, description);
			 st.setInt(4, priority);
			 st.setInt(5, initial_estimate);
			 st.setFloat(6, adjustment_factor);
			 st.setInt(7, adjusted_estimate);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, ProductBacklogOperation.ProductBacklog, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, ProductBacklogOperation.ProductBacklog, e.getMessage());
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
	 * Fetch entire product backlog table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchProductBacklogTable()
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
		q.queryResult("SELECT * FROM " + DBSchemaModel.PBITable);
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
	 * Set data for given column on PBI
	 * 
	 * @return true if data was set, false if error occurred
	 */
	public boolean setProductBacklog(String id, String column, String value)
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
	        	_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.ProductBacklog, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		
		q.query("UPDATE " + DBSchemaModel.PBITable + 
				" SET " + column + "='" + value + "' " +
				"WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
		
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
	 * Remove PBI by id
	 * 
	 * @param id PBI id
	 * @return true if PBI was removed, false otherwise
	 */
	public boolean removeProductBacklogItem(String id)
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
	        	_operation.operationFailed(DataOperation.Remove, ProductBacklogOperation.ProductBacklog, 
	        		i18n.tr("Could not remove product backlog item."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.PBITable + 
				" WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
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
	private Operation<ProductBacklogOperation> _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
