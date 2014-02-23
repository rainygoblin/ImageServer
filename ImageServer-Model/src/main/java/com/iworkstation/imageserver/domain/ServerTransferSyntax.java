package com.iworkstation.imageserver.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ServerTransferSyntax")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.iworkstation.imageserver.domain.ServerTransferSyntax")
public class ServerTransferSyntax {
	private String guid;
	private String uid;
	private String description;
	private boolean lossless;

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

	@Column(name = "Uid", columnDefinition = "varchar(64)", nullable = true)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "Description", columnDefinition = "nvarchar(256)", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Lossless", columnDefinition = "bit", nullable = false)
	public boolean isLossless() {
		return lossless;
	}

	public void setLossless(boolean lossless) {
		this.lossless = lossless;
	}
}
