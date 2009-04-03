package scrummer.ui.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.ui.ListInterchangePanel;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;

/**
 * Developer transfer dialog
 * 
 * Allows transfer of developer from pool to current project
 */
public class TransferDeveloperPage extends JPanel {

	/**
	 * Default constructor
	 */
	public TransferDeveloperPage() {
		super();
		
		setBackground(Color.WHITE);
		setLayout(new GridBagLayout());
		
		add(new ListInterchangePanel(
			new GridLayout(1,1), 
			i18n.tr("Employee"), 
			i18n.tr("Project Employee")));
	}
	
	/**
	 * Create list for use in this control 
	 * @return created list
	 */
	private JList createList()
	{
		JList ret = new JList();
		ret.setLayoutOrientation(JList.VERTICAL_WRAP);
		ret.setBorder(
			BorderFactory.createCompoundBorder(				
				BorderFactory.createLineBorder(Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);

		ret.setFixedCellWidth(100);
		ret.setFixedCellHeight(20);
		ret.setVisibleRowCount(5);
		
		return ret;
	}

	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}
