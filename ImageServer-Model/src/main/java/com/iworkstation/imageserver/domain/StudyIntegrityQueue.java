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

import com.iworkstation.imageserver.enumeration.StudyIntegrityReasonEnum;

@Entity
@Table(name = "StudyIntegrityQueue")
public class StudyIntegrityQueue {
	private String guid;
	private String serverPartitionGUID;
	private String studyStorageGUID;
	private Date insertTime;
	private String description;
	private String studyData;
	private String details;
	private StudyIntegrityReasonEnum studyIntegrityReasonEnum;
	private String groupID;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "InsertTime", columnDefinition = "datetime", nullable = false)
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "Description", columnDefinition = "nvarchar(1024)", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "StudyData", columnDefinition = "xml", nullable = false)
	public String getStudyData() {
		return studyData;
	}

	public void setStudyData(String studyData) {
		this.studyData = studyData;
	}

	@Column(name = "Details", columnDefinition = "xml", nullable = true)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "StudyIntegrityReasonEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public StudyIntegrityReasonEnum getStudyIntegrityReasonEnum() {
		return studyIntegrityReasonEnum;
	}

	public void setStudyIntegrityReasonEnum(
			StudyIntegrityReasonEnum studyIntegrityReasonEnum) {
		this.studyIntegrityReasonEnum = studyIntegrityReasonEnum;
	}

	@Column(name = "GroupID", columnDefinition = "varchar(64)", nullable = true)
	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

}
