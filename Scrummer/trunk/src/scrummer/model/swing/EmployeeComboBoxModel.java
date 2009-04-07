package scrummer.model.swing;

import scrummer.model.DeveloperModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Employee combo box model contains id's + team names
 */
public class EmployeeComboBoxModel extends IdValueComboBoxModel {

	/**
	 * Constructor
	 * @param developerModelCommon common developer operations
	 */
	public EmployeeComboBoxModel(DeveloperModelCommon developerModelCommon)
	{
		_developerModelCommon = developerModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshEmployees();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshEmployees()
	{
		setValues(_developerModelCommon.fetchDeveloperNames());		
	}

	/// common developer operations
	private DeveloperModelCommon _developerModelCommon;
	/// serialization id
	private static final long serialVersionUID = 1501362735692796702L;
}
