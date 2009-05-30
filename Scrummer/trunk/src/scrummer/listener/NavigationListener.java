package scrummer.listener;

import scrummer.model.NavigationModel;

/**
 * Listener to navigation change events
 */
public interface NavigationListener 
{
	/**
	 * Current page was changed
	 * @param newLink new page link
	 */
	void pageChanged(NavigationModel.Link newLink);
	
}
