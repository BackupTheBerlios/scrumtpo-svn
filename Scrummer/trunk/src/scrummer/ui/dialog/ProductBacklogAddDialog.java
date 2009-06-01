package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.listener.ProductBacklogListener;

/**
 * Insert into product backlog dialog
 */
public class ProductBacklogAddDialog 
	extends ProductBacklogDialog
	implements ProductBacklogListener {
	
	/**
	 * Constructor
	 * @param owner owner of this dialog
	 */
	public ProductBacklogAddDialog(JFrame owner)
	{
		super(owner);
		
		// set translated title
		setTitle(i18n.tr("Insert into Product Backlog"));
		
		_productbacklogModel.addProductBacklogListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getActionCommand() == "StandardDialog.OK")
		{			
			int sprintId = _sprintInput.getSelectedId();
			_productbacklogModel.add( 
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
		case Insert:
			setVisible(false);
			break;
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, ProductBacklogOperation identifier, String message) {
		JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
	}

	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}
