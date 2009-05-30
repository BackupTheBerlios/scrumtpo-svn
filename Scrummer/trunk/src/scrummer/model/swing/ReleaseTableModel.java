package scrummer.model.swing;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.ReleaseModelCommon;

/**
 * Release table model displays a list of releases and a comma separated lists of corresponding pbi's
 */
public class ReleaseTableModel extends DefaultTableModel {

	public ReleaseTableModel(ReleaseModelCommon releaseModelCommon) {
		_releaseModelCommon = releaseModelCommon;
	}
	
	/**
	 * Refresh data
	 */
	public void refresh() {
		refreshColumnNames();
		refreshTableData();
		super.fireTableDataChanged();
	}
	
	private void refreshColumnNames() {
		_columns.clear();
		_columns.add(i18n.tr("Release"));
		_columns.add(i18n.tr("PBI"));
	}
	
	public void refreshTableData() {
		_rows = _releaseModelCommon.fetchReleaseData();		
	}

	@Override
	public boolean isCellEditable(int row, int column) {		
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {}
	
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
		return _rows.get(row).get(column+1);
	}

	@Override
	public String getColumnName(int column) {
		return _columns.get(column);
	}
	
	/// column count
	private int _columnCount = 0;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(2);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<scrummer.util.ObjectRow> _rows = new Vector<scrummer.util.ObjectRow>();
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// common release model
	private ReleaseModelCommon _releaseModelCommon;
	/// serialization id
	private static final long serialVersionUID = 1472700314053111307L;
}
