package com.iworkstation.imageserver.enumeration;

public enum ArchiveQueueStatusEnum {
	Pending("Pending", "Pending"), InProgress("InProgress", "In Progress"), Completed(
			"Completed", "The Queue entry is completed."), Failed("Failed",
			"The Queue entry has failed.");

	private String lookup;
	private String description;

	private ArchiveQueueStatusEnum(String lookup, String description) {
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
