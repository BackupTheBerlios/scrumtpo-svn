package scrummer.model;

import java.math.BigDecimal;
import scrummer.listener.ProductBacklogListener;
import scrummer.model.swing.PBIComboBoxModel;
import scrummer.model.swing.ProductBacklogTableModel;
import scrummer.model.swing.ProjectPBIComboBoxModel;
import scrummer.model.swing.ProjectSprintPBIComboBoxModel;
import scrummer.util.Operations;

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
	public ProductBacklogModel(ConnectionModel connectionModel, ProjectModel projectModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_projectModel = projectModel;		
		_productbacklogModelCommon = 
			new ProductBacklogModelCommon(_connectionModel, _operation);
		_productbacklogTableModel = 
			new ProductBacklogTableModel(connectionModel, _productbacklogModelCommon, projectModel);
		_productbacklogComboBoxModel = 
			new PBIComboBoxModel(_productbacklogModelCommon);
		_projectSprintPBIComboBoxModel =
			new ProjectSprintPBIComboBoxModel(_productbacklogModelCommon);
		_projectPBIComboBoxModel =
			new ProjectPBIComboBoxModel(_productbacklogModelCommon);
	}
	
	/**
	 * Sprint backlog model should be set after construction to avoid infinite recursion
	 * @param sprintBacklogModel
	 */
	public void setSprintBacklogModel(SprintBacklogModel sprintBacklogModel) {
		_sprintBacklogModel = sprintBacklogModel;
	}
	
	/**
	 * Insert into Product Backlog
	 * 
	 * @param sprintId current sprint
	 * @param description description of product backlog item
	 * @param priority priority of product backlog item
	 * @param initial_estimate how many hour should be spent on item
	 * @param adjustment_factor ratio of extra hours for item
	 * @param adjusted_estimate max hours spent on item
	 */
	public void add(int sprintId, String description, int priority, BigDecimal initial_estimate, BigDecimal adjustment_factor) {
		int projectId = _projectModel.getCurrentProjectId();
		_productbacklogModelCommon.add(projectId, sprintId, description, priority, initial_estimate, adjustment_factor);
		_productbacklogTableModel.refresh();
	}
	
	public void modify(int pbiId, int sprintId, String description, int priority, BigDecimal initial_estimate, BigDecimal adjustment_factor) {
		int projectId = _projectModel.getCurrentProjectId();
		_productbacklogModelCommon.change(pbiId, projectId, sprintId, description, priority, initial_estimate, adjustment_factor);
		_productbacklogTableModel.refresh();
	}
	
	/**
	 * Product Backlog table model
	 * 
	 * @return product backlog table model
	 */
	public ProductBacklogTableModel getProductBacklogTableModel() {
		return _productbacklogTableModel;
	}

	/**
	 * Fetch all pbi's on current project
	 * @return model
	 */
	public ProjectPBIComboBoxModel getProjectPBIComboBoxModel() {
		int project = _projectModel.getCurrentProjectId();
		_projectPBIComboBoxModel.setProject(project);
		return _projectPBIComboBoxModel;
	}
	
	public ProjectSprintPBIComboBoxModel getProjectSprintPBIComboBoxModel() {
		_projectSprintPBIComboBoxModel.setProject(_projectModel.getCurrentProjectId());
		_projectSprintPBIComboBoxModel.setSprint(_sprintBacklogModel.getCurrentSprint());
		return _projectSprintPBIComboBoxModel; 
	}
	
	/**
	 * Add product backlog data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addProductBacklogListener(ProductBacklogListener listener) {
		_operation.addListener(listener);
	}
	
	/**
	 * Remove product backlog data change listener
	 * @param listener listener to remove
	 */
	public void removeProductBacklogListner(ProductBacklogListener listener) {
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
	
	public Integer getSprint(int productId) {
		return _productbacklogModelCommon.getSprint(productId, _projectModel.getCurrentProjectId());
	}
	
	public String getDescription(int productId)	 {
		return _productbacklogModelCommon.getDescription(productId, _projectModel.getCurrentProjectId());
	}
	
	public BigDecimal getInitialEstimate(int productId) {
		return _productbacklogModelCommon.getInitialEstimate(productId, _projectModel.getCurrentProjectId());
	}
	
	public BigDecimal getAdjustmentFactor(int productId) {
		return _productbacklogModelCommon.getAdjustmentFactor(productId, _projectModel.getCurrentProjectId());
	}
	
	public int getPriority(int productId) {
		return _productbacklogModelCommon.getPriority(productId, _projectModel.getCurrentProjectId());
	}

	/// all pbi's on given project
	private ProjectPBIComboBoxModel _projectPBIComboBoxModel;
	/// PBI combo box model
	private PBIComboBoxModel _productbacklogComboBoxModel;
	/// common developer related functionality
	private ProductBacklogModelCommon _productbacklogModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// product backlog table model
	private ProductBacklogTableModel _productbacklogTableModel;
	/// combo box model of all pbi descriptions on current project and sprint
	private ProjectSprintPBIComboBoxModel _projectSprintPBIComboBoxModel;
	/// project model
	private ProjectModel _projectModel;
	/// sprint backlog model
	private SprintBacklogModel _sprintBacklogModel;
	/// product backlog operation
	private Operations.ProductBacklogOperation _operation = new Operations.ProductBacklogOperation();

}
