package scrummer.ui.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.model.Models;
import scrummer.model.SprintBacklogModel;
import scrummer.model.NavigationModel.Link;
import scrummer.model.swing.SprintDescriptionListModel;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.SprintAddDialog;
import scrummer.ui.dialog.SprintChangeDialog;
import scrummer.uicomponents.StandardButton;

/**
 * Allow addition/removal and modifications of sprint backlogs
 * 
 * Also make it possible to 
 */
public class SprintBacklogPage 
	extends BasePage
	implements ActionListener {

	/**
	 * Constructor
	 * 
	 * @param mainFrame main frame
	 */
	public SprintBacklogPage(MainFrame mainFrame) {
		super(mainFrame);
	
		int k = 10;
		int height = 310;
		
		Models m = Scrummer.getModels();
		_sprintBacklogModel = m.getSprintBacklogModel();
		_sprintDescriptionModel = _sprintBacklogModel.getSprintDescriptionListModel();
		_sprintDescriptionModel.refresh();
		
		Box horizontalSplit = new Box(BoxLayout.X_AXIS);		
		
		Box leftSide = new Box(BoxLayout.Y_AXIS);
		leftSide.setPreferredSize(new Dimension(250, height));
		leftSide.setBackground(Color.WHITE);

		JList sprintList = new JList();
		sprintList.setMinimumSize(new Dimension(300, 210));
		sprintList.setPreferredSize(new Dimension(300, 210));
		sprintList.setFixedCellWidth(200);
		leftSide.setBorder(
			Util.createSpacedTitleBorder(
				0, k, k, k, 
				i18n.tr("Sprints"), 
				0, k - 6, k, 4));
		sprintList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		sprintList.setModel(_sprintDescriptionModel);
		_sprintList = sprintList;
			
		JPanel sprintButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sprintButtonPanel.setBackground(Color.WHITE);
		sprintButtonPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		JButton addSprintButton = new StandardButton(i18n.tr("Add"));
		addSprintButton.setActionCommand("AddSprint");
		addSprintButton.addActionListener(this);
		
		JButton editSprintButton = new StandardButton(i18n.tr("Edit"));
		editSprintButton.setActionCommand("EditSprint");
		editSprintButton.addActionListener(this);
		
		JButton removeSprintButton = new StandardButton(i18n.tr("Remove"));
		removeSprintButton.setActionCommand("RemoveSprint");
		removeSprintButton.addActionListener(this);
		
		sprintButtonPanel.add(addSprintButton);
		sprintButtonPanel.add(editSprintButton);
		sprintButtonPanel.add(removeSprintButton);
		
		sprintButtonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		
		leftSide.add(sprintList);
		leftSide.add(sprintButtonPanel);
		
		Box rightSide = new Box(BoxLayout.Y_AXIS);
		rightSide.setPreferredSize(new Dimension(300, height));

		Box horizontalRight = new Box(BoxLayout.X_AXIS);
		horizontalRight.setPreferredSize(new Dimension(300, height));
		
		_taskLink   = Util.addLink(horizontalRight, Link.SprintBacklogTasks);
		_hurdleLink = Util.addLink(horizontalRight, Link.SprintBacklogHurdles);
		
		rightSide.add(horizontalRight);
		
		horizontalSplit.add(leftSide);
		horizontalSplit.add(rightSide);
		
		horizontalSplit.setBackground(Color.WHITE);
		
		add(horizontalSplit);
		setBackground(Color.WHITE);
		
		if (_sprintDescriptionModel.isEmpty())
		{
			_taskLink.setEnabled(false);
			_hurdleLink.setEnabled(false);
		}
		else
		{
			sprintList.setSelectedIndex(0);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd == "AddSprint")
		{
			SprintAddDialog dialog = new SprintAddDialog(getMainFrame());
			Util.centre(dialog);
			dialog.setVisible(true);
		}
		else if (cmd == "EditSprint")
		{
			int selected = _sprintList.getSelectedIndex();
			if (selected != -1)				
			{			
				int sprintId = _sprintDescriptionModel.getId(selected);
				
				SprintChangeDialog dialog = new SprintChangeDialog(getMainFrame(), sprintId);
				Util.centre(dialog);
				dialog.setVisible(true);
			}
			else
			{
				Util.showError(
					this, 
					i18n.tr("Cannot display modification dialog. " +
							"First select a sprint by clicking on it, " +
							"then click Edit button."), i18n.tr("Error"));
			}
		}
		else if (cmd == "RemoveSprint")
		{
			int selected = _sprintList.getSelectedIndex();
			if (selected != -1)
			{
				int sprintId = _sprintDescriptionModel.getId(selected);
				_sprintBacklogModel.removeSprint(sprintId);
			}
		}
	}
	
	/// sprint list
	private JList _sprintList;
	/// task link 
	private scrummer.ui.Link _taskLink;
	/// hurdle link
	private scrummer.ui.Link _hurdleLink;
	/// sprint backlog model
	private SprintBacklogModel _sprintBacklogModel;
	/// sprint descriptions model
	private SprintDescriptionListModel _sprintDescriptionModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());	
	/// serialization id 
	private static final long serialVersionUID = 5602237842513235459L;
	
}