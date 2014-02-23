package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FilesystemStudyStorage")
public class FilesystemStudyStorage {
	private String guid;
	private String studyStorageGUID;
	private String filesystemGUID;
	private ServerTransferSyntax serverTransferSyntax;
	private String studyFolder;

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

	@Column(name = "StudyStorageGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	@Column(name = "FilesystemGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getFilesystemGUID() {
		return filesystemGUID;
	}

	public void setFilesystemGUID(String filesystemGUID) {
		this.filesystemGUID = filesystemGUID;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ServerTransferSyntaxGUID")
	@ForeignKey(name = "FK_FilesystemStudyStorage_ServerTransferSyntax")
	public ServerTransferSyntax getServerTransferSyntax() {
		return serverTransferSyntax;
	}

	public void setServerTransferSyntax(
			ServerTransferSyntax serverTransferSyntax) {
		this.serverTransferSyntax = serverTransferSyntax;
	}

	@Column(name = "StudyFolder", columnDefinition = "varchar(8)", nullable = false)
	public String getStudyFolder() {
		return studyFolder;
	}

	public void setStudyFolder(String studyFolder) {
		this.studyFolder = studyFolder;
	}
}
