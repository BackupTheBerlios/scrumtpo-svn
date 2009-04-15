package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.listener.SprintBacklogListener;
import scrummer.model.Models;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.TeamComboBoxModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Sprint common dialog for addition/modification
 */
public class SprintDialog extends TwoButtonDialog implements SprintBacklogListener {

	/**
	 * Constructor
	 * 
	 * @param owner main frame
	 */
	public SprintDialog(Frame owner) {
	
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Add Sprint"));
	
		Models m = Scrummer.getModels();
		
		_teamComboBoxModel = m.getDeveloperModel().getTeamComboBoxModel();
		_teamComboBoxModel.refresh();
		_sprintBacklogModel = m.getSprintBacklogModel();
		_sprintBacklogModel.addSprintBacklogListener(this);
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k, k, k, k, 
				i18n.tr("Sprint"), 
				0, k - 4, k - 4, k));
		
		FormBuilder fb = new FormBuilder(Panel);		
		_descriptionInput = 
			fb.addSelectedTextInput(i18n.tr("Description") + ":", "Description");
		_teamInput = 
			fb.addComboBoxInput(i18n.tr("Team") + ":");
		_teamInput.setModel(_teamComboBoxModel);
		_startDateInput = 
			fb.addSelectedTextInput(i18n.tr("Start") + ":", "StartingDate");
		_estimatedInput = 
			fb.addSelectedTextInput(i18n.tr("Estimated") + ":", "EstimatedDate");
		_endDateInput =
			fb.addSelectedTextInput(i18n.tr("End") + ":", "EndDate");
		_lengthInput =
			fb.addSelectedTextInput(i18n.tr("Length") + ":", "Length");
		fb.setCellSpacing(k, k - 2);
		
		OK.setText(i18n.tr("Add"));
		
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 7));
		
		setSize(new Dimension(300, 320));
	}
	
	@Override
	public void operationSucceeded(DataOperation type, SprintBacklogOperation identifier, String message) {}
	
	@Override
	public void operationFailed(DataOperation type, SprintBacklogOperation identifier, String message) {}
	
	@Override
	public void setVisible(boolean b) {
	
		if (!b)
		{
			_sprintBacklogModel.removeSprintBacklogListener(this);
		}
		
		super.setVisible(b);
	}

	/// sprint backlog model
	protected SprintBacklogModel _sprintBacklogModel;
	/// various input fields
	protected SelectedTextField _descriptionInput, _startDateInput, _endDateInput, _lengthInput, _estimatedInput;
	/// team combo box
	protected JComboBox _teamInput;
	/// team dropdown box
	protected TeamComboBoxModel _teamComboBoxModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5484362646565900921L;
}
