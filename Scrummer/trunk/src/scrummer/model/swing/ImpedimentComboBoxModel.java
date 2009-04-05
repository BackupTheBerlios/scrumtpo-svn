package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import scrummer.model.ImpedimentModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * Impediment combo box model contains id's + impediment descriptions
 */
public class ImpedimentComboBoxModel extends DefaultComboBoxModel 
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
		_impediments = _impedimentModelCommon.fetchImpedimentNames();		
	}
	
	/**
	 * Fetch id for specified team
	 * 
	 * @param index team index
	 * @return team id
	 */
	public int getId(int index)
	{
		return _impediments.get(index).Id;
	}
	
	@Override
	public Object getElementAt(int index) {
		return _impediments.get(index).Value;
	}

	@Override
	public int getSize() {
		return _impediments.size();
	}

	/// impediment list
	private Vector<IdValue> _impediments = new Vector<IdValue>();
	/// common impediment operations
	private ImpedimentModelCommon _impedimentModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
