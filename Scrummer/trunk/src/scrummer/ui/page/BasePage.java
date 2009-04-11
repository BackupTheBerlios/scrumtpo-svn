package scrummer.ui.page;

import javax.swing.JPanel;
import scrummer.ui.MainFrame;

/**
 * Base page class - contains MainFrame for convenience
 */
public class BasePage extends JPanel {

	/**
	 * Constructor
	 * @param mainFrame main frame for showing various windows
	 */
	public BasePage(MainFrame mainFrame) {
		_mainFrame = mainFrame;
	}

	/**
	 * @return main frame
	 */
	protected MainFrame getMainFrame()
	{
		return _mainFrame;
	}
	
	/// main frame
	private MainFrame _mainFrame;
	/// serialization id
	private static final long serialVersionUID = 707692988496039450L;
}
