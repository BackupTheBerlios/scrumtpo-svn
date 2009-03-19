package scrummer.uicomponents;

import java.awt.event.FocusEvent;
import java.text.Format;
import javax.swing.JFormattedTextField;

/**
 * Tekstovno polje, ki se označi kadar dobi fokus.
 */
public class SelectedFormattedTextField extends JFormattedTextField {

	/// zapisovalni id
	private static final long serialVersionUID = 1638899045718117436L;

	public SelectedFormattedTextField() {}

	public SelectedFormattedTextField(Object value) {
		super(value);
	}

	public SelectedFormattedTextField(Format format) {
		super(format);
	}

	public SelectedFormattedTextField(AbstractFormatter formatter) {
		super(formatter);
	}

	public SelectedFormattedTextField(AbstractFormatterFactory factory) {
		super(factory);
	}

	public SelectedFormattedTextField(AbstractFormatterFactory factory,
			Object currentValue) {
		super(factory, currentValue);
	}
	
	@Override
	protected void processFocusEvent(FocusEvent e) {
		// TODO Auto-generated method stub
		super.processFocusEvent(e);
		if ( e.getID() == FocusEvent.FOCUS_GAINED )
		{
			selectAll() ;
		}
	}
}