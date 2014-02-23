package com.iworkstation.imageserver.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iworkstation.imageserver.domain.Filesystem;
import com.iworkstation.imageserver.domain.RestoreQueue;
import com.iworkstation.imageserver.domain.ServerFilesystemInfo;
import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.domain.ServerTransferSyntax;
import com.iworkstation.imageserver.domain.StudyStorage;
import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.enumeration.QueueStudyStateEnum;
import com.iworkstation.imageserver.enumeration.StudyStatusEnum;
import com.iworkstation.imageserver.parameter.InsertRestoreQueueParameters;
import com.iworkstation.imageserver.parameter.InsertStudyStorageParameters;
import com.iworkstation.imageserver.parameter.StudyStorageLocationQueryParameters;
import com.iworkstation.imageserver.repository.IGenericRepository;
import com.iworkstation.imageserver.repository.IRestoreQueueRepository;
import com.iworkstation.imageserver.repository.IStudyStorageRepository;
import com.iworkstation.imageserver.service.IFilesystemManager;
import com.iworkstation.imageserver.service.exception.NoWritableFilesystemException;
import com.iworkstation.imageserver.service.exception.StudyIsNearlineException;
import com.iworkstation.imageserver.service.exception.StudyNotFoundException;

@Service
public class FilesystemManagerImpl implements IFilesystemManager {
	private static final Log LOG = LogFactory
			.getLog(FilesystemManagerImpl.class);

	@Autowired
	private IGenericRepository<String, ServerPartition> serverPartitionRepository;
	@Autowired
	private IStudyStorageRepository studyStorageRepository;
	@Autowired
	private IGenericRepository<String, ServerTransferSyntax> serverTransferSyntaxRepository;
	@Autowired
	private IGenericRepository<String, Filesystem> filesystemRepository;
	@Autowired
	private IRestoreQueueRepository restoreQueueRepository;

	@Override
	@Transactional
	public boolean checkFilesystemWriteable(String filesystemGUID) {
		Filesystem filesystem = filesystemRepository.findById(filesystemGUID);

		if (filesystem == null) {
			return false;
		}

		ServerFilesystemInfo info = new ServerFilesystemInfo(filesystem);

		return info.getWriteable();
	}

	@Override
	@Transactional
	public boolean getWriteableIncomingFolder(ServerPartition partition,
			String folder) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public StudyStorageLocation getOrCreateWritableStudyStorageLocation(
			String studyInstanceUid, String studyDate, String syntax,
			String partitionGUID) throws NoWritableFilesystemException,
			StudyIsNearlineException {
		StudyStorageLocation location = null;
		try {
			location = getWritableStudyStorageLocation(partitionGUID,
					studyInstanceUid, true);
			return location;
		} catch (StudyNotFoundException e) {
		}

		ServerFilesystemInfo serverFilesystemInfo = selectFilesystem();
		if (serverFilesystemInfo == null) {

			throw new NoWritableFilesystemException();

		}

		InsertStudyStorageParameters insertParms = new InsertStudyStorageParameters();
		insertParms.setServerPartitionGUID(partitionGUID);
		insertParms.setStudyInstanceUid(studyInstanceUid);
		insertParms.setFolder(studyDate);
		insertParms.setFilesystemGUID(serverFilesystemInfo.getFilesystem()
				.getGuid());
		insertParms
				.setQueueStudyStateEnum(QueueStudyStateEnum.Idle.getLookup());
		insertParms.setTransferSyntaxUid(syntax);

		StudyStatusEnum studyStatusEnum = null;

		List<ServerTransferSyntax> ServerTransferSyntaxs = serverTransferSyntaxRepository
				.loadAll();
		for (ServerTransferSyntax serverTransferSyntax : ServerTransferSyntaxs) {
			if (syntax.equals(serverTransferSyntax.getGuid())) {
				if (serverTransferSyntax.isLossless()) {
					studyStatusEnum = StudyStatusEnum.OnlineLossless;
					break;
				} else {
					studyStatusEnum = StudyStatusEnum.OnlineLossy;
					break;
				}
			}
		}
		if (studyStatusEnum == null) {
			studyStatusEnum = StudyStatusEnum.Online;
		}

		insertParms.setStudyStatusEnum(studyStatusEnum.getLookup());

		location = studyStorageRepository.insertStudyStorage(insertParms);

		loadStudyLocation(location);
		return location;

	}

	@Override
	@Transactional
	public ServerFilesystemInfo selectFilesystem() {
		ServerFilesystemInfo result = null;
		for (Filesystem filesystem : filesystemRepository.loadAll()) {
			ServerFilesystemInfo serverFilesystemInfo = new ServerFilesystemInfo(
					filesystem);
			if (serverFilesystemInfo.getEnable()) {
				if (result != null) {
					if (serverFilesystemInfo.getFreeBytes() > result
							.getFreeBytes()) {
						result = serverFilesystemInfo;
					}
				} else {
					result = serverFilesystemInfo;
				}
			}
		}
		return result;
	}

	@Transactional
	public StudyStorageLocation getWritableStudyStorageLocation(
			String partitionGUID, String studyInstanceUid, boolean restore)
			throws StudyIsNearlineException, StudyNotFoundException {
		StudyStorageLocation location = null;

		StudyStorageLocationQueryParameters parms = new StudyStorageLocationQueryParameters();
		parms.setServerPartitionGUID(partitionGUID);
		parms.setStudyInstanceUid(studyInstanceUid);

		List<StudyStorageLocation> locationList = studyStorageRepository
				.queryStudyStorageLocation(parms);

		for (StudyStorageLocation studyLocation : locationList) {
			if (checkFilesystemWriteable(studyLocation.getFilesystemGUID())) {
				location = studyLocation;
				break;
			}

		}

		if (location != null) {
			loadStudyLocation(location);

			// location.setStudyStorage(studyStorageRepository.findById(location.getGuid()));
			if (!location.getQueueStudyStateEnum().equals(
					QueueStudyStateEnum.Idle.getLookup())
					&& (!location.getQueueStudyStateEnum()
							.equals(QueueStudyStateEnum.ProcessingScheduled
									.getLookup()))) {
				String failureMessage = String
						.format("Study %s on partition %s is being processed: %s, can't accept new images.",
								studyInstanceUid, partitionGUID,
								location.getQueueStudyStateEnum());
				LOG.error(failureMessage);
				location = null;
			}
		}
		if (location == null) {
			checkForStudyRestore(partitionGUID, studyInstanceUid, restore);
		}
		return location;

	}

	@Override
	@Transactional
	public StudyStorageLocation getReadableStudyStorageLocation(
			String partitionGUID, String studyInstanceUid, boolean restore)
			throws StudyIsNearlineException, StudyNotFoundException {
		StudyStorageLocation location = null;
		StudyStorageLocationQueryParameters parms = new StudyStorageLocationQueryParameters();
		parms.setServerPartitionGUID(partitionGUID);
		parms.setStudyInstanceUid(studyInstanceUid);
		List<StudyStorageLocation> locationList = studyStorageRepository
				.queryStudyStorageLocation(parms);

		for (StudyStorageLocation studyLocation : locationList) {
			if (checkFilesystemReadable(studyLocation.getFilesystemGUID())) {
				location = studyLocation;
				loadStudyLocation(location);
				return location;
			}

		}
		checkForStudyRestore(partitionGUID, studyInstanceUid, restore);
		return location;
	}

	private boolean checkFilesystemReadable(String filesystemGUID) {
		ServerFilesystemInfo info = null;

		Filesystem filesystem = filesystemRepository.findById(filesystemGUID);

		if (filesystem == null) {
			return false;
		}
		info = new ServerFilesystemInfo(filesystem);

		return info.getReadable();
	}

	@Override
	@Transactional
	public StudyStorageLocation getWritableStudyStorageLocation(
			String studyStorageGUID) {
		StudyStorageLocation location = null;
		StudyStorageLocationQueryParameters studyStorageLocationQueryParameters = new StudyStorageLocationQueryParameters();
		studyStorageLocationQueryParameters
				.setStudyStorageGUID(studyStorageGUID);

		List<StudyStorageLocation> locationList = studyStorageRepository
				.queryStudyStorageLocation(studyStorageLocationQueryParameters);

		for (StudyStorageLocation studyLocation : locationList) {
			if (checkFilesystemReadable(studyLocation.getFilesystemGUID())) {
				location = studyLocation;
				loadStudyLocation(location);
				return location;
			}

		}
		return location;
	}

	private void loadStudyLocation(StudyStorageLocation studyStorageLocation) {
		ServerPartition serverPartition = serverPartitionRepository
				.findById(studyStorageLocation.getServerPartitionGUID());
		if (serverPartition != null) {
			studyStorageLocation.setServerPartition(serverPartition);
		}

		StudyStorage studyStorage = studyStorageRepository
				.findById(studyStorageLocation.getGuid());
		if (studyStorage != null) {
			studyStorageLocation.setStudyStorage(studyStorage);
		}

	}

	private void checkForStudyRestore(String partitionGUID,
			String studyInstanceUid, boolean restore)
			throws StudyIsNearlineException, StudyNotFoundException {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("studyInstanceUid", studyInstanceUid);
		parms.put("serverPartitionGUID", partitionGUID);
		StudyStorage storage = studyStorageRepository.findUniqueByProps(parms);
		if (storage != null) {
			if (restore == true) {
				InsertRestoreQueueParameters insertRestoreQueueParameters = new InsertRestoreQueueParameters();
				insertRestoreQueueParameters.setStudyStorageGUID(storage
						.getGuid());
				RestoreQueue restoreRq = restoreQueueRepository
						.insertRestoreQueue(insertRestoreQueueParameters);
				if (restoreRq != null)
					throw new StudyIsNearlineException(true);
			}

			throw new StudyIsNearlineException(false);
		}

		throw new StudyNotFoundException(studyInstanceUid);
	}
}
