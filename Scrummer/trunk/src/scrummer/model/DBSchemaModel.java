package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import scrummer.util.Query;

/**
 * Database schema model can be used to get all database table structure data.
 * 
 * Currently this hold mostly just for column names.
 */
public class DBSchemaModel {

	/**
	 * Class designed to return all columns in a table
	 */
	private static class ColumnQuery extends Query
	{
		/**
		 * Constructor
		 * @param connectionModel connection model
		 */
		public ColumnQuery(ConnectionModel connectionModel, String table) {
			super(connectionModel);
			_table = table;
		}
		
		public Vector<String> getColumns()
		{
			return _columns;
		}	
		
		/**
		 * Fetch table name
		 * @return table
		 */
		protected String getTable()
		{
			return _table;
		}
		
		/**
		 * Add column
		 * @param value column name
		 */
		protected void addColumns(String value)
		{
			_columns.add(value);
		}
		
		/// column names
		private Vector<String> _columns = new Vector<String>();;
		/// table name
		private String _table;
	}
	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public DBSchemaModel(ConnectionModel connectionModel) {
		_connectionModel = connectionModel;
	}
	
	/**
	 * Get all column names for given table
	 * 
	 * This uses lazy loading, tables are loaded once, then they don't change anymore.
	 * 
	 * @param table table name
	 * @return column names
	 */
	public Vector<String> getColumns(String table)
	{
		if (_tableColumnsMap.containsKey(table))
		{
			return _tableColumnsMap.get(table);
		}
		else
		{
			_tableColumnsMap.put(table, new Vector<String>());
			
			// _tableColumnsMap.put(key, value)
			Vector<String> ret = new Vector<String>();
			ColumnQuery q = new ColumnQuery(_connectionModel, table)
			{
				@Override
				public void processResult(ResultSet result) throws java.sql.SQLException {
					
					Vector<String> ref = _tableColumnsMap.get(getTable());
		            int i = 0; result.beforeFirst();				
		            while (result.next())
		            {
		            	addColumns(result.getString(1));
		            	i++;
		            }		
				}
				@Override
				public void handleException(SQLException ex) {
					if (_tableColumnsMap.containsKey(getTable()))
					{
						_tableColumnsMap.remove(getTable());
					}
				}
			};
			q.queryResult("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='" + table + "'");
			
			if (_tableColumnsMap.containsKey(table))
			{
				return _tableColumnsMap.get(table);
			}
			else
			{
				return null;
			}
		}
	}

	/// mapping from table names to table column names
	private HashMap<String, Vector<String>> _tableColumnsMap = new HashMap<String, Vector<String>>();
	/// connection model
	private ConnectionModel _connectionModel;
}
