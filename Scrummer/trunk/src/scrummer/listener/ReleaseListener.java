package scrummer.listener;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ReleaseOperation;

public interface ReleaseListener {
	/**
	 * Operation was successful
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message operation message
	 */
	void operationSucceeded(DataOperation type, ReleaseOperation identifier, String message);
	
	/**
	 * Operation failed
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message failure message
	 */
	void operationFailed(DataOperation type, ReleaseOperation identifier, String message);
}
