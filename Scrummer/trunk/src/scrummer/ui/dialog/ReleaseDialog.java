package scrummer.ui.dialog;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.ProductBacklogModel;
import scrummer.ui.FormBuilder;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardButton;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Base dialog for editing and addition of releases
 */
public class ReleaseDialog 
	extends TwoButtonDialog {

	public ReleaseDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
	
		Panel.setLayout(new GridLayout(1,1));
		
		Models m = Scrummer.getModels();
		ProductBacklogModel pbmodel = m.getProductBacklogModel();
		
		Box outerBox = new Box(BoxLayout.Y_AXIS);
		
		JPanel descriptionPanel = new JPanel();
		
		FormBuilder fb = new FormBuilder(descriptionPanel);
		_descriptionInput = fb.addSelectedTextInput(i18n.tr("Name") + ":", "Name");		
				
		Box pbiAddBox = new Box(BoxLayout.Y_AXIS);		
		
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		_pbiInput = new StandardComboBox();
		_pbiInput.setIVModel(pbmodel.getProjectPBIComboBoxModel());
		
		StandardButton addPbiButton = new StandardButton(i18n.tr("Add"));
		addPbiButton.setActionCommand("AddPBI");
		addPbiButton.addActionListener(this);
		
		StandardButton removePbiButton = new StandardButton(i18n.tr("Remove"));
		removePbiButton.setActionCommand("RemovePBI");
		removePbiButton.addActionListener(this);
		
		toolbar.add(_pbiInput);
		toolbar.add(addPbiButton);
		toolbar.add(removePbiButton);
		
		pbiAddBox.add(toolbar);
		
		_pbiList = new JList();
		pbiAddBox.add(_pbiList);
		
		outerBox.add(descriptionPanel);
		outerBox.add(pbiAddBox);
		
		Panel.add(outerBox);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("AddPBI")) {		
			int id = _pbiInput.getSelectedId();
			_pbiIds.add(id);
			String name = _pbiInput.getSelectedItem().toString();
			Vector<Object> v = new Vector<Object>();
			boolean contains = false;
			for (int i = 0 ; i < _pbiList.getModel().getSize(); i++) {
				if (_pbiList.getModel().getElementAt(i).equals(name)) {
					contains = true;
				}
				v.add(_pbiList.getModel().getElementAt(i));
			}
			if (!contains) {
				v.add(name);
				_pbiList.setListData(v);
			}			
		} else if (cmd.equals("RemovePBI")) {
			int id = _pbiInput.getSelectedId();
			String name = _pbiInput.getSelectedItem().toString();
			
			if (_pbiIds.contains(id)) {
				_pbiIds.remove(id);
				Vector<Object> v = new Vector<Object>();
				for (int i = 0 ; i < _pbiList.getModel().getSize(); i++)
					v.add(_pbiList.getModel().getElementAt(i));
				v.remove(name);
				_pbiList.setListData(v);
			}			
		} else {
			super.actionPerformed(e);	
		}		
	}

	/// list of all pbi's that will be added
	protected Set<Integer> _pbiIds = new HashSet<Integer>(); 
	/// all pbi's that will be added
	private JList _pbiList;
	// pbi input combo box
	private StandardComboBox _pbiInput;
	/// description input
	protected SelectedTextField _descriptionInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2094836322416596308L;
}
