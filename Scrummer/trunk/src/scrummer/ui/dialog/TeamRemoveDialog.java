package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.listener.DeveloperListener;
import scrummer.listener.OperationListener;
import scrummer.model.DeveloperModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove team dialog
 */
public class TeamRemoveDialog extends TwoButtonDialog
							  implements DeveloperListener {

	/**
	 * Constructor
	 * 
	 * @param owner parent frame
	 */
	public TeamRemoveDialog(Frame owner) {
		super(owner);
		
		setTitle(i18n.tr("Remove team"));
		
		_developerModel = Scrummer.getModels().getDeveloperModel();
		_developerModel.addDeveloperListener(this);
		_teamComboBoxModel = _developerModel.getTeamComboBoxModel();
		
		int k = 10;
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		_formBuilder = new FormBuilder(Panel);
		_teamInput = _formBuilder.addComboBoxInput(i18n.tr("Team") + ":");
		_teamInput.setModel(_teamComboBoxModel);
		_teamComboBoxModel.refresh();
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 6));
		
		OK.setText("Remove");
		setSize(new Dimension(250, 125));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			int selected =_teamInput.getSelectedIndex(); 
			if (selected != (-1))
			{
				_developerModel.removeTeam(_teamComboBoxModel.getId(selected));
			}
			else
			{
				JOptionPane.showMessageDialog(
					this, 
					i18n.tr("No team has been selected. Select team then click Remove."), 
					i18n.tr("Error"), 
					JOptionPane.ERROR_MESSAGE);
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
		case Remove:
			switch (identifier)
			{
			case Team:
				_teamComboBoxModel.refresh();
				setVisible(false);
			}
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, DeveloperOperation identifier, String message) {
		switch (type)
		{
		case Remove:
			switch (identifier)
			{
			case Team:
				JOptionPane.showMessageDialog(
					this, 
					i18n.tr("Could not remove team: " + message), 
					i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}	
	}
	
	@Override
	public void setVisible(boolean b) {
	
		if (!b)
		{
			_developerModel.removeDeveloperListener(this);
			_teamInput.setSelectedIndex(-1);
		}
		else
		{
			if (_teamComboBoxModel.getSize() > 0)
			{
				_teamInput.setSelectedIndex(0);
				_teamInput.setEnabled(true);
			}
			else
			{
				_teamInput.setEnabled(false);
			}
		}
		
		super.setVisible(b);
	}

	/// developer model
	private DeveloperModel _developerModel;
	/// team combo box model
	private TeamComboBoxModel _teamComboBoxModel;
	/// combo box input
	private JComboBox _teamInput;
	/// form building class
	private FormBuilder _formBuilder;
	/// serialization id
	private static final long serialVersionUID = 9057417391317485126L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}
