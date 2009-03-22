package scrummer.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import scrummer.IO;

/**
 * Application property model
 * 
 * Handles application properties.
 */
public class PropertyModel {

	/**
	 * Constructor
	 * 
	 * Loads default property set and overrides them with a user application configuration file.
	 * This file is specified as the key in default application properties - application.configFile
	 * @param logger logger to use
	 */
	public PropertyModel(LoggingModel logger)
	{
		if (logger == null)
		{
			throw new NullPointerException("Cannot use null logger!");
		}
		_logger = logger;
		_property = defaults();
		if (IO.exists(IO.filepath("bin")))
		{
			String configFile = "bin/" + _property.getProperty("application.configFile");
			String url = IO.filepath(configFile);
			// check if configuration file exists
			// if it exists, load it
			if (IO.exists(url))
			{
				loadPropertyFile(url);
			}
			else
			// otherwise create it from default configuration file
			{
				createDefaultPropertyFile(url);
			}
		}
		else
		{
			_logger.warning("./bin directory does not exist! Could not create configuration file.");
		}
	}
	
	/**
	 * Fetch property
	 * @param key property key
	 * @return property value
	 */
	public String getProperty(String key)
	{
		return _property.getProperty(key);
	}
	
	/**
	 * Set property 
	 * @param key property key
	 * @param value property value
	 */
	public void setProperty(String key, String value)
	{
		_property.setProperty(key, value);	
	}
	
	/**
	 * Save property file to it's default location
	 */
	public void savePropertyFile()
	{
		// fetch default property file name
		String path = 
			IO.filepath("bin") + 
			IO.separator() + 
			_property.getProperty("application.configFile");
		// save it
		savePropertyFile(path);
	}
	
	/**
	 * Save property file to given path
	 * @param url pathname 
	 */
	public void savePropertyFile(String url)
	{
		boolean success = false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(url.toString());
			_property.storeToXML(fos, "");			
			success = true;
		} catch (FileNotFoundException e1) {
			_logger.warning("Could not save application property file!", e1);
		} catch (IOException e1) {
			_logger.warning("Could not save application property file!", e1);
		}
		finally
		{
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {}
		}
		
		if (!success)
		{
			_logger.warning("Could not save appplication property file.");
		}
	}
	
	/**
	 * Create default xml property file at url
	 * @param url url
	 */
	private void createDefaultPropertyFile(String url) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(url.toString());
			_property.storeToXML(fos, "");	
		} catch (FileNotFoundException e1) {
			_logger.warning("Could not create file for initial config!", e1);
		} catch (IOException e1) {
			_logger.warning("Could not save initial config!", e1);
		}
		finally
		{
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {}
		}
	}

	/**
	 * Load property from xml file
	 * @param url path
	 */
	private void loadPropertyFile(String url) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(url.toString());					
			_property.loadFromXML(fis);
		} catch (FileNotFoundException e) {
			_logger.warning("Could not load property file!", e);
			/*
			JOptionPane dialog = new JOptionPane(
					"Could not create configuration file in " + url.toString() + "!", 
					JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			*/
		} catch (IOException e) {
			_logger.warning("Could not load property file!", e);
			/*
			JOptionPane dialog = new JOptionPane(
					"Could not create configuration file in " + url.toString() + "!", 
					JOptionPane.ERROR_MESSAGE);
			dialog.setVisible(true);
			*/
		}
		finally
		{
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {}
		}
	}
	
	/**
	 * Create a property class of default values for various properties 
	 * @return created property class
	 */
	private Properties defaults()
	{
		Properties ret = new Properties();
		// database username
		ret.setProperty("uidefault.LoginDialog.username", "");
		// database server hostname
		ret.setProperty("uidefault.LoginDialog.hostname", "127.0.0.1");
		// database server port
		ret.setProperty("uidefault.LoginDialog.port", "4500");
		// last opened project
		ret.setProperty("uidefault.OpenProjectDialog.lastOpened", "");
		// slovene translation class path
		ret.setProperty("language.bundle.sl_SI", "scrummer.i18n.Messages_sl_SI");
		// english translation class path
		ret.setProperty("language.bundle.en_GB", "scrummer.i18n.Messages_en_GB");
		// configuration filename
		ret.setProperty("application.configFile", "configuration.xml");
		// default language bundle
		ret.setProperty("language.default", ret.getProperty("language.bundle.sl_SI"));
		return ret;
	}
	
	/// application properties
	private Properties _property = null;
	/// logger
	private LoggingModel _logger = null;
}
