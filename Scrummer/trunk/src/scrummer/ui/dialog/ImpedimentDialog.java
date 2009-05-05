package scrummer.ui.dialog;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.enumerator.TaskOperation;
import scrummer.listener.ImpedimentListener;
import scrummer.listener.SprintBacklogListener;
import scrummer.listener.TaskListener;
import scrummer.model.DeveloperModel;
import scrummer.model.ImpedimentModel;
import scrummer.model.SprintBacklogModel;
import scrummer.model.TaskModel;
import scrummer.model.swing.ImpedimentComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

public class ImpedimentDialog 
	extends TwoButtonDialog 
	implements ImpedimentListener, SprintBacklogListener, TaskListener {

	public ImpedimentDialog(Frame parent)
	{
		super(parent, ModalityType.APPLICATION_MODAL);
		
		_impedimentModel = Scrummer.getModels().getImpedimentModel();
		_impedimentModel.addImpedimentListener(this);
		_impedimentComboModel = _impedimentModel.getImpedimentComboBoxModel();

		_developerModel = Scrummer.getModels().getDeveloperModel(); 

		_sbModel = Scrummer.getModels().getSprintBacklogModel();
		_sbModel.addSprintBacklogListener(this);
		
		_taskModel = Scrummer.getModels().getTaskModel();
		_taskModel.addTaskListener(this);		
		
		JLabel teamLbl = new JLabel(i18n.tr("Choose team") + ":");
		StandardComboBox teamInput = new StandardComboBox();
		teamInput.setIVModel(_developerModel.getTeamComboBoxModel());
		_teamInput = teamInput;
		
		Panel.add(teamLbl);
		Panel.add(teamInput);
		
		JLabel empLbl = new JLabel(i18n.tr("Choose employee") + ":");
		StandardComboBox empInput = new StandardComboBox();
		empInput.setIVModel(_developerModel.getEmployeeComboBoxModel());
		_empInput = empInput;
		
		Panel.add(empLbl);
		Panel.add(empInput);
		
		JLabel taskLbl = new JLabel(i18n.tr("Choose task") + ":");
		StandardComboBox taskInput = new StandardComboBox();
		taskInput.setIVModel(_taskModel.getProjectSprintTaskComboBoxModel());
		_taskInput = taskInput;
		
		Panel.add(taskLbl);
		Panel.add(taskInput);
		
		_descriptionTextField = addEntry(i18n.tr("Description") + ":", "Description");
		
		JLabel impTypeLbl = new JLabel(i18n.tr("Choose impediment type") + ":");
		_impTypeInput = new StandardComboBox();
		_impTypeInput.setIVModel(_impedimentModel.getImpedimentTypeComboBoxModel());
				
		Panel.add(impTypeLbl);
		Panel.add(_impTypeInput);
		
		JLabel impStatusLbl = new JLabel(i18n.tr("Choose impediment status") + ":");
		_impStatusInput = new StandardComboBox();
		_impStatusInput.setIVModel(_impedimentModel.getImpedimentStatusComboBoxModel());;
		Panel.add(impStatusLbl);
		Panel.add(_impStatusInput);
		
		_startTextField = addEntry(i18n.tr("Start") + ":", "Start");
		_startTextField.setText(Util.today());
		_endTextField = addEntry(i18n.tr("End") + ":", "End");
		//_endTextField.setText(Util.today());
		_ageTextField = addEntry(i18n.tr("Age") + ":", "Age");
		
		Panel.setLayout(new GridLayout(5, 5, 0, 13));
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add Impediment"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
	}
	
	/**
	 * Add form entry(label + textbox)
	 * 
	 * @param labelText label text
	 * @param textActionCmd text action command
	 * @return added text field
	 */
	public JTextField addEntry(String labelText, String textActionCmd)
	{
		JLabel label = new JLabel(labelText);
		
		JTextField textBox = new SelectedTextField();
		
		Panel.add(label);
		Panel.add(textBox);
		
		return textBox;
	}
	
	@Override
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {}

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {}

	@Override
	public void operationFailed(DataOperation type, TaskOperation identifier, String message) {}

	@Override
	public void operationSucceeded(DataOperation type, TaskOperation identifier, String message) {}
	
	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {}

	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {}
	
	@Override
	public void setVisible(boolean b) {
		if (!b)
		{
			_impedimentModel.removeImpedimentListener(this);
		}
		super.setVisible(b);
	}
	
	/// impediment model
	protected ImpedimentModel _impedimentModel;	
	/// all impediments in combo box
	protected ImpedimentComboBoxModel _impedimentComboModel;
	/// developer model
	protected DeveloperModel _developerModel;
	/// sprint backlog model
	protected SprintBacklogModel _sbModel;
	/// task model
	protected TaskModel _taskModel;
	/// combo box models
	protected StandardComboBox _teamInput, _empInput, _taskInput, _impTypeInput, _impStatusInput;
	/// name text field
	protected JTextField _descriptionTextField, _startTextField, _endTextField, _ageTextField;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2571891107362309751L;

}
