package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PartitionSopClass")
public class PartitionSopClass {

	private String guid;
	private String serverPartitionGUID;
	private String serverSopClassGUID;
	private boolean enabled;

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

	@Column(name = "ServerSopClassGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getServerSopClassGUID() {
		return serverSopClassGUID;
	}

	public void setServerSopClassGUID(String serverSopClassGUID) {
		this.serverSopClassGUID = serverSopClassGUID;
	}

	@Column(name = "Enabled", columnDefinition = "bit", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
