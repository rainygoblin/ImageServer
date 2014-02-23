package com.iworkstation.imageserver.domain;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class StudyStorageLocation {

	private String guid;
	private String studyInstanceUid;
	private String serverPartitionGUID;
	private Date lastAccessedTime;
	private Date insertTime;
	private String studyStatusEnum;
	private String filesystemPath;
	private String partitionFolder;
	private String studyFolder;
	private String filesystemGUID;
	private boolean enabled;
	private boolean readOnly;
	private boolean writeOnly;
	private String filesystemTierEnum;
	private boolean writeLock;
	private short readLock;
	private String serverTransferSyntaxGUID;
	private String transferSyntaxUid;
	private String filesystemStudyStorageGUID;
	private String queueStudyStateEnum;
	private boolean isReconcileRequired;

	private ServerPartition serverPartition;
	private StudyStorage studyStorage;
	private FilesystemStudyStorage filesystemStudyStorage;
	private ObjectMapper mapper = new ObjectMapper();

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getStudyInstanceUid() {
		return studyInstanceUid;
	}

	public void setStudyInstanceUid(String studyInstanceUid) {
		this.studyInstanceUid = studyInstanceUid;
	}

	public String getServerPartitionGUID() {
		return serverPartitionGUID;
	}

	public void setServerPartitionGUID(String serverPartitionGUID) {
		this.serverPartitionGUID = serverPartitionGUID;
	}

	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(Date lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getStudyStatusEnum() {
		return studyStatusEnum;
	}

	public void setStudyStatusEnum(String studyStatusEnum) {
		this.studyStatusEnum = studyStatusEnum;
	}

	public String getFilesystemPath() {
		return filesystemPath;
	}

	public void setFilesystemPath(String filesystemPath) {
		this.filesystemPath = filesystemPath;
	}

	public String getPartitionFolder() {
		return partitionFolder;
	}

	public void setPartitionFolder(String partitionFolder) {
		this.partitionFolder = partitionFolder;
	}

	public String getStudyFolder() {
		return studyFolder;
	}

	public void setStudyFolder(String studyFolder) {
		this.studyFolder = studyFolder;
	}

	public String getFilesystemGUID() {
		return filesystemGUID;
	}

	public void setFilesystemGUID(String filesystemGUID) {
		this.filesystemGUID = filesystemGUID;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isWriteOnly() {
		return writeOnly;
	}

	public void setWriteOnly(boolean writeOnly) {
		this.writeOnly = writeOnly;
	}

	public String getFilesystemTierEnum() {
		return filesystemTierEnum;
	}

	public void setFilesystemTierEnum(String filesystemTierEnum) {
		this.filesystemTierEnum = filesystemTierEnum;
	}

	public boolean isWriteLock() {
		return writeLock;
	}

	public void setWriteLock(boolean writeLock) {
		this.writeLock = writeLock;
	}

	public short getReadLock() {
		return readLock;
	}

	public void setReadLock(short readLock) {
		this.readLock = readLock;
	}

	public String getServerTransferSyntaxGUID() {
		return serverTransferSyntaxGUID;
	}

	public void setServerTransferSyntaxGUID(String serverTransferSyntaxGUID) {
		this.serverTransferSyntaxGUID = serverTransferSyntaxGUID;
	}

	public String getTransferSyntaxUid() {
		return transferSyntaxUid;
	}

	public void setTransferSyntaxUid(String transferSyntaxUid) {
		this.transferSyntaxUid = transferSyntaxUid;
	}

	public String getFilesystemStudyStorageGUID() {
		return filesystemStudyStorageGUID;
	}

	public void setFilesystemStudyStorageGUID(String filesystemStudyStorageGUID) {
		this.filesystemStudyStorageGUID = filesystemStudyStorageGUID;
	}

	public String getQueueStudyStateEnum() {
		return queueStudyStateEnum;
	}

	public void setQueueStudyStateEnum(String queueStudyStateEnum) {
		this.queueStudyStateEnum = queueStudyStateEnum;
	}

	public boolean isIsReconcileRequired() {
		return isReconcileRequired;
	}

	public void setIsReconcileRequired(boolean isReconcileRequired) {
		this.isReconcileRequired = isReconcileRequired;
	}

	public boolean isReconcileRequired() {
		return isReconcileRequired;
	}

	public void setReconcileRequired(boolean isReconcileRequired) {
		this.isReconcileRequired = isReconcileRequired;
	}

	public ServerPartition getServerPartition() {
		return serverPartition;
	}

	public void setServerPartition(ServerPartition serverPartition) {
		this.serverPartition = serverPartition;
	}

	public StudyStorage getStudyStorage() {
		return studyStorage;
	}

	public void setStudyStorage(StudyStorage studyStorage) {
		this.studyStorage = studyStorage;
	}

	public FilesystemStudyStorage getFilesystemStudyStorage() {
		return filesystemStudyStorage;
	}

	public void setFilesystemStudyStorage(
			FilesystemStudyStorage filesystemStudyStorage) {
		this.filesystemStudyStorage = filesystemStudyStorage;
	}

	// / <summary>
	// / Returns a boolean indicating whether the study has been archived and
	// the latest
	// / copy in the archive is lossless.
	// / </summary>
	public boolean isLatestArchiveLossless() {
		return true;
		// get
		// {
		// if (ArchiveLocations == null || ArchiveLocations.Count == 0)
		// return false;
		// return ArchiveLocations[0].ServerTransferSyntax.Lossless;
		// }
	}

	public String getSopInstancePath(String seriesInstanceUid,
			String sopInstanceUid) {
		String path = FilenameUtils.concat(GetSeriesPath(seriesInstanceUid),
				sopInstanceUid + ".dcm");
		return path;
	}

	public String getReconcileSopInstancePath(String aeTitle,
			String studyInstanceUid, String seriesInstanceUid,
			String sopInstanceUid) {
		String path = FilenameUtils.concat(aeTitle, studyInstanceUid);
		path = FilenameUtils.concat(path, seriesInstanceUid);
		path = FilenameUtils.concat(path, sopInstanceUid);
		path += ".dup";
		return path;
	}

	// / <summary>
	// / Returns the path of the folder for the specified series.
	// / </summary>
	// / <param name="seriesInstanceUid"></param>

	public String GetSeriesPath(String seriesInstanceUid) {
		return FilenameUtils.concat(getStudyPath(), seriesInstanceUid);
	}

	// / <summary>
	// / Returns the path of the folder for the StudyStorageLocation.
	// / </summary>
	// / <returns></returns>
	public String getStudyPath() {
		String path = FilenameUtils.concat(partitionFolder, studyFolder);
		path = FilenameUtils.concat(path, studyInstanceUid);
		return FilenameUtils.concat(filesystemPath, path);

	}

	public Study loadStudyDTOFromJSON() {
		String streamFile = FilenameUtils.concat(getStudyPath(),
				studyInstanceUid + ".json");
		try {
			File file = new File(streamFile);
			if (file.exists())
				return mapper.readValue(new File(streamFile), Study.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Study studyDTO = new Study();
		studyDTO.setStudyInstanceUid(studyInstanceUid);
		return studyDTO;

	}

	public void writeStudyToJSON(Study studyDTO)
			throws JsonGenerationException, JsonMappingException, IOException {
		String streamFile = FilenameUtils.concat(getStudyPath(),
				studyInstanceUid + ".json");

		mapper.writeValue(new File(streamFile), studyDTO);

	}
}
