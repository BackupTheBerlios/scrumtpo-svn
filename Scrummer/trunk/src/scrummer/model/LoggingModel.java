package scrummer.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logging model wraps java logging facilities into an easier to type(convenience) class.
 */
public class LoggingModel {
	
	/**
	 * Constructor
	 */
	public LoggingModel()
	{	
		_logger = Logger.getLogger("DefaultLoggingModel");
	}
	
	/**
	 * Log debugging problems associated with a specific configuration
	 * @param message message
	 */
	public void config(String message)
	{
		_logger.log(Level.CONFIG, message);
	}
	
	/**
	 * Log debugging problems associated with a specific configuration
	 * @param message message
	 * @param ex exception
	 */
	public void config(String message, Throwable ex)
	{
		_logger.log(Level.CONFIG, message, ex);
	}
	
	/**
	 * Log informative message for end users and administrators
	 * @param message message to log
	 */
	public void info(String message)
	{
		_logger.log(Level.INFO, message);
	}
	
	/**
	 * Log informative message for end users and administrators
	 * @param message message
	 * @param ex exception
	 */
	public void info(String message, Throwable ex)
	{
		_logger.log(Level.INFO, message, ex);
	}
	
	/**
	 * Log inability to continue normal program execution. The message should be 
	 * understandable to end users and administartors.
	 * @param message message
	 */
	public void severe(String message)
	{
		_logger.log(Level.SEVERE, message);
	}
	
	/**
	 * Log inability to continue normal program execution. The message should be 
	 * understandable to end users and administartors.
	 * @param message message
	 * @param ex exception
	 */
	public void severe(String message, Throwable ex)
	{
		_logger.log(Level.SEVERE, message, ex);
	}
	
	/**
	 * Log a potential problem. 
	 * The message should be understandable to end users and administartors.
	 * @param message message
	 * @param ex exception
	 */
	public void warning(String message)
	{
		_logger.log(Level.WARNING, message);
	}
	
	/**
	 * Log a potential problem. 
	 * The message should be understandable to end users and administartors.
	 * @param message message
	 * @param ex exception
	 */
	public void warning(String message, Throwable ex)
	{
		_logger.log(Level.WARNING, message, ex);
	}
	
	/// wrapper logger instance
	private Logger _logger;
}
