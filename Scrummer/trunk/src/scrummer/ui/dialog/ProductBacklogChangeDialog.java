package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.listener.OperationListener;
import scrummer.listener.ProductBacklogListener;
import scrummer.model.ProductBacklogModel;
import scrummer.model.swing.PBIComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove some team from database
 */
public class ProductBacklogChangeDialog 
	extends TwoButtonDialog
	implements ProductBacklogListener {

	/**
	 * Constructor
	 * 
	 * @param owner owner form
	 */
	public ProductBacklogChangeDialog(Frame owner)
	{
		super(owner, ModalityType.APPLICATION_MODAL);

		setTitle(i18n.tr("Change product backlog item"));
		
		_productbacklogModel = Scrummer.getModels().getProductBacklogModel();
		_productbacklogModel.addProductBacklogListener(this);
		
		_productbacklogComboModel = _productbacklogModel.getPBIComboBoxModel();
		
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
		
		OK.setText("Change");
		setSize(new Dimension(350, 360));
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
				{
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
			case Project:
				_productbacklogComboModel.refresh();
				_pbiInput.setSelectedIndex(0);
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
			case Project:
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
		else
		{
			if (_productbacklogComboModel.getSize() == 0)
			{
				_pbiInput.setEnabled(false);
			}
			else
			{
				_pbiInput.setEnabled(true);
				_pbiInput.setSelectedIndex(0);
			}
		}
		
		super.setVisible(b);
	}

	/// product backlog model
	private ProductBacklogModel _productbacklogModel;
	/// all PBI in combo box
	private PBIComboBoxModel _productbacklogComboModel;
	/// team new name input
	private JTextField _projectInput, _sprintInput,  _descInput, _priorityInput, _initialestimateInput, _adjfactorInput, _adjestimateInput;
	/// team input combo box
	private JComboBox _pbiInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -1346923766182215474L;	
}
