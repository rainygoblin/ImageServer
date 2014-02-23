package com.iworkstation.imageserver.repository.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.parameter.InsertInstanceParameters;
import com.iworkstation.imageserver.parameter.InstanceKeys;
import com.iworkstation.imageserver.parameter.LockStudyParameters;
import com.iworkstation.imageserver.repository.IStudyRepository;

@Repository
public class HibernateStudyRepository extends
		GenericRepositoryImpl<String, Study> implements IStudyRepository {

	public HibernateStudyRepository(){
		super.setEntityClass(Study.class);
	}
	@Override
	public boolean lockStudy(final LockStudyParameters lockStudyParameters) {

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
		return lockStudyParameters.getSuccessful();
	}

	@Override
	public InstanceKeys insertInstance(
			InsertInstanceParameters insertInstanceParameters) {
		Query query = getCurrentSession()
				.createSQLQuery(
						"{call InsertInstance(:ServerPartitionGUID,:StudyStorageGUID,:PatientId,:PatientsName,:IssuerOfPatientId,"
								+ ":StudyInstanceUid,:PatientsBirthDate,:PatientsSex,:StudyDate,:StudyTime,:AccessionNumber,:StudyId,:StudyDescription,"
								+ ":ReferringPhysiciansName,:SeriesInstanceUid,:Modality,:SeriesNumber,:SeriesDescription,:PerformedProcedureStepStartDate,"
								+ ":PerformedProcedureStepStartTime,:SourceApplicationEntityTitle,:SpecificCharacterSet,:PatientsAge )}")
				.addScalar("ServerPartitionGUID", StandardBasicTypes.STRING)
				.addScalar("StudyStorageGUID", StandardBasicTypes.STRING)
				.addScalar("PatientGUID", StandardBasicTypes.STRING)
				.addScalar("StudyGUID", StandardBasicTypes.STRING)
				.addScalar("SeriesGUID", StandardBasicTypes.STRING)
				.addScalar("InsertPatient", StandardBasicTypes.BOOLEAN)
				.addScalar("InsertStudy", StandardBasicTypes.BOOLEAN)
				.addScalar("InsertSeries", StandardBasicTypes.BOOLEAN);
		query.setParameter("ServerPartitionGUID",
				insertInstanceParameters.getServerPartitionGUID());
		query.setParameter("StudyStorageGUID",
				insertInstanceParameters.getStudyStorageGUID());
		query.setParameter("PatientId", insertInstanceParameters.getPatientId());
		query.setParameter("PatientsName",
				insertInstanceParameters.getPatientsName());
		query.setParameter("IssuerOfPatientId",
				insertInstanceParameters.getIssuerOfPatientId());
		query.setParameter("StudyInstanceUid",
				insertInstanceParameters.getStudyInstanceUid());
		query.setParameter("PatientsBirthDate",
				insertInstanceParameters.getPatientsBirthDate());
		query.setParameter("PatientsSex",
				insertInstanceParameters.getPatientsSex());
		query.setParameter("StudyDate", insertInstanceParameters.getStudyDate());
		query.setParameter("StudyTime", insertInstanceParameters.getStudyTime());
		query.setParameter("AccessionNumber",
				insertInstanceParameters.getAccessionNumber());
		query.setParameter("StudyId", insertInstanceParameters.getStudyId());
		query.setParameter("StudyDescription",
				insertInstanceParameters.getStudyDescription());
		query.setParameter("ReferringPhysiciansName",
				insertInstanceParameters.getReferringPhysiciansName());
		query.setParameter("SeriesInstanceUid",
				insertInstanceParameters.getSeriesInstanceUid());
		query.setParameter("Modality", insertInstanceParameters.getModality());
		query.setParameter("SeriesNumber",
				insertInstanceParameters.getSeriesNumber());
		query.setParameter("SeriesDescription",
				insertInstanceParameters.getSeriesDescription());
		query.setParameter("PerformedProcedureStepStartDate",
				insertInstanceParameters.getPerformedProcedureStepStartDate());
		query.setParameter("PerformedProcedureStepStartTime",
				insertInstanceParameters.getPerformedProcedureStepStartTime());
		query.setParameter("SourceApplicationEntityTitle",
				insertInstanceParameters.getSourceApplicationEntityTitle());
		query.setParameter("SpecificCharacterSet",
				insertInstanceParameters.getSpecificCharacterSet());
		query.setParameter("PatientsAge",
				insertInstanceParameters.getPatientsAge());

		query.setResultTransformer(Transformers.aliasToBean(InstanceKeys.class));
		return (InstanceKeys) query.uniqueResult();
	}

}
