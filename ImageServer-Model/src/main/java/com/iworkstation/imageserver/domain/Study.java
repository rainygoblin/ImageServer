package com.iworkstation.imageserver.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Study")
@SqlResultSetMapping(name = "LockStudyScalar", columns = {
		@ColumnResult(name = "Successful"),
		@ColumnResult(name = "FailureReason") })
@NamedNativeQueries(value = { @NamedNativeQuery(name = "LockStudy", query = "{call LockStudy(:StudyStorageGUID,:ReadLock,:WriteLock,:QueueStudyStateEnum,:Successful,:FailureReason)}", resultSetMapping = "LockStudyScalar") })
public class Study {
	private String guid;
	private String serverPartitionGUID;
	private StudyStorage studyStorage;
	private Patient patient;
	private String specificCharacterSet;
	private String studyInstanceUid;
	private String patientsName;
	private String patientId;
	private String issuerOfPatientId;
	private String patientsBirthDate;
	private String patientsAge;
	private String patientsSex;
	private String studyDate;
	private String studyTime;
	private String accessionNumber;
	private String studyId;
	private String studyDescription;
	private String referringPhysiciansName;
	private int numberOfStudyRelatedSeries;
	private int numberOfStudyRelatedInstances;
	private BigDecimal studySizeInKB;
	private Set<Series> series = new HashSet<Series>();

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(columnDefinition = "uniqueidentifier")
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name = "ServerPartitionGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getServerPartitionGUID() {
		return serverPartitionGUID;
	}

	public void setServerPartitionGUID(String serverPartitionGUID) {
		this.serverPartitionGUID = serverPartitionGUID;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "StudyStorageGUID")
	@ForeignKey(name = "FK_Study_StudyStorage")
	public StudyStorage getStudyStorage() {
		return studyStorage;
	}

	public void setStudyStorage(StudyStorage studyStorage) {
		this.studyStorage = studyStorage;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PatientGUID")
	@ForeignKey(name = "FK_Study_Patient")
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Column(name = "SpecificCharacterSet", columnDefinition = "varchar(128)", nullable = true)
	public String getSpecificCharacterSet() {
		return specificCharacterSet;
	}

	public void setSpecificCharacterSet(String specificCharacterSet) {
		this.specificCharacterSet = specificCharacterSet;
	}

	@Column(name = "StudyInstanceUid", columnDefinition = "varchar(64)", nullable = false)
	public String getStudyInstanceUid() {
		return studyInstanceUid;
	}

	public void setStudyInstanceUid(String studyInstanceUid) {
		this.studyInstanceUid = studyInstanceUid;
	}

	@Column(name = "PatientsName", columnDefinition = "nvarchar(64)", nullable = false)
	public String getPatientsName() {
		return patientsName;
	}

	public void setPatientsName(String patientsName) {
		this.patientsName = patientsName;
	}

	@Column(name = "PatientId", columnDefinition = "nvarchar(64)", nullable = true)
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	@Column(name = "IssuerOfPatientId", columnDefinition = "nvarchar(64)", nullable = true)
	public String getIssuerOfPatientId() {
		return issuerOfPatientId;
	}

	public void setIssuerOfPatientId(String issuerOfPatientId) {
		this.issuerOfPatientId = issuerOfPatientId;
	}

	@Column(name = "PatientsBirthDate", columnDefinition = "varchar(8)", nullable = true)
	public String getPatientsBirthDate() {
		return patientsBirthDate;
	}

	public void setPatientsBirthDate(String patientsBirthDate) {
		this.patientsBirthDate = patientsBirthDate;
	}

	@Column(name = "PatientsAge", columnDefinition = "varchar(4)", nullable = true)
	public String getPatientsAge() {
		return patientsAge;
	}

	public void setPatientsAge(String patientsAge) {
		this.patientsAge = patientsAge;
	}

	@Column(name = "PatientsSex", columnDefinition = "varchar(2)", nullable = true)
	public String getPatientsSex() {
		return patientsSex;
	}

	public void setPatientsSex(String patientsSex) {
		this.patientsSex = patientsSex;
	}

	@Column(name = "StudyDate", columnDefinition = "varchar(8)", nullable = true)
	public String getStudyDate() {
		return studyDate;
	}

	public void setStudyDate(String studyDate) {
		this.studyDate = studyDate;
	}

	@Column(name = "StudyTime", columnDefinition = "varchar(16)", nullable = true)
	public String getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(String studyTime) {
		this.studyTime = studyTime;
	}

	@Column(name = "AccessionNumber", columnDefinition = "nvarchar(16)", nullable = true)
	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	@Column(name = "StudyId", columnDefinition = "nvarchar(16)", nullable = true)
	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	@Column(name = "StudyDescription", columnDefinition = "nvarchar(64)", nullable = true)
	public String getStudyDescription() {
		return studyDescription;
	}

	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}

	@Column(name = "ReferringPhysiciansName", columnDefinition = "nvarchar(64)", nullable = true)
	public String getReferringPhysiciansName() {
		return referringPhysiciansName;
	}

	public void setReferringPhysiciansName(String referringPhysiciansName) {
		this.referringPhysiciansName = referringPhysiciansName;
	}

	@Column(name = "NumberOfStudyRelatedSeries", columnDefinition = "int", nullable = false)
	public int getNumberOfStudyRelatedSeries() {
		return numberOfStudyRelatedSeries;
	}

	public void setNumberOfStudyRelatedSeries(int numberOfStudyRelatedSeries) {
		this.numberOfStudyRelatedSeries = numberOfStudyRelatedSeries;
	}

	@Column(name = "NumberOfStudyRelatedInstances", columnDefinition = "int", nullable = false)
	public int getNumberOfStudyRelatedInstances() {
		return numberOfStudyRelatedInstances;
	}

	public void setNumberOfStudyRelatedInstances(
			int numberOfStudyRelatedInstances) {
		this.numberOfStudyRelatedInstances = numberOfStudyRelatedInstances;
	}

	@Column(name = "StudySizeInKB", columnDefinition = "decimal(18, 0)", nullable = true)
	public BigDecimal getStudySizeInKB() {
		return studySizeInKB;
	}

	public void setStudySizeInKB(BigDecimal studySizeInKB) {
		this.studySizeInKB = studySizeInKB;
	}

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "study")
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Set<Series> getSeries() {
		return series;
	}

	public void setSeries(Set<Series> series) {
		this.series = series;
	}
}
