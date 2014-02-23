package com.iworkstation.imageserver.parameter;

public class WorkQueueQueryParameters {
	private String processorID;
	private String workQueuePriorityEnum;
	private Boolean memoryLimited;

	public String getProcessorID() {
		return processorID;
	}

	public void setProcessorID(String processorID) {
		this.processorID = processorID;
	}

	public String getWorkQueuePriorityEnum() {
		return workQueuePriorityEnum;
	}

	public void setWorkQueuePriorityEnum(String workQueuePriorityEnum) {
		this.workQueuePriorityEnum = workQueuePriorityEnum;
	}

	public Boolean isMemoryLimited() {
		return memoryLimited;
	}

	public void setMemoryLimited(Boolean memoryLimited) {
		this.memoryLimited = memoryLimited;
	}
}
