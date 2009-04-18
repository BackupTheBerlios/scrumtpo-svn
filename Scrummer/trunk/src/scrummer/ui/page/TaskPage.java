package scrummer.ui.page;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.AllTaskTableModel;
import scrummer.model.swing.SprintBacklogTableModel;
import scrummer.model.swing.TaskTableModel;
import scrummer.ui.MainFrame;
import scrummer.uicomponents.NiceTable;

/**
 * A page that displays all tasks on current project and sprint
 */
public class TaskPage extends BasePage {

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
		
		NiceTable taskTable = new NiceTable();
		taskTable.setModel(_taskTableModel);
		taskTable.setBackground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane(taskTable);				
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		setBackground(Color.WHITE);
		add(scrollPane);
	}

	/// sprint backlog model
	private SprintBacklogModel _sprintBacklogModel;
	/// task table model
	private AllTaskTableModel _taskTableModel;
	/// serialization id
	private static final long serialVersionUID = -6725138760356156722L;
}
