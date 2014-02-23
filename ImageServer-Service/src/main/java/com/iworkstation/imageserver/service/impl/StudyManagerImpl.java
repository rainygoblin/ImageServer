package com.iworkstation.imageserver.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.parameter.InsertInstanceParameters;
import com.iworkstation.imageserver.parameter.InstanceKeys;
import com.iworkstation.imageserver.repository.IStudyRepository;
import com.iworkstation.imageserver.service.IStudyManager;

@Service
public class StudyManagerImpl implements IStudyManager {

	@Autowired
	private IStudyRepository studyRepository;

	@Override
	@Transactional
	public Study findStudyBySopInstanceUid(String studyInstanceUid) {
		return studyRepository.findUniqueByProp("studyInstanceUid",
				studyInstanceUid);

	}

	@Override
	@Transactional
	public Study findStudy(String studyInstanceUid, String partitionGUID) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("studyInstanceUid", studyInstanceUid);
		parms.put("serverPartitionGUID", partitionGUID);
		return studyRepository.findUniqueByProps(parms);

	}

	@Override
	@Transactional
	public List<Study> findStudyByCriterions(List<Criterion> criterions,
			Map<String, List<Criterion>> associationCriterions) {
		return studyRepository.findEntityByCriterions(criterions,
				associationCriterions);

	}

	@Override
	@Transactional
	public InstanceKeys insertInstance(
			InsertInstanceParameters insertInstanceParameters) {
		return studyRepository.insertInstance(insertInstanceParameters);
	}

	@Override
	@Transactional
	public Study loadByID(String guid) {
		return studyRepository.findById(guid);
	}
}
