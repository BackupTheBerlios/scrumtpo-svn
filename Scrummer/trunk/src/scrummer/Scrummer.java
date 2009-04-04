package scrummer;

import java.awt.Dimension;
import java.io.File;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;
import scrummer.model.LoggingModel;
import scrummer.model.Models;
import scrummer.model.NavigationModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;

/**
 * @brief Application starting class
 */
public class Scrummer {

	/**
	 * Application entry point
	 * @param args arguments 
	 */
	public static void main(String [] args) {
		
		try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }		
		
        _logger = new LoggingModel();
        IO.setLogger(_logger);

		// tale zadeva skopira prevode v ./bin imenik, zato ker toti butasti Eclipse to namenoma onemogoča
		// (se pravi kopiranje .class datotek)
		eclipseCopyHack();
        
        getModels();
        
		MainFrame mf = new MainFrame();
		mf.pack();
		mf.setSize(new Dimension(640, 480));
		Util.centre(mf);
		mf.showLogin();
		mf.setVisible(true);
		
		// go to homepage
		NavigationModel nm = _models.getNavigationModel();
		nm.home();
	}

	/**
	 * Copy translation files to ./bin directory
	 */
	private static void eclipseCopyHack() {
		// find translation classes in class path
		String classPath = System.getProperty("java.class.path");
		// hardcoded one of language
		String slot = "Messages_sl_SI.class";
		String engt = "Messages_en_GB.class";
		
		String outdir = 
			IO.filepath("") + IO.separator() +
			"bin" 			+ IO.separator() +
			"scrummer" 		+ IO.separator() +
			"i18n";
		
		if (!(new File(outdir).exists()))
		{
			new File(outdir).mkdirs();
		}
		
		String [] paths = classPath.split(":");
		for (int i = 0; i < paths.length; i++)
		{
			String pathen = paths[i] + IO.separator() + engt;
			if (IO.exists(pathen))
			{ 		
				IO.copyFile(new File(pathen), new File(outdir + IO.separator() + engt));	
			}
			String pathsl = paths[i] + IO.separator() + slot;
			if (IO.exists(pathsl))
			{
				IO.copyFile(new File(pathsl), new File(outdir + IO.separator() + slot));
			}
		}
	}
	
	/**
	 * Fetch translation object
	 * @param clazz class from which this function is being called
	 * @return translation object
	 */
	public static I18n getI18n(Class<?> clazz)
	{
		return I18nFactory.getI18n(clazz, _languageClassFile);
	}

	/**
	 * Fetch model factory
	 * @return model factory
	 */
	public static Models getModels()
	{
		if (_models == null)
		{
			_models = new Models(_logger);
		}
		
		return _models;
	}
	
	/**
	 * Resolve a dependency between this class and models
	 * @param value language string 
	 */
	public static void setLanguageClassFile(String value) {
		_languageClassFile = value;
	}

	/// model factory
	private static Models _models = null;
	/// logging model
	private static LoggingModel _logger = null;
	/// language class file
	private static String _languageClassFile = "";
}
