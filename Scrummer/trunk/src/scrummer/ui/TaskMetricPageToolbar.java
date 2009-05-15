package scrummer.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.DeveloperModel;
import scrummer.model.Models;
import scrummer.model.ProductBacklogModel;
import scrummer.model.TaskModel;
import scrummer.model.swing.SprintPBITableModel;
import scrummer.uicomponents.StandardComboBox;

/**
 * This page displays employee and pbi combo boxes, that affect underlying 
 */
public class TaskMetricPageToolbar 
	extends JPanel 
	implements ActionListener {

	public TaskMetricPageToolbar() {
		setBackground(Color.WHITE);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		Models m = Scrummer.getModels();
		TaskModel taskModel = m.getTaskModel();
		DeveloperModel developerModel = m.getDeveloperModel(); 
		ProductBacklogModel productModel = m.getProductBacklogModel();
		_sprintPbiTableModel = m.getSprintBacklogModel().getSprintPBITableModel();
		
		FormBuilder fb = new FormBuilder(this, FormBuilder.Layout.LeftToRight);
		
		_taskInput =
			fb.addComboBoxInput(i18n.tr("Task") + ":");
		_taskInput.addActionListener(this);
		_taskInput.setIVModel(taskModel.getProjectSprintTaskComboBoxModel());
		
		_employeeInput = 
			fb.addComboBoxInput(i18n.tr("Employee") + ":");
		_employeeInput.addActionListener(this);
		_employeeInput.setIVModel(developerModel.getEmployeeComboBoxModel());
		
		fb.setCellSpacing(5, 5);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _employeeInput) {
			_sprintPbiTableModel.setEmployeeId(_employeeInput.getSelectedId());
		} else {
			_sprintPbiTableModel.setTaskId(_taskInput.getSelectedId());
		}
	}
	
	/// link between sprint and pbi table model
	SprintPBITableModel _sprintPbiTableModel;
	/// employee and pbi input
	StandardComboBox _employeeInput, _taskInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serializatino id
	private static final long serialVersionUID = 5809025304631771019L;
	
}
