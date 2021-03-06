package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ReleaseOperation;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;
import sun.security.pkcs11.Secmod.DbMode;

public class ReleaseModelCommon {

	public ReleaseModelCommon(ConnectionModel connectionModel, Operations.ReleaseOperation operation) {
		_connectionModel = connectionModel;
		_operation = operation;
	}
	
	public static class ReleaseRow 
		extends DataRow {
        /**
         * Constructor
         *
         * @param result result from which to get data
         */
        public ReleaseRow(ResultSet result) {
            try {
                result.beforeFirst(); result.next();
                Id =
                    result.getInt(1);
                Description =
                    result.getString(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * Does key equal
         * @param taskId
         * @return true if row key equals this row
         */
        public boolean keyEquals(int releaseId) {
            if (releaseId == Id) {
                return true;
            } else {
                return false;
            }
        }
        public int Id;
        public String Description;
	}

	
	
	/**
	 * Fetch all release id's + their corresponding pbi descriptions
	 * @return object rows
	 */
	public Vector<ObjectRow> fetchReleaseData() {
		ResultQuery<Vector<scrummer.util.ObjectRow>> q = new ResultQuery<Vector<scrummer.util.ObjectRow>>(_connectionModel) {
			@Override
			public void processResult(ResultSet result) {
				Vector<ObjectRow> rows = ObjectRow.fetchRows(result);
				// move entire column of diferent pbis for same release into one joined cell
				Vector<ObjectRow> transformedRows = new Vector<ObjectRow>();
				Object id = null, description = null, pbis = null;
				if (rows.size() > 0) {
					for (int i = 0; i < rows.size(); i++) {
						ObjectRow current = rows.get(i);
						if (id == null) {
							id = current.get(0); description = current.get(1); pbis = current.get(2);
						} else {					
							Object cid = current.get(0), 
								   cpbis = current.get(2);
							if (cid.equals(id)) {
								// create a comma separated list of pbis
								pbis = pbis.toString() + ", " + cpbis;
							} else {
								ObjectRow newRow = new ObjectRow(3);
								newRow.set(0, id); newRow.set(1, description); newRow.set(2, pbis);
								transformedRows.add(newRow);
								id = cid; description = current.get(1); pbis = cpbis;
							}
						}					
					}
					ObjectRow newRow = new ObjectRow(3);
					newRow.set(0, id); newRow.set(1, description); newRow.set(2, pbis);
					transformedRows.add(newRow);
					id = null;
				}
				
				setResult(transformedRows);		
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(new Vector<scrummer.util.ObjectRow>());
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.FinalReleaseTable  + "." + DBSchemaModel.ReleaseId + ", " +
			DBSchemaModel.ReleaseDescription + ", " +
			DBSchemaModel.PBIDesc +
			" FROM "   + DBSchemaModel.FinalReleaseTable + 
			" NATURAL LEFT JOIN " + DBSchemaModel.ReleasePBITable + 
			" NATURAL LEFT JOIN " + DBSchemaModel.PBITable);
		return q.getResult();	
	}
	
	/**
	 * Add release
	 * @param releaseDescription description
	 * @return true if added, false otherwise
	 */
	public boolean addRelease(String releaseDescription) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.FinalReleaseTable +
            "(" +
            DBSchemaModel.ReleaseDescription +
            ")" +
            " VALUES (?)";
            st = conn.prepareStatement(query);
            st.setString(1, releaseDescription);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, ReleaseOperation.Release, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, ReleaseOperation.Release, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}

	/**
	 * Remove release
	 * @param releaseId release
	 * @return true if removed, false otherwise
	 */
	public boolean removeRelease(int releaseId) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Remove, ReleaseOperation.Release, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, ReleaseOperation.Release,
            ex.getMessage());
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.FinalReleaseTable +
        " WHERE " +
        DBSchemaModel.ReleaseId + "=" + releaseId
        );
        return q.getResult();
	}

	/**
	 * Add pbi to release
	 * @param releaseId release id
	 * @param pBIId pbi id
	 * @return true if removed, false otherwise
	 */
	public boolean addReleasePbi(int releaseId, int pBIId) {
        boolean ret = false;
        java.sql.Connection conn      = null;
        java.sql.PreparedStatement st = null;
        ResultSet res = null;
        try {
            conn = _connectionModel.getConnection();
            String query =
            "INSERT INTO " + DBSchemaModel.ReleasePBITable +
            "(" +
            DBSchemaModel.ReleaseId + ", " +
            DBSchemaModel.PBIId + 
            ")" +
            " VALUES (?, ?)";
            st = conn.prepareStatement(query);
            st.setInt(1, releaseId);
            st.setInt(2, pBIId);
            st.execute();
            _operation.operationSucceeded(DataOperation.Insert, ReleaseOperation.Release, "");
            ret = true;
        } catch (SQLException e) {
            _operation.operationFailed(DataOperation.Insert, ReleaseOperation.Release, e.getMessage());
            e.printStackTrace();
        } finally {
            res  = _connectionModel.close(res);
            st   = _connectionModel.close(st);
            conn = _connectionModel.close(conn);
        }
        return ret;
	}
	
	/**
	 * Remove release/pbi pair
	 * @param releaseId release id
	 * @param pBIId pbi id
	 * @return true if removed, false otherwise
	 */
	public boolean removeReleasePbi(int releaseId, int pBIId) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Remove, ReleaseOperation.Release, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, ReleaseOperation.Release,
            ex.getMessage());
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.ReleasePBITable +
        " WHERE " +
        DBSchemaModel.PBIId + "=" + pBIId + " AND " +
        DBSchemaModel.ReleaseId + "=" + releaseId
        );
        return q.getResult();
	}
	
	/**
	 * Remove all pbi's from Release_PBI table related to releaseId
	 * @param releaseId release
	 * @return true if removed, false otherwise
	 */
	public boolean removeAllReleasePbi(int releaseId) {
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Remove, ReleaseOperation.ReleasePBI, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, ReleaseOperation.ReleasePBI,
            ex.getMessage());
        }
        };
        q.query("DELETE FROM " + DBSchemaModel.ReleasePBITable +
        " WHERE " +        
        DBSchemaModel.ReleaseId + "=" + releaseId
        );
        return q.getResult();
	}

	/**
	 * Fetch id of release with description
	 * @param description description of release
	 * @return release id or -1 if none found
	 */
	public int getReleaseId(String description) {
		ResultQuery<Integer> q = new ResultQuery<Integer>(_connectionModel) {
			@Override
			public void processResult(ResultSet result) throws SQLException {
				setResult(-1);
				result.beforeFirst();
				while (result.next()) {
					setResult(result.getInt(1));
				}				
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(-1);
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.ReleaseId +
			" FROM " + DBSchemaModel.FinalReleaseTable + " WHERE " +
			DBSchemaModel.ReleaseDescription + "='" + description + "'");
		return q.getResult();
	}
	
	/**
	 * Fetch release row
	 * @param releaseId release
	 * @return data row
	 */
	public ReleaseRow getReleaseRow(int releaseId) {
        ResultQuery<ReleaseRow> q = new ResultQuery<ReleaseRow>(_connectionModel) {
        @Override
        public void processResult(ResultSet result) {
            setResult(new ReleaseRow(result));
            _operation.operationSucceeded(DataOperation.Remove, ReleaseOperation.Release, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(null);
            ex.printStackTrace();
            _operation.operationFailed(DataOperation.Remove, ReleaseOperation.Release,
            ex.getMessage());
        }
        };
        q.queryResult(
        "SELECT * FROM " + DBSchemaModel.FinalReleaseTable + " WHERE " +
        DBSchemaModel.ReleaseId + "=" + releaseId);
        return q.getResult();
    }

	public String getReleaseDescription(int releaseId) {
        return getReleaseRow(releaseId).Description;
	}
	 	
	/**
	 * Update release cell
	 * @param releaseId release
	 * @param column column
	 * @param value value
	 * @return true if cell updated, false otherwise
	 */
	public boolean updateReleaseCell(int releaseId, String column, String value) {
        ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {
        @Override
        public void process() {
            setResult(true);
            _operation.operationSucceeded(DataOperation.Update, ReleaseOperation.Release, "");
        }
        @Override
        public void handleException(SQLException ex) {
            setResult(false);
            _operation.operationFailed(DataOperation.Update, ReleaseOperation.Release, ex.getMessage());
            ex.printStackTrace();
        }
        };
        q.query("UPDATE " + DBSchemaModel.FinalReleaseTable +
        " SET " + column + "='" + value + "'" +
        " WHERE " +
        DBSchemaModel.ReleaseId + "=" + releaseId);
        return q.getResult();
	}
	
	public boolean setReleaseDescription(int releaseId, String value) {
        return updateReleaseCell(releaseId, DBSchemaModel.ReleaseDescription, value);
	}
	
	/**
	 * Fetch all pbi's for release
	 * @param releaseId release
	 * @return a list of pbis
	 */
	public Vector<Integer> fetchPbis(int releaseId) {
		ResultQuery<Vector<Integer>> q = new ResultQuery<Vector<Integer>>(_connectionModel) {
			@Override
			public void processResult(ResultSet result) throws SQLException {
				Vector<Integer> res = new Vector<Integer>();
				result.beforeFirst();
				while (result.next()) {
					res.add(result.getInt(1));					
				}				
				setResult(res);
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(new Vector<Integer>());
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.PBIId +
			" FROM " + DBSchemaModel.ReleasePBITable + " WHERE " +
			DBSchemaModel.ReleaseId + "=" + releaseId);
		return q.getResult();
	}

	/**
	 * Fetch all releases
	 * @return all releases
	 */
	public Vector<IdValue> fetchReleases() {
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel) {
			@Override
			public void processResult(ResultSet result) throws SQLException {
				setResult(IdValue.fetchValues(result));				
			}
			@Override
			public void handleException(SQLException ex) {
				setResult(new Vector<IdValue>());
				ex.printStackTrace();
			}
		};
		q.queryResult("SELECT " + DBSchemaModel.ReleaseId + ", " + DBSchemaModel.ReleaseDescription + " FROM " + DBSchemaModel.FinalReleaseTable);
		return q.getResult();
	}
	
	/// operation notifier
	private Operations.ReleaseOperation _operation;
	/// connection model
	private ConnectionModel _connectionModel;
	
}
