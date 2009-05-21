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
 * Date range input contains two date inputs
 */
public class DateRangeDialog 
	extends TwoButtonDialog {

	/**
	 * Constructor
	 * @param parent parent frame
	 */
	public DateRangeDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
		
		int k = 10;
		Panel.setBorder(Util.createSpacedTitleBorder(
			k, k, k, k, 
			i18n.tr("Interval"), 
			4, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);
		
		_firstDateInput =
			fb.addSelectedTextInput(i18n.tr("From") + ":", "First"); 
		_secondDateInput = 
			fb.addSelectedTextInput(i18n.tr("To") + ":", "Second");
		fb.setCellSpacing(5, 5);
		
		String today = Util.today();
		_firstDateInput.setText(today);
		_secondDateInput.setText(today);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k, k - 4));
		
		setSize(320, 175);
	}
	
	/**
	 * Do dates contain valid values, if not - display error messages
	 * @return true if dates valid, false otherwise
	 */
	protected boolean valid() {
		boolean ret = true;
		
		ret = ret & Validate.empty(_firstDateInput, this);
		ret = ret & Validate.empty(_secondDateInput, this);
		
		ret = ret & (Validate.date(_firstDateInput, i18n.tr("Invalid from date."), this) != null);
		ret = ret & (Validate.date(_firstDateInput, i18n.tr("Invalid from date."), this) != null);
		
		return ret;
	}
	
	/**
	 * @return first date
	 */
	protected Date getFirstDate() {
		return Validate.date(_firstDateInput);
	}
	
	/**
	 * @return second date
	 */
	protected Date getSecondDate() {
		return Validate.date(_secondDateInput);
	}
	
	/// date inputs
	protected SelectedTextField _firstDateInput, _secondDateInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 1119274896604533443L;	
}
