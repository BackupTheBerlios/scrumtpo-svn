package scrummer.listener;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.TaskOperation;

public interface TaskListener {
	
	/**
	 * Operation was successful
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message operation message
	 */
	void operationSucceeded(DataOperation type, TaskOperation identifier, String message);
	
	/**
	 * Operation failed
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message failure message
	 */
	void operationFailed(DataOperation type, TaskOperation identifier, String message);
}
