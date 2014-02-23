package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "StudyIntegrityQueueUid")
public class StudyIntegrityQueueUid {
	private String guid;
	private String studyIntegrityQueueGUID;
	private String seriesDescription;
	private String seriesInstanceUid;
	private String sopInstanceUid;
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

	@Column(name = "StudyIntegrityQueueGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyIntegrityQueueGUID() {
		return studyIntegrityQueueGUID;
	}

	public void setStudyIntegrityQueueGUID(String studyIntegrityQueueGUID) {
		this.studyIntegrityQueueGUID = studyIntegrityQueueGUID;
	}

	@Column(name = "SeriesDescription", columnDefinition = "nvarchar(64)", nullable = true)
	public String getSeriesDescription() {
		return seriesDescription;
	}

	public void setSeriesDescription(String seriesDescription) {
		this.seriesDescription = seriesDescription;
	}

	@Column(name = "SeriesInstanceUid", columnDefinition = "varchar(64)", nullable = false)
	public String getSeriesInstanceUid() {
		return seriesInstanceUid;
	}

	public void setSeriesInstanceUid(String seriesInstanceUid) {
		this.seriesInstanceUid = seriesInstanceUid;
	}

	@Column(name = "SopInstanceUid", columnDefinition = "varchar(64)", nullable = false)
	public String getSopInstanceUid() {
		return sopInstanceUid;
	}

	public void setSopInstanceUid(String sopInstanceUid) {
		this.sopInstanceUid = sopInstanceUid;
	}

	@Column(name = "RelativePath", columnDefinition = "varchar(256)", nullable = true)
	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
}
