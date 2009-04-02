package scrummer.model.swing;

import javax.swing.DefaultListModel;

import scrummer.model.DeveloperModelCommon;

/**
 * All developers that are not on current project
 */
public class DeveloperFreeModel extends DefaultListModel {

	/**
	 * Constructor
	 * 
	 * @param developerModelCommon common developer operations
	 */
	public DeveloperFreeModel(DeveloperModelCommon developerModelCommon)
	{
		
	}

	/**
	 * Refresh data in this model
	 */
	public void refresh()
	{
		refreshProjects();
		fireContentsChanged(this, 0, size() - 1);
	}
	
	/**
	 * Refresh project names
	 */
	private void refreshProjects()
	{
		
	}
	
	/**
	 * Fetch developer id for given row
	 * 
	 * @param rowIndex row index
	 * @return row index
	 */
	public int getId(int rowIndex)
	{
		return 0;
	}
	
	@Override
	public Object elementAt(int index) {
		return null;
	}
	
	@Override
	public Object get(int index) {
		return null;
	}

	@Override
	public Object getElementAt(int index) {
		return null;
	}
	
	@Override
	public int size() {
		return 0;
	}

	@Override
	public int getSize() {
		return 0;
	}

	/*
	/// project model
	private ProjectModelCommon _projectModel;
	/// project list
	private Vector<ProjectModelCommon.ProjectIdName> _projectList = 
		new Vector<ProjectModelCommon.ProjectIdName>();
	*/
	/// serialization id
	private static final long serialVersionUID = -4159843185529045767L;
	/// project table name 
	private static final String Project = "Project";
	
}
