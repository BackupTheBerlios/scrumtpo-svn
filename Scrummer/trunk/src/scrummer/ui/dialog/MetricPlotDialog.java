package scrummer.ui.dialog;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Base dialog for Adding metric plots to graph
 */
public class MetricPlotDialog extends TwoButtonDialog {

	/**
	 * Constructor
	 * @param parent parent form
	 */
	public MetricPlotDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
		setTitle(i18n.tr("Add plot"));
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Plot configuration"), 
				k, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);
		_nameInput = fb.addSelectedTextInput(i18n.tr("Name") + ":", "");
		_fromInput = fb.addSelectedTextInput(i18n.tr("From") + ":", "");
		_toInput = fb.addSelectedTextInput(i18n.tr("To") + ":", "");
		fb.setCellSpacing(5, 5);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		setSize(320, 200);
	}
	
	/// name of 
	protected SelectedTextField _nameInput;
	/// begining of graph timeline
	protected SelectedTextField _fromInput;
	/// hraph timeline will go up to this date
	protected SelectedTextField _toInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// id generator
	private static int _generated = 0;
	/// serialization id
	private static final long serialVersionUID = -2000557781506906626L;	
}
