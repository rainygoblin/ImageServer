package com.iworkstation.imageserver.dcm.findscp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.net.DimseRSP;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.iworkstation.imageserver.domain.RequestAttributes;
import com.iworkstation.imageserver.domain.Series;
import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.domain.StudyStorage;
import com.iworkstation.imageserver.service.ISeriesManager;
import com.iworkstation.imageserver.service.IStudyManager;
import com.iworkstation.imageserver.service.IStudyStorageManager;

final class SeriesLeveQuery extends AbstractQuery {

	private final ServerPartition partition;
	private final IStudyManager studyManager;
	private final ISeriesManager seriesManager;
	private final IStudyStorageManager studyStorageManager;

	private List<Integer> tagList = new ArrayList<Integer>();

	public SeriesLeveQuery(ServerPartition partition,
			IStudyStorageManager studyStorageManager,
			IStudyManager studyManager, ISeriesManager seriesManager) {
		this.partition = partition;
		this.studyStorageManager = studyStorageManager;
		this.studyManager = studyManager;
		this.seriesManager = seriesManager;
	}

	@Override
	public DimseRSP query(DicomObject data, DicomObject rsp) {
		List<DicomObject> dicomObjectResults = new ArrayList<DicomObject>();

		List<Series> results = findSeries(data);
		for (Series series : results) {
			dicomObjectResults.add(populateSeries(series));
		}

		return new MultiFindRSP(rsp, dicomObjectResults);
	}

	private DicomObject populateSeries(Series series) {
		DicomObject dicomObject = new BasicDicomObject();
		dicomObject.putString(Tag.RetrieveAETitle, VR.AE,
				partition.getAeTitle());
		dicomObject.putString(Tag.QueryRetrieveLevel, VR.CS, "SERIES");
		dicomObject.putString(Tag.SpecificCharacterSet, VR.CS, series
				.getStudy().getSpecificCharacterSet());

		Study study = studyManager.loadByID(series.getStudy().getGuid());
		if (study != null) {
			StudyStorage studyStorage = studyStorageManager.loadByID(study
					.getStudyStorage().getGuid());
			if (studyStorage != null) {
				if (studyStorage.getStudyStatusEnum() != null) {
					dicomObject.putString(Tag.InstanceAvailability, VR.CS,
							studyStorage.getStudyStatusEnum().getLookup());
				}
			}

		}
		for (int tag : tagList) {
			switch (tag) {
			case Tag.PatientID:
				dicomObject.putString(Tag.PatientID, VR.LO, series.getStudy()
						.getPatientId());
				break;
			case Tag.StudyInstanceUID:
				dicomObject.putString(Tag.StudyInstanceUID, VR.UI, series
						.getStudy().getStudyInstanceUid());
				break;
			case Tag.SeriesInstanceUID:
				dicomObject.putString(Tag.SeriesInstanceUID, VR.UI,
						series.getSeriesInstanceUid());
				break;
			case Tag.Modality:
				dicomObject
						.putString(Tag.Modality, VR.CS, series.getModality());
				break;
			case Tag.SeriesNumber:
				dicomObject.putString(Tag.SeriesNumber, VR.IS,
						series.getSeriesNumber());
				break;
			case Tag.SeriesDescription:
				dicomObject.putString(Tag.SeriesDescription, VR.LO,
						series.getSeriesDescription());
				break;
			case Tag.PerformedProcedureStepStartDate:
				dicomObject.putString(Tag.PerformedProcedureStepStartDate,
						VR.DA, series.getPerformedProcedureStepStartDate());
				break;
			case Tag.PerformedProcedureStepStartTime:
				dicomObject.putString(Tag.PerformedProcedureStepStartTime,
						VR.TM, series.getPerformedProcedureStepStartTime());
				break;
			case Tag.NumberOfSeriesRelatedInstances:
				dicomObject.putInt(Tag.NumberOfSeriesRelatedInstances, VR.IS,
						series.getNumberOfSeriesRelatedInstances());
				break;
			case Tag.RequestAttributesSequence:
				for (RequestAttributes request : series.getRequestAttributes()) {
					DicomObject suDicomObject = new BasicDicomObject();
					suDicomObject.putString(Tag.ScheduledProcedureStepID,
							VR.SH, request.getScheduledProcedureStepId());
					suDicomObject.putString(Tag.RequestedProcedureID, VR.SH,
							request.getRequestedProcedureId());
					dicomObject.get(Tag.RequestAttributesSequence)
							.addDicomObject(suDicomObject);
				}
				break;

			}
		}
		return dicomObject;
	}

	private List<Series> findSeries(DicomObject data) {
		List<Criterion> seriesCriterions = new ArrayList<Criterion>();
		Map<String, List<Criterion>> seriesAssocianCriterions = new HashMap<String, List<Criterion>>();

		List<Criterion> serverPartitionCriterions = new ArrayList<Criterion>();
		serverPartitionCriterions.add(createStringCondition("guid",
				partition.getGuid()));
		seriesAssocianCriterions.put("serverPartition",
				serverPartitionCriterions);

		Iterator<DicomElement> dicomElementIterator = data.iterator();
		while (dicomElementIterator.hasNext()) {
			DicomElement dicomElement = dicomElementIterator.next();
			tagList.add(dicomElement.tag());
			if (dicomElement != null) {
				switch (dicomElement.tag()) {
				case Tag.StudyInstanceUID:
					if (data.getString(Tag.StudyInstanceUID) != null) {
						List<Criterion> studyCriterions = new ArrayList<Criterion>();
						studyCriterions.add(Restrictions.eq("studyInstanceUid",
								data.getString(Tag.StudyInstanceUID)));
						seriesAssocianCriterions.put("study", studyCriterions);
					}
					break;
				case Tag.SeriesInstanceUID:
					if (data.getString(Tag.SeriesInstanceUID) != null) {
						seriesCriterions.add(createStringCondition(
								"seriesInstanceUid",
								data.getString(Tag.SeriesInstanceUID)));
					}
					break;
				case Tag.Modality:
					if (data.getString(Tag.Modality) != null) {
						seriesCriterions.add(createStringCondition("modality",
								data.getString(Tag.Modality)));
					}
					break;
				case Tag.SeriesNumber:
					if (data.getString(Tag.SeriesNumber) != null) {
						seriesCriterions.add(createStringCondition(
								"seriesNumber",
								data.getString(Tag.SeriesNumber)));
					}
					break;
				case Tag.SeriesDescription:
					if (data.getString(Tag.SeriesDescription) != null) {
						seriesCriterions.add(createStringCondition(
								"seriesDescription",
								data.getString(Tag.SeriesDescription)));
					}
					break;
				case Tag.PerformedProcedureStepStartDate:
					if (data.getString(Tag.PerformedProcedureStepStartDate) != null) {
						seriesCriterions
								.add(createRangeCondition(
										"performedProcedureStepStartDate",
										data.getString(Tag.PerformedProcedureStepStartDate)));
					}
					break;
				case Tag.PerformedProcedureStepStartTime:
					if (data.getString(Tag.PerformedProcedureStepStartTime) != null) {
						seriesCriterions
								.add(createRangeCondition(
										"performedProcedureStepStartTime",
										data.getString(Tag.PerformedProcedureStepStartTime)));
					}
					break;
				}
			}
		}
		return seriesManager.findSeriesByCriterions(seriesCriterions,
				seriesAssocianCriterions);
	}
}
