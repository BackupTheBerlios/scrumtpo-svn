package scrummer.ui.dialog;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.listener.OperationListener;
import scrummer.model.DeveloperModel;
import scrummer.model.swing.DeveloperNonTeamListModel;
import scrummer.model.swing.DeveloperTeamListModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.ui.ListInterchangePanel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedFormattedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Team overview dialog makes team editing easier
 */
public class TeamOverviewDialog extends TwoButtonDialog
								implements ItemListener, 
										   OperationListener<DeveloperOperation> {

	/**
	 * Constructor
	 * @param owner owner frame
	 */
	public TeamOverviewDialog(Frame owner) {		
		super(owner, ModalityType.APPLICATION_MODAL);
		setTitle(i18n.tr("Add Team"));
		
		_developerModel         = Scrummer.getModels().getDeveloperModel();
		_developerModel.addDeveloperListener(this);
		_developerTeamListModel = _developerModel.getDeveloperTeamListModel();
		_developerNonTeamListModel = _developerModel.getDeveloperNonTeamListModel();
		_teamComboBoxModel      = _developerModel.getTeamComboBoxModel();
		
		Panel.setLayout(new GridBagLayout());
		
		int k = 15;
		
		JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		topPanel.setBorder(BorderFactory.createEmptyBorder(k, k, 0, k));
		GridBagConstraints topPanelC = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 1.0);
		
		JLabel teamLbl = new JLabel(i18n.tr("Team") + ":");
		JComboBox teamCbbx = new JComboBox();
		teamCbbx.setModel(_teamComboBoxModel);
		_teamComboBoxModel.refresh();
		teamCbbx.addItemListener(this);
		_teamInput = teamCbbx;
		
		topPanel.add(teamLbl);		
		topPanel.add(teamCbbx);
		
		ListInterchangePanel interchangePanel = 
			new ListInterchangePanel(
				new GridBagLayout(),
				i18n.tr("Employee"),
				i18n.tr("Team Member"));
		ListPanel = interchangePanel;
		GridBagConstraints interchangePanelC = Util.constraint(GridBagConstraints.BOTH, 1.0, 4.0);
		interchangePanelC.gridy = 1;
		
		Color backColor = Color.getColor("Panel.background");
		
		interchangePanel.LeftList.setPreferredSize(new Dimension(200, 140));
		interchangePanel.LeftList.setBackground(backColor);
		interchangePanel.MiddlePanel.setPreferredSize(new Dimension(20, 140));
		interchangePanel.MiddlePanel.setBackground(backColor);
		interchangePanel.RightList.setPreferredSize(new Dimension(200,140));
		interchangePanel.RightList.setBackground(backColor);
		
		Panel.add(topPanel, topPanelC);
		Panel.add(interchangePanel, interchangePanelC);
		
		OK.setText(i18n.tr("Save"));
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(k - 5,k,k - 5,k - 5));
		
		ListPanel.LeftList.setModel(_developerNonTeamListModel);
		ListPanel.RightList.setModel(_developerTeamListModel);
		
		OK.setVisible(false);
		Cancel.setText(i18n.tr("Close"));
		
		ListPanel.MoveLeftButton.addActionListener(this);
		ListPanel.MoveRightButton.addActionListener(this);
		
		setSize(new Dimension(440, 280));
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {		
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			int index = _teamInput.getSelectedIndex();
			if (index != -1)
			{
				int newTeam = _teamComboBoxModel.getId(index);
				_developerTeamListModel.setTeam(newTeam);
				_developerNonTeamListModel.setTeam(newTeam);
					
				ListPanel.LeftList.invalidate();
				ListPanel.RightList.invalidate();
			}
		}
	}
	
	@Override
	public void setVisible(boolean b) {
	
		if (b)
		{
			if (_teamComboBoxModel.getSize() > 0)
			{
				_teamInput.setEnabled(true);
				_teamInput.setSelectedIndex(0);
			}
			else
			{
				_teamInput.setEnabled(false);
			}
		}
		else
		{
			_developerModel.removeDeveloperListener(this);
		}
		
		super.setVisible(b);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		String cmd = e.getActionCommand();
		if (cmd == "MoveRight")
		{
			int selected = ListPanel.LeftList.getSelectedIndex(); 
			if (selected != -1)
			{
				int employeeId = _developerNonTeamListModel.getValue(selected).Id;
				_developerModel.addDeveloperToTeam(employeeId, _developerNonTeamListModel.getTeam());
				
				_developerTeamListModel.refresh();
				_developerNonTeamListModel.refresh();
			}
		}
		else if (cmd == "MoveLeft")
		{
			int selected = ListPanel.RightList.getSelectedIndex(); 
			if (selected != -1)
			{
				int employeeId = _developerTeamListModel.getValue(selected).Id;
				_developerModel.removeDeveloperFromTeam(employeeId, _developerTeamListModel.getTeam());
				
				_developerTeamListModel.refresh();
				_developerNonTeamListModel.refresh();
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, DeveloperOperation identifier, String message) {
		Util.showError(
			this, 
			i18n.tr("An error has occurred when transferring employee from/to team: " + message), 
			i18n.tr("Error"));
	}

	@Override
	public void operationSucceeded(DataOperation type, DeveloperOperation identifier, String message) {}
		
	/// team input box
	private JComboBox _teamInput;
	/// developer model
	private DeveloperModel _developerModel;
	/// all developers not on given team
	private DeveloperNonTeamListModel _developerNonTeamListModel;
	/// current team developer list model
	private DeveloperTeamListModel _developerTeamListModel;
	/// team combo box model
	private TeamComboBoxModel _teamComboBoxModel;
	/// list interchange panel
	private ListInterchangePanel ListPanel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 1110268887633198339L;

	
}
