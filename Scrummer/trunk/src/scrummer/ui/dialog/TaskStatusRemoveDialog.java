package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskStatusOperation;
import scrummer.listener.TaskStatusListener;
import scrummer.model.TaskStatusModel;
import scrummer.model.swing.TaskStatusComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove task status dialog
 */
public class TaskStatusRemoveDialog extends TwoButtonDialog
								 implements TaskStatusListener 
{
	/**
	 * Constructor
	 * 
	 * @param owner parent frame
	 */
	public TaskStatusRemoveDialog(Frame owner) 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Remove task status"));
		
		_taskstatusModel = Scrummer.getModels().getTaskStatusModel();
		_taskstatusComboBoxModel = _taskstatusModel.getTaskStatusComboBoxModel();
		
		_taskstatusModel.addTaskStatusListener(this);
		
		int k = 10;
		
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Select task status"),
				0, k, k, k));
		
		_formBuilder = new FormBuilder(Panel);
		_taskstatusInput = _formBuilder.addComboBoxInput(i18n.tr("Task status") + ":");	
		_taskstatusInput.setIVModel(_taskstatusComboBoxModel);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k));
		
		setSize(new Dimension(320, 150));
		Util.centre(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK")
		{
			if (_taskstatusInput.isSelected())
			{				
				Integer id = _taskstatusInput.getSelectedId();
				_taskstatusModel.removeTaskStatus(id.toString());
				_taskstatusComboBoxModel.refresh();
			}
			else
			{
				Util.showError(
					this, 
					i18n.tr("Could not delete task status. Select one task status first, " +
							"then click on Remove button."), 
					i18n.tr("Error"));
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, TaskStatusOperation identifier, String message) 
	{
		switch (type)
		{
		case Remove:
			switch (identifier)
			{
			case TaskStatus:
				setVisible(false);
				break;
			}
			break;
		}
	}	
	
	@Override
	public void operationFailed(DataOperation type, TaskStatusOperation identifier, String message) {
		Util.showError(this, i18n.tr("Could not remove task status: " + message), i18n.tr("Error"));
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			_taskstatusModel.removeTaskStatusListener(this);
		}
		
		super.setVisible(b);
	}
	
	/// task status input combo box
	private StandardComboBox _taskstatusInput;
	/// task status model
	private TaskStatusModel _taskstatusModel;
	/// task status combo box model
	private TaskStatusComboBoxModel _taskstatusComboBoxModel;
	/// form building class
	private FormBuilder _formBuilder;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 4206239821521998017L;
}
