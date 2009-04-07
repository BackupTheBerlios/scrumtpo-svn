package scrummer.model.swing;

import scrummer.model.ProjectModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Combo box model that interfaces all projects
 */
public class ProjectComboBoxModel extends IdValueComboBoxModel {
	
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
		setValues(_projectModelCommon.fetchProjects());
	}
	
	/// common project model ops.
	private ProjectModelCommon _projectModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6049086390826746852L;
}
