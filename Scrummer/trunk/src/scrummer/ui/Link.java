package scrummer.ui;

import java.awt.Image;
import java.awt.event.MouseEvent;

import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.NavigationModel;

/**
 * Link to some connection model page
 */
public class Link extends GrowingLabel 
{
	/**
	 * Constructor
	 * 
	 * @param link link to page
	 * @param image image
	 */
	public Link(NavigationModel.Link link, Image image) {
		super(image);	
		_link = link;
		Models m = Scrummer.getModels();
		_navigationModel = m.getNavigationModel();
		setText(_navigationModel.getName(link));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		_navigationModel.switchPage(_link);
	}

	/// to which link this navigates
	private NavigationModel.Link _link;
	/// navigation model instance
	private NavigationModel _navigationModel;
	
	private static final long serialVersionUID = -8733476448050328642L;
}
