package scrummer.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField.AbstractFormatter;

import scrummer.uicomponents.SelectedFormattedTextField;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;

/**
 * Helper class for building forms
 */
public class FormBuilder {

	/**
	 * Layout type of this builder
	 */
	public static enum Layout {
		/// two column layout(left side - labels, right side - inputs)
		Form,
		/// left to right layout(label, input, label, input, ...)
		LeftToRight
	}
	
	/**
	 * Constructor
	 * 
	 * @param panel panel on which form will be built
	 */
	public FormBuilder(JPanel panel) {
		_panel = panel;
	}
	
	/**
	 * Constructor
	 * 
	 * @param panel panel
	 * @param layout layout type
	 */
	public FormBuilder(JPanel panel, Layout layout) {
		_panel = panel;
		_layout = layout;
	}
	
	/**
	 * Add form entry(label + textbox)
	 * 
	 * @param labelText label text
	 * @param textActionCmd text action command
	 * 
	 * @return added text field
	 */
	public SelectedTextField addSelectedTextInput(String labelText, String textActionCmd) {
		_addedElements += 2;
		adjustPanelLayout(_panel, _addedElements, _horizontal, _vertical);
		
		JLabel label = new JLabel(labelText);
		SelectedTextField textBox = new SelectedTextField();
		
		_panel.add(label);
		_panel.add(textBox);
		
		return textBox;
	}
	
	/**
	 * Add form entry(label + textbox) and set default text
	 * 
	 * @param labelText label text
	 * @param textActionCmd text action command
	 * @param defaultText default text
	 * 
	 * @return added text field
	 */
	public SelectedTextField addSelectedTextInput(String labelText, String textActionCmd, String defaultText) {
		SelectedTextField ret = addSelectedTextInput(labelText, textActionCmd);
		ret.setText(defaultText);
		return ret;
	}
	
	/**
	 * Add form entry(label + textbox)
	 * 
	 * @param labelText label text
	 * @param textActionCmd text action command
	 * @param formatter class used to format input field
	 * 
	 * @return added text field
	 */
	public SelectedFormattedTextField addSelectedFormattedTextInput(String labelText, String textActionCmd, AbstractFormatter formatter) {
		_addedElements += 2;
		adjustPanelLayout(_panel, _addedElements, _horizontal, _vertical);
		
		JLabel label = new JLabel(labelText);
		SelectedFormattedTextField textBox = new SelectedFormattedTextField(formatter);
		
		_panel.add(label);
		_panel.add(textBox);
		
		return textBox;
	}
	
	/**
	 * Add combo box to form 
	 * 
	 * @param labelText label text
	 * @return created combo box
	 */
	public StandardComboBox addComboBoxInput(String labelText) {
		_addedElements += 2;
		adjustPanelLayout(_panel, _addedElements, _horizontal, _vertical);
		
		JLabel label = new JLabel(labelText);
		StandardComboBox textBox = new StandardComboBox();
		
		_panel.add(label);
		_panel.add(textBox);
		
		return textBox;
	}
	
	/**
	 * Set intercell spacing
	 * @param horizontal
	 * @param vertical
	 */
	public void setCellSpacing(int horizontal, int vertical) {
		_horizontal = horizontal;
		_vertical = vertical;
		adjustPanelLayout(_panel, _addedElements, _horizontal, _vertical);
	}
	
	/**
	 * Set grid layout apropriate for current grid
	 * 
	 * @param panel panel which should be adjusted
	 * @param elementCount element count(will use gridlayout 2 columns elementCount / 2 rows)
	 * @param horizontalSpacing horizontal intercell spacing
	 * @param verticalSpacing vertical intercell spacing
	 */
	private void adjustPanelLayout(JPanel panel, int elementCount, int horizontalSpacing, int verticalSpacing) {
		if (_layout == Layout.Form) {
			panel.setLayout(
				new GridLayout(elementCount / 2, 2, horizontalSpacing, verticalSpacing));	
		} else {
			panel.setLayout(
				new FlowLayout(FlowLayout.LEFT, horizontalSpacing, verticalSpacing));
		}
		
	}
	
	/// layout type
	private Layout _layout = Layout.Form;
	/// horizontal intercell spacing
	private int _horizontal = 0;
	/// vertical intercell spacing
	private int _vertical = 0;
	/// added elements counts how many elements have been added
	private int _addedElements = 0;
	/// panel on which form will be built
	private JPanel _panel;
}
