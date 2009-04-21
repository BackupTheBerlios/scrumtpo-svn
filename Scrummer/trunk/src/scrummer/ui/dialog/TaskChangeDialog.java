package scrummer.ui.dialog;

import java.awt.Frame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;

/**
 * Task modification dialog
 */
public class TaskChangeDialog extends TaskDialog {

	/**
	 * Constructor
	 * @param owner owning frame
	 */
	public TaskChangeDialog(Frame owner) {
		super(owner);
	
		setTitle(i18n.tr("Change Task"));
		
				
	}

	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2229297107817158452L;
}
