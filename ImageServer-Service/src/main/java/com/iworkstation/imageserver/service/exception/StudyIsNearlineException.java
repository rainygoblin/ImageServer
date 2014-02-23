package com.iworkstation.imageserver.service.exception;

public class StudyIsNearlineException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1808600270713424544L;
	private boolean restoreRequested;

	public StudyIsNearlineException(boolean restoreRequested) {
		super("Study is in Nearline state.");
		this.restoreRequested = restoreRequested;
	}

	public boolean isRestoreRequested() {
		return restoreRequested;
	}

}
