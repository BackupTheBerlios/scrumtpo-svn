package scrummer.model.swing.base;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

/**
 * Combo box model that offers true and false selection
 */
public class BooleanComboBoxModel extends DefaultComboBoxModel {

	/**
	 * Constructor
	 * 
	 * @param trueString true string values
	 * @param falseString false string value
	 */
	public BooleanComboBoxModel(String trueString, String falseString) {
		_values.add(trueString);
		_values.add(falseString);
	}
	
	@Override
	public Object getElementAt(int index) {
		return _values.get(index);
	}

	@Override
	public int getSize() {
		return 2;
	}

	/// true and false
	private Vector<String> _values = new Vector<String>();
	/// serialization id
	private static final long serialVersionUID = 6856946815535395844L;
}
