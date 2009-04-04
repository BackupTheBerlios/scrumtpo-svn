package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.listener.OperationListener;
import scrummer.model.swing.DeveloperTableModel;
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
		_productbacklogModelCommon = new ProductBacklogModelCommon(_connectionModel, _operation);
		_productbacklogTableModel = new ProductBacklogTableModel(connectionModel, _productbacklogModelCommon, _operation);
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
		_productbacklogModelCommon.add(project, sprint, description, priority, initial_estimate, adjustment_factor, adjusted_estimate);
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
	
	/**
	 * Add product backlog data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addProductBacklogListener(OperationListener<ProductBacklogOperation> listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove product backlog data change listener
	 * @param listener listener to remove
	 */
	public void removeProductBacklogListner(OperationListener<ProductBacklogOperation> listener)
	{
		_operation.removeListener(listener);
	}
	
	/// common developer related functionality
	private ProductBacklogModelCommon _productbacklogModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// product backlog table model
	private ProductBacklogTableModel _productbacklogTableModel;
	/// product backlog operation
	private Operation<ProductBacklogOperation> _operation = new Operation<ProductBacklogOperation>();
}
