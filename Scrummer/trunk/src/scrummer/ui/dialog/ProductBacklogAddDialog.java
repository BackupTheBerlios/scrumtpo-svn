package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ProductBacklogModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Add developer dialog
 */
public class ProductBacklogAddDialog extends TwoButtonDialog {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public ProductBacklogAddDialog(JFrame owner) throws SQLException
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add Developer"));
		
		_productbacklogModel = Scrummer.getModels().getProductBacklogModel();
		
		Panel.setLayout(new GridLayout(7, 5, 0, 10));
		
		_projectTextField    = addEntry(i18n.tr("Project")    + ":", "Project");
		_sprintTextField = addEntry(i18n.tr("Sprint") + ":", "Sprint");
		_descriptionTextField = addEntry(i18n.tr("Description") + ":", "Description");
		_priorityTextField = addEntry(i18n.tr("Priority") + ":", "Priority");
		_initialestimateTextField = addEntry(i18n.tr("Initial estimate") + ":", "Initial estimate");
		_adjustmentfactorTextField = addEntry(i18n.tr("Adjustment factor") + ":", "Adjustment factor");
		_adjustedestimateTextField = addEntry(i18n.tr("Adjusted estimate") + ":", "Adjusted estimate");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add Developer"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(570, 410));
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
			_productbacklogModel.add(
				Integer.parseInt(_projectTextField.getText()), 
				Integer.parseInt(_sprintTextField.getText()),
				_descriptionTextField.getText(), 
				Integer.parseInt(_priorityTextField.getText()),
				Integer.parseInt(_initialestimateTextField.getText()),
				Float.parseFloat(_adjustmentfactorTextField.getText()),
				Integer.parseInt(_adjustedestimateTextField.getText()));
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	/// product backlog model
	private ProductBacklogModel _productbacklogModel;
	/// name text field
	private JTextField _projectTextField, _sprintTextField, _descriptionTextField, _priorityTextField, _initialestimateTextField, _adjustmentfactorTextField, _adjustedestimateTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
