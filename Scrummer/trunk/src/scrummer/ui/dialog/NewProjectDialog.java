package scrummer.ui.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedFormattedTextField;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * New project dialog.
 */
public class NewProjectDialog extends TwoButtonDialog {
	
	/**
	 * Constructor
	 * @param parent owning control
	 */
	public NewProjectDialog(JFrame parent) {
		super(parent,ModalityType.APPLICATION_MODAL);
		setTitle(i18n.tr("Create New Project"));
		setSize(new Dimension(340,270));
		
		Panel.setLayout(new GridBagLayout());
		Panel.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10));
		BottomPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 6, 6));
		
		JLabel nameLbl = new JLabel(i18n.tr("Project name") + ":");
		GridBagConstraints nameLblC = Util.constraint(GridBagConstraints.WEST, 0.5, 0.7);
		nameLblC.anchor = GridBagConstraints.WEST;
		Panel.add(nameLbl, nameLblC);
		
		SelectedFormattedTextField nameTextInput = new SelectedFormattedTextField();
		nameTextInput.setColumns(14);
		nameTextInput.setDocument(new DescriptionDocument(256));
		
		GridBagConstraints nameInputC = Util.constraint(GridBagConstraints.EAST, 0.3, 0.3); nameInputC.gridx = 1;
		nameInputC.anchor = GridBagConstraints.EAST;
		
		Panel.add(nameTextInput, nameInputC);
		
		JLabel descrLbl = new JLabel(i18n.tr("Project description") + ":");
		GridBagConstraints descrLblC = Util.constraint(GridBagConstraints.WEST, 0.5, 0.3);
		descrLblC.anchor = GridBagConstraints.WEST;
		descrLblC.gridx = 0; descrLblC.gridy = 1;
		Panel.add(descrLbl, descrLblC);
		
		JTextArea descrInput = new JTextArea(7, 28);
		descrInput.setDocument(new DescriptionDocument(1024));
		descrInput.setLineWrap(true);
		descrInput.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		// make text area tab traversable
		Util.tabTraverse(descrInput);
		
		GridBagConstraints descrInputC = Util.constraint(GridBagConstraints.HORIZONTAL, 1.0, 0.4);
		descrInputC.anchor = GridBagConstraints.WEST;
		descrInputC.gridy = 2;
		descrInputC.gridwidth = 2;
		
		// make row count constant
		descrInput.setPreferredSize(new Dimension(100, 50));
		Panel.add(descrInput, descrInputC);
		
		OK.setName("Create");
		Cancel.setName("Cancel");
		
		Util.centre(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK"))
		{
			// TODO: add new project via project model
		}
		else
		{
			super.actionPerformed(e);
		}
	}
	
	/**
	 * Document with text size limit
	 */
	static class DescriptionDocument extends DefaultStyledDocument
	{
		/**
		 * Constructor
		 * @param length max. length of contained text
		 */
		public DescriptionDocument(int length)
		{
			try
			{
				// limit length
				setDocumentFilter(new SizeFilter(length));
			}
			catch (Exception ex)
			{
				Scrummer.getModelFactory().getLoggingModel().warning("Could not set filter on document!", ex);
			}
		}
		
		/**
		 * Filter that knows how to limit document size
		 */
		private static class SizeFilter extends DocumentFilter {

			/**
			 * Constructor
			 * 
			 * @param maxSize max size of edited text
			 */
			public SizeFilter(int maxSize) throws Exception
			{
				if (maxSize <= 0)
				{
					throw new Exception("Wrong size, has to be >= 0: " + maxSize);
				}
				_maxSize = maxSize;
			}
			
			@Override
			public void insertString(FilterBypass fb, int offset,
					String string, AttributeSet attr)
					throws BadLocationException {
			
				int size = fb.getDocument().getLength() + string.length();
				if (size <= _maxSize)
				{
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length,
					String text, AttributeSet attrs)
					throws BadLocationException {
				int size = fb.getDocument().getLength() + text.length() - length;
				if (size <= _maxSize)
				{
					super.insertString(fb, offset, text, attrs);
				}
				
			}
			
			private int _maxSize = 0;
		}
		/// serialization id
		private static final long serialVersionUID = 529023227772650007L;	
	}
	
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id 
	private static final long serialVersionUID = 7218168248710426884L;

}
