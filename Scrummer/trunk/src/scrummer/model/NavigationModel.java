package scrummer.model;

import java.util.Vector;

import scrummer.listener.NavigationListener;

/**
 * Navigation data model
 * 
 * Contains and manages current, last and home navigation links.
 */
public class NavigationModel {

	/**
	 * Add listener to connection related events
	 * @param listener listener
	 */
	public void addConnectionListener(NavigationListener listener)
	{
		if (!_navigationListeners.contains(listener))
		{
			_navigationListeners.add(listener);
		}
	}
	
	/**
	 * Remove listener to connection related events
	 * @param listener listener
	 */
	public void removeConnectionListener(NavigationListener listener)
	{
		_navigationListeners.remove(listener);
	}
	
	/// navigation event listeners
	private Vector<NavigationListener> _navigationListeners;
}
