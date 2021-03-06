package scrummer.model.swing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.Models;
import scrummer.model.TaskModelCommon;
import scrummer.util.ObjectRow;

/**
 * Task table synchronization class 
 */
public class TaskTableModel extends DefaultTableModel 
{	
	/**
	 * Default constructor
	 */
	public TaskTableModel(ConnectionModel connectionModel,
			TaskModelCommon taskModelCommon) 
	{
		super();
		_taskModelCommon = taskModelCommon;
		
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
		_realColumns = schemam.getColumns(DBSchemaModel.TaskTable);
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _taskModelCommon.fetchTaskTable();
        _rowCount = _rows.size();
		

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) 
	{
		
	}
	
	@Override
	public void removeRow(int row) 
	{
		int id = getPrimaryKey(row);
		if (_taskModelCommon.removeTask(id))
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
	
	public int getPrimaryKey(int row)
	{
		return (Integer)_rows.get(row).get(0);
	}

	/// column count
	private int _columnCount = 7;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(8);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common task status model operations
	private TaskModelCommon _taskModelCommon;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	/// table name
	public static final String Task = "Task";
	
}