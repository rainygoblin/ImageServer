package com.iworkstation.imageserver.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "StudyDeleteRecord")
public class StudyDeleteRecord {
	private String guid;
	private Date timestamp;
	private String reason;
	private String serverPartitionAE;
	private String filesystemGUID;
	private String backupPath;
	private String studyInstanceUid;
	private String accessionNumber;
	private String patientId;
	private String patientsName;
	private String studyId;
	private String studyDescription;
	private String studyDate;
	private String studyTime;
	private String archiveInfo;
	private String extendedInfo;

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
	@Column(name = "Timestamp", columnDefinition = "datetime", nullable = false)
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "Reason", columnDefinition = "nvarchar(1024)", nullable = true)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "ServerPartitionAE", columnDefinition = "varchar(64)", nullable = false)
	public String getServerPartitionAE() {
		return serverPartitionAE;
	}

	public void setServerPartitionAE(String serverPartitionAE) {
		this.serverPartitionAE = serverPartitionAE;
	}

	@Column(name = "FilesystemGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getFilesystemGUID() {
		return filesystemGUID;
	}

	public void setFilesystemGUID(String filesystemGUID) {
		this.filesystemGUID = filesystemGUID;
	}

	@Column(name = "BackupPath", columnDefinition = "nvarchar(256)", nullable = true)
	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}

	@Column(name = "StudyInstanceUid", columnDefinition = "varchar(64)", nullable = false)
	public String getStudyInstanceUid() {
		return studyInstanceUid;
	}

	public void setStudyInstanceUid(String studyInstanceUid) {
		this.studyInstanceUid = studyInstanceUid;
	}

	@Column(name = "AccessionNumber", columnDefinition = "varchar(64)", nullable = true)
	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	@Column(name = "PatientId", columnDefinition = "varchar(64)", nullable = true)
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	@Column(name = "PatientsName", columnDefinition = "nvarchar(256)", nullable = true)
	public String getPatientsName() {
		return patientsName;
	}

	public void setPatientsName(String patientsName) {
		this.patientsName = patientsName;
	}

	@Column(name = "StudyId", columnDefinition = "nvarchar(64)", nullable = true)
	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	@Column(name = "StudyDescription", columnDefinition = "nvarchar(64)", nullable = true)
	public String getStudyDescription() {
		return studyDescription;
	}

	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}

	@Column(name = "StudyDate", columnDefinition = "varchar(16)", nullable = true)
	public String getStudyDate() {
		return studyDate;
	}

	public void setStudyDate(String studyDate) {
		this.studyDate = studyDate;
	}

	@Column(name = "StudyTime", columnDefinition = "varchar(32)", nullable = true)
	public String getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(String studyTime) {
		this.studyTime = studyTime;
	}

	@Column(name = "ArchiveInfo", columnDefinition = "xml", nullable = true)
	public String getArchiveInfo() {
		return archiveInfo;
	}

	public void setArchiveInfo(String archiveInfo) {
		this.archiveInfo = archiveInfo;
	}

	@Column(name = "ExtendedInfo", columnDefinition = "nvarchar(MAX)", nullable = true)
	public String getExtendedInfo() {
		return extendedInfo;
	}

	public void setExtendedInfo(String extendedInfo) {
		this.extendedInfo = extendedInfo;
	}
}
