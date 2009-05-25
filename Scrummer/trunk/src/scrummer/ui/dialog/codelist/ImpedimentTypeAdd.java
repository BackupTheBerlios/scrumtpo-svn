package scrummer.ui.dialog.codelist;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.listener.ImpedimentListener;
import scrummer.model.ImpedimentModel;
import scrummer.model.Models;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.ui.Validate;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Dialog for adding impediment type
 */
public class ImpedimentTypeAdd 
	extends TwoButtonDialog 
	implements ImpedimentListener {

	public ImpedimentTypeAdd(JFrame parent) {
		super(parent);
		
		setTitle(i18n.tr("Add Impediment Type"));
		
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
		_impedimentTypeInput = fb.addSelectedTextInput(i18n.tr("Description") + ":", "");
		_impedimentTypeInput.addKeyListener(this);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 4));
		setSize(300, 145);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			if (Validate.empty(_impedimentTypeInput, this)) {
				_impedimentModel.addType(_impedimentTypeInput.getText());
			}
		} else {
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Insert) && (identifier == ImpedimentOperation.ImpedimentType)) {
			Util.showError(this, 
				i18n.tr("Could not add impediment type: " + message), 
				i18n.tr("Error"));
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Insert) && (identifier == ImpedimentOperation.ImpedimentType)) {
			setVisible(false);
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
	/// impediment status description
	private JTextField _impedimentTypeInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1825160533536350825L;
}
