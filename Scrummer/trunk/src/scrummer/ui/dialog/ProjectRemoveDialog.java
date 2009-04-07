package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.OperationListener;
import scrummer.listener.ProjectListener;
import scrummer.model.ProjectModel;
import scrummer.model.swing.ProjectComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Remove project dialog
 */
public class ProjectRemoveDialog extends TwoButtonDialog
								 implements ProjectListener {

	/**
	 * Constructor
	 * 
	 * @param owner parent frame
	 */
	public ProjectRemoveDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Remove Project"));
		Panel.setLayout(new GridLayout(1,2));
		
		_projectModel = Scrummer.getModels().getProjectModel();
		_projectComboBoxModel = _projectModel.getProjectComboBoxModel();
		
		_projectModel.addProjectListener(this);
		
		int k = 10;
		
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Select project"),
				0, k, k, k));
		
		JLabel projectLabel = new JLabel(i18n.tr("Project") + ":");
		
		JComboBox projectInput = new JComboBox();
		projectInput.setModel(_projectComboBoxModel);
		_projectInput = projectInput;
		_projectComboBoxModel.refresh();
			
		Panel.add(projectLabel);
		Panel.add(projectInput);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k));
		
		setSize(new Dimension(320, 150));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK")
		{
			int index = _projectInput.getSelectedIndex(); 
			if (index != -1)	
			{
				int projectId = _projectComboBoxModel.getId(index);
				_projectModel.removeProject(projectId);
			}
			else
			{
				Util.showError(
					this, 
					i18n.tr("Could not delete project. Select one project first, " +
							"then click on Remove button."), 
					i18n.tr("Error"));
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, ProjectOperation identifier, String message) {
		switch (type)
		{
		case Remove:
			switch (identifier)
			{
			case Project:
				setVisible(false);
				break;
			}
			break;
		}
	}	
	
	@Override
	public void operationFailed(DataOperation type, ProjectOperation identifier, String message) {
		Util.showError(this, i18n.tr("Could not remove project: " + message), i18n.tr("Error"));
	}

	@Override
	public void setVisible(boolean b) {
		
		if (b)
		{
			if (_projectComboBoxModel.getSize() > 0)
			{
				_projectInput.setEnabled(true);
				_projectInput.setSelectedIndex(0);
			}
			else
			{
				_projectInput.setEnabled(false);
			}
		}
		else
		{
			_projectModel.removeProjectListener(this);
		}
		
		super.setVisible(b);
	}
	
	/// project input combo box
	private JComboBox _projectInput;
	/// project model
	private ProjectModel _projectModel;
	/// project combo box model
	private ProjectComboBoxModel _projectComboBoxModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 4206239821521998017L;
}
