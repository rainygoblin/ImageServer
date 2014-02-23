package com.iworkstation.imageserver.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.parameter.InsertInstanceParameters;
import com.iworkstation.imageserver.parameter.InstanceKeys;

public interface IStudyManager {

	Study findStudyBySopInstanceUid(String studyInstanceUid);

	Study findStudy(String studyInstanceUid, String partitionGUID);

	List<Study> findStudyByCriterions(List<Criterion> criterions,
			Map<String, List<Criterion>> associationCriterions);

	InstanceKeys insertInstance(
			InsertInstanceParameters insertInstanceParameters);

	Study loadByID(String guid);
}
