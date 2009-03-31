package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;


public class ProductBacklogAddDialog extends TwoButtonDialog implements FocusListener {
	
	public ProductBacklogAddDialog(JFrame owner) throws SQLException
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Insert into Product Backlog"));
		
		Panel.setLayout(new GridLayout(7, 5, 0, 10));
		
		addEntry(i18n.tr("Project") + ":", "Project");
		addEntry(i18n.tr("Sprint") + ":", "Sprint");
		addEntry(i18n.tr("Description")    + ":", "Description");
		addEntry(i18n.tr("Priority") + ":", "Priority");
		addEntry(i18n.tr("Initial estimate") + ":", "Initial estimate");
		addEntry(i18n.tr("Adjustment factor") + ":", "Adjustment factor");
		addEntry(i18n.tr("Adjusted estimate") + ":", "Adjusted estimate");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Insert into Product Backlog"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(570, 310));
		Util.centre(this);
	}

	public void addEntry(String labelText, String textActionCmd)
	{
		JLabel label = new JLabel(labelText);
		
		JTextField textBox = new SelectedTextField();
		_textField.add(textBox);
		textBox.addFocusListener(this);
		
		Panel.add(label);
		Panel.add(textBox);
	}
	
	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		if (_textField.contains(e.getComponent()))
		{
			JTextField field = _textField.get(_textField.indexOf(e.getSource())); 
			System.out.println(field.getText());
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("StandardDialog.OK"))
		{
			int project = Integer.parseInt(_textField.get(0).getText());
			int sprint = Integer.parseInt(_textField.get(1).getText());
			String description = _textField.get(2).getText();
			String priority = _textField.get(3).getText();
			String initial_estimate = _textField.get(4).getText();
			String adjustment_factor = _textField.get(5).getText();
			String adjusted_estimate = _textField.get(6).getText();
			
			ConnectionModel cm = Scrummer.getModels().getConnectionModel();
			java.sql.Connection con;
			java.sql.Statement stmt;
			ResultSet rs;
			try {
				con = cm.getConnection();
				stmt = con.createStatement();
			    rs = stmt.executeQuery("INSERT INTO PBI (Project_id,Sprint_id,PBI_description,PBI_priority,PBI_initial_estimate,PBI_adjustment_factor,PBI_adjusted_estimate) VALUES(project,sprint,description,priority,initial_estimate,adjustment_factor,adjusted_estimate)");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
		}
		else if(e.getActionCommand().equals("StandardDialog.Cancel"))
		{
			setVisible(false);
		}
	}
	
	private Vector<JTextField> _textField = new Vector<JTextField>();;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}


/*		ConnectionModel cm = Scrummer.getModels().getConnectionModel();
		final java.sql.Connection con = cm.getConnection();
		java.sql.Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM PBI");
		
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
		
		Object[] vrstica = new Object[]{};
		
		model.addRow(vrstica);
		
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
		JButton editButton = new StandardButton(i18n.tr("Add row"));
		bottomPanel.add(backButton);
		bottomPanel.add(editButton);
		
		GridBagConstraints bottomGc = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 0.3);
		bottomGc.gridy = 1;
		parentPanel.add(bottomPanel, BorderLayout.PAGE_END);
		add(parentPanel);
		
		Util.centre(this);
	}
	

	public void actionPerformed(ActionEvent e) 
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("Add row"))
		{
			System.out.println("habuamua!!!");
		}
	}

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
	

*/