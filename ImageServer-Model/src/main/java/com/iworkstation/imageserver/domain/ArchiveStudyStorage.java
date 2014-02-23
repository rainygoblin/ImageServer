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
@Table(name = "ArchiveStudyStorage")
public class ArchiveStudyStorage {
	private String guid;
	private String partitionArchiveGUID;
	private String studyStorageGUID;
	private String serverTransferSyntaxGUID;
	private Date archiveTime;
	private String archiveXml;

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

	@Column(name = "StudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	@Column(name = "ServerTransferSyntaxGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getServerTransferSyntaxGUID() {
		return serverTransferSyntaxGUID;
	}

	public void setServerTransferSyntaxGUID(String serverTransferSyntaxGUID) {
		this.serverTransferSyntaxGUID = serverTransferSyntaxGUID;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ArchiveTime", columnDefinition = "datetime", nullable = false)
	public Date getArchiveTime() {
		return archiveTime;
	}

	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}

	@Column(name = "ArchiveXml", columnDefinition = "xml", nullable = false)
	public String getArchiveXml() {
		return archiveXml;
	}

	public void setArchiveXml(String archiveXml) {
		this.archiveXml = archiveXml;
	}

}
