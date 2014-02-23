package com.iworkstation.imageserver.repository.impl;

import org.hibernate.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.iworkstation.imageserver.domain.WorkQueue;
import com.iworkstation.imageserver.parameter.InsertWorkQueueParameters;
import com.iworkstation.imageserver.parameter.PostponeWorkQueueParameters;
import com.iworkstation.imageserver.parameter.UpdateWorkQueueParameters;
import com.iworkstation.imageserver.parameter.WorkQueueQueryParameters;
import com.iworkstation.imageserver.repository.IWorkQueueRepository;

@Repository
public class HibernateWorkQueueRepository extends
		GenericRepositoryImpl<String, WorkQueue> implements
		IWorkQueueRepository {

	public HibernateWorkQueueRepository(){
		super.setEntityClass(WorkQueue.class);
	}
	
	@Override
	public WorkQueue InsertWorkQueue(
			InsertWorkQueueParameters insertWorkQueueParameters) {
		Query query = getCurrentSession().getNamedQuery("InsertWorkQueue");

		query.setParameter("StudyStorageGUID",
				insertWorkQueueParameters.getStudyStorageGUID());

		query.setParameter("ServerPartitionGUID",
				insertWorkQueueParameters.getServerPartitionGUID());

		query.setString("WorkQueueTypeEnum",
				insertWorkQueueParameters.getWorkQueueTypeEnum());

		query.setTimestamp("ScheduledTime",
				insertWorkQueueParameters.getScheduledTime());

		if (insertWorkQueueParameters.getDeviceGUID() == null) {
			query.setParameter("DeviceGUID", null, StandardBasicTypes.STRING);
		} else {
			query.setString("DeviceGUID",
					insertWorkQueueParameters.getDeviceGUID());
		}

		if (insertWorkQueueParameters.getStudyHistoryGUID() == null) {
			query.setParameter("StudyHistoryGUID", null,
					StandardBasicTypes.STRING);
		} else {
			query.setString("StudyHistoryGUID",
					insertWorkQueueParameters.getStudyHistoryGUID());
		}

		if (insertWorkQueueParameters.getWorkQueueData() == null) {
			query.setParameter("Data", null, StandardBasicTypes.STRING);
		} else {
			query.setString("Data",
					insertWorkQueueParameters.getWorkQueueData());
		}

		if (insertWorkQueueParameters.getSeriesInstanceUid() == null) {
			query.setParameter("SeriesInstanceUid", null,
					StandardBasicTypes.STRING);
		} else {
			query.setString("SeriesInstanceUid",
					insertWorkQueueParameters.getSeriesInstanceUid());
		}

		if (insertWorkQueueParameters.getSopInstanceUid() == null) {
			query.setParameter("SopInstanceUid", null,
					StandardBasicTypes.STRING);
		} else {
			query.setString("SopInstanceUid",
					insertWorkQueueParameters.getSopInstanceUid());
		}

		query.setBoolean("Duplicate", insertWorkQueueParameters.isDuplicate());

		query.setString("Extension", insertWorkQueueParameters.getExtension());

		query.setString("WorkQueueGroupID",
				insertWorkQueueParameters.getWorkQueueGroupID());

		query.setString("UidGroupID", insertWorkQueueParameters.getUidGroupID());

		query.setString("UidRelativePath",
				insertWorkQueueParameters.getUidRelativePath());

		return (WorkQueue) query.uniqueResult();
	}

	@Override
	public WorkQueue queryWorkQueue(
			WorkQueueQueryParameters workQueueQueryParameters) {
		Query query = getCurrentSession().getNamedQuery("QueryWorkQueue");

		query.setParameter("ProcessorID",
				workQueueQueryParameters.getProcessorID());

		if (workQueueQueryParameters.getWorkQueuePriorityEnum() == null) {
			query.setParameter("WorkQueuePriorityEnum", null,
					StandardBasicTypes.STRING);
		} else {
			query.setString("WorkQueuePriorityEnum",
					workQueueQueryParameters.getWorkQueuePriorityEnum());
		}

		if (workQueueQueryParameters.isMemoryLimited() == null) {
			query.setParameter("MemoryLimited", null,
					StandardBasicTypes.BOOLEAN);
		} else {
			query.setBoolean("MemoryLimited",
					workQueueQueryParameters.isMemoryLimited());
		}
		return (WorkQueue) query.uniqueResult();
	}

	@Override
	public void updateWorkQueue(
			UpdateWorkQueueParameters updateWorkQueueParameters) {
		Query query = getCurrentSession()
				.createSQLQuery(
						"{call UpdateWorkQueue(:ProcessorID,:WorkQueueGUID,:StudyStorageGUID,:WorkQueueStatusEnum,:FailureCount,:ExpirationTime,:ScheduledTime,:FailureDescription,:QueueStudyStateEnum )}");

		query.setParameter("ProcessorID",
				updateWorkQueueParameters.getProcessorID());

		query.setParameter("WorkQueueGUID",
				updateWorkQueueParameters.getWorkQueueGUID());

		query.setParameter("StudyStorageGUID",
				updateWorkQueueParameters.getStudyStorageGUID());

		query.setParameter("WorkQueueStatusEnum",
				updateWorkQueueParameters.getWorkQueueStatusEnum());

		query.setParameter("FailureCount",
				updateWorkQueueParameters.getFailureCount());

		if (updateWorkQueueParameters.getExpirationTime() == null) {
			query.setParameter("ExpirationTime", null, StandardBasicTypes.DATE);
		} else {
			query.setParameter("ExpirationTime",
					updateWorkQueueParameters.getExpirationTime());
		}

		if (updateWorkQueueParameters.getScheduledTime() == null) {
			query.setParameter("ScheduledTime", null, StandardBasicTypes.DATE);
		} else {
			query.setParameter("ScheduledTime",
					updateWorkQueueParameters.getScheduledTime());
		}

		if (updateWorkQueueParameters.getFailureDescription() == null) {
			query.setParameter("FailureDescription", null,
					StandardBasicTypes.STRING);
		} else {
			query.setParameter("FailureDescription",
					updateWorkQueueParameters.getFailureDescription());
		}

		if (updateWorkQueueParameters.getQueueStudyStateEnum() == null) {
			query.setParameter("QueueStudyStateEnum", null,
					StandardBasicTypes.SHORT);
		} else {
			query.setString("QueueStudyStateEnum",
					updateWorkQueueParameters.getQueueStudyStateEnum());
		}
		query.executeUpdate();
	}

	@Override
	public void postponeWorkQueue(PostponeWorkQueueParameters parameters) {
		Query query = getCurrentSession()
				.createSQLQuery(
						"{call PostponeWorkQueue(:WorkQueueGUID,:ScheduledTime,:ExpirationTime,:Reason,:UpdateWorkQueue )}");

		query.setParameter("WorkQueueGUID", parameters.getWorkQueueGUID());

		query.setParameter("ScheduledTime", parameters.getScheduledTime());

		query.setParameter("ExpirationTime", parameters.getExpirationTime());

		query.setParameter("Reason", parameters.getReason());

		if (parameters.getUpdateWorkQueue() == null) {
			query.setParameter("UpdateWorkQueue", null,
					StandardBasicTypes.BOOLEAN);
		} else {
			query.setParameter("UpdateWorkQueue",
					parameters.getUpdateWorkQueue());
		}

		query.executeUpdate();
	}
}
