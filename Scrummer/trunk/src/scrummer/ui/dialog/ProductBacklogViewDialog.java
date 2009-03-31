package scrummer.ui.dialog;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;




import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class ProductBacklogViewDialog extends JDialog implements MouseListener
{
	public ProductBacklogViewDialog(Frame owner) throws SQLException 
	{	
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("View Product Backlog"));
		setSize(new Dimension(820,340));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BorderLayout());
		parentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		setLayout(new BorderLayout());
		
		ConnectionModel cm = Scrummer.getModels().getConnectionModel();
		final java.sql.Connection con = cm.getConnection();
		java.sql.Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM PBI GROUP BY ");
		
		int id;
		String description;
		String priority;
		String initial_estimate;
		String adjustment_factor;
		String adjusted_estimate;
		
		DefaultTableModel model = new DefaultTableModel(null,new Object[]{"PBI id","Description","Priority","Initial estimate","Adjustment factor","Adjusted estimate"})
		{
			public boolean isCellEditable(int rowIndex, int mColIndex) 
			{
				return false;
			}
		};
		
		while(rs.next())
		{
			id=rs.getInt("PBI_id");
			description=rs.getString("PBI_description");
			priority = rs.getString("PBI_priority");
			initial_estimate = rs.getString("PBI_initial_estimate");
			adjustment_factor = rs.getString("PBI_adjustment_factor");
			adjusted_estimate = rs.getString("PBI_adjusted_estimate");
			model.addRow(new Object[]{id,description,priority,initial_estimate,adjustment_factor,adjusted_estimate}); 
		}
		
		rs.close();    // All done with that resultset
	    stmt.close();  // All done with that statement
	    con.close();  // All done with that DB connection
		
		JTable table = new JTable(model);
		table.setSize(250, 170);
		table.setRowHeight(20);
		
		
		DefaultTableCellRenderer rdr = new DefaultTableCellRenderer() 
		{
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) 
			{
				if (value == null)
					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    		JLabel label = new JLabel(value.toString());
	    		label.setFont(new Font("Arial", Font.PLAIN,16));
	    		label.setAlignmentY(CENTER_ALIGNMENT);
	    		label.setAlignmentX(CENTER_ALIGNMENT);
	    		return label;
	    	}
	    };
		
		TableColumn column = null; 
		for (int i = 0; i < 6; i++) 
		{
		    column = table.getColumnModel().getColumn(i);
		    column.setResizable(false);
		    
		    if (i == 1) 
		        column.setPreferredWidth(200); //third column is bigger
		    else if(i == 0)
		    	column.setPreferredWidth(20);
		    else if(i == 2)
		    	column.setPreferredWidth(50);
		    else 
		        column.setPreferredWidth(100);
		    
		    column.setCellRenderer(rdr);
		    
		}
		
		parentPanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		parentPanel.add(table, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 0, 0));
		bottomPanel.setAlignmentX(RIGHT_ALIGNMENT);
		
		JButton backButton = new StandardButton(i18n.tr("Back"));
		JButton editButton = new StandardButton(i18n.tr("Edit"));
		bottomPanel.add(backButton);
		bottomPanel.add(editButton);
		
		GridBagConstraints bottomGc = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 0.3);
		bottomGc.gridy = 1;
		parentPanel.add(bottomPanel, BorderLayout.PAGE_END);
		add(parentPanel);
		
		Util.centre(this);
	}
	

	public void actionPerformed(ActionEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}


	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {}
	
	/// serialization id 
	private static final long serialVersionUID = 456365932759827558L;
	/// project list
	// private JList _projectList;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass()); 
	
}
	

