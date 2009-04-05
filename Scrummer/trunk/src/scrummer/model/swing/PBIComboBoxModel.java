package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import scrummer.model.ProductBacklogModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * PBI combo box model contains id's + PBI descriptions
 */
public class PBIComboBoxModel extends DefaultComboBoxModel 
{
	/**
	 * Constructor
	 * @param impedimentModelCommon common impediment operations
	 */
	public PBIComboBoxModel(ProductBacklogModelCommon productbacklogModelCommon)
	{
		_productbacklogModelCommon = productbacklogModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshPBIs();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshPBIs()
	{
		_PBIs = _productbacklogModelCommon.fetchPBIsNames();		
	}
	
	/**
	 * Fetch id for specified PBI
	 * 
	 * @param index PBI index
	 * @return PBI id
	 */
	public int getId(int index)
	{
		return _PBIs.get(index).Id;
	}
	
	@Override
	public Object getElementAt(int index) {
		return _PBIs.get(index).Value;
	}

	@Override
	public int getSize() {
		return _PBIs.size();
	}

	/// PBI list
	private Vector<IdValue> _PBIs = new Vector<IdValue>();
	/// common product backlog operations
	private ProductBacklogModelCommon _productbacklogModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
