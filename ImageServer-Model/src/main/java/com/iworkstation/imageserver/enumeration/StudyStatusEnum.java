package com.iworkstation.imageserver.enumeration;


public enum StudyStatusEnum {
	Online("Online", "Study is online"), OnlineLossless("OnlineLossless",
			"Study is online and lossless compressed"), OnlineLossy(
			"OnlineLossy", "Study is online and lossy compressed"), Nearline(
			"Nearline", "The study is nearline (in an automated library)");

	private String lookup;
	private String description;

	private StudyStatusEnum(String lookup, String description) {
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
