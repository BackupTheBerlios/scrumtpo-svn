package scrummer.ui.dialog;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;

import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;
import scrummer.listener.OperationListener;
import scrummer.listener.ProjectListener;
import scrummer.model.Models;
import scrummer.model.ProjectModel;
import scrummer.model.swing.ProjectListModel;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Open project dialog
 */
public class ProjectOpenDialog extends TwoButtonDialog 
	implements MouseListener, 
			   ProjectListener {

	/**
	 * Constructor
	 * 
	 * @param owner owning frame
	 */
	public ProjectOpenDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Open Project"));
		Models m = Scrummer.getModels();
		
		Panel.setLayout(new GridLayout(1, 1));
		int outerk = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				outerk, outerk, outerk, outerk,
				i18n.tr("Select Project"),
				0, outerk, outerk, outerk));
		
		String [] st = new String[] { "A", "B", "C", "Č", "D", "E", "F", "G", "H", "I", "J" };
				
		JList list = new JList(st);
		_projectList = list;
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setBorder(
			BorderFactory.createCompoundBorder(				
				BorderFactory.createLineBorder(Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
			)
		);
		
		list.setFixedCellWidth(100);
		list.setFixedCellHeight(20);
		list.setVisibleRowCount(5);
		list.addMouseListener(this);
		
		Panel.add(list);
		
		int bottomk = 5;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(
				bottomk - 6, bottomk, bottomk + 5, bottomk + 2));
	
		_projectModel = m.getProjectModel();
		_projectModel.addProjectListener(this);
		
		ProjectListModel plistm = _projectModel.getProjectListModel();
		_projectListModel = plistm;
		list.setModel(plistm);
		plistm.refresh();
		
		if (list.isSelectionEmpty() && (_projectListModel.size() > 0))
		{
			list.setSelectedIndex(0);
			list.invalidate();
		}

		setSize(new Dimension(320,235));
		Util.centre(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			int selectedIndex =_projectList.getSelectedIndex(); 
			if (selectedIndex != -1)
			{
				int id = _projectListModel.getId(selectedIndex);
				_projectModel.openProject(id);
			}
			else
			{
				JOptionPane.showMessageDialog(
					this, 
					i18n.tr("Could not open project. First select a project from project list, " +
							"then click on Open."), 
					i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			super.actionPerformed(e);
		}		
	}
	
	@Override
	public void setVisible(boolean b) {
	
		if (!b)
		{
			_projectModel.removeProjectListener(this);
		}
		
		super.setVisible(b);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}


	@Override
	public void mousePressed(MouseEvent e) {}


	@Override
	public void mouseReleased(MouseEvent e) {
		/*
		if (!_projectList.isSelectionEmpty())
		{	
		System.out.println(_projectList.getSelectedValue());
		}
		*/
	}
	
	@Override
	public void operationSucceeded(DataOperation type, ProjectOperation identifier, String message) {
		if (type == DataOperation.Custom)
		{
			switch (identifier)
			{
			case Open:
				setVisible(false);
				break;
			}
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, ProjectOperation identifier, String message) {
		if (type == DataOperation.Custom)
		{
			switch (identifier)
			{
			case Open:
				JOptionPane.showMessageDialog(this, 
					i18n.tr("Could not open project"), 
					i18n.tr("Error"), JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
	
	
	
	/// project list
	private JList _projectList;
	/// project model
	private ProjectModel _projectModel;
	/// project list model
	private ProjectListModel _projectListModel;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id 
	private static final long serialVersionUID = 456365932759827558L;

}
