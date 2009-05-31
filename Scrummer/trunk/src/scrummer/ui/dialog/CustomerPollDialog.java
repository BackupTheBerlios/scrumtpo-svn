package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;

import com.sun.org.apache.xerces.internal.impl.RevalidationHandler;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.enumerator.SprintBacklogOperation;
import scrummer.enumerator.TaskOperation;
import scrummer.listener.DeveloperListener;
import scrummer.listener.MetricListener;
import scrummer.listener.SprintBacklogListener;
import scrummer.listener.TaskListener;
import scrummer.model.DeveloperModel;
import scrummer.model.MetricModel;
import scrummer.model.SprintBacklogModel;
import scrummer.model.swing.ImpedimentComboBoxModel;
import scrummer.ui.Util;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

public class CustomerPollDialog 
	extends TwoButtonDialog 
	implements MetricListener, DeveloperListener, TaskListener, SprintBacklogListener 
{

	public CustomerPollDialog(Frame parent)
	{
		super(parent, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Customer poll"));
		
		_metricModel = Scrummer.getModels().getMetricModel();
		_metricModel.addMetricListener(this);
		
		_sbModel = Scrummer.getModels().getSprintBacklogModel();
		_sbModel.addSprintBacklogListener(this);
		
		Panel.resize(new Dimension(700,250));
		Panel.setLayout(new GridLayout(1,1));

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridBagLayout());		

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 1, 1);
		gbc.weightx = 0.8;
		String newline = "<br>"; // System.getProperty("line.separator");
		
		JLabel userLbl = new JLabel(i18n.tr("Name") + ":");
		JTextField userInput = new JTextField();
		userInput.setColumns(10);
		_userInput = userInput;
		
		JLabel sprintLbl = new JLabel(i18n.tr("Choose sprint") + ":");
		StandardComboBox sprintInput = new StandardComboBox();
		sprintInput.setIVModel(_sbModel.getSprintProjectComboBoxModel());
		_sprintInput = sprintInput;
		
		// set_gbc(gbc,0,0,1,1,GridBagConstraints.HORIZONTAL);
		topPanel.add(userLbl); // , gbc);
		// set_gbc(gbc,0,1,1,1,GridBagConstraints.HORIZONTAL);
		topPanel.add(userInput); // , gbc);
		
		//set_gbc(gbc,0,3,1,1,GridBagConstraints.HORIZONTAL);
		topPanel.add(sprintLbl); // , gbc);
		// set_gbc(gbc,0,4,1,1,GridBagConstraints.HORIZONTAL);
		topPanel.add(sprintInput); //, gbc);
		
		set_gbc(gbc, 0, 0, GridBagConstraints.REMAINDER, 1, GridBagConstraints.HORIZONTAL);
		innerPanel.add(topPanel, gbc);
		
		bg = new ButtonGroup[10];
		
		JLabel q1 = new JLabel(i18n.tr("Sodelovanje z razvojno skupino v času razvoja") + ":");
		
		b11 = new JRadioButton("1", true);
		b12 = new JRadioButton("2", false);
		b13 = new JRadioButton("3", false);
		b14 = new JRadioButton("4", false);
		b15 = new JRadioButton("5", false);
		
		bg[0] = new ButtonGroup();
		bg[0].add(b11);
		bg[0].add(b12);
		bg[0].add(b13);
		bg[0].add(b14);
		bg[0].add(b15);
		
		set_gbc(gbc,1,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q1, gbc);
		set_gbc(gbc,2,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b11, gbc);
		set_gbc(gbc,2,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b12, gbc);
		set_gbc(gbc,2,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b13, gbc);
		set_gbc(gbc,2,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b14, gbc);
		set_gbc(gbc,2,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b15, gbc);
		
		JLabel q2 = new JLabel(i18n.tr("Odzivnost razvijalcev na želje, spremembe v zahtevah") + ":");
		
		b21 = new JRadioButton("1", true);
		b22 = new JRadioButton("2", false);
		b23 = new JRadioButton("3", false);
		b24 = new JRadioButton("4", false);
		b25 = new JRadioButton("5", false);
		
		bg[1] = new ButtonGroup();
		bg[1].add(b21);
		bg[1].add(b22);
		bg[1].add(b23);
		bg[1].add(b24);
		bg[1].add(b25);
		
		set_gbc(gbc,3,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q2, gbc);
		set_gbc(gbc,4,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b21, gbc);
		set_gbc(gbc,4,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b22, gbc);
		set_gbc(gbc,4,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b23, gbc);
		set_gbc(gbc,4,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b24, gbc);
		set_gbc(gbc,4,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b25, gbc);
		
		JLabel q3 = new JLabel(i18n.tr("<html> Popolnost izdelane rešitve " + newline + "(Ali so bile realizirane vse zahteve, " +
									   "dogovorjene na Sprint Planning Meetingu?)</html>") + ":");

		b31 = new JRadioButton("1", true);
		b32 = new JRadioButton("2", false);
		b33 = new JRadioButton("3", false);
		b34 = new JRadioButton("4", false);
		b35 = new JRadioButton("5", false);
		
		bg[2] = new ButtonGroup();
		bg[2].add(b31);
		bg[2].add(b32);
		bg[2].add(b33);
		bg[2].add(b34);
		bg[2].add(b35);
		
		set_gbc(gbc,5,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q3, gbc);
		set_gbc(gbc,6,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b31, gbc);
		set_gbc(gbc,6,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b32, gbc);
		set_gbc(gbc,6,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b33, gbc);
		set_gbc(gbc,6,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b34, gbc);
		set_gbc(gbc,6,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b35, gbc);
		
		JLabel q4 = new JLabel(i18n.tr("<html> Skladnost izdelane rešitve s prićakovanji " + newline + "(Ali se izdelana programska " +
									   "oprema ujema z vašimi pričakovanji na začetku cikla?)</html>") + ":");

		b41 = new JRadioButton("1", true);
		b42 = new JRadioButton("2", false);
		b43 = new JRadioButton("3", false);
		b44 = new JRadioButton("4", false);
		b45 = new JRadioButton("5", false);
		
		bg[3] = new ButtonGroup();
		bg[3].add(b41);
		bg[3].add(b42);
		bg[3].add(b43);
		bg[3].add(b44);
		bg[3].add(b45);
		
		set_gbc(gbc,7,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q4, gbc);
		set_gbc(gbc,8,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b41, gbc);
		set_gbc(gbc,8,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b42, gbc);
		set_gbc(gbc,8,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b43, gbc);
		set_gbc(gbc,8,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b44, gbc);
		set_gbc(gbc,8,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b45, gbc);
		
		JLabel q5 = new JLabel(i18n.tr("<html> Intuitivnost uporabniškega vmesnika " + newline + "(Ali je sistem enostaven " +
									   "za uporabo brez obsežnega predhodnega izobraževanja?)</html>)") + ":");

		b51 = new JRadioButton("1", true);
		b52 = new JRadioButton("2", false);
		b53 = new JRadioButton("3", false);
		b54 = new JRadioButton("4", false);
		b55 = new JRadioButton("5", false);
		
		bg[4] = new ButtonGroup();
		bg[4].add(b51);
		bg[4].add(b52);
		bg[4].add(b53);
		bg[4].add(b54);
		bg[4].add(b55);
		
		set_gbc(gbc,9,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q5, gbc);
		set_gbc(gbc,10,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b51, gbc);
		set_gbc(gbc,10,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b52, gbc);
		set_gbc(gbc,10,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b53, gbc);
		set_gbc(gbc,10,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b54, gbc);
		set_gbc(gbc,10,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b55, gbc);
		
		JLabel q6 = new JLabel(i18n.tr("<html> Uporabnost/Koristnost izdelanih programov " + newline + "(Ali izdelani programi olajšajo delo uporabniku?)") + ":");

		b61 = new JRadioButton("1", true);
		b62 = new JRadioButton("2", false);
		b63 = new JRadioButton("3", false);
		b64 = new JRadioButton("4", false);
		b65 = new JRadioButton("5", false);
		
		bg[5] = new ButtonGroup();
		bg[5].add(b61);
		bg[5].add(b62);
		bg[5].add(b63);
		bg[5].add(b64);
		bg[5].add(b65);
		
		set_gbc(gbc,11,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q6, gbc);
		set_gbc(gbc,12,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b61, gbc);
		set_gbc(gbc,12,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b62, gbc);
		set_gbc(gbc,12,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b63, gbc);
		set_gbc(gbc,12,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b64, gbc);
		set_gbc(gbc,12,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b65, gbc);
		
		JLabel q7 = new JLabel(i18n.tr("<html> Odsotnost napak, zanesljivost delovanja, robustnost " + newline +
									   "(Ali so izdelani programi brez napak? " + newline + "Ali izdelana rešitev deluje stabilno, brez prekinitev?))</html>") + ":");

		b71 = new JRadioButton("1", true);
		b72 = new JRadioButton("2", false);
		b73 = new JRadioButton("3", false);
		b74 = new JRadioButton("4", false);
		b75 = new JRadioButton("5", false);
		
		bg[6] = new ButtonGroup();
		bg[6].add(b71);
		bg[6].add(b72);
		bg[6].add(b73);
		bg[6].add(b74);
		bg[6].add(b75);
		
		set_gbc(gbc,13,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q7, gbc);
		set_gbc(gbc,14,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b71, gbc);
		set_gbc(gbc,14,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b72, gbc);
		set_gbc(gbc,14,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b73, gbc);
		set_gbc(gbc,14,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b74, gbc);
		set_gbc(gbc,14,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b75, gbc);
		
		JLabel q8 = new JLabel(i18n.tr("Kakovost oddanih Sprint Backlog-ov, pravočasnost oddaje") + ":");

		b81 = new JRadioButton("1", true);
		b82 = new JRadioButton("2", false);
		b83 = new JRadioButton("3", false);
		b84 = new JRadioButton("4", false);
		b85 = new JRadioButton("5", false);
		
		bg[7] = new ButtonGroup();
		bg[7].add(b81);
		bg[7].add(b82);
		bg[7].add(b83);
		bg[7].add(b84);
		bg[7].add(b85);
		
		set_gbc(gbc,15,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q8, gbc);
		set_gbc(gbc,16,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b81, gbc);
		set_gbc(gbc,16,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b82, gbc);
		set_gbc(gbc,16,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b83, gbc);
		set_gbc(gbc,16,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b84, gbc);
		set_gbc(gbc,16,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b85, gbc);
		
		JLabel q9 = new JLabel(i18n.tr("Splošna ocena zadovoljstva z delom razvojne skupine") + ":");

		b91 = new JRadioButton("1", true);
		b92 = new JRadioButton("2", false);
		b93 = new JRadioButton("3", false);
		b94 = new JRadioButton("4", false);
		b95 = new JRadioButton("5", false);
		
		bg[8] = new ButtonGroup();
		bg[8].add(b91);
		bg[8].add(b92);
		bg[8].add(b93);
		bg[8].add(b94);
		bg[8].add(b95);
		
		set_gbc(gbc,17,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q9, gbc);
		set_gbc(gbc,18,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b91, gbc);
		set_gbc(gbc,18,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b92, gbc);
		set_gbc(gbc,18,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b93, gbc);
		set_gbc(gbc,18,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b94, gbc);
		set_gbc(gbc,18,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b95, gbc);
		
		JLabel q10 = new JLabel(i18n.tr("Splošna ocena kakovosti izdelanih programov") + ":");

		b101 = new JRadioButton("1", true);
		b102 = new JRadioButton("2", false);
		b103 = new JRadioButton("3", false);
		b104 = new JRadioButton("4", false);
		b105 = new JRadioButton("5", false);
		
		bg[9] = new ButtonGroup();
		bg[9].add(b101);
		bg[9].add(b102);
		bg[9].add(b103);
		bg[9].add(b104);
		bg[9].add(b105);
		
		JTextField comment1 = new JTextField();
		t1 = comment1;
		set_gbc(gbc,19,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(q10, gbc);
		set_gbc(gbc,20,0,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b101, gbc);
		set_gbc(gbc,20,1,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b102, gbc);
		set_gbc(gbc,20,2,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b103, gbc);
		set_gbc(gbc,20,3,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b104, gbc);
		set_gbc(gbc,20,4,1,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(b105, gbc);
		set_gbc(gbc,21,0,10,1,GridBagConstraints.HORIZONTAL);
		innerPanel.add(t1, gbc);
		

		JScrollPane innerScroll = new JScrollPane(innerPanel);
		Panel.add(innerScroll);
		
		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Customer poll"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(800, 600));
		Util.centre(this);
		innerScroll.revalidate();
		Panel.revalidate();
	}
	
	private void set_gbc(GridBagConstraints gbc, int row, int column, int width, int height, int fill) 
	{
		   gbc.gridy = row;
		   gbc.gridx = column;
		   gbc.gridwidth = width;
		   gbc.gridheight = height;
		   gbc.fill = fill;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{ 
		if (e.getActionCommand() == "StandardDialog.OK")
		{
			java.util.Date datum = new java.util.Date();			 
			
			BigDecimal[] s = new BigDecimal[14];
			
			for(int i=0;i<10;i++)
			{
				Enumeration<AbstractButton> allRadioButton=bg[i].getElements();  
				while(allRadioButton.hasMoreElements())  
				{ 	 
					JRadioButton temp=(JRadioButton)allRadioButton.nextElement();  
					if(temp.isSelected())  
					{  
						s[i] = BigDecimal.valueOf(Integer.parseInt(temp.getText()));  
					}  
				}
			}
			
			//komentarjev zaenkrat ne shranjuje ker mora biti measurement_result BigDecimal?? 
			int c = 0;
			for(int i=1;i<11;i=i+2)
			{
				_metricModel.addCustomerPoolMeasurement(i, _userInput.getText(), datum, s[c], _sprintInput.getSelectedId());
				c++;
			}
		}
		else
		{
			super.actionPerformed(e);
		}
	}
	
	@Override
	public void operationFailed(DataOperation type, TaskOperation identifier, String message) {}

	@Override
	public void operationSucceeded(DataOperation type, TaskOperation identifier, String message) {}
		
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier,
			String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void operationSucceeded(DataOperation type,
			MetricOperation identifier, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void operationFailed(DataOperation type,
			DeveloperOperation identifier, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void operationSucceeded(DataOperation type,
			DeveloperOperation identifier, String message) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setVisible(boolean b) {
		if (!b)
		{
			_metricModel.removeMetricListener(this);
		}
		super.setVisible(b);
	}
	
	@Override
	public void operationFailed(DataOperation type,
			SprintBacklogOperation identifier, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void operationSucceeded(DataOperation type,
			SprintBacklogOperation identifier, String message) {
		// TODO Auto-generated method stub
		
	}
	
	/// metric model
	protected MetricModel _metricModel;
	/// all impediments in combo box
	protected ImpedimentComboBoxModel _impedimentComboModel;
	/// developer model
	protected DeveloperModel _developerModel;
	// sprint backlog model
	protected SprintBacklogModel _sbModel;
	/// combo box models
	protected StandardComboBox _sprintInput;
	// radio buttons
	JRadioButton b11, b12, b13, b14, b15, b21, b22, b23, b24, b25, b31, b32, b33, b34, b35, b41, b42, b43, b44, b45, b51, b52, b53, b54, b55;
	JRadioButton b61, b62, b63, b64, b65, b71, b72, b73, b74, b75, b81, b82, b83, b84, b85, b91, b92, b93, b94, b95, b101, b102, b103, b104, b105;
	JRadioButton b111, b112, b113, b114, b115, b121, b122, b123, b124, b125, b131, b132, b133, b134, b135, b141, b142, b143, b144, b145;
	// button groups
	ButtonGroup[] bg;
	// text fields
	JTextField t1, _userInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2571891107362309751L;
}
