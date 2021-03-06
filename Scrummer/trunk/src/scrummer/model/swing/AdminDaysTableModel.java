package scrummer.model.swing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.AdminDaysModelCommon;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.Models;
import scrummer.util.ObjectRow;

/**
 * Administrative days table synchronization class 
 */
public class AdminDaysTableModel extends DefaultTableModel 
{	
	/**
	 * Default constructor
	 */
	public AdminDaysTableModel(ConnectionModel connectionModel, AdminDaysModelCommon admindaysModelCommon) {
		super();
		_admindaysModelCommon = admindaysModelCommon;
		_columns.add(i18n.tr("Employee"));
		_columns.add(i18n.tr("Absence type"));
		_columns.add(i18n.tr("Hours not worked"));
		_columns.add(i18n.tr("Measure day"));
		for (int i = 0; i < 4; i++)
			_realColumns.add("");
	}

	/**
	 * Refresh data
	 */
	public void refresh() {
		refreshColumnNames();
		refreshTableData();
        fireTableDataChanged();
	}
	
	/**
	 * Refresh real column names
	 */
	private void refreshColumnNames() {
		Models m = Scrummer.getModels();
		DBSchemaModel schemam = m.getDBSchemaModel();
		_realColumns = schemam.getColumns(DBSchemaModel.AdminDaysTable);
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData() {
		_rows = _admindaysModelCommon.fetchAdminDaysTable();
        _rowCount = _rows.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
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
		return _columns.get(column);
	}
	
	public int getEmployee(int row) {
		return Integer.parseInt(_rows.get(row).get(0).toString());
	}
	
	public java.util.Date getMeasureDay(int row) {
		ObjectRow orow = _rows.get(row);
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			return df.parse(orow.get(4).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/// column count
	private int _columnCount = 4;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(4);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common administrative days model operations
	private AdminDaysModelCommon _admindaysModelCommon;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	/// table name
	public static final String AdminDays = "Administrative_days";
}