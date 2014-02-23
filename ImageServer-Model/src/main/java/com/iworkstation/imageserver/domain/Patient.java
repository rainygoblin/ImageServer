package com.iworkstation.imageserver.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Patient")
public class Patient {
	private String guid;
	private String serverPartitionGUID;
	private String patientsName;
	private String patientId;
	private String issuerOfPatientId;
	private int numberOfPatientRelatedStudies;
	private int numberOfPatientRelatedSeries;
	private int numberOfPatientRelatedInstances;
	private String specificCharacterSet;
	private Set<Study> studies = new HashSet<Study>();

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

	@Column(name = "PatientsName", columnDefinition = "nvarchar(64)", nullable = true)
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

	@Column(name = "NumberOfPatientRelatedStudies", columnDefinition = "int", nullable = false)
	public int getNumberOfPatientRelatedStudies() {
		return numberOfPatientRelatedStudies;
	}

	public void setNumberOfPatientRelatedStudies(
			int numberOfPatientRelatedStudies) {
		this.numberOfPatientRelatedStudies = numberOfPatientRelatedStudies;
	}

	@Column(name = "NumberOfPatientRelatedSeries", columnDefinition = "int", nullable = false)
	public int getNumberOfPatientRelatedSeries() {
		return numberOfPatientRelatedSeries;
	}

	public void setNumberOfPatientRelatedSeries(int numberOfPatientRelatedSeries) {
		this.numberOfPatientRelatedSeries = numberOfPatientRelatedSeries;
	}

	@Column(name = "NumberOfPatientRelatedInstances", columnDefinition = "int", nullable = false)
	public int getNumberOfPatientRelatedInstances() {
		return numberOfPatientRelatedInstances;
	}

	public void setNumberOfPatientRelatedInstances(
			int numberOfPatientRelatedInstances) {
		this.numberOfPatientRelatedInstances = numberOfPatientRelatedInstances;
	}

	@Column(name = "SpecificCharacterSet", columnDefinition = "varchar(128)", nullable = true)
	public String getSpecificCharacterSet() {
		return specificCharacterSet;
	}

	public void setSpecificCharacterSet(String specificCharacterSet) {
		this.specificCharacterSet = specificCharacterSet;
	}

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "patient")
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Set<Study> getStudies() {
		return studies;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}
}
