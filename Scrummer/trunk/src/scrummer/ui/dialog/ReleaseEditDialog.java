package scrummer.ui.dialog;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JFrame;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ReleaseOperation;
import scrummer.model.Models;
import scrummer.model.ReleaseModel;
import scrummer.ui.Validate;

/**
 * Release edit dialog
 */
public class ReleaseEditDialog 
	extends ReleaseDialog {
	
	public ReleaseEditDialog(JFrame parent, int releaseId) {
		super(parent);
	
		_releaseId = releaseId;
		setTitle(i18n.tr("Edit Release"));
		
		Models m = Scrummer.getModels();		
		_releaseModel = m.getReleaseModel();
		
		_descriptionInput.setText(_releaseModel.getReleaseDescription(releaseId));
		
		Vector<Integer> pbis = _releaseModel.fetchPbis(releaseId);
		_pbiIds.clear();
		_pbiIds.addAll(pbis);
		
		Vector<Object> v = new Vector<Object>();		
		for (int i = 0 ; i < _projectPbiComboBoxModel.getSize(); i++)
			if (_pbiIds.contains(_projectPbiComboBoxModel.getId(i)))
				v.add(_projectPbiComboBoxModel.getValue(i));
		_pbiList.setListData(v);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {			
			String descr = Validate.empty(_descriptionInput, i18n.tr("Description must not be an empty string."), this); if (descr == null) return;
			_releaseModel.setReleaseDescription(_releaseId, descr);
			
			// delete all previous release pbi's
			_releaseModel.removeAllReleasePbi(_releaseId);
			Integer [] arr = new Integer[] {}; 
			arr = _pbiIds.toArray(arr);
			for (int i = 0; i < arr.length; i++) {
				_releaseModel.addReleasePbi(_releaseId, arr[i]);
			}
			setVisible(false);
		} else {
			super.actionPerformed(e);
		}
	}
	@Override
	public void operationFailed(DataOperation type, ReleaseOperation identifier, String message) {
	}
	@Override
	public void operationSucceeded(DataOperation type, ReleaseOperation identifier, String message) {}

	/// which release is being edited
	private int _releaseId;
	/// release model
	private ReleaseModel _releaseModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 7931218649463688864L;	
}
