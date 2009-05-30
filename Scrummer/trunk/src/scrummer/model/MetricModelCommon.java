package scrummer.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * Metric model common
 * 
 * Deals with Measure, Sprint_measurement_result, PBI_measurement_result, Release_measurement_result
 * It also houses indicator calculation functions
 */
public class MetricModelCommon {

	/**
	 * Constructor
	 * @param connectionModel connection model
	 */
	public MetricModelCommon(ConnectionModel connectionModel, Operations.MetricOperation operation) {
		_connectionModel = connectionModel;
		_operation = operation;
	}
	
	/**
	 * Measurement conducted at certain date
	 */
	public static class Measurement {
		public Measurement(Date date, BigDecimal measurement) {
			Date = date;
			Measurement = measurement;
		}
		
		public Date Date;
		public BigDecimal Measurement;
	}
	
	public static class MeasureRow extends DataRow {
        /**
         * Constructor
         *
         * @param result result from which to get data
         */
        public MeasureRow(ResultSet result) {
            try {
                result.beforeFirst(); result.next();
                MeasureId =
                    result.getInt(1);
                MeasureName =
            		result.getString(2);
                MeasureDescription =
            		result.getString(3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * Does key equal
         * @param taskId
         * @return true if row key equals this row
         */
        public boolean keyEquals(int measureId) {
            if (measureId == MeasureId) {
                return true;
            } else {
                return false;
            }
        }
	        
        public int MeasureId;
        public String MeasureName;
        public String MeasureDescription;
	}

	public static class TaskMeasurementRow extends DataRow {
        /**
         * Constructor
         *
         * @param result result from which to get data
         */
        public TaskMeasurementRow(ResultSet result) {
            try {
                result.beforeFirst(); result.next();
                MeasureId =
                        result.getInt(1);
                TaskId =
                        result.getInt(2);
                Datum =
                        result.getDate(3);
                MeasurementResult =
                        result.getString(4);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * Does key equal
         * @param measureId measure
         * @param taskId task
         * @param datum date
         * @return true if row key equals this row
         */
        public boolean keyEquals(int measureId, int taskId, java.sql.Date datum) {
            if ((measureId == MeasureId) &&
                (taskId == TaskId) &&
                (datum == Datum)) {
                return true;
            } else {
                    return false;
            }
        }
        public int MeasureId;
        public int TaskId;
        public java.sql.Date Datum;
        public String MeasurementResult;
	}

	public static class SprintMeasurementRow extends DataRow {
        /**
         * Constructor
         *
         * @param result result from which to get data
         */
        public SprintMeasurementRow(ResultSet result) {
            try {
                    result.beforeFirst(); result.next();
                    SprintId =
                            result.getInt(1);
                    MeasureId =
                            result.getInt(2);
                    Datum =
                            result.getDate(3);
                    MeasurementResult =
                            result.getString(4);
            } catch (SQLException e) {
                    e.printStackTrace();
            }
        }

        /**
         * Does key equal
         * @param taskId
         * @return true if row key equals this row
         */
        public boolean keyEquals(int sprintId, int measureId, java.sql.Date datum) {
            if ((sprintId == SprintId) &&
                (measureId == MeasureId) &&
                (datum == Datum)) {
                    return true;
            } else {
                    return false;
            }
        }
        public int SprintId;
        public int MeasureId;
        public java.sql.Date Datum;
        public String MeasurementResult;
	}
	
	public static class ReleaseMeasurementRow extends DataRow {
        /**
         * Constructor
         *
         * @param result result from which to get data
         */
        public ReleaseMeasurementRow(ResultSet result) {
            try {
                result.beforeFirst(); result.next();
                SprintId =
                    result.getInt(1);
                MeasureId =
                    result.getInt(2);
                Datum =
                    result.getDate(3);
                MeasurementResult =
                    result.getString(4);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * Does key equal
         * @param taskId
         * @return true if row key equals this row
         */
        public boolean keyEquals(int sprintId, int measureId, java.sql.Date datum) {
            if ((sprintId == SprintId) &&
                (measureId == MeasureId) &&
                (datum == Datum)) {
                return true;
            } else {
                return false;
            }
        }
        public int SprintId;
        public int MeasureId;
        public java.sql.Date Datum;
        public String MeasurementResult;
	}
	
	public static class CustomerPollMeasurementRow extends DataRow {
        /**
         * Constructor
         *
         * @param result result from which to get data
         */
        public CustomerPollMeasurementRow(ResultSet result) {
            try {
                    result.beforeFirst(); result.next();
                    CustomerName =
                            result.getString(1);
                    MeasureId =
                            result.getInt(2);
                    Datum =
                            result.getDate(3);
                    MeasurementResult =
                            result.getString(4);
                    SprintId =
                    		result.getInt(5);
            } catch (SQLException e) {
                    e.printStackTrace();
            }
        }

        /**
         * Does key equal
         * @param taskId
         * @return true if row key equals this row
         */
        public boolean keyEquals(String customerName, int measureId, java.sql.Date datum, int sprintId) 
        {
            if ((customerName == CustomerName) &&
                (measureId == MeasureId) &&
                (datum == Datum) &&
                (sprintId == SprintId)) {
                    return true;
            } else {
                    return false;
            }
        }
        public String CustomerName;
        public int MeasureId;
        public java.sql.Date Datum;
        public String MeasurementResult;
        public int SprintId;
	}
	
	public static class DeveloperPollMeasurementRow extends DataRow {
        /**
         * Constructor
         *
         * @param result result from which to get data
         */
        public DeveloperPollMeasurementRow(ResultSet result) {
            try {
                    result.beforeFirst(); result.next();
                    EmployeeId =
                            result.getInt(1);
                    MeasureId =
                            result.getInt(2);
                    Datum =
                            result.getDate(3);
                    MeasurementResult =
                            result.getString(4);
                    SprintId =
                    		result.getInt(5);
            } catch (SQLException e) {
                    e.printStackTrace();
            }
        }

        /**
         * Does key equal
         * @param taskId
         * @return true if row key equals this row
         */
        public boolean keyEquals(int employeeId, int measureId, java.sql.Date datum, int sprintId) 
        {
            if ((employeeId == EmployeeId) &&
                (measureId == MeasureId) &&
                (datum == Datum) &&
                (sprintId == SprintId)) {
                    return true;
            } else {
                    return false;
            }
        }
        public int EmployeeId;
        public int MeasureId;
        public java.sql.Date Datum;
        public String MeasurementResult;
        public int SprintId;
	}
	
	public static class Row extends DataRow {
        /**
         * Constructor
         *
         * @param result result from which to get data
         */
        public Row(ResultSet result) {
            try {
                result.beforeFirst(); result.next();
                MeasureId =
                    result.getInt(1);
                PBIId =
                    result.getInt(2);
                Datum =
                    result.getDate(3);
                MeasurementResult =
                    result.getString(4);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * Does key equal
         * @param taskId
         * @return true if row key equals this row
         */
        public boolean keyEquals(int measureId, int pBIId, java.sql.Date datum) {
            if ((measureId == MeasureId) &&
                (pBIId == PBIId) &&
                (datum == Datum)) {
                    return true;
            } else {
                    return false;
            }
        }
        public int MeasureId;
        public int PBIId;
        public java.sql.Date Datum;
        public String MeasurementResult;
	}

	/**
	 * Add a new measure
	 *  
	 * @param measureName name
	 * @param measureDescription description
	 * @return true if measure added, false otherwise
	 */
	public boolean addMeasure(String measureName, String measureDescription) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.MeasureTable +
            "(" +
            DBSchemaModel.MeasureName + "," +
            DBSchemaModel.MeasureDescription +
            ")" +
            " VALUES (?, ?) ";
            st = conn.prepareStatement(query);
            st.setString(1, measureName);
            st.setString(2, measureDescription);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, MetricOperation.Measure, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, MetricOperation.Measure, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}

	/**
	 * Add task measurement 
	 * @param measureId measure type
	 * @param taskId task
	 * @param datum date
	 * @param measurementResult result
	 * @return true if task added, false otherwise
	 */
	public boolean addTaskMeasurement(int measureId, int taskId, java.sql.Date datum, BigDecimal measurementResult) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.TaskMeasurementResultTable +
            " (" +
            DBSchemaModel.TaskMeasurementResultId + ", " +
            DBSchemaModel.TaskMeasurementTaskId + ", " +
            DBSchemaModel.TaskMeasurementResultDatum + ", " +
            DBSchemaModel.TaskMeasurementResultResult +
            ")" +
            " VALUES (?, ?, ?, ?) ";
            st = conn.prepareStatement(query);
            st.setInt(1, measureId);
            st.setInt(2, taskId);
            st.setDate(3, datum);
            st.setBigDecimal(4, measurementResult);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, MetricOperation.TaskMeasure, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, MetricOperation.TaskMeasure, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}
	
	/**
	 * Add sprint measurement result
	 * 
	 * @param sprintId sprint
	 * @param measureId measure type
	 * @param datum date
	 * @param measurementResult result
	 * @return true if sprint measurement added, false otherwise
	 */
	public boolean addSprintMeasurement(int sprintId, int measureId, java.sql.Date datum, BigDecimal measurementResult) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.SprintMeasurementResultTable +
            "(" +
            DBSchemaModel.SprintMeasurementResultId + ", " + 
            DBSchemaModel.SprintMeasurementSprintId + ", " + 
            DBSchemaModel.SprintMeasurementResultDate + ", " +
            DBSchemaModel.SprintMeasurementResultResult +
            ")" +
            " VALUES (?, ?, ?, ?) ";
            st = conn.prepareStatement(query);
            st.setInt(1, measureId);
            st.setInt(2, sprintId);
            st.setDate(3, datum);
            st.setBigDecimal(4, measurementResult);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, MetricOperation.SprintMeasure, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, MetricOperation.SprintMeasure, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}
	
	/**
	 * Add release measurement
	 * 
	 * @param measureId measure
	 * @param releaseId release 
	 * @param datum date
	 * @param measurementResult result
	 * @return true if measurement added, false otherwise
	 */
	public boolean addReleaseMeasurement(int measureId, int releaseId, java.sql.Date datum, BigDecimal measurementResult) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.ReleaseMeasurementResultTable +
            "(" +
            DBSchemaModel.ReleaseMeasurementResultId + ", " + 
            DBSchemaModel.ReleaseMeasurementReleaseId + ", " +
            DBSchemaModel.ReleaseMeasurementResultDate + ", " +
            DBSchemaModel.ReleaseMeasurementResultResult +
            ")" +
            " VALUES (?, ?, ?) ";
            st = conn.prepareStatement(query);
            st.setInt(1, measureId);
            st.setInt(2, releaseId);
            st.setDate(3, datum);
            st.setBigDecimal(4, measurementResult);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, MetricOperation.ReleaseMeasure, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, MetricOperation.ReleaseMeasure, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}
	
	/**
	 * Add customer poll measurement
	 * 
	 * @param measureId measure
	 * @param customerName customer 
	 * @param datum date
	 * @param measurementResult result
	 * @param sprintId sprint
	 * @return true if measurement added, false otherwise
	 */
	public boolean addCustomerPollMeasurement(int measureId, String customerName, java.sql.Date datum, BigDecimal measurementResult, int sprintId) 
	{
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.CustomerPollMeasurementResultTable +
            "(" +
            DBSchemaModel.CustomerPollMeasurementResultId + ", " + 
            DBSchemaModel.CustomerPollMeasurementResultCustomerName + ", " +
            DBSchemaModel.CustomerPollMeasurementResultDate + ", " +
            DBSchemaModel.CustomerPollMeasurementResultResult + ", " +
            DBSchemaModel.CustomerPollMeasurementSprintId +
            ")" +
            " VALUES (?, ?, ?, ?, ?) ";
            st = conn.prepareStatement(query);
            st.setInt(1, measureId);
            st.setString(2, customerName);
            st.setDate(3, datum);
            st.setBigDecimal(4, measurementResult);
            st.setInt(5, sprintId);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, MetricOperation.CustomerPollMeasure, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, MetricOperation.CustomerPollMeasure, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}
	
	/**
	 * Add developer poll measurement
	 * 
	 * @param measureId measure
	 * @param employeeId employee 
	 * @param datum date
	 * @param measurementResult result
	 * @param sprintId sprint
	 * @return true if measurement added, false otherwise
	 */
	public boolean addDeveloperPollMeasurement(int measureId, int employeeId, java.sql.Date datum, BigDecimal measurementResult, int sprintId) 
	{
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.DeveloperPollMeasurementResultTable +
            "(" +
            DBSchemaModel.DeveloperPollMeasurementResultId + ", " + 
            DBSchemaModel.DeveloperPollMeasurementResulEmployeeId + ", " +
            DBSchemaModel.DeveloperPollMeasurementResultDate + ", " +
            DBSchemaModel.DeveloperPollMeasurementResultResult + ", " +
            DBSchemaModel.DeveloperPollMeasurementSprintId +
            ")" +
            " VALUES (?, ?, ?, ?, ?) ";
            st = conn.prepareStatement(query);
            st.setInt(1, measureId);
            st.setInt(2, employeeId);
            st.setDate(3, datum);
            st.setBigDecimal(4, measurementResult);
            st.setInt(5, sprintId);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, MetricOperation.DeveloperPollMeasure, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, MetricOperation.DeveloperPollMeasure, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}
	
	/**
	 * Add pbi measurement
	 * @param measureId measure type
	 * @param pBIId pbi
	 * @param datum date
	 * @param measurementResult result
	 * @return true if added, false otherwise
	 */
	public boolean addPBIMeasurement(int measureId, int pBIId, java.sql.Date datum, BigDecimal measurementResult) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.PBIMeasurementResultTable +
            "(" +
            DBSchemaModel.PBIMeasurementResultId + ", " +
            DBSchemaModel.PBIMeasurementPBIId + ", " +
            DBSchemaModel.PBIMeasurementResultDate + ", " +
            DBSchemaModel.PBIMeasurementResultResult + 
            ")" +
            " VALUES (?, ?, ?, ?)";
            st = conn.prepareStatement(query);
            st.setInt(1, measureId);
            st.setInt(2, pBIId);
            st.setDate(3, datum);
            st.setBigDecimal(4, measurementResult);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, MetricOperation.PBIMeasure, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, MetricOperation.PBIMeasure, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}

		
	/**
	 * Remove measure
	 * 
	 * @param measureId measure id
	 * @return true if removed, false otherwise
	 */
	public boolean removeMeasure(int measureId) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Remove, MetricOperation.Measure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.Measure,
            i18n.tr("Failed to remove measure"));
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.MeasureTable +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId
        );
        return q.getResult();
	}
	
	/**
	 * Remove release measurement
	 * @param measureId measure
	 * @param releaseId release
	 * @param datum date
	 * @return true if removed, false otherwise
	 */
	public boolean removeReleaseMeasurement(int measureId, int releaseId, java.sql.Date datum) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Remove, MetricOperation.ReleaseMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.ReleaseMeasure,
            ex.getMessage());
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.ReleaseMeasurementResultTable +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.ReleaseId + "=" + releaseId + " AND " +
        DBSchemaModel.ReleaseMeasurementResultDate + "='" + datum + "'"
        );
        return q.getResult();
	}

	/**
	 * Remove task measurement 
	 * @param measureId measure
	 * @param taskId task id
	 * @param datum date
	 * @return true if measurement removed, false otherwise
	 */
	public boolean removeTaskMeasurement(int measureId, int taskId, java.sql.Date datum) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Remove, MetricOperation.TaskMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
                setResult(false);
                ex.printStackTrace();
                _operation.operationFailed(DataOperation.Remove, MetricOperation.TaskMeasure,
                i18n.tr(ex.getMessage()));
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.TaskMeasurementResultTable +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.TaskId + "=" + taskId + " AND " +
        DBSchemaModel.TaskMeasurementResultDatum + "='" + datum + "'"
        );
        return q.getResult();
	}
	
	/**
	 * Remove sprint measurement
	 * @param sprintId sprint
	 * @param measureId  measure
	 * @param datum date
	 * @return true if measurement removed, false otherwise
	 */
	public boolean removeSprintMeasurement(int sprintId, int measureId, java.sql.Date datum) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
                setResult(true);
                _operation.operationSucceeded(DataOperation.Remove, MetricOperation.SprintMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
                setResult(false);
                ex.printStackTrace();
                _operation.operationFailed(DataOperation.Remove, MetricOperation.SprintMeasure,
                ex.getMessage());
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.SprintMeasurementResultTable +
        " WHERE " +
        DBSchemaModel.SprintId + "=" + sprintId + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.SprintMeasurementResultDate + "='" + datum + "'"
        );
        return q.getResult();
	}

	/**
	 * Remove customer poll measurement
	 * @param customerName customer
	 * @param measureId  measure
	 * @param datum date
	 * @param sprintId sprint
	 * @return true if measurement removed, false otherwise
	 */
	public boolean removeCustomerPollMeasurement(String customerName, int measureId, java.sql.Date datum, int sprintId) 
	{
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
                setResult(true);
                _operation.operationSucceeded(DataOperation.Remove, MetricOperation.CustomerPollMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
                setResult(false);
                ex.printStackTrace();
                _operation.operationFailed(DataOperation.Remove, MetricOperation.CustomerPollMeasure,
                ex.getMessage());
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.CustomerPollMeasurementResultTable +
        " WHERE " +
        DBSchemaModel.CustomerPollMeasurementResultCustomerName + "=" + customerName + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.SprintMeasurementResultDate + "='" + datum + " AND " +
        DBSchemaModel.SprintId + "=" + sprintId + "'"
        );
        return q.getResult();
	}
	
	/**
	 * Remove developer poll measurement
	 * @param employeeId employee
	 * @param measureId  measure
	 * @param datum date
	 * @param sprintId sprint
	 * @return true if measurement removed, false otherwise
	 */
	public boolean removeDeveloperPollMeasurement(int employeeId, int measureId, java.sql.Date datum, int sprintId) 
	{
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
                setResult(true);
                _operation.operationSucceeded(DataOperation.Remove, MetricOperation.DeveloperPollMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
                setResult(false);
                ex.printStackTrace();
                _operation.operationFailed(DataOperation.Remove, MetricOperation.DeveloperPollMeasure,
                ex.getMessage());
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.DeveloperPollMeasurementResultTable +
        " WHERE " +
        DBSchemaModel.DeveloperPollMeasurementResulEmployeeId + "=" + employeeId + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.SprintMeasurementResultDate + "='" + datum + " AND " +
        DBSchemaModel.SprintId + "=" + sprintId + "'"
        );
        return q.getResult();
	}
	
	/**
	 * Remove pbi measurement
	 * @param measureId measure
	 * @param pBIId pbi id
	 * @param datum date
	 * @return true if removed, false otherwise
	 */
	public boolean removePBIMeasurement(int measureId, int pBIId, java.sql.Date datum) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Remove, MetricOperation.PBIMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.PBIMeasure, ex.getMessage());
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.PBIMeasurementResultTable +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.PBIId + "=" + pBIId + " AND " +
        DBSchemaModel.PBIMeasurementResultDate + "='" + datum + "'"
        );
        return q.getResult();
	}
	
	/**
	 * Update measure information
	 * 
	 * @param measureId measure id
	 * @param measureName new name
	 * @param measureDescription new description
	 * @return true if update succeeded, false otherwise
	 */
	public boolean updateMeasure(int measureId, String measureName, String measureDescription) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(
            DataOperation.Update, MetricOperation.Measure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            _operation.operationFailed(DataOperation.Update, MetricOperation.Measure,
            i18n.tr("Could not update measure."));
            ex.printStackTrace();
        }
        };
        q.query("UPDATE " + DBSchemaModel.MeasureTable + " " +
         "SET " + DBSchemaModel.MeasureName + "='" + measureName + "'," +
         		  DBSchemaModel.MeasureDescription + "='" + measureDescription + "' " +
        " WHERE " +
        DBSchemaModel.MeasureId + "='" + measureId + "'");
        return q.getResult();
    };
    
    /**
     * Update task information
     * 
     * @param measureId measure
     * @param taskId task
     * @param datum date
     * @param measurementResult new measurement result 
     * @return true if update succeeded, false otherwise
     */
    public boolean updateTaskMeasurement(int measureId, int taskId, java.sql.Date datum, String measurementResult) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Update, MetricOperation.TaskMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            _operation.operationFailed(DataOperation.Update, MetricOperation.TaskMeasure, ex.getMessage());
            ex.printStackTrace();
        }
        };
        q.query("UPDATE " + DBSchemaModel.TaskMeasurementResultTable + " " +
         "SET " + DBSchemaModel.TaskMeasurementResultResult + "='" + measurementResult + "'" +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.TaskId + "=" + taskId + " AND " +
        DBSchemaModel.TaskMeasurementResultDatum + "='" + datum + "'");
        return q.getResult();
	}

    /**
     * Update sprint measurement 
     * @param sprintId sprint
     * @param measureId measure
     * @param datum date
     * @param measurementResult new result
     * @return true if update succeeded, false otherwise
     */
    public boolean updateSprintMeasurement(int sprintId, int measureId, java.sql.Date datum, String measurementResult) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Update, MetricOperation.SprintMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            _operation.operationFailed(DataOperation.Update, MetricOperation.SprintMeasure, ex.getMessage());
            ex.printStackTrace();
        }
        };
        q.query("UPDATE " + DBSchemaModel.SprintMeasurementResultTable + " " +
         "SET " + DBSchemaModel.SprintMeasurementResultResult + "='" + measurementResult + "'" +
        " WHERE " +
        DBSchemaModel.SprintId + "=" + sprintId + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.SprintMeasurementResultDate + "='" + datum + "'");    
        return q.getResult();
    }

    /**
     * Update release measurement information
     * @param measureId measure
     * @param releaseId release
     * @param datum date
     * @param measurementResult measurement result
     * @return true if update succeeded, false otherwise 
     */
    public boolean updateReleaseMeasurement(int measureId, int releaseId, java.sql.Date datum, String measurementResult) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Update, MetricOperation.ReleaseMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            _operation.operationFailed(DataOperation.Update, MetricOperation.ReleaseMeasure, ex.getMessage());
            ex.printStackTrace();
        }
        };
        q.query("UPDATE " + DBSchemaModel.ReleaseMeasurementResultTable + " " +
         "SET " + DBSchemaModel.SprintMeasurementResultResult + "='" + measurementResult + "'" +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.ReleaseId + "=" + releaseId + " AND " +
        DBSchemaModel.ReleaseMeasurementResultDate + "='" + datum + "'");
        return q.getResult();
    }
    
    /**
     * Update customer poll measurement information
     * @param measureId measure
     * @param customerName customer
     * @param datum date
     * @param measurementResult measurement result
     * @return true if update succeeded, false otherwise 
     */
    public boolean updateCustomerPollMeasurement(int measureId, String customerName, java.sql.Date datum, String measurementResult, int sprintId) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Update, MetricOperation.CustomerPollMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            _operation.operationFailed(DataOperation.Update, MetricOperation.CustomerPollMeasure, ex.getMessage());
            ex.printStackTrace();
        }
        };
        q.query("UPDATE " + DBSchemaModel.CustomerPollMeasurementResultTable + " " +
         "SET " + DBSchemaModel.CustomerPollMeasurementResultResult + "='" + measurementResult + "'" +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.CustomerPollMeasurementResultCustomerName + "=" + customerName + " AND " +
        DBSchemaModel.CustomerPollMeasurementResultDate + "='" + datum + "' AND" +
        DBSchemaModel.CustomerPollMeasurementSprintId + "=" + sprintId);
        return q.getResult();
    }

    /**
     * Update developer poll measurement information
     * @param measureId measure
     * @param employeeId employee
     * @param datum date
     * @param measurementResult measurement result
     * @return true if update succeeded, false otherwise 
     */
    public boolean updateDeveloperPollMeasurement(int measureId, int employeeId, java.sql.Date datum, String measurementResult, int sprintId) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Update, MetricOperation.DeveloperPollMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            _operation.operationFailed(DataOperation.Update, MetricOperation.DeveloperPollMeasure, ex.getMessage());
            ex.printStackTrace();
        }
        };
        q.query("UPDATE " + DBSchemaModel.DeveloperPollMeasurementResultTable + " " +
         "SET " + DBSchemaModel.DeveloperPollMeasurementResultResult + "='" + measurementResult + "'" +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.DeveloperPollMeasurementResulEmployeeId + "=" + employeeId + " AND " +
        DBSchemaModel.DeveloperPollMeasurementResultDate + "='" + datum + "' AND" +
        DBSchemaModel.DeveloperPollMeasurementSprintId + "=" + sprintId);
        return q.getResult();
    }
    
    /**
     * Update pbi measurement
     * @param measureId measure
     * @param pBIId pbi
     * @param datum date
     * @param measurementResult result
     * @return true if measure update, false otherwise
     */
    public boolean updatePBIMeasurement(int measureId, int pBIId, java.sql.Date datum, String measurementResult) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Update, MetricOperation.PBIMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            _operation.operationFailed(DataOperation.Update, MetricOperation.PBIMeasure, ex.getMessage());
            ex.printStackTrace();
        }
        };
        q.query("UPDATE " + DBSchemaModel.PBIMeasurementResultTable + " " +
         "SET " + DBSchemaModel.PBIMeasurementResultResult + "='" + measurementResult + "'"  +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.PBIId + "=" + pBIId + " AND " +
        DBSchemaModel.PBIMeasurementResultDate + "='" + datum + "'");
        return q.getResult();
    }
    
    /**
     * Fetch measure with given id
     * @param measureId measure
     * @return row with all information
     */
    public MeasureRow getMeasureRow(int measureId) {
        ResultQuery<MeasureRow> q = new ResultQuery<MeasureRow>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new MeasureRow(result));
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(null);
            ex.printStackTrace();
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.MeasureTable + " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId);
        return q.getResult();
	}

    public String getMeasureName(int measureId) {
        return getMeasureRow(measureId).MeasureName;
    }
    
	public String getMeasureDescription(int measureId) {
	        return getMeasureRow(measureId).MeasureDescription;
	}
	
	public TaskMeasurementRow getTaskMeasurementRow(int measureId, int taskId, java.sql.Date datum) {
        ResultQuery<TaskMeasurementRow> q = new ResultQuery<TaskMeasurementRow>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new TaskMeasurementRow(result));
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(null);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.TaskMeasure, ex.getMessage());
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.TaskMeasurementResultTable + " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.TaskId + "=" + taskId + " AND " +
        DBSchemaModel.TaskMeasurementResultDatum + "=" + datum);
        return q.getResult();
	}
	
	public String getTaskMeasurementResult(int measureId, int taskId, java.sql.Date datum) {
        return getTaskMeasurementRow(measureId, taskId, datum).MeasurementResult;
	}
	
	public SprintMeasurementRow getSprintMeasurementRow(int sprintId, int measureId, java.sql.Date datum) {
        ResultQuery<SprintMeasurementRow> q = new ResultQuery<SprintMeasurementRow>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new SprintMeasurementRow(result));
            _operation.operationSucceeded(DataOperation.Remove, MetricOperation.SprintMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(null);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.SprintMeasure, ex.getMessage());
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.SprintMeasurementResultTable + " WHERE " +
        DBSchemaModel.SprintId + "=" + sprintId + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.SprintMeasurementResultDate + "=" + datum);
        return q.getResult();
	}
	
	public String getSprintMeasurementResult(int sprintId, int measureId, java.sql.Date datum) {
        return getSprintMeasurementRow(sprintId, measureId, datum).MeasurementResult;
	}
	
	/**
	 * Fetch release row
	 * @param measureId measure
	 * @param releaseId release 
	 * @param datum date
	 * @return row
	 */
	public ReleaseMeasurementRow getReleaseRow(int measureId, int releaseId, java.sql.Date datum) {
        ResultQuery<ReleaseMeasurementRow> q = new ResultQuery<ReleaseMeasurementRow>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new ReleaseMeasurementRow(result));
            _operation.operationSucceeded(DataOperation.Select, MetricOperation.ReleaseMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
                setResult(null);
                ex.printStackTrace();
                _operation.operationFailed(DataOperation.Select, MetricOperation.ReleaseMeasure, ex.getMessage());
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.ReleaseMeasurementResultTable + " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.ReleaseId + "=" + releaseId + " AND " +
        DBSchemaModel.ReleaseMeasurementResultDate + "='" + datum + "'");
        return q.getResult();
    }

	public String getReleaseMeasurementResult(int measureId, int releaseId, java.sql.Date datum) {
        return getReleaseRow(measureId, releaseId, datum).MeasurementResult;
	}
	
	/**
	 * Fetch pbi measurement row
	 * @param measureId measure id
	 * @param pBIId pbi id
	 * @param datum date
	 * @return row
	 */
	public Row getPBIMeasurementRow(int measureId, int pBIId, java.sql.Date datum) {
        ResultQuery<Row> q = new ResultQuery<Row>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new Row(result));
            _operation.operationSucceeded(DataOperation.Remove, MetricOperation.PBIMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(null);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.PBIMeasure, i18n.tr(""));
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.PBIMeasurementResultTable + " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.PBIId + "=" + pBIId + " AND " +
        DBSchemaModel.PBIMeasurementResultDate + "='" + datum + "'");
        return q.getResult();
    }

	public String getPBIMeasurementResult(int measureId, int pBIId, java.sql.Date datum) {
        return getPBIMeasurementRow(measureId, pBIId, datum).MeasurementResult;
	}
	
	public String getCustomerPollMeasurementResult(String customerName, int measureId, java.sql.Date datum, int sprintId)
	{
		return getCustomerPollMeasurementRow(customerName, measureId, datum, sprintId).MeasurementResult;
	}
	
	public String getDeveloperPollMeasurementResult(int employeeId, int measureId, java.sql.Date datum, int sprintId)
	{
		return getDeveloperPollMeasurementRow(employeeId, measureId, datum, sprintId).MeasurementResult;
	}
	
	public CustomerPollMeasurementRow getCustomerPollMeasurementRow(String customerName, int measureId, java.sql.Date datum, int sprintId) 
	{
        ResultQuery<CustomerPollMeasurementRow> q = new ResultQuery<CustomerPollMeasurementRow>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new CustomerPollMeasurementRow(result));
            _operation.operationSucceeded(DataOperation.Remove, MetricOperation.CustomerPollMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(null);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.CustomerPollMeasure, ex.getMessage());
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.CustomerPollMeasurementResultTable + " WHERE " +
        DBSchemaModel.CustomerPollMeasurementResultCustomerName + "=" + customerName + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.CustomerPollMeasurementResultDate + "=" + datum + " AND " +
        DBSchemaModel.SprintId + "=" + sprintId);
        return q.getResult();
	}
	
	public DeveloperPollMeasurementRow getDeveloperPollMeasurementRow(int employeeId, int measureId, java.sql.Date datum, int sprintId) 
	{
        ResultQuery<DeveloperPollMeasurementRow> q = new ResultQuery<DeveloperPollMeasurementRow>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new DeveloperPollMeasurementRow(result));
            _operation.operationSucceeded(DataOperation.Remove, MetricOperation.DeveloperPollMeasure, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(null);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.DeveloperPollMeasure, ex.getMessage());
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.DeveloperPollMeasurementResultTable + " WHERE " +
        DBSchemaModel.DeveloperPollMeasurementResulEmployeeId + "=" + employeeId + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.DeveloperPollMeasurementResultDate + "=" + datum + " AND " +
        DBSchemaModel.SprintId + "=" + sprintId);
        return q.getResult();
	}
	
	/**
	 * Fetch id, name pairs for metric
	 * @return metric descriptions
	 */
	public Vector<IdValue> fetchMetricDescriptions() {
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				setResult(IdValue.fetchValues(result));
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}					
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.MeasureId + ", " +
						DBSchemaModel.MeasureName + 
			" FROM " + DBSchemaModel.MeasureTable);
		return q.getResult();
	}
	
	/**
	 * General metric data fetch rows
	 * @param id id of task, sprint, ...
	 * @param metricId metric id
	 * @param tableName table name
	 * @param idColumn not id column but task, sprint id, ... column
	 * @param dateColumn date column name
	 * @param measureColumn measure column name
	 * @return rows or null if error occurred
	 */
	private Vector<ObjectRow> fetchAnyMetricData(int id, int metricId, String tableName, String idColumn, String dateColumn, String measureColumn) {
		ResultQuery<Vector<ObjectRow>> q = new ResultQuery<Vector<ObjectRow>>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				Vector<ObjectRow> res = ObjectRow.fetchRows(result);
				ObjectRow.convertDate(res, 1);
				setResult(res);
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(null);
				ex.printStackTrace();
			}					
		};
		q.queryResult("SELECT " +
				idColumn + ", " +
				dateColumn  + ", " +
				measureColumn +
			" FROM " + tableName + 
			" WHERE " +
			idColumn + "=" + id + " AND " + 
			DBSchemaModel.MeasureId + "=" + metricId);
		return q.getResult();
	}
	
	/**
	 * Fetch task metric information
	 * @param taskId task id
	 * @return table rows
	 */
	public Vector<ObjectRow> fetchTaskMetric(int taskId, int metricId) {
		Vector<ObjectRow> ret = 
			fetchAnyMetricData(taskId, metricId,
				DBSchemaModel.TaskMeasurementResultTable, 
				DBSchemaModel.TaskMeasurementTaskId, 
				DBSchemaModel.TaskMeasurementResultDatum, 
				DBSchemaModel.TaskMeasurementResultResult);
		if (ret == null) {
			_operation.operationFailed(DataOperation.Select, MetricOperation.TaskMeasure, 
				i18n.tr("Could not retrieve task measures."));
			return new Vector<ObjectRow>();
		} else {
			_operation.operationSucceeded(DataOperation.Select, MetricOperation.TaskMeasure, 
					"");
			return ret;
		}
	}
	
	/**
	 * Fetch release metric information
	 * @param releaseId release id
	 * @return table rows
	 */
	public Vector<ObjectRow> fetchReleaseMetric(int releaseId, int metricId) {
		Vector<ObjectRow> ret = 
			fetchAnyMetricData(releaseId, metricId,
				DBSchemaModel.ReleaseMeasurementResultTable, 
				DBSchemaModel.ReleaseMeasurementReleaseId, 
				DBSchemaModel.ReleaseMeasurementResultDate, 
				DBSchemaModel.ReleaseMeasurementResultResult);
		if (ret == null) {
			_operation.operationFailed(DataOperation.Select, MetricOperation.ReleaseMeasure, 
				i18n.tr("Could not retrieve task measures."));
			return new Vector<ObjectRow>();
		} else {
			_operation.operationSucceeded(DataOperation.Select, MetricOperation.ReleaseMeasure, "");
			return ret;
		}
	}
	
	/**
	 * Fetch release metric information
	 * @param releaseId release id
	 * @return table rows
	 */
	public Vector<ObjectRow> fetchPBIMetric(int releaseId, int metricId) {
		Vector<ObjectRow> ret = 
			fetchAnyMetricData(releaseId, metricId,
				DBSchemaModel.PBIMeasurementResultTable, 
				DBSchemaModel.PBIMeasurementPBIId, 
				DBSchemaModel.PBIMeasurementResultDate, 
				DBSchemaModel.PBIMeasurementResultResult);
		if (ret == null) {
			_operation.operationFailed(DataOperation.Select, MetricOperation.PBIMeasure, 
				i18n.tr("Could not retrieve pbi measures."));
			return new Vector<ObjectRow>();
		} else {
			_operation.operationSucceeded(DataOperation.Select, MetricOperation.PBIMeasure, "");
			return ret;
		}
	}
	
	/**
	 * Fetch sprint metric information
	 * @param sprintId task id
	 * @param metricId metric id
	 * @return table rows
	 */
	public Vector<ObjectRow> fetchSprintMetric(int sprintId, int metricId) {
		Vector<ObjectRow> ret = 
			fetchAnyMetricData(sprintId, metricId,
				DBSchemaModel.SprintMeasurementResultTable, 
				DBSchemaModel.SprintMeasurementSprintId, 
				DBSchemaModel.SprintMeasurementResultDate, 
				DBSchemaModel.SprintMeasurementResultResult);
		if (ret == null) {
			_operation.operationFailed(DataOperation.Select, MetricOperation.SprintMeasure, 
				i18n.tr("Could not retrieve sprint measures."));
			return new Vector<ObjectRow>();
		} else {
			_operation.operationSucceeded(DataOperation.Select, MetricOperation.SprintMeasure, "");
			return ret;
		}
	}
	
	/**
	 * Fetch measures on a time interval
	 * @param measureName measure name(Measure_name)
	 * @param table table from which to get date
	 * @param objectIdColumn object id column
	 * @param objectId object id
	 * @param from date from 
	 * @param to date to
	 * @return a list of measurements
	 */
	public Vector<Measurement> fetchMeasures(String measureName, String table, String objectIdColumn, int objectId, Date from, Date to) {
		ResultQuery<Vector<Measurement>> q = new ResultQuery<Vector<Measurement>>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				Vector<Measurement> ret = new Vector<Measurement>();
				result.beforeFirst();
				while (result.next()) {
					ret.add(new Measurement(result.getDate(1), result.getBigDecimal(2)));
				}
				setResult(ret);
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(new Vector<Measurement>());
				ex.printStackTrace();
			}
		};
		String query ="SELECT Datum, Measurement_result FROM " + table + " NATURAL JOIN Measure WHERE ";
		query += "Datum BETWEEN '" + new java.sql.Date(from.getTime()).toString() + "' AND '";
		query += new java.sql.Date(to.getTime()).toString() + "' AND ";
		query += objectIdColumn + "=" + objectId + " AND ";
		query += DBSchemaModel.MeasureName + "='" + measureName + "'";
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Fetch measures on a time interval
	 * @param measureName measure name(Measure_name)
	 * @param table table from which to get date
	 * @param objectIdColumn object id column
	 * @param objectId object id
	 * @param from date from 
	 * @param to date to
	 * @return a list of measurements
	 */
	public Vector<Measurement> fetchCPMeasures(String measureName, String table, String objectIdColumn, String customerName, int sprintId, Date from, Date to) 
	{
		ResultQuery<Vector<Measurement>> q = new ResultQuery<Vector<Measurement>>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				Vector<Measurement> ret = new Vector<Measurement>();
				result.beforeFirst();
				while (result.next()) {
					ret.add(new Measurement(result.getDate(1), result.getBigDecimal(2)));
				}
				setResult(ret);
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(new Vector<Measurement>());
				ex.printStackTrace();
			}
		};
		String query ="SELECT Datum, Measurement_result FROM " + table + " NATURAL JOIN Measure WHERE ";
		query += "Datum BETWEEN '" + new java.sql.Date(from.getTime()).toString() + "' AND '";
		query += new java.sql.Date(to.getTime()).toString() + "' AND ";
		query += objectIdColumn + "=" + customerName + " AND ";
		query += DBSchemaModel.SprintId + "=" + sprintId + " AND ";
		query += DBSchemaModel.MeasureName + "='" + measureName + "'";
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Fetch measures on a time interval
	 * @param measureName measure name(Measure_name)
	 * @param table table from which to get date
	 * @param objectIdColumn object id column
	 * @param objectId object id
	 * @param from date from 
	 * @param to date to
	 * @return a list of measurements
	 */
	public Vector<Measurement> fetchDPMeasures(String measureName, String table, String objectIdColumn, int employeeId, int sprintId, Date from, Date to) 
	{
		ResultQuery<Vector<Measurement>> q = new ResultQuery<Vector<Measurement>>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				Vector<Measurement> ret = new Vector<Measurement>();
				result.beforeFirst();
				while (result.next()) {
					ret.add(new Measurement(result.getDate(1), result.getBigDecimal(2)));
				}
				setResult(ret);
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(new Vector<Measurement>());
				ex.printStackTrace();
			}
		};
		String query ="SELECT Datum, Measurement_result FROM " + table + " NATURAL JOIN Measure WHERE ";
		query += "Datum BETWEEN '" + new java.sql.Date(from.getTime()).toString() + "' AND '";
		query += new java.sql.Date(to.getTime()).toString() + "' AND ";
		query += objectIdColumn + "=" + employeeId + " AND ";
		query += DBSchemaModel.SprintId + "=" + sprintId + " AND ";
		query += DBSchemaModel.MeasureName + "='" + measureName + "'";
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Calculate remaining work for all tasks on given date and project
	 * @param projectId project
	 * @param d1 date
	 * @return calculate remaining work in hours
	 */
	public BigDecimal calculateAllTaskRemainingWork(int projectId, java.sql.Date d1) {
		ResultQuery<BigDecimal> q = new ResultQuery<BigDecimal>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next()) {
					BigDecimal res = result.getBigDecimal(1);
					if (res == null) {
						setResult(new BigDecimal(0));
					} else {
						setResult(res);
					}
				}
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(new BigDecimal(0));
				ex.printStackTrace();
			}
		};
		String query = "SELECT SUM(" + DBSchemaModel.SprintPBIHoursRemaining + ") as Hours_remaining_sum FROM " +
		DBSchemaModel.SprintPBITable + " NATURAL JOIN " + DBSchemaModel.SprintTable + " WHERE " +
		DBSchemaModel.ProjectId + "=" + projectId + " AND " +
		DBSchemaModel.SprintPBIMeasureDay + "='" + d1.toString() + "'"; 		
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Calculate spent work for all tasks on given date and project between two dates
	 * 
	 * @param projectId project
	 * @param d1 starting date
	 * @param d2 ending date
	 * @return calculate remaining work in hours
	 */
	public BigDecimal calculateAllTaskSpentWork(int projectId, java.sql.Date d1, java.sql.Date d2) {
		ResultQuery<BigDecimal> q = new ResultQuery<BigDecimal>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next()) {
					BigDecimal res = result.getBigDecimal(1);
					if (res == null) {
						setResult(BigDecimal.ZERO);
					} else {
						setResult(res);
					}
				}
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(BigDecimal.ZERO);
				ex.printStackTrace();
			}
		};
		String query = "SELECT SUM(" + DBSchemaModel.SprintPBIHourseSpent + ") as Hours_spent_sum FROM " +
		DBSchemaModel.SprintPBITable + " NATURAL JOIN " + DBSchemaModel.SprintTable + " WHERE " +
		DBSchemaModel.ProjectId + "=" + projectId + " AND " +
		DBSchemaModel.SprintPBIMeasureDay + " BETWEEN " + "'" + d1.toString() + "' AND '" + d2.toString() + "'"; 		
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Calculate spent work for all tasks on given date and sprint
	 * 
	 * @param sprintId project
	 * @param date on which day to calculate
	 * @return calculate remaining work in hours
	 */
	public BigDecimal calculateAllTaskSpentWork(int sprintId, java.sql.Date date) {
		ResultQuery<BigDecimal> q = new ResultQuery<BigDecimal>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				result.beforeFirst();
				while (result.next()) {
					BigDecimal res = result.getBigDecimal(1);
					if (res == null) {
						setResult(BigDecimal.ZERO);
					} else {
						setResult(res);
					}
				}
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(BigDecimal.ZERO);
				ex.printStackTrace();
			}
		};
		String query = "SELECT SUM(" + DBSchemaModel.SprintPBIHourseSpent + ") as Hours_spent_sum FROM " +
		DBSchemaModel.SprintPBITable + " NATURAL JOIN " + DBSchemaModel.SprintTable + " WHERE " +
		DBSchemaModel.SprintId + "=" + sprintId + " AND " +
		DBSchemaModel.SprintPBIMeasureDay + "='" + date + "'"; 		
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Calculate work effectiveness indicator
	 * @param projectId project id
	 * @param d1 starting date
	 * @param d2 ending date
	 * @return work effectiveness
	 */
	public BigDecimal calculateWorkEffectiveness(int projectId, java.sql.Date d1, java.sql.Date d2) {
		BigDecimal allTaskSpent = calculateAllTaskSpentWork(projectId, d1, d2); 
		BigDecimal bd = BigDecimal.ZERO;		
		try {
			if (allTaskSpent.compareTo(BigDecimal.ZERO) == 0) {
				System.out.println(allTaskSpent);
				BigDecimal d1Remaining = calculateAllTaskRemainingWork(projectId, d1);
				BigDecimal d2Remaining = calculateAllTaskRemainingWork(projectId, d2);
				bd = d1Remaining.subtract(d2Remaining).divide(allTaskSpent, 3, RoundingMode.UP);
			}
			_operation.operationSucceeded(
			     DataOperation.Custom, 
			     MetricOperation.WorkEffectivenessCalculated, bd.toEngineeringString());
		} catch (ArithmeticException ex) {
			bd = BigDecimal.ZERO;
		}
		return bd;
	}
	
	/**
	 * Calculate value of all completed tasks
	 * @param sprintId sprint for which to calculate value
	 * @param sprintStart start of sprint
	 * @param projectId project id
	 * @param date date on which to perform calculation
	 * @return earned value indicator
	 */
	public BigDecimal calculateEarnedValue(int projectId, int sprintId, java.sql.Date sprintStart, java.sql.Date date) {
		ResultQuery<BigDecimal> q = new ResultQuery<BigDecimal>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				setResult(BigDecimal.ZERO);
				result.beforeFirst();
				while (result.next()) {
					BigDecimal res = result.getBigDecimal(1);					
					if (res != null) {						
						setResult(res);
					}
				}
				_operation.operationSucceeded(
					DataOperation.Custom, 
					MetricOperation.EarnedValueCalculated, 
					getResult().toEngineeringString());
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(BigDecimal.ZERO);
				ex.printStackTrace();
				_operation.operationFailed(
					DataOperation.Custom,
					MetricOperation.EarnedValueCalculated, 
					ex.getMessage());
			}
		};
		// calculate top part of ER equation (work spent until day) and work remaining at day, join
		// them together using natural join; then calculate the equation results from table
		// that has all the coefficients
		String query =
			"SELECT " +  
			"SUM(Hours_spent_until_d / (Hours_spent_until_d + Hours_remaining_d)) as Earned_value FROM " +
				"(SELECT " + 
				DBSchemaModel.TaskId + ", " +
				"SUM(" + DBSchemaModel.SprintPBIHoursRemaining + ") as Hours_remaining_d" + ", " +
				"Hours_spent_until_d  FROM " + DBSchemaModel.SprintPBITable + " NATURAL JOIN  (" +
					"SELECT " + 
					DBSchemaModel.TaskId + ", " + 
					"SUM(" + DBSchemaModel.HoursSpent + ") as Hours_spent_until_d FROM " + 
					DBSchemaModel.SprintPBITable + 
					" WHERE " + 
					DBSchemaModel.SprintPBIMeasureDay + 
					" BETWEEN '" + sprintStart.toString() + "' AND '" + date.toString() + "' " + 
					" GROUP BY " + DBSchemaModel.TaskId + ") as Hours_remaining_table " +
					" NATURAL JOIN " + DBSchemaModel.SprintTable + 
					" WHERE " + DBSchemaModel.SprintProjectId + "=" + projectId + " " +
					" AND " + DBSchemaModel.SprintId + "=" + sprintId +
				" AND " + 
				DBSchemaModel.SprintPBIMeasureDay + "='" + date.toString() + "'" + 
				" GROUP BY " + DBSchemaModel.TaskId + ") as Calculation_table GROUP BY " + DBSchemaModel.TaskId;
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Calculate scheduled performance index
	 * 
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @param sprintLength sprint length
	 * @param sprintStart start of sprint
	 * @param date date on which to perform calculation
	 * @return earned value indicator
	 */
	public BigDecimal calculateSchedulePerformanceIndex(int projectId, int sprintId, int sprintLength, java.sql.Date sprintStart, java.sql.Date date) {
		BigDecimal topPart = calculateTopSchedulePerformanceIndex(projectId, sprintId, sprintStart, date);
		BigDecimal bottomPart = calculateAllTaskRemainingWork(projectId, sprintStart);
			
		if (bottomPart.compareTo(BigDecimal.ZERO) == 0) {
			long dayLength = 86400000;
			long diff = (date.getTime() - sprintStart.getTime()) / dayLength;
			
			BigDecimal factor = new BigDecimal(diff);
			factor.divide(new BigDecimal(sprintLength), 3, RoundingMode.DOWN);
		
			return topPart.divide(bottomPart).multiply(factor);
		} else {
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * Calculate top part of scheduled performance index equation(without sprint length/days elapsed
	 * 
	 * @param projectId project id
	 * @param sprintId sprint id
	 * @param sprintStart start of sprint
	 * @param date date on which to perform calculation
	 * @return earned value indicator
	 */
	public BigDecimal calculateTopSchedulePerformanceIndex(int projectId, int sprintId, java.sql.Date sprintStart, java.sql.Date date) {
		ResultQuery<BigDecimal> q = new ResultQuery<BigDecimal>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				setResult(BigDecimal.ZERO);
				result.beforeFirst();
				while (result.next()) {
					BigDecimal res = result.getBigDecimal(1);
					if (res != null) {
						setResult(res);
					}
				}
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(BigDecimal.ZERO);
				ex.printStackTrace();
			}
		};
		// calculate top part of ER equation (work spent until day) and work remaining at day, join
		// them together using natural join; then calculate the equation results from table
		// that has all the coefficients
		String query =
			"SELECT SUM(Hours_spent_until_d / (Hours_spent_until_d + Hours_remaining_d) * Hours_remaining_at_start) as SPI_top FROM " + 
			"(SELECT " + 
			DBSchemaModel.TaskId + ", " +
			"SUM(" + DBSchemaModel.SprintPBIHoursRemaining + ") as Hours_remaining_d" + ", " +
			"Hours_spent_until_d, Hours_remaining_at_start FROM " + DBSchemaModel.SprintPBITable + " NATURAL JOIN  (" +
				"SELECT " + 
				DBSchemaModel.TaskId + ", " + 
				"SUM(" + DBSchemaModel.HoursSpent + ") as Hours_spent_until_d FROM " + 
				DBSchemaModel.SprintPBITable + 
				" WHERE " + 
				DBSchemaModel.SprintPBIMeasureDay + 
				" BETWEEN '" + sprintStart.toString() + "' AND '" + date.toString() + "' " + 
				" GROUP BY " + DBSchemaModel.TaskId + ") as Hours_remaining_table " +
				" NATURAL JOIN " + "(" +
				"SELECT " + DBSchemaModel.TaskId + ", SUM(" + DBSchemaModel.SprintPBIHoursRemaining + ") as Hours_remaining_at_start" + 
				" FROM " + DBSchemaModel.SprintPBITable +
				" NATURAL JOIN " + DBSchemaModel.SprintTable +
				" WHERE " + DBSchemaModel.SprintPBIMeasureDay + "='" + sprintStart.toString() + "'" +
				" AND " + DBSchemaModel.SprintProjectId + "=" + projectId + " " +
				" AND " + DBSchemaModel.SprintId + "=" + sprintId +
			" GROUP BY " + DBSchemaModel.TaskId + ") as Calculation_table GROUP BY " + DBSchemaModel.TaskId + ") as Tmp_table";			
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Calculate scheduled performance index
	 * @param projectId project id
	 * @param date date on which to perform calculation
	 * @return earned value indicator
	 */
	public BigDecimal calculateCostPerformanceIndex(int projectId, java.sql.Date date, java.sql.Date init) {
		ResultQuery<BigDecimal> q = new ResultQuery<BigDecimal>(_connectionModel) {	
			@Override
			public void processResult(ResultSet result) throws SQLException {
				setResult(BigDecimal.ZERO);
				result.beforeFirst();
				while (result.next()) {
					BigDecimal res = result.getBigDecimal(1);
					if (res != null) {
						setResult(res);
					}
				}
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(BigDecimal.ZERO);
				ex.printStackTrace();
			}
		};
		// calculate top part of ER equation (work spent until day) and work remaining at day, join
		// them together using natural join; then calculate the equation results from table
		// that has all the coefficients
		String query = "";
		/*
			"(SELECT " + 
			DBSchemaModel.TaskId + ", " +
			"SUM(" + DBSchemaModel.SprintPBIHoursRemaining + ") as Hours_remaining_d" + ", " +
			"Hours_spent_until_d  FROM " + DBSchemaModel.SprintPBITable + 
			" NATURAL JOIN  (" +
				"SELECT " + 
				DBSchemaModel.TaskId + ", " + 
				"SUM(" + DBSchemaModel.HoursSpent + ") as Hours_spent_until_d FROM " + 
				DBSchemaModel.SprintPBITable + 
				" WHERE " + 
				DBSchemaModel.SprintPBIMeasureDay + " < '" + date.toString() + "' " + 
				" GROUP BY " + DBSchemaModel.TaskId + ") as Hours_remaining_table " +
			" NATURAL JOIN (" +
				"SELECT " + 
				DBSchemaModel.TaskId + ", " +
				"SUM(" + DBSchemaModel.SprintPBIHoursRemaining + ") as Hours_remaining_sum " +
				" FROM " +
				DBSchemaModel.SprintPBITable + 
				" NATURAL JOIN " + 
				DBSchemaModel.SprintTable + 
				" WHERE " +
				DBSchemaModel.ProjectId + "=" + projectId + " AND " +
				DBSchemaModel.SprintPBIMeasureDay + "='" + init.toString() + "'" +
				" GROUP BY " + DBSchemaModel.TaskId + ") as Hours_remaining_init_table " +
			" WHERE " + 
			DBSchemaModel.SprintPBIMeasureDay + "='" + date.toString() + "'" + 
			" GROUP BY " + DBSchemaModel.TaskId;
		*/
		q.queryResult(query);
		return q.getResult();
	}	
	
	/**
	 * Count all tasks that were completed in given sprint(excluding split/divided)
	 * @param sprintId sprint
	 * @param startDate start of sprint
	 * @param endDate end of sprint 
	 * @return number of tasks completed
	 */
	public int countTasksCompleted(int sprintId, java.sql.Date startDate, java.sql.Date endDate) {
		ResultQuery<Integer> q = new ResultQuery<Integer>(_connectionModel) {	
		@Override
		public void processResult(ResultSet result) throws SQLException {
			setResult(0);
			result.beforeFirst();
			while (result.next()) {
				setResult(result.getInt(1));
			}
		}
		@Override
		public void handleException(SQLException ex) {
			setResult(0);
			ex.printStackTrace();
		}
		};
		String query = "SELECT COUNT(" + DBSchemaModel.TaskId + ") FROM " + DBSchemaModel.TaskTable +
		" NATURAL JOIN " + DBSchemaModel.TaskStatusTable +
		" NATURAL JOIN " + DBSchemaModel.SprintPBITable + 
		" WHERE " + 
		DBSchemaModel.SprintId + "=" + sprintId + " AND " +
		DBSchemaModel.TaskStatusDesc + "='completed'" + " AND " +
		DBSchemaModel.TaskStatusDesc + "!='split/divided'" + " AND " +
		DBSchemaModel.TaskDate + " BETWEEN '" + startDate + "' AND '" + endDate + "'" + 
		" GROUP BY " + DBSchemaModel.TaskId; 		
		q.queryResult(query);		
		
		return q.getResult();		
	}
	
	/**
	 * Count all existing tasks in sprint (excludes split/divided tasks)
	 * @param sprintId sprint sprint
	 * @param startDate start of sprint
	 * @param endDate end of sprint
	 * @return task count
	 */
	public int countTasks(int sprintId, java.sql.Date startDate, java.sql.Date endDate) {
		ResultQuery<Integer> q = new ResultQuery<Integer>(_connectionModel) {	
		@Override
		public void processResult(ResultSet result) throws SQLException {
			setResult(0);
			result.beforeFirst();
			while (result.next()) {
				setResult(result.getInt(1));
			}
		}
		@Override
		public void handleException(SQLException ex) {
			setResult(0);
			ex.printStackTrace();
		}
		};
		String query = "SELECT COUNT(" + DBSchemaModel.TaskId + ") FROM " + DBSchemaModel.TaskTable +
		" NATURAL JOIN " + DBSchemaModel.TaskStatusTable +
		" NATURAL JOIN " + DBSchemaModel.SprintPBITable + 
		" WHERE " + 
		DBSchemaModel.SprintId + "=" + sprintId + " AND " +
		DBSchemaModel.TaskStatusDesc + "!='split/divided'" + " AND " +
		DBSchemaModel.TaskDate + " BETWEEN '" + startDate + "' AND '" + endDate + "'" +  
		" GROUP BY " + DBSchemaModel.TaskId; 		
		q.queryResult(query);				
		return q.getResult();		
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operations.MetricOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());	
}
