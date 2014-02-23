package com.iworkstation.imageserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iworkstation.imageserver.domain.StudyStorage;
import com.iworkstation.imageserver.parameter.LockStudyParameters;
import com.iworkstation.imageserver.repository.IStudyStorageRepository;
import com.iworkstation.imageserver.service.IStudyStorageManager;

@Service
public class StudyStorageManagerImpl implements IStudyStorageManager {
	@Autowired
	private IStudyStorageRepository studyStorageRepository;

	@Override
	@Transactional
	public void lockStudy(LockStudyParameters lockStudyParameters) {
		studyStorageRepository.lockStudy(lockStudyParameters);
	}

	@Override
	@Transactional
	public StudyStorage loadByID(String studyStorageGUID) {
		return studyStorageRepository.findById(studyStorageGUID);
	}

}
