package scrummer.ui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.ProjectListener;
import scrummer.model.Models;
import scrummer.model.ProjectModel;
import scrummer.model.PropertyModel;
import scrummer.ui.dialog.AboutBoxDialog;
import scrummer.ui.dialog.AbsenceTypeAddDialog;
import scrummer.ui.dialog.AbsenceTypeChangeDialog;
import scrummer.ui.dialog.AbsenceTypeRemoveDialog;
import scrummer.ui.dialog.AddDeveloperDialog;
import scrummer.ui.dialog.AdminDaysAddDialog;
import scrummer.ui.dialog.AdminDaysViewDialog;
import scrummer.ui.dialog.DailyScrumMeetingDialog;
import scrummer.ui.dialog.DevelopersViewDialog;
import scrummer.ui.dialog.ImpedimentStatusAdd;
import scrummer.ui.dialog.ImpedimentStatusChange;
import scrummer.ui.dialog.ImpedimentStatusRemove;
import scrummer.ui.dialog.ImpedimentTypeAdd;
import scrummer.ui.dialog.ImpedimentTypeChange;
import scrummer.ui.dialog.ImpedimentTypeRemove;
import scrummer.ui.dialog.ImpedimentsAddDialog;
import scrummer.ui.dialog.ImpedimentsChangeDialog;
import scrummer.ui.dialog.ImpedimentsViewDialog;
import scrummer.ui.dialog.LoginDialog;
import scrummer.ui.dialog.MetricAddDialog;
import scrummer.ui.dialog.MetricChangeDialog;
import scrummer.ui.dialog.MetricRemoveDialog;
import scrummer.ui.dialog.ProjectNewDialog;
import scrummer.ui.dialog.ProjectOpenDialog;
import scrummer.ui.dialog.ProductBacklogAddDialog;
import scrummer.ui.dialog.ProductBacklogViewDialog;
import scrummer.ui.dialog.ProjectRemoveDialog;
import scrummer.ui.dialog.SprintBacklogViewDialog;
import scrummer.ui.dialog.SprintPlanningMeetingDialog;
import scrummer.ui.dialog.TaskStatusAddDialog;
import scrummer.ui.dialog.TaskStatusChangeDialog;
import scrummer.ui.dialog.TaskStatusRemoveDialog;
import scrummer.ui.dialog.TaskTypeAddDialog;
import scrummer.ui.dialog.TaskTypeChangeDialog;
import scrummer.ui.dialog.TaskTypeRemoveDialog;
import scrummer.ui.dialog.TeamAddDialog;
import scrummer.ui.dialog.TeamOverviewDialog;
import scrummer.ui.dialog.TeamChangeNameDialog;
import scrummer.ui.dialog.TeamRemoveDialog;

/**
 * Main application window
 */
public class MainFrame 
	extends JFrame 
	implements ActionListener, WindowListener, ProjectListener {
	/**
	 * Default constructor
	 * @throws HeadlessException
	 */
	public MainFrame() throws HeadlessException 
	{
		super();
		// store local model factory instance
		_models = Scrummer.getModels();
		_projectModel = _models.getProjectModel();
		_projectModel.addProjectListener(this);
		
		// set title
		setTitle(ApplicationName);
		// quit application on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// listen to window close events(to save properties)
		addWindowListener(this);
		// add menu bar, entries for it and event listeners
		addMenu();
		// add navigation grid
		NavigationGridPanel grid = new NavigationGridPanel(this);
		add(grid);
	}
	
	/**
	 * Display login dialog
	 */
	public void showLogin()
	{
		// display login
		LoginDialog dialog = new LoginDialog(this);
		// dialog.pack();
		Util.centre(dialog);
		dialog.setVisible(true);
	}
	
	/**
	 * Menu ui creation
	 */
	private void addMenu()
	{
		JMenuBar bar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(i18n.tr("File"));
		fileMenu.setMnemonic(KeyEvent.VK_F);
		addMenuEntry(fileMenu, i18n.tr("New Project"), 			KeyEvent.VK_N, "NewProject");
		addMenuEntry(fileMenu, i18n.tr("Open Project"), 		KeyEvent.VK_O, "OpenProject");
		addMenuEntry(fileMenu, i18n.tr("Remove Project"), 		KeyEvent.VK_R, "RemoveProject");
		_closeMenuItem = addMenuEntry(fileMenu, i18n.tr("Close Project"), KeyEvent.VK_C, "CloseProject");
		_closeMenuItem.setEnabled(false);
		
		fileMenu.addSeparator();
		
		addMenuEntry(fileMenu, i18n.tr("Insert into product backlog"), 	KeyEvent.VK_A, "AddProductBacklog");
		addMenuEntry(fileMenu, i18n.tr("View product backlog"), KeyEvent.VK_P, "ViewProductBacklog");
		addMenuEntry(fileMenu, i18n.tr("Change product backlog item"), KeyEvent.VK_P, "ChangeProductBacklogItem");
		
		fileMenu.addSeparator();
		addMenuEntry(fileMenu, i18n.tr("Sprint planning meeting"), KeyEvent.VK_S, "SprintPlanMeet");
		addMenuEntry(fileMenu, i18n.tr("View Sprint Backlog"), KeyEvent.VK_B, "ViewSprintBacklog");
		addMenuEntry(fileMenu, i18n.tr("Daily scrum meeting"), KeyEvent.VK_Z, "DailyScrumMeet");
		
		fileMenu.addSeparator();
		
		addMenuEntry(fileMenu, i18n.tr("Exit"), KeyEvent.VK_X, "Exit");
		
		JMenu sifrantiMenu = new JMenu(i18n.tr("Code List"));
		sifrantiMenu.setMnemonic(KeyEvent.VK_0);
		
		addMenuEntry(sifrantiMenu, i18n.tr("Add absence type"), KeyEvent.VK_1, "AddAbsenceType");
		addMenuEntry(sifrantiMenu, i18n.tr("Change absence type"), KeyEvent.VK_2, "ChangeAbsenceType");
		addMenuEntry(sifrantiMenu, i18n.tr("Change absence type"), KeyEvent.VK_2, "ChangeAbsenceType");
		addMenuEntry(sifrantiMenu, i18n.tr("Remove absence type"), KeyEvent.VK_3, "RemoveAbsenceType");
		
		sifrantiMenu.addSeparator();

		addMenuEntry(sifrantiMenu, i18n.tr("Add impediment type"), KeyEvent.VK_I, "AddImpedimentType");
		addMenuEntry(sifrantiMenu, i18n.tr("Change impediment type"), KeyEvent.VK_M, "ChangeImpedimentType");
		addMenuEntry(sifrantiMenu, i18n.tr("Remove impediment type"), KeyEvent.VK_M, "RemoveImpedimentType");

		sifrantiMenu.addSeparator();
		
		addMenuEntry(sifrantiMenu, i18n.tr("Add impediment status"), KeyEvent.VK_I, "AddImpedimentStatus");
		addMenuEntry(sifrantiMenu, i18n.tr("Change impediment status"), KeyEvent.VK_M, "ChangeImpedimentStatus");
		addMenuEntry(sifrantiMenu, i18n.tr("Remove impediment status"), KeyEvent.VK_M, "RemoveImpedimentStatus");
		
		sifrantiMenu.addSeparator();
		
		addMenuEntry(sifrantiMenu, i18n.tr("Add Metric"), KeyEvent.VK_0, "AddMetric");
		addMenuEntry(sifrantiMenu, i18n.tr("Change Metric"), KeyEvent.VK_0, "ChangeMetric");
		addMenuEntry(sifrantiMenu, i18n.tr("Remove Metric"), KeyEvent.VK_0, "RemoveMetric");
		
		sifrantiMenu.addSeparator();
		
		addMenuEntry(sifrantiMenu, i18n.tr("Add task type"), KeyEvent.VK_4, "AddTaskType");
		addMenuEntry(sifrantiMenu, i18n.tr("Change task type"), KeyEvent.VK_5, "ChangeTaskType");
		addMenuEntry(sifrantiMenu, i18n.tr("Remove task type"), KeyEvent.VK_6, "RemoveTaskType");
		
		sifrantiMenu.addSeparator();
		
		addMenuEntry(sifrantiMenu, i18n.tr("Add task status"), KeyEvent.VK_7, "AddTaskStatus");
		addMenuEntry(sifrantiMenu, i18n.tr("Change task status"), KeyEvent.VK_8, "ChangeTaskStatus");
		addMenuEntry(sifrantiMenu, i18n.tr("Remove task status"), KeyEvent.VK_9, "RemoveTaskStatus");
		
		JMenu developerMenu = new JMenu(i18n.tr("Employees"));
		developerMenu.setMnemonic(KeyEvent.VK_E);
		
		addMenuEntry(developerMenu, i18n.tr("Add"), KeyEvent.VK_A, "AddDeveloper");
		addMenuEntry(developerMenu, i18n.tr("View"), KeyEvent.VK_V, "ViewDevelopers");
		
		JMenu teamMenu = new JMenu(i18n.tr("Team"));
		teamMenu.setMnemonic(KeyEvent.VK_T);
		
		addMenuEntry(teamMenu, i18n.tr("Add"),         KeyEvent.VK_A, "AddTeam");
		addMenuEntry(teamMenu, i18n.tr("View"),        KeyEvent.VK_V, "ViewTeam");
		addMenuEntry(teamMenu, i18n.tr("Change Name"), KeyEvent.VK_V, "ChangeName");
		addMenuEntry(teamMenu, i18n.tr("Remove"),      KeyEvent.VK_R, "RemoveTeam");
		
		bar.add(fileMenu);
		bar.add(sifrantiMenu);
		bar.add(developerMenu);
		bar.add(teamMenu);
		
		JMenu helpMenu = new JMenu(i18n.tr("Help"));
		helpMenu.setMnemonic(KeyEvent.VK_H);
		addMenuEntry(helpMenu, i18n.tr("About"), KeyEvent.VK_A, "About");
		bar.add(helpMenu);
		
		setJMenuBar(bar);
	}
	
	/**
	 * Add menu item
	 * 
	 * @param menu to which menu to add
	 * @param name name as seen on screen
	 * @param keyCode key shortcut
	 * @param actionCommand string which will be compared to see if this was selected
	 */
	private JMenuItem addMenuEntry(JMenu menu, String name, int keyCode, String actionCommand)
	{
		JMenuItem item = new JMenuItem(name, keyCode);
		item.addActionListener(this);
		item.setActionCommand(actionCommand);
		menu.add(item);
		return item;
	}

	/**
	 * Save application properties
	 */
	private void lastPropertySave() {
		if (!_saved) {
			_saved = true;
			// save property file
			PropertyModel pm = _models.getPropertyModel();
			// save property file to default location
			pm.savePropertyFile();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (handleFileMenu(e)) {}
		else if (handleCodeListMenu(e)) {}
		else if (cmd.equals("ViewDevelopers")) {
			showIt(new DevelopersViewDialog(this));
		} else if (cmd.equals("ViewDevelopers")) {
			showIt(new DevelopersViewDialog(this));
		} else if(cmd.equals("AddDeveloper")) {
			showIt(new AddDeveloperDialog(this));			
		} else if (cmd.equals("AddTeam"))	{
			showIt(new TeamAddDialog(this));			
		} else if (cmd.equals("ViewTeam")) {
			showIt(new TeamOverviewDialog(this));			
		} else if (cmd.equals("ChangeName")) {
			showIt(new TeamChangeNameDialog(this));
		} else if (cmd.equals("RemoveTeam")) {
			showIt(new TeamRemoveDialog(this));
		} else if (cmd.equals("About")) {
			showIt(new AboutBoxDialog(this));
		}
	}
	
	/**
	 * Handle file menu action events
	 * @param e event
	 * @return true if handled, false otherwise
	 */
	private boolean handleFileMenu(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("NewProject")) {
			return showIt(new ProjectNewDialog(this));
		} else if (cmd.equals("OpenProject")) {
			return showIt(new ProjectOpenDialog(this));
		} else if (cmd == "RemoveProject") {
			return showIt(new ProjectRemoveDialog(this));
		} else if (cmd.equals("CloseProject")) {
			_projectModel.closeProject();
			return true;
		} else if (cmd.equals("ViewProductBacklog")) {
			return showIt(new ProductBacklogViewDialog(this));
		} else if(cmd.equals("AddProductBacklog")) {
			return showIt(new ProductBacklogAddDialog(this));
		} else if(cmd.equals("ChangeProductBacklogItem")) {
			/*
			ProductBacklogChangeDialog dialog = new ProductBacklogChangeDialog(this, 2);
			Util.centre(dialog);
			dialog.setVisible(true);
			*/
			return true;
		} else if(cmd.equals("SprintPlanMeet")) {
			return showIt(new SprintPlanningMeetingDialog(this));
		} else if(cmd.equals("DailyScrumMeet")) {
			return showIt(new DailyScrumMeetingDialog(this));
		} else if(cmd.equals("ViewSprintBacklog")) {
			return showIt(new SprintBacklogViewDialog(this));
		} else if (cmd.equals("Exit")) {
			dispose();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Handle code list menu events
	 * @param e event
	 * @return true if handled, false otherwise
	 */
	private boolean handleCodeListMenu(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (handleImpedimentCodeListMenu(e)) {
			return true;
		} else if (handleTaskCodeListMenu(e)) {
			return true;		
		} else if (handleMetricCodeListMenu(e)) {
			return true;
		} else if(cmd.equals("AddAbsenceType")) {
			return showIt(new AbsenceTypeAddDialog(this));
		} else if(cmd.equals("ChangeAbsenceType")) {
			return showIt(new AbsenceTypeChangeDialog(this));
		} else if(cmd.equals("RemoveAbsenceType")) {
			return showIt(new AbsenceTypeRemoveDialog(this));
		} else if(cmd.equals("AddAbsenceType")) {
			return showIt(new AbsenceTypeAddDialog(this));
		} else if(cmd.equals("ChangeAbsenceType")) {
			return showIt(new AbsenceTypeChangeDialog(this));
		} else if(cmd.equals("AdminDaysView")) {
			return showIt(new AdminDaysViewDialog(this));
		} else if(cmd.equals("AdminDaysAdd")) {
			return showIt(new AdminDaysAddDialog(this));
		} else {
			return false;
		}
	}
	
	/**
	 * Handle impediment code list 
	 * @param e event
	 * @return true if handled, false otherwise
	 */
	private boolean handleImpedimentCodeListMenu(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("AddImpediment")) {
			return showIt(new ImpedimentsAddDialog(this));
		} else if(cmd.equals("ChangeImpedimentStatus")) {
			return showIt(new ImpedimentStatusChange(this));
		} else if(cmd.equals("ViewImpediments")) {
			return showIt(new ImpedimentsViewDialog(this));
		} else if(cmd.equals("ChangeImpediment")) {
			// showIt(new ImpedimentsChangeDialog(this));
			return true;
		} else if(cmd.equals("AddImpedimentType")) {
			return showIt(new ImpedimentTypeAdd(this));
		} else if(cmd.equals("ChangeImpedimentType")) {
			return showIt(new ImpedimentTypeChange(this));
		} else if(cmd.equals("RemoveImpedimentType")) {
			return showIt(new ImpedimentTypeRemove(this));
		} else if(cmd.equals("AddImpedimentStatus")) {
			return showIt(new ImpedimentStatusAdd(this));
		} else if(cmd.equals("RemoveImpedimentStatus")) {
			return showIt(new ImpedimentStatusRemove(this));
		} else {
			return false;
		}
	}
	
	/**
	 * Handle task related code list menu events
	 * @param e event
	 * @return true if handled, false otherwise
	 */
	private boolean handleTaskCodeListMenu(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("AddTaskType")) {
			return showIt(new TaskTypeAddDialog(this));
		} else if(cmd.equals("ChangeTaskType")) {
			return showIt(new TaskTypeChangeDialog(this));
		} else if(cmd.equals("RemoveTaskType")) {
			return showIt(new TaskTypeRemoveDialog(this));
		} else if (cmd.equals("AddTaskStatus")) {
			return showIt(new TaskStatusAddDialog(this));
		} else if(cmd.equals("ChangeTaskStatus")) {
			return showIt(new TaskStatusChangeDialog(this));
		} else if(cmd.equals("RemoveTaskStatus")) {
			return showIt(new TaskStatusRemoveDialog(this));
		} else {
			return false;
		}
	}
	
	/**
	 * Handle metric related code list menu events
	 * @param e event
	 * @return true if handled, false otherwise
	 */
	private boolean handleMetricCodeListMenu(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("AddMetric")) {
			return showIt(new MetricAddDialog(this));
		} else if (cmd.equals("ChangeMetric")) {
			return showIt(new MetricChangeDialog(this));
		} else if (cmd.equals("RemoveMetric")) {
			return showIt(new MetricRemoveDialog(this));
		} else {
			return false;
		}
	}
	
	/**
	 * Centre and display dialog applicatino modally
	 * @param dialog dialog to display
	 */
	private boolean showIt(JDialog dialog) {
		Util.centre(dialog);
		dialog.setVisible(true);
		return true;
	}
	
	@Override
	public void setVisible(boolean b) {
		if (!b)	{
			lastPropertySave();
		}
		super.setVisible(b);
	}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		lastPropertySave();	
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
	
	@Override
	public void operationSucceeded(DataOperation type, ProjectOperation identifier, String message) {
		switch (type)
		{
		case Update: {
			switch (identifier) {
			case Project:
				setTitle(ApplicationName + " - " + _projectModel.getCurrentProjectName());
				break;
			}
		}
		case Custom: {
			switch (identifier) {
			case Open:
				setTitle(ApplicationName + " - " + _projectModel.getCurrentProjectName());
				_closeMenuItem.setEnabled(true);
				break;
			case Close:
				setTitle(ApplicationName);
				_closeMenuItem.setEnabled(false);
				break;
			}
		}
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, ProjectOperation identifier, String message) {}
	
	/// menu item for closing projects
	private JMenuItem _closeMenuItem;
	/// project model
	private ProjectModel _projectModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// model factory
	private Models _models;
	/// have properties been saved on form closing or setVisible
	private boolean _saved = false;
	/// who am I?
	private static final String ApplicationName = "Scrummer";
	/// serialization id
	private static final long serialVersionUID = 6549724505081986425L;
}
