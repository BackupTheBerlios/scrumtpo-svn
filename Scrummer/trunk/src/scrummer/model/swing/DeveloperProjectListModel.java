package scrummer.model.swing;

import javax.swing.DefaultListModel;

public class DeveloperProjectListModel extends DefaultListModel {

	/**
	 * Constructor
	 */
	public DeveloperProjectListModel() {
	
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
		// _projectList = _projectModel.fetchProjects();
	}
	
	/**
	 * Fetch id for given row(project)
	 * 
	 * @param rowIndex row index
	 * @return row index
	 */
	public int getId(int rowIndex)
	{
		return 0;
		// return _projectList.elementAt(rowIndex).Id;
	}
	
	@Override
	public Object elementAt(int index) {
		return null;
	//	return _projectList.elementAt(index).Name;
	}
	
	@Override
	public Object get(int index) {
		return null;
	//	return _projectList.get(index).Name;
	}

	@Override
	public Object getElementAt(int index) {
		return null;
	//	return _projectList.get(index).Name;
	}
	
	@Override
	public int size() {
		return 0;
		// return _projectList.size();
	}

	@Override
	public int getSize() {
		return 0;
		// return _projectList.size();
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
