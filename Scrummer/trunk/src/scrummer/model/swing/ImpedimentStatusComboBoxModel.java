package scrummer.model.swing;

import scrummer.model.ImpedimentModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Model contains impediment status
 */
public class ImpedimentStatusComboBoxModel extends IdValueComboBoxModel {

	public ImpedimentStatusComboBoxModel(ImpedimentModelCommon impedimentModelCommon) {
		_impedimentModelCommon = impedimentModelCommon;
	}
	
	@Override
	public void refresh() {
		setValues(_impedimentModelCommon.fetchStatuses());
	}

	/// common impediment data ops.
	private ImpedimentModelCommon _impedimentModelCommon;
	/// serialization id
	private static final long serialVersionUID = -433372236779140175L;
}
