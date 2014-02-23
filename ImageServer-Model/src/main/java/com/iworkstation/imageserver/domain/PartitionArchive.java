package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.ArchiveTypeEnum;

@Entity
@Table(name = "PartitionArchive")
public class PartitionArchive {
	private String guid;
	private String serverPartitionGUID;
	private ArchiveTypeEnum archiveTypeEnum;
	private String description;
	private boolean enabled;
	private boolean readOnly;
	private int archiveDelayHours;
	private String configurationXml;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "ArchiveTypeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public ArchiveTypeEnum getArchiveTypeEnum() {
		return archiveTypeEnum;
	}

	public void setArchiveTypeEnum(ArchiveTypeEnum archiveTypeEnum) {
		this.archiveTypeEnum = archiveTypeEnum;
	}

	@Column(name = "Description", columnDefinition = "nvarchar(128)", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Enabled", columnDefinition = "bit", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "ReadOnly", columnDefinition = "bit", nullable = false)
	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Column(name = "ArchiveDelayHours", columnDefinition = "int", nullable = false)
	public int getArchiveDelayHours() {
		return archiveDelayHours;
	}

	public void setArchiveDelayHours(int archiveDelayHours) {
		this.archiveDelayHours = archiveDelayHours;
	}

	@Column(name = "ConfigurationXml", columnDefinition = "xml", nullable = false)
	public String getConfigurationXml() {
		return configurationXml;
	}

	public void setConfigurationXml(String configurationXml) {
		this.configurationXml = configurationXml;
	}
}
