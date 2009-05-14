package scrummer.util;

import scrummer.enumerator.DataOperation;
import scrummer.listener.AbsenceTypeListener;
import scrummer.listener.AdminDaysListener;
import scrummer.listener.DeveloperListener;
import scrummer.listener.ImpedimentListener;
import scrummer.listener.MetricListener;
import scrummer.listener.ProductBacklogListener;
import scrummer.listener.ProjectListener;
import scrummer.listener.SprintBacklogListener;
import scrummer.listener.TaskListener;
import scrummer.listener.TaskStatusListener;
import scrummer.listener.TaskTypeListener;

public class Operations {

	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class ProjectOperation extends Operation<scrummer.enumerator.ProjectOperation, ProjectListener> {
		@Override
		protected void opFailure(ProjectListener listener, DataOperation type, scrummer.enumerator.ProjectOperation identifier, String message) {			
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(ProjectListener listener, DataOperation type, scrummer.enumerator.ProjectOperation identifier, String message) {
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class DeveloperOperation extends Operation<scrummer.enumerator.DeveloperOperation, DeveloperListener> {
		@Override
		protected void opFailure(DeveloperListener listener, DataOperation type, scrummer.enumerator.DeveloperOperation identifier, String message) {
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(DeveloperListener listener, DataOperation type, scrummer.enumerator.DeveloperOperation identifier, String message) {
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class ImpedimentOperation extends Operation<scrummer.enumerator.ImpedimentOperation, ImpedimentListener> {
		@Override
		protected void opFailure(ImpedimentListener listener, DataOperation type, scrummer.enumerator.ImpedimentOperation identifier, String message) {
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(ImpedimentListener listener, DataOperation type, scrummer.enumerator.ImpedimentOperation identifier, String message) {
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class AbsenceTypeOperation extends Operation<scrummer.enumerator.AbsenceTypeOperation, AbsenceTypeListener> {
		@Override
		protected void opFailure(AbsenceTypeListener listener, DataOperation type, scrummer.enumerator.AbsenceTypeOperation identifier, String message) {			
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(AbsenceTypeListener listener, DataOperation type, scrummer.enumerator.AbsenceTypeOperation identifier, String message) {
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class SprintBacklogOperation extends Operation<scrummer.enumerator.SprintBacklogOperation, SprintBacklogListener> {
		@Override
		protected void opFailure(SprintBacklogListener listener,  DataOperation type, scrummer.enumerator.SprintBacklogOperation identifier, String message) {			
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(SprintBacklogListener listener, DataOperation type, scrummer.enumerator.SprintBacklogOperation identifier, String message) {
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	/**
	 * Project operation handles project listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class ProductBacklogOperation extends Operation<scrummer.enumerator.ProductBacklogOperation, ProductBacklogListener> {
		@Override
		protected void opFailure(ProductBacklogListener listener, DataOperation type, scrummer.enumerator.ProductBacklogOperation identifier, String message) {
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(ProductBacklogListener listener, DataOperation type, scrummer.enumerator.ProductBacklogOperation identifier, String message) {			
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	/**
	 * Administrative days operation handles administrative days listeners
	 * @param <Identifier> identifier designates administrative days specific 
	 */
	public static class AdminDaysOperation extends Operation<scrummer.enumerator.AdminDaysOperation, AdminDaysListener> {
		@Override
		protected void opFailure(AdminDaysListener listener, DataOperation type, scrummer.enumerator.AdminDaysOperation identifier, String message) {			
			listener.operationFailed(type, identifier, message);
		}

		@Override
		protected void opSuccess(AdminDaysListener listener, DataOperation type, scrummer.enumerator.AdminDaysOperation identifier, String message) {
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	public static class TaskStatusOperation extends Operation<scrummer.enumerator.TaskStatusOperation, TaskStatusListener> {
		@Override
		protected void opFailure(TaskStatusListener listener, DataOperation type, scrummer.enumerator.TaskStatusOperation identifier, String message) {
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(TaskStatusListener listener, DataOperation type, scrummer.enumerator.TaskStatusOperation identifier, String message) {
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	public static class TaskTypeOperation extends Operation<scrummer.enumerator.TaskTypeOperation, TaskTypeListener> {
		@Override
		protected void opFailure(TaskTypeListener listener, DataOperation type, scrummer.enumerator.TaskTypeOperation identifier, String message) {
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(TaskTypeListener listener, DataOperation type, scrummer.enumerator.TaskTypeOperation identifier, String message) {
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	public static class TaskOperation extends Operation<scrummer.enumerator.TaskOperation, TaskListener> {
		@Override
		protected void opFailure(TaskListener listener, DataOperation type, scrummer.enumerator.TaskOperation identifier, String message) {			
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(TaskListener listener, DataOperation type, scrummer.enumerator.TaskOperation identifier, String message) {			
			listener.operationSucceeded(type, identifier, message);
		}
	}
	
	/**
	 * Metric operation handles metric related listeners
	 * @param <Identifier> identifier designates project specific 
	 */
	public static class MetricOperation extends Operation<scrummer.enumerator.MetricOperation, MetricListener> {
		@Override
		protected void opFailure(MetricListener listener, DataOperation type, scrummer.enumerator.MetricOperation identifier, String message) {
			listener.operationFailed(type, identifier, message);
		}
		@Override
		protected void opSuccess(MetricListener listener, DataOperation type, scrummer.enumerator.MetricOperation identifier, String message) {			
			listener.operationSucceeded(type, identifier, message);
		}
	}
}
