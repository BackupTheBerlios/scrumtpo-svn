package scrummer.model.graph;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import org.jfree.data.xy.AbstractXYDataset;
import scrummer.model.MetricModel;
import scrummer.model.MetricModelCommon;
import scrummer.model.MetricModelCommon.Measurement;
import scrummer.model.swing.MetricTableModel;
import scrummer.model.swing.MetricTableModel.MetricType;

/**
 * Data set with access to all metric related data
 */
public class MetricDataSet extends AbstractXYDataset {
	
	/**
	 * Metric data on an interval
	 */
	public static class IntervalData {
		/**
		 * Constructor
		 * @param metricName metric name
		 * @param metricType metric type
		 * @param objectId object id
		 * @param from start of interval
		 * @param to end of interval
		 */
		public IntervalData(Date from, Date to) {			
			_from = from;
			_to = to;
		}		
		
		/**
		 * Add pair to data
		 * @param d date
		 * @param value value to add
		 */
		public void add(Date d, BigDecimal value) {
			Measurement m = new Measurement(d, value);
			_measurements.add(m);
		}
		
		/**
		 * Set size
		 * @return
		 */
		public int size() {
			return _measurements.size();
		}
		
		/**
		 * Fetch x axis position of given item on graph
		 * @return x coordinate
		 */
		public Number getX(int item) {
			return _measurements.get(item).Date.getTime();
		}
		
		/**
		 * Fetch y axis position of given item on graph
		 * @return y coordinate
		 */
		public Number getY(int item) {
			return _measurements.get(item).Measurement;
		}
		
		/// a vector of measurements
		private Vector<MetricModelCommon.Measurement> _measurements = 
			new Vector<MetricModelCommon.Measurement>(); 
		/// which date designates beginning of measurements data
		private Date _from;
		/// which data designates end of measurements data
		private Date _to;
	}
	
	/**
	 * Default constructor
	 * 
	 */
	public MetricDataSet(MetricModel metricModel) {		
		_metricModel = metricModel;
	}
	
	/**
	 * Add new object measure
	 * @param name name which can be used to communicate metrics to this dataset
	 * @param metricName metric name 
	 * @param metricType metric type
	 * @param objectId object id
	 * @param from date from
	 * @param to date to
	 */
	/*
	public void addObjectMeasure(String name, String metricName, MetricTableModel.MetricType metricType, int objectId, Date from, Date to) {
		if (_intervalMeasurements.containsKey(name)) {
			throw new scrummer.exception.ValueInvalid(name, "Measure already exists.");
		}
		_intervalMeasurements.put(name, new IntervalData(_metricModel, metricName, metricType, objectId, from, to));		
		refresh();
	}
	*/
	
	/**
	 * Set object measure
	 *  
	 * @param name name of measure
	 * @param metricName metric name
	 * @param metricType metric type
	 * @param objectId object id
	 * @param from date from
	 * @param to date to
	 */
	/*
	public void setObjectMeasure(String name, String metricName, MetricTableModel.MetricType metricType, int objectId, Date from, Date to) {
		_intervalMeasurements.put(name, new IntervalData(_metricModel, metricName, metricType, objectId, from, to));		
		refresh();
	}
	*/
	
	/**
	 * Remove measure set with specified name
	 * @param name measure name
	 */
	/*
	public void removeObjectMeasure(String name) {
		_intervalMeasurements.remove(name);
		refresh();
	}	
	*/
	
	@Override
	public int getSeriesCount() {
		return _intervalMeasurements.size();
	}

	@Override
	public Comparable getSeriesKey(int arg0) {
		return _intervalMeasurements.keySet().toArray()[arg0].toString();
	}

	@Override
	public int getItemCount(int arg0) {
		Collection<IntervalData> intervals = _intervalMeasurements.values();
		int sz = 0;
		for (IntervalData i : intervals)
			sz += i.size();
		return sz;
	}

	@Override
	public Number getX(int series, int item) {		
		String key = getKey(series);
		return _intervalMeasurements.get(key).getX(item);
	}

	@Override
	public Number getY(int series, int item) {
		return _intervalMeasurements.get(getKey(series)).getY(item);
	}
	
	/**
	 * Get key at index-th location
	 * @param index index from start
	 * @return key value
	 */
	private String getKey(int index) {
		return _intervalMeasurements.keySet().toArray()[index].toString();
	}
	
	/**
	 * Set data to display on graph
	 * @param metric metric name
	 * @param data data to display
	 */
	public void setData(String metric, IntervalData data) {				
		_intervalMeasurements.clear();
		_intervalMeasurements.put(metric, data);
		fireDatasetChanged();
	}
	
	public Date getStartDate() {
		return _intervalMeasurements.get(_currentMetric)._from;
	}
	
	public Date getEndDate() {
		return _intervalMeasurements.get(_currentMetric)._to;
	}
	
	private String _currentMetric;
	/// interval measurements
	private HashMap<String, IntervalData> _intervalMeasurements = new HashMap<String, IntervalData>();
	/// metric model
	private MetricModel _metricModel;
	/// serialization id
	private static final long serialVersionUID = 6106993921915233920L;
}
