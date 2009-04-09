package scrummer.model;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.NavigationListener;
import scrummer.listener.ProjectListener;

/**
 * Navigation data model
 * 
 * Contains and manages current, last and home navigation links.
 */
public class NavigationModel implements ProjectListener {

	/**
	 * Possible links
	 */
	public enum Link
	{
		/// blank link
		Blank,
		/// application overview
		Overview,
		/// project overview
		Project,		
		/// product backlog
		ProductBacklog,
		/// sprint backlog
		SprintBacklog,
		/// sprint backlog - tasks
		SprintBacklogTasks,
		/// sprint backlog - metrics 
		SprintBacklogMetric,
		/// sprint backlog - absent
		SprintBacklogAbsent,
		/// sprint backlog - hurdles
		SprintBacklogHurdles
	}
	
	/**
	 * Constructor
	 * 
	 * @param projectModel project model
	 */
	public NavigationModel(ProjectModel projectModel)
	{	
		_top = Link.Overview;
		
		_translatedLinks.put(Link.Blank, 				i18n.tr("Blank"));
		_translatedLinks.put(Link.Overview, 			i18n.tr("Overview"));
		_translatedLinks.put(Link.ProductBacklog, 		i18n.tr("Product Backlog"));
		_translatedLinks.put(Link.Project, 				i18n.tr("Project"));
		_translatedLinks.put(Link.SprintBacklog, 		i18n.tr("Sprint Backlog"));
		_translatedLinks.put(Link.SprintBacklogAbsent,  i18n.tr("Absent"));
		_translatedLinks.put(Link.SprintBacklogHurdles, i18n.tr("Hurdles"));
		_translatedLinks.put(Link.SprintBacklogMetric,  i18n.tr("Metrics"));
		_translatedLinks.put(Link.SprintBacklogTasks,   i18n.tr("Tasks"));
		
		projectModel.addProjectListener(this);
	}
	
	/**
	 * Fetch translated name of link
	 * @param link link
	 * @return translated name
	 */
	public String getName(Link link)
	{
		return _translatedLinks.get(link);
	}
	
	/**
	 * Johnny-go-home
	 */
	public void home()
	{
		switchPage(_top);
	}
	
	/**
	 * Rupel-be-back
	 */
	public void back()
	{
		if (_previous.size() > 0)
		{
			Link last = _previous.get(_previous.size() - 1);
			_previous.removeElementAt(_previous.size() - 1);
			
			_current  = last;
			pageChanged(_current);
		}
	}
	
	/**
	 * Switch to different page
	 * @param newPage page to be switched
	 */
	public void switchPage(Link newPage)
	{
		_previous.add(_current);
		_current  = newPage;
			
		pageChanged(_current);
	}
	
	/**
	 * Notify everyone that page changed
	 * @param newLink new link
	 */
	private void pageChanged(Link newLink)
	{
		_navigationSemaphore.acquireUninterruptibly();
		for (int i = 0; i < _navigationListeners.size(); i++)
		{
			_navigationListeners.get(i).pageChanged(newLink);
		}
		_navigationSemaphore.release();
	}
	
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
		_navigationSemaphore.acquireUninterruptibly();
		_navigationListeners.remove(listener);
		_navigationSemaphore.release();
	}
	
	@Override
	public void operationSucceeded(DataOperation type, ProjectOperation identifier, String message) {
		switch (type)
		{
		case Custom:
			switch (identifier)
			{
			case Open:
				home();
				break;
			case Close:
				switchPage(NavigationModel.Link.Blank);
				break;
			}
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, ProjectOperation identifier, String message) {}
	
	/// previous navigation link
	private Vector<Link> _previous = new Vector<Link>();
	/// current navigation link
	private Link _current = Link.Overview;
	/// top navigation link
	private Link _top = Link.Overview;
	/// translated link names(displayed)
	private HashMap<Link, String> _translatedLinks = new HashMap<Link, String>();
	/// synchronization primitive for listener manipulation
	private Semaphore _navigationSemaphore = new Semaphore(1); 
	/// navigation event listeners
	private Vector<NavigationListener> _navigationListeners = new Vector<NavigationListener>();
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
