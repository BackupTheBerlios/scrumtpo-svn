package scrummer.ui.dialog.codelist;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskTypeOperation;
import scrummer.listener.TaskTypeListener;
import scrummer.model.TaskTypeModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add absence type dialog
 */
public class TaskTypeAddDialog 
	extends TwoButtonDialog
	implements TaskTypeListener 
	{
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public TaskTypeAddDialog(JFrame owner) 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add task type"));
		
		_tasktypeModel = Scrummer.getModels().getTaskTypeModel();
		_tasktypeModel.addTaskTypeListener(this);
		
		Panel.setLayout(new GridLayout(5, 5, 0, 13));
		
		_descTextField = addEntry(i18n.tr("Description") + ":", "Description");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add task type"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(570, 300));
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
			_tasktypeModel.add(_descTextField.getText()); 
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			// _impedimentModel.
		}

		super.setVisible(b);
	}

	@Override
	public void operationFailed(DataOperation type, TaskTypeOperation identifier, String message) {
		JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void operationSucceeded(DataOperation type, TaskTypeOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			setVisible(false);
			break;
		}
	}
	
	/// absence type model
	private TaskTypeModel _tasktypeModel;
	/// name text field
	private JTextField _descTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}