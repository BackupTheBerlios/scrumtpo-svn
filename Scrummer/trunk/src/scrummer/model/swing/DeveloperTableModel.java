package scrummer.model.swing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.listener.OperationListener;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.DeveloperModelCommon;
import scrummer.model.Models;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;
import scrummer.util.Query;

/**
 * Developer table synchronization class 
 */
public class DeveloperTableModel extends DefaultTableModel {
	
	/**
	 * Default constructor
	 * 
	 * @param connectionModel connection model to connect to database
	 */
	public DeveloperTableModel(ConnectionModel connectionModel, 
							   DeveloperModelCommon developerModelCommon,
							   Operation<DeveloperOperation> operation) {
		super();
		_developerModelCommon = developerModelCommon;
		
		_columns.add(i18n.tr("ID"));
		_columns.add(i18n.tr("Name"));
		_columns.add(i18n.tr("Surname"));
		_columns.add(i18n.tr("Address"));
		for (int i = 0; i < 4; i++)
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
		_realColumns = schemam.getColumns(DBSchemaModel.EmployeeTable);
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _developerModelCommon.fetchDeveloperTable();
        _rowCount = _rows.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (_developerModelCommon.setDeveloper(
				_rows.get(row).get(0).toString(),
				_realColumns.get(column + 1),
				value.toString()))
		{
			refresh();
		}
	}
	
	@Override
	public void removeRow(int row) {
		if (_developerModelCommon.removeDeveloper(_rows.get(row).get(0).toString()))
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
	private int _columnCount = 3;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(4);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common developer model operations
	private DeveloperModelCommon _developerModelCommon;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	
}
