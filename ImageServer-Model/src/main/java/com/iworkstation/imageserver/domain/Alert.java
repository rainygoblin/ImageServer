package com.iworkstation.imageserver.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.AlertCategoryEnum;
import com.iworkstation.imageserver.enumeration.AlertLevelEnum;

@Entity
@Table(name = "Alert")
public class Alert {

	private String guid;
	private Date insertTime;
	private String component;
	private int typeCode;
	private String source;
	private AlertLevelEnum alertLevelEnum;
	private AlertCategoryEnum alertCategoryEnum;
	private String content;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "InsertTime", columnDefinition = "datetime", nullable = false)
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "Component", columnDefinition = "nvarchar(50)", nullable = false)
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	@Column(name = "TypeCode", columnDefinition = "int", nullable = false)
	public int getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "Source", columnDefinition = "nvarchar(50)", nullable = false)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "AlertLevelEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public AlertLevelEnum getAlertLevelEnum() {
		return alertLevelEnum;
	}

	public void setAlertLevelEnum(AlertLevelEnum alertLevelEnum) {
		this.alertLevelEnum = alertLevelEnum;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "AlertCategoryEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public AlertCategoryEnum getAlertCategoryEnum() {
		return alertCategoryEnum;
	}

	public void setAlertCategoryEnum(AlertCategoryEnum alertCategoryEnum) {
		this.alertCategoryEnum = alertCategoryEnum;
	}

	@Column(name = "Content", columnDefinition = "xml", nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
