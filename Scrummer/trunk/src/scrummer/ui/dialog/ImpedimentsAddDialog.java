package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import scrummer.enumerator.DeveloperOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.listener.ImpedimentListener;
import scrummer.listener.OperationListener;
import scrummer.model.DeveloperModel;
import scrummer.model.ImpedimentModel;
import scrummer.model.swing.EmployeeComboBoxModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class ImpedimentsAddDialog 
	extends TwoButtonDialog
	implements ImpedimentListener {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public ImpedimentsAddDialog(JFrame owner) throws SQLException 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add Impediment"));
		
		_impedimentModel = Scrummer.getModels().getImpedimentModel();
		_impedimentModel.addImpedimentListener(this);
		
		/*_developerModel = Scrummer.getModels().getDeveloperModel();
		_developerModel.addDeveloperListener(new OperationListener<DeveloperOperation>);
		_teamComboBoxModel = _developerModel.getTeamComboBoxModel();
		
		_teamComboBox.setModel(_teamComboBoxModel);
		_teamComboBoxModel.refresh();
		
		Panel.add(_teamComboBox);*/
		
		Panel.setLayout(new GridLayout(5, 5, 0, 13));
		
		_teamTextField    = addEntry(i18n.tr("Team")    + ":", "Team");
		_sprintTextField = addEntry(i18n.tr("Sprint") + ":", "Sprint");
		_employeeTextField = addEntry(i18n.tr("Employee") + ":", "Employee");
		_taskTextField = addEntry(i18n.tr("Task") + ":", "Task");
		_descriptionTextField = addEntry(i18n.tr("Description") + ":", "Description");
		_typeTextField = addEntry(i18n.tr("Type") + ":", "Type");
		_statusTextField = addEntry(i18n.tr("Status") + ":", "Status");
		_startTextField = addEntry(i18n.tr("Start") + ":", "Start");
		_endTextField = addEntry(i18n.tr("End") + ":", "End");
		_ageTextField = addEntry(i18n.tr("Age") + ":", "Age");
		
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add Impediment"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(570, 310));
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); 
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			java.util.Date startI, endI;
			try {
				startI = dateFormat.parse(_startTextField.getText());
				endI = dateFormat.parse(_endTextField.getText());
				java.sql.Date sqlDate1 = new java.sql.Date(startI.getTime());
				java.sql.Date sqlDate2 = new java.sql.Date(endI.getTime());
				_impedimentModel.add(
						Integer.parseInt(_teamTextField.getText()),
						Integer.parseInt(_sprintTextField.getText()), 
						Integer.parseInt(_employeeTextField.getText()), 
						Integer.parseInt(_taskTextField.getText()),
						_descriptionTextField.getText(),
						_typeTextField.getText(),
						_statusTextField.getText(),
						sqlDate1,
						sqlDate2,
						Integer.parseInt(_ageTextField.getText()));
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
	public void setVisible(boolean b) {
		
		if (!b)
		{
			// _impedimentModel.
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
	
	/// impediment model
	private ImpedimentModel _impedimentModel;
	/// developer model
	/*private DeveloperModel _developerModel;
	/// combo box models
	private JComboBox _employeeComboBox, _teamComboBox;
	// team combo box model
	private TeamComboBoxModel _teamComboBoxModel;*/
	/// name text field
	private JTextField _teamTextField, _sprintTextField, _employeeTextField, _taskTextField, _descriptionTextField, _typeTextField, _statusTextField, _startTextField, _endTextField, _ageTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}