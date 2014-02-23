package com.iworkstation.imageserver.parameter;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;

public class InsertInstanceParameters {
	private String serverPartitionGUID;
	private String studyStorageGUID;
	private String patientId;
	private String patientsName;
	private String issuerOfPatientId;
	private String studyInstanceUid;
	private String patientsBirthDate;
	private String patientsSex;
	private String patientsAge;
	private String studyDate;
	private String studyTime;
	private String accessionNumber;
	private String studyId;
	private String studyDescription;
	private String referringPhysiciansName;
	private String seriesInstanceUid;
	private String modality;
	private String seriesNumber;
	private String seriesDescription;
	private String performedProcedureStepStartDate;
	private String performedProcedureStepStartTime;
	private String sourceApplicationEntityTitle;
	private String specificCharacterSet;

	public String getServerPartitionGUID() {
		return serverPartitionGUID;
	}

	public void setServerPartitionGUID(String serverPartitionGUID) {
		this.serverPartitionGUID = serverPartitionGUID;
	}

	public String getStudyStorageGUID() {
		return studyStorageGUID;
	}

	public void setStudyStorageGUID(String studyStorageGUID) {
		this.studyStorageGUID = studyStorageGUID;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientsName() {
		return patientsName;
	}

	public void setPatientsName(String patientsName) {
		this.patientsName = patientsName;
	}

	public String getIssuerOfPatientId() {
		return issuerOfPatientId;
	}

	public void setIssuerOfPatientId(String issuerOfPatientId) {
		this.issuerOfPatientId = issuerOfPatientId;
	}

	public String getStudyInstanceUid() {
		return studyInstanceUid;
	}

	public void setStudyInstanceUid(String studyInstanceUid) {
		this.studyInstanceUid = studyInstanceUid;
	}

	public String getPatientsBirthDate() {
		return patientsBirthDate;
	}

	public void setPatientsBirthDate(String patientsBirthDate) {
		this.patientsBirthDate = patientsBirthDate;
	}

	public String getPatientsSex() {
		return patientsSex;
	}

	public void setPatientsSex(String patientsSex) {
		this.patientsSex = patientsSex;
	}

	public String getPatientsAge() {
		return patientsAge;
	}

	public void setPatientsAge(String patientsAge) {
		this.patientsAge = patientsAge;
	}

	public String getStudyDate() {
		return studyDate;
	}

	public void setStudyDate(String studyDate) {
		this.studyDate = studyDate;
	}

	public String getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(String studyTime) {
		this.studyTime = studyTime;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getStudyDescription() {
		return studyDescription;
	}

	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}

	public String getReferringPhysiciansName() {
		return referringPhysiciansName;
	}

	public void setReferringPhysiciansName(String referringPhysiciansName) {
		this.referringPhysiciansName = referringPhysiciansName;
	}

	public String getSeriesInstanceUid() {
		return seriesInstanceUid;
	}

	public void setSeriesInstanceUid(String seriesInstanceUid) {
		this.seriesInstanceUid = seriesInstanceUid;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public String getSeriesNumber() {
		return seriesNumber;
	}

	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}

	public String getSeriesDescription() {
		return seriesDescription;
	}

	public void setSeriesDescription(String seriesDescription) {
		this.seriesDescription = seriesDescription;
	}

	public String getPerformedProcedureStepStartDate() {
		return performedProcedureStepStartDate;
	}

	public void setPerformedProcedureStepStartDate(
			String performedProcedureStepStartDate) {
		this.performedProcedureStepStartDate = performedProcedureStepStartDate;
	}

	public String getPerformedProcedureStepStartTime() {
		return performedProcedureStepStartTime;
	}

	public void setPerformedProcedureStepStartTime(
			String performedProcedureStepStartTime) {
		this.performedProcedureStepStartTime = performedProcedureStepStartTime;
	}

	public String getSourceApplicationEntityTitle() {
		return sourceApplicationEntityTitle;
	}

	public void setSourceApplicationEntityTitle(
			String sourceApplicationEntityTitle) {
		this.sourceApplicationEntityTitle = sourceApplicationEntityTitle;
	}

	public String getSpecificCharacterSet() {
		return specificCharacterSet;
	}

	public void setSpecificCharacterSet(String specificCharacterSet) {
		this.specificCharacterSet = specificCharacterSet;
	}

	public void initial(DicomObject dcmObj) {
		patientId = dcmObj.getString(Tag.PatientID);
		patientsName = dcmObj.getString(Tag.PatientName);
		issuerOfPatientId = dcmObj.getString(Tag.IssuerOfPatientID);
		studyInstanceUid = dcmObj.getString(Tag.StudyInstanceUID);
		patientsBirthDate = dcmObj.getString(Tag.PatientBirthDate);
		patientsSex = dcmObj.getString(Tag.PatientSex);
		patientsAge = dcmObj.getString(Tag.PatientAge);
		studyDate = dcmObj.getString(Tag.StudyDate);
		studyTime = dcmObj.getString(Tag.StudyTime);
		accessionNumber = dcmObj.getString(Tag.AccessionNumber);
		studyId = dcmObj.getString(Tag.StudyID);
		studyDescription = dcmObj.getString(Tag.StudyDescription);
		referringPhysiciansName = dcmObj.getString(Tag.ReferringPhysicianName);
		seriesInstanceUid = dcmObj.getString(Tag.SeriesInstanceUID);
		modality = dcmObj.getString(Tag.Modality);
		seriesNumber = dcmObj.getString(Tag.SeriesNumber);
		seriesDescription = dcmObj.getString(Tag.SeriesDescription);
		performedProcedureStepStartDate = dcmObj
				.getString(Tag.PerformedProcedureStepStartDate);
		performedProcedureStepStartTime = dcmObj
				.getString(Tag.PerformedProcedureStepStartTime);
		sourceApplicationEntityTitle = dcmObj
				.getString(Tag.SourceApplicationEntityTitle);
		specificCharacterSet = dcmObj.getString(Tag.SpecificCharacterSet);
	}
}
