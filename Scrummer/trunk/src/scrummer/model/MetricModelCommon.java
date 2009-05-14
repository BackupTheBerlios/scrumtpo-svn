package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import scrummer.Scrummer;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.MetricOperation;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * Metric model common
 * 
 * Deals with Measure, Sprint_measurement_result, PBI_measurement_result, Release_measurement_result
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
	public boolean addTaskMeasurement(int measureId, int taskId, java.sql.Date datum, String measurementResult) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.TaskMeasurementResultTable +
            "(" +
            DBSchemaModel.TaskMeasurementResultResult +
            ")" +
            " VALUES (?) " +
            " WHERE " +
            DBSchemaModel.MeasureId + "=" + measureId + "," +
            DBSchemaModel.TaskId + "=" + taskId + "," +
            DBSchemaModel.TaskMeasurementResultDatum + "=" + datum +
            ")";
            st = conn.prepareStatement(query);
            st.setString(1, measurementResult);
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
	public boolean addSprintMeasurement(int sprintId, int measureId, java.sql.Date datum, String measurementResult) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.SprintMeasurementResultTable +
            "(" +
            DBSchemaModel.SprintMeasurementResultResult +
            ")" +
            " VALUES (?) " +
            " WHERE " +
            DBSchemaModel.SprintId + "=" + sprintId + "," +
            DBSchemaModel.MeasureId + "=" + measureId + "," +
            DBSchemaModel.SprintMeasurementResultDate + "=" + datum +
            ")";
            st = conn.prepareStatement(query);
            st.setString(1, measurementResult);
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
	public boolean addReleaseMeasurement(int measureId, int releaseId, java.sql.Date datum, String measurementResult) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.ReleaseMeasurementResultTable +
            "(" +
            DBSchemaModel.ReleaseMeasurementResultResult +
            ")" +
            " VALUES (?) " +
            " WHERE " +
            DBSchemaModel.MeasureId + "=" + measureId + "," +
            DBSchemaModel.ReleaseId + "=" + releaseId + "," +
            DBSchemaModel.ReleaseMeasurementResultDate + "=" + datum +
            ")";
            st = conn.prepareStatement(query);
            st.setString(1, measurementResult);
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
        DBSchemaModel.ReleaseMeasurementResultDate + "=" + datum
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
        DBSchemaModel.TaskMeasurementResultDatum + "=" + datum
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
        DBSchemaModel.SprintMeasurementResultDate + "=" + datum
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
         "SET " + DBSchemaModel.MeasureName + "=" + measureName + "," +
         "SET " + DBSchemaModel.MeasureDescription + "=" + measureDescription +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId);
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
         "SET " + DBSchemaModel.TaskMeasurementResultResult + "=" + measurementResult +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.TaskId + "=" + taskId + " AND " +
        DBSchemaModel.TaskMeasurementResultDatum + "=" + datum);
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
         "SET " + DBSchemaModel.SprintMeasurementResultResult + "=" + measurementResult +
        " WHERE " +
        DBSchemaModel.SprintId + "=" + sprintId + " AND " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.SprintMeasurementResultDate + "=" + datum);
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
         "SET " + DBSchemaModel.SprintMeasurementResultResult + "=" + measurementResult +
        " WHERE " +
        DBSchemaModel.MeasureId + "=" + measureId + " AND " +
        DBSchemaModel.ReleaseId + "=" + releaseId + " AND " +
        DBSchemaModel.ReleaseMeasurementResultDate + "=" + datum);
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
            _operation.operationFailed(DataOperation.Remove, MetricOperation.Measure,
            i18n.tr(""));
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
            _operation.operationFailed(DataOperation.Remove, MetricOperation.SprintMeasure,
            i18n.tr(""));
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
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operations.MetricOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());	
}
