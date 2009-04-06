package scrummer.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import scrummer.model.ConnectionModel;

/**
 * Query class makes writing queries more convenient
 */
public class Query {
	
	/**
	 * Constructor
	 * @param connectionModel connection model
	 */
	public Query(ConnectionModel connectionModel)
	{
		_connectionModel = connectionModel;
	}

	public void dispose()
	{
		if (_resultSet != null)
		{
			_connectionModel.close(_resultSet);
		}
		if (_statement != null)
		{
			_connectionModel.close(_statement);
		}
		if (_connection != null)
		{
			_connectionModel.close(_connection);
		}
	}
	
	/**
	 * Process is called just after processResult and is last thing that gets
	 * called before disposal
	 */
	public void process() {}
	
	/**
	 * Process query results
	 * 
	 * @param result query results
	 */
	public void processResult(ResultSet result) throws java.sql.SQLException {}
	
	/**
	 * Handle thrown exception
	 * 
	 * @param ex exception
	 */
	public void handleException(SQLException ex) {}
	
	/**
	 * Query database
	 * 
	 * @param query database query
	 * @return result set
	 */
	public void queryResult(String query)
	{
		// System.out.println(query);
		try
		{
			// fetch connection
			_connection = _connectionModel.getConnection();
			
			_statement = _connection.createStatement();
			_resultSet = _statement.executeQuery(query);
			processResult(_resultSet);
		}
		catch (SQLException ex)
		{
			handleException(ex);
		}
		finally
		{
			dispose();
		}
	}
	
	/**
	 * Query database
	 * 
	 * @param query database query
	 * @return result set
	 */
	public void query(String query)
	{
		try
		{
			// fetch connection
			_connection = _connectionModel.getConnection();
			// create statement
			_statement = _connection.createStatement();
			// execute it
			_statement.execute(query);
			// final call
			process();
		}
		catch (SQLException ex)
		{
			handleException(ex);
		}
		finally
		{
			dispose();
		}
	}
	
	private ResultSet	      _resultSet         = null;
	private Statement 		  _statement         = null;
	private Connection 		  _connection        = null;
	private ConnectionModel   _connectionModel   = null;
}
