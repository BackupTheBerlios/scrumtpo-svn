package scrummer.model.swing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.ImpedimentModelCommon;
import scrummer.model.Models;
import scrummer.util.ObjectRow;

/**
 * Impediment table synchronization class 
 */
public class ImpedimentTableModel extends DefaultTableModel {
	
	/**
	 * Default constructor
	 */
	public ImpedimentTableModel(ConnectionModel connectionModel,
								ImpedimentModelCommon impedimentModelCommon) 
	{
		super();
		_impedimentModelCommon = impedimentModelCommon;
		
		_columns.add(i18n.tr("ID"));
		_columns.add(i18n.tr("Team"));
		_columns.add(i18n.tr("Sprint"));
		_columns.add(i18n.tr("Employee"));
		_columns.add(i18n.tr("Task"));
		_columns.add(i18n.tr("Description"));
		_columns.add(i18n.tr("Type"));
		_columns.add(i18n.tr("Status"));
		_columns.add(i18n.tr("Start"));
		_columns.add(i18n.tr("End"));
		_columns.add(i18n.tr("Age"));
		for (int i = 0; i < 11; i++)
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
		_realColumns = schemam.getColumns(DBSchemaModel.ImpedimentTable);
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _impedimentModelCommon.fetchImpedimentTable();
        _rowCount = _rows.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) 
	{
		if (_impedimentModelCommon.setImpediment(
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
		if (_impedimentModelCommon.removeImpediment(Integer.parseInt(_rows.get(row).get(0).toString())))
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
	
	public int getPrimaryKey(int row) {
		return (Integer)_rows.get(row).get(0);
	}

	/// column count
	private int _columnCount = 10;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(11);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common impediment model operations
	private ImpedimentModelCommon _impedimentModelCommon;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	/// table name
	public static final String Impediment = "Impediment";
}