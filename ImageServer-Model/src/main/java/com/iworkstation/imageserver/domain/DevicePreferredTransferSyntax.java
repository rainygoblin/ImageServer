package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DevicePreferredTransferSyntax")
public class DevicePreferredTransferSyntax {
	private String guid;
	private String deviceGUID;
	private String serverSopClassGUID;
	private String serverTransferSyntaxGUID;

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

	@Column(name = "DeviceGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getDeviceGUID() {
		return deviceGUID;
	}

	public void setDeviceGUID(String deviceGUID) {
		this.deviceGUID = deviceGUID;
	}

	@Column(name = "ServerSopClassGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getServerSopClassGUID() {
		return serverSopClassGUID;
	}

	public void setServerSopClassGUID(String serverSopClassGUID) {
		this.serverSopClassGUID = serverSopClassGUID;
	}

	@Column(name = "ServerTransferSyntaxGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getServerTransferSyntaxGUID() {
		return serverTransferSyntaxGUID;
	}

	public void setServerTransferSyntaxGUID(String serverTransferSyntaxGUID) {
		this.serverTransferSyntaxGUID = serverTransferSyntaxGUID;
	}

}
