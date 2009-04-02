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
		
		int k = 10;
		
		JList freeDevList = createList();
		freeDevList.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Employees"), 
				k, k, k, k));
		freeDevList.setPreferredSize(new Dimension(200,200));
		
		GridBagConstraints freeDevListC = Util.constraint(GridBagConstraints.VERTICAL, 2.0, 1.0);

		JPanel midPanel = new JPanel(new GridLayout(2,1));
		midPanel.setBackground(Color.WHITE);
		midPanel.setPreferredSize(new Dimension(200, 200));
		midPanel.setAlignmentY(CENTER_ALIGNMENT);
		
		GridBagConstraints midPanelC = Util.constraint(GridBagConstraints.VERTICAL, 1.0, 1.0);
		
		Box moveRightBox = new Box(BoxLayout.Y_AXIS);
		moveRightBox.setMaximumSize(new Dimension(200,200));
				
		JButton moveRightButton = new StandardButton(">");
		moveRightButton.setSize(new Dimension(60,40));
		moveRightButton.setActionCommand("MoveRight");
		moveRightButton.setAlignmentY(TOP_ALIGNMENT);
		
		Box innerMoveRightBox = new Box(BoxLayout.X_AXIS);
		innerMoveRightBox.setMaximumSize(new Dimension(40,200));
		innerMoveRightBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		innerMoveRightBox.add(moveRightButton);
		moveRightBox.add(innerMoveRightBox);
		
		Box moveLeftBox = new Box(BoxLayout.Y_AXIS);
		moveLeftBox.setMaximumSize(new Dimension(200, 200));
		
		Box innerMoveLeftBox = new Box(BoxLayout.X_AXIS);
		innerMoveLeftBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		innerMoveLeftBox.setMaximumSize(new Dimension(40, 200));
		
		JButton moveLeftButton = new StandardButton("<");
		moveLeftButton.setSize(new Dimension(60, 40));
		moveLeftButton.setAlignmentY(BOTTOM_ALIGNMENT);
		moveLeftButton.setActionCommand("MoveLeft");		
		
		innerMoveLeftBox.add(moveLeftButton);
		moveLeftBox.add(innerMoveLeftBox);
		
		midPanel.add(moveLeftBox);
		midPanel.add(moveRightBox);
		
		JList projectDevList = createList();
		projectDevList.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Project Employees"), 
				k, k, k, k));
		projectDevList.setPreferredSize(new Dimension(200,200));
		GridBagConstraints projectDevListC = Util.constraint(GridBagConstraints.VERTICAL, 2.0, 1.0);
		
		add(freeDevList,    freeDevListC);
		add(midPanel,       midPanelC);
		add(projectDevList, projectDevListC);
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
