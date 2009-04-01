package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.listener.OperationListener;
import scrummer.model.DeveloperModel;
import scrummer.model.ImpedimentModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class ImpedimentsAddDialog 
	extends TwoButtonDialog
	implements OperationListener<ImpedimentOperation> {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public ImpedimentsAddDialog(JFrame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add Impediment"));
		
		_impedimentModel = Scrummer.getModels().getImpedimentModel();
		
		Panel.setLayout(new GridLayout(3, 2, 0, 10));
		
		_teamTextField    = addEntry(i18n.tr("Name")    + ":", "Name");
		_sprintTextField = addEntry(i18n.tr("Surname") + ":", "Surname");
		_employeeTextField = addEntry(i18n.tr("Address") + ":", "Address");
		_taskTextField = addEntry(i18n.tr("Address") + ":", "Address");
		_descriptionTextField = addEntry(i18n.tr("Address") + ":", "Address");
		_typeTextField = addEntry(i18n.tr("Address") + ":", "Address");
		_statusTextField = addEntry(i18n.tr("Address") + ":", "Address");
		_startTextField = addEntry(i18n.tr("Address") + ":", "Address");
		_endTextField = addEntry(i18n.tr("Address") + ":", "Address");
		_ageTextField = addEntry(i18n.tr("Address") + ":", "Address");
		
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add Impediment"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(270, 210));
	}

	/**
	 * Add form entry(label + textbox)
	 * 
	 * @param labelText label text
	 * @param textActionCmd text action command
	 * @return added text field
	 */
	public JTextField addEntry(String labelText, String textActionCmd)
	{
		JLabel label = new JLabel(labelText);
		
		JTextField textBox = new SelectedTextField();
		
		Panel.add(label);
		Panel.add(textBox);
		
		return textBox;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			_impedimentModel.add(
				Integer.parseInt(_teamTextField.getText()),
				Integer.parseInt(_sprintTextField.getText()), 
				Integer.parseInt(_employeeTextField.getText()), 
				Integer.parseInt(_taskTextField.getText()),
				_descriptionTextField.getText(),
				_typeTextField.getText(),
				_statusTextField.getText(),
				_startTextField.getText(),
				_endTextField.getText(),
				Integer.parseInt(_ageTextField.getText()));
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
			// _impedimentModel.
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
	
	/// impediment model
	private ImpedimentModel _impedimentModel;
	/// name text field
	private JTextField _teamTextField, _sprintTextField, _employeeTextField, _taskTextField, _descriptionTextField, _typeTextField, _statusTextField, _startTextField, _endTextField, _ageTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}