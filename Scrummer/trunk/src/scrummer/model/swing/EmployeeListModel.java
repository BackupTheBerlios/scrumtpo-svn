package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultListModel;

import scrummer.model.DeveloperModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * Contains all employees
 */
public class EmployeeListModel extends DefaultListModel {

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
		_employees = _developerModelCommon.fetchDeveloperNames();
	}
	
	/**
	 * Insert given element 
	 * 
	 * @param value value to add
	 */
	public void addValue(IdValue value)
	{
		_employees.add(value);
		fireContentsChanged(this, 0, size());
	}
	
	public IdValue getValue(int index)
	{
		return _employees.get(index);
	}
	
	@Override
	public Object getElementAt(int index) {
		return _employees.get(index).Value;
	}
	
	@Override
	public Object get(int index) {
		return getElementAt(index);
	}
	
	@Override
	public boolean removeElement(Object obj) {
		boolean ret = _employees.remove(obj);
		fireContentsChanged(this, 0, size());
		return ret;
	}

	@Override
	public void removeElementAt(int index) {
		_employees.remove(index);
		fireContentsChanged(this, 0, size());
	}

	@Override
	public void removeAllElements() {
		_employees.clear();
		fireContentsChanged(this, 0, size());
	}

	@Override
	public int size() {
		
		return _employees.size();
	}
	
	@Override
	public int getSize() {
		return size();
	}
	
	/// employee list
	private Vector<IdValue> _employees = new Vector<IdValue>();
	/// common employee data operations
	private DeveloperModelCommon _developerModelCommon;
	/// serialization id
	private static final long serialVersionUID = 2319123392937384449L;
}
