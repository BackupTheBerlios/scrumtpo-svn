package scrummer.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.ui.Util;

/**
 * Sprint modification dialog
 */
public class SprintChangeDialog extends SprintDialog {

	/**
	 * Constructor
	 * 
	 * @param owner owning frame
	 * @param sprintId sprint id
	 */
	public SprintChangeDialog(Frame owner, int sprintId) {
		super(owner);
		setTitle(i18n.tr("Change Sprint"));
		_sprintId = sprintId;
		
		int select = _teamComboBoxModel.getIndex(_sprintBacklogModel.getTeam(sprintId));
		if (select != -1)
		{
			_teamInput.setSelectedIndex(select);
		}
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		_descriptionInput.setText(_sprintBacklogModel.getSprintDescription(sprintId));
		Date d = _sprintBacklogModel.getBeginDate(sprintId);
		_startDateInput.setText((d == null) ? null : df.format(d));
		
		d = _sprintBacklogModel.getEndDate(sprintId);
		_endDateInput.setText((d == null) ? null : df.format(d));
		
		_lengthInput.setText(new Integer(_sprintBacklogModel.getSprintLength(sprintId)).toString());
		
		d = _sprintBacklogModel.getSprintEstimated(sprintId);
		_estimatedInput.setText((d == null) ? null : df.format(d));
		
		OK.setText(i18n.tr("Update"));
	}
	
	@Override
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case Sprint:
				Util.showError(this, 
					i18n.tr("Error while saving sprint information") + ": " + message, 
					i18n.tr("Error"));
				break;
			}
			break;
		}	
	}

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case Sprint:
				setVisible(false);
				break;
			}
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK")
		{	
			boolean change = true;
			int selectedTeam = _teamInput.getSelectedIndex();
			if (selectedTeam != -1)
			{
				selectedTeam = _teamComboBoxModel.getId(selectedTeam);
			}
			else
			{
				change = false;
			}
			
			if (change)
			{
				SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
				
				try {
					_sprintBacklogModel.updateSprint(
						_sprintId, 
						selectedTeam,
						_descriptionInput.getText(),
						df.parse(_startDateInput.getText()),
						df.parse(_endDateInput.getText()),
						df.parse(_estimatedInput.getText()),
						Integer.parseInt(_lengthInput.getText()));
				} catch (NumberFormatException e1) {
					Util.showError(this, i18n.tr("Wrong sprint length number."), i18n.tr("Error"));
					e1.printStackTrace();
				} catch (ParseException e1) {
					Util.showError(this, i18n.tr("Invalid date entered."), i18n.tr("Error"));
					e1.printStackTrace();
				}
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	/// sprint id
	private int _sprintId;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 8400642500579725817L;
}
