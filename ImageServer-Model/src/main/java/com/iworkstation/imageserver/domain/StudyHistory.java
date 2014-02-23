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

import com.iworkstation.imageserver.enumeration.StudyHistoryTypeEnum;

@Entity
@Table(name = "StudyHistory")
public class StudyHistory {
	private String guid;
	private Date insertTime;
	private String studyStorageGUID;
	private String destStudyStorageGUID;
	private StudyHistoryTypeEnum studyHistoryTypeEnum;
	private String studyData;
	private String changeDescription;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "InsertTime", columnDefinition = "datetime", nullable = false)
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "StudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	@Column(name = "DestStudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getDestStudyStorageGUID() {
		return destStudyStorageGUID;
	}

	public void setDestStudyStorageGUID(String destStudyStorageGUID) {
		this.destStudyStorageGUID = destStudyStorageGUID;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "StudyHistoryTypeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public StudyHistoryTypeEnum getStudyHistoryTypeEnum() {
		return studyHistoryTypeEnum;
	}

	public void setStudyHistoryTypeEnum(
			StudyHistoryTypeEnum studyHistoryTypeEnum) {
		this.studyHistoryTypeEnum = studyHistoryTypeEnum;
	}

	@Column(name = "StudyData", columnDefinition = "xml", nullable = false)
	public String getStudyData() {
		return studyData;
	}

	public void setStudyData(String studyData) {
		this.studyData = studyData;
	}

	@Column(name = "ChangeDescription", columnDefinition = "xml", nullable = true)
	public String getChangeDescription() {
		return changeDescription;
	}

	public void setChangeDescription(String changeDescription) {
		this.changeDescription = changeDescription;
	}

}
