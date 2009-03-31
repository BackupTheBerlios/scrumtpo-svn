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
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.DeveloperModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class AddDeveloperDialog extends TwoButtonDialog {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public AddDeveloperDialog(JFrame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add Developer"));
		
		_developerModel = Scrummer.getModels().getDeveloperModel();
		
		Panel.setLayout(new GridLayout(3, 2, 0, 10));
		
		_nameTextField    = addEntry(i18n.tr("Name")    + ":", "Name");
		_surnameTextField = addEntry(i18n.tr("Surname") + ":", "Surname");
		_addressTextField = addEntry(i18n.tr("Address") + ":", "Address");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add Developer"), 
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
			_developerModel.add(
				_nameTextField.getText(), 
				_surnameTextField.getText(), 
				_addressTextField.getText());
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	/// developer model
	private DeveloperModel _developerModel;
	/// name text field
	private JTextField _nameTextField, _surnameTextField, _addressTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
