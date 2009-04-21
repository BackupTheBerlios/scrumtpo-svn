package scrummer.ui.dialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.text.NumberFormatter;

import org.xnap.commons.i18n.I18n;

import com.sun.imageio.plugins.common.I18N;

import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.ProductBacklogModel;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.SprintProjectComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedFormattedTextField;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

public class ProductBacklogDialog extends TwoButtonDialog {

	public ProductBacklogDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		
		Models m = Scrummer.getModels();		
		_productbacklogModel = m.getProductBacklogModel();
		_sprintBacklogModel = m.getSprintBacklogModel();
		_sprintProjectComboBoxModel = _sprintBacklogModel.getSprintProjectComboBoxModel();
			
		FormBuilder fb = new FormBuilder(Panel);
		fb.setCellSpacing(0, 10);
		_sprintInput = 
			fb.addComboBoxInput(i18n.tr("Sprint") + ":");
		_sprintInput.setIVModel(_sprintProjectComboBoxModel);
		
		_descriptionTextField = 
			fb.addSelectedTextInput(i18n.tr("Description") + ":", "Description");
		
		NumberFormatter priorityFormat = new NumberFormatter(NumberFormat.getIntegerInstance());
		_priorityTextField = 
			fb.addSelectedFormattedTextInput(i18n.tr("Priority") + ":", "Priority", priorityFormat);
		_priorityTextField.setText("5");
		
		DecimalFormat df = new DecimalFormat();
		df.setDecimalSeparatorAlwaysShown(false);
		df.setMaximumFractionDigits(2);
		df.setParseBigDecimal(true);
		NumberFormatter estimateFormat = new NumberFormatter(df);
		
		_initialestimateTextField = 
			fb.addSelectedFormattedTextInput(i18n.tr("Initial estimate") + ":", "Initial estimate", new NumberFormatter(NumberFormat.getNumberInstance()));
		_initialestimateTextField.setText("30");
		
		_adjustmentfactorTextField = 
			fb.addSelectedFormattedTextInput(i18n.tr("Adjustment factor") + ":", "Adjustment factor", estimateFormat);
		_adjustmentfactorTextField.setText("2");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Requirement data"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK + 5, bottomK));
		
		setSize(new Dimension(370, 290));
		Util.centre(this);
	}
	
	@Override
	public void setVisible(boolean b) {

		if (b)
		{
			_sprintProjectComboBoxModel.refresh();
		}
		
		super.setVisible(b);
	}

	/// all sprints on project combo box model
	protected SprintProjectComboBoxModel _sprintProjectComboBoxModel;
	/// spritn backlog model
	protected SprintBacklogModel _sprintBacklogModel;
	/// product backlog model
	protected ProductBacklogModel _productbacklogModel;
	/// sprint input combo box
	protected StandardComboBox _sprintInput;
	/// name text field
	protected SelectedTextField _descriptionTextField;
	/// formatted text fields
	protected SelectedFormattedTextField  _priorityTextField, _initialestimateTextField, _adjustmentfactorTextField;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
}
