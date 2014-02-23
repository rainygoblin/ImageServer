package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ServerSopClass")
public class ServerSopClass {
	private String guid;
	private String sopClassUid;
	private String description;
	private boolean nonImage;

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

	@Column(name = "SopClassUid", columnDefinition = "varchar(64)", nullable = false)
	public String getSopClassUid() {
		return sopClassUid;
	}

	public void setSopClassUid(String sopClassUid) {
		this.sopClassUid = sopClassUid;
	}

	@Column(name = "Description", columnDefinition = "nvarchar(128)", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "NonImage", columnDefinition = "bit", nullable = false)
	public boolean isNonImage() {
		return nonImage;
	}

	public void setNonImage(boolean nonImage) {
		this.nonImage = nonImage;
	}
}
