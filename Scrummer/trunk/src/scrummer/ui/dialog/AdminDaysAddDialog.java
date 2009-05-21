package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
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
import scrummer.model.DeveloperModel;
import scrummer.model.Models;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.AbsenceTypeComboBoxModel;
import scrummer.model.swing.EmployeeComboBoxModel;
import scrummer.ui.Util;
import scrummer.ui.Validate;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
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
	public AdminDaysAddDialog(JFrame owner) 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		setTitle(i18n.tr("Add administrative day"));
		
		Models m = Scrummer.getModels();
		_admindaysModel = m.getAdminDaysModel();
		_admindaysModel.addAdminDaysListener(this);
		DeveloperModel devModel = m.getDeveloperModel(); 
		
		_sbModel = Scrummer.getModels().getSprintBacklogModel();
		_sbModel.addSprintBacklogListener(this);
		
		_empComboModel = devModel.getEmployeeComboBoxModel();
		
		JLabel empLbl = new JLabel(i18n.tr("Choose employee") + ":");
		StandardComboBox empInput = new StandardComboBox();
		empInput.setIVModel(_empComboModel);
		_empInput = empInput;
		
		Panel.add(empLbl);
		Panel.add(empInput);
		
		_absComboModel = _admindaysModel.getAbsenceTypeComboBoxModel();
		
		JLabel absLbl = new JLabel(i18n.tr("Choose absence type") + ":");
		StandardComboBox absInput = new StandardComboBox();
		absInput.setIVModel(_absComboModel);
		_absInput = absInput;
		
		Panel.add(absLbl);
		Panel.add(absInput);
		
		Panel.setLayout(new GridLayout(5, 5, 0, 13));
		
		_hoursnotworkedTextField = addEntry(i18n.tr("Hours not worked") + ":", "HoursNotWorked");
		_dayTextField = addEntry(i18n.tr("Measure day") + ":", "MeasureDay");
		_dayTextField.setText(Util.today());
		
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
	public JTextField addEntry(String labelText, String textActionCmd) {
		JLabel label = new JLabel(labelText);
		
		JTextField textBox = new SelectedTextField();
		
		Panel.add(label);
		Panel.add(textBox);
		
		return textBox;
	}
		
	@Override
	public void actionPerformed(ActionEvent e)  {
		if (e.getActionCommand() == "StandardDialog.OK") {
			int employee, absence, hours;
			employee = _empInput.getSelectedId();
			absence = _absInput.getSelectedId();
			hours = Integer.parseInt(_hoursnotworkedTextField.getText().trim());			
			Date d = Validate.date(_dayTextField, i18n.tr("Wrong date format."), this); if (d == null) return;
			_admindaysModel.add(employee, absence, hours, d);
		} else {
			super.actionPerformed(e);
		}
	}

	@Override
	public void setVisible(boolean b) {
		if (!b) {
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
		if (type == DataOperation.Insert) {
			setVisible(false);	
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {}

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {}
	
	/// administrative days model
	private AdminDaysModel _admindaysModel;
	/// name text field
	private JTextField _hoursnotworkedTextField, _dayTextField;
	/// sprint backlog model
	private SprintBacklogModel _sbModel;
	/// combo box models
	private StandardComboBox _empInput, _absInput;
	/// all employeed in combo box
	private EmployeeComboBoxModel _empComboModel;
	/// all absence types in combo box
	private AbsenceTypeComboBoxModel _absComboModel;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}