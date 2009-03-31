package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.TwoButtonDialog;


public class ImpedimentsAddDialog extends TwoButtonDialog implements FocusListener {
	
	public ImpedimentsAddDialog(JFrame owner) throws SQLException
	{
		super(owner, ModalityType.APPLICATION_MODAL);
		// set translated title
		setTitle(i18n.tr("Add impediment"));
		
		Panel.setLayout(new GridLayout(7, 5, 0, 10));
		
		addEntry(i18n.tr("Team") + ":", "Team");
		addEntry(i18n.tr("Sprint") + ":", "Sprint");
		addEntry(i18n.tr("Employee")    + ":", "Employee");
		addEntry(i18n.tr("Task") + ":", "Task");
		addEntry(i18n.tr("Description") + ":", "Description");
		addEntry(i18n.tr("Type") + ":", "Type");
		addEntry(i18n.tr("Status") + ":", "Status");
		addEntry(i18n.tr("Start") + ":", "Start");
		addEntry(i18n.tr("End") + ":", "End");
		addEntry(i18n.tr("Age") + ":", "Age");
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Add impediment"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(570, 310));
		Util.centre(this);
	}

	public void addEntry(String labelText, String textActionCmd)
	{
		JLabel label = new JLabel(labelText);
		
		JTextField textBox = new SelectedTextField();
		_textField.add(textBox);
		textBox.addFocusListener(this);
		
		Panel.add(label);
		Panel.add(textBox);
	}
	
	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		if (_textField.contains(e.getComponent()))
		{
			JTextField field = _textField.get(_textField.indexOf(e.getSource())); 
			System.out.println(field.getText());
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("StandardDialog.OK"))
		{
			int project = Integer.parseInt(_textField.get(0).getText());
			int sprint = Integer.parseInt(_textField.get(1).getText());
			String description = _textField.get(2).getText();
			String priority = _textField.get(3).getText();
			String initial_estimate = _textField.get(4).getText();
			String adjustment_factor = _textField.get(5).getText();
			String adjusted_estimate = _textField.get(6).getText();
			
			ConnectionModel cm = Scrummer.getModels().getConnectionModel();
			java.sql.Connection con;
			java.sql.Statement stmt;
			ResultSet rs;
			try {
				con = cm.getConnection();
				stmt = con.createStatement();
			    rs = stmt.executeQuery("INSERT INTO PBI (Project_id,Sprint_id,PBI_description,PBI_priority,PBI_initial_estimate,PBI_adjustment_factor,PBI_adjusted_estimate) VALUES(project,sprint,description,priority,initial_estimate,adjustment_factor,adjusted_estimate)");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
		}
		else if(e.getActionCommand().equals("StandardDialog.Cancel"))
		{
			setVisible(false);
		}
	}
	
	private Vector<JTextField> _textField = new Vector<JTextField>();;
	/// serialization id
	private static final long serialVersionUID = 8159590855907206180L;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());	
}