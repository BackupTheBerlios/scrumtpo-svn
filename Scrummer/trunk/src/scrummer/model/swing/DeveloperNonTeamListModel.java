package scrummer.model.swing;

import scrummer.model.DeveloperModelCommon;

/**
 * Model for all employees that are not on some team
 */
public class DeveloperNonTeamListModel extends DeveloperTeamBaseListModel {

	/**
	 * Constructor
	 * 
	 * @param teamId team id
	 * @param developerModelCommon common developer model
	 */
	public DeveloperNonTeamListModel(int teamId, DeveloperModelCommon developerModelCommon)
	{
		super(teamId, developerModelCommon);
	}

	@Override
	public void refresh() {
	
		refreshRows();
		super.refresh();
	}
	
	/**
	 * Refresh developers
	 */
	private void refreshRows()
	{
		setValues(getDeveloperModelCommon().fetchNonTeamMembers(getTeam()));
	}
	
	/// serialization id
	private static final long serialVersionUID = 4910864066463713374L;
}
