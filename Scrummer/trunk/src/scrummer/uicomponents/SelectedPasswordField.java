package scrummer.uicomponents;

import java.awt.event.FocusEvent;

import javax.swing.JPasswordField;
import javax.swing.text.Document;

/**
 * Password field that gets selected when it is focused
 */
public class SelectedPasswordField extends JPasswordField 
{
	public SelectedPasswordField() {
		// TODO Auto-generated constructor stub
	}

	public SelectedPasswordField(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public SelectedPasswordField(int columns) {
		super(columns);
		// TODO Auto-generated constructor stub
	}

	public SelectedPasswordField(String text, int columns) {
		super(text, columns);
		// TODO Auto-generated constructor stub
	}

	public SelectedPasswordField(Document doc, String txt, int columns) {
		super(doc, txt, columns);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processFocusEvent(FocusEvent e) {
		super.processFocusEvent(e);
		if ( e.getID() == FocusEvent.FOCUS_GAINED )
		{
			selectAll() ;
		}
		else
		{
			select(0, 0);
		}
	}
	
	private static final long serialVersionUID = 1067352971777523482L;
}
