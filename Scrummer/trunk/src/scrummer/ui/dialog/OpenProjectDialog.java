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
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Open project dialog
 */
public class OpenProjectDialog extends TwoButtonDialog implements MouseListener {

	/**
	 * Constructor
	 * 
	 * @param owner owning frame
	 */
	public OpenProjectDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Open Project"));
		
		Panel.setLayout(new GridLayout(1,1));
		int outerk = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				outerk, outerk, outerk, outerk,
				i18n.tr("Select Project"),
				0, outerk, outerk, outerk));
		
		String [] st = new String[] { "A", "B", "C", "Č", "D", "E", "F", "G", "H", "I", "J" };
				
		JList list = new JList(st);
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
		
		setSize(new Dimension(320,235));
		Util.centre(this);
	}
	
	public void actionPerformed(ActionEvent e) {}

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
	
	/// serialization id 
	private static final long serialVersionUID = 456365932759827558L;
	/// project list
	// private JList _projectList;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass()); 
}
