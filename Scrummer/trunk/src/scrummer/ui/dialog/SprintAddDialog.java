package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Sprint addition form
 */
public class SprintAddDialog extends TwoButtonDialog {

	/**
	 * Constructor
	 * 
	 * @param owner owning frame
	 */
	public SprintAddDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
	
		setTitle(i18n.tr("Add Sprint"));
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Sprint"), 
				0, k - 4, k - 4, k));
		
		FormBuilder fb = new FormBuilder(Panel);		
		fb.addSelectedTextInput(i18n.tr("Description") + ":", "Description");
		fb.addSelectedTextInput(i18n.tr("Team") + ":", "Team");
		fb.addSelectedTextInput(i18n.tr("Start") + ":", "StartingDate");
		fb.addSelectedTextInput(i18n.tr("End") + ":", "EndDate");
		fb.addSelectedTextInput(i18n.tr("Length") + ":", "Length");
		fb.setCellSpacing(k, k - 2);
		
		OK.setText(i18n.tr("Add"));
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 7));
		
		setSize(new Dimension(300, 260));
	}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5484362646565900921L;
}
