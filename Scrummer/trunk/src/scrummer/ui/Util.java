package scrummer.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.KeyStroke;

/**
 * Various useful user interface utility functions
 */
public class Util {

	/**
	 * Centre main frame on screen
	 */
	public static void centre(Window window) {
		// Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    // Determine the new location of the window
	    int w = window.getSize().width;
	    int h = window.getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    // Move the window
	    window.setLocation(x, y);
	}
	
	/**
	 * Centre given window relative to another window
	 * @param window window to centre
	 * @param parent window relative to which to centre
	 */
	public static void centreRelative(Window window, Window parent)
	{
		// Determine the new location of the window
	    int w = window.getSize().width;
	    int h = window.getSize().height;
	    int x = (parent.getWidth()-w)/2;
	    int y = (parent.getHeight()-h)/2;
	    // Move the window
	    window.setLocation(x, y);
	}
	
	/**
	 * Prikladna funkcija za izdelavo omejitev
	 * 
	 * @param fill v katero smer se stvar razteguje
	 * @param weightx teža glede na x os
	 * @param weighty teža glede na y os
	 * @return sestavljene omejitve
	 */
	public static GridBagConstraints constraint(int fill, double weightx, double weighty)
	{
		GridBagConstraints ret = new GridBagConstraints();
		ret.fill = fill;
		ret.weightx = weightx;
		ret.weighty = weighty;
		return ret;
	}
	/**
     * Patch the behaviour of a component. This works for JTextArea.
     * TAB transfers focus to the next focusable component,
     * SHIFT+TAB transfers focus to the previous focusable component.
     * 
     * @param c The component to be patched.
     */
    public static void tabTraverse(Component c) {
        Set<KeyStroke> 
        strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("pressed TAB")));
        c.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, strokes);
        strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("shift pressed TAB")));
        c.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, strokes);
    }

}