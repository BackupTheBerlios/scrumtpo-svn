package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import scrummer.model.SprintBacklogModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * SBI combo box model contains id's + task descriptions
 */
public class SBIComboBoxModel extends DefaultComboBoxModel 
{
	/**
	 * Constructor
	 * @param impedimentModelCommon common impediment operations
	 */
	public SBIComboBoxModel(SprintBacklogModelCommon sprintbacklogModelCommon)
	{
		_sprintbacklogModelCommon = sprintbacklogModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshSBIs();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshSBIs()
	{
		//_SBIs = _sprintbacklogModelCommon.fetchSBIsNames();		
	}
	
	/**
	 * Fetch id for specified SBI
	 * 
	 * @param index SBI index
	 * @return SBI id
	 */
	/*public int getId(int index)
	{
		return _SBIs.get(index).Id;
	}*/
	
	/*@Override
	public Object getElementAt(int index) {
		return _SBIs.get(index).Value;
	}*/

	/*@Override
	public int getSize() {
		return _SBIs.size();
	}*/

	/// PBI list
	//private Vector<IdsValue> _SBIs = new Vector<IdsValue>();
	/// common product backlog operations
	private SprintBacklogModelCommon _sprintbacklogModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
