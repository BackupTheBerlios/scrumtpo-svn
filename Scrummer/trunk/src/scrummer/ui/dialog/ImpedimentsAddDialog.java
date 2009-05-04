package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import scrummer.model.swing.EmployeeComboBoxModel;
import scrummer.model.swing.ProjectSprintTaskComboBoxModel;
import scrummer.model.swing.SprintProjectComboBoxModel;
import scrummer.model.swing.TaskComboBoxModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class ImpedimentsAddDialog 
	extends TwoButtonDialog
	implements ImpedimentListener, SprintBacklogListener, TaskListener {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public ImpedimentsAddDialog(JFrame owner) 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		setTitle(i18n.tr("Add Impediment"));
		
		OK.setText(i18n.tr("Add"));
		
		_impedimentModel = Scrummer.getModels().getImpedimentModel();
		_impedimentModel.addImpedimentListener(this);

		_developerModel = Scrummer.getModels().getDeveloperModel(); 

		_sbModel = Scrummer.getModels().getSprintBacklogModel();
		_sbModel.addSprintBacklogListener(this);
		
		_taskModel = Scrummer.getModels().getTaskModel();
		_taskModel.addTaskListener(this);
		
		_empComboBoxModel = _developerModel.getEmployeeComboBoxModel();
		_teamComboBoxModel = _developerModel.getTeamComboBoxModel();
		_sprintComboBoxModel = _sbModel.getSprintProjectComboBoxModel();
		_taskComboBoxModel = _taskModel.getProjectSprintTaskComboBoxModel();
		
		JLabel teamLbl = new JLabel(i18n.tr("Choose team") + ":");
		StandardComboBox teamInput = new StandardComboBox();
		teamInput.setIVModel(_teamComboBoxModel);
		_teamInput = teamInput;
		
		Panel.add(teamLbl);
		Panel.add(teamInput);
		
		JLabel empLbl = new JLabel(i18n.tr("Choose employee") + ":");
		StandardComboBox empInput = new StandardComboBox();
		empInput.setIVModel(_empComboBoxModel);
		_empInput = empInput;
		
		Panel.add(empLbl);
		Panel.add(empInput);
		
		JLabel taskLbl = new JLabel(i18n.tr("Choose task") + ":");
		StandardComboBox taskInput = new StandardComboBox();
		taskInput.setIVModel(_taskComboBoxModel);
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
		
		setSize(new Dimension(570, 310));
		Util.centre(this);
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
	public void actionPerformed(ActionEvent e) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); 
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			java.util.Date startI, endI;
			try {
				startI = dateFormat.parse(_startTextField.getText());
				endI = dateFormat.parse(_endTextField.getText());
				java.sql.Date sqlDate1 = new java.sql.Date(startI.getTime());
				java.sql.Date sqlDate2 = new java.sql.Date(endI.getTime());
				_impedimentModel.add(
						_teamInput.getSelectedId(), 
						_empInput.getSelectedId(), 
						_taskInput.getSelectedId(),
						_descriptionTextField.getText(),
						_impTypeInput.getSelectedId(),
						_impStatusInput.getSelectedId(),
						sqlDate1,
						sqlDate2,
						Integer.parseInt(_ageTextField.getText()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			_impedimentModel.removeImpedimentListener(this);
		}

		super.setVisible(b);
	}

	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			setVisible(false);
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {}

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {}

	@Override
	public void operationFailed(DataOperation type, TaskOperation identifier, String message) {}

	@Override
	public void operationSucceeded(DataOperation type, TaskOperation identifier, String message) {}
	
	/// impediment model
	private ImpedimentModel _impedimentModel;
	/// developer model
	private DeveloperModel _developerModel;
	/// sprint backlog model
	private SprintBacklogModel _sbModel;
	/// task model
	private TaskModel _taskModel;
	/// combo box models
	private StandardComboBox _teamInput, _empInput, _taskInput, _impTypeInput, _impStatusInput;
	// team combo box model
	private TeamComboBoxModel _teamComboBoxModel;
	/// employee combo box model
	private EmployeeComboBoxModel _empComboBoxModel;
	/// sprint combo box model
	private SprintProjectComboBoxModel _sprintComboBoxModel;
	/// task combo box model
	private ProjectSprintTaskComboBoxModel _taskComboBoxModel;
	/// impediment type combo box model
	
	///impediment status combo box model
	
	/// name text field
	private JTextField _descriptionTextField, _startTextField, _endTextField, _ageTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
