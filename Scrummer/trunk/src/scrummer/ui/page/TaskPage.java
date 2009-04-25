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
import scrummer.model.TaskModel;
import scrummer.model.TaskModelCommon;
import scrummer.model.swing.AllTaskTableModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.TaskAddDialog;
import scrummer.ui.dialog.TaskChangeDialog;
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
		_taskModel = m.getTaskModel();
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
		_taskTable = taskTable;
		
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
			int selection = _taskTable.getSelectedRow();
			if (selection != -1)
			{
				int id = _taskTableModel.getPrimaryKey(selection);
				TaskChangeDialog dialog = new TaskChangeDialog(getMainFrame(), id);
				Util.centre(dialog);
				dialog.setVisible(true);
			}
		}
		else if (cmd == "Remove")
		{
			int selection = _taskTable.getSelectedRow();
			if (selection != -1)
			{
				int id = _taskTableModel.getPrimaryKey(selection);
				_taskModel.remove(id);
			}
		}
	}
	
	/// task table widget
	private NiceTable _taskTable;
	/// sprint backlog model
	private SprintBacklogModel _sprintBacklogModel;
	/// task model
	private TaskModel _taskModel;
	/// task table model
	private AllTaskTableModel _taskTableModel;
	/// serialization id
	private static final long serialVersionUID = -6725138760356156722L;
}
