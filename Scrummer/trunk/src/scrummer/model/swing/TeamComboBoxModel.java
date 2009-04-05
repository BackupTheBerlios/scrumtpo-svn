package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import scrummer.model.DeveloperModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * Team combo box model contains id's + team names
 */
public class TeamComboBoxModel extends DefaultComboBoxModel {

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
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshTeams()
	{
		_teams = _developerModelCommon.fetchTeamNames();		
	}
	
	/**
	 * Fetch id for specified team
	 * 
	 * @param index team index
	 * @return team id
	 */
	public int getId(int index)
	{
		return _teams.get(index).Id;
	}
	
	@Override
	public Object getElementAt(int index) {
		return _teams.get(index).Value;
	}

	@Override
	public int getSize() {
		return _teams.size();
	}

	/// team list
	private Vector<IdValue> _teams = new Vector<IdValue>();
	/// common developer operations
	private DeveloperModelCommon _developerModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
