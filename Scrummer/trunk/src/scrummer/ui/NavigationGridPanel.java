package scrummer.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Navigation grid is a panel that contains a fixed grid i.e. it doesn't scale
 * if user resizes it and stays at centre at all times.
 */
public class NavigationGridPanel extends JPanel implements MouseListener {
	
	/**
	 * Default constructor
	 */
	public NavigationGridPanel() {
		super();
		
		setLayout(new GridBagLayout());
		
		int k = 4;
		int cellWidth = 128;
		int cellHeight = 160;
		
		int width = k * cellWidth;
		int height = k * cellHeight;
		
		Panel = new JPanel();
		Panel.setLayout(new GridBagLayout());
		
		int panelAdd = 10;
	
		limitSize(Panel, width - panelAdd, height - panelAdd);
		
		for (int i = 0; i < 6; i++)
		{
			ImageTextPanel iep = new ImageTextPanel();
			iep.setName(new Integer(i).toString());
			iep.addLabelMouseListener(this);
			iep.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
			
			GridBagConstraints gc = Util.constraint(GridBagConstraints.BOTH, 1.0, 1.0);
			gc.fill = GridBagConstraints.BOTH;
			gc.gridx = i % 2; gc.gridy = i % 3;
			
			Panel.add(iep, gc);
		}
		
		JScrollPane scrollPane = new JScrollPane(Panel);
		// compensate for scrollbar width
		int add = 10;
		limitSize(scrollPane, width + add, height + add);
		GridBagConstraints gc = Util.constraint(GridBagConstraints.CENTER, 1.0, 1.0);		
		
		add(scrollPane, gc);
	}
	
	/**
	 * Set min, max and preferred size
	 * @param pane pane for which to set width and height
	 * @param width width
	 * @param height height
	 */
	private void limitSize(JComponent pane, int width, int height)
	{
		pane.setMinimumSize(new Dimension(width, height));
		pane.setPreferredSize(new Dimension(width, height));
		pane.setMaximumSize(new Dimension(width, height));			
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	/// grid panel
	public JPanel Panel;
	/// serialization id
	private static final long serialVersionUID = -2270654969033851818L;
}
