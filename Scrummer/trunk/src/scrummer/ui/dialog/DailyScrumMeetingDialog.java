// vstavljanje v del sprint backloga
// najprej izbereš ustrezni task (glede na task_description)
// nato izbereš dan za katerega vnašaš porabljene/preostale ure ter ovire
// nato imaš možnost vnesti "spent_hours", "remaining_hours", "nb_open_imped", "nb_closed_imped"
// na koncu vse shraniš

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
import scrummer.listener.OperationListener;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.TaskComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

public class DailyScrumMeetingDialog 
	extends TwoButtonDialog
	implements OperationListener<SprintBacklogOperation> {

	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public DailyScrumMeetingDialog(Frame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Daily scrum meeting"));
		
		_sprintbacklogModel = Scrummer.getModels().getSprintBacklogModel();
		//_sprintbacklogModel.addSprintBacklogListener(this);
		
		_taskComboModel = _sprintbacklogModel.getTaskComboBoxModel();
		
		int k = 10;
		Panel.setLayout(new GridLayout(6, 6, 10, 12));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel taskLbl = new JLabel(i18n.tr("Task") + ":");
		JComboBox taskInput = new JComboBox();
		taskInput.setModel(_taskComboModel);
		_taskInput = taskInput;
		_taskComboModel.refresh();
		
		Panel.add(taskLbl);
		Panel.add(taskInput);
		
		_measuredayInput = addEntry(i18n.tr("Measure day") + ":", "MeasureDay");
		_hoursspentInput = addEntry(i18n.tr("Spent hours") + ":", "HoursSpent");
		_hoursremainInput = addEntry(i18n.tr("Remaining hours") + ":", "HoursRemain");
		_nbopenimpedInput = addEntry(i18n.tr("Number of open impediments") + ":", "NbOpenImped");
		_nbclosedimpedInput = addEntry(i18n.tr("Number of closed impediments") + ":", "NbClosedImped");
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 4));
		
		OK.setText("Save");
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
				String hoursspent = _hoursspentInput.getText().trim();
				String hoursremain = _hoursremainInput.getText().trim();
				String nbopenimped = _nbopenimpedInput.getText().trim();
				String nbclosedimped = _nbclosedimpedInput.getText().trim();
				
				if (day.length() > 0)
				{
					int selected = _taskInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _taskComboModel.getId(selected);
						if(hoursspent.length() > 0 && hoursremain.length() > 0 && nbopenimped.length() > 0 && nbclosedimped.length() > 0)
						{
							if (_sprintbacklogModel.existsTaskInSBI(id))
								_sprintbacklogModel.setTaskMeasures(id, Integer.parseInt(day), Integer.parseInt(hoursspent), Integer.parseInt(hoursremain), Integer.parseInt(nbopenimped), Integer.parseInt(nbclosedimped));
							else
								Util.showError(this, i18n.tr("First insert task into your sprint backlog and then set it's measures!"), i18n.tr("Error"));
						}
					}
					else
					{
						Util.showError(this, i18n.tr("Some task must be selected to set it's measures."), i18n.tr("Error"));
					}
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
			if (_taskComboModel.getSize() == 0)
			{
				_taskInput.setEnabled(false);
			}
			else
			{
				_taskInput.setEnabled(true);
				_taskInput.setSelectedIndex(0);
			}
		}
		
		super.setVisible(b);
	}

	/// sprint backlog model
	private SprintBacklogModel _sprintbacklogModel;
	/// all SBI in combo box
	private TaskComboBoxModel _taskComboModel;
	/// team new name input
	private JTextField _measuredayInput, _hoursspentInput, _hoursremainInput, _nbopenimpedInput, _nbclosedimpedInput;
	/// team input combo box
	private JComboBox _taskInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
