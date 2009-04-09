package scrummer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import scrummer.Scrummer;
import scrummer.listener.NavigationListener;
import scrummer.model.Models;
import scrummer.model.NavigationModel;
import scrummer.model.ProjectModel;
import scrummer.model.ResourceModel;
import scrummer.ui.page.ProjectPage;
import scrummer.ui.page.TransferDeveloperPage;

/**
 * Navigation grid is a panel that contains a fixed grid i.e. it doesn't scale
 * if user resizes it and stays at centre at all times.
 */
public class NavigationGridPanel extends JPanel implements MouseListener, NavigationListener {
	
	/**
	 * Default constructor
	 * 
	 * @param mainFrame main frame
	 */
	public NavigationGridPanel(MainFrame mainFrame) {
		super();
		
		_mainFrame = mainFrame;
		// navigation model
		Models m = Scrummer.getModels();
		_projectModel = m.getProjectModel();
		_navigationModel = m.getNavigationModel();
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
		_panel.add(header);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);		
		
		_panel.add(bottomPanel);

		if (!_projectModel.isOpened())
		{
			// newLink = NavigationModel.Link.Blank;
			_header.setButtonsEnabled(false);
		}
		else
		{
			_header.setButtonsEnabled(true);
		}
		
		_header.TitleText.setText(_navigationModel.getName(newLink));
		
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

	private void showSprintBacklogHurdles(JPanel panel) 
	{
		
	}

	private void showSprintBacklogAbsent(JPanel panel) {}

	//iteracijski
	private void showSprintBacklog(JPanel panel) {
		
		panel.setLayout(new BorderLayout());
		
		int length = 280;
		// distance between two rows vertically
		int vertAdd = 30;
		
		JPanel box = new JPanel();
		box.setLayout(new GridLayout(2, 2, 0, vertAdd));
		box.setPreferredSize(new Dimension(length, 90));
		box.setMaximumSize(new Dimension(length, 90));
		box.setAlignmentX(CENTER_ALIGNMENT);
		box.setAlignmentY(CENTER_ALIGNMENT);
		box.setBackground(Color.WHITE);
		
		addLink(box, NavigationModel.Link.SprintBacklogTasks);
		addLink(box, NavigationModel.Link.SprintBacklogMetric);
		addLink(box, NavigationModel.Link.SprintBacklogAbsent);
		addLink(box, NavigationModel.Link.SprintBacklogHurdles);
		
		box.setMaximumSize(new Dimension(length, 150 + vertAdd));
		
		Box horCentreBox = new Box(BoxLayout.X_AXIS);
		horCentreBox.setAlignmentX(CENTER_ALIGNMENT);
		horCentreBox.setMaximumSize(new Dimension(length, 1600));
		horCentreBox.add(box);
		
		Box vertBox = new Box(BoxLayout.Y_AXIS);
		vertBox.add(horCentreBox);
		
		panel.add(vertBox, BorderLayout.CENTER);	
	}

	private void showProjectOptions(JPanel panel) {
		panel.setLayout(new GridLayout(1,1));
		ProjectPage page = new ProjectPage(_mainFrame);
		panel.add(page);
	}

	private void showProjectDevelopers(JPanel panel) {
		
		panel.setLayout(new GridLayout(1,1));
		TransferDeveloperPage page = new TransferDeveloperPage();
		panel.add(page);
	}

	private void showProject(JPanel panel) {
		panel.setLayout(new BorderLayout());
		
		JPanel box = new JPanel();
		box.setLayout(new FlowLayout(FlowLayout.CENTER));
		box.setAlignmentX(CENTER_ALIGNMENT);
		box.setBackground(Color.WHITE);
		
		addLink(box, NavigationModel.Link.ProjectDevelopers);
		addLink(box, NavigationModel.Link.ProjectOptions);
		
		box.setMaximumSize(new Dimension(1600, 100));
		
		Box vertBox = new Box(BoxLayout.X_AXIS);
		vertBox.add(box);
		
		panel.add(vertBox);
	}

	//programski
	private void showProductBacklog(JPanel panel) {}

	/**
	 * Show project, sprint backlog, product backlog links
	 * @param panel panel to manipulate
	 */
	private void showOverview(JPanel panel) {
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
		if (_header == null)
		{
			_header = new NavigationGridHeader(BoxLayout.Y_AXIS);
			
			_header.HomeButton.setEnabled(false);		
			_header.LeftButton.setEnabled(false);
			_header.UpButton.setEnabled(false);
			
			_header.setPreferredSize(new Dimension(1600, 110));
			_header.setMaximumSize(new Dimension(1600, 110));
		}
		
		return _header;
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
			BufferedImage image = res.get(ResourceModel.Image.Sun);
			switch (link)
			{
			case Project:
				image = res.get(ResourceModel.Image.Project);
				break;
			case ProductBacklog:
				image = res.get(ResourceModel.Image.ProductBacklog);
				break;
			case SprintBacklog:
				image = res.get(ResourceModel.Image.SprintBacklog);
				break;
			}
			
			scrummer.ui.Link linkControl = 
				new scrummer.ui.Link(
					link,
					image);
			linkControl.setBorderGrowth(0, 8, 8);
			linkControl.setPictureSideOffset(36);
			linkControl.setPictureTopOffset(5);
			linkControl.setPreferredSize(new Dimension(64 + 72,76));
			linkControl.setMaximumSize(new Dimension(64 + 72,76));
			linkControl.setTextBottomOffset(4);
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

	/// main frame
	private MainFrame _mainFrame;
	/// grid panel
    private Box _panel;
    /// title label
    private JLabel _titleLabel;
    /// header box
    private NavigationGridHeader _header = null;
    /// project model
    private ProjectModel _projectModel;
	/// navigation model
	private NavigationModel _navigationModel; 
	/// serialization id
	private static final long serialVersionUID = -2270654969033851818L;
}
