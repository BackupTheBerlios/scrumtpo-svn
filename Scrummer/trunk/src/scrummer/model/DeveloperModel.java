package scrummer.model;

import java.util.Vector;

import scrummer.enumerator.DeveloperOperation;
import scrummer.listener.OperationListener;
import scrummer.model.DBSchemaModel.IdValue;
import scrummer.model.swing.DeveloperTableModel;
import scrummer.model.swing.DeveloperTeamListModel;
import scrummer.model.swing.EmployeeListModel;
import scrummer.model.swing.TeamComboBoxModel;
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
		_developerModelCommon = 
			new DeveloperModelCommon(_connectionModel, _operation);
		_developerTableModel = 
			new DeveloperTableModel(connectionModel, _developerModelCommon, _operation);
		_employeeListModelA = 
			new EmployeeListModel(_developerModelCommon);
		_employeeListModelB = 
			new EmployeeListModel(_developerModelCommon);
		_teamComboBoxModel = 
			new TeamComboBoxModel(_developerModelCommon);
		_developerTeamListModel =
			new DeveloperTeamListModel(0, _developerModelCommon);
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
	 * Move selected indices from A to B
	 * @param indices indices of elements in A
	 */
	public void moveAtoB(int [] indices)
	{
		Vector<IdValue> moved = new Vector<IdValue>();
		for (int i = 0; i < indices.length; i++)
		{
			moved.add(_employeeListModelA.getValue(indices[i]));
		}
		
		for (int i = 0; i < moved.size(); i++)
		{
			_employeeListModelA.removeElement(moved.get(i));
		}
		for (int i = 0; i < moved.size(); i++)
		{
			_employeeListModelB.addValue(moved.get(i));
		}
	}
	
	/**
	 * Move selected indices from B to A
	 * @param indices indices of elements in B
	 */
	public void moveBtoA(int [] indices)
	{
		Vector<IdValue> moved = new Vector<IdValue>();
		for (int i = 0; i < indices.length; i++)
		{
			moved.add(_employeeListModelB.getValue(indices[i]));
		}
		
		for (int i = 0; i < moved.size(); i++)
		{
			_employeeListModelB.removeElement(moved.get(i));
		}
		for (int i = 0; i < moved.size(); i++)
		{
			_employeeListModelA.addValue(moved.get(i));
		}
	}	
	
	/**
	 * Add team with name from B employee list
	 * 
	 * @param name team name
	 */
	public void addTeamB(String name)
	{
		// assemble all id's
		Vector<Integer> employees = new Vector<Integer>();
		for (int i = 0; i < _employeeListModelB.size(); i++)
		{
			employees.add(_employeeListModelB.getValue(i).Id);
		}
		_developerModelCommon.addTeam(name, employees);
	}
	
	/**
	 * Set new team name
	 * 
	 * @param teamId team id
	 * @param newName name to set
	 */
	public void setTeamName(int teamId, String newName)
	{
		_developerModelCommon.setTeamName(teamId, newName);
	}
	
	/**
	 * Remove team by id
	 * 
	 * @param id team id
	 */
	public void removeTeam(int id)
	{
		_developerModelCommon.removeTeam(id);
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
	 * Employee list model - contains id's + employees 
	 * @return model
	 */
	public EmployeeListModel getEmployeeListModelA()
	{
		return _employeeListModelA;
	}
	
	/**
	 * Employee list model - contains id's + employees 
	 * @return model
	 */
	public EmployeeListModel getEmployeeListModelB()
	{
		return _employeeListModelB;
	}
	
	/**
	 * Fetch team combo box model(contains all team names)
	 * @return model
	 */
	public TeamComboBoxModel getTeamComboBoxModel()
	{
		return _teamComboBoxModel;
	}
	
	/**
	 * Fetch developer team list model
	 * @return model
	 */
	public DeveloperTeamListModel getDeveloperTeamListModel()
	{
		return _developerTeamListModel;
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
	/// employee list model
	private EmployeeListModel _employeeListModelA;
	/// employee list model - contains all employees except those in A
	private EmployeeListModel _employeeListModelB;
	/// developer team list model - team developers
	private DeveloperTeamListModel _developerTeamListModel;
	/// team combo box model
	private TeamComboBoxModel _teamComboBoxModel;
	/// developer operation
	private Operation<DeveloperOperation> _operation = new Operation<DeveloperOperation>();
}
