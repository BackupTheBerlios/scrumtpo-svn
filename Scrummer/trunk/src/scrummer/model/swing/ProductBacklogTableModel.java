package scrummer.model.swing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.Models;
import scrummer.model.ProductBacklogModelCommon;
import scrummer.model.ProjectModel;
import scrummer.util.ObjectRow;

/**
 * Product Backlog table synchronization class 
 */
public class ProductBacklogTableModel extends DefaultTableModel 
{	
	/**
	 * Default constructor
	 */
	public ProductBacklogTableModel(ConnectionModel connectionModel,
									ProductBacklogModelCommon productbacklogModelCommon,
									ProjectModel projectModel) 
	{
		super();
		_projectModel = projectModel;
		_productbacklogModelCommon = productbacklogModelCommon;
		
		_columns.add(i18n.tr("ID"));
		_columns.add(i18n.tr("Description"));
		_columns.add(i18n.tr("Priority"));
		_columns.add(i18n.tr("Initial estimate"));
		_columns.add(i18n.tr("Adjustment factor"));
		_columns.add(i18n.tr("Adjusted estimate"));
		_columns.add(i18n.tr("Sprint"));
		
		for (int i = 0; i < 8; i++)
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
		_realColumns = schemam.getColumns(DBSchemaModel.PBITable);		
		_columnCount = _realColumns.size();
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _productbacklogModelCommon.fetchProductBacklogTable(_projectModel.getCurrentProjectId());
        _rowCount = _rows.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int column) 
	{
		if (_productbacklogModelCommon.setProductBacklog(
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
		if (_productbacklogModelCommon.removeProductBacklogItem(_rows.get(row).get(0).toString()))
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

	/// column count
	private int _columnCount = 0;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(8);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common developer model operations
	private ProductBacklogModelCommon _productbacklogModelCommon;
	/// project model
	private ProjectModel _projectModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	/// table name
	public static final String PBI = "PBI";
	
}