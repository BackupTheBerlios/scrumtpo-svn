package scrummer.model.swing;

import scrummer.model.MetricModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * Model with developer questions
 */
public class QuestionDeveloperComboBoxModel extends IdValueComboBoxModel {

	public QuestionDeveloperComboBoxModel(MetricModelCommon metricModelCommon) {
		_metricModelCommon = metricModelCommon;
	}

	@Override
	public void refresh() {
		setValues(_metricModelCommon.fetchDeveloperMetricDescriptions());
		super.refresh();
	}

	/// common metric model
	private MetricModelCommon _metricModelCommon;
	/// serialization id
	private static final long serialVersionUID = 1778113359146327234L;	
}
