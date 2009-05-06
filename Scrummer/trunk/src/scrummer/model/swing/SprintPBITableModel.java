package scrummer.model.swing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import scrummer.model.SprintBacklogModelCommon;
import scrummer.util.ObjectRow;

/**
 * This model contains chronologically ordered daily metrics
 */
public class SprintPBITableModel extends DefaultTableModel {

	/**
	 * Constructor
	 */
	public SprintPBITableModel(SprintBacklogModelCommon sprintBacklogModelCommmon) {		
		_sprintBacklogModelCommon = sprintBacklogModelCommmon;
	}

	public void refresh() {
		refreshColumnNames();
		refreshTableData();
		super.fireTableDataChanged();
	}
	
	private void refreshColumnNames() {
		// fetch sprint length
		int sprintLength = 10;
		_columns.clear();
		_columns.add("");
		for (int i = 1; i < sprintLength; i++)
		{
			_columns.add(new Integer(i).toString());
		}		
	}
	
	public void refreshTableData() {		
		Vector<ObjectRow> rows = 
			_sprintBacklogModelCommon.getSprintPBIs(
				_currentSprint, _pbiId, _employeeId);
		int counter = 1;
		if (rows.isEmpty())
		{
			for (int i = 1; i < _sprintLength; i++)
			{
				ObjectRow row = new ObjectRow(4);
				row.set(0, i);
				row.set(1, 0);
				row.set(2, 0);
				row.set(3, 0);
				rows.add(row);
			}
		}
		else
		{
			for (int i = 0; i < rows.size(); i++)
			{
				ObjectRow current = rows.get(i-1);
			}
		}
	}
	
	/// current spriunt id
	private int _currentSprint = 0;
	/// employee id
	private int _employeeId = 0;
	/// pbi id
	private int _pbiId = 0;
	/// locally stored sprint length
	private int _sprintLength = 10;
	/// column count
	private int _columnCount = 0;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(2);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common sprint backlog data ops
	private SprintBacklogModelCommon _sprintBacklogModelCommon;
	/// serialization id
	private static final long serialVersionUID = -2979385877140097149L;
}
