package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.listener.ImpedimentListener;
import scrummer.model.DBSchemaModel.ImpedimentEnum;
import scrummer.ui.Util;
import scrummer.ui.Validate;
import scrummer.uicomponents.SelectedTextField;

/**
 * Impediment change dialog
 */
public class ImpedimentsChangeDialog 
	extends ImpedimentDialog 
	implements ImpedimentListener 
{
	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public ImpedimentsChangeDialog(JFrame owner, int impId)
	{
		super(owner);
		
		_impId = impId;
		setTitle(i18n.tr("Change impediment"));
		_impedimentModel = Scrummer.getModels().getImpedimentModel();
		_impedimentModel.addImpedimentListener(this);
		
		int teamId = _impedimentModel.getInteger(ImpedimentEnum.TeamId, impId); 
		_teamInput.selectId(teamId);
		int empId = _impedimentModel.getInteger(ImpedimentEnum.EmployeeId, impId); 
		_empInput.selectId(empId);
		_descriptionTextField.setText(_impedimentModel.getString(ImpedimentEnum.ImpedimentDescription, impId));
		int typeId = _impedimentModel.getInteger(ImpedimentEnum.ImpedimentType, impId); 
		_impTypeInput.selectId(typeId);
		int statusId = _impedimentModel.getInteger(ImpedimentEnum.ImpedimentStatus, impId);
		_impStatusInput.selectId(statusId);
		
		_ageTextField.setText(new Integer(_impedimentModel.getInteger(ImpedimentEnum.ImpedimentAge, impId)).toString());
			
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date d1 = _impedimentModel.getDate(ImpedimentEnum.ImpedimentStart, impId);
		
		_startTextField.setText((d1 == null) ? "" : df.format(d1));
		Date d2 = _impedimentModel.getDate(ImpedimentEnum.ImpedimentEnd, impId);
		_endTextField.setText((d2 == null) ? "" : df.format(d2));
		
		setSize(570, 310);		
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
			int team = _teamInput.getSelectedId();
			int employee = _empInput.getSelectedId();
			int task = _taskInput.getSelectedId();
			if (!Validate.emptyMsg(_descriptionTextField, this)) return;
			String desc = _descriptionTextField.getText().trim();
			
			int type = _impTypeInput.getSelectedId();
			int status = _impStatusInput.getSelectedId();
			int age = Validate.number(_ageTextField);
			
			Date startDate = Validate.date(_startTextField, i18n.tr("Wrong starting date"), this);
			Date endDate = Validate.date(_endTextField, i18n.tr("Wrong ending date."), this);
			
			if ((startDate == null) || (endDate == null)) return; 
			
			int id = _impId;
			_impedimentModel.update(id, team, employee, task, desc, type, status, startDate, endDate, age);
			
			setVisible(false);
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Update) && (identifier == ImpedimentOperation.Impediment))
		{
			_impedimentComboModel.refresh();			
			setVisible(false);
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Update) && (identifier == ImpedimentOperation.Impediment))
		{
			Util.showError(this, 
				i18n.tr("An error has occurred when setting updating impediment") + ": " + message, 
				i18n.tr("Error"));
		}
	}
	
	/// impediment id
	private int _impId;	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
