package com.iworkstation.imageserver.enumeration;

public enum AlertCategoryEnum {
	System("System", "System alert"), Application("Application",
			"Application alert"), Security("Security", "Security alert"), User(
			"User", "User alert");

	private String lookup;
	private String description;

	private AlertCategoryEnum(String lookup, String description) {
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
