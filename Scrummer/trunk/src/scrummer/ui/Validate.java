package scrummer.ui;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.exception.ValueInvalid;

/**
 * Validator class makes validation of ui input fields more convenient
 */
public class Validate {

	/**
	 * Check if text field is not empty. If it is display a default error message.
	 * 
	 * @param input input field to check
	 * @param parent parent on which to display err message
	 * @return true if field isn't empty, false otherwise
	 */
	public static boolean emptyMsg(JTextField input, Component parent)
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
	 * Check if text field is not empty.
	 * 
	 * @param input input field to check
	 * @param parent parent on which to display err message
	 * @return true if field isn't empty, false otherwise
	 */
	public static boolean empty(JTextField input, Component parent) {
		String trimmed = input.getText().trim();
		if (trimmed.length() > 0) {
			return true;
		} else {			
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
			ret = df.parse(input.getText());
		} catch (ParseException e) {
			Util.showError(parent, message, i18n.tr("Error"));
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Check whether contents of input field is date
	 * 
	 * @param input input field
	 * @return text converted to date or null if value invalid
	 */
	public static Date date(JTextField input)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date ret = null;
		try {
			ret = df.parse(input.getText());
		} catch (ParseException e) {			
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Check if given field contains a number
	 * @param input input field
	 * @return number or 0 if field is empty
	 */
	public static int number(JTextField input)
	{
		int ret = 0;
		try
		{
			ret = Integer.parseInt(input.getText().trim());
		}
		catch (NumberFormatException ex) {}
		return ret;
	}
	
	/**
	 * Check if given text field value is in range
	 * @param input input text field
	 * @param low low border
	 * @param high high border
	 * @return true if value is inbetween, false otherwise
	 */
	public static boolean inrange(JTextField input, int low, int high) {	
		if (low > high) {
			throw new ValueInvalid(
				new Integer(low).toString(), 
				"Low interval border must be lower than high.");
		}
		
		Integer i = Integer.parseInt(input.getText());
		if ((i >= low) && (i <= high)) {
			return true;
		} else {
			return false;
		}	
	}
	
	/**
	 * Check if given text field value is in range. If it is not display error message.
	 * @param input input text field
	 * @param low low border
	 * @param high high border
	 * @param message error message
	 * @param parent parent control
	 * @return true if value is inbetween, false otherwise
	 */
	public static boolean inrange(JTextField input, int low, int high, String message, Component parent) {
		if (!inrange(input, low, high)) {
			Util.showError(parent, message, i18n.tr("Error"));
			return false;
		} else {
			return true;
		}
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
