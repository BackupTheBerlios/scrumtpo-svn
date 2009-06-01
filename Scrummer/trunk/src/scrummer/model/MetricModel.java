package scrummer.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.listener.MetricListener;
import scrummer.model.MetricModelCommon.Measurement;
import scrummer.model.graph.MetricDataSet;
import scrummer.model.graph.QuestionDataSet;
import scrummer.model.swing.MetricComboBoxModel;
import scrummer.model.swing.MetricTableModel;
import scrummer.model.swing.QuestionCustomerComboBoxModel;
import scrummer.model.swing.QuestionDeveloperComboBoxModel;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * This model deals with metrics(Task_measurement_result, Sprint_measurement_result, 
 * PBI_measurement_result, CustomerPoll_measurement_result, DeveloperPool_measurement_result
 */
public class MetricModel {
		
	/**
	 * Metric types
	 */
	public enum MetricEnum {
		/// work effectiveness indicator
		WorkEffectiveness,
		/// earned value indicator
		EarnedValue,
		/// sprint burndown chart
		SprintBurndown,
		/// schedule performance index
		SPI,
		/// cost performance index
		CPI,
		/// number of tasks completed in sprint / total tasks in sprint
		TaskCompleteness,
		/// number of pbi's completed for release / total number of pbi's
		PBICompleteness,
		/// customer questioanairre average results
		CustomerPoll,
		/// developer questionaire average results
		DeveloperPoll,
		/// customer questionaire for all sprints
		AllCustomerPoll,
		/// developer questionaire for all sprints
		AllDeveloperPoll
		;
		
		/**
		 * Fetch translated enum string
		 * @param en enum value
		 * @return translated string
		 */
		public static String convert(MetricEnum en) {
			switch (en) {
			case WorkEffectiveness: 
				return i18n.tr("Work Effectiveness");
			case CPI: 
				return i18n.tr("Cost Performance Index");
			case EarnedValue: 
				return i18n.tr("Earned Value");
			case PBICompleteness: 
				return i18n.tr("PBI Completeness");
			case SPI: 
				return i18n.tr("Schedule Performance Index");
			case SprintBurndown: 
				return i18n.tr("Sprint Burndown Chart");
			case TaskCompleteness: 
				return i18n.tr("Task Completeness");
			case CustomerPoll: 
				return i18n.tr("Customer poll");
			case DeveloperPoll: 
				return i18n.tr("Developer poll");
			case AllCustomerPoll:
				return i18n.tr("All Sprint Customer Poll");
			case AllDeveloperPoll:
				return i18n.tr("All Sprint Developer Poll");
			default: return "";
			}
		}
		
		/// translation class field
		private static I18n i18n = Scrummer.getI18n(MetricEnum.class);
	}
	
	/**
	 * Constructor
	 * @param connectionModel connection model
	 * @param projectModel project model
	 * @param sprintBacklogModel sprint backlog model
	 */
	public MetricModel(ConnectionModel connectionModel, ProjectModel projectModel, SprintBacklogModel sprintBacklogModel, ReleaseModel releaseModel) {
		_projectModel = projectModel;
		_releaseModel = releaseModel;
		_metricModelCommon = 
			new MetricModelCommon(connectionModel, _operation);
		_metricComboBoxModel = 
			new MetricComboBoxModel(_metricModelCommon);
		_metricTableModel = 
			new MetricTableModel(_metricModelCommon);
		_metricDataSet = 
			new MetricDataSet(this);
		_sprintBacklogModel = 
			sprintBacklogModel;
		_questionDataSet = 
			new QuestionDataSet();
		_questionCustomerComboBoxModel = 
			new QuestionCustomerComboBoxModel(_metricModelCommon);
		_questionDeveloperComboBoxModel =
			new QuestionDeveloperComboBoxModel(_metricModelCommon);
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
	 * Add customer poll measurement
	 * 
	 * @param measureId measure
	 * @param customerName customer 
	 * @param date date
	 * @param measurementResult result
	 * @param sprintId sprint
	 */
	public void addCustomerPoolMeasurement(int measureId, String customerName, java.util.Date date, BigDecimal measurementResult, int sprintId)
	{
		if(_metricModelCommon.addCustomerPollMeasurement(measureId, customerName, new Date(date.getTime()), measurementResult, sprintId))
		{
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Add developer poll measurement
	 * 
	 * @param measureId measure
	 * @param employeeId employee 
	 * @param date date
	 * @param measurementResult result
	 * @param sprintId sprint
	 */
	public void addDeveloperPoolMeasurement(int measureId, int employeeId, java.util.Date date, BigDecimal measurementResult, int sprintId)
	{
		if(_metricModelCommon.addDeveloperPollMeasurement(measureId, employeeId, new Date(date.getTime()), measurementResult, sprintId))
		{
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Add pbi measurement
	 * 
	 * @param pbiId measure
	 * @param releaseId release 
	 * @param date date
	 * @param measurementResult result
	 */
	public void addPBIMeasurement(int pbiId, int releaseId, java.util.Date date, BigDecimal measurementResult) {
		if (_metricModelCommon.addPBIMeasurement(pbiId, releaseId, new Date(date.getTime()), measurementResult)) {
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
	 * Remove customer poll measurement
	 * @param customerName customer
	 * @param measureId  measure
	 * @param datum date
	 * @param sprintId sprint
	 */
	public void removeCustomerPollMeasurement(int measureId, String customerName, java.util.Date datum, int sprintId) 
	{
		if (_metricModelCommon.removeCustomerPollMeasurement(customerName, measureId, new Date(datum.getTime()), sprintId)) 
		{
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Remove developer poll measurement
	 * @param employeeId employee
	 * @param measureId  measure
	 * @param datum date
	 * @param sprintId sprint
	 */
	public void removeCustomerPollMeasurement(int measureId, int employeeId, java.util.Date datum, int sprintId) 
	{
		if (_metricModelCommon.removeDeveloperPollMeasurement(employeeId, measureId, new Date(datum.getTime()), sprintId)) 
		{
			_metricTableModel.refresh();
		}
	}
	
	/**
	 * Remove pbi measurement
	 * @param measureId  measure type
	 * @param pbiId pbi
	 * @param datum date
	 */
	public void removePBIMeasurement(int measureId, int pbiId, java.util.Date datum) {
		if (_metricModelCommon.removePBIMeasurement(measureId, pbiId, new Date(datum.getTime()))) {
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
    public void updateReleaseMeasurement(int measureId, int releaseId, java.util.Date datum, BigDecimal measurementResult) {
    	if (_metricModelCommon.updateReleaseMeasurement(measureId, releaseId, new Date(datum.getTime()), measurementResult.toEngineeringString())) {
    		_metricTableModel.refresh();
    	}
    }
    
    /**
     * Update customer poll measurement information
     * @param measureId measure
     * @param customerName customer
     * @param datum date
     * @param measurementResult measurement result 
     * @param sprintId sprint
     */
    public void updateCustomerPollMeasurement(int measureId, String customerName, java.util.Date datum, String measurementResult, int sprintId) 
    {
    	if (_metricModelCommon.updateCustomerPollMeasurement(measureId, customerName, new Date(datum.getTime()), measurementResult, sprintId)) 
    	{
    		_metricTableModel.refresh();
    	}
    }
    
    /**
     * Update developer poll measurement information
     * @param measureId measure
     * @param employeeId employee
     * @param datum date
     * @param measurementResult measurement result 
     * @param sprintId sprint
     */
    public void updateDeveloperPollMeasurement(int measureId, int employeeId, java.util.Date datum, String measurementResult, int sprintId) 
    {
    	if (_metricModelCommon.updateDeveloperPollMeasurement(measureId, employeeId, new Date(datum.getTime()), measurementResult, sprintId)) 
    	{
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
    public void updatePBIMeasurement(int pbiId, int releaseId, java.util.Date datum, BigDecimal measurementResult) {
    	if (_metricModelCommon.updatePBIMeasurement(pbiId, releaseId, new Date(datum.getTime()), measurementResult.toEngineeringString())) {
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
		return _metricModelCommon.getReleaseMeasurementResult(releaseId, measureId, new Date(datum.getTime()));
	}
	
	/**
	 * Fetch customer poll measurement
	 * @param customerName customer
	 * @param measureId measure
	 * @param datum date
	 * @param sprintId sprint
	 * @return measurement
	 */
	public String getCustomerPollMeasurementResult(String customerName, int measureId, java.util.Date datum, int sprintId) 
	{
		return _metricModelCommon.getCustomerPollMeasurementResult(customerName, measureId, new Date(datum.getTime()),sprintId);
	}
	
	/**
	 * Fetch developer poll measurement
	 * @param employeeId employee
	 * @param measureId measure
	 * @param datum date
	 * @param sprintId sprint
	 * @return measurement
	 */
	public String getDeveloperPollMeasurementResult(int employeeId, int measureId, java.util.Date datum, int sprintId) 
	{
		return _metricModelCommon.getDeveloperPollMeasurementResult(employeeId, measureId, new Date(datum.getTime()), sprintId);
	}
	
	/**
	 * Fetch PBI measurement
	 * @param pbiId release
	 * @param measureId measure
	 * @param datum date
	 * @return measurement
	 */
	public String getPBIMeasurementResult(int pbiId, int measureId, java.util.Date datum) {
		return _metricModelCommon.getPBIMeasurementResult(pbiId, measureId, new Date(datum.getTime()));
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
	 * Remove event listener from this data object changes
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
	 * @return question data set
	 */
	public QuestionDataSet getQuestionDataSet() {
		return _questionDataSet;
	}
	
	/**
	 * @return combo box with all customer questions
	 */
	public QuestionCustomerComboBoxModel getQuestionCustomerComboBoxModel() {
		return _questionCustomerComboBoxModel;
	}
	
	/**
	 * @return combo box with all developer questions
	 */
	public QuestionDeveloperComboBoxModel getQuestionDeveloperComboBoxModel() {
		return _questionDeveloperComboBoxModel;
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
	public Vector<Measurement> fetchPBIMeasures(String measureName, int pbiId, java.util.Date from, java.util.Date to) {
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
	public Vector<Measurement> fetchReleaseMeasures(String measureName, int releaseId, java.util.Date from, java.util.Date to) {
		return _metricModelCommon.fetchMeasures(
			measureName, 
			DBSchemaModel.ReleaseMeasurementResultTable, 
			DBSchemaModel.ReleaseMeasurementReleaseId, 
			releaseId, from, to);
	}
	
	/**
	 * Fetch customer poll related measures
	 * @param measureName measure name
	 * @param customerName customer
	 * @param sprintId sprint
	 * @param from date from
	 * @param to date to
	 * @return a list of task measurements
	 */
	public Vector<Measurement> fetchCustomerPollMeasures(String measureName, String customerName, int sprintId, Date from, Date to) 
	{
		return _metricModelCommon.fetchCPMeasures(
			measureName, 
			DBSchemaModel.CustomerPollMeasurementResultTable, 
			DBSchemaModel.CustomerPollMeasurementResultCustomerName, 
			customerName, sprintId, from, to);
	}
	
	/**
	 * Fetch developer poll related measures
	 * @param measureName measure name
	 * @param employeeId employee
	 * @param sprintId sprint
	 * @param from date from
	 * @param to date to
	 * @return a list of task measurements
	 */
	public Vector<Measurement> fetchDeveloperPollMeasures(String measureName, int employeeId, int sprintId, Date from, Date to) 
	{
		return _metricModelCommon.fetchDPMeasures(
			measureName, 
			DBSchemaModel.DeveloperPollMeasurementResultTable, 
			DBSchemaModel.DeveloperPollMeasurementResulEmployeeId, 
			employeeId, sprintId, from, to);
	}
	
	/**
	 * Calculate work effectiveness on a time interval
	 * @param from starting date
	 * @param to ending date
	 * @return effectiveness factor
	 */
	public BigDecimal calculateWorkEffectiveness(java.util.Date from, java.util.Date to) {
		int projectId = _projectModel.getCurrentProjectId();
		return _metricModelCommon.calculateWorkEffectiveness(projectId, new Date(from.getTime()), new Date(to.getTime()));
	}
	
	/**
	 * Calculate earned value
	 * @param sprintId sprint
	 * @param sprintStart start of sprint
	 * @param date date up to which to calculate it
	 * @return calculated value
	 */
	public BigDecimal calculateEarnedValue(int sprintId, java.util.Date sprintStart, java.util.Date date) {
		int projectId = _projectModel.getCurrentProjectId();
		return _metricModelCommon.calculateEarnedValue(
			projectId,
			sprintId, 
			new Date(sprintStart.getTime()), 
			new Date(date.getTime()));
	}
	
	/**
	 * Calculate schedule performance index
	 * @param sprintId sprint
	 * @param date date up to which date to calculate it
	 * @return calculate spi
	 */
	public BigDecimal calculateSchedulePerformanceIndex(int sprintId, java.util.Date sprintStart, java.util.Date date) {
		int projectId = _projectModel.getCurrentProjectId();
		int sprintLength = _sprintBacklogModel.getSprintLength(sprintId);
		return _metricModelCommon.calculateSchedulePerformanceIndex(
			projectId, sprintId, sprintLength,
			new Date(sprintStart.getTime()),
			new Date(date.getTime()));
	}
	
	/**
	 * Calculate cost performance index
	 * @param sprintId sprint
	 * @param sprintStart sprint start
	 * @param date date up to which to calculate it
	 * @return calculated cpi
	 */
	public BigDecimal calculateCostPerformanceIndex(int sprintId, java.util.Date sprintStart, java.util.Date date) {
		int projectId = _projectModel.getCurrentProjectId();
		return BigDecimal.ZERO;
	}
	
	/**
	 * Notify everyone that work effectiveness indicator was not calculated
	 */
	public void failCalculatingWorkEffectiveness() {
		_operation.operationFailed(DataOperation.Custom, MetricOperation.WorkEffectivenessCalculated, "");
	}
	
	/**
	 * Notify everyone that earned value indicator was not calculated
	 */
	public void failCalculatingEarnedValue() {
		_operation.operationFailed(DataOperation.Custom, MetricOperation.EarnedValueCalculated, "");
	}
	
	/**
	 * Notify everyone that schedule performance index failed to be calculated
	 */
	public void failCalculatingSchedulePerformanceIndex() {
		_operation.operationFailed(DataOperation.Custom, MetricOperation.SPICalculated, "");
	}
	
	/**
	 * Notify everyone that cost performance index failed to be calculated
	 */
	public void failCalculatingCostPerformanceIndex() {
		_operation.operationFailed(DataOperation.Custom, MetricOperation.CPICalculated, "");
	}
		
	/**
	 * Calculate earned values for every day in given sprint
	 * 
	 * Earned values calculated for given day are calculated from start of sprint up to that day.
	 * 
	 * @param sprintId sprint id
	 */
	public void calculateMonthlyEarnedValue(int sprintId) {
		java.util.Date sprintStart = _sprintBacklogModel.getBeginDate(sprintId);
		java.util.Date sprintEnd = _sprintBacklogModel.getEndDate(sprintId);
		
		MetricDataSet.IntervalData data = new MetricDataSet.IntervalData(sprintStart, sprintEnd);
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sprintStart);
		// for each day in sprint calculate earned value from this day to tomorrow
		while (gc.getTime().before(sprintEnd)) {
			
			java.util.Date time = gc.getTime();
			BigDecimal dec = calculateEarnedValue(sprintId, sprintStart, time);
			data.add(time, dec);			
			gc.add(GregorianCalendar.DATE, 1);
		}
		_metricDataSet.setData(MetricEnum.EarnedValue.toString(), data);
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");
	}
	
	/**
	 * Calculate work effectiveness for each day in sprint
	 * 
	 * From start of sprint to every day in sprint.
	 * 
	 * @param sprintId sprint id
	 */
	public void calculateMonthlyWorkEffectiveness(int sprintId) {
		java.util.Date sprintStart = _sprintBacklogModel.getBeginDate(sprintId);
		java.util.Date sprintEnd = _sprintBacklogModel.getEndDate(sprintId);
		
		MetricDataSet.IntervalData data = new MetricDataSet.IntervalData(sprintStart, sprintEnd);
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sprintStart);
		// for each day in sprint calculate earned value from this day to tomorrow
		while (gc.getTime().before(sprintEnd)) {
			java.util.Date time = gc.getTime();
			BigDecimal dec = calculateWorkEffectiveness(sprintStart, time);
			data.add(time, dec);			
			gc.add(GregorianCalendar.DATE, 1);
		}
		_metricDataSet.setData(MetricEnum.WorkEffectiveness.toString(), data);
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");
	}

	/**
	 * Calculate schedule performance index for each day in sprint
	 * 
	 * It is calculate at each day in sprint.
	 * 
	 * @param sprintId sprint id
	 */
	public void calculateMonthlySPI(int sprintId) {
		java.util.Date sprintStart = _sprintBacklogModel.getBeginDate(sprintId);
		java.util.Date sprintEnd = _sprintBacklogModel.getEndDate(sprintId);
		
		MetricDataSet.IntervalData data = new MetricDataSet.IntervalData(sprintStart, sprintEnd);
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sprintStart);
		// for each day in sprint calculate earned value from this day to tomorrow
		while (gc.getTime().before(sprintEnd)) {
			java.util.Date time = gc.getTime();
			BigDecimal dec = calculateSchedulePerformanceIndex(sprintId, sprintStart, time);
			data.add(time, dec);			
			gc.add(GregorianCalendar.DATE, 1);
		}
		_metricDataSet.setData(MetricEnum.SPI.toString(), data);
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");
	}
	
	/**
	 * Calculate task completeness index for given sprint
	 * @param sprintId sprint
	 */
	public void calculateTaskCompleteness(int sprintId) {
		java.util.Date sprintStart = _sprintBacklogModel.getBeginDate(sprintId);
		java.util.Date sprintEnd = _sprintBacklogModel.getEndDate(sprintId);
		
		MetricDataSet.IntervalData data = new MetricDataSet.IntervalData(sprintStart, sprintEnd);
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sprintStart);
		// for each day in sprint calculate earned value from this day to tomorrow
		while (gc.getTime().before(sprintEnd)) {
			java.util.Date time = gc.getTime();
			BigDecimal top = 
				new BigDecimal(
					_metricModelCommon.countTasks(
						sprintId,
						new Date(sprintStart.getTime()), 
						new Date(sprintEnd.getTime())));
			BigDecimal bottom =
				new BigDecimal(
						_metricModelCommon.countTasksCompleted(
							sprintId,
							new Date(sprintStart.getTime()), 
							new Date(sprintEnd.getTime())));
			
			BigDecimal res = BigDecimal.ZERO;
			try {
				res = top.divide(bottom, 3, BigDecimal.ROUND_HALF_EVEN);
			} catch (ArithmeticException ex) {}
			
			// 	calculateSchedulePerformanceIndex(sprintId, sprintStart, time);
			data.add(time, res);			
			gc.add(GregorianCalendar.DATE, 1);
		}
		_metricDataSet.setData(MetricEnum.TaskCompleteness.toString(), data);
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");
	}
	
	/**
	 * Calculate spent work for every task on given sprint
	 * @param sprintId sprint
	 */
	public void calculateSprintWorkSpent(int sprintId) {
		java.util.Date sprintStart = _sprintBacklogModel.getBeginDate(sprintId);
		java.util.Date sprintEnd = _sprintBacklogModel.getEndDate(sprintId);
		
		MetricDataSet.IntervalData data = new MetricDataSet.IntervalData(sprintStart, sprintEnd);
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sprintStart);
		// for each day in sprint calculate earned value from this day to tomorrow
		while (gc.getTime().before(sprintEnd)) {
			java.util.Date time = gc.getTime();
			BigDecimal res = _metricModelCommon.calculateAllTaskSpentWork(sprintId, new Date(time.getTime()));
			// 	calculateSchedulePerformanceIndex(sprintId, sprintStart, time);
			data.add(time, res);			
			gc.add(GregorianCalendar.DATE, 1);
		}
		_metricDataSet.setData(MetricEnum.SprintBurndown.toString(), data);
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");
	}
	
	/**
	 * Calculate pbi completeness
	 * @param sprintId sprint 
	 * @param releaseId release
	 */
	public void calculatePBIComplete(int sprintId, int releaseId) {
		int pbicount = _releaseModel.fetchPbis(releaseId).size();
		int allpbicount = _metricModelCommon.calculatePBISprintCount(sprintId);
		java.util.Date sprintStart = _sprintBacklogModel.getBeginDate(sprintId);
		java.util.Date sprintEnd = _sprintBacklogModel.getEndDate(sprintId);
		
		MetricDataSet.IntervalData data = new MetricDataSet.IntervalData(sprintStart, sprintEnd);
		BigDecimal res = BigDecimal.ZERO;
		if (allpbicount > 0) {
			res = new BigDecimal((float)pbicount / (float)allpbicount);
		}
		data.add(sprintStart, res);
		data.add(sprintEnd, res);
		
		_metricDataSet.setData(MetricEnum.PBICompleteness.toString(), data);
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");
	}
	
	/**
	 * Calculate customer question marks
	 * @param sprintId sprint
	 * @param questionId question id
	 */
	public void calculateCustomerQuestionMark(int sprintId, int questionId) {	
		HashMap<String, Integer> marks = _metricModelCommon.calculateCustomerQuestionMark(sprintId, questionId);		
		_questionDataSet.setValues(marks);		
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");
	}
	
	/**
	 * Calculate dev question marks
	 * @param sprintId sprint
	 * @param questionId question id
	 */
	public void calculateDeveloperQuestionMark(int sprintId, int questionId) {	
		HashMap<String, Integer> marks = _metricModelCommon.calculateDeveloperQuestionMark(sprintId, questionId);		
		_questionDataSet.setValues(marks);		
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");
	}
	
	/**
	 * Calculate all sprint developer poll results
	 * @param questionId question id
	 */
	public void calculateAllSprintDeveloperPoll(int questionId) {	
		HashMap<String, Integer> marks = _metricModelCommon.calculateDeveloperQuestionMark(questionId);		
		_questionDataSet.setValues(marks);		
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");	
	}
	
	/**
	 * Calculate all sprint customer poll results
	 * @param questionId question id
	 */
	public void calculateAllSprintCustomerPoll(int questionId) {	
		HashMap<String, Integer> marks = _metricModelCommon.calculateCustomerQuestionMark(questionId);		
		_questionDataSet.setValues(marks);		
		_operation.operationSucceeded(DataOperation.Custom, MetricOperation.Graph, "");	
	}
	
	/// customer questions
	private QuestionCustomerComboBoxModel _questionCustomerComboBoxModel;
	/// developer questions
	private QuestionDeveloperComboBoxModel _questionDeveloperComboBoxModel;
	/// release model
	private ReleaseModel _releaseModel;
	/// project model
	private ProjectModel _projectModel;
	/// sprint backlog model
	private SprintBacklogModel _sprintBacklogModel;
	/// graph metric data set
	private MetricDataSet _metricDataSet;
	/// question data set
	private QuestionDataSet _questionDataSet;
	/// metric table model
	private MetricTableModel _metricTableModel;
	/// metric name combo box
	private MetricComboBoxModel _metricComboBoxModel;
	/// common metric model
	private MetricModelCommon _metricModelCommon;
	/// project event listeners
	private Operations.MetricOperation _operation = new Operations.MetricOperation();
	
}