package scrummer.uicomponents;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * Text field that selects contents on gotten focus
 */
public class SelectedTextField extends JTextField 
{
	public SelectedTextField() {
		init();
	}

	public SelectedTextField(String text) {
		super(text);
		init();
	}

	public SelectedTextField(int columns) {
		super(columns);
		init();
	}

	public SelectedTextField(String text, int columns) {
		super(text, columns);
	}

	public SelectedTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		init();
	}
	
	@Override
	protected void processFocusEvent(FocusEvent e) {
		super.processFocusEvent(e);
		if ( e.getID() == FocusEvent.FOCUS_GAINED ) {
			selectAll() ;
		} else {
			select(0, 0);
		}
	}
	
	private void init() {
		setMinimumSize(new Dimension(1, 20));
		setMaximumSize(new Dimension(1, 22));
	}
	
	/// translation class
	private static final long serialVersionUID = -113124952966116940L;
}
