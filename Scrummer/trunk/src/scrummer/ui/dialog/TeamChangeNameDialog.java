package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.listener.DeveloperListener;
import scrummer.model.DeveloperModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
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
		// Panel.setLayout(new GridLayout(2,2, 10, 15));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		_formBuilder = new FormBuilder(Panel);
		_formBuilder.setCellSpacing(10, 15);
		_teamInput = _formBuilder.addComboBoxInput(i18n.tr("Team") + ":");
		_teamInput.setIVModel(_teamComboModel);
		
		_nameInput = _formBuilder.addSelectedTextInput(i18n.tr("New name") + ":", "NewName");
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 4));
		
		OK.setText("Change Name");
		setSize(new Dimension(320, 160));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			String text = _nameInput.getText().trim();
			if (text.length() > 0)
			{
				if (_teamInput.isSelected())
				{
					_developerModel.setTeamName(_teamInput.getSelectedId(), text);
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
		
		super.setVisible(b);
	}

	/// developer model
	private DeveloperModel _developerModel;
	/// all teams in combo box
	private TeamComboBoxModel _teamComboModel;
	/// team new name input
	private JTextField _nameInput;
	/// team input combo box
	private StandardComboBox _teamInput;
	/// form building class
	private FormBuilder _formBuilder;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
