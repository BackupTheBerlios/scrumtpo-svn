package scrummer.model.swing;

import java.util.Vector;

import scrummer.model.ProjectModel;
import scrummer.model.SprintBacklogModelCommon;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Display all sprints on some project
 */
public class SprintProjectComboBoxModel extends IdValueComboBoxModel {

	/**
	 * Constructor
	 * 
	 * @param sprintBacklogModelCommon common model for data ops.
	 * @param projectModel project model for getting project id
	 */
	public SprintProjectComboBoxModel(SprintBacklogModelCommon sprintBacklogModelCommon, ProjectModel projectModel) {
		_sprintBacklogModelCommon = sprintBacklogModelCommon;
		_projectModel = projectModel;
	}
	
	@Override
	public void refresh() {
		refreshRows();
		super.refresh();
	}

	/**
	 * Refresh rows
	 */
	private void refreshRows()
	{
		setValues(_sprintBacklogModelCommon.fetchSprintDescriptions(_projectModel.getCurrentProjectId()));
		// Vector<Integer> sprints = _sprintBacklogModelCommon.fetchSprints(_projectModel.getCurrentProjectId());
		// setValues(sprints);
		/*
		Vector<IdValue> duplicated = new Vector<IdValue>();
		for (int i = 0; i < sprints.size(); i++)
		{
			IdValue current = new IdValue(sprints.get(i), sprints.get(i).toString());
			duplicated.add(current);
		}
		setValues(duplicated);
		*/
	}

	/// project model
	private ProjectModel _projectModel;
	/// common sprint backlog operations model
	private SprintBacklogModelCommon _sprintBacklogModelCommon;
	/// serialization id
	private static final long serialVersionUID = 8567946224297304431L;
}
