package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.xnap.commons.i18n.I18n;
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

public class DeveloperPollDialog 
	extends TwoButtonDialog 
	implements MetricListener, DeveloperListener, TaskListener, SprintBacklogListener 
{

	public DeveloperPollDialog(Frame parent)
	{
		super(parent, ModalityType.APPLICATION_MODAL);
		
		setTitle(i18n.tr("Developer poll"));
		
		_metricModel = Scrummer.getModels().getMetricModel();
		_metricModel.addMetricListener(this);
		
		_developerModel = Scrummer.getModels().getDeveloperModel();
		_developerModel.addDeveloperListener(this);
		
		_sbModel = Scrummer.getModels().getSprintBacklogModel();
		_sbModel.addSprintBacklogListener(this);
		
		Panel.resize(new Dimension(700,250));
		Panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 1, 1);
		gbc.weightx = 0.8;
		String newline = System.getProperty("line.separator");
		
		JLabel empLbl = new JLabel(i18n.tr("Choose employee") + ":");
		StandardComboBox empInput = new StandardComboBox();
		empInput.setIVModel(_developerModel.getEmployeeComboBoxModel());
		_empInput = empInput;
		
		JLabel sprintLbl = new JLabel(i18n.tr("Choose sprint") + ":");
		StandardComboBox sprintInput = new StandardComboBox();
		sprintInput.setIVModel(_sbModel.getSprintProjectComboBoxModel());
		_sprintInput = sprintInput;
		
		set_gbc(gbc,0,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(empLbl, gbc);
		set_gbc(gbc,0,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(empInput, gbc);
		
		set_gbc(gbc,0,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(sprintLbl, gbc);
		set_gbc(gbc,0,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(sprintInput, gbc);
		
		bg = new ButtonGroup[14];
		
		JLabel q1 = new JLabel(i18n.tr("<html> Jasnost zastavljenega Product Backlog-a " + newline +
									   "(Ali je bil Product Backlog za trenutni Sprint " + newline +
									   "jasno zastavljen? Ali vam je bilo iz kratkega opisa " + newline +
									   " za vsako zahtevo jasno, kaj Product Owner zahteva?)</html>") + ":");
		
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
		
		JTextField comment1 = new JTextField();
		t1 = comment1;
		set_gbc(gbc,1,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q1, gbc);
		set_gbc(gbc,2,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b11, gbc);
		set_gbc(gbc,2,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b12, gbc);
		set_gbc(gbc,2,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b13, gbc);
		set_gbc(gbc,2,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b14, gbc);
		set_gbc(gbc,2,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b15, gbc);
		set_gbc(gbc,2,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t1, gbc);
		
		JLabel q2 = new JLabel(i18n.tr("<html> Ocena �asa za posamezno zahtevo v Product Backlogu " + newline +
									   "(Ali so bile ocene potrebnega dela ustrezne?)</html>") + ":");

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
		
		JTextField comment2 = new JTextField();
		t2 = comment2;
		set_gbc(gbc,3,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q2, gbc);
		set_gbc(gbc,4,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b21, gbc);
		set_gbc(gbc,4,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b22, gbc);
		set_gbc(gbc,4,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b23, gbc);
		set_gbc(gbc,4,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b24, gbc);
		set_gbc(gbc,4,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b25, gbc);
		set_gbc(gbc,4,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t2, gbc);
		
		JLabel q3 = new JLabel(i18n.tr("<html> Administracija pri metodologiji Scrum (Ali so bile " + newline +
									   " preglednice, ki ste jih izpolnjevali, jasne in razumljive?)</html>") + ":");

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
		
		
		JTextField comment3 = new JTextField();
		t3 = comment3;
		set_gbc(gbc,5,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q3, gbc);
		set_gbc(gbc,6,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b31, gbc);
		set_gbc(gbc,6,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b32, gbc);
		set_gbc(gbc,6,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b33, gbc);
		set_gbc(gbc,6,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b34, gbc);
		set_gbc(gbc,6,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b35, gbc);
		set_gbc(gbc,6,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t3, gbc);
		
		JLabel q4 = new JLabel(i18n.tr("<html> Obremenjenost z administracijo (5 � vodenje Sprint Backloga " + newline +
									   " ni obremenjujo�e, 1 � vodenje Sprint Backloga je zelo obremenjujo�e)</html>") + ":");

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
		
		JTextField comment4 = new JTextField();
		t4 = comment4;
		set_gbc(gbc,7,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q4, gbc);
		set_gbc(gbc,8,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b41, gbc);
		set_gbc(gbc,8,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b42, gbc);
		set_gbc(gbc,8,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b43, gbc);
		set_gbc(gbc,8,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b44, gbc);
		set_gbc(gbc,8,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b45, gbc);
		set_gbc(gbc,8,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t4, gbc);
		
		JLabel q5 = new JLabel(i18n.tr("<html> Tehni�ne te�ave na za�etku Sprinta (5 � ni bilo te�av, " + newline +
									   "1 � bilo je veliko te�av </html>)") + ":");

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
		
		JTextField comment5 = new JTextField();
		t5 = comment5;
		set_gbc(gbc,9,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q5, gbc);
		set_gbc(gbc,10,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b51, gbc);
		set_gbc(gbc,10,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b52, gbc);
		set_gbc(gbc,10,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b53, gbc);
		set_gbc(gbc,10,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b54, gbc);
		set_gbc(gbc,10,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b55, gbc);
		set_gbc(gbc,10,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t5, gbc);
		
		JLabel q6 = new JLabel(i18n.tr("<html> Vsebinske te�ave (razumevanje zahtevane funkcionalnosti) na za�etku " + newline +
									   " Sprinta (5 � ni bilo te�av, 1 � bilo je veliko te�av) </html>") + ":");

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
		
		JTextField comment6 = new JTextField();
		t6 = comment6;
		set_gbc(gbc,11,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q6, gbc);
		set_gbc(gbc,12,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b61, gbc);
		set_gbc(gbc,12,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b62, gbc);
		set_gbc(gbc,12,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b63, gbc);
		set_gbc(gbc,12,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b64, gbc);
		set_gbc(gbc,12,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b65, gbc);
		set_gbc(gbc,12,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t6, gbc);
		
		JLabel q7 = new JLabel(i18n.tr("<html> Tehni�ne te�ave na koncu Sprinta (5 � ni bilo te�av, 1 � bilo je veliko te�av)</html>") + ":");

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
		
		JTextField comment7 = new JTextField();
		t7 = comment7;
		set_gbc(gbc,13,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q7, gbc);
		set_gbc(gbc,14,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b71, gbc);
		set_gbc(gbc,14,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b72, gbc);
		set_gbc(gbc,14,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b73, gbc);
		set_gbc(gbc,14,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b74, gbc);
		set_gbc(gbc,14,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b75, gbc);
		set_gbc(gbc,14,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t7, gbc);
		
		JLabel q8 = new JLabel(i18n.tr("<html> Vsebinske te�ave (razumevanje zahtevane funkcionalnosti) na koncu Sprinta " + newline +
									   "(5 � ni bilo te�av, 1 � bilo je veliko te�av)</html>") + ":");

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
		
		JTextField comment8 = new JTextField();
		t8 = comment8;
		set_gbc(gbc,15,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q8, gbc);
		set_gbc(gbc,16,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b81, gbc);
		set_gbc(gbc,16,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b82, gbc);
		set_gbc(gbc,16,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b83, gbc);
		set_gbc(gbc,16,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b84, gbc);
		set_gbc(gbc,16,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b85, gbc);
		set_gbc(gbc,16,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t8, gbc);
		
		JLabel q9 = new JLabel(i18n.tr("Sodelovanje s Scrum Masterjem") + ":");

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
		
		JTextField comment9 = new JTextField();
		t9 = comment9;
		set_gbc(gbc,17,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q9, gbc);
		set_gbc(gbc,18,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b91, gbc);
		set_gbc(gbc,18,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b92, gbc);
		set_gbc(gbc,18,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b93, gbc);
		set_gbc(gbc,18,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b94, gbc);
		set_gbc(gbc,18,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b95, gbc);
		set_gbc(gbc,18,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t9, gbc);
		
		JLabel q10 = new JLabel(i18n.tr("Sodelovanje s Product Owner-jem") + ":");

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
		
		JTextField comment10 = new JTextField();
		t10 = comment10;
		set_gbc(gbc,19,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q10, gbc);
		set_gbc(gbc,20,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b101, gbc);
		set_gbc(gbc,20,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b102, gbc);
		set_gbc(gbc,20,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b103, gbc);
		set_gbc(gbc,20,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b104, gbc);
		set_gbc(gbc,20,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b105, gbc);
		set_gbc(gbc,20,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t10, gbc);
		
		JLabel q11 = new JLabel(i18n.tr("Sodelovanje znotraj razvojne skupine") + ":");

		b111 = new JRadioButton("1", true);
		b112 = new JRadioButton("2", false);
		b113 = new JRadioButton("3", false);
		b114 = new JRadioButton("4", false);
		b115 = new JRadioButton("5", false);
		
		bg[10] = new ButtonGroup();
		bg[10].add(b111);
		bg[10].add(b112);
		bg[10].add(b113);
		bg[10].add(b114);
		bg[10].add(b115);
		
		JTextField comment11 = new JTextField();
		t11 = comment11;
		set_gbc(gbc,21,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q11, gbc);
		set_gbc(gbc,22,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b111, gbc);
		set_gbc(gbc,22,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b112, gbc);
		set_gbc(gbc,22,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b113, gbc);
		set_gbc(gbc,22,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b114, gbc);
		set_gbc(gbc,22,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b115, gbc);
		set_gbc(gbc,22,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t11, gbc);
		
		JLabel q12 = new JLabel(i18n.tr("<html> Primernost obsega dela na projektu (Ali je obseg dela " + newline + 
									    "na projektu primerno izbran?)</html>") + ":");

		b121 = new JRadioButton("1", true);
		b122 = new JRadioButton("2", false);
		b123 = new JRadioButton("3", false);
		b124 = new JRadioButton("4", false);
		b125 = new JRadioButton("5", false);
		
		bg[11] = new ButtonGroup();
		bg[11].add(b121);
		bg[11].add(b122);
		bg[11].add(b123);
		bg[11].add(b124);
		bg[11].add(b125);
		
		JTextField comment12 = new JTextField();
		t12 = comment12;
		set_gbc(gbc,23,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q12, gbc);
		set_gbc(gbc,24,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b121, gbc);
		set_gbc(gbc,24,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b122, gbc);
		set_gbc(gbc,24,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b123, gbc);
		set_gbc(gbc,24,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b124, gbc);
		set_gbc(gbc,24,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b125, gbc);
		set_gbc(gbc,24,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t12, gbc);
		
		JLabel q13 = new JLabel(i18n.tr("<html> Splo�na ocena va�ega zadovoljstva s potekom dela na projektu") + ":");

		b131 = new JRadioButton("1", true);
		b132 = new JRadioButton("2", false);
		b133 = new JRadioButton("3", false);
		b134 = new JRadioButton("4", false);
		b135 = new JRadioButton("5", false);
		
		bg[12] = new ButtonGroup();
		bg[12].add(b131);
		bg[12].add(b132);
		bg[12].add(b133);
		bg[12].add(b134);
		bg[12].add(b135);
		
		JTextField comment13 = new JTextField();
		t13 = comment13;
		set_gbc(gbc,25,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q13, gbc);
		set_gbc(gbc,26,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b131, gbc);
		set_gbc(gbc,26,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b132, gbc);
		set_gbc(gbc,26,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b133, gbc);
		set_gbc(gbc,26,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b134, gbc);
		set_gbc(gbc,26,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b135, gbc);
		set_gbc(gbc,26,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t13, gbc);
		
		JLabel q14 = new JLabel(i18n.tr("<html> Splo�na ocena metodologije Scrum (Ali je ta metodologija " + newline +
									    "koristna za delo razvojne skupine? Ali bi jo priporo�ili drugim razvijalcem?)</html>") + ":");

		b141 = new JRadioButton("1", true);
		b142 = new JRadioButton("2", false);
		b143 = new JRadioButton("3", false);
		b144 = new JRadioButton("4", false);
		b145 = new JRadioButton("5", false);
		
		bg[13] = new ButtonGroup();
		bg[13].add(b141);
		bg[13].add(b142);
		bg[13].add(b143);
		bg[13].add(b144);
		bg[13].add(b145);
			
		JTextField comment14 = new JTextField();
		t14 = comment14;
		set_gbc(gbc,27,0,10,1,GridBagConstraints.HORIZONTAL);
		Panel.add(q14, gbc);
		set_gbc(gbc,28,0,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b141, gbc);
		set_gbc(gbc,28,1,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b142, gbc);
		set_gbc(gbc,28,2,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b143, gbc);
		set_gbc(gbc,28,3,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b144, gbc);
		set_gbc(gbc,28,4,1,1,GridBagConstraints.HORIZONTAL);
		Panel.add(b145, gbc);
		set_gbc(gbc,28,5,5,1,GridBagConstraints.HORIZONTAL);
		Panel.add(t14, gbc);


		int topK = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				topK, topK, topK, topK, 
				i18n.tr("Developer poll"), 
				3, topK, topK, topK));
		
		int bottomK = 6;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK,bottomK));
		
		setSize(new Dimension(800, 1000));
		Util.centre(this);
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
			
			for(int i=0;i<14;i++)
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
			for(int i=13;i<41;i=i+2)
			{
				_metricModel.addDeveloperPoolMeasurement(i, _empInput.getSelectedId(), datum, s[c], _sprintInput.getSelectedId());
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
	
	/// metric model
	protected MetricModel _metricModel;
	/// all impediments in combo box
	protected ImpedimentComboBoxModel _impedimentComboModel;
	/// developer model
	protected DeveloperModel _developerModel;
	// sprint backlog model
	protected SprintBacklogModel _sbModel;
	/// combo box models
	protected StandardComboBox _empInput, _sprintInput;
	// radio buttons
	JRadioButton b11, b12, b13, b14, b15, b21, b22, b23, b24, b25, b31, b32, b33, b34, b35, b41, b42, b43, b44, b45, b51, b52, b53, b54, b55;
	JRadioButton b61, b62, b63, b64, b65, b71, b72, b73, b74, b75, b81, b82, b83, b84, b85, b91, b92, b93, b94, b95, b101, b102, b103, b104, b105;
	JRadioButton b111, b112, b113, b114, b115, b121, b122, b123, b124, b125, b131, b132, b133, b134, b135, b141, b142, b143, b144, b145;
	// button groups
	ButtonGroup[] bg;
	// text fields
	JTextField t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2571891107362309751L;
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
}
