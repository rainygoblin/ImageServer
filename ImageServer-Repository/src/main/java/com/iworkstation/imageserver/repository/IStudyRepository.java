package com.iworkstation.imageserver.repository;

import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.parameter.InsertInstanceParameters;
import com.iworkstation.imageserver.parameter.InstanceKeys;
import com.iworkstation.imageserver.parameter.LockStudyParameters;

public interface IStudyRepository extends IGenericRepository<String, Study> {

	boolean lockStudy(LockStudyParameters lockStudyParameters);

	InstanceKeys insertInstance(
			InsertInstanceParameters insertInstanceParameters);

}
