package scrummer.listener;

import scrummer.enumerator.DataOperation;

/**
 * Operation listener should be notified of 
 * success or failure of operations in data layer(insertion, removal, update, ...)
 */
public interface OperationListener<Identifier> {

	/**
	 * Operation was successful
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message operation message
	 */
	void operationSucceeded(DataOperation type, Identifier identifier, String message);
	
	/**
	 * Operation failed
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message failure message
	 */
	void operationFailed(DataOperation type, Identifier identifier, String message);
	
}
