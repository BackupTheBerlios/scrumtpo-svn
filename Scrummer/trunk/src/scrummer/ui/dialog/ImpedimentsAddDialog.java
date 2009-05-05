package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.enumerator.TaskOperation;
import scrummer.listener.ImpedimentListener;
import scrummer.listener.SprintBacklogListener;
import scrummer.listener.TaskListener;
import scrummer.model.DeveloperModel;
import scrummer.model.ImpedimentModel;
import scrummer.model.SprintBacklogModel;
import scrummer.model.TaskModel;
import scrummer.model.swing.EmployeeComboBoxModel;
import scrummer.model.swing.ProjectSprintTaskComboBoxModel;
import scrummer.model.swing.SprintProjectComboBoxModel;
import scrummer.model.swing.TaskComboBoxModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.ui.Util;
import scrummer.ui.Validate;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class ImpedimentsAddDialog 
	extends ImpedimentDialog
	 {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public ImpedimentsAddDialog(Frame owner) 
	{
		super(owner);
		setTitle(i18n.tr("Add Impediment"));		
		OK.setText(i18n.tr("Add"));
		setSize(570, 310);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			java.util.Date startI = Validate.date(_startTextField, i18n.tr("Wrong starting date."), this);			 
			java.util.Date endI = Validate.date(_endTextField, i18n.tr("Wrong end date."), this);
			
			int age = 0;
			if (Validate.empty(_ageTextField, this))
			{
				age = Integer.parseInt(_ageTextField.getText());
			}
			
			_impedimentModel.add(
				_teamInput.getSelectedId(), 
				_empInput.getSelectedId(), 
				_taskInput.getSelectedId(),
				_descriptionTextField.getText(),
				_impTypeInput.getSelectedId(),
				_impStatusInput.getSelectedId(),
				(startI != null) ? new java.sql.Date(startI.getTime()) : null,
				(endI != null) ? new java.sql.Date(endI.getTime()) : null,
				age);
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
			_impedimentModel.removeImpedimentListener(this);
		}

		super.setVisible(b);
	}

	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			setVisible(false);
			break;
		}
	}

	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
