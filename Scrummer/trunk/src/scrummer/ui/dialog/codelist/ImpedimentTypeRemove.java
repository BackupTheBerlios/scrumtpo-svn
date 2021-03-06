package scrummer.ui.dialog.codelist;

import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.listener.ImpedimentListener;
import scrummer.model.ImpedimentModel;
import scrummer.model.Models;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Dialog for removing impediment types
 */
public class ImpedimentTypeRemove 
	extends TwoButtonDialog 
	implements ImpedimentListener {

	public ImpedimentTypeRemove(JFrame parent) {
		super(parent);
		
		setTitle(i18n.tr("Remove Impediment Type"));
		
		Models m = Scrummer.getModels();
		_impedimentModel = m.getImpedimentModel();
		_impedimentModel.addImpedimentListener(this);		
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Impediment Type"),
				0, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);
		_impedimentTypeInput     = fb.addComboBoxInput(i18n.tr("Status") + ":");
		_impedimentTypeInput.setIVModel(_impedimentModel.getImpedimentTypeComboBoxModel());
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 3));
		
		OK.setText(i18n.tr("Remove"));
		setSize(320, 150);
	}
	
	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Remove) && (identifier == ImpedimentOperation.ImpedimentType)) {
			setVisible(false);
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Remove) && (identifier == ImpedimentOperation.ImpedimentType)) {
			Util.showError(
				this, i18n.tr("Could not remove impediment type: " + message), 
				i18n.tr("Error"));
		}
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			_impedimentModel.removeType(_impedimentTypeInput.getSelectedId());
		} else {
			super.actionPerformed(e);
		}
	}

	@Override
	public void setVisible(boolean b) {
		if (!b) {
			_impedimentModel.removeImpedimentListener(this);
		}
		
		super.setVisible(b);
	}
	
	/// impediment model
	protected ImpedimentModel _impedimentModel;
	/// impediment status type
	protected StandardComboBox _impedimentTypeInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());	
	/// serialization id
	private static final long serialVersionUID = 9115482245920565917L;
}
