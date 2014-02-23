package com.iworkstation.imageserver.parameter;

public class LockStudyParameters {

	private String studyStorageGUID;
	private Boolean writeLock;
	private Boolean readLock;
	private String queueStudyStateEnum;
	private Boolean successful;
	private String failureReason;

	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	public Boolean getWriteLock() {
		return writeLock;
	}

	public void setWriteLock(Boolean writeLock) {
		this.writeLock = writeLock;
	}

	public Boolean getReadLock() {
		return readLock;
	}

	public void setReadLock(Boolean readLock) {
		this.readLock = readLock;
	}

	public String getQueueStudyStateEnum() {
		return queueStudyStateEnum;
	}

	public void setQueueStudyStateEnum(String queueStudyStateEnum) {
		this.queueStudyStateEnum = queueStudyStateEnum;
	}

	public Boolean getSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

}
