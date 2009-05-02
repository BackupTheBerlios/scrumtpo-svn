package scrummer.model.swing;

import scrummer.model.ImpedimentModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

public class ImpedimentTypeComboBoxModel
	extends IdValueComboBoxModel {

	public ImpedimentTypeComboBoxModel(ImpedimentModelCommon impedimentModelCommon) {
		_impedimentModelCommon = impedimentModelCommon;
	}
	
	@Override
	public void refresh() {
		setValues(_impedimentModelCommon.fetchTypes());
	}

	private ImpedimentModelCommon _impedimentModelCommon;
	/// serialization id
	private static final long serialVersionUID = -270394929167957801L;
}
