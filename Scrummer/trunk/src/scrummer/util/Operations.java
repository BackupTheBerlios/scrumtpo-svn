package scrummer.util;

import scrummer.enumerator.DataOperation;
import scrummer.listener.DeveloperListener;
import scrummer.listener.ImpedimentListener;
import scrummer.listener.ProductBacklogListener;
import scrummer.listener.ProjectListener;
import scrummer.listener.SprintBacklogListener;

public class Operations {

	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class ProjectOperation extends Operation<scrummer.enumerator.ProjectOperation, ProjectListener>
	{
		@Override
		protected void opFailure(DataOperation type, scrummer.enumerator.ProjectOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				ProjectListener listener = _listeners.get(i);
				listener.operationSucceeded(type, identifier, message);
			}
		}

		@Override
		protected void opSuccess(DataOperation type, scrummer.enumerator.ProjectOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				ProjectListener listener = _listeners.get(i);
				listener.operationFailed(type, identifier, message);
			}
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class DeveloperOperation extends Operation<scrummer.enumerator.DeveloperOperation, DeveloperListener>
	{
		@Override
		protected void opFailure(DataOperation type, scrummer.enumerator.DeveloperOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				DeveloperListener listener = _listeners.get(i);
				listener.operationSucceeded(type, identifier, message);
			}
		}

		@Override
		protected void opSuccess(DataOperation type, scrummer.enumerator.DeveloperOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				DeveloperListener listener = _listeners.get(i);
				listener.operationFailed(type, identifier, message);
			}
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class ImpedimentOperation extends Operation<scrummer.enumerator.ImpedimentOperation, ImpedimentListener>
	{
		@Override
		protected void opFailure(DataOperation type, scrummer.enumerator.ImpedimentOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				ImpedimentListener listener = _listeners.get(i);
				listener.operationSucceeded(type, identifier, message);
			}
		}

		@Override
		protected void opSuccess(DataOperation type, scrummer.enumerator.ImpedimentOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				ImpedimentListener listener = _listeners.get(i);
				listener.operationFailed(type, identifier, message);
			}
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class SprintBacklogOperation extends Operation<scrummer.enumerator.SprintBacklogOperation, SprintBacklogListener>
	{
		@Override
		protected void opFailure(DataOperation type, scrummer.enumerator.SprintBacklogOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				SprintBacklogListener listener = _listeners.get(i);
				listener.operationSucceeded(type, identifier, message);
			}
		}

		@Override
		protected void opSuccess(DataOperation type, scrummer.enumerator.SprintBacklogOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				SprintBacklogListener listener = _listeners.get(i);
				listener.operationFailed(type, identifier, message);
			}
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class ProductBacklogOperation extends Operation<scrummer.enumerator.ProductBacklogOperation, ProductBacklogListener>
	{
		@Override
		protected void opFailure(DataOperation type, scrummer.enumerator.ProductBacklogOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				ProductBacklogListener listener = _listeners.get(i);
				listener.operationSucceeded(type, identifier, message);
			}
		}

		@Override
		protected void opSuccess(DataOperation type, scrummer.enumerator.ProductBacklogOperation identifier, String message) {
			for (int i = 0; i < _listeners.size(); i++)
			{
				ProductBacklogListener listener = _listeners.get(i);
				listener.operationFailed(type, identifier, message);
			}
		}
	}
}
