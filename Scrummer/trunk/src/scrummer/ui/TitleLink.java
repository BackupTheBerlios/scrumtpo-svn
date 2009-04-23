package scrummer.ui;

import java.awt.Image;
import java.awt.event.MouseEvent;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.listener.NavigationListener;
import scrummer.model.NavigationModel;
import scrummer.model.NavigationModel.Link;

/**
 * Title link(home, back, up)
 */
public class TitleLink extends GrowingLabel implements NavigationListener {

	/**
	 * Posible link endpoints
	 */
	public enum Endpoint
	{
		/// go backwards
		Back,
		/// go upwards
		Up,
		/// go to home link
		Home
	}
	
	/**
	 * Constructor
	 * 
	 * @param endpoint where does this link lead
	 * @param image image to display
	 * @param text to 
	 */
	public TitleLink(Endpoint endpoint, Image image) {
		super(image);
		
		_endpoint = endpoint;
		_navigationModel = Scrummer.getModels().getNavigationModel();
		_navigationModel.addConnectionListener(this);
		
		switch (endpoint)
		{
		case Back: 
			setText(i18n.tr("Back")); break;
		case Home:
			setText(i18n.tr("Home")); break;
		case Up:
			setText(i18n.tr("Up")); break;		
		}
	}
	
	/**
	 * Remember page change(modify internal point-to link accordingly) 
	 * @param newLink
	 */
	@Override	
	public void pageChanged(Link newLink) {
	
		// icon may change in the future when 
		// can't go up, 
		// is already home, or 
		// can't go back
	}
	
	/**
	 * Modify link on model
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (isEnabled())
		{
			super.mouseClicked(e);
			
			switch (_endpoint)
			{
			case Back: _navigationModel.back(); break;
			case Home: _navigationModel.home(); break;
			case Up: break;
			}
		}
	}

	/// navigation model
	private NavigationModel _navigationModel;
	/// endpoint
	private Endpoint _endpoint;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5460261965829718211L;
}
