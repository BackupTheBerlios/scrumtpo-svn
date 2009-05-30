package scrummer.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.ui.Validate;

/**
 * Add developer dialog
 */
public class ImpedimentsAddDialog 
	extends ImpedimentDialog
	 {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public ImpedimentsAddDialog(Frame owner) 
	{
		super(owner);
		setTitle(i18n.tr("Add Impediment"));		
		OK.setText(i18n.tr("Add"));
		setSize(570, 310);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			java.util.Date startI = Validate.date(_startTextField, i18n.tr("Wrong starting date."), this);			 
			java.util.Date endI = Validate.date(_endTextField, i18n.tr("Wrong end date."), this);
			
			int age = 0;
			if (Validate.empty(_ageTextField, this))
			{
				age = Integer.parseInt(_ageTextField.getText());
			}
			
			_impedimentModel.add(
				_teamInput.getSelectedId(), 
				_empInput.getSelectedId(), 
				_taskInput.getSelectedId(),
				_descriptionTextField.getText(),
				_impTypeInput.getSelectedId(),
				_impStatusInput.getSelectedId(),
				(startI != null) ? new java.sql.Date(startI.getTime()) : null,
				(endI != null) ? new java.sql.Date(endI.getTime()) : null,
				age);
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			_impedimentModel.removeImpedimentListener(this);
		}

		super.setVisible(b);
	}

	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			setVisible(false);
			break;
		}
	}

	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
