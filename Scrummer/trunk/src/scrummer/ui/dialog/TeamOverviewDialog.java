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

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.ui.ListInterchangePanel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedFormattedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Team overview dialog makes team editing easier
 */
public class TeamOverviewDialog extends TwoButtonDialog {

	/**
	 * Constructor
	 * @param owner owner frame
	 */
	public TeamOverviewDialog(Frame owner) {		
		super(owner, ModalityType.APPLICATION_MODAL);
		setTitle(i18n.tr("Add Team"));
		
		Panel.setLayout(new GridBagLayout());
		
		int k = 15;
		
		JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		topPanel.setBorder(BorderFactory.createEmptyBorder(k, k, 0, k));
		GridBagConstraints topPanelC = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 1.0);
		
		/*
		JLabel nameLbl = new JLabel(i18n.tr("Team name") + ":");

		SelectedFormattedTextField nameTextInput = new SelectedFormattedTextField();
		nameTextInput.setColumns(14);
		*/
		
		JLabel teamLbl = new JLabel(i18n.tr("Team") + ":");
		JComboBox teamCbbx = new JComboBox();
		
		topPanel.add(teamLbl);		
		topPanel.add(teamCbbx);
		
		/*
		topPanel.add(nameLbl);		
		topPanel.add(nameTextInput);
		*/
		
		ListInterchangePanel interchangePanel = 
			new ListInterchangePanel(
				new GridBagLayout(),
				i18n.tr("Employee"),
				i18n.tr("Team Member"));
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
		
		setSize(new Dimension(440, 280));
	}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 1110268887633198339L;
}
