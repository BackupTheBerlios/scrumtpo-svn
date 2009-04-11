package scrummer.uicomponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultFocusTraversalPolicy;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
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
		setFocusCycleRoot(true);
		setFocusTraversalPolicy(new FocusPolicy());
	}
	
	public static class FocusPolicy extends DefaultFocusTraversalPolicy
	{
		public FocusPolicy()
		{}
		
		@Override
		public Component getComponentAfter(Container container,
				Component component) {
			
			if (component == getLastComponent(container))
			{
				return super.getFirstComponent(container.getParent());
			}
			else
			{
				return super.getComponentAfter(container, component);
			}
		}	
	}
	
	/// serialization id
	private static final long serialVersionUID = 5498309926057211075L;
}
