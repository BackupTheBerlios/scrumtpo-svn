package scrummer.model.swing;

import java.util.Vector;
import javax.swing.DefaultListModel;
import scrummer.model.DeveloperModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * Base class for developer lists
 */
public class DeveloperTeamBaseListModel extends DefaultListModel {

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
	
	/**
	 * Fetch developer model
	 * 
	 * @return developer model
	 */
	protected DeveloperModelCommon getDeveloperModelCommon()
	{
		return _developerModelCommon;
	}
	
	/**
	 * @return employees
	 */
	protected Vector<IdValue> getEmployees()
	{
		return _employees;
	}
	
	/**
	 * Set employees
	 * @param value value to set
	 */
	protected void setEmployees(Vector<IdValue> value)
	{
		_employees = value;
	}
	
	/// employee list
	private Vector<IdValue> _employees = new Vector<IdValue>();
	/// team id
	private int _teamId;
	/// common personell operations
	private DeveloperModelCommon _developerModelCommon;
	/// serialization id
	private static final long serialVersionUID = -2022165024472847L;
}
