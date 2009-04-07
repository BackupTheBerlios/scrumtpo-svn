package scrummer.model.swing;

import scrummer.model.DeveloperModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Team combo box model contains id's + team names
 */
public class TeamComboBoxModel extends IdValueComboBoxModel {

	/**
	 * Constructor
	 * @param developerModelCommon common developer operations
	 */
	public TeamComboBoxModel(DeveloperModelCommon developerModelCommon)
	{
		_developerModelCommon = developerModelCommon;
	}
	
	/**
	 * Refresh data from databse
	 */
	public void refresh()
	{
		refreshTeams();
		super.refresh();

	}
	
	private void refreshTeams()
	{
		setValues(_developerModelCommon.fetchTeamNames());		
	}

	/// common developer operations
	private DeveloperModelCommon _developerModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;
}
