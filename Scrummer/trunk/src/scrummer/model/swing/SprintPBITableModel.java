package scrummer.model.swing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
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

	/**
	 * Refresh data
	 */
	public void refresh() {
		if (_sprintStart != null) {
			refreshColumnNames();
			refreshTableData();
			super.fireTableDataChanged();
		}
	}
	
	private void refreshColumnNames() {
		_columns.clear();
		_columns.add(i18n.tr("Hours Spent"));
		_columns.add(i18n.tr("Hours Remaining"));
		_columns.add(i18n.tr("Open Impediments"));
		_columns.add(i18n.tr("Closed Impediments"));
	}
	
	public void refreshTableData() {
		_rows = new Vector<ObjectRow>();
		Vector<ObjectRow> rows = 
			_sprintBacklogModelCommon.getSprintPBIs(
				_currentSprint, _taskId, _employeeId);
		
		for (int i = 0; i < 4; i++) _rows.add(new ObjectRow(1 + _sprintLength));		
		// iterate all rows in table
		for (int row = 0; row < 4; row++) {
			ObjectRow current = _rows.get(row);	
			int j = 0;
			if (rows.size() > 0) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(_sprintStart);
				// iterate every row along all metric columns			
				for (int i = 0; i <= _sprintLength; i++) {
					// find current date row
					int idx = -1;
					for (int fi = 0; fi < rows.size(); fi++) {
						if (((java.sql.Date)rows.get(fi).get(0)).getTime() == gc.getTime().getTime()) {
							idx = fi;
						}
					}
					if (idx != -1) {
						current.set(i, rows.get(idx).get(row+1));	
					} else {
						current.set(i, 0);
					}					
					gc.add(GregorianCalendar.DATE, 1);
				}
			}
		}
				
		_rowCount = 4;
		_columnCount = _sprintLength + 1;
	}
	
	/**
	 * Set current sprint id
	 * @param sprintId sprint id
	 */
	public void setSprintId(int sprintId) {
		_currentSprint = sprintId;
		refresh();
	}
	
	/**
	 * Set employee id
	 * @param value value to set
	 */
	public void setEmployeeId(int value) {
		_employeeId = value;
		refresh();
	}
		
	/**
	 * Set task id
	 * @param value value to set
	 */
	public void setTaskId(int value) {
		_taskId = value;
		refresh();
	}
	
	/**
	 * Set sprint length
	 * @param value value to set
	 */
	public void setSprintLength(int value) {
		_sprintLength = value;
		refresh();
	}
	
	/**
	 * Set start of sprint
	 * @param sprintStart 
	 */
	public void setSprintStart(Date sprintStart) {
		_sprintStart = sprintStart;
		refresh();
	}

	@Override
	public boolean isCellEditable(int row, int column) {		
		if (column == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		
		Date columnDate = null;
		if (_sprintStart != null) {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(_sprintStart);
			gc.add(GregorianCalendar.DATE, column);
			columnDate = gc.getTime();
		}
		try {
			int val = Integer.parseInt(value.toString());
			// if it exists modify it
			if (_sprintBacklogModelCommon.existsSprintPBI(new java.sql.Date(columnDate.getTime()), _taskId, _currentSprint, _employeeId)) {
				Integer realValue = Integer.parseInt(value.toString());
				boolean updateSuceeded = false;
				switch (row) {
				case 0: 
					updateSuceeded = _sprintBacklogModelCommon.setHoursSpent(_currentSprint, _taskId, new java.sql.Date(columnDate.getTime()), realValue);					
					break;
				case 1:							
					updateSuceeded = _sprintBacklogModelCommon.setHoursRemaining(_currentSprint, _taskId, new java.sql.Date(columnDate.getTime()), realValue);
					break;
				case 2:
					updateSuceeded = _sprintBacklogModelCommon.setNbOpenImped(_currentSprint, _taskId, new java.sql.Date(columnDate.getTime()), realValue);
					break;
				case 3:
					updateSuceeded = _sprintBacklogModelCommon.setNbClosedImped(_currentSprint, _taskId, new java.sql.Date(columnDate.getTime()), realValue);
					break;
				}
				if (updateSuceeded) {
					refresh();
				}				
			} else { // otherwise create new entry				
				int hoursSpent 		  = (row == 0) ? val : 0;
				int hoursRemaining    = (row == 1) ? val : 0;
				int openImpediments   = (row == 2) ? val : 0;
				int closedImpediments = (row == 3) ? val : 0;
				if (_sprintBacklogModelCommon.addDailyEntry(
						_currentSprint, _taskId, 
						new java.sql.Date(columnDate.getTime()), 
						_employeeId, 
						hoursSpent, hoursRemaining, 
						openImpediments, closedImpediments)) {
					refresh();
				}
			}
		} catch (Exception ex) {}		
	}
	
	@Override
	public void removeRow(int row) {}

	@Override
	public int getColumnCount() {
		return _columnCount;
	}

	@Override
	public int getRowCount() {
		return _rowCount;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return _columns.get(row);
		} else {
			return _rows.get(row).get(column);
		}
	}

	@Override
	public String getColumnName(int column) {
		if (column > 0) {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(_sprintStart);
			gc.add(GregorianCalendar.DATE, column);
			return gc.get(GregorianCalendar.DAY_OF_MONTH) + "." + (gc.get(GregorianCalendar.MONTH) + 1) + ".";
		} else {
			return "";
		}
	}
	
	/// current sprint id
	private int _currentSprint = 0;
	/// employee id
	private int _employeeId = 0;
	/// task id
	private int _taskId = 0;
	/// locally stored sprint length
	private int _sprintLength = 10;
	/// start of sprint
	private Date _sprintStart = null;
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
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -2979385877140097149L;
}
