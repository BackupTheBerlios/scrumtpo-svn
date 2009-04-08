package scrummer.ui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import scrummer.uicomponents.SelectedTextField;

/**
 * Helper class for building forms
 */
public class FormBuilder {

	/**
	 * Constructor
	 * 
	 * @param panel panel on which form will be built
	 */
	public FormBuilder(JPanel panel)
	{
		_panel = panel;
	}
	
	/**
	 * Add form entry(label + textbox)
	 * 
	 * @param labelText label text
	 * @param textActionCmd text action command
	 * 
	 * @return added text field
	 */
	public JTextField addSelectedTextInput(String labelText, String textActionCmd)
	{
		_addedElements += 2;
		adjustPanelLayout(_panel, _addedElements, _horizontal, _vertical);
		
		JLabel label = new JLabel(labelText);
		JTextField textBox = new SelectedTextField();
		
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
	public JComboBox addComboBoxInput(String labelText)
	{
		_addedElements += 2;
		adjustPanelLayout(_panel, _addedElements, _horizontal, _vertical);
		
		JLabel label = new JLabel(labelText);
		JComboBox textBox = new JComboBox();
		
		_panel.add(label);
		_panel.add(textBox);
		
		return textBox;
	}
	
	/**
	 * Set intercell spacing
	 * @param horizontal
	 * @param vertical
	 */
	public void setCellSpacing(int horizontal, int vertical)
	{
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
	private void adjustPanelLayout(JPanel panel, int elementCount, int horizontalSpacing, int verticalSpacing)
	{
		panel.setLayout(
			new GridLayout(elementCount / 2, 2, horizontalSpacing, verticalSpacing));
	}
	
	/// horizontal intercell spacing
	private int _horizontal = 0;
	/// vertical intercell spacing
	private int _vertical = 0;
	/// added elements counts how many elements have been added
	private int _addedElements = 0;
	/// panel on which form will be built
	private JPanel _panel;
}
