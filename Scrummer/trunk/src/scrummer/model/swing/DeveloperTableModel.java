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
	public DeveloperTableModel(ConnectionModel connectionModel) {
		super();
		_connectionModel = connectionModel;
		
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
		_columns = schemam.getColumns(Employee);
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		Query q = new Query(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) {
			    _rows = ObjectRow.fetchRows(result);
	            _rowCount = _rows.size();		
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult("SELECT * FROM " + Employee);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		
		Query q = new Query(_connectionModel)
		{
			@Override
			public void process() {
	            refresh();
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Update, DeveloperOperation.Developer, 
	        		i18n.tr("Could not set parameter."));
			}
		};
		String idColumnName = _realColumns.get(0);
		String columnName = _realColumns.get(column+1);
		q.query("UPDATE " + Employee + " SET " + columnName + "='" + value.toString() + "' " + 
            	"WHERE " + idColumnName + "='" + _rows.get(row).get(0) + "'");
	}
	
	@Override
	public void removeRow(int row) {
	
		Query q = new Query(_connectionModel)
		{
			@Override
			public void process() {
				refresh();		
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, DeveloperOperation.Developer, 
	        		i18n.tr("Could not remove developer."));
			}
		};
		String idColumnName = _realColumns.get(0);
		q.query("DELETE FROM " + Employee + " WHERE " + idColumnName + "='" + _rows.get(row).get(0) + "'");
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

	/**
	 * Add developer data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addDeveloperListener(OperationListener<DeveloperOperation> listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove developer data change listener
	 * @param listener listener to remove
	 */
	public void removeDeveloperListner(OperationListener<DeveloperOperation> listener)
	{
		_operation.removeListener(listener);
	}
	
	/// column count
	private int _columnCount = 3;
	/// row count
	private int _rowCount = 0;
	/// connection handler
	private ConnectionModel _connectionModel;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(4);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// data listener
	private Operation<DeveloperOperation> _operation = new Operation<DeveloperOperation>();
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	/// table name
	public static final String Employee = "Employee";
	
}
