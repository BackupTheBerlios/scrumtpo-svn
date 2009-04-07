package scrummer.model.swing;

import scrummer.model.DeveloperModelCommon;
import scrummer.model.swing.base.IdValueListModel;

/**
 * Contains all employees
 */
public class EmployeeListModel extends IdValueListModel {

	/**
	 * Constructor
	 */
	public EmployeeListModel(DeveloperModelCommon developerModelCommon) {
		_developerModelCommon = developerModelCommon;
	}
	
	/**
	 * Refresh data in this model
	 */
	public void refresh()
	{
		refreshRows();
		fireContentsChanged(this, 0, size() - 1);
	}
	
	/**
	 * Refresh rows
	 */
	private void refreshRows()
	{
		setValues(_developerModelCommon.fetchDeveloperNames());
	}
	
	/// common employee data operations
	private DeveloperModelCommon _developerModelCommon;
	/// serialization id
	private static final long serialVersionUID = 2319123392937384449L;
}
