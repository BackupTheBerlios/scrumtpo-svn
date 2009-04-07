package scrummer.model.swing;

import scrummer.model.DeveloperModelCommon;
import scrummer.model.swing.base.IdValueListModel;

/**
 * Base class for developer lists
 */
public class DeveloperTeamBaseListModel extends IdValueListModel {

	/**
	 * Constructor
	 * 
	 * @param teamId team id
	 * @param developerModelCommon developer data ops.
	 */
	public DeveloperTeamBaseListModel(int teamId, DeveloperModelCommon developerModelCommon) {
		_teamId = teamId;
		_developerModelCommon = developerModelCommon;
	}

	/**
	 * Refresh data in this model
	 */
	public void refresh()
	{
		fireContentsChanged(this, 0, size() - 1);
	}
	
	/**
	 * @return team id
	 */
	public int getTeam()
	{
		return _teamId;
	}
	
	/**
	 * Set new team id
	 */
	public void setTeam(int id)
	{
		_teamId = id;
		refresh();
	}
	
	/**
	 * Fetch developer model
	 * 
	 * @return developer model
	 */
	protected DeveloperModelCommon getDeveloperModelCommon()
	{
		return _developerModelCommon;
	}
	
	/// team id
	private int _teamId;
	/// common personell operations
	private DeveloperModelCommon _developerModelCommon;
	/// serialization id
	private static final long serialVersionUID = -2022165024472847L;
}
