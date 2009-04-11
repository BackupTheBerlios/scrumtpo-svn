package scrummer.ui.page;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sun.org.apache.xml.internal.security.utils.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProductBacklogOperation;
import scrummer.listener.ProductBacklogListener;
import scrummer.model.ProductBacklogModel;
import scrummer.model.swing.ProductBacklogTableModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.ProductBacklogAddDialog;
import scrummer.ui.dialog.ProductBacklogChangeDialog;
import scrummer.uicomponents.NiceTable;
import scrummer.uicomponents.StandardButton;

/**
 * This page contains product backlog table and buttons for adding/editing selected row
 */
public class ProductBacklogPage 
	extends BasePage
	implements MouseListener, ActionListener {

	/**
	 * Constructor
	 */
	public ProductBacklogPage(MainFrame mainFrame) {
		super(mainFrame);
		
		_productBacklogModel = Scrummer.getModels().getProductBacklogModel();
		_productBacklogTableModel = _productBacklogModel.getProductBacklogTableModel();
		
		setLayout(new GridLayout(1,1));
		_productBacklogTableModel.refresh();
		
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		toolbar.setBackground(Color.WHITE);
		toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 1));
		
		JButton addButton = new StandardButton(i18n.tr("Add"));
		addButton.setActionCommand("Add");
		addButton.addActionListener(this);
		
		JButton editButton = new StandardButton(i18n.tr("Edit"));
		editButton.setActionCommand("Edit");
		editButton.addActionListener(this);
		
		JButton removeButton = new StandardButton(i18n.tr("Remove"));
		removeButton.setActionCommand("Remove");
		removeButton.addActionListener(this);
		
		toolbar.add(addButton);
		toolbar.add(editButton);
		toolbar.add(removeButton);
		
		NiceTable table = new NiceTable();
		table.setModel(_productBacklogTableModel);
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5,5,5,5), 
				BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
		tableScroll.setBackground(Color.WHITE);
		table.addMouseListener(this);
		_productBacklogTable = table;
				
		box.add(toolbar);
		box.add(tableScroll);
		
		add(box);		
		tableScroll.getViewport().setBackground(Color.WHITE);
		
		table.validate();
		setBackground(Color.WHITE);
		validate();
		
		setFocusTraversalPolicy(new NiceTable.FocusPolicy());
	}
	
	/**
	 * Show change dialog if a row was selected
	 * @param mainFrame main frame
	 * @param table table on which to check for selection
	 * @param tableModel table model from which to get pbi id
	 */
	private void showProductBacklogChangeDialog(MainFrame mainFrame, JTable table, ProductBacklogTableModel tableModel)
	{
		int row = table.getSelectedRow();
		if (row != -1)
		{
			int pbiId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
			ProductBacklogChangeDialog dialog = new ProductBacklogChangeDialog(mainFrame, pbiId);
			Util.centre(dialog);
			dialog.setVisible(true);
		}
	}
	
	@Override
	public void setVisible(boolean flag) {
	
		super.setVisible(flag);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2)
		{			
			showProductBacklogChangeDialog(getMainFrame(), _productBacklogTable, _productBacklogTableModel);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		String cmd = e.getActionCommand();
		if (cmd == "Add")
		{
			ProductBacklogAddDialog dialog = new ProductBacklogAddDialog(getMainFrame());
			Util.centre(dialog);	
			dialog.setVisible(true);
		}
		else if (cmd == "Edit")
		{
			showProductBacklogChangeDialog(getMainFrame(), _productBacklogTable, _productBacklogTableModel);
		}
		else if (cmd == "Remove")
		{
			int selectedRow = _productBacklogTable.getSelectedRow();
			if (selectedRow == (-1))
			{
				JOptionPane.showMessageDialog(
					getMainFrame(), 
					i18n.tr("Cannot remove from Product Backlog because no Product Backlog item is selected. " +
							"Click on a table row and then press remove."),
					i18n.tr("Error"),
					JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				_productBacklogTableModel.removeRow(selectedRow);
			}
		}
	}
	
	/// product backlog table
	private JTable _productBacklogTable;
	/// product backlog model
	private ProductBacklogModel _productBacklogModel;
	/// product backlog table model
	private ProductBacklogTableModel _productBacklogTableModel;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -3054683430416992071L;
	
}
