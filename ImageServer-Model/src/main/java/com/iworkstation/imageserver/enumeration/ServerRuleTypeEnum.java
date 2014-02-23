package com.iworkstation.imageserver.enumeration;


public enum ServerRuleTypeEnum {
	AutoRoute("AutoRoute", "A DICOM auto-routing rule"), StudyDelete(
			"StudyDelete", "A rule to specify when to delete a study"), Tier1Retention(
			"Tier1Retention",
			"A rule to specify how long a study will be retained on Tier1"), OnlineRetention(
			"OnlineRetention",
			"A rule to specify how long a study will be retained online"), StudyCompress(
			"StudyCompress",
			"A rule to specify when a study should be compressed"), SopCompress(
			"SopCompress",
			"A rule to specify when a SOP Instance should be compressed (during initial processing)");

	private String lookup;
	private String description;

	private ServerRuleTypeEnum(String lookup, String description) {
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
