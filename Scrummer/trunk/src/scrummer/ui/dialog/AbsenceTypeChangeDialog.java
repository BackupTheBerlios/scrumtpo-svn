package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.AbsenceTypeOperation;
import scrummer.enumerator.DataOperation;
import scrummer.listener.AbsenceTypeListener;
import scrummer.model.AbsenceTypeModel;
import scrummer.model.swing.AbsenceTypeComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

public class AbsenceTypeChangeDialog 
	extends TwoButtonDialog
	implements AbsenceTypeListener 
	{
	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public AbsenceTypeChangeDialog(Frame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change absence type"));
		
		_absencetypeModel = Scrummer.getModels().getAbsenceTypeModel();
		_absencetypeModel.addAbsenceTypeListener(this);
		
		_absencetypeComboModel = _absencetypeModel.getAbsenceTypeComboBoxModel();
		
		int k = 10;
		Panel.setLayout(new GridLayout(6, 6, 10, 12));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel absLbl = new JLabel(i18n.tr("Absence type") + ":");
		JComboBox absInput = new JComboBox();
		absInput.setModel(_absencetypeComboModel);
		_absInput = absInput;
		_absencetypeComboModel.refresh();
		
		Panel.add(absLbl);
		Panel.add(absInput);
		
		_descInput = addEntry(i18n.tr("New description") + ":", "NewDescription");
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 4));
		
		OK.setText("Change");
		setSize(new Dimension(460, 310));
		Util.centre(this);
	}
	
	/** 
	 * Add form entry(label + textbox)
	 * 
	 * @param labelText label text
	 * @param textActionCmd text action command
	 * @return added text field
	 */
	public JTextField addEntry(String labelText, String textActionCmd)
	{
		JLabel label = new JLabel(labelText);
		
		JTextField textBox = new SelectedTextField();
		
		Panel.add(label);
		Panel.add(textBox);
		
		return textBox;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			String desc = _descInput.getText().trim();
			int selected = _absInput.getSelectedIndex();
			if (selected != -1)
			{
				int id = _absencetypeComboModel.getId(selected);
				_absencetypeModel.setNewDesc(id, desc);
			}
			else
			{
				Util.showError(this, i18n.tr("Some absence type must be selected to change it's description."), i18n.tr("Error"));
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, AbsenceTypeOperation identifier, String message) {
		switch (type)
		{
		case Update:
		
			switch (identifier)
			{
			case AbsenceType:
				_absencetypeComboModel.refresh();
				_absInput.setSelectedIndex(0);
				setVisible(false);
				break;
			}
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, AbsenceTypeOperation identifier, String message) {
		
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case AbsenceType:
				Util.showError(this, 
					i18n.tr("An error has occurred when setting description") + ": " + message, 
					i18n.tr("Error"));
				break;
			}
			break;
		}
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			_absencetypeModel.removeAbsenceTypeListener(this);
		}
		else
		{
			if (_absencetypeComboModel.getSize() == 0)
			{
				_absInput.setEnabled(false);
			}
			else
			{
				_absInput.setEnabled(true);
				_absInput.setSelectedIndex(0);
			}
		}
		
		super.setVisible(b);
	}

	/// absence type model
	private AbsenceTypeModel _absencetypeModel;
	/// all absence types in combo box
	private AbsenceTypeComboBoxModel _absencetypeComboModel;
	/// description new name input
	private JTextField _descInput;
	/// absence type input combo box
	private JComboBox _absInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;
}
