package scrummer.ui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.SprintBacklogModel;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Sprint input dialog
 */
public class SprintDialog extends TwoButtonDialog {

	public SprintDialog(JFrame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
			k, k, k, k, 
			i18n.tr("Sprint Information"), 
			2, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);		
		_sprintInput = fb.addComboBoxInput(i18n.tr("Sprint"));
		
		Models m = Scrummer.getModels();
		SprintBacklogModel sbmodel = m.getSprintBacklogModel();
		_sprintInput.setIVModel(sbmodel.getSprintProjectComboBoxModel());
		// 		getSprintProjectComboBoxModel());
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 3));
		
		setSize(320, 150);
	}

	/// sprint input combo box
	protected StandardComboBox _sprintInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -3163011849659854743L;
}
