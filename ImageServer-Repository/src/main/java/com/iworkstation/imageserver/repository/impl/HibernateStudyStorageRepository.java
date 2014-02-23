package com.iworkstation.imageserver.repository.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.iworkstation.imageserver.domain.StudyStorage;
import com.iworkstation.imageserver.domain.StudyStorageLocation;
import com.iworkstation.imageserver.parameter.InsertStudyStorageParameters;
import com.iworkstation.imageserver.parameter.LockStudyParameters;
import com.iworkstation.imageserver.parameter.StudyStorageLocationQueryParameters;
import com.iworkstation.imageserver.repository.IStudyStorageRepository;

@Repository
public class HibernateStudyStorageRepository extends
		GenericRepositoryImpl<String, StudyStorage> implements
		IStudyStorageRepository {

	public HibernateStudyStorageRepository(){
		super.setEntityClass(StudyStorage.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyStorageLocation> queryStudyStorageLocation(
			StudyStorageLocationQueryParameters studyStorageLocationQueryParameters) {
		Query query = getCurrentSession().getNamedQuery(
				"QueryStudyStorageLocation");

		if (studyStorageLocationQueryParameters.getStudyStorageGUID() == null) {
			query.setParameter("StudyStorageGUID", null,
					StandardBasicTypes.STRING);
		} else {
			query.setParameter("StudyStorageGUID",
					studyStorageLocationQueryParameters.getStudyStorageGUID());
		}

		if (studyStorageLocationQueryParameters.getServerPartitionGUID() == null) {
			query.setParameter("ServerPartitionGUID", null,
					StandardBasicTypes.STRING);
		} else {
			query.setParameter("ServerPartitionGUID",
					studyStorageLocationQueryParameters
							.getServerPartitionGUID());
		}

		if (studyStorageLocationQueryParameters.getStudyInstanceUid() == null) {
			query.setParameter("StudyInstanceUid", null,
					StandardBasicTypes.STRING);
		} else {
			query.setParameter("StudyInstanceUid",
					studyStorageLocationQueryParameters.getStudyInstanceUid());
		}

		query.setResultTransformer(Transformers
				.aliasToBean(StudyStorageLocation.class));
		return query.list();
	}

	@Override
	public StudyStorageLocation insertStudyStorage(
			InsertStudyStorageParameters insertStudyStorageParameters) {
		// check the input parameters
		Query query = getCurrentSession().getNamedQuery("InsertStudyStorage");

		query.setParameter("ServerPartitionGUID",
				insertStudyStorageParameters.getServerPartitionGUID());
		query.setParameter("StudyInstanceUid",
				insertStudyStorageParameters.getStudyInstanceUid());
		query.setParameter("Folder", insertStudyStorageParameters.getFolder());
		query.setParameter("FilesystemGUID",
				insertStudyStorageParameters.getFilesystemGUID());
		query.setParameter("TransferSyntaxUid",
				insertStudyStorageParameters.getTransferSyntaxUid());
		query.setParameter("StudyStatusEnum",
				insertStudyStorageParameters.getStudyStatusEnum());
		query.setParameter("QueueStudyStateEnum",
				insertStudyStorageParameters.getQueueStudyStateEnum());

		query.setResultTransformer(Transformers
				.aliasToBean(StudyStorageLocation.class));

		StudyStorageLocation location = null;
		List<?> results = query.list();
		for (Object obj : results) {
			location = (StudyStorageLocation) obj;
			break;
		}
		return location;

	}

	@Override
	public void lockStudy(final LockStudyParameters lockStudyParameters) {
		Work work = new Work() {
			public void execute(Connection connection) throws SQLException {
				CallableStatement stmt = connection
						.prepareCall("{call LockStudy(?,?,?,?,?,?)}");
				stmt.setString(1, lockStudyParameters.getStudyStorageGUID());
				if (lockStudyParameters.getReadLock() == null) {
					stmt.setNull(2, java.sql.Types.BOOLEAN);
				} else {
					stmt.setBoolean(2, lockStudyParameters.getReadLock());
				}
				if (lockStudyParameters.getWriteLock() == null) {
					stmt.setNull(3, java.sql.Types.BOOLEAN);
				} else {
					stmt.setBoolean(3, lockStudyParameters.getWriteLock());
				}
				if (lockStudyParameters.getQueueStudyStateEnum() == null) {
					stmt.setNull(4, java.sql.Types.NVARCHAR);
				} else {
					stmt.setString(2,
							lockStudyParameters.getQueueStudyStateEnum());
				}
				stmt.registerOutParameter(5, java.sql.Types.BOOLEAN);
				stmt.registerOutParameter(6, java.sql.Types.NVARCHAR);
				stmt.execute();
				lockStudyParameters.setSuccessful(stmt.getBoolean(5));
				lockStudyParameters.setFailureReason(stmt.getString(6));
			}
		};

		getCurrentSession().doWork(work);
	}

}
