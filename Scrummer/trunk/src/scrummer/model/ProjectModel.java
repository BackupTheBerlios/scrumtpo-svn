package scrummer.model;

import java.util.Vector;

import scrummer.listener.ProjectListener;

/**
 * Project creation and loading data model 
 */
public class ProjectModel {

	/**
	 * Add listener to connection related events
	 * @param listener listener
	 */
	public void addConnectionListener(ProjectListener listener)
	{
		if (!_projectListeners.contains(listener))
		{
			_projectListeners.add(listener);
		}
	}
	
	/**
	 * Remove listener to connection related events
	 * @param listener listener
	 */
	public void removeConnectionListener(ProjectListener listener)
	{
		_projectListeners.remove(listener);
	}
	
	/// project event listeners
	private Vector<ProjectListener> _projectListeners;
	
	
}
