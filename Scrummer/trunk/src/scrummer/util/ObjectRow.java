package scrummer.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

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
	
	private int _columnCount = 0;
	/// cell list
	private Vector<Object> _cells = new Vector<Object>();
}
