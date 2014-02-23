package com.iworkstation.imageserver.service;

public interface IStudyIntegrityQueueManager {
	boolean studyIntegrityUidExists(String studyStorageGUID,
			String seriesInstanceUid, String sopInstanceUid);
}
