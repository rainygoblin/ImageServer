package com.iworkstation.imageserver.parameter;

import java.util.Date;

public class UpdateWorkQueueParameters {
	private String workQueueGUID;
	private String studyStorageGUID;
	private String workQueueStatusEnum;
	private Date expirationTime;
	private Date scheduledTime;
	private int failureCount;
	private String processorID;
	private String failureDescription;
	private String queueStudyStateEnum;

	public String getWorkQueueGUID() {
		return workQueueGUID;
	}

	public void setWorkQueueGUID(String workQueueGUID) {
		this.workQueueGUID = workQueueGUID;
	}

	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	public String getWorkQueueStatusEnum() {
		return workQueueStatusEnum;
	}

	public void setWorkQueueStatusEnum(String workQueueStatusEnum) {
		this.workQueueStatusEnum = workQueueStatusEnum;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public String getProcessorID() {
		return processorID;
	}

	public void setProcessorID(String processorID) {
		this.processorID = processorID;
	}

	public String getFailureDescription() {
		return failureDescription;
	}

	public void setFailureDescription(String failureDescription) {
		this.failureDescription = failureDescription;
	}

	public String getQueueStudyStateEnum() {
		return queueStudyStateEnum;
	}

	public void setQueueStudyStateEnum(String queueStudyStateEnum) {
		this.queueStudyStateEnum = queueStudyStateEnum;
	}
}
