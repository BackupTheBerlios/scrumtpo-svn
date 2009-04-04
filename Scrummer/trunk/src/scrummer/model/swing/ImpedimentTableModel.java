package scrummer.model.swing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.ImpedimentModelCommon;
import scrummer.model.Models;
import scrummer.util.ObjectRow;
import scrummer.util.Operation;

/**
 * Impediment table synchronization class 
 */
public class ImpedimentTableModel extends DefaultTableModel {
	
	/**
	 * Default constructor
	 */
	public ImpedimentTableModel(ConnectionModel connectionModel,
								ImpedimentModelCommon impedimentModelCommon,
								Operation<ImpedimentOperation> operation) 
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
		
		/*java.sql.Connection conn = null;
        Statement st = null; 
        ResultSet res = null;
        try
        {
        	conn = _connectionModel.getConnection();
        
            String query = 
            	"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='" + Impediment + "'";   
            	        
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
        }*/
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _impedimentModelCommon.fetchImpedimentTable();
        _rowCount = _rows.size();
		
		/*java.sql.Connection conn = null;
        Statement st = null; 
        ResultSet res = null;
        try
        {
        	conn = _connectionModel.getConnection();
        
            String query = "SELECT * FROM " + Impediment;
        
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
        }*/
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
		
		/*String idColumnName = _realColumns.get(0);
		String columnName = _realColumns.get(column+1);
		
		java.sql.Connection conn = null;
        Statement st = null; 
        try
        {
        	conn = _connectionModel.getConnection();
        
            String query = 
            	"UPDATE " + Impediment + " SET " + columnName + "='" + value.toString() + "' " + 
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
        		ImpedimentOperation.Impediment, 
        		i18n.tr("Could not set parameter."));
        }
        finally
        {
            st  = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }*/
	}
	
	@Override
	public void removeRow(int row) 
	{
		if (_impedimentModelCommon.removeImpediment(_rows.get(row).get(0).toString()))
		{
			refresh();
		}	
		
		/*String idColumnName = _realColumns.get(0);
		
		java.sql.Connection conn = null;
        Statement st = null; 
        try
        {
        	conn = _connectionModel.getConnection();
        
            String query = 
            	"DELETE FROM " + Impediment + " WHERE " + idColumnName + "='" + _rows.get(row).get(0) + "'";
        
            st = conn.createStatement();
            st.execute(query);
           
            // update cell after all the fuss
            refresh();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        	_operation.operationFailed(
        		DataOperation.Remove, 
        		ImpedimentOperation.Impediment, 
        		i18n.tr("Could not remove impediment."));
        }
        finally
        {
            st  = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }*/
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
	private int _columnCount = 7;
	/// row count
	private int _rowCount = 0;
	/// column names for display
	private Vector<String> _columns = new Vector<String>(8);
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