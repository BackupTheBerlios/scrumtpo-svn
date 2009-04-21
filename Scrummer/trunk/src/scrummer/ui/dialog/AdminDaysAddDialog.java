package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.AdminDaysOperation;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.listener.AdminDaysListener;
import scrummer.listener.SprintBacklogListener;
import scrummer.model.AdminDaysModel;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.AbsenceTypeComboBoxModel;
import scrummer.model.swing.EmployeeComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class AdminDaysAddDialog 
	extends TwoButtonDialog
	implements AdminDaysListener, SprintBacklogListener 
{	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public AdminDaysAddDialog(JFrame owner) throws SQLException 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add administrative day"));
		
		_admindaysModel = Scrummer.getModels().getAdminDaysModel();
		_admindaysModel.addAdminDaysListener(this);
		
		_sbModel = Scrummer.getModels().getSprintBacklogModel();
		_sbModel.addSprintBacklogListener(this);
		
		_empComboModel = _sbModel.getEmpComboBoxModel();
		
		JLabel empLbl = new JLabel(i18n.tr("Choose employee") + ":");
		JComboBox empInput = new JComboBox();
		empInput.setModel(_empComboModel);
		_empInput = empInput;
		_empComboModel.refresh();
		
		Panel.add(empLbl);
		Panel.add(empInput);
		
		_absComboModel = _admindaysModel.getAbsenceTypeComboBoxModel();
		
		JLabel absLbl = new JLabel(i18n.tr("Choose absence type") + ":");
		JComboBox absInput = new JComboBox();
		absInput.setModel(_absComboModel);
		_absInput = absInput;
		_absComboModel.refresh();
		
		Panel.add(absLbl);
		Panel.add(absInput);
		
		Panel.setLayout(new GridLayout(5, 5, 0, 13));
		
		_hoursnotworkedTextField = addEntry(i18n.tr("Hours not worked") + ":", "HoursNotWorked");
		_dayTextField = addEntry(i18n.tr("Measure day") + ":", "MeasureDay");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add administrative day"), 
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
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			int employee, absence, hours, day;
			employee = _empComboModel.getId(_empInput.getSelectedIndex());
			absence = _absComboModel.getId(_absInput.getSelectedIndex());
			hours = Integer.parseInt(_hoursnotworkedTextField.getText().trim());
			day = Integer.parseInt(_dayTextField.getText().trim());
			_admindaysModel.add(employee, absence, hours, day);
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
	public void operationFailed(DataOperation type, AdminDaysOperation identifier, String message) {
		JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void operationSucceeded(DataOperation type, AdminDaysOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			setVisible(false);
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type,
			SprintBacklogOperation identifier, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void operationSucceeded(DataOperation type,
			SprintBacklogOperation identifier, String message) {
		// TODO Auto-generated method stub
		
	}
	
	/// administrative days model
	private AdminDaysModel _admindaysModel;
	/// name text field
	private JTextField _hoursnotworkedTextField, _dayTextField;
	/// sprint backlog model
	private SprintBacklogModel _sbModel;
	/// combo box models
	private JComboBox _empInput, _absInput;
	/// all employeed in combo box
	private EmployeeComboBoxModel _empComboModel;
	/// all absence types in combo box
	private AbsenceTypeComboBoxModel _absComboModel;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}