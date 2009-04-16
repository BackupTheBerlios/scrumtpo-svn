package scrummer.listener;

import scrummer.enumerator.AbsenceTypeOperation;
import scrummer.enumerator.DataOperation;

public interface AbsenceTypeListener {
	
	/**
	 * Operation was successful
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message operation message
	 */
	void operationSucceeded(DataOperation type, AbsenceTypeOperation identifier, String message);
	
	/**
	 * Operation failed
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message failure message
	 */
	void operationFailed(DataOperation type, AbsenceTypeOperation identifier, String message);
}
