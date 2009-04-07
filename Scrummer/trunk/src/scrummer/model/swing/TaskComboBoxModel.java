package scrummer.model.swing;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import scrummer.model.SprintBacklogModelCommon;
import scrummer.model.DBSchemaModel.IdValue;

public class TaskComboBoxModel extends DefaultComboBoxModel 
{
	
	public TaskComboBoxModel(SprintBacklogModelCommon taskModelCommon)
	{
		_taskModelCommon = taskModelCommon;
	}
	
	public void refresh()
	{
		refreshSBIs();
		fireContentsChanged(this, 0, getSize());
	}
	
	private void refreshSBIs()
	{
		_SBIs = _taskModelCommon.fetchSBIsNames();		
	}
	
	public int getId(int index)
	{
		return _SBIs.get(index).Id;
	}
	
	@Override
	public Object getElementAt(int index) {
		return _SBIs.get(index).Value;
	}

	@Override
	public int getSize() {
		return _SBIs.size();
	}

	/// impediment list
	private Vector<IdValue> _SBIs = new Vector<IdValue>();
	/// common impediment operations
	private SprintBacklogModelCommon _taskModelCommon;
	/// serialization id
	private static final long serialVersionUID = 6494898047192792857L;

}