package scrummer.model;

import java.util.Vector;

import scrummer.listener.ConnectionListener;

/** 
 * Handles connection related data
 * 
 * Handles data required for connection. Also handles connection loss.
 */
public class ConnectionModel {

	/**
	 * Add listener to connection related events
	 * @param listener listener
	 */
	public void addConnectionListener(ConnectionListener listener)
	{
		if (!_connectionListeners.contains(listener))
		{
			_connectionListeners.add(listener);
		}
	}
	
	/**
	 * Remove listener to connection related events
	 * @param listener listener
	 */
	public void removeConnectionListener(ConnectionListener listener)
	{
		_connectionListeners.remove(listener);
	}
	
	private Vector<ConnectionListener> _connectionListeners;
	
}
