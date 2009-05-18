package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.listener.MetricListener;
import scrummer.model.MetricModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardButton;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Common dialog for addition and removal of measures
 */
public class MeasureDialog 
	extends TwoButtonDialog 
	implements MetricListener {

	/**
	 * Constructor
	 */
	public MeasureDialog(JFrame parent) {
		super(parent);
		
		_metricModel = Scrummer.getModels().getMetricModel();
		_metricModel.addMetricListener(this);
		
		int k = 10;
		Panel.setBorder(
			Util.createSpacedTitleBorder(
				k - 3, k, k, k, 
				i18n.tr("Measurement"), 
				k - 6, k, k, k));
		
		FormBuilder fb = new FormBuilder(Panel);
		
		_dateInput = fb.addSelectedTextInput(i18n.tr("Date") + ":", "Date");
		
		JPanel textCalc = new JPanel();
		textCalc.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		_resultInput = new SelectedTextField();
		_resultInput.setColumns(8);
		
		JButton calcButton = new StandardButton();
		calcButton.setText(i18n.tr("="));
		calcButton.setMinimumSize(new Dimension(1,1));
		calcButton.setSize(32, 10);
		calcButton.setActionCommand("Calculate");
		calcButton.setPreferredSize(new Dimension(32,18));
		
		textCalc.add(_resultInput);
		textCalc.add(calcButton);
		
		fb.addCustom(i18n.tr("Measurement") + ":", textCalc);
		fb.setCellSpacing(5, 6);
		
		_dateInput.setPreferredSize(new Dimension(_dateInput.getWidth(), 20));
		_resultInput.setPreferredSize(new Dimension(_resultInput.getWidth(), 20));
		
		int bottomK = 10;
		BottomPanel.setBorder(
			BorderFactory.createEmptyBorder(0, bottomK, bottomK, bottomK - 3));
		
		setSize(315, 180);
	}
	
	@Override
	public void setVisible(boolean b) {
		if (!b) {
			_metricModel.removeMetricListener(this);
		}
		super.setVisible(b);
	}

	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {}
	
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {}
	 
	/// metric model
	protected MetricModel _metricModel;
	/// date input
	protected SelectedTextField _dateInput;
	/// selected result input
	protected SelectedTextField _resultInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5015031774929775662L;
	
}
