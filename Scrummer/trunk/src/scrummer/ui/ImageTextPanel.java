package scrummer.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import scrummer.IO;
import scrummer.Scrummer;
import scrummer.model.LoggingModel;
import scrummer.model.Models;

/**
 * A panel with image and text
 * 
 * Image grows when user hoovers over panel with mouse
 */
public class ImageTextPanel extends JPanel implements ActionListener, MouseListener {
	
	/**
	 * Border rising/falling state
	 */
	enum BorderState
	{
		// no change
		STANDSTILL,
		// falling border
		DOWN,
		/// rising border
		UP
	}
	
	/**
	 * Default constructor
	 */
	public ImageTextPanel() {

		super();
		
		Models m 		 = Scrummer.getModels();
		_logger 		 = m.getLoggingModel();
		
		setLayout(new GridLayout(1,1));
		
		JPanel panel = new JPanel();
		// panel.setBackground(Color.CYAN);
		panel.setLayout(new GridBagLayout());
		_border = BorderFactory.createEmptyBorder(_currentBorder, _currentBorder, _currentBorder, _currentBorder); 
		panel.setBorder(_border);
		_panel = panel;
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridBagLayout());
		// inner.setBackground(Color.RED);
		inner.setSize(new
				Dimension(50,50));
		
		BufferedImage image = null;
		// ImageIcon icon = null;
		try {
			image = ImageIO.read(IO.standardpath("image" + IO.separator() + "faca.png"));
			JLabel label = new GrowingLabel(image);
			label.setText("Test");
			_label = label;
			
			GridBagConstraints gcLabel = Util.constraint(GridBagConstraints.BOTH, 1.0, 1.0);
			inner.add(label, gcLabel);			
		} catch (MalformedURLException e) {
			_logger.severe("Wrong url.", e);
		} catch (IOException e) {
			_logger.severe("Could not load image.", e);
		}
		
		GridBagConstraints innerGc = Util.constraint(GridBagConstraints.BOTH, 1.0, 1.0);
		
		panel.add(inner, innerGc);
		
		add(panel);
	}

	@Override
	public void setName(String name) {
		_label.setName(name + ".Label");
		super.setName(name);
	}

	/**
	 * Add mouse listener for label events
	 */
	public void addLabelMouseListener(MouseListener listener) {
		
		_label.addMouseListener(listener);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (_state == BorderState.DOWN)
		{
			if (_currentBorder > _minBorder)
			{
				_currentBorder--;
				_panel.setBorder(BorderFactory.createEmptyBorder(_currentBorder, _currentBorder, _currentBorder, _currentBorder));
			}
			else
			{
				_state = BorderState.STANDSTILL;
			}
		}
		else if (_state == BorderState.UP)
		{
			if (_currentBorder < _maxBorder)
			{
				_currentBorder++;
				_panel.setBorder(BorderFactory.createEmptyBorder(_currentBorder, _currentBorder, _currentBorder, _currentBorder));
			}
			else
			{
				_state = BorderState.STANDSTILL;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (_state == BorderState.STANDSTILL)
		{
			_state = BorderState.DOWN;
			if (!_timer.isRunning())
			{
				_timer.start();
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		_state = BorderState.UP;
		if (!_timer.isRunning())
		{
			_timer.start();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	/// outer panel
	private JPanel _panel;
	/// border 
	private Border _border;
	/// label displayed on this panel
	private JLabel _label;
	/// animation timer
	private Timer _timer;
	/// minimal edge
	private int _minBorder = 0;
	/// current edge
	private int _currentBorder = 1;
	/// peak edge
	private int _maxBorder = 20;
	/// animation state
	private BorderState _state = BorderState.STANDSTILL;
	/// logging model
	private LoggingModel _logger;
	/// serialization id
	private static final long serialVersionUID = 899225986872758807L;
}
