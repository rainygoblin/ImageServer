package com.iworkstation.imageserver.parameter;

public class InsertRestoreQueueParameters {
	private String studyStorageGUID;
	private String archiveStudyStorageGUID;

	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	public String getArchiveStudyStorageGUID() {
		return archiveStudyStorageGUID;
	}

	public void setArchiveStudyStorageGUID(String archiveStudyStorageGUID) {
		this.archiveStudyStorageGUID = archiveStudyStorageGUID;
	}

}
