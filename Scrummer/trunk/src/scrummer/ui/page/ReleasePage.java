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
import scrummer.ui.MainFrame;
import scrummer.uicomponents.AddEditRemovePanel;

/**
 * A page that lists releases and corresponding pbi's
 */
public class ReleasePage 
	extends BasePage 
	implements ActionListener {

	public ReleasePage(MainFrame parent) {
		super(parent);
		
		setLayout(new GridLayout(1,1));
		
		Models m = Scrummer.getModels();
		
		// _metricTableModel = _metricModel.getMetricTableModel();
		// _taskComboBoxModel = m.getTaskModel().getTaskComboBoxModel();
		// _sprintProjectComboBoxModel = m.getSprintBacklogModel().getSprintProjectComboBoxModel();
		
	
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		_toolbar = new AddEditRemovePanel();
		_toolbar.addActionListener(this);
		
		/*
		_releaseTable = new NiceTable();
		_releaseTable.setBackground(Color.WHITE);
		_releaseTable.setModel(_metricTableModel);
		*/
		JScrollPane scrollPane = new JScrollPane();				
		scrollPane.setBackground(Color.WHITE);
		
		setBackground(Color.WHITE);
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		box.add(_toolbar);
		box.add(scrollPane);
		
		add(box);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	private AddEditRemovePanel _toolbar;
	/// serialization id
	private static final long serialVersionUID = -4739847690347968524L;		
}
