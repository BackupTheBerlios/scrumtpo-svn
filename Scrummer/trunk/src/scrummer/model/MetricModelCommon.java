package scrummer.model;

import java.math.BigDecimal;
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
     * Fecth measure with given id
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

	public SprintMeasurementRow getRow(int sprintId, int measureId, java.sql.Date datum) {
        ResultQuery<SprintMeasurementRow> q = new ResultQuery<SprintMeasurementRow>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new SprintMeasurementRow(result));
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(null);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, MetricOperation.ReleaseMeasure, ex.getMessage());
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.SprintMeasurementResultTable + " WHERE " +
        DBSchemaModel.SprintId + "=" + sprintId + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.SprintMeasurementResultDate + "=" + datum);
        return q.getResult();
	}
	
	public String getMeasurementResult(int sprintId, int measureId, java.sql.Date datum) {
        return getSprintMeasurementRow(sprintId, measureId, datum).MeasurementResult;
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
	 * Calculate work effectiveness indicator
	 * @param projectId project id
	 * @param d1 starting date
	 * @param d2 ending date
	 * @return work effectiveness
	 */
	public BigDecimal calculateWorkEffectiveness(int projectId, java.sql.Date d1, java.sql.Date d2) {
		BigDecimal allTaskSpent = calculateAllTaskSpentWork(projectId, d1, d2); 
		BigDecimal bd = BigDecimal.ZERO;
		if (allTaskSpent != BigDecimal.ZERO) {
			BigDecimal d1Remaining = calculateAllTaskRemainingWork(projectId, d1);
			BigDecimal d2Remaining = calculateAllTaskRemainingWork(projectId, d2);
			bd = d1Remaining.subtract(d2Remaining).divide(allTaskSpent);
		}
		_operation.operationSucceeded(
		     DataOperation.Custom, 
		     MetricOperation.WorkEffectivenessCalculated, bd.toEngineeringString());
		return bd;
	}
	
	/**
	 * Calculate value of all completed tasks
	 * @param projectId project id
	 * @param date date on which to perform calculation
	 * @return earned value indicator
	 */
	public BigDecimal calculateEarnedValue(int projectId, java.sql.Date date) {
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
		// calculate top part of ER equation (work spent until day) and work remaining at day, join
		// them together using natural join; then calculate the equation results from table
		// that has all the coefficients
		String query =
			"SELECT " + DBSchemaModel.TaskId + ", " + 
			"(Hours_spent_until_d / (Hours_spent_until_d + Hours_remaining_d)) as Earned_value FROM " +
				"(SELECT " + 
				DBSchemaModel.TaskId + "; " +
				"SUM(" + DBSchemaModel.SprintPBIHoursRemaining + ") as Hours_remaining_d" + ", " +
				"Hours_spent_until_d  FROM " + DBSchemaModel.SprintPBITable + " NATURAL JOIN  (" +
					"SELECT " + 
						DBSchemaModel.TaskId + ", " + 
						"SUM(" + DBSchemaModel.HoursSpent + ") as Hours_spent_until_d FROM " + 
						DBSchemaModel.SprintPBITable + 
						" WHERE " + 
						DBSchemaModel.SprintPBIMeasureDay + " < '" + date.toString() + "' " + 
						" GROUP BY " + DBSchemaModel.TaskId + ") as Hours_remaining_table " +
				" WHERE " + 
				DBSchemaModel.SprintPBIMeasureDay + "='" + date.toString() + "'" + 
				" GROUP BY " + DBSchemaModel.TaskId + ")";
		q.queryResult(query);
		return q.getResult();
	}
	
	/**
	 * Calculate scheduled performance index
	 * @param projectId project id
	 * @param date date on which to perform calculation
	 * @return earned value indicator
	 */
	public BigDecimal calculateSchedulePerformanceIndex(int projectId, java.sql.Date date, java.sql.Date init) {
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
		// calculate top part of ER equation (work spent until day) and work remaining at day, join
		// them together using natural join; then calculate the equation results from table
		// that has all the coefficients
		String query =
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
		q.queryResult(query);
		return q.getResult();
	}
	
	// za earned value: select Task_id, SUM(Hours_remaining) as Hours_remaining_d, Hours_spent_until_d as Hours_remaining_d from Sprint_PBI natural join (select Task_id, SUM(Hours_spent) as Hours_spent_until_d from Sprint_PBI group by Task_id) as Hours_remaining_table group by Task_id;
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operations.MetricOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());	
}
