package scrummer.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import scrummer.model.DBSchemaModel;

/**
 * Object row abstracts data row
 */
public class ObjectRow {

	/**
	 * Constructor
	 * 
	 * Construct row of objects from resultset
	 */
	public ObjectRow(ResultSet result)
	{
		try {
			_columnCount = result.getMetaData().getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			for (int i = 0; i < _columnCount; i++)
			{
				_cells.add(result.getObject(i+1));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetch column count
	 * @return
	 */
	public int getColumnCount()
	{
		return _columnCount;
	}
	
	/**
	 * Fetch object in column(starts at 0)
	 * 
	 * @param column column index
	 * @return object in given column
	 */
	public Object get(int column)
	{
		if ((column < 0) || (column >= _columnCount))
		{
			throw new IndexOutOfBoundsException("Index out of range: [" + 0 + "," + _columnCount + "].");
		}
		return _cells.get(column);
	}
	
	/**
	 * Set i-th cell to custom value
	 * @param column column
	 * @param value value to set
	 */
	public void set(int column, Object value)
	{
		_cells.set(column, value);
	}
	
	/**
	 * Load all rows into ObjectRow's
	 * @param result result set
	 * @return rows
	 */
	public static Vector<ObjectRow> fetchRows(ResultSet result)
	{
		Vector<ObjectRow> rows = new Vector<ObjectRow>();
		
		try {
			result.beforeFirst();
	        while (result.next())
	        {
	        	ObjectRow row = new ObjectRow(result);
	        	rows.add(row);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows;
	}
	
	/**
	 * Convert all dates in n-th column to application specific format
	 * @param rows rows
	 * @param column column to format
	 */
	public static void convertDate(Vector<ObjectRow> rows, int column)
	{
		for (int i = 0; i < rows.size(); i++)
		{
			ObjectRow row = rows.get(i);
						
			java.sql.Date date = (java.sql.Date)row.get(column);
			Date d = new Date(date.getTime()); 
			
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			String res = df.format(d);		
			row.set(column, res);
		}
	}
	
	/**
	 * Convert text in i-th column into equivalent enum, that will be shown to the end user.
	 * This is needed for translation and beauty
	 * 
	 * @param rows rows
	 * @param column column which should be changed
	 * @param enumTable table
	 */
	public static void convertEnum(Vector<ObjectRow> rows, int column, String enumTable)
	{
		for (int i = 0; i < rows.size(); i++)
		{
			ObjectRow row = rows.get(i);
						
			Integer enumValue = (Integer)row.get(column);		
			row.set(column, DBSchemaModel.convertEnum(enumTable, enumValue));
		}
	}
	
	/// column count
	private int _columnCount = 0;
	/// cell list
	private Vector<Object> _cells = new Vector<Object>();
}
