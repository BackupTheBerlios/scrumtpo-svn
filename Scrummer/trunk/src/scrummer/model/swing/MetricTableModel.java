package scrummer.model.swing;

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
		Sprint
		/*,
		/// pbi related metrics
		PBI,
		/// release related metrics
		Release
		*/
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
        fireTableDataChanged();
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		if (_id != -1) {
			switch (_metricType) {
			case Sprint:
				_rows = _metricModelCommon.fetchSprintMetric(_id);
				break;
			case Task:
				_rows = _metricModelCommon.fetchTaskMetric(_id);
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
		return _rows.get(row).get(column);
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
	 * @param metric metric type
	 * @param id specific
	 */
	public void setMetricType(MetricType metric, int id) {
		_metricType = metric;
		_id = id;
		refresh();
	}
	
	/// common metric model data ops
	private MetricModelCommon _metricModelCommon;
	/// displayed metric type
	private MetricType _metricType = MetricType.Sprint;
	/// id of sprint, task, pbi or release
	private int _id = -1;
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
