package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ProductBacklogModel;
import scrummer.model.SprintBacklogModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class SprintBacklogAddDialog extends TwoButtonDialog {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public SprintBacklogAddDialog(JFrame owner) throws SQLException
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add Developer"));
		
		_sprintbacklogModel = Scrummer.getModels().getSprintBacklogModel();
		
		Panel.setLayout(new GridLayout(7, 5, 0, 10));
		
		_taskdescriptionTextField = addEntry(i18n.tr("Task description") + ":", "Task description");
		_tasktypeTextField = addEntry(i18n.tr("Task type") + ":", "Task type");
		_taskstatusTextField = addEntry(i18n.tr("Task status") + ":", "Task status");
		_taskdateTextField = addEntry(i18n.tr("Task date") + ":", "Task active");
		_taskactiveTextField = addEntry(i18n.tr("Task active") + ":", "Task active");
		_PBIidTextField = addEntry(i18n.tr("PBI id") + ":", "PBI id");
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
			throw new RuntimeException("Kle je blo par sintaksnih napak!");
			/*
			_sprintbacklogModel.add(
				_taskdescriptionTextField.getText(),
				Integer.parseInt(_tasktypeTextField.getText()),
				Integer.parseInt(_taskstatusTextField.getText()),
				_taskdateTextField.getText(),
				Integer.parseInt(_taskactiveTextField.getText()),
				Integer.parseInt(_PBIidTextField.getText()), 
				Integer.parseInt(_hoursspentTextField.getText()),
				Integer.parseInt(_hoursremainingTextField.getText()),
				Integer.parseInt(_nbopenimpedTextField.getText()),
				Integer.parseInt(_nbclosedimpedTextField.getText()));
			*/
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	/// product backlog model
	private SprintBacklogModel _sprintbacklogModel;
	/// name text field
	private JTextField _taskdescriptionTextField, _tasktypeTextField, _taskstatusTextField, _taskdateTextField, _taskactiveTextField, _PBIidTextField, _hoursspentTextField, _hoursremainingTextField, _nbopenimpedTextField, _nbclosedimpedTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
