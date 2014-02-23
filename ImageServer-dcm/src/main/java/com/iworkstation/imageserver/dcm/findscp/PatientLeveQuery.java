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

import com.iworkstation.imageserver.domain.Patient;
import com.iworkstation.imageserver.domain.ServerPartition;
import com.iworkstation.imageserver.domain.Study;
import com.iworkstation.imageserver.service.IPatientManager;

final class PatientLeveQuery extends AbstractQuery {

	private final ServerPartition partition;
	private final IPatientManager patientManager;
	private List<Integer> tagList = new ArrayList<Integer>();

	public PatientLeveQuery(ServerPartition partition,
			IPatientManager patientManager) {
		this.partition = partition;
		this.patientManager = patientManager;
	}

	@Override
	public DimseRSP query(DicomObject data, DicomObject rsp) {
		List<DicomObject> dicomObjectResults = new ArrayList<DicomObject>();

		List<Patient> results = findPatients(data);
		for (Patient patient : results) {
			dicomObjectResults.add(populatePatient(patient));
		}

		return new MultiFindRSP(rsp, dicomObjectResults);
	}

	private DicomObject populatePatient(Patient patient) {
		DicomObject dicomObject = new BasicDicomObject();
		dicomObject.putString(Tag.RetrieveAETitle, VR.AE,
				partition.getAeTitle());
		dicomObject.putString(Tag.QueryRetrieveLevel, VR.CS, "PATIENT");
		dicomObject.putString(Tag.SpecificCharacterSet, VR.CS,
				patient.getSpecificCharacterSet());

		Study currentStudy = null;
		for (Study study : patient.getStudies()) {
			currentStudy = study;
			break;
		}
		for (int tag : tagList) {
			switch (tag) {
			case Tag.PatientName:
				dicomObject.putString(Tag.PatientName, VR.PN,
						patient.getPatientsName());
				break;
			case Tag.PatientID:
				dicomObject.putString(Tag.PatientID, VR.LO,
						patient.getPatientId());
				break;
			case Tag.IssuerOfPatientID:
				dicomObject.putString(Tag.IssuerOfPatientID, VR.LO,
						patient.getIssuerOfPatientId());
				break;
			case Tag.NumberOfPatientRelatedStudies:
				dicomObject.putInt(Tag.NumberOfPatientRelatedStudies, VR.IS,
						patient.getNumberOfPatientRelatedStudies());
				break;
			case Tag.NumberOfPatientRelatedSeries:
				dicomObject.putInt(Tag.NumberOfPatientRelatedSeries, VR.IS,
						patient.getNumberOfPatientRelatedSeries());
				break;
			case Tag.NumberOfPatientRelatedInstances:
				dicomObject.putInt(Tag.NumberOfPatientRelatedInstances, VR.IS,
						patient.getNumberOfPatientRelatedInstances());
				break;
			case Tag.PatientSex:
				if (currentStudy != null) {
					dicomObject.putString(Tag.PatientSex, VR.CS,
							currentStudy.getPatientsSex());
				}
				break;
			case Tag.PatientBirthDate:
				if (currentStudy != null) {
					dicomObject.putString(Tag.PatientBirthDate, VR.DA,
							currentStudy.getPatientsBirthDate());
				}
				break;

			}
		}
		return dicomObject;
	}

	private List<Patient> findPatients(DicomObject data) {
		List<Criterion> patientCriterions = new ArrayList<Criterion>();
		Map<String, List<Criterion>> studyAssocianCriterions = new HashMap<String, List<Criterion>>();
		List<Criterion> studyCriterions = new ArrayList<Criterion>();

		List<Criterion> serverPartitionCriterions = new ArrayList<Criterion>();
		serverPartitionCriterions.add(createStringCondition("guid",
				partition.getGuid()));
		studyAssocianCriterions.put("serverPartition",
				serverPartitionCriterions);

		Iterator<DicomElement> dicomElementIterator = data.iterator();
		while (dicomElementIterator.hasNext()) {
			DicomElement dicomElement = dicomElementIterator.next();
			tagList.add(dicomElement.tag());
			if (dicomElement != null) {
				switch (dicomElement.tag()) {
				case Tag.PatientName:
					if (data.getString(Tag.PatientName) != null) {
						patientCriterions
								.add(createStringCondition("patientsName",
										data.getString(Tag.PatientName)));
					}
					break;

				case Tag.PatientID:
					if (data.getString(Tag.PatientID) != null) {
						patientCriterions.add(createStringCondition(
								"patientId", data.getString(Tag.PatientID)));
					}
					break;
				case Tag.IssuerOfPatientID:
					if (data.getString(Tag.IssuerOfPatientID) != null) {
						patientCriterions.add(createRangeCondition(
								"issuerOfPatientId",
								data.getString(Tag.IssuerOfPatientID)));
					}
					break;
				case Tag.PatientSex:
					if (data.getString(Tag.PatientSex) != null) {
						studyCriterions.add(createStringCondition(
								"patientsSex", data.getString(Tag.PatientSex)));

					}
					break;
				case Tag.PatientBirthDate:
					if (data.getString(Tag.PatientBirthDate) != null) {
						studyCriterions.add(createRangeCondition(
								"patientsBirthDate",
								data.getString(Tag.PatientBirthDate)));
					}
					break;
				}
			}
		}

		studyAssocianCriterions.put("studies", studyCriterions);

		return patientManager.findPatientByCriterions(patientCriterions,
				studyAssocianCriterions);
	}
}
