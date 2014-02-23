package com.iworkstation.imageserver.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.QueueStudyStateEnum;
import com.iworkstation.imageserver.enumeration.WorkQueuePriorityEnum;
import com.iworkstation.imageserver.enumeration.WorkQueueTypeEnum;

@Entity
@Table(name = "WorkQueueTypeProperties")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.iworkstation.imageserver.domain.WorkQueueTypeProperties")
public class WorkQueueTypeProperties {
	private String guid;
	private WorkQueueTypeEnum workQueueTypeEnum;
	private WorkQueuePriorityEnum workQueuePriorityEnum;
	private boolean memoryLimited;
	private boolean alertFailedWorkQueue;
	private int maxFailureCount;
	private int processDelaySeconds;
	private int failureDelaySeconds;
	private int deleteDelaySeconds;
	private int postponeDelaySeconds;
	private int expireDelaySeconds;
	private int maxBatchSize;
	private QueueStudyStateEnum queueStudyStateEnum;
	private short queueStudyStateOrder;
	private boolean readLock;
	private boolean writeLock;

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
	@Column(name = "WorkQueueTypeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public WorkQueueTypeEnum getWorkQueueTypeEnum() {
		return workQueueTypeEnum;
	}

	public void setWorkQueueTypeEnum(WorkQueueTypeEnum workQueueTypeEnum) {
		this.workQueueTypeEnum = workQueueTypeEnum;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "WorkQueuePriorityEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public WorkQueuePriorityEnum getWorkQueuePriorityEnum() {
		return workQueuePriorityEnum;
	}

	public void setWorkQueuePriorityEnum(
			WorkQueuePriorityEnum workQueuePriorityEnum) {
		this.workQueuePriorityEnum = workQueuePriorityEnum;
	}

	@Column(name = "MemoryLimited", columnDefinition = "bit", nullable = false)
	public boolean isMemoryLimited() {
		return memoryLimited;
	}

	public void setMemoryLimited(boolean memoryLimited) {
		this.memoryLimited = memoryLimited;
	}

	@Column(name = "AlertFailedWorkQueue", columnDefinition = "bit", nullable = false)
	public boolean isAlertFailedWorkQueue() {
		return alertFailedWorkQueue;
	}

	public void setAlertFailedWorkQueue(boolean alertFailedWorkQueue) {
		this.alertFailedWorkQueue = alertFailedWorkQueue;
	}

	@Column(name = "MaxFailureCount", columnDefinition = "int", nullable = false)
	public int getMaxFailureCount() {
		return maxFailureCount;
	}

	public void setMaxFailureCount(int maxFailureCount) {
		this.maxFailureCount = maxFailureCount;
	}

	@Column(name = "ProcessDelaySeconds", columnDefinition = "int", nullable = false)
	public int getProcessDelaySeconds() {
		return processDelaySeconds;
	}

	public void setProcessDelaySeconds(int processDelaySeconds) {
		this.processDelaySeconds = processDelaySeconds;
	}

	@Column(name = "FailureDelaySeconds", columnDefinition = "int", nullable = false)
	public int getFailureDelaySeconds() {
		return failureDelaySeconds;
	}

	public void setFailureDelaySeconds(int failureDelaySeconds) {
		this.failureDelaySeconds = failureDelaySeconds;
	}

	@Column(name = "DeleteDelaySeconds", columnDefinition = "int", nullable = false)
	public int getDeleteDelaySeconds() {
		return deleteDelaySeconds;
	}

	public void setDeleteDelaySeconds(int deleteDelaySeconds) {
		this.deleteDelaySeconds = deleteDelaySeconds;
	}

	@Column(name = "PostponeDelaySeconds", columnDefinition = "int", nullable = false)
	public int getPostponeDelaySeconds() {
		return postponeDelaySeconds;
	}

	public void setPostponeDelaySeconds(int postponeDelaySeconds) {
		this.postponeDelaySeconds = postponeDelaySeconds;
	}

	@Column(name = "ExpireDelaySeconds", columnDefinition = "int", nullable = false)
	public int getExpireDelaySeconds() {
		return expireDelaySeconds;
	}

	public void setExpireDelaySeconds(int expireDelaySeconds) {
		this.expireDelaySeconds = expireDelaySeconds;
	}

	@Column(name = "MaxBatchSize", columnDefinition = "int", nullable = false)
	public int getMaxBatchSize() {
		return maxBatchSize;
	}

	public void setMaxBatchSize(int maxBatchSize) {
		this.maxBatchSize = maxBatchSize;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "QueueStudyStateEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public QueueStudyStateEnum getQueueStudyStateEnum() {
		return queueStudyStateEnum;
	}

	public void setQueueStudyStateEnum(QueueStudyStateEnum queueStudyStateEnum) {
		this.queueStudyStateEnum = queueStudyStateEnum;
	}

	@Column(name = "QueueStudyStateOrder", columnDefinition = "smallint", nullable = true)
	public short getQueueStudyStateOrder() {
		return queueStudyStateOrder;
	}

	public void setQueueStudyStateOrder(short queueStudyStateOrder) {
		this.queueStudyStateOrder = queueStudyStateOrder;
	}

	@Column(name = "ReadLock", columnDefinition = "bit", nullable = false)
	public boolean isReadLock() {
		return readLock;
	}

	public void setReadLock(boolean readLock) {
		this.readLock = readLock;
	}

	@Column(name = "WriteLock", columnDefinition = "bit", nullable = false)
	public boolean isWriteLock() {
		return writeLock;
	}

	public void setWriteLock(boolean writeLock) {
		this.writeLock = writeLock;
	}
}
