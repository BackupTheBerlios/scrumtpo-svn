package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.model.swing.ProductBacklogTableModel;
import scrummer.util.Operation;

/**
 * Product Backlog model
 *
 * Takes care of insert into, remove from and modifying Product Backlog.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class ProductBacklogModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public ProductBacklogModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
	}
	
	/**
	 * Insert into Product Backlog
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
			 
			 _productbacklogOp.operationSucceeded(DataOperation.Insert, ProductBacklogOperation.ProductBacklog, "");
		} catch (SQLException e) {
			_productbacklogOp.operationFailed(DataOperation.Insert, ProductBacklogOperation.ProductBacklog, e.getMessage());
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
	 * Remove product backlog item by id
	 * @param id product backlog id
	 */
	public void remove(int id)
	{
		throw new RuntimeException("Not yet implemented.");
	}
	
	/**
	 * Product Backlog table model
	 * 
	 * @return product backlog table model
	 */
	public ProductBacklogTableModel getProductBacklogTableModel()
	{
		return _productbacklogTableModel;
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer table model
	private ProductBacklogTableModel _productbacklogTableModel;
	/// developer operation
	private Operation<ProductBacklogOperation> _productbacklogOp = new Operation<ProductBacklogOperation>();
}
