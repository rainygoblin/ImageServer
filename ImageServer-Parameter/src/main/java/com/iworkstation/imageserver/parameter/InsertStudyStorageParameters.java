package com.iworkstation.imageserver.parameter;

public class InsertStudyStorageParameters {

	private String serverPartitionGUID;
	private String studyInstanceUid;
	private String filesystemGUID;
	private String folder;
	private String transferSyntaxUid;
	private String studyStatusEnum;
	private String queueStudyStateEnum;

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

	public String getFilesystemGUID() {
		return filesystemGUID;
	}

	public void setFilesystemGUID(String filesystemGUID) {
		this.filesystemGUID = filesystemGUID;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getTransferSyntaxUid() {
		return transferSyntaxUid;
	}

	public void setTransferSyntaxUid(String transferSyntaxUid) {
		this.transferSyntaxUid = transferSyntaxUid;
	}

	public String getStudyStatusEnum() {
		return studyStatusEnum;
	}

	public void setStudyStatusEnum(String studyStatusEnum) {
		this.studyStatusEnum = studyStatusEnum;
	}

	public String getQueueStudyStateEnum() {
		return queueStudyStateEnum;
	}

	public void setQueueStudyStateEnum(String queueStudyStateEnum) {
		this.queueStudyStateEnum = queueStudyStateEnum;
	}

}
