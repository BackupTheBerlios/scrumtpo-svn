package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import scrummer.model.SprintBacklogModelCommon;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * SBI combo box model contains id's + task descriptions
 */
public class SBIComboBoxModel extends IdValueComboBoxModel 
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

	/// common product backlog operations
	private SprintBacklogModelCommon _sprintbacklogModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
