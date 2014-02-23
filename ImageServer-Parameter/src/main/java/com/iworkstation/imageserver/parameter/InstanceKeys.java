package com.iworkstation.imageserver.parameter;

public class InstanceKeys {
	private String serverPartitionGUID;
	private String studyStorageGUID;
	private String patientGUID;
	private String studyGUID;
	private String seriesGUID;
	private boolean insertPatient;
	private boolean insertStudy;
	private boolean insertSeries;

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

	public String getPatientGUID() {
		return patientGUID;
	}

	public void setPatientGUID(String patientGUID) {
		this.patientGUID = patientGUID;
	}

	public String getStudyGUID() {
		return studyGUID;
	}

	public void setStudyGUID(String studyGUID) {
		this.studyGUID = studyGUID;
	}

	public String getSeriesGUID() {
		return seriesGUID;
	}

	public void setSeriesGUID(String seriesGUID) {
		this.seriesGUID = seriesGUID;
	}

	public boolean isInsertPatient() {
		return insertPatient;
	}

	public void setInsertPatient(boolean insertPatient) {
		this.insertPatient = insertPatient;
	}

	public boolean isInsertStudy() {
		return insertStudy;
	}

	public void setInsertStudy(boolean insertStudy) {
		this.insertStudy = insertStudy;
	}

	public boolean isInsertSeries() {
		return insertSeries;
	}

	public void setInsertSeries(boolean insertSeries) {
		this.insertSeries = insertSeries;
	}
}
