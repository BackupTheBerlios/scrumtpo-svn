package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.util.ObjectRow;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ReleaseOperation;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

public class ReleaseModelCommon {

	public ReleaseModelCommon(ConnectionModel connectionModel, Operations.ReleaseOperation operation) {
		_connectionModel = connectionModel;
		_operation = operation;
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
								id = null;
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
			"SELECT " + DBSchemaModel.ReleaseId + ", " +
			DBSchemaModel.ReleaseDescription + ", " +
			DBSchemaModel.PBIDesc +
			" FROM "   + DBSchemaModel.FinalReleaseTable + 
			" NATURAL JOIN " + DBSchemaModel.ReleasePBITable + 
			" NATURAL JOIN " + DBSchemaModel.PBITable);
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
            " VALUES (?) )";
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
	 * Fetch id of release with description
	 * @param description description of release
	 * @return release id or -1 if none found
	 */
	public int getReleaseId(String description) {
		ResultQuery<Integer> q = new ResultQuery<Integer>(_connectionModel) {
			@Override
			public void processResult(ResultSet result) throws SQLException {
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
	
	/// operation notifier
	private Operations.ReleaseOperation _operation;
	/// connection model
	private ConnectionModel _connectionModel;
	
}
