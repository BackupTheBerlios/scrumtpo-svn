package scrummer.uicomponents;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * Nice table colours every other table entry
 * 
 * It may also do other nice stuff in time - like when last cell is tabbed - it will 
 * focus on to next control instead on focusing on first cell.
 */
public class NiceTable extends JTable {

	/**
	 * Makes every other line coloured
	 */
	static class OddRenderer extends DefaultTableCellRenderer
	{
		public OddRenderer(Color color)
		{			
			_color = color;
		}
		
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
		{
	        setEnabled(table == null || table.isEnabled()); // see question above
	
	        if ((row % 2) == 1)
	            setBackground(_color);
	        else
	            setBackground(null);
	      
	        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
	
	        return this;
		}		
		
		/// colour of every other line
		private Color _color;
		/// serialization id
		private static final long serialVersionUID = -2673793561513970296L;
	}
	
	public NiceTable() {
		init();
	}

	public NiceTable(TableModel dm) {
		super(dm);
		init();
	}

	public NiceTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		init();
	}

	public NiceTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		init();
	}

	public NiceTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		init();
	}

	public NiceTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		init();
	}

	public NiceTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		init();
	}
	
	private void init()
	{
		OddRenderer renderer = new OddRenderer(new Color(200, 200, 255, 75));
		renderer.setOpaque(true);
		setDefaultRenderer(Object.class, renderer);
		setRowSelectionAllowed(true);
	}
	
	@Override
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle,
			boolean extend) {
				
		boolean changed = false;
		
		if (rowIndex != -1)
		{
			int lastRow = getModel().getRowCount() - 1;
			int lastColumn = getModel().getColumnCount() - 1;
			
			// if jumped from last to first cell using tab
			if ((rowIndex == 0) && (columnIndex == 0) &&
				(_previousRowIndex == lastRow) && 
				(_previousColumnIndex == lastColumn))
			{
				if (_previousComponent != null)
				{
					_previousComponent.requestFocus();
					changed = true;
				}
			}
			// if jumped from first to last cell using shift+tab
			else if ((rowIndex == lastRow) && 
					 (columnIndex == lastColumn) &&
					 (_previousRowIndex == 0) && 
					 (_previousColumnIndex == 0))
			{
				if (_nextComponent != null)
				{					
					_nextComponent.requestFocus();
					changed = true;
				}
			}
		}
		
		super.changeSelection(rowIndex, columnIndex, toggle, extend);
		if (changed)
		{
			clearSelection();	
		}
		
		_previousRowIndex = rowIndex;
		_previousColumnIndex = columnIndex;
	}
	
	/**
	 * Set adjacent components 
	 * 
	 * @param previous previous component
	 * @param next next component
	 */
	public void setAdjacentComponents(JComponent previous, JComponent next)
	{
		_previousComponent = previous;
		_nextComponent = next;
	}

	/// last selected cell row index
	private int _previousRowIndex = -1;
	/// last selected cell column index
	private int _previousColumnIndex = -1;
	/// previous component to be focused
	private JComponent _previousComponent = null;
	/// after table component to be focused
	private JComponent _nextComponent = null;
	/// serialization id
	private static final long serialVersionUID = 5498309926057211075L;
}
