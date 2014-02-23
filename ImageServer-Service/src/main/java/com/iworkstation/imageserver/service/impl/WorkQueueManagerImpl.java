package com.iworkstation.imageserver.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iworkstation.imageserver.domain.WorkQueue;
import com.iworkstation.imageserver.domain.WorkQueueTypeProperties;
import com.iworkstation.imageserver.domain.WorkQueueUid;
import com.iworkstation.imageserver.enumeration.WorkQueueTypeEnum;
import com.iworkstation.imageserver.parameter.InsertWorkQueueParameters;
import com.iworkstation.imageserver.parameter.PostponeWorkQueueParameters;
import com.iworkstation.imageserver.parameter.UpdateWorkQueueParameters;
import com.iworkstation.imageserver.parameter.WorkQueueQueryParameters;
import com.iworkstation.imageserver.repository.IGenericRepository;
import com.iworkstation.imageserver.repository.IWorkQueueRepository;
import com.iworkstation.imageserver.service.IWorkQueueManager;

@Service
public class WorkQueueManagerImpl implements IWorkQueueManager {

	@Autowired
	private IWorkQueueRepository workQueueRepository;
	@Autowired
	private IGenericRepository<String, WorkQueueUid> workQueueUidRepository;
	@Autowired
	private IGenericRepository<String, WorkQueueTypeProperties> workQueueTypePropertiesRepository;

	@Override
	@Transactional
	public WorkQueue insertWorkQueue(
			InsertWorkQueueParameters insertWorkQueueParameters) {
		WorkQueue workQueue = workQueueRepository
				.InsertWorkQueue(insertWorkQueueParameters);

		return workQueue;

	}

	@Override
	@Transactional
	public boolean workQueueUidExists(String studyStorageGUID,
			String seriesInstanceUid, String sopInstanceUid) {
		boolean result = false;
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("seriesInstanceUid", seriesInstanceUid);
		parms.put("sopInstanceUid", sopInstanceUid);
		WorkQueueUid workQueueUid = workQueueUidRepository
				.findUniqueByProps(parms);
		if (workQueueUid != null) {
			WorkQueue workQueue = workQueueRepository.findById(workQueueUid
					.getWorkQueueGUID());
			if (workQueue != null) {
				if (workQueue.getStudyStorageGUID().equals(studyStorageGUID)) {
					result = true;
				}

			}
		}
		return result;
	}

	@Override
	@Transactional
	public void deleteWorkQueueUid(WorkQueueUid uid) {
		WorkQueueUid workQueueUid = workQueueUidRepository.findById(uid
				.getGuid());
		workQueueUidRepository.delete(workQueueUid);
	}

	@Override
	@Transactional
	public WorkQueue queryWorkQueue(WorkQueueQueryParameters parms) {
		return workQueueRepository.queryWorkQueue(parms);

	}

	@Override
	@Transactional
	public void updateWorkQueue(UpdateWorkQueueParameters parms) {
		workQueueRepository.updateWorkQueue(parms);
	}

	@Transactional
	@Override
	public List<WorkQueueUid> findWorkQueueUidByWorkQueueGUID(
			String workQueueGUID) {
		return workQueueUidRepository
				.findByProp("workQueueGUID", workQueueGUID);

	}

	@Override
	@Transactional
	public WorkQueueTypeProperties getWorkQueueProperties(
			WorkQueueTypeEnum workQueueType) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion criterion = Restrictions.eq("workQueueTypeEnum",
				workQueueType);
		criterionList.add(criterion);

		List<WorkQueueTypeProperties> workQueueTypePropertiesList = workQueueTypePropertiesRepository
				.findEntityByCriterions(criterionList, null);
		if (workQueueTypePropertiesList != null) {
			if (workQueueTypePropertiesList.size() > 0) {
				return workQueueTypePropertiesList.get(0);
			}
		}
		return null;
	}

	@Override
	@Transactional
	public List<WorkQueue> findWorkQueueByStudyStorage(String studyStorageGUID) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Criterion criterion = Restrictions.eq("studyStorageGUID",
				studyStorageGUID);
		criterionList.add(criterion);

		return workQueueRepository.findEntityByCriterions(criterionList, null);

	}

	@Override
	@Transactional
	public void postponeWorkQueue(PostponeWorkQueueParameters parameters) {
		workQueueRepository.postponeWorkQueue(parameters);
	}

}
