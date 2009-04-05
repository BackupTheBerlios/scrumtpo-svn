package scrummer.model.swing;

import java.util.Vector;
import javax.swing.DefaultListModel;
import scrummer.model.ProjectModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * Represents a list of available projects
 */
public class ProjectListModel extends DefaultListModel {
	
	/**
	 * Constructor
	 */
	public ProjectListModel(ProjectModelCommon projectModel) {
		_projectModel = projectModel;
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
		_projectList = _projectModel.fetchProjects();
	}
	
	/**
	 * Fetch id for given row(project)
	 * 
	 * @param rowIndex row index
	 * @return row index
	 */
	public int getId(int rowIndex)
	{
		return _projectList.elementAt(rowIndex).Id;
	}
	
	@Override
	public Object elementAt(int index) {
		return _projectList.elementAt(index).Value;
	}
	
	@Override
	public Object getElementAt(int index) {
		return _projectList.elementAt(index).Value;
	}

	@Override
	public Object get(int index) {
		return _projectList.get(index).Value;
	}
	
	@Override
	public int size() {
		return _projectList.size();
	}

	@Override
	public int getSize() {
		return _projectList.size();
	}

	/// project model
	private ProjectModelCommon _projectModel;
	/// project list
	private Vector<IdValue> _projectList = 
		new Vector<IdValue>();
	/// serialization id
	private static final long serialVersionUID = -4159843185529045767L;
}
