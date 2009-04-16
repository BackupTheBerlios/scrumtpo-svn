package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import scrummer.Scrummer;
import scrummer.enumerator.AbsenceTypeOperation;
import scrummer.enumerator.DataOperation;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.util.ObjectRow;
import scrummer.util.Operations;
import scrummer.util.ResultQuery;

/**
 * This model features common absence type related functionality
 */
public class AbsenceTypeModelCommon 
{	
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public AbsenceTypeModelCommon(ConnectionModel connectionModel, Operations.AbsenceTypeOperation operation)
	{
		_connectionModel = connectionModel;
		_operation 		 = operation;
	}
	
	/**
	 * Add absence type
	 * @param description absence type description */
	public void add(String description)
	{
		 java.sql.Connection conn      = null;
         java.sql.PreparedStatement st = null;
         ResultSet res = null;
         try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO " + DBSchemaModel.AbsenceTypeTable +
			 	"(Absence_type_description) " +
			 	"VALUES (?)";
			 st = conn.prepareStatement(query);
			 st.setString(1, description);
			 st.execute();
			 
			 _operation.operationSucceeded(DataOperation.Insert, AbsenceTypeOperation.AbsenceType, "");
		} catch (SQLException e) {
			_operation.operationFailed(DataOperation.Insert, AbsenceTypeOperation.AbsenceType, e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			res  = _connectionModel.close(res);
			st   = _connectionModel.close(st);
			conn = _connectionModel.close(conn);
		}
	}
	
	/**
	 * Fetch absence types and return full descriptions + ids
	 * 
	 * @return identified absencetypes
	 */
	public Vector<IdValue> fetchAbsenceTypeNames()
	{
		ResultQuery<Vector<IdValue>> q = new ResultQuery<Vector<IdValue>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) 
			{
				Vector<IdValue> res = new Vector<IdValue>();
				try {
					result.beforeFirst();
					while (result.next())
					{
						res.add(new IdValue(result.getInt(1), result.getString(2)));
					}
					setResult(res);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void handleException(SQLException ex) 
			{
				ex.printStackTrace();
			}
		};
		q.queryResult(
			"SELECT " + DBSchemaModel.AbsenceTypeId + ", " +
			DBSchemaModel.AbsenceTypeDesc +
			" FROM "   + DBSchemaModel.AbsenceTypeTable);
		if (q.getResult() == null)
		{
			return new Vector<IdValue>();
		}
		else
		{
			return q.getResult();
		}
	}
	
	/**
	 * Fetch entire absence type table
	 * 
	 * @return all rows
	 */
	public Vector<ObjectRow> fetchAbsenceTypeTable()
	{
		ResultQuery<Vector<ObjectRow>> q = new ResultQuery<Vector<ObjectRow>>(_connectionModel)
		{
			@Override
			public void processResult(ResultSet result) {
				setResult(ObjectRow.fetchRows(result)); 
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
			}
		};
		q.queryResult("SELECT * FROM " + DBSchemaModel.AbsenceTypeTable);
		if (q.getResult() == null)
		{
			return new Vector<ObjectRow>();
		}
		else
		{
			return q.getResult();
		}
	}
	
	/**
	 * Remove absence type by id
	 * 
	 * @param id absence type id
	 * @return true if absence type was removed, false otherwise
	 */
	public boolean removeAbsenceType(String id)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{	
			@Override
			public void process() {
				setResult(true);
			}
			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
	        	_operation.operationFailed(DataOperation.Remove, AbsenceTypeOperation.AbsenceType, 
	        		i18n.tr("Could not remove absence type."));
			}
		};
		q.query("DELETE FROM " + DBSchemaModel.AbsenceTypeTable + 
				" WHERE " + DBSchemaModel.AbsenceTypeId + "='" + id + "'");
		if (q.getResult() == null)
		{
			return false;
		}
		else
		{
			return q.getResult();
		}
	}
	
	/**
	 * Set absence type description
	 * 
	 * @param id absence type id
	 * @param name new absence type description
	 */
	public boolean setAbsenceTypeDesc(String value, String id, String name)
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, AbsenceTypeOperation.AbsenceType, "");
				setResult(true);
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, AbsenceTypeOperation.AbsenceType, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.AbsenceTypeTable + " " +
			"SET " + DBSchemaModel.AbsenceTypeDesc + "='" + value + "' " +
			"WHERE " + DBSchemaModel.AbsenceTypeId+ "='" + id + "'");
		if (q.getResult() == null)
		{
			return false;
		}
		else
		{
			return q.getResult();
		}
	}
	
	public void setNewDesc(int id, String desc) 
	{
		ResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel)
		{
			@Override
			public void process() {
				_operation.operationSucceeded(DataOperation.Update, AbsenceTypeOperation.AbsenceType, "");
			}

			@Override
			public void handleException(SQLException ex) {
				ex.printStackTrace();
				_operation.operationFailed(DataOperation.Update, AbsenceTypeOperation.AbsenceType, ex.getMessage());
			}
			
		};
		q.query(
			"UPDATE " + DBSchemaModel.AbsenceTypeTable + " " +
			"SET " + DBSchemaModel.AbsenceTypeDesc + "='" + desc + "' " +
			"WHERE " + DBSchemaModel.AbsenceTypeId+ "='" + id + "'");
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer data operation notifier
	private Operations.AbsenceTypeOperation _operation;
	/// translation class field
	private org.xnap.commons.i18n.I18n i18n = Scrummer.getI18n(getClass());
}
