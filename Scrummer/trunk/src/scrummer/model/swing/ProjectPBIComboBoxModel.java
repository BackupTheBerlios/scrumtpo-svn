package scrummer.model.swing;

import java.util.Vector;

import scrummer.model.ProductBacklogModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * All pbi's on current project
 */
public class ProjectPBIComboBoxModel extends IdValueComboBoxModel {

	public ProjectPBIComboBoxModel(ProductBacklogModelCommon productBacklogModelCommon) {
		_productbacklogModelCommon = productBacklogModelCommon;
	}

	/**
	 * Refresh data from database
	 */
	public void refresh() {
		refreshPBIs();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshPBIs() {
		setValues(_productbacklogModelCommon.fetchPBIProjectNames(_currentProject));		
	}
	
	/**
	 * Set current project
	 * @param value value to set
	 */
	public void setProject(int value) {
		_currentProject = value;
		refresh();
	}
	
	/// all pbi's on current project
	private int _currentProject;
	/// common product backlog operations
	private ProductBacklogModelCommon _productbacklogModelCommon;
	/// serialization id
	private static final long serialVersionUID = -8046135714351820053L;
}
