package scrummer.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.listener.ImpedimentListener;
import scrummer.model.ImpedimentModel;
import scrummer.model.DBSchemaModel.ImpedimentEnum;
import scrummer.model.swing.ImpedimentComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

public class ImpedimentsChangeDialog extends TwoButtonDialog implements ImpedimentListener 
{
	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public ImpedimentsChangeDialog(Frame owner, int impId)
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		
		_impId = impId;
		setTitle(i18n.tr("Change impediment"));
		_impedimentModel.addImpedimentListener(this);
		
		_teamInput.setText(_impedimentModel.getString(ImpedimentEnum.TeamId, impId));
		_sprintInput.setText(_impedimentModel.getString(ImpedimentEnum.SprintId, impId));
		_sprintInput.setText(_impedimentModel.getString(ImpedimentEnum.EmployeeId, impId));
		_employeeInput.setText(_impedimentModel.getString(ImpedimentEnum.TaskId, impId));
		_descInput.setText(_impedimentModel.getString(ImpedimentEnum.ImpedimentDescription, impId));
		_typeInput.setText(_impedimentModel.getString(ImpedimentEnum.ImpedimentType, impId));
		_statusInput.setText(_impedimentModel.getString(ImpedimentEnum.ImpedimentStatus, impId));
		_ageInput.setText(_impedimentModel.getString(ImpedimentEnum.ImpedimentAge, impId));
			
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date d1 = _impedimentModel.getDate(ImpedimentEnum.ImpedimentStart, impId);
		_startInput.setText((d1 == null) ? "" : df.format(d1));
		Date d2 = _impedimentModel.getDate(ImpedimentEnum.ImpedimentEnd, impId);
		_endInput.setText((d2 == null) ? "" : df.format(d2));
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
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); 
			java.util.Date startI, endI;
			try {
				String team = _teamInput.getText().trim();
				String sprint = _sprintInput.getText().trim();
				String employee = _employeeInput.getText().trim();
				String task = _taskInput.getText().trim();
				String desc = _descInput.getText().trim();
				String type = _typeInput.getText().trim();
				String status = _statusInput.getText().trim();
				String age = _ageInput.getText().trim();
				
				if (team.length() > 0)
				{
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentTeam(id, team);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's team."), i18n.tr("Error"));
					}
				}
				if(sprint.length() > 0)
				{
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentSprint(id, sprint);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's sprint."), i18n.tr("Error"));
					}
				}
				if(employee.length() > 0)
				{
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentEmployee(id, employee);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's employee."), i18n.tr("Error"));
					}
				}
				if(task.length() > 0)
				{
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentTask(id, task);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's task."), i18n.tr("Error"));
					}
				}
				if(desc.length() > 0)
				{
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentDesc(id, desc);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's description."), i18n.tr("Error"));
					}
				}
				if(type.length() > 0)
				{
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentType(id, type);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's type."), i18n.tr("Error"));
					}
				}
				if(status.length() > 0)
				{
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentStatus(id, status);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's status."), i18n.tr("Error"));
					}
				}
				if(_startInput.getText().trim().length() > 0)
				{
					startI = dateFormat.parse(_startInput.getText());
					java.sql.Date start = new java.sql.Date(startI.getTime());
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentStart(id, start);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's start."), i18n.tr("Error"));
					}
				}
				if(_endInput.getText().trim().length() > 0)
				{
					endI = dateFormat.parse(_endInput.getText());
					java.sql.Date end = new java.sql.Date(endI.getTime());
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentEnd(id, end);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's end."), i18n.tr("Error"));
					}
				}
				if(age.length() > 0)
				{
					int selected = _impInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _impedimentComboModel.getId(selected);
						_impedimentModel.setImpedimentAge(id, age);
					}
					else
					{
						Util.showError(this, i18n.tr("Some impediment must be selected to change it's age."), i18n.tr("Error"));
					}
				}
				/*else
				{
					Util.showError(this, i18n.tr("Impediment must be at least one character long."), i18n.tr("Error"));
				}*/
				
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
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		switch (type)
		{
		case Update:
		
			switch (identifier)
			{
			case Team:
				_impedimentComboModel.refresh();
				_impInput.setSelectedIndex(0);
				setVisible(false);
				break;
			}
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case Team:
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
			_impedimentModel.removeImpedimentListener(this);
		}
		else
		{
			if (_impedimentComboModel.getSize() == 0)
			{
				_teamInput.setEnabled(false);
			}
			else
			{
				_impInput.setEnabled(true);
				_impInput.setSelectedIndex(0);
			}
		}
		
		super.setVisible(b);
	}
	
	/// impediment id
	private int _impId;
	/// impediment model
	private ImpedimentModel _impedimentModel;
	/// all impediments in combo box
	private ImpedimentComboBoxModel _impedimentComboModel;
	/// team new name input
	private JTextField _teamInput, _sprintInput, _taskInput, _employeeInput, _descInput, _typeInput, _statusInput, _startInput, _endInput, _ageInput;
	/// team input combo box
	private JComboBox _impInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
