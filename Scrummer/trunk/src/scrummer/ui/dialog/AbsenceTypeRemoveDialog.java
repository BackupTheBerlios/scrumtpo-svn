package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.AbsenceTypeOperation;
import scrummer.enumerator.DataOperation;
import scrummer.listener.AbsenceTypeListener;
import scrummer.model.AbsenceTypeModel;
import scrummer.model.swing.AbsenceTypeComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove task type dialog
 */
public class AbsenceTypeRemoveDialog extends TwoButtonDialog
								 implements AbsenceTypeListener 
{
	/**
	 * Constructor
	 * 
	 * @param owner parent frame
	 */
	public AbsenceTypeRemoveDialog(Frame owner) 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Remove absence type"));
		
		_absencetypeModel = Scrummer.getModels().getAbsenceTypeModel();
		_absencetypeComboBoxModel = _absencetypeModel.getAbsenceTypeComboBoxModel();
		
		_absencetypeModel.addAbsenceTypeListener(this);
		
		int k = 10;
		
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Select absence type"),
				0, k, k, k));
		
		_formBuilder = new FormBuilder(Panel);
		_absencetypeInput = _formBuilder.addComboBoxInput(i18n.tr("Absence type") + ":");	
		_absencetypeInput.setIVModel(_absencetypeComboBoxModel);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k));
		
		setSize(new Dimension(320, 150));
		Util.centre(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK")
		{
			if (_absencetypeInput.isSelected())
			{				
				Integer id = _absencetypeInput.getSelectedId();
				_absencetypeModel.removeAbsenceType(id.toString());
				_absencetypeComboBoxModel.refresh();
			}
			else
			{
				Util.showError(
					this, 
					i18n.tr("Could not delete absence type. Select one absence type first, " +
							"then click on Remove button."), 
					i18n.tr("Error"));
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, AbsenceTypeOperation identifier, String message) 
	{
		switch (type)
		{
		case Remove:
			switch (identifier)
			{
			case AbsenceType:
				setVisible(false);
				break;
			}
			break;
		}
	}	
	
	@Override
	public void operationFailed(DataOperation type, AbsenceTypeOperation identifier, String message) {
		Util.showError(this, i18n.tr("Could not remove absence type: " + message), i18n.tr("Error"));
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			_absencetypeModel.removeAbsenceTypeListener(this);
		}
		
		super.setVisible(b);
	}
	
	/// absence type input combo box
	private StandardComboBox _absencetypeInput;
	/// absence type model
	private AbsenceTypeModel _absencetypeModel;
	/// absence type combo box model
	private AbsenceTypeComboBoxModel _absencetypeComboBoxModel;
	/// form building class
	private FormBuilder _formBuilder;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 4206239821521998017L;
}
