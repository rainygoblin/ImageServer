package com.iworkstation.imageserver.enumeration;

public enum ServiceLockTypeEnum {
	FilesystemDelete(
			"FilesystemDelete",
			"This services checks if a filesystem is above its high watermark.  If the filesystem is above the high watermark it migrates studies, deletes studies, and purges studies until the low watermark is reached."), FilesystemReinventory(
			"FilesystemReinventory",
			"This service re-inventories the studies stored on a filesystem.  It scans the contents of the filesystem, and if a study is not already stored in the database, it will insert records to process the study into the WorkQueue."), FilesystemStudyProcess(
			"FilesystemStudyProcess",
			"This service scans the contents of a filesystem and reapplies Study Processing rules to all studies on the filesystem."), FilesystemLosslessCompress(
			"FilesystemLosslessCompress",
			"This service checks for studies that are eligible to be lossless compressed on a filesystem.  It works independently from the watermarks configured for the filesystem and will insert records into the WorkQueue to compress the studies as soon as they are eligible."), FilesystemLossyCompress(
			"FilesystemLossyCompress",
			"This service checks for studies that are eligible to be lossy compressed on a filesystem.  It works independently from the watermarks configured for the filesystem and will insert records into the WorkQueue to compress the studies as soon as they are eligible."), FilesystemRebuildXml(
			"FilesystemRebuildXml",
			"Rebuild the Study XML file for each study stored on the Filesystem"), ArchiveApplicationLog(
			"ArchiveApplicationLog",
			"This service removes application log entries from the database and archives them in zip files to a filesystem.  When initially run, it selects a filesystem from the lowest filesystem tier configured on the system."), PurgeAlerts(
			"PurgeAlerts",
			"This service by default removes Alert records from the database after a configurable time.  If configured it can save the alerts in zip files on a filesystem.  When initially run, it selects a filesystem from the lowest filesystem tier configured on the system to archive to."), ImportFiles(
			"ImportFiles",
			"This service periodically scans the filesystem for dicom files and imports them into the system.");

	private String lookup;
	private String description;

	private ServiceLockTypeEnum(String lookup, String description) {
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
