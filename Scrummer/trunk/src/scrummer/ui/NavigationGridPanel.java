package scrummer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
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
		
		Box header = createHeader(_navigationModel.getName(newLink));
		header.setPreferredSize(new Dimension(1600, 110));
		header.setMaximumSize(new Dimension(1600, 110));
		_panel.add(header);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);		
		
		_panel.add(bottomPanel);
		
		switch (newLink)
		{
		case Blank: 
			showBlank(bottomPanel); 
			break;
		case Overview:
			showOverview(bottomPanel); 
			break;
		case ProductBacklog:
			showProductBacklog(bottomPanel); 
			break;
		case Project:
			showProject(bottomPanel); 
			break;
		case ProjectDevelopers:
			showProjectDevelopers(bottomPanel); 
			break;
		case ProjectOptions:
			showProjectOptions(bottomPanel); 
			break;
		case SprintBacklog:
			showSprintBacklog(bottomPanel); 
			break;
		case SprintBacklogAbsent:
			showSprintBacklogAbsent(bottomPanel); 
			break;
		case SprintBacklogHurdles:
			showSprintBacklogHurdles(bottomPanel);
			break;
		case SprintBacklogMetric:
			showSprintBacklogMetric(bottomPanel);
			break;
		case SprintBacklogTasks:
			showSprintBacklogTasks(bottomPanel);
			break;
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
	private void showOverview(JPanel panel) {
		
		ResourceModel res = Scrummer.getModels().getResourceModel();
		
		panel.setLayout(new BorderLayout());
		
		JPanel box = new JPanel();
		box.setLayout(new FlowLayout(FlowLayout.CENTER));
		box.setAlignmentX(CENTER_ALIGNMENT);
		box.setBackground(Color.WHITE);
		
		addLink(box, NavigationModel.Link.Project);
		addLink(box, NavigationModel.Link.ProductBacklog);
		addLink(box, NavigationModel.Link.SprintBacklog);
		
		box.setMaximumSize(new Dimension(1600, 100));
		
		Box vertBox = new Box(BoxLayout.X_AXIS);
		vertBox.add(box);
		
		panel.add(vertBox);
	}

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
		
		addLabel(linkPanel, ResourceModel.Image.ArrowLeft, TitleLink.Endpoint.Back, 2);
		addLabel(linkPanel, ResourceModel.Image.ArrowUp,   TitleLink.Endpoint.Up, 2);
		addLabel(linkPanel, ResourceModel.Image.Home,      TitleLink.Endpoint.Home, 0);
				
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
	 * @param endpoint type of link
	 * @param addBottomText additional value to add to bottom text offset
	 */
	private void addLabel(JPanel panel, ResourceModel.Image image, TitleLink.Endpoint endpoint, int addBottomText)
	{
		ResourceModel res = Scrummer.getModels().getResourceModel();	
		// add back, up, home connection
		try {
			GrowingLabel label = new TitleLink(endpoint, res.get(image));
			label.setBorderGrowth(0, 8, 8);
			label.setPictureSideOffset(5);
			label.setPictureTopOffset(5);
			label.setTextBottomOffset(4 + addBottomText);
			label.setPreferredSize(new Dimension(64,76));
			// label.setText(text);
			panel.add(label);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add link to some container
	 * @param component container
	 * @param link link to which it points
	 */
	private void addLink(JComponent component, NavigationModel.Link link)
	{
		ResourceModel res = Scrummer.getModels().getResourceModel();
		try {
			scrummer.ui.Link linkControl = 
				new scrummer.ui.Link(
					link,
					res.get(ResourceModel.Image.Sun));
			linkControl.setBorderGrowth(0, 8, 8);
			linkControl.setPictureSideOffset(36);
			linkControl.setPictureTopOffset(5);
			linkControl.setPreferredSize(new Dimension(64 + 72,76));
			linkControl.setMaximumSize(new Dimension(64 + 72,76));
			
			component.add(linkControl);
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
