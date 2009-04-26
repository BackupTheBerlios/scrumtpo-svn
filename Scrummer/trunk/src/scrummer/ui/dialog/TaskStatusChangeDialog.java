package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskStatusOperation;
import scrummer.listener.TaskStatusListener;
import scrummer.model.TaskStatusModel;
import scrummer.model.swing.TaskStatusComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

public class TaskStatusChangeDialog 
	extends TwoButtonDialog
	implements TaskStatusListener 
	{
	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public TaskStatusChangeDialog(Frame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change absence type"));
		
		_taskstatusModel = Scrummer.getModels().getTaskStatusModel();
		_taskstatusModel.addTaskStatusListener(this);
		
		_taskstatusComboModel = _taskstatusModel.getTaskStatusComboBoxModel();
		
		int k = 10;
		Panel.setLayout(new GridLayout(6, 6, 10, 12));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel absLbl = new JLabel(i18n.tr("Task type") + ":");
		StandardComboBox absInput = new StandardComboBox();
		absInput.setIVModel(_taskstatusComboModel);
		_absInput = absInput;
		
		Panel.add(absLbl);
		Panel.add(absInput);
		
		_descInput = addEntry(i18n.tr("New description") + ":", "NewDescription");
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 4));
		
		OK.setText("Change");
		setSize(new Dimension(460, 310));
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
	public void actionPerformed(ActionEvent e) 
	{	
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			String desc = _descInput.getText().trim();
			if (_absInput.isSelected())
			{			
				_taskstatusModel.setNewDesc(_absInput.getSelectedId(), desc);
			}
			else
			{
				Util.showError(this, i18n.tr("Some task status must be selected to change it's description."), i18n.tr("Error"));
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, TaskStatusOperation identifier, String message) {
		switch (type)
		{
		case Update:
		
			switch (identifier)
			{
			case TaskStatus:
				_taskstatusComboModel.refresh();
				_absInput.setSelectedIndex(0);
				setVisible(false);
				break;
			}
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, TaskStatusOperation identifier, String message) 
	{
		
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case TaskStatus:
				Util.showError(this, 
					i18n.tr("An error has occurred when setting description") + ": " + message, 
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
			_taskstatusModel.removeTaskStatusListener(this);
		}
		
		super.setVisible(b);
	}

	/// task status model
	private TaskStatusModel _taskstatusModel;
	/// all task statuses in combo box
	private TaskStatusComboBoxModel _taskstatusComboModel;
	/// description new name input
	private JTextField _descInput;
	/// task status input combo box
	private StandardComboBox _absInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;
}
