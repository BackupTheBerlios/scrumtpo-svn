package scrummer.ui.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.listener.DeveloperListener;
import scrummer.model.DeveloperModel;
import scrummer.model.Models;
import scrummer.model.swing.EmployeeListModel;
import scrummer.ui.ListInterchangePanel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedFormattedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add team dialog
 */
public class TeamAddDialog extends TwoButtonDialog
						   implements DeveloperListener {

	/**
	 * Constructor
	 * 
	 * @param parent parent window
	 */
	public TeamAddDialog(JFrame parent)
	{
		super(parent, ModalityType.APPLICATION_MODAL);
		setTitle(i18n.tr("Add Team"));
		
		Models m = Scrummer.getModels();
		_developerModel = m.getDeveloperModel();
		_developerModel.addDeveloperListener(this);
		_employeeListModelLeft = _developerModel.getEmployeeListModelA();
		_employeeListModelRight = _developerModel.getEmployeeListModelB();
		
		Panel.setLayout(new GridBagLayout());
		
		int k = 15;
		
		JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		topPanel.setBorder(BorderFactory.createEmptyBorder(k, k, 0, k));
		GridBagConstraints topPanelC = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 1.0);
		
		JLabel nameLbl = new JLabel(i18n.tr("Team name") + ":");

		SelectedFormattedTextField nameTextInput = new SelectedFormattedTextField();
		nameTextInput.setColumns(14);
		_teamNameInput = nameTextInput;
		
		topPanel.add(nameLbl);		
		topPanel.add(nameTextInput);
		
		ListInterchangePanel interchangePanel = 
			new ListInterchangePanel(
				new GridBagLayout(),
				i18n.tr("Employee"),
				i18n.tr("Team Member"));
		ListPanel = interchangePanel;
		GridBagConstraints interchangePanelC = Util.constraint(GridBagConstraints.BOTH, 1.0, 4.0);
		interchangePanelC.gridy = 1;
		
		Color backColor = Color.getColor("Panel.background");
		
		ListPanel.LeftList.setPreferredSize(new Dimension(200, 140));
		ListPanel.LeftList.setBackground(backColor);
		ListPanel.MiddlePanel.setPreferredSize(new Dimension(20, 140));
		ListPanel.MiddlePanel.setBackground(backColor);
		ListPanel.RightList.setPreferredSize(new Dimension(200,140));
		ListPanel.RightList.setBackground(backColor);
		
		Panel.add(topPanel, topPanelC);
		Panel.add(interchangePanel, interchangePanelC);
		
		OK.setText(i18n.tr("Add"));
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(k - 5,k,k - 5,k - 5));
		
		ListPanel.LeftList.setModel(_employeeListModelLeft);
		_employeeListModelLeft.refresh();
		ListPanel.LeftList.invalidate();
		
		_employeeListModelRight.removeAllElements();
		ListPanel.RightList.setModel(_employeeListModelRight);
		
		ListPanel.MoveLeftButton.setActionCommand("MoveLeft");
		ListPanel.MoveLeftButton.addActionListener(this);
		
		ListPanel.MoveRightButton.setActionCommand("MoveRight");
		ListPanel.MoveRightButton.addActionListener(this);
		
		setSize(new Dimension(440, 280));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "MoveLeft")
		{
			if (!ListPanel.RightList.isSelectionEmpty())
			{
				// check for selection in right model
				int [] selection = ListPanel.RightList.getSelectedIndices();
				_developerModel.moveBtoA(selection);
			}
		}
		else if (e.getActionCommand() == "MoveRight")
		{
			if (!ListPanel.LeftList.isSelectionEmpty())
			{
				// check for selection in right model
				int [] selection = ListPanel.LeftList.getSelectedIndices();
				_developerModel.moveAtoB(selection);
			}
		}
		else if (e.getActionCommand() == "StandardDialog.OK")
		{
			String text = _teamNameInput.getText().trim();
			if (text.length() == 0)
			{
				JOptionPane.showMessageDialog(
				    this, 
				    i18n.tr("Team name must contain at least one character."), 
				    i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				_developerModel.addTeamB(text);
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, DeveloperOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			switch (identifier)
			{
			case Team:
				JOptionPane.showMessageDialog(
					this, 
					i18n.tr("Could not delete team: " + message), 
					i18n.tr("Error"), 
					JOptionPane.ERROR_MESSAGE);
				break;
			}			
			break;
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, DeveloperOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			switch (identifier)
			{
			case Team:	
				setVisible(false);
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
	/// employee list model
	private EmployeeListModel _employeeListModelLeft, _employeeListModelRight;
	/// list interchange panel
	private ListInterchangePanel ListPanel;
	/// team name
	private JTextField _teamNameInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass()); 
	/// serialization id
	private static final long serialVersionUID = -1026179270145279297L;
}
