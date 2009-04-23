package scrummer.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;
import scrummer.util.Operations;
import scrummer.util.Query;
import scrummer.util.ResultQuery;

/**
 * This model features common developer related functionality
 */
public class ProductBacklogModelCommon {
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public ProductBacklogModelCommon(ConnectionModel connectionModel, Operations.ProductBacklogOperation operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Insert into product backlog
	 * 
	 * @param projectId project into which to insert
	 * @param sprintId current sprint
	 * @param description description of product backlog item
	 * @param priority priority of product backlog item
	 * @param initial_estimate how many hour should be spent on item
	 * @param adjustment_factor ratio of extra hours for item
	 */
	public void add(int projectId, int sprintId, String description, int priority, BigDecimal initial_estimate, BigDecimal adjustment_factor)
	{
		
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO PBI " +
			 	"(" + DBSchemaModel.PBIProject + ", " +
			 		  DBSchemaModel.PBISprint + ", " +
			 		  DBSchemaModel.PBIDesc + ", " +
			 		  DBSchemaModel.PBIPriority + ", " +
			 		  DBSchemaModel.PBIIniEstimate + ", " +
			 		  DBSchemaModel.PBIAdjFactor + ") " +
			 	"VALUES (?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, projectId);
			 st.setInt(2, sprintId);
			 st.setString(3, description);
			 st.setInt(4, priority);
			 st.setBigDecimal(5, initial_estimate);
			 st.setBigDecimal(6, adjustment_factor);
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
	 * @param projectId project
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchProductBacklogTable(int projectId)
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
		q.queryResult(
			"SELECT " + DBSchemaModel.PBIId + ", " +
						DBSchemaModel.PBIDesc + ", " +
						DBSchemaModel.PBIPriority + ", " +
						DBSchemaModel.PBIIniEstimate + ", " +
						DBSchemaModel.PBIAdjFactor + ", " +						
					    DBSchemaModel.PBIIniEstimate + " * " + 
						DBSchemaModel.PBIAdjFactor + " as " + DBSchemaModel.PBIAdjEstimate + ", " + 
						DBSchemaModel.PBISprint + " " +
			"FROM " + DBSchemaModel.PBITable + " WHERE " + DBSchemaModel.PBIProject + "='" + projectId + "'");
		if (q.getResult() == null)
		{
			return new Vector<ObjectRow>();
		}
		else
		{
			return q.getResult();
		}
	}
	
	public Integer getSprint(int productId, int projectId)
	{
		return Integer.parseInt(getProductBacklog(productId, DBSchemaModel.PBISprint));
	}
	
	public String getDescription(int productId, int projectId)	
	{
		return getProductBacklog(productId, DBSchemaModel.PBIDesc);
	}
	
	public BigDecimal getInitialEstimate(int productId, int projectId)
	{
		return new BigDecimal(getProductBacklog(productId, DBSchemaModel.PBIIniEstimate));
	}
	
	public BigDecimal getAdjustmentFactor(int productId, int projectId)
	{
		return new BigDecimal(getProductBacklog(productId, DBSchemaModel.PBIAdjFactor));
	}
	
	public int getPriority(int productId, int projectId)
	{
		return Integer.parseInt(getProductBacklog(productId,DBSchemaModel.PBIPriority));
	}
	
	/**
	 * Fetch product backlog item 
	 * 
	 * @param productId product backlog item id
	 * @param column required column
	 * @return cell value
	 */
	private String getProductBacklog(int productId, String column)
	{
		ResultQuery<String> q = new ResultQuery<String>(_connectionModel)
		{				
			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next())
					setResult(result.getString(1));
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.ProductBacklog, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		
		q.queryResult(
			"SELECT " + column + " " + 
			"FROM " + DBSchemaModel.PBITable + " " +
			"WHERE " + DBSchemaModel.PBIId + "='" + productId + "'");
		
		return q.getResult();
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
	
	/**
	 * Fetch PBIs and return full descriptions + ids
	 * 
	 * @return identified PBIs
	 */
	public Vector<IdValue> fetchPBIsNames() 
	{
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) 
			{
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
			public void handleException(SQLException ex) 
			{
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.PBIId + ", " +
			DBSchemaModel.PBIDesc +
			" FROM "   + DBSchemaModel.PBITable);
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
	 * Fetch all pbi names on given project and sprint
	 * @param project project
	 * @param sprint sprint
	 * @return a list of pbi descriptions
	 */
	public Vector<IdValue> fetchPBIsNames(int project, int sprint) {
	
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) 
			{
				setResult(IdValue.fetchValues(result));		
			}
			@Override
			public void handleException(SQLException ex) 
			{
				setResult(new Vector<IdValue>());
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.PBIId + ", " +
			DBSchemaModel.PBIDesc +
			" FROM "   + DBSchemaModel.PBITable + " WHERE " +
			DBSchemaModel.PBIProject + "=" + project + 
			" AND " +
			DBSchemaModel.PBISprint + "=" + sprint);
		
		return q.getResult();
	}
	
	/**
	 * Set pbi project
	 * 
	 * @param id pbi id
	 * @param name new pbi project
	 */
	public void setPBIProject(int id, String name) 
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ProductBacklogOperation.Project, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.Project, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.PBITable + " " +
			"SET " + DBSchemaModel.PBIProject + "='" + name + "' " +
			"WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
	}
	
	/**
	 * Set pbi sprint
	 * 
	 * @param id pbi id
	 * @param name new pbi sprint
	 */
	public void setPBISprint(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ProductBacklogOperation.Project, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.Project, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.PBITable + " " +
			"SET " + DBSchemaModel.PBISprint + "='" + name + "' " +
			"WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
	}
	
	/**
	 * Set pbi description
	 * 
	 * @param id pbi id
	 * @param name new pbi description
	 */
	public void setPBIDesc(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ProductBacklogOperation.Project, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.Project, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.PBITable + " " +
			"SET " + DBSchemaModel.PBIDesc + "='" + name + "' " +
			"WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
	}
	
	/**
	 * Set pbi priority
	 * 
	 * @param id pbi id
	 * @param name new pbi priority
	 */
	public void setPBIPriority(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ProductBacklogOperation.Project, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.Project, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.PBITable + " " +
			"SET " + DBSchemaModel.PBIPriority + "='" + name + "' " +
			"WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
	}
	
	/**
	 * Set pbi initial estimate
	 * 
	 * @param id pbi id
	 * @param name new pbi initial estimate
	 */
	public void setPBIIniEstimate(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ProductBacklogOperation.Project, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.Project, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.PBITable + " " +
			"SET " + DBSchemaModel.PBIIniEstimate + "='" + name + "' " +
			"WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
	}
	
	/**
	 * Set pbi adjustment factor
	 * 
	 * @param id pbi id
	 * @param name new pbi adjustment factor
	 */
	public void setPBIAdjFactor(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ProductBacklogOperation.Project, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.Project, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.PBITable + " " +
			"SET " + DBSchemaModel.PBIAdjFactor + "='" + name + "' " +
			"WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
	}
	
	/**
	 * Set pbi adjusted estimate
	 * 
	 * @param id pbi id
	 * @param name new pbi adjusted estimate
	 */
	public void setPBIAdjEstimate(int id, String name) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ProductBacklogOperation.Project, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.Project, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.PBITable + " " +
			"SET " + DBSchemaModel.PBIAdjEstimate + "='" + name + "' " +
			"WHERE " + DBSchemaModel.PBIId + "='" + id + "'");
	}
	
	/**
	 * Change one or more pbi entries
	 * 
	 * @param pbiId product backlog item
	 * @param sprintId sprint
	 * @param description product backlog description
	 * @param priority priority
	 * @param initial_estimate initial estimate
	 * @param adjustment_factor adjustment factor
	 */
	public void change(int pbiId, int projectId, int sprintId, String description, int priority, BigDecimal initial_estimate, BigDecimal adjustment_factor) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, ProductBacklogOperation.ProductBacklog, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, ProductBacklogOperation.ProductBacklog, ex.getMessage());
			}
			
		};
				
		q.query(
			"UPDATE " + DBSchemaModel.PBITable + " " +
			"SET " +  DBSchemaModel.PBISprint + "='" + sprintId + "', " +
			 		  DBSchemaModel.PBIDesc + "='" + description + "', " +
			 		  DBSchemaModel.PBIPriority + "='" + priority + "', " +
			 		  DBSchemaModel.PBIIniEstimate + "='" + initial_estimate + "', " +
			 		  DBSchemaModel.PBIAdjFactor + "='" + adjustment_factor + "'" +
			"WHERE " + DBSchemaModel.PBIId + "='" + pbiId + "' AND " +
					   DBSchemaModel.PBIProject + "='" + projectId + "'");
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operations.ProductBacklogOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
	
}
