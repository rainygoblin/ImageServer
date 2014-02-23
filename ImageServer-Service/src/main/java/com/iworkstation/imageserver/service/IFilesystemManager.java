package com.iworkstation.imageserver.service;

import com.iworkstation.imageserver.domain.ServerFilesystemInfo;
import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.service.exception.NoWritableFilesystemException;
import com.iworkstation.imageserver.service.exception.StudyIsNearlineException;
import com.iworkstation.imageserver.service.exception.StudyNotFoundException;

public interface IFilesystemManager {
	ServerFilesystemInfo selectFilesystem();

	boolean checkFilesystemWriteable(String filesystemGUID);

	boolean getWriteableIncomingFolder(ServerPartition partition, String folder);

	StudyStorageLocation getReadableStudyStorageLocation(
			String partitionGUID, String studyInstanceUid, boolean restore)
			throws StudyIsNearlineException, StudyNotFoundException;

	StudyStorageLocation getOrCreateWritableStudyStorageLocation(
			String studyInstanceUid, String studyDate, String syntax,
			String partitionGUID) throws NoWritableFilesystemException,
			StudyIsNearlineException;

	StudyStorageLocation getWritableStudyStorageLocation(
			String studyStorageGUID);

	StudyStorageLocation getWritableStudyStorageLocation(
			String partitionGUID, String studyInstanceUid, boolean restore)
			throws StudyIsNearlineException, StudyNotFoundException;
}
