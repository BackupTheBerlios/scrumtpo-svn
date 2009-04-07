package scrummer.model.swing;

import scrummer.model.ImpedimentModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Impediment combo box model contains id's + impediment descriptions
 */
public class ImpedimentComboBoxModel extends IdValueComboBoxModel 
{
	/**
	 * Constructor
	 * @param impedimentModelCommon common impediment operations
	 */
	public ImpedimentComboBoxModel(ImpedimentModelCommon impedimentModelCommon)
	{
		_impedimentModelCommon = impedimentModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshImpediments();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshImpediments()
	{
		setValues(_impedimentModelCommon.fetchImpedimentNames());		
	}

	/// common impediment operations
	private ImpedimentModelCommon _impedimentModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
