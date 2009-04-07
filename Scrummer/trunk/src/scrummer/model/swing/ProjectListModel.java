package scrummer.model.swing;

import java.util.Vector;
import javax.swing.DefaultListModel;
import scrummer.model.ProjectModelCommon;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.model.swing.base.IdValueListModel;

/**
 * Represents a list of available projects
 */
public class ProjectListModel extends IdValueListModel {
	
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
		setValues(_projectModel.fetchProjects());
	}
	
	/// project model
	private ProjectModelCommon _projectModel;
	/// serialization id
	private static final long serialVersionUID = -4159843185529045767L;
}
