package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class AddDeveloperDialog extends TwoButtonDialog implements FocusListener {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public AddDeveloperDialog(JFrame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add Developer"));
		
		Panel.setLayout(new GridLayout(3, 2, 0, 10));
		
		addEntry(i18n.tr("Name")    + ":", "Name");
		addEntry(i18n.tr("Surname") + ":", "Surname");
		addEntry(i18n.tr("Address") + ":", "Address");
		
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
	 */
	public void addEntry(String labelText, String textActionCmd)
	{
		JLabel label = new JLabel(labelText);
		
		JTextField textBox = new SelectedTextField();
		_textField.add(textBox);
		textBox.addFocusListener(this);
		
		Panel.add(label);
		Panel.add(textBox);
	}
	
	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		if (_textField.contains(e.getComponent()))
		{
			JTextField field = _textField.get(_textField.indexOf(e.getSource())); 
			System.out.println(field.getText());
		}	
	}
	
	private Vector<JTextField> _textField = new Vector<JTextField>();;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
