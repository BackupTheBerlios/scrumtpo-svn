package scrummer.model.swing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.Models;
import scrummer.model.SprintBacklogModelCommon;
import scrummer.util.ObjectRow;

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
	public SprintBacklogTableModel(ConnectionModel connectionModel, 
				SprintBacklogModelCommon sprintbacklogModelCommon) 
	{
		super();
		_sprintbacklogModelCommon = sprintbacklogModelCommon;
		
		_columns.add(i18n.tr("Day"));
		_columns.add(i18n.tr("PBI id"));
		_columns.add(i18n.tr("Sprint id"));
		_columns.add(i18n.tr("Task id"));
		_columns.add(i18n.tr("Task description"));
		_columns.add(i18n.tr("Task type"));
		_columns.add(i18n.tr("Task status"));
		_columns.add(i18n.tr("Task date"));
		_columns.add(i18n.tr("Task active"));
		_columns.add(i18n.tr("Employee id"));
		_columns.add(i18n.tr("Hours spent"));
		_columns.add(i18n.tr("Hours remaining"));
		_columns.add(i18n.tr("Number of open impediments"));
		_columns.add(i18n.tr("Number of closed impediments"));
		
		for (int i = 0; i < 14; i++)
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
		_realColumns = schemam.getColumns(DBSchemaModel.SprintPBITable);		
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _sprintbacklogModelCommon.fetchSprintBacklogTable();
        _rowCount = _rows.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) 
	{	
		//if (_sprintbacklogModelCommon.setSprintBacklog(_rows.get(row).get(0).toString(),_realColumns.get(column + 1),value.toString()))
		//{
		//	refresh();
		//}
		/*
		if (_sprintbacklogModelCommon.setSprintBacklog(
				Integer.parseInt(_rows.get(row).get(0).toString()),
				Integer.parseInt(_rows.get(row).get(1).toString()),
				Integer.parseInt(_rows.get(row).get(2).toString()),
				Integer.parseInt(_rows.get(row).get(3).toString()),
				_realColumns.get(column + 1),
				value.toString()))
		{
			refresh();
		}
		*/
		
		/*String idColumnName = _realColumns.get(0);
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
        }*/
	}
	
	@Override
	public void removeRow(int row) 
	{
		if (_sprintbacklogModelCommon.removeSprintBacklog(_rows.get(row).get(0).toString()))
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
	
	/// column count Sprint PBI
	private int _columnCount = 13;
	/// row count
	private int _rowCount = 0;
	/// column names for Sprint PBI display
	private Vector<String> _columns = new Vector<String>(14);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common developer model operations
	private SprintBacklogModelCommon _sprintbacklogModelCommon;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
	
}