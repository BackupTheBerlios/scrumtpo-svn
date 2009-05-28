package scrummer.model.swing;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.MetricModelCommon;
import scrummer.util.ObjectRow;

/**
 * Metric model displays metric inputs date ascending for every type of measure table -
 * Sprint, Task, (PBI, Release planned)
 */
public class MetricTableModel extends DefaultTableModel {

	/**
	 * Displayable metric type
	 */
	public enum MetricType {
		/// task related metrics
		Task,
		/// sprint related metrics
		Sprint,
		/// pbi related metrics
		PBI,
		/// release related metrics
		Release
	}
	
	public MetricTableModel(MetricModelCommon metricModelCommon) {
		_metricModelCommon = metricModelCommon;
	
		_columns.add(i18n.tr("Date"));
		_columns.add(i18n.tr("Measure"));
		_columnCount = 2;
		
		for (int i = 0; i < 2; i++)
			_realColumns.add("");
	}
	
	/**
	 * Refresh table data
	 */
	public void refresh() {		
		refreshTableData();
        fireTableDataChanged();
	}
	
	/**
	 * @param row row index
	 * @return id in database
	 */
	public int getId(int row) {
		String s = _rows.get(row).get(0).toString();
		if (s.trim().length() == 0) { s = "0"; }
		return Integer.parseInt(s.trim());
	}
	
	/**
	 * Fetch date for some row
	 * @param row row
	 * @return converted date
	 */
	public Date getDate(int row) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			return df.parse(_rows.get(row).get(1).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Fetch measure value at specified row
	 * @param row row from which to fetch it
	 * @return measure
	 */
	public BigDecimal getValue(int row) {
		return new BigDecimal(_rows.get(row).get(2).toString());
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData() {
		if ((_metricId != -1) && (_objectId != -1)) {
			switch (_metricType) {
			case Sprint:
				_rows = _metricModelCommon.fetchSprintMetric(_objectId, _metricId);				
				break;
			case Task:
				_rows = _metricModelCommon.fetchTaskMetric(_objectId, _metricId);
				break;
			case PBI:
				_rows = _metricModelCommon.fetchPBIMetric(_objectId, _metricId);
				break;
			case Release:
				_rows = _metricModelCommon.fetchReleaseMetric(_objectId, _metricId);
				break;
			}
	        _rowCount = _rows.size();
		}
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
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	/**
	 * Set different metric type
	 * @param metricType metric type
	 * @param metricId metric id
	 * @param id specific object id
	 */
	public void setMetricType(MetricType metricType, int metricId, int objectId) {
		_metricType = metricType;
		_metricId = metricId;
		_objectId = objectId;
		refresh();
	}
	
	/// common metric model data ops
	private MetricModelCommon _metricModelCommon;
	/// displayed metric type
	private MetricType _metricType = MetricType.Sprint;
	/// id of sprint, task, pbi or release
	private int _metricId = -1;
	/// object id designates either task, sprint etc.
	private int _objectId = -1;
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
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2477990263298000954L;
}
