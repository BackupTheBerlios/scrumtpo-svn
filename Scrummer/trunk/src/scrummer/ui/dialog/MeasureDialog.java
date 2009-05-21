package scrummer.ui.dialog;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.omg.CORBA._PolicyStub;
import org.xnap.commons.i18n.I18n;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.listener.MetricListener;
import scrummer.model.MetricModel;
import scrummer.ui.FormBuilder;
import scrummer.ui.Util;
import scrummer.uicomponents.SelectedTextField;
import scrummer.uicomponents.StandardComboBox;
import scrummer.uicomponents.TwoButtonDialog;

/**
 * Common dialog for addition and removal of measures
 */
public class MeasureDialog 
	extends TwoButtonDialog 
	implements ItemListener, MetricListener {

	/**
	 * Constructor
	 */
	public MeasureDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
		
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
		_resultInput = fb.addComboBoxInput(i18n.tr("Result") + ":");
		_resultInput.setEditable(true);
		_resultInput.addItem("Work Effectiveness");
		_resultInput.addItemListener(this);
		_resultInput.setEnabled(true);
	
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
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int index = _resultInput.getSelectedIndex();
			switch (index) {
			case 0:				
				try { _previous = new BigDecimal(_resultInput.getSelectedItem().toString());
				} catch (NumberFormatException ex) {}
				WorkEffectivenessDialog dialog = 
					new WorkEffectivenessDialog(getParentFrame());
				Util.centre(dialog);
				dialog.setVisible(true);
				break;
			}
		}
	}
	
	@Override
	public void operationSucceeded(DataOperation type, MetricOperation identifier, String message) {}
	
	@Override
	public void operationFailed(DataOperation type, MetricOperation identifier, String message) {}
	 
	/// previous resultinput contents
	protected BigDecimal _previous = BigDecimal.ZERO;
	/// metric model
	protected MetricModel _metricModel;
	/// date input
	protected SelectedTextField _dateInput;
	/// selected result input
	protected StandardComboBox _resultInput;
	// protected SelectedTextField _resultInput;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 5015031774929775662L;
}
