package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove team dialog
 */
public class TeamRemoveDialog extends TwoButtonDialog {

	/**
	 * Constructor
	 * 
	 * @param owner parent frame
	 */
	public TeamRemoveDialog(Frame owner) {
		super(owner);
		
		setTitle(i18n.tr("Remove team"));
		
		int k = 10;
		Panel.setLayout(new GridLayout(1,2));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel teamLbl = new JLabel(i18n.tr("Team") + ":");
		JComboBox teamInput = new JComboBox();
		
		Panel.add(teamLbl);
		Panel.add(teamInput);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 6));
		
		OK.setText("Remove");
		setSize(new Dimension(250, 125));
	}
	
	/// serialization id
	private static final long serialVersionUID = 9057417391317485126L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}
