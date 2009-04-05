package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import scrummer.model.DBSchemaModel;
import scrummer.model.DeveloperModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * Employee combo box model contains id's + team names
 */
public class EmployeeComboBoxModel extends DefaultComboBoxModel {

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
		_employees = _developerModelCommon.fetchEmployeeNames();		
	}
	
	/**
	 * Fetch id for specified employee
	 * 
	 * @param index team index
	 * @return employee id
	 */
	public int getId(int index)
	{
		return _employees.get(index).Id;
	}
	
	@Override
	public Object getElementAt(int index) {
		return _employees.get(index).Value;
	}

	@Override
	public int getSize() {
		return _employees.size();
	}

	/// employee list
	private Vector<IdValue> _employees = new Vector<IdValue>();
	/// common developer operations
	private DeveloperModelCommon _developerModelCommon;
}
