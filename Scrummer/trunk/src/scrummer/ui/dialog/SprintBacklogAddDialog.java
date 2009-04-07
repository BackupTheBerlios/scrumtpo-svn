package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.listener.OperationListener;
import scrummer.listener.SprintBacklogListener;
import scrummer.model.ProductBacklogModel;
import scrummer.model.SprintBacklogModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class SprintBacklogAddDialog 
	extends TwoButtonDialog
	implements SprintBacklogListener {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public SprintBacklogAddDialog(JFrame owner) throws SQLException
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Insert into Sprint Backlog"));
		
		_sprintbacklogModel = Scrummer.getModels().getSprintBacklogModel();
		_sprintbacklogModel.addSprintBacklogListener(this);
		
		Panel.setLayout(new GridLayout(7, 5, 0, 10));
		
		_taskdescriptionTextField = addEntry(i18n.tr("Task description") + ":", "Task description");
		_tasktypeTextField = addEntry(i18n.tr("Task type") + ":", "Task type");
		_taskstatusTextField = addEntry(i18n.tr("Task status") + ":", "Task status");
		_taskdateTextField = addEntry(i18n.tr("Task date") + ":", "Task active");
		JLabel label = new JLabel("Task active:");
		String[] yesno = {"Yes","No"};
		_taskactiveComboBox = new JComboBox(yesno);
		Panel.add(label);
		Panel.add(_taskactiveComboBox);
		
		//_taskactiveTextField = addEntry(i18n.tr("Task active") + ":", "Task active");
		_measuredayTextField = addEntry(i18n.tr("Day") + ":", "Day");
		_PBIidTextField = addEntry(i18n.tr("PBI id") + ":", "PBI id");
		_sprintTextField = addEntry(i18n.tr("Sprint id") + ":", "Sprint id");
		_employeeTextField = addEntry(i18n.tr("Employee id") + ":", "Employee id");
		_hoursspentTextField = addEntry(i18n.tr("Hours spent") + ":", "Hours spent");
		_hoursremainingTextField = addEntry(i18n.tr("Hours remaining") + ":", "Hours remaining");
		_nbopenimpedTextField = addEntry(i18n.tr("Number of open impediments") + ":", "Number of open impediments");
		_nbclosedimpedTextField = addEntry(i18n.tr("Number of closed impediments") + ":", "Number of closed impediments");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Insert into Sprint Backlog"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(570, 410));
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
	
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			_sprintbacklogModel.add(
				_taskdescriptionTextField.getText(),
				Integer.parseInt(_tasktypeTextField.getText()),
				Integer.parseInt(_taskstatusTextField.getText()),
				_taskdateTextField.getText(),
				_taskactiveComboBox.getSelectedItem().toString(),
				//_taskactiveTextField.getText(),
				Integer.parseInt(_measuredayTextField.getText()),
				Integer.parseInt(_PBIidTextField.getText()),
				Integer.parseInt(_sprintTextField.getText()),
				Integer.parseInt(_employeeTextField.getText()),
				Integer.parseInt(_hoursspentTextField.getText()),
				Integer.parseInt(_hoursremainingTextField.getText()),
				Integer.parseInt(_nbopenimpedTextField.getText()),
				Integer.parseInt(_nbclosedimpedTextField.getText()));
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
			// _productbacklogModel.
		}

		super.setVisible(b);
	}
	
	@Override
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {
		JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			setVisible(false);
			break;
		}
	}

	/// product backlog model
	private SprintBacklogModel _sprintbacklogModel;
	/// combo boxes
	private JComboBox _taskactiveComboBox;
	/// name text field
	private JTextField _taskdescriptionTextField, _tasktypeTextField, _taskstatusTextField, _taskdateTextField, _taskactiveTextField, _measuredayTextField, _PBIidTextField, _sprintTextField, _employeeTextField, _hoursspentTextField, _hoursremainingTextField, _nbopenimpedTextField, _nbclosedimpedTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
