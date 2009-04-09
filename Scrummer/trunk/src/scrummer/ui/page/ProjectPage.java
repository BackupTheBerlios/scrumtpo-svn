package scrummer.ui.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.ProjectListener;
import scrummer.model.ProjectModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.MainFrame;
import scrummer.ui.Util;
import scrummer.ui.dialog.ProjectNewDialog;

/**
 * This page contains project name and description 
 */
public class ProjectPage extends JPanel
						 implements FocusListener, ProjectListener {
	/**
	 * Constructor
	 * 
	 * @param mainFrame main frame
	 */
	public ProjectPage(MainFrame mainFrame) {
		
		_mainFrame = mainFrame;
		_projectModel = Scrummer.getModels().getProjectModel();
		_projectModel.addProjectListener(this);
		
		Box box = new Box(BoxLayout.X_AXIS);
		Box subBox = new Box(BoxLayout.Y_AXIS);
		
		subBox.setBackground(Color.WHITE);
		subBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		subBox.setPreferredSize(new Dimension(335, 370));
		
		JPanel topSubBox = new JPanel();
		topSubBox.setPreferredSize(new Dimension(300,15));
		topSubBox.setBackground(Color.WHITE);
		
		FormBuilder fb = new FormBuilder(topSubBox);
		_projectInput = fb.addSelectedTextInput(i18n.tr("Project Name" + ":"), "ProjectName");
		_projectInput.setText(_projectModel.getCurrentProjectName());
		fb.setCellSpacing(0, 0);
		
		JPanel midSubBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		midSubBox.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		midSubBox.setBackground(Color.WHITE);
		
		midSubBox.setMaximumSize(new Dimension(335, 15));
		JLabel descriptionLbl = new JLabel(i18n.tr("Description") + ":");
		descriptionLbl.setAlignmentY(BOTTOM_ALIGNMENT);
		midSubBox.add(descriptionLbl);
		
		JPanel bottomSubBox = new JPanel();
		bottomSubBox.setBackground(Color.WHITE);
		_descriptionInput = new JTextArea(14, 28);
		bottomSubBox.add(_descriptionInput);
		bottomSubBox.setPreferredSize(new Dimension(300, 300));
		_descriptionInput.setDocument(new ProjectNewDialog.DescriptionDocument(1024));
		_descriptionInput.setLineWrap(true);
		_descriptionInput.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		_descriptionInput.setText(_projectModel.getCurrentProjectDescription());
		
		// make text area tab traversable
		Util.tabTraverse(_descriptionInput);
		
		subBox.add(topSubBox);
		subBox.add(midSubBox);
		subBox.add(bottomSubBox);
		box.add(subBox);
		
		add(box);
		
		_projectInput.addFocusListener(this);
		_descriptionInput.addFocusListener(this);
		
		setBackground(Color.WHITE);
	}
	
	@Override
	public void focusGained(FocusEvent e) {}
	
	@Override
	public void focusLost(FocusEvent e) {
		Object source = e.getSource();
		if (source == _projectInput)
		{
			_projectModel.setProjectName(_projectInput.getText());
		}
		else if (source == _descriptionInput)
		{
			_projectModel.setProjectDescription(_descriptionInput.getText());
		}
	}

	@Override
	public void operationSucceeded(DataOperation type, ProjectOperation identifier, String message) {}
	
	@Override
	public void operationFailed(DataOperation type, ProjectOperation identifier, String message) {
		switch (type)
		{
		case Update:
			switch (identifier)
			{
			case Project:
				_projectInput.setText(_projectModel.getCurrentProjectName());
				_descriptionInput.setText(null);
				_descriptionInput.setText(_projectModel.getCurrentProjectDescription());
				Util.showError(_mainFrame, 
					i18n.tr("Error while saving project data") + ": " + message, 
					i18n.tr("Error"));
				break; 
			}
		}
	}
	
	/// project input text field
	private JTextField _projectInput;
	/// description input
	private JTextArea _descriptionInput;
	/// project model
	private ProjectModel _projectModel;
	/// main frame - for showing error messages
	private MainFrame _mainFrame;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = -424166083500909992L;
	
}
