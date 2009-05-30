package scrummer.ui.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ReleaseOperation;
import scrummer.listener.ReleaseListener;
import scrummer.model.Models;
import scrummer.model.ProductBacklogModel;
import scrummer.model.ReleaseModel;
import scrummer.model.swing.ProjectPBIComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardButton;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Base dialog for editing and addition of releases
 */
public class ReleaseDialog 
	extends TwoButtonDialog 
	implements ReleaseListener {

	public ReleaseDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
	
		Panel.setLayout(new GridLayout(1,1));
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
			k, k, k, k, 
			i18n.tr("Release Information"), 
			2, k, k, k));
		
		Models m = Scrummer.getModels();
		ProductBacklogModel pbmodel = m.getProductBacklogModel();
		_projectPbiComboBoxModel = pbmodel.getProjectPBIComboBoxModel();
		_releaseModel = m.getReleaseModel();
		_releaseModel.addListener(this);
		
		Box outerBox = new Box(BoxLayout.Y_AXIS);
		
		JPanel descriptionPanel = new JPanel();
		
		FormBuilder fb = new FormBuilder(descriptionPanel);
		_descriptionInput = fb.addSelectedTextInput(i18n.tr("Name") + ":", "Name");		
				
		Box pbiAddBox = new Box(BoxLayout.Y_AXIS);		
		
		JPanel toolbar = new JPanel();
		toolbar.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		_pbiInput = new StandardComboBox();
		_pbiInput.setIVModel(_projectPbiComboBoxModel);
		
		StandardButton addPbiButton = new StandardButton(i18n.tr("Add"));
		addPbiButton.setActionCommand("AddPBI");
		addPbiButton.addActionListener(this);
		
		StandardButton removePbiButton = new StandardButton(i18n.tr("Remove"));
		removePbiButton.setActionCommand("RemovePBI");
		removePbiButton.addActionListener(this);
		
		toolbar.add(_pbiInput);
		toolbar.add(addPbiButton);
		toolbar.add(removePbiButton);
		
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1,1));
		
		_pbiList = new JList();
		pbiAddBox.add(_pbiList);		
		_pbiList.setMinimumSize(new Dimension(300, 80));
		_pbiList.setPreferredSize(new Dimension(300, 80));
		_pbiList.setSize(300, 100);
		_pbiList.setFixedCellWidth(100);
		_pbiList.setFixedCellHeight(20);
		_pbiList.setLayoutOrientation(JList.VERTICAL_WRAP);
		_pbiList.setVisibleRowCount(5);
		_pbiList.setBackground(Color.WHITE);
		_pbiList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		_pbiList.invalidate();
		
		listPanel.add(_pbiList);
		
		pbiAddBox.add(toolbar);
		pbiAddBox.add(listPanel);		
		
		outerBox.add(descriptionPanel);
		outerBox.add(pbiAddBox);		
		
		Panel.add(outerBox);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0,k,k,k - 4));
		
		setSize(320, 270);
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
			if (!_pbiList.isSelectionEmpty()) {								
				String selected = _pbiList.getSelectedValue().toString();
				int id = _projectPbiComboBoxModel.getId(selected);
				if (_pbiIds.contains(id)) {
					_pbiIds.remove(id);
					Vector<Object> v = new Vector<Object>();
					for (int i = 0 ; i < _pbiList.getModel().getSize(); i++)
						v.add(_pbiList.getModel().getElementAt(i));
					v.remove(selected);
					_pbiList.setListData(v);
				}			
			}
		} else {
			super.actionPerformed(e);	
		}		
	}
	
	@Override
	public void operationFailed(DataOperation type, ReleaseOperation identifier, String message) {}

	@Override
	public void operationSucceeded(DataOperation type, ReleaseOperation identifier, String message) {}	

	@Override
	public void setVisible(boolean b) {
	
		if (!b) {
			_releaseModel.removeListener(this);
		}
		
		super.setVisible(b);
	}

	/// list of all pbi's that will be added
	protected Set<Integer> _pbiIds = new HashSet<Integer>();
	/// all pbi's on project
	private ProjectPBIComboBoxModel _projectPbiComboBoxModel;
	/// all pbi's that will be added
	private JList _pbiList;
	// pbi input combo box
	private StandardComboBox _pbiInput;
	/// description input
	protected SelectedTextField _descriptionInput;
	/// release model
	protected ReleaseModel _releaseModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2094836322416596308L;
	
}
