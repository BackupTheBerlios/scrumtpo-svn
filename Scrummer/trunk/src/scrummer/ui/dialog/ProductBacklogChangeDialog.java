package scrummer.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.listener.ProductBacklogListener;
import scrummer.ui.Util;

/**
 * Remove some team from database
 */
public class ProductBacklogChangeDialog 
	extends ProductBacklogDialog
	implements ProductBacklogListener {

	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 * @param pbiId product backlog item id
	 */
	public ProductBacklogChangeDialog(Frame owner, int pbiId)
	{
		super(owner);
		_pbiId = pbiId;
		
		setTitle(i18n.tr("Change product backlog item"));
				
		_productbacklogModel.addProductBacklogListener(this);
		
		_descriptionTextField.selectAll();
		_descriptionTextField.setText(_productbacklogModel.getDescription(pbiId));
		int sprintIndex = _productbacklogModel.getSprint(pbiId);
		for (int i = 0; i < _sprintInput.getModel().getSize(); i++) {
			if (_sprintProjectComboBoxModel.getId(i) == sprintIndex)
			{
				_sprintInput.setSelectedIndex(i);
			}
		}
		_priorityTextField.setText(new Integer(_productbacklogModel.getPriority(pbiId)).toString());
		_initialestimateTextField.setText(_productbacklogModel.getInitialEstimate(pbiId).toString());
		_adjustmentfactorTextField.setText(_productbacklogModel.getAdjustmentFactor(pbiId).toString());
		
		OK.setText("Change");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			int sprintId = _sprintInput.getSelectedId();
			_productbacklogModel.modify(
				_pbiId,
				sprintId,
				_descriptionTextField.getText(),
				Integer.parseInt(_priorityTextField.getText()),
				new BigDecimal(_initialestimateTextField.getText()),
				new BigDecimal(_adjustmentfactorTextField.getText()));
		}
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, ProductBacklogOperation identifier, String message) {
		switch (type)
		{
		case Update:
		
			switch (identifier)
			{
			case ProductBacklog:
				
				// _productbacklogComboModel.refresh();
				// _pbiInput.setSelectedIndex(0);
				setVisible(false);
				break;
			}
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, ProductBacklogOperation identifier, String message) {
		
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case ProductBacklog:
				Util.showError(this, 
					i18n.tr("An error has occurred when setting team name") + ": " + message, 
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
			_productbacklogModel.removeProductBacklogListner(this);
		}
		
		super.setVisible(b);
	}

	/// pbi id, for which this form is showing data
	private int _pbiId;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
