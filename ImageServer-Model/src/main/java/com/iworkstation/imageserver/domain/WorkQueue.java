package com.iworkstation.imageserver.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.WorkQueuePriorityEnum;
import com.iworkstation.imageserver.enumeration.WorkQueueStatusEnum;
import com.iworkstation.imageserver.enumeration.WorkQueueTypeEnum;

@Entity
@Table(name = "WorkQueue")
@NamedNativeQueries(value = {
		@NamedNativeQuery(name = "InsertWorkQueue", query = "{call InsertWorkQueue(:StudyStorageGUID,:ServerPartitionGUID,:WorkQueueTypeEnum,:ScheduledTime,:DeviceGUID,:StudyHistoryGUID,:Data,:SeriesInstanceUid,:SopInstanceUid,:Duplicate,:Extension,:WorkQueueGroupID,	:UidGroupID,:UidRelativePath)}", resultClass = WorkQueue.class),
		@NamedNativeQuery(name = "QueryWorkQueue", query = "{call QueryWorkQueue(:ProcessorID,:WorkQueuePriorityEnum,:MemoryLimited)}", resultClass = WorkQueue.class) })
public class WorkQueue {
	private String guid;
	private String serverPartitionGUID;
	private String studyStorageGUID;
	private String deviceGUID;
	private String studyHistoryGUID;
	private WorkQueueTypeEnum workQueueTypeEnum;
	private WorkQueueStatusEnum workQueueStatusEnum;
	private WorkQueuePriorityEnum workQueuePriorityEnum;
	private String processorID;
	private String groupID;
	private Date expirationTime;
	private Date scheduledTime;
	private Date insertTime;
	private Date lastUpdatedTime;
	private short failureCount;
	private String failureDescription;
	private String data;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(columnDefinition = "uniqueidentifier")
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name = "ServerPartitionGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getServerPartitionGUID() {
		return serverPartitionGUID;
	}

	public void setServerPartitionGUID(String serverPartitionGUID) {
		this.serverPartitionGUID = serverPartitionGUID;
	}

	@Column(name = "StudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	@Column(name = "DeviceGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getDeviceGUID() {
		return deviceGUID;
	}

	public void setDeviceGUID(String deviceGUID) {
		this.deviceGUID = deviceGUID;
	}

	@Column(name = "StudyHistoryGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyHistoryGUID() {
		return studyHistoryGUID;
	}

	public void setStudyHistoryGUID(String studyHistoryGUID) {
		this.studyHistoryGUID = studyHistoryGUID;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "WorkQueueTypeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public WorkQueueTypeEnum getWorkQueueTypeEnum() {
		return workQueueTypeEnum;
	}

	public void setWorkQueueTypeEnum(WorkQueueTypeEnum workQueueTypeEnum) {
		this.workQueueTypeEnum = workQueueTypeEnum;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "WorkQueueStatusEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public WorkQueueStatusEnum getWorkQueueStatusEnum() {
		return workQueueStatusEnum;
	}

	public void setWorkQueueStatusEnum(WorkQueueStatusEnum workQueueStatusEnum) {
		this.workQueueStatusEnum = workQueueStatusEnum;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "WorkQueuePriorityEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public WorkQueuePriorityEnum getWorkQueuePriorityEnum() {
		return workQueuePriorityEnum;
	}

	public void setWorkQueuePriorityEnum(
			WorkQueuePriorityEnum workQueuePriorityEnum) {
		this.workQueuePriorityEnum = workQueuePriorityEnum;
	}

	@Column(name = "ProcessorID", columnDefinition = "varchar(128)", nullable = true)
	public String getProcessorID() {
		return processorID;
	}

	public void setProcessorID(String processorID) {
		this.processorID = processorID;
	}

	@Column(name = "GroupID", columnDefinition = "varchar(64)", nullable = true)
	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ExpirationTime", columnDefinition = "datetime", nullable = true)
	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ScheduledTime", columnDefinition = "datetime", nullable = false)
	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "InsertTime", columnDefinition = "datetime", nullable = false)
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastUpdatedTime", columnDefinition = "datetime", nullable = true)
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	@Column(name = "FailureCount", columnDefinition = "int", nullable = false)
	public short getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(short failureCount) {
		this.failureCount = failureCount;
	}

	@Column(name = "FailureDescription", columnDefinition = "nvarchar(512)", nullable = true)
	public String getFailureDescription() {
		return failureDescription;
	}

	public void setFailureDescription(String failureDescription) {
		this.failureDescription = failureDescription;
	}

	@Column(name = "Data", columnDefinition = "xml", nullable = true)
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
