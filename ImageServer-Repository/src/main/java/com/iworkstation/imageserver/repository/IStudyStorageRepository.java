package com.iworkstation.imageserver.repository;

import java.util.List;

import com.iworkstation.imageserver.domain.StudyStorage;
import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.parameter.InsertStudyStorageParameters;
import com.iworkstation.imageserver.parameter.LockStudyParameters;
import com.iworkstation.imageserver.parameter.StudyStorageLocationQueryParameters;

public interface IStudyStorageRepository extends
		IGenericRepository<String, StudyStorage> {
	List<StudyStorageLocation> queryStudyStorageLocation(
			StudyStorageLocationQueryParameters studyStorageLocationQueryParameters);

	StudyStorageLocation insertStudyStorage(
			InsertStudyStorageParameters insertStudyStorageParameters);

	void lockStudy(LockStudyParameters lockStudyParameters);

}
