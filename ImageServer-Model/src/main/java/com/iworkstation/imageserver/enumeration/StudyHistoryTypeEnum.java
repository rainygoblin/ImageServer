package com.iworkstation.imageserver.enumeration;


public enum StudyHistoryTypeEnum {
	StudyReconciled(
			"StudyReconciled",
			"Demographics in the orginal images were modified to match against another study on the server."), WebEdited(
			"WebEdited", "Study was edited via the Web GUI"), Duplicate(
			"Duplicate", "Duplicate was received and processed."), Reprocessed(
			"Reprocessed", "Study was reprocessed."), SeriesDeleted(
			"SeriesDeleted", "One or more series was deleted manually.");

	private String lookup;
	private String description;

	private StudyHistoryTypeEnum(String lookup, String description) {
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
