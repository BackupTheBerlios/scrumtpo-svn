package scrummer.model.swing;

import scrummer.model.ProductBacklogModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * PBI combo box model contains id's + PBI descriptions
 */
public class PBIComboBoxModel extends IdValueComboBoxModel 
{
	/**
	 * Constructor
	 * @param impedimentModelCommon common impediment operations
	 */
	public PBIComboBoxModel(ProductBacklogModelCommon productbacklogModelCommon) {
		_productbacklogModelCommon = productbacklogModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh() {
		refreshPBIs();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshPBIs() {
		setValues(_productbacklogModelCommon.fetchPBIsNames());		
	}

	/// common product backlog operations
	private ProductBacklogModelCommon _productbacklogModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;
}
