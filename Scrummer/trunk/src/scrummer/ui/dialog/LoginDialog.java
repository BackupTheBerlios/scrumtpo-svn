package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.xnap.commons.i18n.I18n;

import scrummer.IO;
import scrummer.Scrummer;
import scrummer.model.LoggingModel;
import scrummer.model.PropertyModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardButton;

/**
 * Dialog shows username, password, hostname and port text fields.
 */
public class LoginDialog extends JDialog implements ActionListener, FocusListener, ChangeListener {
	
	/**
	 * Constructor
	 * @param parent parent frame
	 */
	public LoginDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Login"));
		// fetch logger
		_logger = Scrummer.getModelFactory().getLoggingModel();
		_properties = Scrummer.getModelFactory().getPropertyModel();
		// set size
		setSize(new Dimension(340,280));
		
		JPanel framePanel = new JPanel();
		framePanel.setLayout(new GridBagLayout());
		framePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(framePanel);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		setupTopPanel(topPanel);
		
		JPanel centrePanel = new JPanel();
		setupCentrePanel(centrePanel);
		
		JPanel confirmPanel = new JPanel();
		setupConfirmPanel(confirmPanel);
		
		GridBagConstraints topPanelC    = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 1);
		GridBagConstraints bottomPanelC = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 5);
		bottomPanelC.gridy = 1;
		GridBagConstraints confirmPanelC = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 1);
		confirmPanelC.gridy = 2;
		
		framePanel.add(topPanel,    topPanelC);
		framePanel.add(centrePanel, bottomPanelC);
		framePanel.add(confirmPanel, confirmPanelC);
	}

	/**
	 * Configure top panel
	 * @param panel panel to configure
	 */
	private void setupTopPanel(JPanel panel) {
		ImageIcon icon;
		try {
			icon = new ImageIcon(ImageIO.read(IO.path("file://", "image" + IO.separator() + "scrum.png")));
			JLabel label = new JLabel(icon);
			panel.add(label);
		} catch (MalformedURLException e) {
			_logger.severe("Wrong url.", e);
		} catch (IOException e) {
			_logger.severe("Could not load image.", e);
		}
	}
	
	/**
	 * Configure bottom panel
	 * @param panel panel to configure
	 */
	private void setupCentrePanel(JPanel panel) {
		panel.setLayout(new GridLayout(2, 4, 6, 4));
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(i18n.tr("Connect to server")), 
				BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		
		JLabel usernameLabel = new JLabel(i18n.tr("Username") + ":");
		JTextField usernameInput = new SelectedTextField("");
		usernameInput.setName("Username");
		usernameInput.setText(getDefault("username"));
		usernameInput.addFocusListener(this);
		_usernameInput = usernameInput;
		
		JLabel passwordLabel = new JLabel(i18n.tr("Password") + ":");
		JPasswordField passwordInput = new JPasswordField(10);
		
		JLabel hostnameLabel = new JLabel(i18n.tr("Hostname") + ":");
		JTextField hostnameInput = new SelectedTextField("");
		hostnameInput.setText(getDefault("hostname"));
		hostnameInput.setName("Hostname");
		hostnameInput.addFocusListener(this);
		_hostnameInput = hostnameInput;
		
		JLabel portLabel = new JLabel(i18n.tr("Port") + ":");
		JSpinner portInput = new JSpinner();
		portInput.setName("Port");
		portInput.addChangeListener(this);
		_portInput = portInput;
		
		String port = getDefault("port");
		int iport = Integer.parseInt(port);
		portInput.setValue(iport);
		 
		panel.add(usernameLabel);
		panel.add(usernameInput);
	
		panel.add(hostnameLabel);
		panel.add(hostnameInput);
		
		panel.add(passwordLabel);
		panel.add(passwordInput);
		
		panel.add(portLabel);
		panel.add(portInput);
	}
	
	/**
	 * Setup a penel with connect button
	 * @param panel panel to configure
	 */
	private void setupConfirmPanel(JPanel panel) {
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JButton connectButton = new StandardButton(i18n.tr("Connect"));
		connectButton.setActionCommand("Connect");
		connectButton.addActionListener(this);
		panel.add(connectButton);
	}

	/**
	 * Get login dialog specific property
	 * @param property LoginDialog specific property
	 * @return value
	 */
	private String getDefault(String property)
	{
		return _properties.getProperty("uidefault.LoginDialog." + property);
	}
	
	/**
	 * Set login dialog specific property 
	 * @param property
	 * @param value
	 * @return
	 */
	private void setDefault(String property, String value)
	{
		_properties.setProperty("uidefault.LoginDialog." + property, value);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Connect"))
		{
			setVisible(false);
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getComponent() == _usernameInput)
		{
			setDefault("username", _usernameInput.getText());
		}
		else if (e.getComponent() == _hostnameInput)
		{
			setDefault("hostname", _hostnameInput.getText());
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == _portInput)
		{
			setDefault("port", _portInput.getValue().toString());
		}
	}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass()); 
	/// logger
	private LoggingModel _logger;
	/// property container
	private PropertyModel _properties;
	/// username and hostname input controls
	private JTextField _usernameInput, _hostnameInput;
	/// port input control
	private JSpinner _portInput;
	/// serialization id
	private static final long serialVersionUID = 2696593902322011991L;

	
}
