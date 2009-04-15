package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;

/**
 * Sprint addition form
 */
public class SprintAddDialog 
	extends SprintDialog {

	/**
	 * Constructor
	 * 
	 * @param owner owning frame
	 */
	public SprintAddDialog(Frame owner) {
		super(owner);
	
		setTitle(i18n.tr("Add Sprint"));
	
		GregorianCalendar gc = new GregorianCalendar();
		String todayStr = 
			gc.get(Calendar.DAY_OF_MONTH) + "." + 
			(gc.get(Calendar.MONTH) - Calendar.JANUARY + 1) + "." + 
			gc.get(Calendar.YEAR);
		
		gc.add(Calendar.MONTH, 1);
		String nextMonthStr = 
			gc.get(Calendar.DAY_OF_MONTH) + "." + 
			(gc.get(Calendar.MONTH) - Calendar.JANUARY + 1) + "." + 
			gc.get(Calendar.YEAR);
		
		_startDateInput.setText(todayStr);		
		_estimatedInput.setText(nextMonthStr);
		_endDateInput.setText(nextMonthStr);
		
		OK.setText(i18n.tr("Add"));
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 7));
		
		setSize(new Dimension(300, 275));
	}
	
	@Override
	public void setVisible(boolean b) {
		
		if (b)
		{
			if (_teamComboBoxModel.getSize() > 0)
			{
				_teamInput.setSelectedIndex(0);
			}
			else
			{
				_teamInput.setEnabled(false);
			}
		}
		else
		{
			_sprintBacklogModel.removeSprintBacklogListener(this);
		}
		
		super.setVisible(b);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK")
		{		
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			
			try {
				_sprintBacklogModel.addSprint(
					_descriptionInput.getText(),
					_teamComboBoxModel.getId(_teamInput.getSelectedIndex()),
					formatter.parse(_startDateInput.getText()),
					formatter.parse(_endDateInput.getText()),
					Integer.parseInt(_lengthInput.getText()),
					formatter.parse(_estimatedInput.getText()));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (ParseException e1) {				
				e1.printStackTrace();
			}
		}
		else
		{
			super.actionPerformed(e);	
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {
		switch (type)
		{
		case Insert:
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
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {}

	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5484362646565900921L;
	
}
