package scrummer.model;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.listener.NavigationListener;

/**
 * Navigation data model
 * 
 * Contains and manages current, last and home navigation links.
 */
public class NavigationModel {

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
		/// project options
		ProjectOptions,
		/// developers on project
		ProjectDevelopers,		
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
	 */
	public NavigationModel()
	{	
		_top = Link.Overview;
		
		_translatedLinks.put(Link.Blank, 				i18n.tr("Blank"));
		_translatedLinks.put(Link.Overview, 			i18n.tr("Overview"));
		_translatedLinks.put(Link.ProductBacklog, 		i18n.tr("Product Backlog"));
		_translatedLinks.put(Link.Project, 				i18n.tr("Project"));
		_translatedLinks.put(Link.ProjectDevelopers, 	i18n.tr("Developers"));
		_translatedLinks.put(Link.ProjectOptions, 		i18n.tr("Options"));
		_translatedLinks.put(Link.SprintBacklog, 		i18n.tr("Sprint Backlog"));
		_translatedLinks.put(Link.SprintBacklogAbsent,  i18n.tr("Absent"));
		_translatedLinks.put(Link.SprintBacklogHurdles, i18n.tr("Hurdles"));
		_translatedLinks.put(Link.SprintBacklogMetric,  i18n.tr("Metrics"));
		_translatedLinks.put(Link.SprintBacklogTasks,   i18n.tr("Tasks"));
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
		_navigationSemaphore.acquireUninterruptibly();
		if (!_navigationListeners.contains(listener))
		{
			_navigationListeners.add(listener);
		}
		_navigationSemaphore.release();
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
	
	/// previous navigation link
	private Vector<Link> _previous = new Vector<Link>();
	/// current navigation link
	private Link _current = Link.Blank;
	/// top navigation link
	private Link _top = Link.Blank;
	/// translated link names(displayed)
	private HashMap<Link, String> _translatedLinks = new HashMap<Link, String>();
	/// synchronization primitive for listener manipulation
	private Semaphore _navigationSemaphore = new Semaphore(1); 
	/// navigation event listeners
	private Vector<NavigationListener> _navigationListeners = new Vector<NavigationListener>();
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());;
}
