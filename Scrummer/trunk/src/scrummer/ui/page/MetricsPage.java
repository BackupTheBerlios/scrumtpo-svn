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
import scrummer.model.swing.SprintBacklogTableModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.DailyScrumMeetingDialog;
import scrummer.uicomponents.AddEditRemovePanel;
import scrummer.uicomponents.NiceTable;

/**
 * A page that displays all tasks on current project and sprint
 */
public class MetricsPage extends BasePage implements ActionListener 
{
	public MetricsPage(MainFrame mainFrame) {
		super(mainFrame);		
		setLayout(new GridLayout(1,1));
		
		Models m = Scrummer.getModels();
		_sprintbacklogModel = m.getSprintBacklogModel();
		_sprintbacklogTableModel = _sprintbacklogModel.getSprintBacklogTableModel();
		_sprintbacklogTableModel.refresh();
		
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		AddEditRemovePanel toolbar = new AddEditRemovePanel();
		toolbar.addActionListener(this);
		
		NiceTable sprintbacklogTable = new NiceTable();
		sprintbacklogTable.setModel(_sprintbacklogTableModel);
		sprintbacklogTable.setBackground(Color.WHITE);
		sprintbacklogTable.setAdjacentComponents
			(toolbar.Add, toolbar.Remove);
		_sprintbacklogTable = sprintbacklogTable;
		
		JScrollPane scrollPane = new JScrollPane(sprintbacklogTable);				
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
			DailyScrumMeetingDialog dialog = new DailyScrumMeetingDialog(getMainFrame());
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
	
	/// sprint backlog days table widget
	private NiceTable _sprintbacklogTable;
	/// sprint backlog model
	private SprintBacklogModel _sprintbacklogModel;
	/// sprint backlog table model
	private SprintBacklogTableModel _sprintbacklogTableModel;
	/// serialization id
	private static final long serialVersionUID = -6725138760356156722L;
}
