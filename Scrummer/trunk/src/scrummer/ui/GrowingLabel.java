package scrummer.ui;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextAttribute;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Label that is drawn smaller than it really is. When user hoovers over it it grows
 */
public class GrowingLabel extends JLabel  implements ActionListener, MouseListener, MouseMotionListener, ImageObserver {
	
	/// serialization id
	private static final long serialVersionUID = 9036820516848673485L;
	
	/**
	 * Kaj se dogaja z robom
	 */
	enum BorderState
	{
		// ne dogaja se nič
		STANDSTILL,
		// rob pada(numerično)
		DOWN,
		/// rob narašča(numerično)
		UP
	}
	
	public GrowingLabel(Image image) {
		super();
		_image = image;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// časovnik
		_timer = new Timer(0, this);
		_timer.setActionCommand("Čas");
		_timer.setDelay(10);
		
		_timer.start();
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(_image, _picside + _currentBorder, _picup + _currentBorder, 
				getWidth() - _picside - _currentBorder, 
				getHeight() - _picup - _currentBorder, 
				0, 0, _image.getWidth(this), 
					  _image.getHeight(this), this);	
		
		// for debugging
		// g.setColor(Color.BLACK);
		// g.drawRect(_picside, _picup, g.getClipBounds().width - 2 * _picside, g.getClipBounds().height - 2 * _picup);
		int textWidth = g.getFontMetrics().stringWidth(getText());
		
		if (_mouseIn)
		{
			AttributedString as = new AttributedString(getText());
		    as.addAttribute(TextAttribute.FONT, getFont());
		    as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		    g.drawString(as.getIterator(), getWidth() / 2 - textWidth / 2, getHeight() - _textbottom);    
		}
		else
		{
			g.drawString(getText(), getWidth() / 2 - textWidth / 2, getHeight() - _textbottom);
		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (_state == BorderState.DOWN)
		{
			if (_currentBorder > _minBorder)
			{
				_currentBorder -= 2;
				this.repaint();				
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
				_currentBorder += 2;
				this.repaint();
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
		_mouseIn = true;
		_state = BorderState.DOWN;
		if (!_timer.isRunning())
		{
			_timer.start();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		_mouseIn = false;
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		_mouseIn = true;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		_state = BorderState.DOWN;
		if (!_timer.isRunning())
		{
			_timer.start();
		}
	}

	/// animacijski časovnik
	private Timer _timer;
	/// najmanjši rob
	private int _minBorder = 0;
	/// trenutni rob
	private int _currentBorder = 24;
	/// največji rob
	private int _maxBorder = 30;
	/// stanje robne animacije
	private BorderState _state = BorderState.STANDSTILL;
	/// is mouse on this control
	private boolean _mouseIn = false;
	
	// painting options
	
	/// image to paint
	private Image _image;
	
	/// distance from top 
	private int _picup = 30;
	/// distance from sides
	private int _picside = 40;
	/// distance of text from bottom
	private int _textbottom = 5;
}
