package com.iworkstation.imageserver.parameter;

import java.util.Date;

public class InsertWorkQueueParameters {
	private String serverPartitionGUID;
	private String studyStorageGUID;
	private String workQueueTypeEnum;
	private Date scheduledTime;
	private String seriesInstanceUid;
	private String sopInstanceUid;
	private boolean duplicate = false;
	private String extension;
	private String studyHistoryGUID;
	private String deviceGUID;
	private String workQueueData;
	private String workQueueGroupID;
	private String uidRelativePath;
	private String uidGroupID;

	public String getServerPartitionGUID() {
		return serverPartitionGUID;
	}

	public void setServerPartitionGUID(String serverPartitionGUID) {
		this.serverPartitionGUID = serverPartitionGUID;
	}

	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	public String getWorkQueueTypeEnum() {
		return workQueueTypeEnum;
	}

	public void setWorkQueueTypeEnum(String workQueueTypeEnum) {
		this.workQueueTypeEnum = workQueueTypeEnum;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getSeriesInstanceUid() {
		return seriesInstanceUid;
	}

	public void setSeriesInstanceUid(String seriesInstanceUid) {
		this.seriesInstanceUid = seriesInstanceUid;
	}

	public String getSopInstanceUid() {
		return sopInstanceUid;
	}

	public void setSopInstanceUid(String sopInstanceUid) {
		this.sopInstanceUid = sopInstanceUid;
	}

	public boolean isDuplicate() {
		return duplicate;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getStudyHistoryGUID() {
		return studyHistoryGUID;
	}

	public void setStudyHistoryGUID(String studyHistoryGUID) {
		this.studyHistoryGUID = studyHistoryGUID;
	}

	public String getDeviceGUID() {
		return deviceGUID;
	}

	public void setDeviceGUID(String deviceGUID) {
		this.deviceGUID = deviceGUID;
	}

	public String getWorkQueueData() {
		return workQueueData;
	}

	public void setWorkQueueData(String workQueueData) {
		this.workQueueData = workQueueData;
	}

	public String getWorkQueueGroupID() {
		return workQueueGroupID;
	}

	public void setWorkQueueGroupID(String workQueueGroupID) {
		this.workQueueGroupID = workQueueGroupID;
	}

	public String getUidRelativePath() {
		return uidRelativePath;
	}

	public void setUidRelativePath(String uidRelativePath) {
		this.uidRelativePath = uidRelativePath;
	}

	public String getUidGroupID() {
		return uidGroupID;
	}

	public void setUidGroupID(String uidGroupID) {
		this.uidGroupID = uidGroupID;
	}
}
