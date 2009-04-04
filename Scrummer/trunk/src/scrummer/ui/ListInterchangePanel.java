package scrummer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import scrummer.uicomponents.StandardButton;

/**
 * A panel with two lists and inbetween two arrows left and right
 */
public class ListInterchangePanel extends JPanel {

	/**
	 * Constructor
	 * 
	 * @param layout 
	 * @param leftName 
	 * @param rightName
	 */
	public ListInterchangePanel(LayoutManager layout, String leftName, String rightName) {
		super(layout);
		
		int k = 10;
		JList leftList = new JList();
		LeftList = leftList;
		leftList.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				leftName, 
				k, k, k, k));
		leftList.setPreferredSize(new Dimension(200,200));
		// leftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		GridBagConstraints leftListC = Util.constraint(GridBagConstraints.VERTICAL, 2.0, 1.0);

		JPanel midPanel = new JPanel(new GridLayout(2,1));
		midPanel.setBackground(Color.WHITE);
		midPanel.setPreferredSize(new Dimension(200, 200));
		midPanel.setAlignmentY(CENTER_ALIGNMENT);
		MiddlePanel = midPanel;
		
		GridBagConstraints midPanelC = Util.constraint(GridBagConstraints.VERTICAL, 1.0, 1.0);
		
		Box moveRightBox = new Box(BoxLayout.Y_AXIS);
		moveRightBox.setMaximumSize(new Dimension(200,200));
				
		JButton moveRightButton = new StandardButton(">");
		moveRightButton.setSize(new Dimension(60,40));
		moveRightButton.setActionCommand("MoveRight");
		moveRightButton.setAlignmentY(TOP_ALIGNMENT);
		MoveRightButton = moveRightButton;
		
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
		MoveLeftButton = moveLeftButton;
		
		innerMoveLeftBox.add(moveLeftButton);
		moveLeftBox.add(innerMoveLeftBox);
		
		midPanel.add(moveLeftBox);
		midPanel.add(moveRightBox);
		
		JList rightList = new JList();
		RightList = rightList;
		rightList.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				rightName, 
				k, k, k, k));
		rightList.setPreferredSize(new Dimension(200,200));
		// rightList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		GridBagConstraints rightListC = Util.constraint(GridBagConstraints.VERTICAL, 2.0, 1.0);
		
		add(leftList,  leftListC);
		add(midPanel,  midPanelC);
		add(rightList, rightListC);
	}
	
	/// move left button 
	public JButton MoveLeftButton;
	/// move right button
	public JButton MoveRightButton;
	/// left list
	public JList LeftList;
	/// middle panel
	public JPanel MiddlePanel;
	/// right list
	public JList RightList;
	/// serialization id
	private static final long serialVersionUID = 6473186258303568102L;
}
