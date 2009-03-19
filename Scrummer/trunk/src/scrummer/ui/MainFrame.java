package scrummer.ui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.ui.dialog.AboutBoxDialog;
import scrummer.ui.dialog.LoginDialog;
import scrummer.ui.dialog.NewProjectDialog;
import scrummer.ui.dialog.OpenProjectDialog;

/**
 * Main application window
 */
public class MainFrame extends JFrame implements ActionListener {

	/**
	 * Default constructor
	 * @throws HeadlessException
	 */
	public MainFrame() throws HeadlessException {
		super();
		setTitle("Scrummer");
		// quit application on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		addMenuEntry(fileMenu, i18n.tr("New Project"), KeyEvent.VK_N, "NewProject");
		addMenuEntry(fileMenu, i18n.tr("Open Project"), KeyEvent.VK_O, "OpenProject");
		fileMenu.addSeparator();
		addMenuEntry(fileMenu, i18n.tr("Exit"), KeyEvent.VK_X, "Exit");
		
		bar.add(fileMenu);
		
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
		else if (cmd.equals("Exit"))
		{
			this.setVisible(false);
		}
		else if (cmd.equals("About"))
		{
			AboutBoxDialog box = new AboutBoxDialog(this);
			box.setVisible(true);
		}
	}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 6549724505081986425L;

}
