package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.JDialog;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.ui.Util;

/**
 * About box
 */
public class AboutBoxDialog extends JDialog {

	/**
	 * Constructor
	 * @param owner owner control
	 */
	public AboutBoxDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		setTitle(i18n.tr("About"));
		setSize(new Dimension(320,240));
		Util.centre(this);
	}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id 
	private static final long serialVersionUID = -5659217354344790937L;
}
