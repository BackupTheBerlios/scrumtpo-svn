package scrummer.ui.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import scrummer.Scrummer;
import scrummer.model.ImpedimentModel;
import scrummer.model.Models;
import scrummer.model.swing.ImpedimentTableModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.ImpedimentsAddDialog;
import scrummer.ui.dialog.ImpedimentsChangeDialog;
import scrummer.uicomponents.AddEditRemovePanel;
import scrummer.uicomponents.NiceTable;

/**
 * A page that displays all impediments on current project and sprint
 */
public class ImpedimentPage extends BasePage implements ActionListener 
{
	public ImpedimentPage(MainFrame mainFrame) {
		super(mainFrame);		
		setLayout(new GridLayout(1,1));
		
		Models m = Scrummer.getModels();
		_impedimentModel = m.getImpedimentModel();
		_impedimentTableModel = _impedimentModel.getImpedimentTableModel();
		_impedimentTableModel.refresh();
		
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		AddEditRemovePanel toolbar = new AddEditRemovePanel();
		toolbar.addActionListener(this);
		
		NiceTable impedimentTable = new NiceTable();
		impedimentTable.setModel(_impedimentTableModel);
		impedimentTable.setBackground(Color.WHITE);
		impedimentTable.setAdjacentComponents
			(toolbar.Add, toolbar.Remove);
		_impedimentTable = impedimentTable;
		
		JScrollPane scrollPane = new JScrollPane(impedimentTable);				
		scrollPane.setBackground(Color.WHITE);
		
		setBackground(Color.WHITE);
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		box.add(toolbar);
		box.add(scrollPane);
		
		add(box);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd == "Add")
		{
			ImpedimentsAddDialog dialog;
			try {
				dialog = new ImpedimentsAddDialog(getMainFrame());
				Util.centre(dialog);
				dialog.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (cmd == "Edit")
		{
			int selection = _impedimentTable.getSelectedRow();
			if (selection != -1)
			{
				int id = _impedimentTableModel.getPrimaryKey(selection);
				ImpedimentsChangeDialog dialog = new ImpedimentsChangeDialog(getMainFrame(), id);
				Util.centre(dialog);
				dialog.setVisible(true);
			}
		}
		else if (cmd == "Remove")
		{
			int selection = _impedimentTable.getSelectedRow();
			if (selection != -1)
			{
				int id = _impedimentTableModel.getPrimaryKey(selection);
				_impedimentModel.remove(id);
			}
		}
	}
	
	/// impediment days table widget
	private NiceTable _impedimentTable;
	/// impediment model
	private ImpedimentModel _impedimentModel;
	/// impediment table model
	private ImpedimentTableModel _impedimentTableModel;
	/// serialization id
	private static final long serialVersionUID = -6725138760356156722L;
}
