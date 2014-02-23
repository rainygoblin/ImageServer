package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "RequestAttributes")
public class RequestAttributes {
	private String guid;
	private Series series;
	private String requestedProcedureId;
	private String scheduledProcedureStepId;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SeriesGUID")
	@ForeignKey(name = "FK_RequestAttributes_Series")
	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	@Column(name = "RequestedProcedureId", columnDefinition = "nvarchar(16)", nullable = false)
	public String getRequestedProcedureId() {
		return requestedProcedureId;
	}

	public void setRequestedProcedureId(String requestedProcedureId) {
		this.requestedProcedureId = requestedProcedureId;
	}

	@Column(name = "ScheduledProcedureStepId", columnDefinition = "nvarchar(16)", nullable = false)
	public String getScheduledProcedureStepId() {
		return scheduledProcedureStepId;
	}

	public void setScheduledProcedureStepId(String scheduledProcedureStepId) {
		this.scheduledProcedureStepId = scheduledProcedureStepId;
	}
}
