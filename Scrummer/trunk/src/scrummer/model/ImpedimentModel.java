package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;
import scrummer.model.swing.ImpedimentTableModel;
import scrummer.model.swing.ProductBacklogTableModel;
import scrummer.util.Operation;

/**
 * Impediment model
 *
 * Takes care of adding, removing and modifying impediments.
 * 
 * This class works regardless of project state(opened, closed).
 */
public class ImpedimentModel 
{
	/**
	 * Constructor
	 * 
	 * @param connectionModel connection model
	 */
	public ImpedimentModel(ConnectionModel connectionModel)
	{
		if (connectionModel == null)
		{
			throw new NullPointerException("Cannot use null connection model!");
		}
		/// connection model
		_connectionModel = connectionModel;
		_impedimentTableModel = new ImpedimentTableModel(connectionModel);
	}
	
	/**
	 * Add impediment
	 * @param team employee's team
	 * @param sprint sprint when impediment occurred
	 * @param employee employee who experienced impediment
	 * @param task task which has to do with impediment
	 * @param desc description of impediment
	 * @param type impediment type
	 * @param status impediment status
	 * @param start date when impediment started
	 * @param end date when impediment was resolved
	 * @param age number of days when impediment was active
	 */
	public void add(int team, int sprint, int employee, int task, String desc, String type, String status, String start, String end, int age)
	{
		java.sql.Connection conn      = null;
		java.sql.PreparedStatement st = null;
		ResultSet res = null;
		try {
			 conn = _connectionModel.getConnection();
			 String query =
				"INSERT INTO Impediment " +
			 	"(Team_id, Sprint_id, Employee_id, Task_id, Impediment_description, Impediment_type, Impediment_status, Impediment_start, Impediment_end, Impediment_age) " +
			 	"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 st = conn.prepareStatement(query);
			 st.setInt(1, team);
			 st.setInt(2,sprint);
			 st.setInt(3,employee);
			 st.setInt(4,task);
			 st.setString(5, desc);
			 st.setString(6, type);
			 st.setString(7, status);
			 st.setString(8, start);
			 st.setString(9, end);
			 st.setInt(10, age);
			 st.execute();
			 
			 _impedimentOp.operationSucceeded(DataOperation.Insert, ImpedimentOperation.Impediment, "");
		} catch (SQLException e) {
			_impedimentOp.operationFailed(DataOperation.Insert, ImpedimentOperation.Impediment, e.getMessage());
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
	 * Remove impediment by id
	 * @param id impediment id
	 */
	public void remove(int id)
	{
		throw new RuntimeException("Not yet implemented.");
	}
	
	/**
	 * Impediment table model
	 * 
	 * @return impediment table model
	 */
	public ImpedimentTableModel getImpedimentTableModel()
	{
		return _impedimentTableModel;
	}
	
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer table model
	private ImpedimentTableModel _impedimentTableModel;
	/// developer operation
	private Operation<ImpedimentOperation> _impedimentOp = new Operation<ImpedimentOperation>();
}
