package scrummer.model;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import scrummer.listener.ConnectionListener;

/** 
 * Handles connection related data
 * 
 * Handles data required for connection. Also handles connection loss.
 */
public class ConnectionModel {

	/**
	 * Default constructor
	 */
	public ConnectionModel(LoggingModel logger, PropertyModel properties)
	{
		
		_logger = logger;
		_properties = properties;
		_connectionless = new Boolean(_properties.getProperty("application.debug.ignoreConnection"));
		// DriverManager.setLoginTimeout(25);
	}
	
	/**
	 * Connect to MySQL server
	 * @return connection instance
	 * @throws SQLException in case connection failed
	 */
	public java.sql.Connection getConnection() throws SQLException
	{
		_lastConnectionString = createConnectionString(_username, _password, _hostname, _port, _database);
		
		String message = "";
		java.sql.Connection ret;
		try
		{
			ret = DriverManager.getConnection(_lastConnectionString); 
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			_logger.severe("Could not connect to database!", e);
			message = e.getMessage();
			ret = null;
			if (!_connectionless)
			{
				// notify everyone that connection failed
				connectionFailed(message);
			}
			throw e;
		}
		return ret; 
	}
	
	/**
	 * Set user name
	 */
	public void setUsername(String value)
	{
		_username = value;
	}
	
	/**
	 * Set server hostname
	 */
	public void setHostname(String value)
	{
		_hostname = value;
	}
	
	/**
	 * Set password
	 */
	public void setPassword(String value)
	{
		_password = value;
	}
	
	/**
	 * Set port number
	 */
	public void setPort(int value)
	{
		_port = value;
	}
	
	/**
	 * Set database name
	 */
	public void setDatabase(String value)
	{
		_database = value;
	}
	
	/**
	 * @return whether model ignores connection failures
	 */
	public boolean getConnectionless()
	{
		return _connectionless;
	}
	
	/**
	 * Make model ignore connection failures
	 */
	public void setConnectionless(boolean value)
	{
		_connectionless = value;	
	}
	
	/**
	 * Close result set
	 * 
	 * @param statement prepared statement
	 * @return always returns null
	 */
	public java.sql.ResultSet close(java.sql.ResultSet resultSet)
	{
		if (resultSet != null)
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException ex)
			{
				logSqlException(ex);
			}
		}
		return null;
	}
	
	/**
	 * close statement
	 * 
	 * @param statement prepared statement
	 * @return always returns null
	 */
	public java.sql.Statement close(java.sql.Statement statement)
	{
		if (statement != null)
		{
			try
			{
				statement.close();
			}
			catch (SQLException ex)
			{
				logSqlException(ex);
			}
		}
		return null;
	}
	
	/**
	 * Close statement
	 * 
	 * @param statement prepared statement
	 * @return always returns null
	 */
	public java.sql.PreparedStatement close(java.sql.PreparedStatement statement)
	{
		if (statement != null)
		{
			try
			{
				statement.close();
			}
			catch (SQLException ex)
			{
				logSqlException(ex);
			}
		}
		return null;
	}
	
	/**
	 * Close connection
	 * 
	 * @param conn connection
	 * @return always null
	 */
	public java.sql.Connection close(java.sql.Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException ex)
			{
				logSqlException(ex);
			}
		}
		return null;
	}

	/**
	 * Zabeleži razne informacije kadar pade sql izjema
	 * @param ex izjema, ki je padla
	 */
	public void logSqlException(SQLException ex)
	{
		_logger.severe("Izjema pri operaciji v zvezi s Sql-om", ex);
		_logger.severe("Sporočilo:" + ex.getMessage());
		_logger.severe("Stanje:" + ex.getSQLState());
		_logger.severe("Sql napaka:" + ex.getErrorCode());
	}

	
	/**
	 * Add listener to connection related events
	 * @param listener listener
	 */
	public void addConnectionListener(ConnectionListener listener)
	{
		_listenerSemaphore.acquireUninterruptibly();
		if (!_connectionListeners.contains(listener))
		{
			_connectionListeners.add(listener);
		}
		_listenerSemaphore.release();
	}
	
	/**
	 * Remove listener to connection related events
	 * @param listener listener
	 */
	public void removeConnectionListener(ConnectionListener listener)
	{
		_listenerSemaphore.acquireUninterruptibly();
		_connectionListeners.remove(listener);
		_listenerSemaphore.release();
	}
	
	/**
	 * Notify listeners of connection failure
	 * @param reason reason of failure
	 */
	private static void connectionFailed(String reason)
	{
		_listenerSemaphore.acquireUninterruptibly();
		for (int i = 0; i < _connectionListeners.size(); i++)
		{
			_connectionListeners.get(i).connectionFailed(reason);
		}
		_listenerSemaphore.release();
	}
	
	/**
	 * Create connection string
	 * 
	 * @param username database username
	 * @param password user password
	 * @param hostname server hostname
	 * @param port server database port
	 * @param database database name
	 * @return create database string
	 */
	private static String createConnectionString(String username, String password, String hostname, int port, String database)
	{
		String ret = 
			"jdbc:mysql://" + hostname + ":" + Integer.toString(port) + "/" + database + "?" +
			"user=" + username + "&password=" + password + "&" +
			"useUnicode=true&" +
			"characterEncoding=utf8&" +
			"characterSetResults=utf8";
		return ret;
	}
	
	/// listeners for connection events
	private static Vector<ConnectionListener> _connectionListeners = new Vector<ConnectionListener>();
	/// user name
	private static String _username;
	/// host name
	private static String _hostname;
	/// "unprotected" password
	private static String _password;
	/// port 
	private static int _port;
	/// database name
	private static String _database;
	/// last set connection string
	private static String _lastConnectionString;
	/// synchronize listener acces
	private static Semaphore _listenerSemaphore = new Semaphore(1);
	/// in case this field is set, connection failures are not reported
	private static boolean _connectionless = false;
	/// logger
	private static LoggingModel _logger;
	/// property model
	private PropertyModel _properties;
}
