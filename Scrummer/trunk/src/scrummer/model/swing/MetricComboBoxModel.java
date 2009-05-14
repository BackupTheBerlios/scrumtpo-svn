package scrummer.model.swing;

import scrummer.model.MetricModelCommon;
import scrummer.model.swing.base.IdValueComboBoxModel;

/**
 * A list of metrics
 */
public class MetricComboBoxModel extends IdValueComboBoxModel {
	
	public MetricComboBoxModel(MetricModelCommon metricModelCommon) {
		_metricModelCommon = metricModelCommon;
	}
	
	@Override
	public void refresh() {
		refreshMetrics();
		super.refresh();
	}
	
	private void refreshMetrics() {
		setValues(_metricModelCommon.fetchMetricDescriptions());
	}
	
	/// common metrics related operations
	private MetricModelCommon _metricModelCommon;
	/// serialization id
	private static final long serialVersionUID = -287780926769370955L;
}
