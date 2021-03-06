package scrummer.model.swing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.Models;
import scrummer.model.TaskStatusModelCommon;
import scrummer.util.ObjectRow;

/**
 * Task status table synchronization class 
 */
public class TaskStatusTableModel extends DefaultTableModel 
{	
	/**
	 * Default constructor
	 */
	public TaskStatusTableModel(ConnectionModel connectionModel,
			TaskStatusModelCommon taskstatusModelCommon) 
	{
		super();
		_taskstatusModelCommon = taskstatusModelCommon;
		
		_columns.add(i18n.tr("Id"));
		_columns.add(i18n.tr("Description"));
		for (int i = 0; i < 2; i++)
			_realColumns.add("");
	}

	/**
	 * Refresh data
	 */
	public void refresh()
	{
		refreshColumnNames();
		refreshTableData();
        fireTableDataChanged();
	}
	
	/**
	 * Refresh real column names
	 */
	private void refreshColumnNames()
	{
		Models m = Scrummer.getModels();
		DBSchemaModel schemam = m.getDBSchemaModel();
		_realColumns = schemam.getColumns(DBSchemaModel.TaskStatusTable);
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _taskstatusModelCommon.fetchTaskStatusTable();
        _rowCount = _rows.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) 
	{
		if (_taskstatusModelCommon.setTaskStatusDesc(
				_rows.get(row).get(0).toString(),
				_realColumns.get(column + 1),
				value.toString()))
		{
			refresh();
		}
	}
	
	@Override
	public void removeRow(int row) 
	{
		if (_taskstatusModelCommon.removeTaskStatus(_rows.get(row).get(0).toString()))
		{
			refresh();
		}	
	}

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
		return _rows.get(row).get(column+1);
	}

	@Override
	public String getColumnName(int column) {
		return _columns.get(column + 1);
	}

	/// column count
	private int _columnCount = 2;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(2);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common task status model operations
	private TaskStatusModelCommon _taskstatusModelCommon;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	/// table name
	public static final String TaskStatus = "Task_status";
	
}