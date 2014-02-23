package com.iworkstation.imageserver.parameter;

import java.util.Date;

public class PostponeWorkQueueParameters {
	private String workQueueGUID;
	private Date scheduledTime;
	private Date expirationTime;
	private Boolean updateWorkQueue;
	private String reason;

	public String getWorkQueueGUID() {
		return workQueueGUID;
	}

	public void setWorkQueueGUID(String workQueueGUID) {
		this.workQueueGUID = workQueueGUID;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Boolean getUpdateWorkQueue() {
		return updateWorkQueue;
	}

	public void setUpdateWorkQueue(Boolean updateWorkQueue) {
		this.updateWorkQueue = updateWorkQueue;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
