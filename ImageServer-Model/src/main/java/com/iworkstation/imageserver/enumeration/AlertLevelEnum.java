package com.iworkstation.imageserver.enumeration;

public enum AlertLevelEnum {
	Informational("Informational", "Informational alert"), Warning("Warning",
			"Warning alert"), Error("Error", "Error alert"), Critical(
			"Critical", "Critical alert");

	private String lookup;
	private String description;

	private AlertLevelEnum(String lookup, String description) {
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
