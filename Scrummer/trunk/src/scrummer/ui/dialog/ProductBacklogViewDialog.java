package scrummer.ui.dialog;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.listener.OperationListener;
import scrummer.listener.ProductBacklogListener;
import scrummer.model.ConnectionModel;
import scrummer.model.ImpedimentModel;
import scrummer.model.ProductBacklogModel;
import scrummer.model.swing.DeveloperTableModel;
import scrummer.model.swing.ImpedimentTableModel;
import scrummer.model.swing.ProductBacklogTableModel;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;




import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class ProductBacklogViewDialog 
	extends JDialog 
	implements MouseListener, ActionListener, ProductBacklogListener
{
	
	/**
	 * Constructor
	 * @param owner owner frame
	 */
	public ProductBacklogViewDialog(Frame owner) 
	{	
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("View Product Backlog"));
		setSize(new Dimension(820,340));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BorderLayout());
		
		setLayout(new BorderLayout());
		
		ProductBacklogModel pbmodel = Scrummer.getModels().getProductBacklogModel();
		_productbacklogModel = pbmodel;
		_productbacklogModel.addProductBacklogListener(this);
		ProductBacklogTableModel model = pbmodel.getProductBacklogTableModel();
		_productbacklogTableModel = model;
		JTable table = new JTable(model);
		_productbacklogTable = table;
		// refresh data from database
		model.refresh();
		
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
		
	    JScrollPane scrollPane = new JScrollPane(table);
		parentPanel.add(scrollPane, BorderLayout.CENTER);
		
		int k = 10;
		scrollPane.setBorder(
			Util.createSpacedTitleBorder(
			k, k, k, k,
			i18n.tr("Product Backlog table"), 
			0, k, k, k));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, k, k));
		bottomPanel.setAlignmentX(RIGHT_ALIGNMENT);
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(k, k, 2, 2));
		
		JButton removeButton = new StandardButton(i18n.tr("Remove"));
		removeButton.setActionCommand("RemoveButton");
		removeButton.addActionListener(this);
		
		JButton closeButton = new StandardButton(i18n.tr("Close"));
		closeButton.setActionCommand("CloseButton");
		closeButton.addActionListener(this);
		
		bottomPanel.add(removeButton);
		bottomPanel.add(closeButton);
		
		GridBagConstraints bottomGc = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 0.3);
		bottomGc.gridy = 1;
		parentPanel.add(bottomPanel, BorderLayout.PAGE_END);
		add(parentPanel);
		
		Util.centre(this);
	}
	

	public void actionPerformed(ActionEvent e) 
	{
		String cmd = e.getActionCommand();
		if (cmd == "RemoveButton")
		{
			int selectedRow = _productbacklogTable.getSelectedRow();
			if (selectedRow == (-1))
			{
				JOptionPane.showMessageDialog(
					this, 
					i18n.tr("Cannot remove from Product Backlog because no Product Backlog item is selected. " +
							"Click on a table row and then press remove."),
					i18n.tr("Error"),
					JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				_productbacklogTableModel.removeRow(selectedRow);
			}
		}
		else if (cmd == "CloseButton")
		{
			setVisible(false);
		}
	}

	@Override
	public void setVisible(boolean b) 
	{	
		if (!b)
		{
			_productbacklogModel.removeProductBacklogListner(this);
		}
		
		super.setVisible(b);
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
	
	@Override
	public void operationFailed(DataOperation type, ProductBacklogOperation identifier, String message) 
	{
		switch (type)
		{
		case Remove:
			JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, ProductBacklogOperation identifier, String message) {}
	
	/// product backlog table
	private JTable _productbacklogTable;
	/// product backlog model
	private ProductBacklogModel _productbacklogModel;
	/// product backlog table model
	private ProductBacklogTableModel _productbacklogTableModel;
	/// serialization id 
	private static final long serialVersionUID = 456365932759827558L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	
}