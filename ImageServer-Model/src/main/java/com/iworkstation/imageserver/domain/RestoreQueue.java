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

import com.iworkstation.imageserver.enumeration.RestoreQueueStatusEnum;

@Entity
@Table(name = "RestoreQueue")
@NamedNativeQueries(value = { @NamedNativeQuery(name = "InsertRestoreQueue", query = "{call InsertRestoreQueue(:StudyStorageGUID, :ArchiveStudyStorageGUID)}", resultClass = RestoreQueue.class) })
public class RestoreQueue {
	private String guid;
	private String archiveStudyStorageGUID;
	private String studyStorageGUID;
	private Date scheduledTime;
	private RestoreQueueStatusEnum restoreQueueStatusEnum;
	private String processorId;
	private String failureDescription;

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

	@Column(name = "ArchiveStudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getArchiveStudyStorageGUID() {
		return archiveStudyStorageGUID;
	}

	public void setArchiveStudyStorageGUID(String archiveStudyStorageGUID) {
		this.archiveStudyStorageGUID = archiveStudyStorageGUID;
	}

	@Column(name = "StudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ScheduledTime", columnDefinition = "datetime", nullable = false)
	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "RestoreQueueStatusEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public RestoreQueueStatusEnum getRestoreQueueStatusEnum() {
		return restoreQueueStatusEnum;
	}

	public void setRestoreQueueStatusEnum(
			RestoreQueueStatusEnum restoreQueueStatusEnum) {
		this.restoreQueueStatusEnum = restoreQueueStatusEnum;
	}

	@Column(name = "ProcessorId", columnDefinition = "varchar(128)", nullable = true)
	public String getProcessorId() {
		return processorId;
	}

	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}

	@Column(name = "FailureDescription", columnDefinition = "nvarchar(512)", nullable = true)
	public String getFailureDescription() {
		return failureDescription;
	}

	public void setFailureDescription(String failureDescription) {
		this.failureDescription = failureDescription;
	}
}
