package scrummer.model;

import java.sql.Date;

/**
 * This model deals with 
 */
public class MetricModel {

	public MetricModel(ConnectionModel connectionModel) {
		_metricModelCommon = 
			new MetricModelCommon(connectionModel);
	}
	
	/**
	 * Add a new measure
	 *  
	 * @param measureName name
	 * @param measureDescription description
	 */
	public void addMeasure(String measureName, String measureDescription) {
		_metricModelCommon.addMeasure(measureName, measureDescription);
	}
	/**
	 * Add task measurement 
	 * @param measureId measure type
	 * @param taskId task
	 * @param date date
	 * @param measurementResult result
	 */
	public void addTaskMeasurement(int measureId, int taskId, java.util.Date date, String measurementResult) {
		_metricModelCommon.addTaskMeasurement(measureId, taskId, new java.sql.Date(date.getTime()), measurementResult);
	}
	
	/**
	 * Add sprint measurement result
	 * 
	 * @param sprintId sprint
	 * @param measureId measure type
	 * @param datum date
	 * @param measurementResult result
	 */
	public void addSprintMeasurement(int sprintId, int measureId, java.util.Date date, String measurementResult) {
		_metricModelCommon.addSprintMeasurement(sprintId, measureId, new java.sql.Date(date.getTime()), measurementResult);
	}
	
	/**
	 * Add release measurement
	 * 
	 * @param measureId measure
	 * @param releaseId release 
	 * @param date date
	 * @param measurementResult result
	 */
	public void addReleaseMeasurement(int measureId, int releaseId, java.util.Date date, String measurementResult) {
		_metricModelCommon.addReleaseMeasurement(measureId, releaseId, new Date(date.getTime()), measurementResult);
	}
	
	/**
	 * Remove measure
	 * 
	 * @param measureId measure id
	 */
	public void removeMeasure(int measureId) {
		_metricModelCommon.removeMeasure(measureId);
	}
	
	/**
	 * Remove release measurement
	 * @param measureId measure
	 * @param releaseId release
	 * @param datum date
	 */
	public void removeReleaseMeasurement(int measureId, int releaseId, java.util.Date datum) {
		_metricModelCommon.removeReleaseMeasurement(measureId, releaseId, new Date(datum.getTime()));
	}
	
	/**
	 * Remove task measurement 
	 * @param measureId measure
	 * @param taskId task id
	 * @param datum date
	 */
	public void removeTaskMeasurement(int measureId, int taskId, Date datum) {
		_metricModelCommon.removeTaskMeasurement(measureId, taskId, new Date(datum.getTime()));
	}
	
	/**
	 * Remove sprint measurement
	 * @param sprintId sprint
	 * @param measureId  measure
	 * @param datum date
	 */
	public void removeSprintMeasurement(int sprintId, int measureId, java.util.Date datum) {
		_metricModelCommon.removeSprintMeasurement(sprintId, measureId, new Date(datum.getTime()));
	}
	
	/**
	 * Update measure information
	 * 
	 * @param measureId measure id
	 * @param measureName new name
	 * @param measureDescription new description
	 */
	public void updateMeasure(int measureId, String measureName, String measureDescription) {
		_metricModelCommon.updateMeasure(measureId, measureName, measureDescription);	
	}
	
	/**
     * Update task information
     * 
     * @param measureId measure
     * @param taskId task
     * @param datum date
     * @param measurementResult new measurement result 
     */
    public void updateTaskMeasurement(int measureId, int taskId, java.util.Date datum, String measurementResult) {
    	_metricModelCommon.updateTaskMeasurement(measureId, taskId, new Date(datum.getTime()), measurementResult);
    }
    
    /**
     * Update sprint measurement 
     * @param sprintId sprint
     * @param measureId measure
     * @param datum date
     * @param measurementResult new result
     */
    public void updateSprintMeasurement(int sprintId, int measureId, Date datum, String measurementResult) {
    	_metricModelCommon.updateSprintMeasurement(sprintId, measureId, new Date(datum.getTime()), measurementResult);
    }
    
    /**
     * Update release measurement information
     * @param measureId measure
     * @param releaseId release
     * @param datum date
     * @param measurementResult measurement result 
     */
    public void updateReleaseMeasurement(int measureId, int releaseId, java.util.Date datum, String measurementResult) {
    	_metricModelCommon.updateReleaseMeasurement(measureId, releaseId, new Date(datum.getTime()), measurementResult);
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
		
	/// common metric model
	private MetricModelCommon _metricModelCommon;
}
