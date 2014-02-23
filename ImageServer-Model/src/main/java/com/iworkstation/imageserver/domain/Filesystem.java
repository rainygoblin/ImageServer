package com.iworkstation.imageserver.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.FilesystemTierEnum;

@Entity
@Table(name = "Filesystem")
public class Filesystem {
	private String guid;
	private String filesystemPath;
	private boolean enabled;
	private boolean readOnly;
	private boolean writeOnly;
	private FilesystemTierEnum filesystemTierEnum;
	private BigDecimal lowWatermark;
	private BigDecimal highWatermark;
	private String description;

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

	@Column(name = "FilesystemPath", columnDefinition = "nvarchar(256)", nullable = false)
	public String getFilesystemPath() {
		return filesystemPath;
	}

	public void setFilesystemPath(String filesystemPath) {
		this.filesystemPath = filesystemPath;
	}

	@Column(name = "Enabled", columnDefinition = "bit", nullable = false)
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "ReadOnly", columnDefinition = "bit", nullable = false)
	public boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Column(name = "WriteOnly", columnDefinition = "bit", nullable = false)
	public boolean getWriteOnly() {
		return writeOnly;
	}

	public void setWriteOnly(boolean writeOnly) {
		this.writeOnly = writeOnly;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "FilesystemTierEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public FilesystemTierEnum getFilesystemTierEnum() {
		return filesystemTierEnum;
	}

	public void setFilesystemTierEnum(FilesystemTierEnum filesystemTierEnum) {
		this.filesystemTierEnum = filesystemTierEnum;
	}

	@Column(name = "LowWatermark", columnDefinition = "decimal(8, 4)", nullable = false)
	public BigDecimal getLowWatermark() {
		return lowWatermark;
	}

	public void setLowWatermark(BigDecimal lowWatermark) {
		this.lowWatermark = lowWatermark;
	}

	@Column(name = "HighWatermark", columnDefinition = "decimal(8, 4)", nullable = false)
	public BigDecimal getHighWatermark() {
		return highWatermark;
	}

	public void setHighWatermark(BigDecimal highWatermark) {
		this.highWatermark = highWatermark;
	}

	@Column(name = "Description", columnDefinition = "nvarchar(128)", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
