package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WorkQueueUid")
public class WorkQueueUid {
	private String guid;
	private String workQueueGUID;
	private String seriesInstanceUid;
	private String sopInstanceUid;
	private boolean failed;
	private boolean duplicate;
	private String extension;
	private short failureCount;
	private String groupID;
	private String relativePath;

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

	@Column(name = "WorkQueueGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getWorkQueueGUID() {
		return workQueueGUID;
	}

	public void setWorkQueueGUID(String workQueueGUID) {
		this.workQueueGUID = workQueueGUID;
	}

	@Column(name = "SeriesInstanceUid", columnDefinition = "smallint", nullable = true)
	public String getSeriesInstanceUid() {
		return seriesInstanceUid;
	}

	public void setSeriesInstanceUid(String seriesInstanceUid) {
		this.seriesInstanceUid = seriesInstanceUid;
	}

	@Column(name = "SopInstanceUid", columnDefinition = "varchar(64)", nullable = true)
	public String getSopInstanceUid() {
		return sopInstanceUid;
	}

	public void setSopInstanceUid(String sopInstanceUid) {
		this.sopInstanceUid = sopInstanceUid;
	}

	@Column(name = "Failed", columnDefinition = "bit", nullable = false)
	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	@Column(name = "Duplicate", columnDefinition = "bit", nullable = false)
	public boolean isDuplicate() {
		return duplicate;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	@Column(name = "Extension", columnDefinition = "varchar(10)", nullable = true)
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Column(name = "FailureCount", columnDefinition = "smallint", nullable = false)
	public short getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(short failureCount) {
		this.failureCount = failureCount;
	}

	@Column(name = "GroupID", columnDefinition = "varchar(64)", nullable = true)
	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	@Column(name = "RelativePath", columnDefinition = "varchar(256)", nullable = true)
	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
}
