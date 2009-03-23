package scrummer.model;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import com.mysql.jdbc.Connection;

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
	public ConnectionModel(LoggingModel logger)
	{
		_logger = logger;
		DriverManager.setLoginTimeout(5);
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
			_logger.severe("Could not connect to database!", e);
			message = e.getMessage();
			ret = null;
			// notify everyone that connection failed
			connectionFailed(message);
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
	 * Ne naredi nič in vrne null(za skladnost)
	 * 
	 * @param conn primerek povezave
	 * @return vedno vrne null
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
	private void connectionFailed(String reason)
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
	private String createConnectionString(String username, String password, String hostname, int port, String database)
	{
		String ret = 
			"jdbc:mysql://" + hostname + "/" + database + "?" +
			"user=" + username + "&password=" + password + "&" +
			"useUnicode=true&" +
			"characterEncoding=utf8&" +
			"characterSetResults=utf8";
		return ret;
	}
	
	/// listeners for connection events
	private Vector<ConnectionListener> _connectionListeners = new Vector<ConnectionListener>();
	/// user name
	private String _username;
	/// host name
	private String _hostname;
	/// "unprotected" password
	private String _password;
	/// port 
	private int _port;
	/// database name
	private String _database;
	/// last set connection string
	private String _lastConnectionString;
	/// synchronize listener acces
	private Semaphore _listenerSemaphore = new Semaphore(1);
	/// logger
	private LoggingModel _logger;
}
