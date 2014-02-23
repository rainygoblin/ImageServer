package com.iworkstation.imageserver.enumeration;


public enum FilesystemTierEnum {
	Tier1("Tier1", "Filesystem Tier 1"), Tier2("Tier2", "Filesystem Tier 2"), Tier3(
			"Tier3", "Filesystem Tier 3");

	private String lookup;
	private String description;

	private FilesystemTierEnum(String lookup, String description) {
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
