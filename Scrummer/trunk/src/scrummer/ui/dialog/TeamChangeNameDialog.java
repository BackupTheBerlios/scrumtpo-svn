package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.listener.DeveloperListener;
import scrummer.listener.OperationListener;
import scrummer.model.DeveloperModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove some team from database
 */
public class TeamChangeNameDialog extends TwoButtonDialog
								  implements DeveloperListener {

	
	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public TeamChangeNameDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change team name"));
		
		_developerModel = Scrummer.getModels().getDeveloperModel();
		_developerModel.addDeveloperListener(this);
		
		_teamComboModel = _developerModel.getTeamComboBoxModel();
		
		int k = 10;
		Panel.setLayout(new GridLayout(2,2, 10, 15));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel teamLbl = new JLabel(i18n.tr("Team") + ":");
		JComboBox teamInput = new JComboBox();
		teamInput.setModel(_teamComboModel);
		_teamInput = teamInput;
		_teamComboModel.refresh();
		
		Panel.add(teamLbl);
		Panel.add(teamInput);
		
		_nameInput = addEntry(i18n.tr("New name") + ":", "NewName");
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 4));
		
		OK.setText("Change Name");
		setSize(new Dimension(320, 160));
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
			String text = _nameInput.getText().trim();
			if (text.length() > 0)
			{
				int selected = _teamInput.getSelectedIndex();
				if (selected != -1)
				{
					int id = _teamComboModel.getId(selected);
					_developerModel.setTeamName(id, text);
				}
				else
				{
					Util.showError(this, i18n.tr("Some team must be selected to change it's name."), i18n.tr("Error"));
				}
			}
			else
			{
				Util.showError(this, i18n.tr("Team must be at least one character long."), i18n.tr("Error"));
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, DeveloperOperation identifier, String message) {
		switch (type)
		{
		case Update:
		
			switch (identifier)
			{
			case TeamName:
				_teamComboModel.refresh();
				_teamInput.setSelectedIndex(0);
				setVisible(false);
				break;
			}
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, DeveloperOperation identifier, String message) {
		
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case TeamName:
				Util.showError(this, 
					i18n.tr("An error has occurred when setting team name") + ": " + message, 
					i18n.tr("Error"));
				break;
			}
			break;
		}
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			_developerModel.removeDeveloperListener(this);
		}
		else
		{
			if (_teamComboModel.getSize() == 0)
			{
				_teamInput.setEnabled(false);
			}
			else
			{
				_teamInput.setEnabled(true);
				_teamInput.setSelectedIndex(0);
			}
		}
		
		super.setVisible(b);
	}

	/// developer model
	private DeveloperModel _developerModel;
	/// all teams in combo box
	private TeamComboBoxModel _teamComboModel;
	/// team new name input
	private JTextField _nameInput;
	/// team input combo box
	private JComboBox _teamInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
