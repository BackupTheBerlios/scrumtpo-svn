package scrummer.ui.dialog;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.ReleaseModel;
import scrummer.ui.Validate;

/**
 * Dialog for adding release
 */
public class ReleaseAddDialog 
	extends ReleaseDialog {

	public ReleaseAddDialog(JFrame parent) {
		super(parent);
		setTitle(i18n.tr("Add Release"));
		Models m = Scrummer.getModels();
		_releaseModel = m.getReleaseModel();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("StandardDialog.OK")) {		
			if (!Validate.empty(_descriptionInput, this)) { return; }
			String name = _descriptionInput.getText();
			_releaseModel.addRelease(name);
			int id = _releaseModel.getReleaseId(name);
			Integer [] arr = new Integer [] {};
			_pbiIds.toArray(arr);
			// Integer [] arr = _pbiIds.toArray;
			if (id == -1) { return; } else {
				// gather description and all id's
				for (int i = 0; i < _pbiIds.size(); i++) {
					_releaseModel.addReleasePbi(id, arr[i]);
				}
			}
		} else {		
			super.actionPerformed(e);
		}
	}

	/// release model
	private ReleaseModel _releaseModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -5650526983364930880L;
}