package scrummer.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;

import scrummer.listener.MetricListener;
import scrummer.model.MetricModelCommon.Measurement;
import scrummer.model.graph.MetricDataSet;
import scrummer.model.swing.MetricComboBoxModel;
import scrummer.model.swing.MetricTableModel;
import scrummer.util.Operations;

/**
 * This model deals with metrics(Task_measurement_result, Sprint_measurement_result, 
 * PBI_measurement_result, 
 */
public class MetricModel {

	/**
	 * Constructor
	 * @param connectionModel connection model
	 */
	public MetricModel(ConnectionModel connectionModel) {
		_metricModelCommon = 
			new MetricModelCommon(connectionModel, _operation);
		_metricComboBoxModel = 
			new MetricComboBoxModel(_metricModelCommon);
		_metricTableModel = 
			new MetricTableModel(_metricModelCommon);
		_metricDataSet = 
			new MetricDataSet(this);
	}
	
	/**
	 * Add a new measure
	 *  
	 * @param measureName name
	 * @param measureDescription description
	 */
	public void addMeasure(String measureName, String measureDescription) {
		if (_metricModelCommon.addMeasure(measureName, measureDescription)) {
			_metricComboBoxModel.refresh();
		}
	}
	/**
	 * Add task measurement 
	 * @param measureId measure type
	 * @param taskId task
	 * @param date date
	 * @param measurementResult result
	 */
	public void addTaskMeasurement(int measureId, int taskId, java.util.Date date, BigDecimal measurementResult) {
		if (_metricModelCommon.addTaskMeasurement(measureId, taskId, new java.sql.Date(date.getTime()), measurementResult)) {
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Add sprint measurement result
	 * 
	 * @param measureId measure type
	 * @param sprintId sprint
	 * @param datum date
	 * @param measurementResult result
	 */
	public void addSprintMeasurement(int measureId, int sprintId, java.util.Date date, BigDecimal measurementResult) {
		if (_metricModelCommon.addSprintMeasurement(sprintId, measureId, new java.sql.Date(date.getTime()), measurementResult)) {
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Add release measurement
	 * 
	 * @param measureId measure
	 * @param releaseId release 
	 * @param date date
	 * @param measurementResult result
	 */
	public void addReleaseMeasurement(int measureId, int releaseId, java.util.Date date, BigDecimal measurementResult) {
		if (_metricModelCommon.addReleaseMeasurement(measureId, releaseId, new Date(date.getTime()), measurementResult)) {
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Remove measure
	 * 
	 * @param measureId measure id
	 */
	public void removeMeasure(int measureId) {
		if (_metricModelCommon.removeMeasure(measureId)) {
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Remove release measurement
	 * @param measureId measure
	 * @param releaseId release
	 * @param datum date
	 */
	public void removeReleaseMeasurement(int measureId, int releaseId, java.util.Date datum) {
		if (_metricModelCommon.removeReleaseMeasurement(measureId, releaseId, new Date(datum.getTime()))) {
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Remove task measurement 
	 * @param measureId measure
	 * @param taskId task id
	 * @param datum date
	 */
	public void removeTaskMeasurement(int measureId, int taskId, java.util.Date datum) {
		if (_metricModelCommon.removeTaskMeasurement(measureId, taskId, new Date(datum.getTime()))) {
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Remove sprint measurement
	 * @param sprintId sprint
	 * @param measureId  measure
	 * @param datum date
	 */
	public void removeSprintMeasurement(int measureId, int sprintId, java.util.Date datum) {
		if (_metricModelCommon.removeSprintMeasurement(sprintId, measureId, new Date(datum.getTime()))) {
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Update measure information
	 * 
	 * @param measureId measure id
	 * @param measureName new name
	 * @param measureDescription new description
	 */
	public void updateMeasure(int measureId, String measureName, String measureDescription) {
		if (_metricModelCommon.updateMeasure(measureId, measureName, measureDescription)) {
			_metricTableModel.refresh();
		}
	}
	
	/**
     * Update task information
     * 
     * @param measureId measure
     * @param taskId task
     * @param datum date
     * @param measurementResult new measurement result 
     */
    public void updateTaskMeasurement(int measureId, int taskId, java.util.Date datum, BigDecimal measurementResult) {
    	System.out.println(measureId + "; " + taskId);
    	if (_metricModelCommon.updateTaskMeasurement(measureId, taskId, new Date(datum.getTime()), measurementResult.toEngineeringString())) {
    		_metricTableModel.refresh();
    	}
    }
    
    /**
     * Update sprint measurement 
     * @param sprintId sprint
     * @param measureId measure
     * @param datum date
     * @param measurementResult new result
     */
    public void updateSprintMeasurement(int measureId, int sprintId, java.util.Date datum, BigDecimal measurementResult) {
    	System.out.println(measureId + "; " + sprintId);
    	if (_metricModelCommon.updateSprintMeasurement(sprintId, measureId, new Date(datum.getTime()), measurementResult.toEngineeringString())) {
    		_metricTableModel.refresh();
    	}
    }
    
    /**
     * Update release measurement information
     * @param measureId measure
     * @param releaseId release
     * @param datum date
     * @param measurementResult measurement result 
     */
    public void updateReleaseMeasurement(int measureId, int releaseId, java.util.Date datum, String measurementResult) {
    	if (_metricModelCommon.updateReleaseMeasurement(measureId, releaseId, new Date(datum.getTime()), measurementResult)) {
    		_metricTableModel.refresh();
    	}
    }
	
	/**
	 * Fetch measure name
	 * @param measureId measure
	 * @return name
	 */
	public String getMeasureName(int measureId) {
		return _metricModelCommon.getMeasureName(measureId);
	}	
	
	/**
	 * Fetch task measurement
	 * @param measureId measure
	 * @param taskId task
	 * @param datum date
	 * @return measurement
	 */
	public String getTaskMeasurementResult(int measureId, int taskId, java.util.Date datum) {
		return _metricModelCommon.getTaskMeasurementResult(measureId, taskId, new Date(datum.getTime()));
	}
	
	/**
	 * Fetch sprint measurement
	 * @param sprintId sprint
	 * @param taskId task
	 * @param datum date
	 * @return measurement
	 */
	public String getSprintMeasurementResult(int sprintId, int measureId, java.util.Date datum) {
		return _metricModelCommon.getSprintMeasurementResult(sprintId, measureId, new Date(datum.getTime()));
	}
	
	/**
	 * Fetch release measurement
	 * @param releaseId release
	 * @param measureId measure
	 * @param datum date
	 * @return measurement
	 */
	public String getReleaseMeasurementResult(int releaseId, int measureId, java.util.Date datum) {
		return _metricModelCommon.getMeasurementResult(releaseId, measureId, new Date(datum.getTime()));
	}
	
	
	
	/**
	 * Fetch measure description    
	 * @param measureId measure id
	 * @return description
	 */
	public String getMeasureDescription(int measureId) {
		return _metricModelCommon.getMeasureDescription(measureId);
	}
	
	/**
	 * Add event listener to changes from this data object changes
	 * @param listener listener instance
	 */
	public void addMetricListener(MetricListener listener)  {
		_operation.addListener(listener);
	}
	
	/**
	 * Remnove event listener from this data object changes
	 * @param listener listener instance
	 */
	public void removeMetricListener(MetricListener listener) {
		_operation.removeListener(listener);
	}
	
	/**
	 * @return metric name dropdown
	 */
	public MetricComboBoxModel getMetricComboBoxModel() {
		return _metricComboBoxModel;
	}
	
	/**
	 * @return metric table model
	 */
	public MetricTableModel getMetricTableModel() {
		return _metricTableModel;
	}
	
	/**
	 * @return metric data set
	 */
	public MetricDataSet getMetricDataSet() {
		return _metricDataSet;
	}
	
	/**
	 * Fetch sprint measurements 
	 * 
	 * @param measureName measure name
	 * @param sprintId sprint id
	 * @param from from date
	 * @param to end date
	 * @return measurements
	 */
	public Vector<Measurement> fetchSprintMeasures(String measureName, int sprintId, java.util.Date from, java.util.Date to) {
		return _metricModelCommon.fetchMeasures(
			measureName, 
			DBSchemaModel.SprintMeasurementResultTable, 
			DBSchemaModel.SprintMeasurementSprintId, 
			sprintId, from, to);
	}
	
	/**
	 * Fetch task related measures
	 * @param measureName measure name
	 * @param taskId task id
	 * @param from date from
	 * @param to date to
	 * @return a list of task measurements
	 */
	public Vector<Measurement> fetchTaskMeasures(String measureName, int taskId, java.util.Date from, java.util.Date to) {
		return _metricModelCommon.fetchMeasures(
			measureName, 
			DBSchemaModel.TaskMeasurementResultTable, 
			DBSchemaModel.TaskMeasurementTaskId, 
			taskId, from, to);
	}
	
	/**
	 * Fetch pbi related measures
	 * @param measureName measure name
	 * @param taskId task id
	 * @param from date from
	 * @param to date to
	 * @return a list of task measurements
	 */
	public Vector<Measurement> fetchPBIMeasures(String measureName, int pbiId, Date from, Date to) {
		return _metricModelCommon.fetchMeasures(
			measureName, 
			DBSchemaModel.PBIMeasurementResultTable, 
			DBSchemaModel.PBIMeasurementPBIId, 
			pbiId, from, to);
	}
	
	/**
	 * Fetch release related measures
	 * @param measureName measure name
	 * @param releaseId relevant release
	 * @param from date from
	 * @param to date to
	 * @return a list of task measurements
	 */
	public Vector<Measurement> fetchReleaseMeasures(String measureName, int releaseId, Date from, Date to) {
		return _metricModelCommon.fetchMeasures(
			measureName, 
			DBSchemaModel.ReleaseMeasurementResultTable, 
			DBSchemaModel.ReleaseMeasurementReleaseId, 
			releaseId, from, to);
	}
	
	/// graph metric data set
	private MetricDataSet _metricDataSet;
	/// metric table model
	private MetricTableModel _metricTableModel;
	/// metric name combo box
	private MetricComboBoxModel _metricComboBoxModel;
	/// common metric model
	private MetricModelCommon _metricModelCommon;
	/// project event listeners
	private Operations.MetricOperation _operation = new Operations.MetricOperation();
}