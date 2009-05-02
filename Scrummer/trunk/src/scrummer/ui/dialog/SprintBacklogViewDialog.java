package scrummer.ui.dialog;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.listener.SprintBacklogListener;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.SprintBacklogTableModel;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Developer overview and removal dialog
 */
public class SprintBacklogViewDialog 
	extends JDialog 
	implements MouseListener, ActionListener, SprintBacklogListener
{
	/**
	 * Constructor
	 * @param owner owner frame
	 */
	public SprintBacklogViewDialog(Frame owner)
	{	
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("View Sprint Backlog"));
		setSize(new Dimension(1020,480));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BorderLayout());
		
		setLayout(new BorderLayout());
		
		SprintBacklogModel sbModel = Scrummer.getModels().getSprintBacklogModel();
		_sprintbacklogModel = sbModel;
		_sprintbacklogModel.addSprintBacklogListener(this);
		SprintBacklogTableModel model = sbModel.getSprintBacklogTableModel();
		_sprintbacklogTableModel = model;
		JTable table = new JTable(model);
		_sprintbacklogTable = table;
		// refresh data from database
		model.refresh();
		
		table.setSize(550, 470);
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
			i18n.tr("Sprint Backlog table"), 
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
			int selectedRow = _sprintbacklogTable.getSelectedRow();
			if (selectedRow == (-1))
			{
				JOptionPane.showMessageDialog(
					this, 
					i18n.tr("Cannot remove Sprint Backlog item because no item is selected. " +
							"Click on a table row and then press remove."),
					i18n.tr("Error"),
					JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				_sprintbacklogTableModel.removeRow(selectedRow);
			}
		}
		else if (cmd == "CloseButton")
		{
			setVisible(false);
		}
	}

	@Override
	public void setVisible(boolean b) {
		
		if (!b)
		{
			_sprintbacklogModel.removeSprintBacklogListener(this);
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
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) 
	{
		switch (type)
		{
		case Remove:
			JOptionPane.showMessageDialog(this, message, i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {} 
	
	/// developer table
	private JTable _sprintbacklogTable;
	/// developer model
	private SprintBacklogModel _sprintbacklogModel;
	/// developer table model
	private SprintBacklogTableModel _sprintbacklogTableModel;
	/// serialization id 
	private static final long serialVersionUID = 456365932759827558L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());	
}