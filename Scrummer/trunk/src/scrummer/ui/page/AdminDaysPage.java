package scrummer.ui.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import scrummer.Scrummer;
import scrummer.model.AdminDaysModel;
import scrummer.model.Models;
import scrummer.model.swing.AdminDaysTableModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.AdminDaysAddDialog;
import scrummer.uicomponents.AddEditRemovePanel;
import scrummer.uicomponents.NiceTable;

/**
 * A page that displays all tasks on current project and sprint
 */
public class AdminDaysPage 
	extends BasePage 
	implements ActionListener {

	/**
	 * Constructor
	 * 
	 * @param mainFrame main frame
	 * @param sprintId sprint
	 */
	public AdminDaysPage(MainFrame mainFrame) {
		super(mainFrame);		
		setLayout(new GridLayout(1,1));
		
		Models m = Scrummer.getModels();
		_admindaysModel = m.getAdminDaysModel();
		_admindaysTableModel = _admindaysModel.getAdminDaysTableModel();
		_admindaysTableModel.refresh();
		
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		AddEditRemovePanel toolbar = new AddEditRemovePanel();
		toolbar.addActionListener(this);
		
		NiceTable admindaysTable = new NiceTable();
		admindaysTable.setModel(_admindaysTableModel);
		admindaysTable.setBackground(Color.WHITE);
		admindaysTable.setAdjacentComponents
			(toolbar.Add, toolbar.Remove);
		_admindaysTable = admindaysTable;
		
		JScrollPane scrollPane = new JScrollPane(admindaysTable);				
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
		if (cmd == "Add") {
			AdminDaysAddDialog dialog = new AdminDaysAddDialog(getMainFrame());
			Util.centre(dialog);
			dialog.setVisible(true);
		} else if (cmd == "Edit")
		{
		
		}
		else if (cmd == "Remove")
		{
			int selection = _admindaysTable.getSelectedRow();
			if (selection != -1)
			{
				int emp = _admindaysTableModel.getEmployee(selection);
				Date day = _admindaysTableModel.getMeasureDay(selection);
				_admindaysModel.remove(emp,day);
			}
		}
	}
	
	/// administrative days table widget
	private NiceTable _admindaysTable;
	/// administrative days model
	private AdminDaysModel _admindaysModel;
	/// administrative days table model
	private AdminDaysTableModel _admindaysTableModel;
	/// serialization id
	private static final long serialVersionUID = -6725138760356156722L;
}
