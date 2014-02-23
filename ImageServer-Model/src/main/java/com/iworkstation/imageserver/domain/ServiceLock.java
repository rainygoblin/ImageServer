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

import com.iworkstation.imageserver.enumeration.ServiceLockTypeEnum;

@Entity
@Table(name = "ServiceLock")
public class ServiceLock {
	private String guid;
	private ServiceLockTypeEnum serviceLockTypeEnum;
	private String processorId;
	private boolean lock;
	private Date scheduledTime;
	private String filesystemGUID;
	private boolean enabled;
	private String state;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "ServiceLockTypeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public ServiceLockTypeEnum getServiceLockTypeEnum() {
		return serviceLockTypeEnum;
	}

	public void setServiceLockTypeEnum(ServiceLockTypeEnum serviceLockTypeEnum) {
		this.serviceLockTypeEnum = serviceLockTypeEnum;
	}

	@Column(name = "ProcessorId", columnDefinition = "varchar(128)", nullable = true)
	public String getProcessorId() {
		return processorId;
	}

	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}

	@Column(name = "Lock", columnDefinition = "bit", nullable = true)
	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ScheduledTime", columnDefinition = "datetime", nullable = false)
	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	@Column(name = "FilesystemGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getFilesystemGUID() {
		return filesystemGUID;
	}

	public void setFilesystemGUID(String filesystemGUID) {
		this.filesystemGUID = filesystemGUID;
	}

	@Column(name = "Enabled", columnDefinition = "bit", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "State", columnDefinition = "xml", nullable = true)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
