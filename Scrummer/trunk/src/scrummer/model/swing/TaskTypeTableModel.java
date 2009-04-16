package scrummer.model.swing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.Models;
import scrummer.model.TaskTypeModelCommon;
import scrummer.util.ObjectRow;

/**
 * Impediment table synchronization class 
 */
public class TaskTypeTableModel extends DefaultTableModel 
{	
	/**
	 * Default constructor
	 */
	public TaskTypeTableModel(ConnectionModel connectionModel,
								TaskTypeModelCommon tasktypeModelCommon) 
	{
		super();
		_tasktypeModelCommon = tasktypeModelCommon;
		
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
		_realColumns = schemam.getColumns(DBSchemaModel.TaskTypeTable);
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _tasktypeModelCommon.fetchTaskTypeTable();
        _rowCount = _rows.size();
		

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) 
	{
		if (_tasktypeModelCommon.setTaskTypeDesc(
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
		if (_tasktypeModelCommon.removeTaskType(_rows.get(row).get(0).toString()))
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
	/// common absence type model operations
	private TaskTypeModelCommon _tasktypeModelCommon;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	/// table name
	public static final String TaskType = "Task_type";
}