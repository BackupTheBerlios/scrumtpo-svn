package scrummer.ui.dialog;

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
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardButton;

/**
 * Open project dialog
 */
public class OpenProjectDialog extends JDialog implements MouseListener {

	/**
	 * Constructor
	 * 
	 * @param owner owning frame
	 */
	public OpenProjectDialog(Frame owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Open Project"));
		setSize(new Dimension(320,240));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new GridBagLayout());
		parentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		setLayout(new GridLayout(1,1));
		
		String [] st = new String[] { "A", "B", "C", "Č", "D", "E", "F", "G", "H", "I", "J" };
				
		JList list = new JList(st);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setFixedCellWidth(100);
		list.setFixedCellHeight(20);
		list.addMouseListener(this);
		// _projectList = list;
		
		GridBagConstraints listGc = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 1.0);
		parentPanel.add(list, listGc);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 0, 0));
		bottomPanel.setAlignmentX(RIGHT_ALIGNMENT);
		
		JButton cancelButton = new StandardButton(i18n.tr("Cancel"));
		bottomPanel.add(cancelButton);
		
		GridBagConstraints bottomGc = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 0.3);
		bottomGc.gridy = 1;
		parentPanel.add(bottomPanel, bottomGc);
		add(parentPanel);
		
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
