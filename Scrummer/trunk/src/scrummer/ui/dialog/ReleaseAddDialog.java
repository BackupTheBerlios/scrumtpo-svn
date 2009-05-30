package scrummer.ui.dialog;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ReleaseOperation;
import scrummer.model.Models;
import scrummer.model.ReleaseModel;
import scrummer.ui.Util;
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
			String name = Validate.empty(_descriptionInput, i18n.tr("Name cannot be empty."), this); if (name == null) return; 
			_releaseModel.addRelease(name);
			int id = _releaseModel.getReleaseId(name);
			Integer [] arr = new Integer [] {};
			arr = _pbiIds.toArray(arr);
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

	@Override
	public void operationFailed(DataOperation type, ReleaseOperation identifier, String message) {
		if ((type == DataOperation.Insert) && (identifier == ReleaseOperation.Release)) {
			Util.showError(this, 
				i18n.tr("An error has occurred while adding a new release: ") + message, 
				i18n.tr("Error"));
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, ReleaseOperation identifier, String message) {
		if ((type == DataOperation.Insert) && (identifier == ReleaseOperation.Release)) {
			setVisible(false);
		}
	}

	/// release model
	private ReleaseModel _releaseModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -5650526983364930880L;
}