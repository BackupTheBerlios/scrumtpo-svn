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
		for (int i = 0; i < _sprintInput.getModel().getSize(); i++)
		{
			if (Integer.parseInt(_sprintProjectComboBoxModel.getValue(i)) == sprintIndex)
			{
				_sprintInput.setSelectedIndex(i);
			}
		}
		_priorityTextField.setText(new Integer(_productbacklogModel.getPriority(pbiId)).toString());
		_initialestimateTextField.setText(_productbacklogModel.getInitialEstimate(pbiId).toString());
		_adjustmentfactorTextField.setText(_productbacklogModel.getAdjustmentFactor(pbiId).toString());
		
		
		// _descriptionTextField = _productbacklogModel.
		
		/*
		int k = 10;
		Panel.setLayout(new GridLayout(8, 9, 10, 10));
		Panel.setBorder(BorderFactory.createEmptyBorder(k + 3, k, k + 10, k));
		
		JLabel pbiLbl = new JLabel(i18n.tr("Product backlog item") + ":");
		JComboBox pbiInput = new JComboBox();
		pbiInput.setModel(_productbacklogComboModel);
		_pbiInput = pbiInput;
		_productbacklogComboModel.refresh();
		
		Panel.add(pbiLbl);
		Panel.add(pbiInput);
		
		_projectInput = addEntry(i18n.tr("New project") + ":", "NewProject");
		_sprintInput = addEntry(i18n.tr("New sprint") + ":", "NewSprint");
		_descInput = addEntry(i18n.tr("New description") + ":", "NewDesc");
		_priorityInput = addEntry(i18n.tr("New priority") + ":", "NewPriority");
		_initialestimateInput = addEntry(i18n.tr("New initial estimate") + ":", "NewIniEstimate");
		_adjfactorInput = addEntry(i18n.tr("New adjustment factor") + ":", "NewAdjFactor");
		_adjestimateInput = addEntry(i18n.tr("New adjusted estimate") + ":", "NewAdjEstimate");
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, k + 2, k - 4));
		*/
		OK.setText("Change");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			int sprintId = Integer.parseInt(_sprintProjectComboBoxModel.getElementAt(_sprintInput.getSelectedIndex()).toString());
			_productbacklogModel.modify(
				_pbiId,
				sprintId,
				_descriptionTextField.getText(),
				Integer.parseInt(_priorityTextField.getText()),
				new BigDecimal(_initialestimateTextField.getText()),
				new BigDecimal(_adjustmentfactorTextField.getText()));
			
		/*	
				String project = _projectInput.getText().trim();
				String sprint = _sprintInput.getText().trim();
				String description = _descInput.getText().trim();
				String priority = _priorityInput.getText().trim();
				String iniestimate = _initialestimateInput.getText().trim();
				String adjfactor = _adjfactorInput.getText().trim();
				String adjestimate = _adjestimateInput.getText().trim();
				
				if (project.length() > 0)
				{
					int selected = _pbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _productbacklogComboModel.getId(selected);
						_productbacklogModel.setPBIProject(id, project);
					}
					else
					{
						Util.showError(this, i18n.tr("Some PBI must be selected to change it's project."), i18n.tr("Error"));
					}
				}
				if(sprint.length() > 0)
				{
					int selected = _pbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _productbacklogComboModel.getId(selected);
						_productbacklogModel.setPBISprint(id, sprint);
					}
					else
					{
						Util.showError(this, i18n.tr("Some PBI must be selected to change it's sprint."), i18n.tr("Error"));
					}
				}
				if(description.length() > 0)
				{
					int selected = _pbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _productbacklogComboModel.getId(selected);
						_productbacklogModel.setPBIDesc(id, description);
					}
					else
					{
						Util.showError(this, i18n.tr("Some PBI must be selected to change it's description."), i18n.tr("Error"));
					}
				}
				if(priority.length() > 0)
				{test
					int selected = _pbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _productbacklogComboModel.getId(selected);
						_productbacklogModel.setPBIPriority(id, priority);
					}
					else
					{
						Util.showError(this, i18n.tr("Some PBI must be selected to change it's priority."), i18n.tr("Error"));
					}
				}
				if(iniestimate.length() > 0)
				{
					int selected = _pbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _productbacklogComboModel.getId(selected);
						_productbacklogModel.setPBIIniEstimate(id, iniestimate);
					}
					else
					{
						Util.showError(this, i18n.tr("Some PBI must be selected to change it's initial estimate."), i18n.tr("Error"));
					}
				}
				if(adjfactor.length() > 0)
				{
					int selected = _pbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _productbacklogComboModel.getId(selected);
						_productbacklogModel.setPBIAdjFactor(id, adjfactor);
					}
					else
					{
						Util.showError(this, i18n.tr("Some PBI must be selected to change it's adjustment factor."), i18n.tr("Error"));
					}
				}
				if(adjestimate.length() > 0)
				{
					int selected = _pbiInput.getSelectedIndex();
					if (selected != -1)
					{
						int id = _productbacklogComboModel.getId(selected);
						_productbacklogModel.setPBIAdjEstimate(id, adjestimate);
					}
					else
					{
						Util.showError(this, i18n.tr("Some PBI must be selected to change it's adjusted estimate."), i18n.tr("Error"));
					}
				}
				/*else
				{
					Util.showError(this, i18n.tr("PBI must be at least one character long."), i18n.tr("Error"));
				}*/
			
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
