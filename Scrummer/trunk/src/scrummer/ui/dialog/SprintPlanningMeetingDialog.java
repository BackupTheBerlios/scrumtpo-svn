package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.listener.SprintBacklogListener;
import scrummer.model.DeveloperModel;
import scrummer.model.Models;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.EmployeeComboBoxModel;
import scrummer.model.swing.PBIComboBoxModel;
import scrummer.model.swing.TaskComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

public class SprintPlanningMeetingDialog 
	extends TwoButtonDialog
	implements SprintBacklogListener {

	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public SprintPlanningMeetingDialog(Frame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change sprint backlog item"));
		
		Models m = Scrummer.getModels();
		_sprintbacklogModel = m.getSprintBacklogModel();
		_sprintbacklogModel.addSprintBacklogListener(this);
		_taskComboModel = m.getTaskModel().getTaskComboBoxModel();
		
		DeveloperModel devModel = m.getDeveloperModel();
		_empComboModel = devModel.getEmployeeComboBoxModel();
		_pbiComboModel = m.getProductBacklogModel().getPBIComboBoxModel();
		
		int k = 10;
		Panel.setLayout(new GridLayout(6, 6, 10, 12));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel taskLbl = new JLabel(i18n.tr("Choose task") + ":");
		StandardComboBox taskInput = new StandardComboBox();
		taskInput.setIVModel(_taskComboModel);
		_taskInput = taskInput;
		
		Panel.add(taskLbl);
		Panel.add(taskInput);
		
		JLabel empLbl = new JLabel(i18n.tr("Choose employee") + ":");
		StandardComboBox empInput = new StandardComboBox();
		empInput.setIVModel(_empComboModel);
		_empInput = empInput;
		
		Panel.add(empLbl);
		Panel.add(empInput);
		
		JLabel pbiLbl = new JLabel(i18n.tr("Choose product backlog item") + ":");
		StandardComboBox pbiInput = new StandardComboBox();
		pbiInput.setIVModel(_pbiComboModel);
		_pbiInput = pbiInput;
		
		Panel.add(pbiLbl);
		Panel.add(pbiInput);
		
		_sprintInput = addEntry(i18n.tr("Set sprint") + ":", "NewSprint");
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 4));
		
		OK.setText("Change");
		setSize(new Dimension(460, 360));
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
		
		if (e.getActionCommand() == "StandardDialog.OK")
		{
				String pbi = _pbiInput.getSelectedItem().toString().trim();
				String sprint = _sprintInput.getText().trim();
				String employee = _empInput.getSelectedItem().toString().trim();
				
				if (pbi.length() > 0)
				{
					if (_taskInput.isSelected() && 
						_pbiInput.isSelected() && 
						_empInput.isSelected())
					{
						int id = _taskInput.getSelectedId();
						int pbi_id = _pbiInput.getSelectedId();
						int emp_id = _empInput.getSelectedId();
						if(sprint.length() > 0)
						{
							if(employee.length() > 0)
							{
								_sprintbacklogModel.setTaskProp(id, pbi_id, sprint, emp_id);
							}
							else
							{
								Util.showError(this, i18n.tr("You have to select one item!"), i18n.tr("Error"));
							}
						}
						else
						{
							Util.showError(this, i18n.tr("Sprint must be at least one character long."), i18n.tr("Error"));
						}
					}
					else
					{
						Util.showError(this, i18n.tr("Some task must be selected to set it's product backlog item."), i18n.tr("Error"));
					}
				}
				else
				{
					Util.showError(this, i18n.tr("You have to select one item!"), i18n.tr("Error"));
				}
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {
		switch (type)
		{
		case Update:
		
			switch (identifier)
			{
			case Task:
				_taskComboModel.refresh();
				_taskInput.setSelectedIndex(0);
				setVisible(false);
				break;
			}
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {
		
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case Task:
				Util.showError(this, 
					i18n.tr("An error has occurred when setting sprint backlog properties") + ": " + message, 
					i18n.tr("Error"));
				break;
			}
			break;
		}
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			//_sprintbacklogModel.removeSprintBacklogListner(this);
		}
		
		super.setVisible(b);
	}

	/// sprint backlog model
	private SprintBacklogModel _sprintbacklogModel;
	/// all tasks in combo box
	private TaskComboBoxModel _taskComboModel;
	/// all employeed in combo box
	private EmployeeComboBoxModel _empComboModel;
	/// all pbis in combo box
	private PBIComboBoxModel _pbiComboModel;
	/// team new name input
	private JTextField _sprintInput;
	/// team input combo box
	private StandardComboBox _taskInput, _empInput, _pbiInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
