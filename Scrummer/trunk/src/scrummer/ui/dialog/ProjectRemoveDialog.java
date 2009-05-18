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
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
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
		
		_projectModel = Scrummer.getModels().getProjectModel();
		_projectComboBoxModel = _projectModel.getProjectComboBoxModel();
		
		_projectModel.addProjectListener(this);
		
		int k = 10;
		
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Select project"),
				0, k, k, k));
		
		_formBuilder = new FormBuilder(Panel);
		_projectInput = _formBuilder.addComboBoxInput(i18n.tr("Project") + ":");	
		_projectInput.setIVModel(_projectComboBoxModel);
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, k, k, k));
		
		setSize(new Dimension(320, 150));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		if (cmd == "StandardDialog.OK")
		{
			if (_projectInput.isSelected())
			{				
				_projectModel.removeProject(_projectInput.getSelectedId());
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
		if (!b) {
			_projectModel.removeProjectListener(this);
		}
		super.setVisible(b);
	}
	
	/// project input combo box
	private StandardComboBox _projectInput;
	/// project model
	private ProjectModel _projectModel;
	/// project combo box model
	private ProjectComboBoxModel _projectComboBoxModel;
	/// form building class
	private FormBuilder _formBuilder;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 4206239821521998017L;
}
