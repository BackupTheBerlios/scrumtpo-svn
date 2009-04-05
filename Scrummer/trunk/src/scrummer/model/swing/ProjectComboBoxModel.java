package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import scrummer.model.ProjectModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

/**
 * Combo box model that interfaces all projects
 */
public class ProjectComboBoxModel extends DefaultComboBoxModel {
	
	/**
	 * Constructor
	 * 
	 * @param projectModelCommon common project operations
	 */
	public ProjectComboBoxModel(ProjectModelCommon projectModelCommon) {
		_projectModelCommon = projectModelCommon;
	}
	
	/**
	 * Refresh data in this model
	 */
	public void refresh()
	{
		refreshProjects();
		fireContentsChanged(this, 0, getSize() - 1);
	}
	
	/**
	 * Fetch all projects
	 */
	private void refreshProjects()
	{
		_projects = _projectModelCommon.fetchProjects();
	}
		
	/**
	 * Insert given element 
	 * 
	 * @param value value to add
	 */
	public void addValue(IdValue value)
	{
		_projects.add(value);
		fireContentsChanged(this, 0, getSize());
	}
	
	public IdValue getValue(int index)
	{
		return _projects.get(index);
	}
		
	@Override
	public Object getElementAt(int index) {
		return _projects.get(index).Value;
	}
	
	@Override
	public void removeElement(Object obj) {
		_projects.remove(obj);
		fireContentsChanged(this, 0, getSize());
		// return ret;
	}

	@Override
	public void removeElementAt(int index) {
		_projects.remove(index);
		fireContentsChanged(this, 0, getSize());
	}

	@Override
	public void removeAllElements() {
		_projects.clear();
		fireContentsChanged(this, 0, getSize());
	}

	@Override
	public int getSize() {
		return _projects.size();
	}
	
	/// employee list
	private Vector<IdValue> _projects = new Vector<IdValue>();
	/// common project model ops.
	private ProjectModelCommon _projectModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6049086390826746852L;
}
