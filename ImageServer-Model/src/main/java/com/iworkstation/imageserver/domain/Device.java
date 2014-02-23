package com.iworkstation.imageserver.domain;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.DeviceTypeEnum;

@Entity
@Table(name = "Device")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.iworkstation.imageserver.domain.Device")
public class Device {
	private String guid;
	private String serverPartitionGUID;
	private String aeTitle;
	private String ipAddress;
	private int port;
	private String description;
	private boolean dhcp = false;
	private boolean enabled = true;
	private boolean allowStorage = true;
	private boolean acceptKOPR = true;
	private boolean allowRetrieve = true;
	private boolean allowQuery = true;
	private boolean allowAutoRoute = true;
	private short throttleMaxConnections = 10;
	private Date lastAccessedTime;
	private DeviceTypeEnum deviceTypeEnum;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "Guid", columnDefinition = "uniqueidentifier")
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

	@Column(name = "AeTitle", columnDefinition = "nvarchar(256)", nullable = false)
	public String getAeTitle() {
		return aeTitle;
	}

	public void setAeTitle(String aeTitle) {
		this.aeTitle = aeTitle;
	}

	@Column(name = "IpAddress", columnDefinition = "nvarchar(256)", nullable = true)
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "Port", columnDefinition = "int", nullable = false)
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Column(name = "Description", columnDefinition = "nvarchar(256)", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Dhcp", columnDefinition = "bit", nullable = false)
	public boolean getDhcp() {
		return dhcp;
	}

	public void setDhcp(boolean dhcp) {
		this.dhcp = dhcp;
	}

	@Column(name = "Enabled", columnDefinition = "bit", nullable = false)
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "AllowStorage", columnDefinition = "bit", nullable = false)
	public boolean getAllowStorage() {
		return allowStorage;
	}

	public void setAllowStorage(boolean allowStorage) {
		this.allowStorage = allowStorage;
	}

	@Column(name = "AcceptKOPR", columnDefinition = "bit", nullable = false)
	public boolean getAcceptKOPR() {
		return acceptKOPR;
	}

	public void setAcceptKOPR(boolean acceptKOPR) {
		this.acceptKOPR = acceptKOPR;
	}

	@Column(name = "AllowRetrieve", columnDefinition = "bit", nullable = false)
	public boolean getAllowRetrieve() {
		return allowRetrieve;
	}

	public void setAllowRetrieve(boolean allowRetrieve) {
		this.allowRetrieve = allowRetrieve;
	}

	@Column(name = "AllowQuery", columnDefinition = "bit", nullable = false)
	public boolean getAllowQuery() {
		return allowQuery;
	}

	public void setAllowQuery(boolean allowQuery) {
		this.allowQuery = allowQuery;
	}

	@Column(name = "AllowAutoRoute", columnDefinition = "bit", nullable = false)
	public boolean getAllowAutoRoute() {
		return allowAutoRoute;
	}

	public void setAllowAutoRoute(boolean allowAutoRoute) {
		this.allowAutoRoute = allowAutoRoute;
	}

	@Column(name = "ThrottleMaxConnections", columnDefinition = "smallint", nullable = false)
	public short getThrottleMaxConnections() {
		return throttleMaxConnections;
	}

	public void setThrottleMaxConnections(short throttleMaxConnections) {
		this.throttleMaxConnections = throttleMaxConnections;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastAccessedTime", columnDefinition = "datetime", nullable = false)
	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(Date lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "DeviceTypeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public DeviceTypeEnum getDeviceTypeEnum() {
		return deviceTypeEnum;
	}

	public void setDeviceTypeEnum(DeviceTypeEnum deviceTypeEnum) {
		this.deviceTypeEnum = deviceTypeEnum;
	}

}
