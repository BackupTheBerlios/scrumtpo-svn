package scrummer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import scrummer.Scrummer;
import scrummer.listener.NavigationListener;
import scrummer.model.NavigationModel;
import scrummer.model.ResourceModel;
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
		
		Box header = createHeader("Test");
		header.setPreferredSize(new Dimension(1600, 110));
		header.setMaximumSize(new Dimension(1600, 110));
		_panel.add(header);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.PINK);		
		
		_panel.add(bottomPanel);
		
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
		linkPanel.setBackground(Color.WHITE);
		linkPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		linkPanel.setMinimumSize(new Dimension(500, 50));
		linkPanel.setPreferredSize(new Dimension(200, 50));
		
		addLabel(linkPanel, ResourceModel.Image.ArrowLeft, "Left", 2);
		addLabel(linkPanel, ResourceModel.Image.ArrowUp, "Up", 2);
		addLabel(linkPanel, ResourceModel.Image.Home, "Home", 0);
				
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.setBackground(Color.WHITE);
		titlePanel.setMinimumSize(new Dimension(500, 30));
		titlePanel.setPreferredSize(new Dimension(500, 30));
		titlePanel.setMaximumSize(new Dimension(1600, 30));
		
		JLabel titleLabel = new TitleLabel(title);
		titleLabel.setSize(200, 10);
		titleLabel.setPreferredSize(new Dimension(200, 20));
		
		titlePanel.add(titleLabel);
		
		header.add(linkPanel);
		header.add(titlePanel);
		
		return header;
	}
	
	/**
	 * Add label to panel
	 * @param panel panel to which to add
	 * @param image image that will be displayed on label
	 * @param text text to be written on label
	 * @param addBottomText additional value to add to bottom text offset
	 */
	private void addLabel(JPanel panel, ResourceModel.Image image, String text, int addBottomText)
	{
		ResourceModel res = Scrummer.getModels().getResourceModel();	
		// add back, up, home connection
		try {
			GrowingLabel label = new GrowingLabel(res.get(image));
			label.setBorderGrowth(0, 4, 8);
			label.setPictureSideOffset(5);
			label.setPictureTopOffset(5);
			label.setTextBottomOffset(4 + addBottomText);
			label.setPreferredSize(new Dimension(64,76));
			label.setText(text);
			panel.add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
