package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.DeveloperModel;
import scrummer.model.Models;
import scrummer.model.TaskModel;
import scrummer.model.swing.EmployeeComboBoxModel;
import scrummer.model.swing.TaskStatusComboBoxModel;
import scrummer.model.swing.TaskTypeComboBoxModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.model.swing.base.BooleanComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Base task dialog for addition/modification
 */
public class TaskDialog extends TwoButtonDialog {

	/**
	 * Constructor
	 * 
	 * @param owner owner frame
	 */
	public TaskDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
	
		Models m = Scrummer.getModels();
		_taskModel = 
			m.getTaskModel();
		_employeeModel = 
			m.getDeveloperModel();
		_employeeComboBoxModel = 
			_employeeModel.getEmployeeComboBoxModel();
		_teamComboBoxModel =
			_employeeModel.getTeamComboBoxModel();
		_taskStatusComboBoxModel =
			m.getTaskStatusModel().getTaskStatusComboBoxModel();
		_taskTypeComboBoxModel =
			m.getTaskTypeModel().getTaskTypeComboBoxModel();
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Task"), 
				0, k - 4, k - 4, k));
		
		FormBuilder fb = new FormBuilder(Panel);
		fb.setCellSpacing(k, k);
	
		_descriptionInput = 
			fb.addSelectedTextInput(i18n.tr("Description") + ":", "Description");
		_pbiInput =
			fb.addComboBoxInput(i18n.tr("PBI") + ":");
		_parentInput =
			fb.addComboBoxInput(i18n.tr("Parent") + ":");
		_taskEmployeeInput = 
			fb.addComboBoxInput(i18n.tr("Employee") + ":");		
		_taskTeamInput = 
			fb.addComboBoxInput(i18n.tr("Team") + ":");
		_taskTypeInput = 
			fb.addComboBoxInput(i18n.tr("Type") + ":");
		_taskStatusInput = 
			fb.addComboBoxInput(i18n.tr("Status") + ":");
		_dateInput = 
			fb.addSelectedTextInput(i18n.tr("Date") + ":", "Date");
		_taskActiveInput = 
			fb.addComboBoxInput(i18n.tr("Active") + ":");
		
		_pbiInput.setIVModel(m.getProductBacklogModel().getProjectSprintPBIComboBoxModel());
		_parentInput.setIVModel(m.getTaskModel().getProjectSprintTaskComboBoxModel());
		_parentInput.addItem(" ");
		_taskEmployeeInput.setIVModel(_employeeComboBoxModel);
		_taskTeamInput.setIVModel(_teamComboBoxModel);
		_taskTypeInput.setIVModel(_taskTypeComboBoxModel);
		_taskStatusInput.setIVModel(_taskStatusComboBoxModel);		
		
		_taskActiveModel = new BooleanComboBoxModel(i18n.tr("Yes"), i18n.tr("No"));
		_taskActiveInput.setModel(_taskActiveModel);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 7));
		
		setSize(new Dimension(300, 390));
	}
	
	/// description and status gui inputs
	protected JTextField _descriptionInput, _dateInput;
	/// combo box inputs
	protected StandardComboBox _parentInput, _pbiInput, _taskActiveInput, _taskEmployeeInput, _taskTeamInput, _taskTypeInput, _taskStatusInput; 
	/// is task active combo box model
	protected BooleanComboBoxModel _taskActiveModel;
	/// developer model
	protected DeveloperModel _employeeModel;
	/// employee combo box model
	protected EmployeeComboBoxModel _employeeComboBoxModel;
	/// team combo box model
	protected TeamComboBoxModel _teamComboBoxModel;
	/// task status combo box model
	protected TaskStatusComboBoxModel _taskStatusComboBoxModel;
	/// task type combo box model
	protected TaskTypeComboBoxModel _taskTypeComboBoxModel;
	/// task model
	protected TaskModel _taskModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 1882592100410179528L;
}
