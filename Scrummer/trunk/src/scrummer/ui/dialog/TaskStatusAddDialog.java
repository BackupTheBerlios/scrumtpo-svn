package scrummer.ui.dialog;

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
import scrummer.enumerator.TaskStatusOperation;
import scrummer.listener.TaskStatusListener;
import scrummer.model.TaskStatusModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add task status dialog
 */
public class TaskStatusAddDialog 
	extends TwoButtonDialog
	implements TaskStatusListener 
	{
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public TaskStatusAddDialog(JFrame owner) throws SQLException 
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add task status"));
		
		_taskstatusModel = Scrummer.getModels().getTaskStatusModel();
		_taskstatusModel.addTaskStatusListener(this);
		
		Panel.setLayout(new GridLayout(5, 5, 0, 13));
		
		_descTextField = addEntry(i18n.tr("Description") + ":", "Description");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add task status"), 
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
			_taskstatusModel.add(_descTextField.getText()); 
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
	public void operationFailed(DataOperation type, TaskStatusOperation identifier, String message) {
		JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void operationSucceeded(DataOperation type, TaskStatusOperation identifier, String message) {
		switch (type)
		{
		case Insert:
			setVisible(false);
			break;
		}
	}
	
	/// task status model
	private TaskStatusModel _taskstatusModel;
	/// name text field
	private JTextField _descTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}