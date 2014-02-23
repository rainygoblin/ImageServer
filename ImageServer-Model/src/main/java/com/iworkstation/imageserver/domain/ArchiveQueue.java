package com.iworkstation.imageserver.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.ArchiveQueueStatusEnum;

@Entity
@Table(name = "ArchiveQueue")
public class ArchiveQueue {
	private String guid;
	private String partitionArchiveGUID;
	private Date scheduledTime;
	private String studyStorageGUID;
	private ArchiveQueueStatusEnum archiveQueueStatusEnum;
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

	@Column(name = "PartitionArchiveGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getPartitionArchiveGUID() {
		return partitionArchiveGUID;
	}

	public void setPartitionArchiveGUID(String partitionArchiveGUID) {
		this.partitionArchiveGUID = partitionArchiveGUID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ScheduledTime", columnDefinition = "datetime", nullable = false)
	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	@Column(name = "StudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "ArchiveQueueStatusEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public ArchiveQueueStatusEnum getArchiveQueueStatusEnum() {
		return archiveQueueStatusEnum;
	}

	public void setArchiveQueueStatusEnum(
			ArchiveQueueStatusEnum archiveQueueStatusEnum) {
		this.archiveQueueStatusEnum = archiveQueueStatusEnum;
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
