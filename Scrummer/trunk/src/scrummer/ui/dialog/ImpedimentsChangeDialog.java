package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.listener.ImpedimentListener;
import scrummer.listener.OperationListener;
import scrummer.model.ImpedimentModel;
import scrummer.model.swing.ImpedimentComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove some team from database
 */
public class ImpedimentsChangeDialog 
	extends TwoButtonDialog
	implements ImpedimentListener {

	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public ImpedimentsChangeDialog(Frame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change impediment"));
		
		_impedimentModel = Scrummer.getModels().getImpedimentModel();
		_impedimentModel.addImpedimentListener(this);
		
		_impedimentComboModel = _impedimentModel.getImpedimentComboBoxModel();
		
		int k = 10;
		Panel.setLayout(new GridLayout(6, 6, 10, 12));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel impLbl = new JLabel(i18n.tr("Impediment") + ":");
		JComboBox impInput = new JComboBox();
		impInput.setModel(_impedimentComboModel);
		_impInput = impInput;
		_impedimentComboModel.refresh();
		
		Panel.add(impLbl);
		Panel.add(impInput);
		
		_teamInput = addEntry(i18n.tr("New team") + ":", "NewTeam");
		_sprintInput = addEntry(i18n.tr("New sprint") + ":", "NewSprint");
		_employeeInput = addEntry(i18n.tr("New employee") + ":", "NewEmployee");
		_taskInput = addEntry(i18n.tr("New task") + ":", "NewTask");
		_descInput = addEntry(i18n.tr("New description") + ":", "NewDescription");
		_typeInput = addEntry(i18n.tr("New type") + ":", "NewType");
		_statusInput = addEntry(i18n.tr("New status") + ":", "NewStatus");
		_startInput = addEntry(i18n.tr("New start") + ":", "NewStart");
		_endInput = addEntry(i18n.tr("New end") + ":", "NewEnd");
		_ageInput = addEntry(i18n.tr("New age") + ":", "NewAge");
		
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

	/// developer model
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
