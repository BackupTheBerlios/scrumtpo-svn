package scrummer.uicomponents;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Standard button
 * 
 * Is "clicked" when focused and user presses enter.
 * It also has nicer style than default buttons.
 */
public class StandardButton extends JButton implements KeyListener {
	
	public StandardButton() {
		init();
	}

	public StandardButton(Icon icon) {
		super(icon);
		init();
	}

	public StandardButton(String text) {
		super(text);
		init();
	}

	public StandardButton(Action a) {
		super(a);
		init();
	}

	public StandardButton(String text, Icon icon) {
		super(text, icon);
		init();
	}
	
	/**
	 * Vsem konstruktorjem skupna koda
	 */
	private void init() {
		int k = 5;
		setSize(100, 20);
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY), 
				BorderFactory.createEmptyBorder(k, k, k, k)));
		this.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			doClick();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	/// serialization id
	private static final long serialVersionUID = -6874921456279102626L;
}
