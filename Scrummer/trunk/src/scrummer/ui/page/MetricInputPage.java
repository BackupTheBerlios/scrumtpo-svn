package scrummer.ui.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.ui.MainFrame;
import scrummer.ui.MetricPageToolbar;
import scrummer.uicomponents.NiceTable;

/**
 * Metric input page shows a table of metrics and allows input/edit of them.
 * There are also options for calculating known metrics.
 */
public class MetricInputPage 
	extends BasePage 
	implements ActionListener, ItemListener {

	public MetricInputPage(MainFrame mainFrame) {
		super(mainFrame);
		setLayout(new GridLayout(1,1));
		
		Models m = Scrummer.getModels();
		
		/*
		_sprintBacklogModel = m.getSprintBacklogModel();
		_taskModel = m.getTaskModel();
		_taskTableModel = _sprintBacklogModel.getTaskTableModel();
		_taskTableModel.refresh();
		*/
		
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		MetricPageToolbar toolbar = new MetricPageToolbar();
		// toolbar.addActionListener(this);
		
		NiceTable taskTable = new NiceTable();
		// taskTable.setModel(_taskTableModel);
		taskTable.setBackground(Color.WHITE);
		/*
		taskTable.setAdjacentComponents
			(toolbar.Add, toolbar.Remove);
		*/
		// _taskTable = taskTable;
		
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
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}	
	
	/// serialization id
	private static final long serialVersionUID = -4897261591430383488L;
}
