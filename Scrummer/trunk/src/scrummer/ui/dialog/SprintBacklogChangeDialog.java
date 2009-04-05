package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.listener.OperationListener;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.SBIComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

public class SprintBacklogChangeDialog 
	extends TwoButtonDialog
	implements OperationListener<SprintBacklogOperation> {

	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public SprintBacklogChangeDialog(Frame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change sprint backlog item"));
		
		_sprintbacklogModel = Scrummer.getModels().getSprintBacklogModel();
		_sprintbacklogModel.addSprintBacklogListener(this);
		
		//_sprintbacklogComboModel = _sprintbacklogModel.getSBIComboBoxModel();
		
		int k = 10;
		Panel.setLayout(new GridLayout(6, 6, 10, 12));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel sbiLbl = new JLabel(i18n.tr("Sprint backlog item") + ":");
		JComboBox sbiInput = new JComboBox();
		sbiInput.setModel(_sprintbacklogComboModel);
		_sbiInput = sbiInput;
		_sprintbacklogComboModel.refresh();
		
		Panel.add(sbiLbl);
		Panel.add(sbiInput);
		
		_measuredayInput = addEntry(i18n.tr("New measure day") + ":", "NewMeasureDay");
		_pbiInput = addEntry(i18n.tr("New product backlog item") + ":", "NewPBI");
		_taskInput = addEntry(i18n.tr("New task") + ":", "NewTask");
		_sprintInput = addEntry(i18n.tr("New sprint") + ":", "NewSprint");
		_employeeInput = addEntry(i18n.tr("New employee") + ":", "NewEmployee");
		_hoursspentInput = addEntry(i18n.tr("New spent hours") + ":", "NewHoursSpent");
		_hoursremainInput = addEntry(i18n.tr("New remaining hours") + ":", "NewHoursRemain");
		_nbopenimpedInput = addEntry(i18n.tr("New number of open impediments") + ":", "NewNbOpenImped");
		_nbclosedimpedInput = addEntry(i18n.tr("New number of closed impediments") + ":", "NewNbClosedImped");
		
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
				String day = _measuredayInput.getText().trim();
				String pbi = _pbiInput.getText().trim();
				String task = _taskInput.getText().trim();
				String sprint = _sprintInput.getText().trim();
				String employee = _employeeInput.getText().trim();
				String hoursspent = _hoursspentInput.getText().trim();
				String hoursremain = _hoursremainInput.getText().trim();
				String nbopenimped = _nbopenimpedInput.getText().trim();
				String nbclosedimped = _nbclosedimpedInput.getText().trim();
				
				/*if (day.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBIDay(id, day);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's measure day."), i18n.tr("Error"));
					}
				}
				else if(pbi.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBIPBI(id, pbi);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's product backlog item."), i18n.tr("Error"));
					}
				}
				else if(task.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBITask(id, task);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's task."), i18n.tr("Error"));
					}
				}
				else if(sprint.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBISprint(id, sprint);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's priority."), i18n.tr("Error"));
					}
				}
				else if(employee.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBIEmployee(id, employee);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's employee."), i18n.tr("Error"));
					}
				}
				if(hoursspent.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBIHoursSpent(id, hoursspent);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's spent hours."), i18n.tr("Error"));
					}
				}
				if(hoursremain.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBIHoursRemain(id, hoursremain);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's remaining hours."), i18n.tr("Error"));
					}
				}
				if(nbopenimped.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBINbOpenImped(id, nbopenimped);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's number of open impediments."), i18n.tr("Error"));
					}
				}
				if(hoursremain.length() > 0)
				{
					int selected = _sbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _sprintbacklogComboModel.getId(selected);
						_sprintbacklogModel.setSBINbClosedImped(id, nbclosedimped);
					}
					else
					{
						Util.showError(this, i18n.tr("Some sprint backlog item must be selected to change it's number of closed impediments."), i18n.tr("Error"));
					}
				}
				else
				{
					Util.showError(this, i18n.tr("PBI must be at least one character long."), i18n.tr("Error"));
				}*/
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
			case Sprint:
				_sprintbacklogComboModel.refresh();
				_sbiInput.setSelectedIndex(0);
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
			case Sprint:
				Util.showError(this, 
					i18n.tr("An error has occurred when setting team name") + ": " + message, 
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
		else
		{
			if (_sprintbacklogComboModel.getSize() == 0)
			{
				_sbiInput.setEnabled(false);
			}
			else
			{
				_sbiInput.setEnabled(true);
				_sbiInput.setSelectedIndex(0);
			}
		}
		
		super.setVisible(b);
	}

	/// sprint backlog model
	private SprintBacklogModel _sprintbacklogModel;
	/// all SBI in combo box
	private SBIComboBoxModel _sprintbacklogComboModel;
	/// team new name input
	private JTextField _measuredayInput, _pbiInput,  _taskInput, _sprintInput, _employeeInput, _hoursspentInput, _hoursremainInput, _nbopenimpedInput, _nbclosedimpedInput;
	/// team input combo box
	private JComboBox _sbiInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
