package com.iworkstation.imageserver.parameter;

public class StudyStorageLocationQueryParameters {
	private String studyStorageGUID;
	private String serverPartitionGUID;
	private String studyInstanceUid;

	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	public String getServerPartitionGUID() {
		return serverPartitionGUID;
	}

	public void setServerPartitionGUID(String serverPartitionGUID) {
		this.serverPartitionGUID = serverPartitionGUID;
	}

	public String getStudyInstanceUid() {
		return studyInstanceUid;
	}

	public void setStudyInstanceUid(String studyInstanceUid) {
		this.studyInstanceUid = studyInstanceUid;
	}
}
