package scrummer.ui.dialog;

import java.awt.Frame;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Sprint modification dialog
 */
public class SprintChangeDialog extends TwoButtonDialog {

	/**
	 * Constructor
	 * 
	 * @param owner owning frame
	 */
	public SprintChangeDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
	
	}
	
	/// serialization id
	private static final long serialVersionUID = 8400642500579725817L;
}
