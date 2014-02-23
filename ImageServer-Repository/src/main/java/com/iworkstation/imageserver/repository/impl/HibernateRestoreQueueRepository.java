package com.iworkstation.imageserver.repository.impl;

import org.hibernate.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.iworkstation.imageserver.domain.RestoreQueue;
import com.iworkstation.imageserver.parameter.InsertRestoreQueueParameters;
import com.iworkstation.imageserver.repository.IRestoreQueueRepository;

@Repository
public class HibernateRestoreQueueRepository extends
		GenericRepositoryImpl<String, RestoreQueue> implements
		IRestoreQueueRepository {

	public HibernateRestoreQueueRepository(){
		super.setEntityClass(RestoreQueue.class);
	}
	
	@Override
	public RestoreQueue insertRestoreQueue(
			InsertRestoreQueueParameters insertRestoreQueueParameters) {
		Query query = getCurrentSession().getNamedQuery("InsertRestoreQueue");
		query.setParameter("StudyStorageGUID",
				insertRestoreQueueParameters.getStudyStorageGUID());
		if (insertRestoreQueueParameters.getArchiveStudyStorageGUID() == null) {
			query.setParameter("ArchiveStudyStorageGUID", null,
					StandardBasicTypes.STRING);
		} else {
			query.setParameter("ArchiveStudyStorageGUID",
					insertRestoreQueueParameters.getArchiveStudyStorageGUID());
		}

		return (RestoreQueue) query.uniqueResult();
	}
}
