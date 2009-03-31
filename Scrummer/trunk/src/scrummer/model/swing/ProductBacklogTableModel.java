package scrummer.model.swing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.util.ObjectRow;

/**
 * Product Backlog table synchronization class 
 */
public class ProductBacklogTableModel extends DefaultTableModel {
	
	/**
	 * Default constructor
	 */
	public ProductBacklogTableModel() {
		_connectionModel = Scrummer.getModels().getConnectionModel();
		refresh();
	}

	/**
	 * Refresh data
	 */
	public void refresh()
	{
		refreshTableData();
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
            String query = "SELECT * FROM PBI";
            conn.createStatement();
            res = st.executeQuery(query);
            _rows = ObjectRow.fetchRows(res);
            
            fireTableDataChanged();
        }
        catch (SQLException ex) {}
        finally
        {
            res = _connectionModel.close(res);
            st  = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }

	}
	
	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public int getRowCount() {
		return _rows.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return _rows.get(row).get(column);
	}

	/// connection handler
	private ConnectionModel _connectionModel;
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;
}
