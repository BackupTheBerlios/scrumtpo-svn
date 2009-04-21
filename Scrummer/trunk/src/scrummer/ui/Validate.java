package scrummer.ui;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

/**
 * Validator class makes validation of ui input fields more convenient
 */
public class Validate {

	/**
	 * Check if text field is not empty. If it is display a default error message.
	 * 
	 * @param input input field to check
	 * @param parent parent on which to display err message
	 * @return
	 */
	public static boolean empty(JTextField input, Component parent)
	{
		String trimmed = input.getText().trim();
		if (trimmed.length() > 0)
		{
			return true;
		}
		else
		{
			Util.showError(parent, 
				i18n.tr("Input field cannot be an empty string. " +
						"Please enter a word with at least one character."), 
				i18n.tr("Error"));
			return false;
		}
	}
	
	/**
	 * Check if text field is not empty. If it is display an error message.
	 * 
	 * @param input input field to check
	 * @param message message to display
	 * @param parent parent of error message
	 * @return true if value is valid, false otherwise
	 */
	public static String empty(JTextField input, String message, Component parent)
	{
		String trimmed = input.getText().trim();
		if (trimmed.length() > 0)
		{
			return trimmed;
		}
		else
		{
			Util.showError(parent, message, i18n.tr("Error"));
			return null;
		}
	}
	
	/**
	 * Check whether contents of input field is date
	 * 
	 * @param input input field
	 * @param message error message to show in case value incorrect
	 * @param parent parent control
	 * @return text converted to date or null if value invalid
	 */
	public static Date date(JTextField input, String message, Component parent)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date ret = null;
		try {
			ret = df.parse(message);
		} catch (ParseException e) {
			Util.showError(parent, message, i18n.tr("Error"));
			ret = null;
		}
		return ret;
	}
	
	/**
	 * This functions has to be called because this class was meant to be used
	 * with static methods
	 * @param value translation instance
	 */
	public static void setI18n(I18n value)
	{
		i18n = value;
	}
	
	/// translation class field
	private static I18n i18n; 
}
