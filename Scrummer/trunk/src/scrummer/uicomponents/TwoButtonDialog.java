package scrummer.uicomponents;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.ui.Util;

/**
 * Standardni dialog ima nekje na dnu gumbe za Vredu/Prekliči
 * @author simon
 */
public class TwoButtonDialog extends JDialog implements ActionListener {

	/// zapisovalni id
	private static final long serialVersionUID = 4671414325425563526L;
	
	public TwoButtonDialog() {
		init();
	}

	public TwoButtonDialog(Frame owner) {
		super(owner);
		init();
	}

	public TwoButtonDialog(Dialog owner) {
		super(owner);
		init();
	}

	public TwoButtonDialog(Window owner) {
		super(owner);
		init();
	}

	public TwoButtonDialog(Frame owner, boolean modal) {
		super(owner, modal);
		init();
	}

	public TwoButtonDialog(Frame owner, String title) {
		super(owner, title);
		init();
	}

	public TwoButtonDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		init();
	}

	public TwoButtonDialog(Dialog owner, String title) {
		super(owner, title);
		init();
	}

	public TwoButtonDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		init();
	}

	public TwoButtonDialog(Window owner, String title) {
		super(owner, title);
		init();
	}

	public TwoButtonDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		init();
	}

	public TwoButtonDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		init();
	}

	public TwoButtonDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		init();
	}

	public TwoButtonDialog(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		init();
	}

	public TwoButtonDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		init();
	}

	public TwoButtonDialog(Window owner, String title,
			ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		init();
	}

	/**
	 * Koda skupna vsem konstruktorjem
	 */
	private void init()
	{
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		Panel = new JPanel();
		
		GridBagConstraints gcTop = Util.constraint(GridBagConstraints.BOTH, 1.0, 0.85);
		add(Panel, gcTop);
		
		BottomPanel = new JPanel();
		BottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		GridBagConstraints gcBottom = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0f, .15);
		gcBottom.gridy = 1;
		
		OK = new StandardButton(i18n.tr("OK"));
		OK.setName("OK");
		OK.setActionCommand("StandardDialog.OK");
		OK.addActionListener(this);
		BottomPanel.add(OK);
		
		Cancel = new StandardButton(i18n.tr("Cancel"));
		Cancel.setName("Cancel");
		Cancel.addActionListener(this);
		Cancel.setActionCommand("StandardDialog.Cancel");
		BottomPanel.add(Cancel);
		
		add(BottomPanel, gcBottom);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("StandardDialog.Cancel"))
		{
			setVisible(false);
		}
	}
	
	/// zgornji del dialoga(na to dodajaš kontrole) 
	public JPanel Panel;
	public JPanel BottomPanel;
	/// gumb za sprejem obrazca
	public JButton OK;
	public JButton Cancel;
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass()); 
}