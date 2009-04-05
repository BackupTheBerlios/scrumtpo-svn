package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.listener.OperationListener;
import scrummer.model.swing.DeveloperTableModel;
import scrummer.model.swing.ImpedimentComboBoxModel;
import scrummer.model.swing.PBIComboBoxModel;
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
		_productbacklogComboBoxModel = new PBIComboBoxModel(_productbacklogModelCommon);
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
	
	/**
	 * Fetch PBI combo box model
	 * @return model
	 */
	public PBIComboBoxModel getPBIComboBoxModel() {
		return _productbacklogComboBoxModel;
	}
	
	/**
	 * Set new PBI project
	 * 
	 * @param pbiId pbi id
	 * @param newProject project to set
	 */
	public void setPBIProject(int pbiid, String newProject) {
		_productbacklogModelCommon.setPBIProject(pbiid, newProject);
	}
	
	/**
	 * Set new PBI sprint
	 * 
	 * @param pbiId pbi id
	 * @param newSprint sprint to set
	 */
	public void setPBISprint(int pbiid, String newSprint) {
		_productbacklogModelCommon.setPBISprint(pbiid, newSprint);
	}
	
	/**
	 * Set new PBI description
	 * 
	 * @param pbiId pbi id
	 * @param newDesc description to set
	 */
	public void setPBIDesc(int pbiid, String newDesc) {
		_productbacklogModelCommon.setPBIDesc(pbiid, newDesc);
	}
	
	/**
	 * Set new PBI priority
	 * 
	 * @param pbiId pbi id
	 * @param newPriority priority to set
	 */
	public void setPBIPriority(int pbiid, String newPriority) {
		_productbacklogModelCommon.setPBIPriority(pbiid, newPriority);
	}
	
	/**
	 * Set new PBI initial estimate
	 * 
	 * @param pbiId pbi id
	 * @param newIniestimate initial estimate to set
	 */
	public void setPBIIniEstimate(int pbiid, String newIniestimate) {
		_productbacklogModelCommon.setPBIIniEstimate(pbiid, newIniestimate);
	}
	
	/**
	 * Set new PBI adjustment factor
	 * 
	 * @param pbiId pbi id
	 * @param newAdjfactor adjustment factor to set
	 */
	public void setPBIAdjFactor(int pbiid, String newAdjfactor) {
		_productbacklogModelCommon.setPBIAdjFactor(pbiid, newAdjfactor);
	}
	
	/**
	 * Set new PBI adjusted estimate
	 * 
	 * @param pbiId pbi id
	 * @param newAdjestimate adjusted estimate to set
	 */
	public void setPBIAdjEstimate(int pbiid, String newAdjestimate) {
		_productbacklogModelCommon.setPBIAdjEstimate(pbiid, newAdjestimate);
	}
	
	/// PBI combo box model
	private PBIComboBoxModel _productbacklogComboBoxModel;
	/// common developer related functionality
	private ProductBacklogModelCommon _productbacklogModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// product backlog table model
	private ProductBacklogTableModel _productbacklogTableModel;
	/// product backlog operation
	private Operation<ProductBacklogOperation> _operation = new Operation<ProductBacklogOperation>();
}
