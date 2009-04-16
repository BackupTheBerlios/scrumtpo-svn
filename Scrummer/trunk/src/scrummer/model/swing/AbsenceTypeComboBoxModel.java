package scrummer.model.swing;

import scrummer.model.AbsenceTypeModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Absence type combo box model contains id's + absence type descriptions
 */
public class AbsenceTypeComboBoxModel extends IdValueComboBoxModel 
{
	/**
	 * Constructor
	 * @param admindaysModelCommon common administrative days operations
	 */
	public AbsenceTypeComboBoxModel(AbsenceTypeModelCommon absencetypeModelCommon)
	{
		_absencetypeModelCommon = absencetypeModelCommon;
	}
	
	/**
	 * Refresh data from database
	 */
	public void refresh()
	{
		refreshAbsenceType();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshAbsenceType()
	{
		setValues(_absencetypeModelCommon.fetchAbsenceTypeNames());		
	}

	/// common administrative days operations
	private AbsenceTypeModelCommon _absencetypeModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}
