package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import scrummer.ui.Validate;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Dialog for editing impediment status
 */
public class ImpedimentStatusChange 
	extends TwoButtonDialog 
	implements ImpedimentListener {

	public ImpedimentStatusChange(JFrame parent) {
		super(parent);
		setTitle(i18n.tr("Change Impediment Status"));
		
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
		fb.setCellSpacing(0, 10);
		_impedimentStatusInput     = fb.addComboBoxInput(i18n.tr("Status") + ":");
		_impedimentStatusInput.setIVModel(_impedimentModel.getImpedimentStatusComboBoxModel());
		_impedimentDescriptionInput = fb.addSelectedTextInput(i18n.tr("Description") + ":", "");
		_impedimentDescriptionInput.addKeyListener(this);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k + 2, k - 3));
		
		OK.setText(i18n.tr("Change"));
		
		setSize(320, 180);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {
			if (Validate.empty(_impedimentDescriptionInput, this)) {
				_impedimentModel.changeStatus(
					_impedimentStatusInput.getSelectedId(), 
					_impedimentDescriptionInput.getText());
			}
		} else {
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Update) && (identifier == ImpedimentOperation.ImpedimentStatus)) {
			setVisible(false);
		}	
	}

	@Override
	public void operationFailed(DataOperation type, ImpedimentOperation identifier, String message) {
		if ((type == DataOperation.Update) && (identifier == ImpedimentOperation.ImpedimentStatus)) {
			Util.showError(
					this, i18n.tr("Could not add or update impediment status: " + message), 
					i18n.tr("Error"));
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
	protected StandardComboBox _impedimentStatusInput;
	/// impediment description
	protected SelectedTextField _impedimentDescriptionInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -8485320210444043566L;
	
}
