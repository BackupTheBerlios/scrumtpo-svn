package scrummer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;

import scrummer.model.LoggingModel;

/**
 * Various input and output related functionality
 */
public class IO {

	/**
	 * Check whether file exists
	 * @param file path
	 * @return true if it exists, false otherwise
	 */
	public static boolean exists(String file)
	{
		return new File(file).exists();
	}

	/**
	 * Check whether file exists
	 * @param file file to check for
	 * @return true if it exists, false otherwise
	 */
	public static boolean exists(URL file)
	{
		return exists(file.toString());
	}
	
	/**
	 * Check whether file can be opened
	 * @param file path
	 * @return true if file can be read, false otherwise
	 */
	public static boolean canRead(String file)
	{
		return new File(file).canRead();
	}
	
	/**
	 * Check whether file can be opened
	 * @param file path
	 * @return true if file can be read, false otherwise
	 */
	public static boolean canRead(URL file)
	{
		return new File(file.toString()).canRead();
	}

	/**
	 * Check whether file can be written 
	 * @param file path
	 * @return true if file can be written, false otherwise
	 */
	public static boolean canWrite(String file)
	{
		return new File(file).canWrite();
	}
	
	/**
	 * Check whether file can be written 
	 * @param file path
	 * @return true if file can be written, false otherwise
	 */
	public static boolean canWrite(URL file)
	{
		return new File(file.toString()).canWrite();
	}
	
	/**
	 * Convert given path into absolute pathname without protocol prefix
	 * @param file pathname
	 * @return absolute path to file
	 */
	public static String filepath(String file)
	{
		File f = new File(file);
		return f.getAbsolutePath();
	}
	
	/**
	 * Convert given path into absolute path but don't prefix it with protocol string
	 * @param path original path
	 * @return converted path
	 */
	public static URL path(String prefix, String path)
	{
		File f = new File(path);
		URL ret;
		try {
			ret = new URL(prefix + f.getAbsolutePath());
		} catch (MalformedURLException e) {
			Scrummer.getModels().getLoggingModel().severe("Wrong path.", e);
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Convenience method to prefix some path with file:
	 * @param path path to file
	 * @return file url
	 */
	public static URL standardpath(String path)
	{
		return path("file:", path);
	}
	
	/**
	 * Copy source to destination file
	 * @param in input file
	 * @param out output file
	 */
	public static void copyFile(File in, File out)  
	{		
		try 
		{
		    FileChannel inChannel = new FileInputStream(in).getChannel();
		    FileChannel outChannel = new FileOutputStream(out).getChannel();
		    try {
		        inChannel.transferTo(0, inChannel.size(), outChannel);
		    } 
		    catch (IOException e) {
		    	_logger.severe("Could not copy file!", e);
		    }
		    finally {
		        if (inChannel != null)
					try {
						inChannel.close();
					} catch (IOException e) {
						_logger.severe("Could not close input file!", e);
					}
		        if (outChannel != null)
					try {
						outChannel.close();
					} catch (IOException e) {
						_logger.severe("Could not close output file!", e);
					}
		    }
		} catch (FileNotFoundException e1) {
			_logger.severe("File not found!", e1);
		}
	}
	
	/**
	 * @return file separator
	 */
	public static String separator()
	{
		return System.getProperty("file.separator");
	}
	
	/**
	 * Set logger(break dependencies 
	 * @param logger logger instance
	 */
	public static void setLogger(LoggingModel logger)
	{
		_logger = logger;
	}
	
	private static LoggingModel _logger;
}
