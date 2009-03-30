package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.util.Operation;

/**
 * Developer model
 *
 * Takes care of adding, removing and modifying developers.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class DeveloperModel {

	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public DeveloperModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
	}
	
	/**
	 * Add developer
	 * @param name developer name
	 * @param surname developer surname
	 * @param address developer address
	 */
	public void add(String name, String surname, String address)
	{
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 st = conn.prepareStatement("INSERT INTO Employee VALUES ({1}, {2}, {3})");
			 st.setString(1, name);
			 st.setString(2, surname);
			 st.setString(3, address);
			 res = st.executeQuery();
			 _developerOp.operationSucceeded(DataOperation.Insert, DeveloperOperation.Developer, "");
			 _developerOp.operationSucceeded(DataOperation.Insert, DeveloperOperation.Developer, "");
		} catch (SQLException e) {
			_developerOp.operationFailed(DataOperation.Insert, DeveloperOperation.Developer, e.getMessage());
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
	 * Remove developer by id
	 * @param id developer id
	 */
	public void remove(int id)
	{
		throw new RuntimeException("Not yet implemented.");
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer operation
	private Operation<DeveloperOperation> _developerOp = new Operation<DeveloperOperation>();
}
