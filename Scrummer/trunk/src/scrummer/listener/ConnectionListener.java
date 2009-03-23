package scrummer.listener;

/**
 * Listener for database connection related events 
 */
public interface ConnectionListener {

	/**
	 * Connecting to database failed for some reason
	 * @param reason failure reason
	 */
	void connectionFailed(String reason);
	
}
