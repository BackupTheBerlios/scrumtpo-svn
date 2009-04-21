package scrummer.uicomponents;

import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import scrummer.exception.NullField;
import scrummer.exception.ValueInvalid;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Standard combo box implementation
 * 
 * When a model is set on this combo box, first item is selected automatically.
 */
public class StandardComboBox extends JComboBox {

	public StandardComboBox() {}

	public StandardComboBox(ComboBoxModel model) {
		super(model);
	}

	public StandardComboBox(Object[] items) {
		super(items);
	}

	public StandardComboBox(Vector<?> items) {
		super(items);
	}

	@Override
	public void setModel(ComboBoxModel model) {
		super.setModel(model);
		
		if (model.getSize() > 0)
		{
			setSelectedIndex(0);
			setEnabled(true);
		}		
		else
		{
			setEnabled(false);
		}
	}
	
	/**
	 * Refresh and set model
	 * @param model model to set
	 */
	public void setIVModel(IdValueComboBoxModel model)
	{
		_idValueModel = model;
		model.refresh();
		setModel(model);		
	}

	/**
	 * @return true if an option is selected, false otherwise
	 */
	public boolean isSelected()
	{
		return (getSelectedIndex() != -1);
	}
	
	/**
	 * @return selection id from custom set model
	 * @note you must call setIVModel for this to work
	 */
	public int getSelectedId()
	{
		if (_idValueModel == null)
		{
			throw new NullField("Could not get selection id because IdValue model was not set. " +
					"Call setIVModel before this function.");
		}
		return _idValueModel.getId(getSelectedIndex());
	}
	
	private IdValueComboBoxModel _idValueModel = null;
	/// serialization id
	private static final long serialVersionUID = 7904287195629466132L;
}
