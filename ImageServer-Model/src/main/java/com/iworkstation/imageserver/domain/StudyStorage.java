package com.iworkstation.imageserver.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.QueueStudyStateEnum;
import com.iworkstation.imageserver.enumeration.StudyStatusEnum;

@Entity
@Table(name = "StudyStorage")
@SqlResultSetMapping(name = "scalar", columns = { @ColumnResult(name = "Guid"),
		@ColumnResult(name = "StudyInstanceUid"),
		@ColumnResult(name = "ServerPartitionGUID"),
		@ColumnResult(name = "LastAccessedTime"),
		@ColumnResult(name = "InsertTime"),
		@ColumnResult(name = "StudyStatusEnum"),
		@ColumnResult(name = "FilesystemPath"),
		@ColumnResult(name = "PartitionFolder"),
		@ColumnResult(name = "StudyFolder"),
		@ColumnResult(name = "FilesystemGUID"),
		@ColumnResult(name = "Enabled"), @ColumnResult(name = "ReadOnly"),
		@ColumnResult(name = "WriteOnly"),
		@ColumnResult(name = "FilesystemTierEnum"),
		@ColumnResult(name = "WriteLock"), @ColumnResult(name = "ReadLock"),
		@ColumnResult(name = "ServerTransferSyntaxGUID"),
		@ColumnResult(name = "TransferSyntaxUid"),
		@ColumnResult(name = "FilesystemStudyStorageGUID"),
		@ColumnResult(name = "QueueStudyStateEnum"),
		@ColumnResult(name = "IsReconcileRequired") })
@NamedNativeQueries(value = {
		@NamedNativeQuery(name = "QueryStudyStorageLocation", query = "{call QueryStudyStorageLocation(:StudyStorageGUID,:ServerPartitionGUID,:StudyInstanceUid)}", resultSetMapping = "scalar"),
		@NamedNativeQuery(name = "InsertStudyStorage", query = "{call InsertStudyStorage(:ServerPartitionGUID,:StudyInstanceUid,:Folder,:FilesystemGUID,:TransferSyntaxUid,:StudyStatusEnum,:QueueStudyStateEnum)}", resultSetMapping = "scalar") })
public class StudyStorage {
	private String guid;
	private String serverPartitionGUID;
	private String studyInstanceUid;
	private Date insertTime;
	private Date lastAccessedTime;
	private boolean writeLock;
	private short readLock;
	private StudyStatusEnum studyStatusEnum;
	private QueueStudyStateEnum queueStudyStateEnum;

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

	@Column(name = "StudyInstanceUid", columnDefinition = "varchar(64)", nullable = false)
	public String getStudyInstanceUid() {
		return studyInstanceUid;
	}

	public void setStudyInstanceUid(String studyInstanceUid) {
		this.studyInstanceUid = studyInstanceUid;
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
	@Column(name = "LastAccessedTime", columnDefinition = "datetime", nullable = false)
	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(Date lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	@Column(name = "WriteLock", columnDefinition = "bit", nullable = false)
	public boolean isWriteLock() {
		return writeLock;
	}

	public void setWriteLock(boolean writeLock) {
		this.writeLock = writeLock;
	}

	@Column(name = "ReadLock", columnDefinition = "smallint", nullable = false)
	public short getReadLock() {
		return readLock;
	}

	public void setReadLock(short readLock) {
		this.readLock = readLock;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "StudyStatusEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public StudyStatusEnum getStudyStatusEnum() {
		return studyStatusEnum;
	}

	public void setStudyStatusEnum(StudyStatusEnum studyStatusEnum) {
		this.studyStatusEnum = studyStatusEnum;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "QueueStudyStateEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public QueueStudyStateEnum getQueueStudyStateEnum() {
		return queueStudyStateEnum;
	}

	public void setQueueStudyStateEnum(QueueStudyStateEnum queueStudyStateEnum) {
		this.queueStudyStateEnum = queueStudyStateEnum;
	}
}
