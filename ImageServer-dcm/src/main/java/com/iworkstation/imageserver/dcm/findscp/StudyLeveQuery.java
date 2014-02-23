package com.iworkstation.imageserver.dcm.findscp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.net.DimseRSP;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.iworkstation.imageserver.domain.Series;
import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.enumeration.QueueStudyStateEnum;
import com.iworkstation.imageserver.enumeration.StudyStatusEnum;
import com.iworkstation.imageserver.service.ISeriesManager;
import com.iworkstation.imageserver.service.IStudyManager;

final class StudyLeveQuery extends AbstractQuery {
	private static final Log LOG = LogFactory.getLog(StudyLeveQuery.class);

	private final ServerPartition partition;
	private final IStudyManager studyManager;
	private final ISeriesManager seriesManager;
	private List<Integer> tagList = new ArrayList<Integer>();

	public StudyLeveQuery(ServerPartition partition,
			IStudyManager studyManager, ISeriesManager seriesManager) {
		this.partition = partition;
		this.studyManager = studyManager;
		this.seriesManager = seriesManager;
	}

	@Override
	public DimseRSP query(DicomObject data, DicomObject rsp) {
		List<DicomObject> dicomObjectResults = new ArrayList<DicomObject>();

		List<Study> results = findStudys(data, "ONLINE");
		for (Study study : results) {
			dicomObjectResults.add(populateStudy(study, "ONLINE"));
		}
		results = findStudys(data, "NEARLINE");
		for (Study study : results) {
			dicomObjectResults.add(populateStudy(study, "NEARLINE"));
		}
		return new MultiFindRSP(rsp, dicomObjectResults);
	}

	private DicomObject populateStudy(Study study, String availability) {
		DicomObject dicomObject = new BasicDicomObject();
		dicomObject.putString(Tag.RetrieveAETitle, VR.AE,
				partition.getAeTitle());
		dicomObject.putString(Tag.QueryRetrieveLevel, VR.CS, "STUDY");
		dicomObject.putString(Tag.SpecificCharacterSet, VR.CS,
				study.getSpecificCharacterSet());
		dicomObject.putString(Tag.InstanceAvailability, VR.CS, availability);

		for (int tag : tagList) {
			switch (tag) {
			case Tag.StudyInstanceUID:
				dicomObject.putString(Tag.StudyInstanceUID, VR.UI,
						study.getStudyInstanceUid());
				break;
			case Tag.PatientName:
				dicomObject.putString(Tag.PatientName, VR.PN,
						study.getPatientsName());
				break;
			case Tag.PatientID:
				dicomObject.putString(Tag.PatientID, VR.LO,
						study.getPatientId());
				break;
			case Tag.PatientBirthDate:
				dicomObject.putString(Tag.PatientBirthDate, VR.DA,
						study.getPatientsBirthDate());
				break;
			case Tag.PatientAge:
				dicomObject.putString(Tag.PatientAge, VR.AS,
						study.getPatientsAge());
				break;
			case Tag.PatientSex:
				dicomObject.putString(Tag.PatientSex, VR.CS,
						study.getPatientsSex());
				break;
			case Tag.StudyDate:
				dicomObject.putString(Tag.StudyDate, VR.DA,
						study.getStudyDate());
				break;
			case Tag.StudyTime:
				dicomObject.putString(Tag.StudyTime, VR.TM,
						study.getStudyTime());
				break;
			case Tag.AccessionNumber:
				dicomObject.putString(Tag.AccessionNumber, VR.SH,
						study.getAccessionNumber());
				break;
			case Tag.StudyID:
				dicomObject.putString(Tag.StudyID, VR.SH, study.getStudyId());
				break;
			case Tag.StudyDescription:
				dicomObject.putString(Tag.StudyDescription, VR.LO,
						study.getStudyDescription());
				break;
			case Tag.ReferringPhysicianName:
				dicomObject.putString(Tag.ReferringPhysicianName, VR.PN,
						study.getReferringPhysiciansName());
				break;
			case Tag.NumberOfStudyRelatedSeries:
				dicomObject.putInt(Tag.NumberOfStudyRelatedSeries, VR.IS,
						study.getNumberOfStudyRelatedSeries());
				break;
			case Tag.NumberOfStudyRelatedInstances:
				dicomObject.putInt(Tag.NumberOfStudyRelatedInstances, VR.IS,
						study.getNumberOfStudyRelatedInstances());
				break;
			case Tag.ModalitiesInStudy:
				String value = "";
				Map<String, List<Criterion>> studyAssocianCriterions = new HashMap<String, List<Criterion>>();
				List<Criterion> serisCriterions = new ArrayList<Criterion>();
				serisCriterions.add(createStringCondition("guid",
						study.getGuid()));
				studyAssocianCriterions.put("study", serisCriterions);
				List<Series> seriesList = seriesManager.findSeriesByCriterions(
						null, studyAssocianCriterions);
				for (Series series : seriesList) {
					value = value.length() == 0 ? series.getModality() : String
							.format("{%s}\\{%s}", value, series.getModality());
				}
				dicomObject.putString(Tag.ModalitiesInStudy, VR.CS, value);
				break;

			}
		}
		return dicomObject;
	}

	private List<Study> findStudys(DicomObject data, String availability) {
		LOG.info(String.format("To find %s study list", availability));
		List<Criterion> studyCriterions = new ArrayList<Criterion>();
		Map<String, List<Criterion>> studyAssocianCriterions = new HashMap<String, List<Criterion>>();

		studyCriterions.add(createStringCondition("serverPartitionGUID",
				partition.getGuid()));

		Iterator<DicomElement> dicomElementIterator = data.iterator();
		while (dicomElementIterator.hasNext()) {
			DicomElement dicomElement = dicomElementIterator.next();
			tagList.add(dicomElement.tag());
			if (dicomElement != null) {
				switch (dicomElement.tag()) {
				case Tag.StudyInstanceUID:
					if (data.getString(Tag.StudyInstanceUID) != null) {
						studyCriterions.add(createStringCondition(
								"studyInstanceUid",
								data.getString(Tag.StudyInstanceUID)));
					}
					break;
				case Tag.PatientName:
					if (data.getString(Tag.PatientName) != null) {
						studyCriterions
								.add(createStringCondition("patientsName",
										data.getString(Tag.PatientName)));
					}
					break;
				case Tag.PatientID:
					if (data.getString(Tag.PatientID) != null) {
						studyCriterions.add(createStringCondition("patientId",
								data.getString(Tag.PatientID)));
					}
					break;
				case Tag.PatientBirthDate:
					if (data.getString(Tag.PatientBirthDate) != null) {
						studyCriterions.add(createRangeCondition(
								"patientsBirthDate",
								data.getString(Tag.PatientBirthDate)));
					}
					break;
				case Tag.PatientSex:
					if (data.getString(Tag.PatientSex) != null) {
						studyCriterions.add(createStringCondition(
								"patientsSex", data.getString(Tag.PatientSex)));
					}
					break;
				case Tag.StudyDate:
					if (data.getString(Tag.StudyDate) != null) {
						studyCriterions.add(createRangeCondition("studyDate",
								data.getString(Tag.StudyDate)));
					}
					break;
				case Tag.StudyTime:
					if (data.getString(Tag.StudyTime) != null) {
						studyCriterions.add(createRangeCondition("studyTime",
								data.getString(Tag.StudyTime)));
					}
					break;
				case Tag.AccessionNumber:
					if (data.getString(Tag.StudyTime) != null) {
						studyCriterions.add(createStringCondition(
								"accessionNumber",
								data.getString(Tag.AccessionNumber)));
					}
					break;
				case Tag.StudyID:
					if (data.getString(Tag.StudyTime) != null) {
						studyCriterions.add(createStringCondition("studyId",
								data.getString(Tag.StudyID)));
					}
					break;
				case Tag.StudyDescription:
					if (data.getString(Tag.StudyTime) != null) {
						studyCriterions.add(createStringCondition(
								"studyDescription",
								data.getString(Tag.StudyDescription)));
					}
					break;
				case Tag.ReferringPhysicianName:
					if (data.getString(Tag.StudyTime) != null) {
						studyCriterions.add(createStringCondition(
								"referringPhysiciansName",
								data.getString(Tag.ReferringPhysicianName)));
					}
					break;
				case Tag.ModalitiesInStudy:
					if (data.getString(Tag.StudyTime) != null) {
						List<Criterion> seriesCriterions = new ArrayList<Criterion>();
						seriesCriterions.add(createStringCondition("modality",
								data.getString(Tag.ModalitiesInStudy)));
						studyAssocianCriterions.put("series", seriesCriterions);
					}
					break;
				}
			}
		}

		// set the studystorage association query
		List<Criterion> studyStorageCriterions = new ArrayList<Criterion>();
		if (StudyStatusEnum.Nearline.getLookup().equalsIgnoreCase(availability)) {
			studyStorageCriterions.add(Restrictions.eq("studyStatusEnum",
					StudyStatusEnum.Nearline));
		} else {
			studyStorageCriterions.add(Restrictions.ne("studyStatusEnum",
					StudyStatusEnum.Nearline));
		}
		QueueStudyStateEnum[] queueStudyStates = {
				QueueStudyStateEnum.DeleteScheduled,
				QueueStudyStateEnum.EditScheduled };
		studyStorageCriterions.add(Restrictions.not(Restrictions.in(
				"queueStudyStateEnum", queueStudyStates)));
		studyAssocianCriterions.put("studyStorage", studyStorageCriterions);
		return studyManager.findStudyByCriterions(studyCriterions,
				studyAssocianCriterions);
	}
}
