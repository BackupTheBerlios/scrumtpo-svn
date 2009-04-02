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
import scrummer.enumerator.ImpedimentOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.listener.OperationListener;
import scrummer.model.ConnectionModel;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;

/**
 * Product Backlog table synchronization class 
 */
public class SprintBacklogTableModel extends DefaultTableModel 
{	
	/**
	 * Default constructor
	 * 
	 * @param connectionModel connection model to connect to database
	 */
	public SprintBacklogTableModel(ConnectionModel connectionModel) 
	{
		super();
		_connectionModel = connectionModel;
		
		_columns.add(i18n.tr("PBI id"));
		_columns.add(i18n.tr("Sprint id"));
		_columns.add(i18n.tr("Task id"));
		_columns.add(i18n.tr("Employee id"));
		
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
		java.sql.Connection conn = null;
        Statement st = null; 
        ResultSet res = null;
        try
        {
        	conn = _connectionModel.getConnection();
        
            String query = 
            	"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='" + Sprint_PBI + "'";   
            	        
            st = conn.createStatement();
            res = st.executeQuery(query);
            
            int i = 0;
            res.beforeFirst();
            while (res.next())
            {
            	_realColumns.set(i, res.getString(1));
            	i++;
            }
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        }
        finally
        {
            res = _connectionModel.close(res);
            st  = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		java.sql.Connection conn = null;
        Statement st = null; 
        ResultSet res = null;
        try
        {
        	conn = _connectionModel.getConnection();
        
            String query = "SELECT * FROM " + Sprint_PBI;
        
            st = conn.createStatement();
            res = st.executeQuery(query);
            
            _rows = ObjectRow.fetchRows(res);
            _rowCount = _rows.size();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        }
        finally
        {
            res = _connectionModel.close(res);
            st  = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		
		String idColumnName = _realColumns.get(0);
		String columnName = _realColumns.get(column+1);
		
		java.sql.Connection conn = null;
        Statement st = null; 
        try
        {
        	conn = _connectionModel.getConnection();
        
            String query = 
            	"UPDATE " + Sprint_PBI + " SET " + columnName + "='" + value.toString() + "' " + 
            	"WHERE " + idColumnName + "='" + _rows.get(row).get(0) + "'";
        
           st = conn.createStatement();
           st.execute(query);
           
           // update cell after all the fuss
           refresh();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        	_operation.operationFailed(
        		DataOperation.Update, 
        		SprintBacklogOperation.SprintBacklog, 
        		i18n.tr("Could not set parameter."));
        }
        finally
        {
            st  = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
	}
	
	@Override
	public void removeRow(int row) {
	
		String idColumnName = _realColumns.get(0);
		
		java.sql.Connection conn = null;
        Statement st = null; 
        try
        {
        	conn = _connectionModel.getConnection();
        
            String query = 
            	"DELETE FROM " + Sprint_PBI + " WHERE " + idColumnName + "='" + _rows.get(row).get(0) + "'";
        
            st = conn.createStatement();
            st.execute(query);
           
            // update cell after all the fuss
            refresh();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        	_operation.operationFailed(
        		DataOperation.Remove, 
        		SprintBacklogOperation.SprintBacklog, 
        		i18n.tr("Could not remove sprint backlog item."));
        }
        finally
        {
            st  = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
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

	/**
	 * Add sprint backlog item data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addSprintBacklogListener(OperationListener<SprintBacklogOperation> listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove sprint backlog item data change listener
	 * @param listener listener to remove
	 */
	public void removeSprintBacklogListner(OperationListener<SprintBacklogOperation> listener)
	{
		_operation.removeListener(listener);
	}
	
	/// column count Sprint PBI
	private int _columnCount = 3;
	/// row count
	private int _rowCount = 0;
	/// connection handler
	private ConnectionModel _connectionModel;
	/// column names for Sprint PBI display
	private Vector<String> _columns = new Vector<String>(4);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// data listener
	private Operation<SprintBacklogOperation> _operation = new Operation<SprintBacklogOperation>();
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	/// table name Sprint PBI
	public static final String Sprint_PBI = "Sprint_PBI";
	/// table name Task
	public static final String Task = "Task";
	
}