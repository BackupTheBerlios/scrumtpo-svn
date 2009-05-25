package scrummer.ui.dialog.codelist;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskTypeOperation;
import scrummer.listener.TaskTypeListener;
import scrummer.model.TaskTypeModel;
import scrummer.model.swing.TaskTypeComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove task type dialog
 */
public class TaskTypeRemoveDialog extends TwoButtonDialog
								 implements TaskTypeListener 
{
	/**
	 * Constructor
	 * 
	 * @param owner parent frame
	 */
	public TaskTypeRemoveDialog(Frame owner) 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Remove task type"));
		
		_tasktypeModel = Scrummer.getModels().getTaskTypeModel();
		_tasktypeComboBoxModel = _tasktypeModel.getTaskTypeComboBoxModel();
		
		_tasktypeModel.addTaskTypeListener(this);
		
		int k = 10;
		
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Select task type"),
				0, k, k, k));
		
		_formBuilder = new FormBuilder(Panel);
		_tasktypeInput = _formBuilder.addComboBoxInput(i18n.tr("Task type") + ":");	
		_tasktypeInput.setIVModel(_tasktypeComboBoxModel);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k));
		
		setSize(new Dimension(320, 150));
		Util.centre(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK")
		{
			if (_tasktypeInput.isSelected())
			{				
				Integer id = _tasktypeInput.getSelectedId();
				_tasktypeModel.removeTaskType(id.toString());
				_tasktypeComboBoxModel.refresh();
			}
			else
			{
				Util.showError(
					this, 
					i18n.tr("Could not delete task type. Select one task type first, " +
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
	public void operationSucceeded(DataOperation type, TaskTypeOperation identifier, String message) 
	{
		switch (type)
		{
		case Remove:
			switch (identifier)
			{
			case TaskType:
				setVisible(false);
				break;
			}
			break;
		}
	}	
	
	@Override
	public void operationFailed(DataOperation type, TaskTypeOperation identifier, String message) {
		Util.showError(this, i18n.tr("Could not remove task type: " + message), i18n.tr("Error"));
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			_tasktypeModel.removeTaskTypeListener(this);
		}
		
		super.setVisible(b);
	}
	
	/// task type input combo box
	private StandardComboBox _tasktypeInput;
	/// task type model
	private TaskTypeModel _tasktypeModel;
	/// task type combo box model
	private TaskTypeComboBoxModel _tasktypeComboBoxModel;
	/// form building class
	private FormBuilder _formBuilder;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 4206239821521998017L;
}
