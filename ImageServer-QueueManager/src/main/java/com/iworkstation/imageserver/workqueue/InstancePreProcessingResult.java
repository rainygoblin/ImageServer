package com.iworkstation.imageserver.workqueue;

public class InstancePreProcessingResult {
	private boolean autoReconciled;

	private boolean discardImage;

	private boolean modified;

	public boolean isAutoReconciled() {
		return autoReconciled;
	}

	public void setAutoReconciled(boolean autoReconciled) {
		this.autoReconciled = autoReconciled;
	}

	public boolean isDiscardImage() {
		return discardImage;
	}

	public void setDiscardImage(boolean discardImage) {
		this.discardImage = discardImage;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

}
