package scrummer.model.swing;

import scrummer.model.DeveloperModelCommon;

/**
 * List model that displays all developers on some team
 */
public class DeveloperTeamListModel extends DeveloperTeamBaseListModel {

	/**
	 * Constructor
	 * 
	 * @param teamId team id
	 * @param developerModelCommon developer data ops.
	 */
	public DeveloperTeamListModel(int teamId, DeveloperModelCommon developerModelCommon) {
		super(teamId, developerModelCommon);
	}

	/**
	 * Refresh data in this model
	 */
	public void refresh()
	{
		refreshRows();
		super.refresh();
	}
	
	/**
	 * Refresh rows
	 */
	private void refreshRows()
	{
		setValues(getDeveloperModelCommon().fetchTeamMembers(getTeam()));
	}
	
	/// serialization id
	private static final long serialVersionUID = 6533415185976124056L;
}
