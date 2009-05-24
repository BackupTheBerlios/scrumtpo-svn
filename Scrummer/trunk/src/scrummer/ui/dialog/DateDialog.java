package scrummer.ui.dialog;

import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.ui.Validate;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * A dialog that displays a date
 */
public class DateDialog 
	   extends TwoButtonDialog {

	public DateDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
		
		int k = 10;
		Panel.setBorder(Util.createSpacedTitleBorder(
			k, k, k, k, 
			i18n.tr("Interval"), 
			4, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);
		
		_dateInput =
			fb.addSelectedTextInput(i18n.tr("Date") + ":", "Date"); 		
		fb.setCellSpacing(5, 5);
		
		String today = Util.today();
		_dateInput.setText(today);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k, k - 4));
		
		setSize(320, 155);
	}

	/**
	 * Do dates contain valid values, if not - display error messages
	 * @return true if dates valid, false otherwise
	 */
	protected boolean valid() {
		boolean ret = true;
		ret = ret & Validate.empty(_dateInput, this);
		ret = ret & (Validate.date(_dateInput, i18n.tr("Invalid date format."), this) != null);
		return ret;
	}
	
	/**
	 * @return first date
	 */
	protected Date getDate() {
		return Validate.date(_dateInput);
	}
	
	/// date inputs
	protected SelectedTextField _dateInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -3531660897791794270L;
}