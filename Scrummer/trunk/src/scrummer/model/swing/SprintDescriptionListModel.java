package scrummer.model.swing;

import scrummer.model.ProjectModel;
import scrummer.model.SprintBacklogModelCommon;
import scrummer.model.swing.base.IdValueListModel;

/**
 * A list model that displays Sprint descriptions while also holding corresponding id's on 
 * current project. 
 */
public class SprintDescriptionListModel extends IdValueListModel {
	/**
	 * Constructor
	 * 
	 * @param sprintBacklogCommon common sprint data ops.
	 * @param projectModel project model
	 */
	public SprintDescriptionListModel(SprintBacklogModelCommon sprintBacklogCommon, ProjectModel projectModel) {		
		_sprintBacklogCommon = sprintBacklogCommon;
		_projectModel = projectModel;
	}
	
	@Override
	public void refresh() {
		refreshRows();
		super.refresh();
	}

	/**
	 * Fetch sprint id/description pairs from db.
	 */
	private void refreshRows() {
		setValues(_sprintBacklogCommon.fetchSprintDescriptions(_projectModel.getCurrentProjectId()));
	}

	/// sprint backlog model
	private SprintBacklogModelCommon _sprintBacklogCommon;
	/// project model
	private ProjectModel _projectModel;
	/// serialization id
	private static final long serialVersionUID = 8434242422528715931L;
}
