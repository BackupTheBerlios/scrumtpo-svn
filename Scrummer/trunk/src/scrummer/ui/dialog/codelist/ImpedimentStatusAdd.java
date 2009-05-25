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
 * Dialog for adding new impediment status
 */
public class ImpedimentStatusAdd 
	extends TwoButtonDialog
	implements ImpedimentListener {

	public ImpedimentStatusAdd(JFrame owner) {
		super(owner);
		
		setTitle(i18n.tr("Add Impediment Status"));
		
		Models m = Scrummer.getModels();
		_impedimentModel = m.getImpedimentModel();
		_impedimentModel.addImpedimentListener(this);
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Impediment Status"),
				0, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);
		_impedimentStatusInput = fb.addSelectedTextInput(i18n.tr("Description") + ":", "");
		_impedimentStatusInput.addKeyListener(this);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k - 4));
		setSize(300, 145);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			if (Validate.empty(_impedimentStatusInput, this)) {
				_impedimentModel.addStatus(_impedimentStatusInput.getText());
			}
		} else {
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Insert) && (identifier == ImpedimentOperation.ImpedimentStatus)) {
			Util.showError(this, 
				i18n.tr("Could not add new impediment status: " + message), 
				i18n.tr("Error"));
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Insert) && (identifier == ImpedimentOperation.ImpedimentStatus)) {
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
	private JTextField _impedimentStatusInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 4202696327505280535L;
}
