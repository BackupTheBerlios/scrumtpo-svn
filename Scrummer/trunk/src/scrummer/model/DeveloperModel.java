package scrummer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import scrummer.enumerator.DataOperation;
import scrummer.enumerator.DeveloperOperation;
import scrummer.listener.OperationListener;
import scrummer.model.swing.DeveloperTableModel;
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
		_developerModelCommon = new DeveloperModelCommon(_connectionModel, _operation);
		_developerTableModel = new DeveloperTableModel(connectionModel, _developerModelCommon, _operation);
		
	}
	
	/**
	 * Add developer
	 * @param name developer name
	 * @param surname developer surname
	 * @param address developer address
	 */
	public void add(String name, String surname, String address)
	{
		 _developerModelCommon.add(name, surname, address);
	}
	
	/**
	 * Developer table model
	 * 
	 * @return developer table model
	 */
	public DeveloperTableModel getDeveloperTableModel()
	{
		return _developerTableModel;
	}
	
	/**
	 * Add developer data change listener
	 * 
	 * @param listener listener to add
	 */
	public void addDeveloperListener(OperationListener<DeveloperOperation> listener)
	{
		_operation.addListener(listener);
	}
	
	/**
	 * Remove developer data change listener
	 * @param listener listener to remove
	 */
	public void removeDeveloperListener(OperationListener<DeveloperOperation> listener)
	{
		_operation.removeListener(listener);
	}
	
	/// common developer related functionality
	private DeveloperModelCommon _developerModelCommon;
	/// connection model
	private ConnectionModel _connectionModel;
	/// developer table model
	private DeveloperTableModel _developerTableModel;
	/// developer operation
	private Operation<DeveloperOperation> _operation = new Operation<DeveloperOperation>();
}
