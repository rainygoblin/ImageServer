package com.iworkstation.imageserver.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ApplicationLog")
public class ApplicationLog {
	private String guid;
	private String host;
	private Date timestamp;
	private String logLevel;
	private String thread;
	private String message;
	private String exception;

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

	@Column(name = "Host",columnDefinition = "nvarchar(50)",nullable=false)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Timestamp",columnDefinition = "datetime",nullable=false)
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "LogLevel",columnDefinition = "varchar(5)",nullable=false)
	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	@Column(name = "Thread",columnDefinition = "varchar(50)",nullable=false)
	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	@Column(name = "Message",columnDefinition = "nvarchar(2000)",nullable=false)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "Exception",columnDefinition = "nvarchar(2000)")
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
}
