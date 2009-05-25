package scrummer.ui.dialog.codelist;

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
import scrummer.enumerator.TaskTypeOperation;
import scrummer.listener.TaskTypeListener;
import scrummer.model.TaskTypeModel;
import scrummer.model.swing.TaskTypeComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

public class TaskTypeChangeDialog 
	extends TwoButtonDialog
	implements TaskTypeListener 
	{
	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public TaskTypeChangeDialog(Frame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change absence type"));
		
		_tasktypeModel = Scrummer.getModels().getTaskTypeModel();
		_tasktypeModel.addTaskTypeListener(this);
		
		_tasktypeComboModel = _tasktypeModel.getTaskTypeComboBoxModel();
		
		int k = 10;
		Panel.setLayout(new GridLayout(6, 6, 10, 12));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel absLbl = new JLabel(i18n.tr("Task type") + ":");
		StandardComboBox absInput = new StandardComboBox();
		absInput.setIVModel(_tasktypeComboModel);
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
				_tasktypeModel.setNewDesc(_absInput.getSelectedId(), desc);
			}
			else
			{
				Util.showError(this, i18n.tr("Some task type must be selected to change it's description."), i18n.tr("Error"));
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, TaskTypeOperation identifier, String message) {
		switch (type)
		{
		case Update:
		
			switch (identifier)
			{
			case TaskType:
				_tasktypeComboModel.refresh();
				_absInput.setSelectedIndex(0);
				setVisible(false);
				break;
			}
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, TaskTypeOperation identifier, String message) 
	{
		
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case TaskType:
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
			_tasktypeModel.removeTaskTypeListener(this);
		}
		
		super.setVisible(b);
	}

	/// task type model
	private TaskTypeModel _tasktypeModel;
	/// all task types in combo box
	private TaskTypeComboBoxModel _tasktypeComboModel;
	/// description new name input
	private JTextField _descInput;
	/// absence type input combo box
	private StandardComboBox _absInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;
}
