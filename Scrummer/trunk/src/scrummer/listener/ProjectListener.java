package scrummer.listener;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ProjectOperation;

/**
 * Listener for project management related events
 */
public interface ProjectListener {

	/**
	 * Operation was successful
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message operation message
	 */
	void operationSucceeded(DataOperation type, ProjectOperation identifier, String message);
	
	/**
	 * Operation failed
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message failure message
	 */
	void operationFailed(DataOperation type, ProjectOperation identifier, String message);
}
