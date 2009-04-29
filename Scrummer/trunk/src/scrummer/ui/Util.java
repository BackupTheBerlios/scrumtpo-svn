package scrummer.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import scrummer.Scrummer;
import scrummer.model.NavigationModel;
import scrummer.model.ResourceModel;

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
	 * Create title border with spaced borders engulfing it
	 * @param topOuter top spacing for outer spaced border
	 * @param leftOuter left spacing for outer spaced border
	 * @param bottomOuter bottom spacing for outer spaced border
	 * @param rightOuter right spacing for outer spaced border
	 * @param text text on title border
	 * @param topInner top spacing for inner spaced border
	 * @param leftInner left spacing for inner spaced border
	 * @param bottomInner bottom spacing for inner spaced border
	 * @param rightInner right spacing for inner spaced border
	 * @return created compound border
	 */
	public static Border createSpacedTitleBorder(
			int topOuter, int leftOuter, int bottomOuter, int rightOuter,
			String text,
			int topInner, int leftInner, int bottomInner, int rightInner)
	{
		return 
		BorderFactory.createCompoundBorder (
			BorderFactory.createEmptyBorder(topOuter, leftOuter, bottomOuter, rightOuter),
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(text),
				BorderFactory.createEmptyBorder(topInner, leftInner, bottomInner, rightInner)
			)
		);
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
    
    /**
     * 
     * @param parent
     * @param message
     */
    public static void showError(Component parent, String message, String title)
    {
    	JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }
   
	/**
	 * Add link(growing button) to some container
	 * 
	 * @param component container
	 * @param link link to which it points
	 */
	public static scrummer.ui.Link addLink(JComponent component, NavigationModel.Link link)
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
			return linkControl;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get todays date as string
	 * @return date string
	 */
	public static String today()
	{
		GregorianCalendar gc = new GregorianCalendar();
		return  
			gc.get(Calendar.DAY_OF_MONTH) + "." + 
			(gc.get(Calendar.MONTH) - Calendar.JANUARY + 1) + "." + 
			gc.get(Calendar.YEAR);		
	}
}
