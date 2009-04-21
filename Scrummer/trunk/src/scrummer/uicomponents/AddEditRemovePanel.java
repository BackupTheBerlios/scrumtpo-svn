package scrummer.uicomponents;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import scrummer.Scrummer;

/**
 * A panel that displays Add, Edit and Remove buttons
 */
public class AddEditRemovePanel extends JPanel {

	/**
	 * Constructor
	 */
	public AddEditRemovePanel() {
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 1));
		
		JButton addButton = new StandardButton(i18n.tr("Add"));
		addButton.setActionCommand("Add");
		Add = addButton;
		
		JButton editButton = new StandardButton(i18n.tr("Edit"));
		editButton.setActionCommand("Edit");
		Edit = editButton;
		
		JButton removeButton = new StandardButton(i18n.tr("Remove"));
		removeButton.setActionCommand("Remove");
		Remove = removeButton;
		
		add(addButton); add(editButton); add(removeButton);
	}
	
	/**
	 * Add action listener to all buttons
	 * @param listener listener
	 */
	public void addActionListener(ActionListener listener)
	{
		Add.addActionListener(listener);
		Edit.addActionListener(listener);
		Remove.addActionListener(listener);
	}
	
	/**
	 * Remove action listener
	 * @param listener listener
	 */
	public void removeActionListener(ActionListener listener)
	{
		Add.removeActionListener(listener);
		Edit.removeActionListener(listener);
		Remove.removeActionListener(listener);
	}
	
	/// add, remove and edit buttons
	public JButton Add, Remove, Edit;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -7164327400740987574L;	
}
