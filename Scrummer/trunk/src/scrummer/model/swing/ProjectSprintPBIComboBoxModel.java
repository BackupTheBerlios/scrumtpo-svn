package scrummer.model.swing;

import java.util.Vector;

import scrummer.model.ProductBacklogModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * All PBI's on given project and sprint
 */
public class ProjectSprintPBIComboBoxModel extends IdValueComboBoxModel {

	/**
	 * Constructor
	 * 
	 * @param productbacklogModelCommon product backlo model comon ops
	 */
	public ProjectSprintPBIComboBoxModel(ProductBacklogModelCommon productbacklogModelCommon) {
		_productBacklogModelCommon = productbacklogModelCommon;
	}
	
	/**
	 * @param value project id
	 */
	public void setProject(int value) {
		_projectId = value;
	}
	
	/**
	 * @param value sprint id
	 */
	public void setSprint(int value) {
		_sprintId = value;
	}
	
	@Override
	public void refresh() {
		refreshRows();
		super.refresh();
	}

	private void refreshRows() {
		setValues(_productBacklogModelCommon.fetchPBIsNames(_projectId, _sprintId));
	}

	/// project id
	private int _projectId;
	/// sprint id
	private int _sprintId;
	/// common product backlog model
	private ProductBacklogModelCommon _productBacklogModelCommon;
	/// serialization id
	private static final long serialVersionUID = 2591784811933201815L;
}
