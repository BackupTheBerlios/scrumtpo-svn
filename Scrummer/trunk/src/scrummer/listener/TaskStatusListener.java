package scrummer.listener;

import scrummer.enumerator.TaskStatusOperation;
import scrummer.enumerator.DataOperation;

public interface TaskStatusListener {
	
	/**
	 * Operation was successful
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message operation message
	 */
	void operationSucceeded(DataOperation type, TaskStatusOperation identifier, String message);
	
	/**
	 * Operation failed
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message failure message
	 */
	void operationFailed(DataOperation type, TaskStatusOperation identifier, String message);
}
