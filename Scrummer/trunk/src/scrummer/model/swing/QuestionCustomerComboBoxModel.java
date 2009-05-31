package scrummer.model.swing;

import scrummer.model.MetricModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * A model that displays only questions that are being asked by customers
 */
public class QuestionCustomerComboBoxModel extends IdValueComboBoxModel {

	public QuestionCustomerComboBoxModel(MetricModelCommon metricModelCommon) {
		_metricModelCommon = metricModelCommon;
	}

	@Override
	public void refresh() {
		setValues(_metricModelCommon.fetchCustomerMetricDescriptions());
		super.refresh();
	}
	
	private MetricModelCommon _metricModelCommon;
	/// serialization id
	private static final long serialVersionUID = 8231494609475938261L;
}
