package scrummer.ui.dialog;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.*;

public class DevelopersAddDialog extends JDialog implements MouseListener
{
	public DevelopersAddDialog(Frame owner) throws SQLException 
	{	
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Add developers"));
		setSize(new Dimension(820,340));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BorderLayout());
		parentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		setLayout(new BorderLayout());
		
		/*String[] columnNames = {"Team member ID","Name","Surname","Address"};  
		
		Object[][] data = {
			    {"1", "Katja", "Cetinski", "Brestova pot 4 Ko�evje"},
			     {"2","Simon","Mihevc","Logatec"},
			     {"3","Matej","Klun","Obirska 23 Ljubljana"},
			     {"4","Da�a","Gelze","Lo�ki potok"},
			     {"5","Tadej","�ertanc","Celov�ka 285 Ljubljana"},
			     {"6","Anja","�ahuk","Trata XIV/20 Ko�evje"}
			};
		final java.sql.Connection con = ConnectionModel.getConnection();
		java.sql.Statement stmt = con.createStatement();
		
		int j = 0;
	    while (rs.next()) 
	    {
	        int x = rs.getInt("Employee_id");
	        String s = rs.getString("Employee_description");
	        System.out.println("ROW " + ++j + ": " +
	          x + "; " + s + "; " + ".");
	      }

		
		TableModel model1 = new DefaultTableModel(data,columnNames);*/
		
		/*
		Models m = Scrummer.getModels();
		final java.sql.Connection con = m.getConnectionModel().getConnection();
		java.sql.Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
		
		int id;
		String name;
		*/
		String surname;
		String address;
		DefaultTableModel model = new DefaultTableModel(null, new Object[]{"Team member ID", "Name", "Surname", "Address"});
		/*
		while(rs.next())
		{
			id = rs.getInt("Employee_id");
			name = rs.getString("Employee_name");
			surname = rs.getString("Employee_surname");
			address = rs.getString("Employee_address");
			model.addRow(new Object[]{id,name,surname,address}); 
		}
		
		rs.close();    // All done with that resultset
	    stmt.close();  // All done with that statement
	    con.close();  // All done with that DB connection
		*/
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
		for (int i = 0; i < 4; i++) 
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
		JButton editButton = new StandardButton(i18n.tr("Add row"));
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
	

