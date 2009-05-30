package scrummer.model;

import scrummer.listener.ReleaseListener;
import scrummer.model.swing.ReleaseTableModel;
import scrummer.util.Operations;

/**
 * Release model
 */
public class ReleaseModel {

	public ReleaseModel(ConnectionModel connectionModel) {
		_releaseModelCommon =
			new ReleaseModelCommon(connectionModel, _operation);
		_releaseTableModel = 
			new ReleaseTableModel(_releaseModelCommon);
	}
	
	/**
	 * Add release
	 * @param releaseDescription description
	 */
	public void addRelease(String description) {
        if (_releaseModelCommon.addRelease(description)) {
        	_releaseTableModel.refresh();
        }
	}
	
	/**
	 * Fetch release id from description
	 * @param description release description
	 * @return id of release if it exists
	 */
	public int getReleaseId(String description) {
		return _releaseModelCommon.getReleaseId(description);
	}
	
	/**
	 * Remove release
	 * @param releaseId release
	 */
	public void removeRelease(int releaseId) {
		if (_releaseModelCommon.removeRelease(releaseId)) {
			_releaseTableModel.refresh();
		}
	}
	
	/**
	 * Add pbi to release
	 * @param pBIId pbi id
	 * @param releaseId release id
	 */
	public void addReleasePbi(int releaseId, int pBIId) {
		if (_releaseModelCommon.addReleasePbi(releaseId, pBIId)) {
			_releaseTableModel.refresh();
		}
	}
	
	/**
	 * Remove all release-id's pbi's
	 * @param releaseId relesae id
	 */
	public void removeAllReleasePbi(int releaseId) {
		if (_releaseModelCommon.removeAllReleasePbi(releaseId)) {
		}
	}
	
	/**
	 * Remove pbi from release pair
	 * @param pBIId pbi 
	 * @param releaseId release
	 */
	public void removeReleasePbi(int releaseId, int pBIId) {
		if (_releaseModelCommon.removeReleasePbi(releaseId, pBIId)) {
			_releaseTableModel.refresh();
		}
	}
	
	/**
	 * Add listener for release events
	 * @param listener listener
	 */
	public void addListener(ReleaseListener listener) {
		_operation.addListener(listener);
	}
	
	/**
	 * Remove listener from release related events
	 * @param listener listener
	 */
	public void removeListener(ReleaseListener listener) {
		_operation.removeListener(listener);
	}
	
	public ReleaseTableModel getReleaseTableModel() {
		return _releaseTableModel;
	}
	
	/// release table model
	private ReleaseTableModel _releaseTableModel;
	/// operation notifier
	private Operations.ReleaseOperation _operation = new Operations.ReleaseOperation();
	/// release model
	private ReleaseModelCommon _releaseModelCommon;
	
}
