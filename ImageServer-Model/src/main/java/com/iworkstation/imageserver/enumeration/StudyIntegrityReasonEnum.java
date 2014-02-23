package com.iworkstation.imageserver.enumeration;


public enum StudyIntegrityReasonEnum {
	InconsistentData("InconsistentData",
			"Images must be reconciled because of inconsistent data."), Duplicate(
			"Duplicate",
			"Duplicates were received and need to be reconciled.");

	private String lookup;
	private String description;

	private StudyIntegrityReasonEnum(String lookup, String description) {
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
