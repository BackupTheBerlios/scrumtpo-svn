package scrummer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import scrummer.Scrummer;
import scrummer.listener.NavigationListener;
import scrummer.model.NavigationModel;
import scrummer.model.NavigationModel.Link;

/**
 * Navigation grid is a panel that contains a fixed grid i.e. it doesn't scale
 * if user resizes it and stays at centre at all times.
 */
public class NavigationGridPanel extends JPanel implements MouseListener, NavigationListener {
	
	/**
	 * Default constructor
	 */
	public NavigationGridPanel() {
		super();
		
		// navigation model
		_navigationModel = Scrummer.getModels().getNavigationModel();
		_navigationModel.addConnectionListener(this);
		setLayout(new GridLayout(1,1));
		
		_panel = new Box(BoxLayout.Y_AXIS);
		add(_panel);
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

	@Override
	public void pageChanged(NavigationModel.Link newLink) {
		// remove all components from panel
		_panel.removeAll();
		// add header
		// _panel.setLayout();
		
		Box header = createHeader("Test");
		// GridBagConstraints headerC = Util.constraint(GridBagConstraints.BOTH, 1.0, 1.0);
		header.setPreferredSize(new Dimension(1600, 150));
		header.setMaximumSize(new Dimension(1600, 150));
		_panel.add(header); // , headerC);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.PINK);		
		
		// GridBagConstraints bottomC = Util.constraint(GridBagConstraints.BOTH, 1.0, 3.0);
		// bottomC.gridy = 1;
		_panel.add(bottomPanel); // ), bottomC);
		
		if (newLink == Link.Blank)
		{
			showBlank(bottomPanel);
		}
		else if (newLink == Link.Overview)
		{
			showOverview(bottomPanel);
		}
		else if (newLink == Link.ProductBacklog)
		{
			showProductBacklog(bottomPanel);
		}
		else if (newLink == Link.Project)
		{
			showProject(bottomPanel);
		}
		else if (newLink == Link.ProjectDevelopers)
		{
			showProjectDevelopers(bottomPanel);
		}
		else if (newLink == Link.ProjectOptions)
		{
			showProjectOptions(bottomPanel);
		}
		else if (newLink == Link.SprintBacklog)
		{
			showSprintBacklog(bottomPanel);
		}
		else if (newLink == Link.SprintBacklogAbsent)
		{
			showSprintBacklogAbsent(bottomPanel);
		}
		else if (newLink == Link.SprintBacklogHurdles)
		{
			showSprintBacklogHurdles(bottomPanel);
		}
		else if (newLink == Link.SprintBacklogMetric)
		{
			showSprintBacklogMetric(bottomPanel);
		}
		else if (newLink == Link.SprintBacklogTasks)
		{
			showSprintBacklogTasks(bottomPanel);
		}
		
		validate();
		_panel.repaint();
		repaint();
	}
	
	private void showSprintBacklogTasks(JPanel panel) {}

	private void showSprintBacklogMetric(JPanel panel) {}

	private void showSprintBacklogHurdles(JPanel panel) {}

	private void showSprintBacklogAbsent(JPanel panel) {}

	private void showSprintBacklog(JPanel panel) {}

	private void showProjectOptions(JPanel panel) {}

	private void showProjectDevelopers(JPanel panel) {}

	private void showProject(JPanel panel) {}

	private void showProductBacklog(JPanel panel) {}

	/**
	 * Show project, sprint backlog, product backlog links
	 * @param panel panel to manipulate
	 */
	private void showOverview(JPanel panel) {}

	/**
	 * Create header panel with two links(back, home)
	 * and current section title
	 * @param title section title
	 * @return created header
	 */
	private Box createHeader(String title)	
	{
		Box header = new Box(BoxLayout.Y_AXIS);
		
		JPanel linkPanel = new JPanel();
		linkPanel.setBackground(Color.BLUE);
		linkPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		linkPanel.setMinimumSize(new Dimension(500, 50));
		linkPanel.setPreferredSize(new Dimension(200, 50));
		
		// add back, up, home connection
		// GrowingLabel backLabel = new GrowingLabel(new Image());
		
		
		/*
		GridBagConstraints linkPanelC = Util.constraint(GridBagConstraints.BOTH, 1.0, 1.0);
		linkPanelC.gridy = 0;
		*/
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.setBackground(Color.YELLOW);
		titlePanel.setMinimumSize(new Dimension(500, 50));
		
		/*
		GridBagConstraints titlePanelC = Util.constraint(GridBagConstraints.BOTH, 1.0, 1.0);
		titlePanelC.gridy = 1;
		*/
		
		JLabel titleLabel = new TitleLabel(title);
		titleLabel.setSize(200, 30);
		titleLabel.setPreferredSize(new Dimension(200, 30));
		
		/*
		GridBagConstraints titleLabelC = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 1.0);
		titleLabelC.anchor = GridBagConstraints.WEST;
		titleLabelC.gridy = 0;
		*/
		
		titlePanel.add(titleLabel); // , titleLabelC);
		
		header.add(linkPanel);  // ,  linkPanelC);
		header.add(titlePanel); // , titlePanelC);
		
		return header;
	}
	
	/**
	 * Do nothing stub
	 * @param panel panel to manipulate
	 */
	private void showBlank(JPanel panel) {}

	@Override
	public void setVisible(boolean flag) {
		_navigationModel.removeConnectionListener(this);
		super.setVisible(flag);
	}

	/// grid panel
    private Box _panel;
	/// navigation model
	private NavigationModel _navigationModel; 
	/// serialization id
	private static final long serialVersionUID = -2270654969033851818L;
}
