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

import com.iworkstation.imageserver.enumeration.FilesystemQueueTypeEnum;

@Entity
@Table(name = "FilesystemQueue")
public class FilesystemQueue {
	private String guid;
	private FilesystemQueueTypeEnum filesystemQueueTypeEnum;
	private String StudyStorageGUID;
	private String filesystemGUID;
	private Date scheduledTime;
	private String seriesInstanceUid;
	private String QueueXml;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "FilesystemQueueTypeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public FilesystemQueueTypeEnum getFilesystemQueueTypeEnum() {
		return filesystemQueueTypeEnum;
	}

	public void setFilesystemQueueTypeEnum(
			FilesystemQueueTypeEnum filesystemQueueTypeEnum) {
		this.filesystemQueueTypeEnum = filesystemQueueTypeEnum;
	}

	@Column(name = "StudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyStorageGUID() {
		return StudyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		StudyStorageGUID = studyStorageGUID;
	}

	@Column(name = "FilesystemGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getFilesystemGUID() {
		return filesystemGUID;
	}

	public void setFilesystemGUID(String filesystemGUID) {
		this.filesystemGUID = filesystemGUID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ScheduledTime", columnDefinition = "datetime", nullable = false)
	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	@Column(name = "SeriesInstanceUid", columnDefinition = "varchar(64)", nullable = true)
	public String getSeriesInstanceUid() {
		return seriesInstanceUid;
	}

	public void setSeriesInstanceUid(String seriesInstanceUid) {
		this.seriesInstanceUid = seriesInstanceUid;
	}

	@Column(name = "QueueXml", columnDefinition = "xml", nullable = true)
	public String getQueueXml() {
		return QueueXml;
	}

	public void setQueueXml(String queueXml) {
		QueueXml = queueXml;
	}
}
