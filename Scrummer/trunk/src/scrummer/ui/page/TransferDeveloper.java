package scrummer.ui.page;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;

/**
 * Developer transfer dialog
 * 
 * Allows transfer of developer from pool to current project
 */
public class TransferDeveloper extends JPanel {

	/**
	 * Default constructor
	 */
	public TransferDeveloper() {
		super();
		
		setLayout(new GridBagLayout());
		
		JList freeDevList = createList();
		GridBagConstraints freeDevListC = Util.constraint(GridBagConstraints.VERTICAL, 2.0, 1.0);
		
		Box midPanel = new Box(BoxLayout.Y_AXIS);
		
		GridBagConstraints midPanelC = Util.constraint(GridBagConstraints.VERTICAL, 1.0, 1.0);
		
		JButton moveRightButton = new StandardButton(">");
		moveRightButton.setActionCommand("MoveRight");
		
		JButton moveLeftButton = new StandardButton("<");
		moveLeftButton.setActionCommand("MoveLeft");
		
		midPanel.add(moveRightButton);
		midPanel.add(moveLeftButton);
		
		JList projectDevList = createList();
		GridBagConstraints projectDevListC = Util.constraint(GridBagConstraints.VERTICAL, 2.0, 1.0);
		
		add(freeDevList,    freeDevListC);
		add(midPanel, 		midPanelC);
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

}
