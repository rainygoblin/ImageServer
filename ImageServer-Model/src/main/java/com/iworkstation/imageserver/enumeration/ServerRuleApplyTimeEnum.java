package com.iworkstation.imageserver.enumeration;

public enum ServerRuleApplyTimeEnum {
	SopReceived("SopReceived",
			"Apply rule when a SOP Instance has been received"), SopProcessed(
			"SopProcessed", "Apply rule when a SOP Instance has been processed"), SeriesProcessed(
			"SeriesProcessed",
			"Apply rule when a Series is initially processed"), StudyProcessed(
			"StudyProcessed", "Apply rule after a Study has been processed"), StudyArchived(
			"StudyArchived", "Apply rule after a Study is archived"), StudyRestored(
			"StudyRestored", "Apply rule after a Study has been restored"), SopEdited(
			"SopEdited", "Apply rule when a SOP Instance is edited");

	private String lookup;
	private String description;

	private ServerRuleApplyTimeEnum(String lookup, String description) {
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
