package scrummer.util;

import java.util.Vector;
import java.util.concurrent.Semaphore;
import scrummer.enumerator.DataOperation;
import scrummer.listener.OperationListener;

/**
 * This class makes notifications of listeners about failure or success of
 * operations like selecting, insertions from database or other data sources
 * easier.
 * 
 * To use it create wrapper methods for addListener and removeListener methods in some class, then
 * call operationSucceeded or operationFailed to notify every listener.
 *
 * @param <Identifier> identifier through which listeners and source identify common operation
 */
public class Operation<Identifier> {
	
	/**
	 * Operation was successful
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message operation message
	 */
	public void operationSucceeded(DataOperation type, Identifier identifier, String message)
	{
		try {
			_semaphore.acquire();
			for (int i = 0; i < _listeners.size(); i++)
			{
				_listeners.get(i).operationSucceeded(type, identifier, message);
			}
			_semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Operation failed
	 * 
	 * @param type operation type
	 * @param identifier operation identifier
	 * @param message failure message
	 */
	public void operationFailed(DataOperation type, Identifier identifier, String message)
	{
		try {
			_semaphore.acquire();
			for (int i = 0; i < _listeners.size(); i++)
			{
				_listeners.get(i).operationFailed(type, identifier, message);
			}
			_semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add listener to operation events
	 * 
	 * @param listener listener to add
	 */
	public void addListener(OperationListener<Identifier> listener)
	{
		try {
			_semaphore.acquire();
			if (!_listeners.contains(listener))
			{
				_listeners.add(listener);
			}
			_semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove listener from list
	 * 
	 * @param listener listener
	 */
	public void removeListener(OperationListener<Identifier> listener)
	{
		_listeners.remove(listener);
	}
	
	/// listeners
	private Vector<OperationListener<Identifier>> _listeners = new Vector<OperationListener<Identifier>>();
	/// lock access to listeners
	private Semaphore _semaphore = new Semaphore(1);
}
