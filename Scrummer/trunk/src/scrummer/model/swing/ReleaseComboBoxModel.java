package scrummer.model.swing;

import scrummer.model.ReleaseModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * All releases 
 */
public class ReleaseComboBoxModel extends IdValueComboBoxModel {

	/**
	 * Release combo box model
	 */
	public ReleaseComboBoxModel(ReleaseModelCommon releaseModelCommon) {
		_releaseModelCommon = releaseModelCommon;
	}
	
	@Override
	public void refresh() {
		setValues(_releaseModelCommon.fetchReleases());
	}

	/// release model common dops 
	private ReleaseModelCommon _releaseModelCommon;
	/// serialization id
	private static final long serialVersionUID = 469789298376751000L;	
}
