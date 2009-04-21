package scrummer.ui.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.AllTaskTableModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.TaskAddDialog;
import scrummer.uicomponents.AddEditRemovePanel;
import scrummer.uicomponents.NiceTable;

/**
 * A page that displays all tasks on current project and sprint
 */
public class TaskPage 
	extends BasePage 
	implements ActionListener {

	/**
	 * Constructor
	 * 
	 * @param mainFrame main frame
	 * @param sprintId sprint
	 */
	public TaskPage(MainFrame mainFrame) {
		super(mainFrame);		
		setLayout(new GridLayout(1,1));
		
		Models m = Scrummer.getModels();
		_sprintBacklogModel = m.getSprintBacklogModel();
		_taskTableModel = _sprintBacklogModel.getTaskTableModel();
		_taskTableModel.refresh();
		
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		AddEditRemovePanel toolbar = new AddEditRemovePanel();
		toolbar.addActionListener(this);
		
		NiceTable taskTable = new NiceTable();
		taskTable.setModel(_taskTableModel);
		taskTable.setBackground(Color.WHITE);
		taskTable.setAdjacentComponents
			(toolbar.Add, toolbar.Remove);
		
		JScrollPane scrollPane = new JScrollPane(taskTable);				
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
			TaskAddDialog dialog = new TaskAddDialog(getMainFrame());
			Util.centre(dialog);
			dialog.setVisible(true);
		}
		else if (cmd == "Edit")
		{
			
		}
		else if (cmd == "Remove")
		{
			
		}
	}
	
	/// sprint backlog model
	private SprintBacklogModel _sprintBacklogModel;
	/// task table model
	private AllTaskTableModel _taskTableModel;
	/// serialization id
	private static final long serialVersionUID = -6725138760356156722L;
}
