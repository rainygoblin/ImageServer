package com.iworkstation.imageserver.enumeration;

public enum WorkQueuePriorityEnum {
	Stat("Stat", "Stat priority"), Low("Low", "Low priority"), Medium("Medium",
			"Medium priority"), High("High", "High priority");

	private String lookup;
	private String description;

	private WorkQueuePriorityEnum(String lookup, String description) {
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
