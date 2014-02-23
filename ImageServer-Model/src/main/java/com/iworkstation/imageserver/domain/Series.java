package com.iworkstation.imageserver.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Series")
public class Series {
	private String guid;
	private String serverPartitionGUID;
	private Study study;
	private String seriesInstanceUid;
	private String modality;
	private String seriesNumber;
	private String seriesDescription;
	private int numberOfSeriesRelatedInstances;
	private String performedProcedureStepStartDate;
	private String performedProcedureStepStartTime;
	private String sourceApplicationEntityTitle;
	private Set<RequestAttributes> requestAttributes = new HashSet<RequestAttributes>();

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudyGUID")
	@ForeignKey(name = "FK_Series_Study")
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	@Column(name = "SeriesInstanceUid", columnDefinition = "varchar(64)", nullable = false)
	public String getSeriesInstanceUid() {
		return seriesInstanceUid;
	}

	public void setSeriesInstanceUid(String seriesInstanceUid) {
		this.seriesInstanceUid = seriesInstanceUid;
	}

	@Column(name = "Modality", columnDefinition = "varchar(16)", nullable = false)
	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	@Column(name = "SeriesNumber", columnDefinition = "varchar(12)", nullable = true)
	public String getSeriesNumber() {
		return seriesNumber;
	}

	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}

	@Column(name = "SeriesDescription", columnDefinition = "nvarchar(64)", nullable = true)
	public String getSeriesDescription() {
		return seriesDescription;
	}

	public void setSeriesDescription(String seriesDescription) {
		this.seriesDescription = seriesDescription;
	}

	@Column(name = "NumberOfSeriesRelatedInstances", columnDefinition = "int", nullable = false)
	public int getNumberOfSeriesRelatedInstances() {
		return numberOfSeriesRelatedInstances;
	}

	public void setNumberOfSeriesRelatedInstances(
			int numberOfSeriesRelatedInstances) {
		this.numberOfSeriesRelatedInstances = numberOfSeriesRelatedInstances;
	}

	@Column(name = "PerformedProcedureStepStartDate", columnDefinition = "varchar(8)", nullable = false)
	public String getPerformedProcedureStepStartDate() {
		return performedProcedureStepStartDate;
	}

	public void setPerformedProcedureStepStartDate(
			String performedProcedureStepStartDate) {
		this.performedProcedureStepStartDate = performedProcedureStepStartDate;
	}

	@Column(name = "PerformedProcedureStepStartTime", columnDefinition = "varchar(16)", nullable = false)
	public String getPerformedProcedureStepStartTime() {
		return performedProcedureStepStartTime;
	}

	public void setPerformedProcedureStepStartTime(
			String performedProcedureStepStartTime) {
		this.performedProcedureStepStartTime = performedProcedureStepStartTime;
	}

	@Column(name = "SourceApplicationEntityTitle", columnDefinition = "varchar(16)", nullable = true)
	public String getSourceApplicationEntityTitle() {
		return sourceApplicationEntityTitle;
	}

	public void setSourceApplicationEntityTitle(
			String sourceApplicationEntityTitle) {
		this.sourceApplicationEntityTitle = sourceApplicationEntityTitle;
	}

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "series")
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Set<RequestAttributes> getRequestAttributes() {
		return requestAttributes;
	}

	public void setRequestAttributes(Set<RequestAttributes> requestAttributes) {
		this.requestAttributes = requestAttributes;
	}
}
