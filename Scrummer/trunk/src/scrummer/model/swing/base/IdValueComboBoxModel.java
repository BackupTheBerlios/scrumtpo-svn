package scrummer.model.swing.base;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import scrummer.model.DBSchemaModel.IdValue;

/**
 * A combo box that contains Id String value pairs and returns Value-s
 */
public class IdValueComboBoxModel extends DefaultComboBoxModel {

	public IdValueComboBoxModel() {}

	public IdValueComboBoxModel(Object[] items) {
		super(items);
	}

	public IdValueComboBoxModel(Vector<?> v) {
		super(v);
	}
	
	/**
	 * Refresh values
	 */
	public void refresh() {
		fireContentsChanged(this, 0, getSize());
	}
	
	/**
	 * Fetch id for specified team
	 * 
	 * @param index team index
	 * @return team id
	 */
	public int getId(int index)
	{
		return _values.get(index).Id;
	}
	
	@Override
	public Object getElementAt(int index) {
		return _values.get(index).Value;
	}

	@Override
	public int getSize() {
		return _values.size();
	}

	/**
	 * Set values
	 * @param values combo box values
	 */
	protected void setValues(Vector<IdValue> values)
	{
		_values = values;
	}
	
	/// team list
	private Vector<IdValue> _values = new Vector<IdValue>();
	/// serialization id
	private static final long serialVersionUID = 3227995868538223436L;
}
