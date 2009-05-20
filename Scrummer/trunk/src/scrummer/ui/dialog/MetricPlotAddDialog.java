package scrummer.ui.dialog;

import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;

/**?
 * Dialog for adding metric plots
 */
public class MetricPlotAddDialog extends MetricPlotDialog {

	/**
	 * Constructor
	 * @param parent parent form
	 */
	public MetricPlotAddDialog(JFrame parent) {
		super(parent);	
	}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 709974257565527015L;
}
