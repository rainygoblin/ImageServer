package com.iworkstation.imageserver.enumeration;

public enum RestoreQueueStatusEnum {
	Pending("Pending", "Pending"), InProgress("InProgress", "In Progress"), Completed(
			"Completed", "The Queue entry is completed."), Failed("Failed",
			"The Queue entry has failed."), Restoring("Restoring",
			"The Queue entry is waiting for the study to be restored by the archive.");

	private String lookup;
	private String description;

	private RestoreQueueStatusEnum(String lookup, String description) {
		this.lookup = lookup;
		this.description = description;
	}

	public String getLookup() {
		return lookup;
	}

	public String getDescription() {
		return description;
	}
}
