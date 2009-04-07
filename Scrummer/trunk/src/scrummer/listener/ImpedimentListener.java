package scrummer.listener;

import scrummer.enumerator.DataOperation;
import scrummer.enumerator.ImpedimentOperation;

public interface ImpedimentListener {
	
	/**
	 * Operation was successful
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message operation message
	 */
	void operationSucceeded(DataOperation type, ImpedimentOperation identifier, String message);
	
	/**
	 * Operation failed
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message failure message
	 */
	void operationFailed(DataOperation type, ImpedimentOperation identifier, String message);
}
