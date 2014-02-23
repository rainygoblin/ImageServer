package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PartitionTransferSyntax")
public class PartitionTransferSyntax {

	private String guid;
	private String serverPartitionGUID;
	private String serverTransferSyntaxGUID;
	private Boolean enabled;

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

	@Column(name = "ServerTransferSyntaxGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getServerTransferSyntaxGUID() {
		return serverTransferSyntaxGUID;
	}

	public void setServerTransferSyntaxGUID(String serverTransferSyntaxGUID) {
		this.serverTransferSyntaxGUID = serverTransferSyntaxGUID;
	}

	@Column(name = "Enabled", columnDefinition = "bit", nullable = false)
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
