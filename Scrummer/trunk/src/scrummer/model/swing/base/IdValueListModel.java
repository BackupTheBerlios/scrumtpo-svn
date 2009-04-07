package scrummer.model.swing.base;

import java.util.Vector;
import javax.swing.DefaultListModel;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * A list model, that contains IdValue instances and shows only Value part of the class.
 * 
 * To use it derive a class from it and override refresh. Don't forget to call super.refresh()
 */
public class IdValueListModel extends DefaultListModel {

	/**
	 * Constructor
	 */
	public IdValueListModel() {}

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
		_values.add(value);
		fireContentsChanged(this, 0, size());
	}
	
	/**
	 * Get id for specified row
	 * @param index row index
	 * @return id
	 */
	public int getId(int index)
	{
		return getValue(index).Id;
	}
	
	public IdValue getValue(int index)
	{
		return _values.get(index);
	}
	
	@Override
	public Object getElementAt(int index) {
		return _values.get(index).Value;
	}
	
	@Override
	public Object get(int index) {
		return getElementAt(index);
	}
	
	@Override
	public boolean removeElement(Object obj) {
		boolean ret = _values.remove(obj);
		fireContentsChanged(this, 0, size());
		return ret;
	}

	@Override
	public void removeElementAt(int index) {
		_values.remove(index);
		fireContentsChanged(this, 0, size());
	}

	@Override
	public void removeAllElements() {
		_values.clear();
		fireContentsChanged(this, 0, size());
	}

	@Override
	public int size() {	
		return _values.size();
	}
	
	@Override
	public int getSize() {
		return size();
	}
	
	/**
	 * @return employees
	 */
	protected Vector<IdValue> getValues()
	{
		return _values;
	}
	
	/**
	 * Set employees
	 * @param value value to set
	 */
	protected void setValues(Vector<IdValue> value)
	{
		_values = value;
	}
	
	/// employee list
	private Vector<IdValue> _values = new Vector<IdValue>();
	/// serialization id
	private static final long serialVersionUID = -2037442284913997969L;
}
