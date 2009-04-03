package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove some team from database
 */
public class TeamChangeNameDialog extends TwoButtonDialog {

	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public TeamChangeNameDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change team name"));
		
		int k = 10;
		Panel.setLayout(new GridLayout(2,2, 10, 15));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel teamLbl = new JLabel(i18n.tr("Team") + ":");
		JComboBox teamInput = new JComboBox();
		
		Panel.add(teamLbl);
		Panel.add(teamInput);
		
		addEntry(i18n.tr("New name:"), "NewName");
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 4));
		
		OK.setText("Change Name");
		setSize(new Dimension(300, 160));
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

	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}
