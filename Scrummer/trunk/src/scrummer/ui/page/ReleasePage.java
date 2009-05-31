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
import scrummer.model.ReleaseModel;
import scrummer.model.swing.ReleaseTableModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.ReleaseAddDialog;
import scrummer.ui.dialog.ReleaseEditDialog;
import scrummer.uicomponents.AddEditRemovePanel;
import scrummer.uicomponents.NiceTable;

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
		_releaseModel = m.getReleaseModel();
		_releaseTableModel = _releaseModel.getReleaseTableModel();
		
		// _metricTableModel = _metricModel.getMetricTableModel();
		// _taskComboBoxModel = m.getTaskModel().getTaskComboBoxModel();
		// _sprintProjectComboBoxModel = m.getSprintBacklogModel().getSprintProjectComboBoxModel();		
	
		Box box = new Box(BoxLayout.Y_AXIS);
		int k = 1;
		box.setBorder(BorderFactory.createEmptyBorder(k, k, k, k));
		
		_toolbar = new AddEditRemovePanel();
		_toolbar.addActionListener(this);
		
		_releaseTable = new NiceTable();
		_releaseTable.setBackground(Color.WHITE);
		_releaseTable.setModel(_releaseTableModel);
		_releaseTableModel.refresh();
		_releaseTable.invalidate();
			
		JScrollPane scrollPane = new JScrollPane(_releaseTable);				
		scrollPane.setBackground(Color.WHITE);
		
		setBackground(Color.WHITE);
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		box.add(_toolbar);
		box.add(scrollPane);
		
		add(box);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Add")) {
			ReleaseAddDialog dialog = new ReleaseAddDialog(getMainFrame());
			Util.centre(dialog);
			dialog.setVisible(true);
		} else if (cmd.equals("Edit")) {
			int selected = _releaseTable.getSelectedRow();
			if (selected != -1) {
				int releaseId = _releaseTableModel.findReleaseId(selected);
				ReleaseEditDialog dialog = new ReleaseEditDialog(getMainFrame(), releaseId);
				Util.centre(dialog);
				dialog.setVisible(true);
			}
		} else if (cmd.equals("Remove")) {
			// if item is selected
			int selected = _releaseTable.getSelectedRow();
			if (selected != -1) {
				// delete it
				int releaseId = _releaseTableModel.findReleaseId(selected);
				_releaseModel.removeRelease(releaseId);
				_releaseModel.removeAllReleasePbi(releaseId);
			}			
		}
	}
	
	/// release model
	private ReleaseModel _releaseModel;
	/// release table
	private NiceTable _releaseTable;
	/// table model of releases and their corresponding pbi's
	private ReleaseTableModel _releaseTableModel;
	/// toolbar
	private AddEditRemovePanel _toolbar;
	/// serialization id
	private static final long serialVersionUID = -4739847690347968524L;		
}
