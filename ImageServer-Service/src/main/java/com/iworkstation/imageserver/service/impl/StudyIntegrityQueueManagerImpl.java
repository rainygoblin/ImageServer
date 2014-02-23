package com.iworkstation.imageserver.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iworkstation.imageserver.domain.StudyIntegrityQueue;
import com.iworkstation.imageserver.domain.StudyIntegrityQueueUid;
import com.iworkstation.imageserver.repository.IGenericRepository;
import com.iworkstation.imageserver.service.IStudyIntegrityQueueManager;

@Service
public class StudyIntegrityQueueManagerImpl implements
		IStudyIntegrityQueueManager {
	@Autowired
	private IGenericRepository<String, StudyIntegrityQueueUid> studyIntegrityQueueUidRepository;
	@Autowired
	private IGenericRepository<String, StudyIntegrityQueue> studyIntegrityQueueRepository;

	@Override
	@Transactional
	public boolean studyIntegrityUidExists(String studyStorageGUID,
			String seriesInstanceUid, String sopInstanceUid) {
		boolean result = false;
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("seriesInstanceUid", seriesInstanceUid);
		parms.put("sopInstanceUid", sopInstanceUid);
		StudyIntegrityQueueUid studyIntegrityQueueUid = studyIntegrityQueueUidRepository
				.findUniqueByProps(parms);
		if (studyIntegrityQueueUid != null) {
			StudyIntegrityQueue studyIntegrityQueue = studyIntegrityQueueRepository
					.findById(studyIntegrityQueueUid
							.getStudyIntegrityQueueGUID());
			if (studyIntegrityQueue != null) {
				if (studyIntegrityQueue.getStudyStorageGUID().equals(
						studyStorageGUID)) {
					result = true;
				}
			}
		}
		return result;
	}

}
