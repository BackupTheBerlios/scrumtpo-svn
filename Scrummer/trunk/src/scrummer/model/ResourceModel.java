package scrummer.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.omg.CORBA.portable.ValueInputStream;

import scrummer.IO;
import scrummer.exception.ValueInvalid;

/**
 * Resource model encapsulates resource access
 * 
 * This model handles image, font and other resources.
 */
public class ResourceModel {

	/**
	 * Images accessable through this model
	 */
	public enum Image
	{
		/// scrummer logo
		ScrummerLogo,
		/// left arrow
		ArrowLeft,
		/// up arrow
		ArrowUp,
		/// home 
		Home
		;
		
		/**
		 * Convert enum to filename
		 * @param image get image string
		 * @return filename(relative to bin/ directory
		 */
		public static String getName(ResourceModel.Image image)
		{
			switch (image)
			{
			case ScrummerLogo: 
				return r("scrum.png");
			case ArrowLeft:    
				return r("left.png");
			case ArrowUp: 	   
				return r("up.png");
			case Home: 		   
				return r("home.png");
			}
			
			throw new ValueInvalid(image.toString(), "Unknown image path!");
		}
		
		/**
		 * Return path with all / replaced with platform specific pathname separator
		 * @param path file path
		 * @return platform specific file path
		 */
		private static String r(String path)
		{
			return ("bin/" + path).replace("/", IO.separator());
		}
	}
	
	/**
	 * Default constructor
	 * 
	 * @param logger logger
	 */
	public ResourceModel(LoggingModel logger)
	{
		_logger = logger;		
	}
	
	/**
	 * Fetch image
	 * 
	 * @param image fetched image
	 * @return image
	 * @throws IOException when image could not be loaded
	 */
	public BufferedImage get(ResourceModel.Image image) throws IOException
	{
		if (!_images.containsKey(image))
		{
			String url = ResourceModel.Image.getName(image); 
			BufferedImage bufImage = ImageIO.read(IO.standardpath(url));
			_images.put(image, bufImage);
		}
		System.out.println("TEST");
		return _images.get(image);
	}
	
	/// image map
	private HashMap<ResourceModel.Image, BufferedImage> _images = new HashMap<ResourceModel.Image, BufferedImage>();
	/// logger
	private LoggingModel _logger;
}
