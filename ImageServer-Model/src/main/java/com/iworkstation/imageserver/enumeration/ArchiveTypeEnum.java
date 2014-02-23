package com.iworkstation.imageserver.enumeration;


public enum ArchiveTypeEnum {
	HsmArchive("HsmArchive", "Hierarchical storage management archive such as StorageTek QFS");

	private String lookup;
	private String description;

	private ArchiveTypeEnum(String lookup, String description) {
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
