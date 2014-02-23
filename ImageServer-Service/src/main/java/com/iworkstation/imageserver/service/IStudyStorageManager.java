package com.iworkstation.imageserver.service;

import com.iworkstation.imageserver.domain.StudyStorage;
import com.iworkstation.imageserver.parameter.LockStudyParameters;

public interface IStudyStorageManager {

	void lockStudy(LockStudyParameters lockStudyParameters);

	StudyStorage loadByID(String studyStorageGUID);
}
