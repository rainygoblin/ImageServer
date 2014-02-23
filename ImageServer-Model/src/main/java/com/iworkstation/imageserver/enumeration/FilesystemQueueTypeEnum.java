package com.iworkstation.imageserver.enumeration;


public enum FilesystemQueueTypeEnum {
	DeleteStudy(
			"DeleteStudy",
			"A record telling when a study is eligible for deletion.  The study will be completely removed from the system."), PurgeStudy(
			"PurgeStudy",
			"A record telling when a study can be purged from a filesystem.  Only archived studies can be purged.  The study will remain archived and can be restored."), TierMigrate(
			"TierMigrate",
			"A record telling when a study is eligible to be migrated to a lower tier filesystem."), LosslessCompress(
			"LosslessCompress",
			"A record telling when a study is eligible for lossless compression and the type of compression to be performed on the study."), LossyCompress(
			"LossyCompress",
			"A record telling when a study is eligible for lossy compression and the type of compression to be performed.");

	private String lookup;
	private String description;

	private FilesystemQueueTypeEnum(String lookup, String description) {
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
