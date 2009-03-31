package scrummer.ui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.PropertyModel;
import scrummer.ui.dialog.AboutBoxDialog;
import scrummer.ui.dialog.AddDeveloperDialog;
import scrummer.ui.dialog.DevelopersAddDialog;
import scrummer.ui.dialog.DevelopersViewDialog;
import scrummer.ui.dialog.LoginDialog;
import scrummer.ui.dialog.NewProjectDialog;
import scrummer.ui.dialog.OpenProjectDialog;
import scrummer.ui.dialog.ProductBacklogAddDialog;
import scrummer.ui.dialog.ProductBacklogViewDialog;

/**
 * Main application window
 */
public class MainFrame extends JFrame implements ActionListener, WindowListener {

	/**
	 * Default constructor
	 * @throws HeadlessException
	 */
	public MainFrame() throws HeadlessException {
		super();
		// store local model factory instance
		_models = Scrummer.getModels();
		// set title
		setTitle("Scrummer");
		// quit application on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// listen to window close events(to save properties)
		addWindowListener(this);
		// add menu bar, entries for it and event listeners
		addMenu();
		// add navigation grid
		NavigationGridPanel grid = new NavigationGridPanel();
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
		fileMenu.addSeparator();
		addMenuEntry(fileMenu, i18n.tr("Add product backlog"), 	KeyEvent.VK_A, "AddProductBacklog");
		addMenuEntry(fileMenu, i18n.tr("View product backlog"), KeyEvent.VK_P, "ViewProductBacklog");
		
		fileMenu.addSeparator();
		addMenuEntry(fileMenu, i18n.tr("Exit"), KeyEvent.VK_X, "Exit");
		
		JMenu developerMenu = new JMenu(i18n.tr("Developer"));
		
		addMenuEntry(developerMenu, i18n.tr("Add"), KeyEvent.VK_D, "AddDeveloper");
		addMenuEntry(developerMenu, i18n.tr("View"), KeyEvent.VK_V, "ViewDevelopers");
		
		bar.add(fileMenu);
		bar.add(developerMenu);
		
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
	private void addMenuEntry(JMenu menu, String name, int keyCode, String actionCommand)
	{
		JMenuItem item = new JMenuItem(name, keyCode);
		item.addActionListener(this);
		item.setActionCommand(actionCommand);
		menu.add(item);
	}

	/**
	 * Save application properties
	 */
	private void lastPropertySave()
	{
		if (!_saved)
		{
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
		if (cmd.equals("NewProject"))
		{
			NewProjectDialog dialog = new NewProjectDialog(this);
			dialog.setVisible(true);
		}
		else if (cmd.equals("OpenProject"))
		{
			OpenProjectDialog dialog = new OpenProjectDialog(this);
			dialog.setVisible(true);
		}
		else if (cmd.equals("ViewProductBacklog"))
		{
			ProductBacklogViewDialog dialog;
			try {
				dialog = new ProductBacklogViewDialog(this);
				dialog.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(cmd.equals("AddProductBacklog"))
		{
			ProductBacklogAddDialog dialog;
			try {
				dialog = new ProductBacklogAddDialog(this);
				dialog.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (cmd.equals("ViewDevelopers"))
		{
			DevelopersViewDialog dialog;
			try {
				dialog = new DevelopersViewDialog(this);
				dialog.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(cmd.equals("AddDeveloper"))
		{
			AddDeveloperDialog dialog = new AddDeveloperDialog(this);
			Util.centre(dialog);
			dialog.setVisible(true);
			/*
			DevelopersAddDialog dialog;
			try {
				dialog = new DevelopersAddDialog(this);
				dialog.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			
		}
		else if (cmd.equals("Exit"))
		{
			dispose();
		}
		else if (cmd.equals("About"))
		{
			AboutBoxDialog box = new AboutBoxDialog(this);
			box.setVisible(true);
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		lastPropertySave();
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
	
	
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// model factory
	private Models _models;
	/// have properties been saved on form closing or setVisible
	private boolean _saved = false;
	/// serialization id
	private static final long serialVersionUID = 6549724505081986425L;
}
