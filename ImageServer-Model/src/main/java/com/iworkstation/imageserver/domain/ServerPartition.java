package com.iworkstation.imageserver.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.DuplicateSopPolicyEnum;

@Entity
@Table(name = "ServerPartition")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.iworkstation.imageserver.domain.ServerPartition")
public class ServerPartition {
	private String guid;
	private boolean enabled;
	private String name;
	private String description;
	private String aeTitle;
	private int numberOfThreads=3;
	private int port;
	private int stgCmtPort = 104;
	private String partitionFolder;
	private boolean acceptAnyDevice;
	private boolean auditDeleteStudy;
	private boolean autoInsertDevice;
	private int defaultRemotePort;
	private int studyCount = 0;
	private DuplicateSopPolicyEnum duplicateSopPolicyEnum;
	private int dimseRspDelay;
	private int idleTimeout;
	private int dimseRspTimeout;
	private boolean matchAccessionNumber;
	private boolean matchIssuerOfPatientId;
	private boolean matchPatientId;
	private boolean matchPatientsBirthDate;
	private boolean matchPatientsName;
	private boolean matchPatientsSex;
	private boolean stgcmtEnabled = true;
	private int associationReaperPeriod;

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

	@Column(name = "Enabled", columnDefinition = "bit", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "Name", columnDefinition = "nvarchar(128)", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Description", columnDefinition = "nvarchar(128)", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "AeTitle", columnDefinition = "varchar(16)", nullable = false)
	public String getAeTitle() {
		return aeTitle;
	}

	public void setAeTitle(String aeTitle) {
		this.aeTitle = aeTitle;
	}

	@Column(name = "NumberOfThreads", columnDefinition = "int", nullable = false)
	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}

	@Column(name = "Port", columnDefinition = "int", nullable = false)
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Column(name = "StgCmtPort", columnDefinition = "int", nullable = true)
	public int getStgCmtPort() {
		return stgCmtPort;
	}

	public void setStgCmtPort(int stgCmtPort) {
		this.stgCmtPort = stgCmtPort;
	}

	@Column(name = "PartitionFolder", columnDefinition = "nvarchar(16)", nullable = false)
	public String getPartitionFolder() {
		return partitionFolder;
	}

	public void setPartitionFolder(String partitionFolder) {
		this.partitionFolder = partitionFolder;
	}

	@Column(name = "AcceptAnyDevice", columnDefinition = "bit", nullable = false)
	public boolean isAcceptAnyDevice() {
		return acceptAnyDevice;
	}

	public void setAcceptAnyDevice(boolean acceptAnyDevice) {
		this.acceptAnyDevice = acceptAnyDevice;
	}

	@Column(name = "AuditDeleteStudy", columnDefinition = "bit", nullable = false)
	public boolean isAuditDeleteStudy() {
		return auditDeleteStudy;
	}

	public void setAuditDeleteStudy(boolean auditDeleteStudy) {
		this.auditDeleteStudy = auditDeleteStudy;
	}

	@Column(name = "AutoInsertDevice", columnDefinition = "bit", nullable = false)
	public boolean isAutoInsertDevice() {
		return autoInsertDevice;
	}

	public void setAutoInsertDevice(boolean autoInsertDevice) {
		this.autoInsertDevice = autoInsertDevice;
	}

	@Column(name = "DefaultRemotePort", columnDefinition = "int", nullable = false)
	public int getDefaultRemotePort() {
		return defaultRemotePort;
	}

	public void setDefaultRemotePort(int defaultRemotePort) {
		this.defaultRemotePort = defaultRemotePort;
	}

	@Column(name = "StudyCount", columnDefinition = "int", nullable = false)
	public int getStudyCount() {
		return studyCount;
	}

	public void setStudyCount(int studyCount) {
		this.studyCount = studyCount;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "DuplicateSopPolicyEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public DuplicateSopPolicyEnum getDuplicateSopPolicyEnum() {
		return duplicateSopPolicyEnum;
	}

	public void setDuplicateSopPolicyEnum(
			DuplicateSopPolicyEnum duplicateSopPolicyEnum) {
		this.duplicateSopPolicyEnum = duplicateSopPolicyEnum;
	}

	@Column(name = "MatchAccessionNumber", columnDefinition = "bit", nullable = false)
	public boolean isMatchAccessionNumber() {
		return matchAccessionNumber;
	}

	public void setMatchAccessionNumber(boolean matchAccessionNumber) {
		this.matchAccessionNumber = matchAccessionNumber;
	}

	@Column(name = "MatchIssuerOfPatientId", columnDefinition = "bit", nullable = false)
	public boolean isMatchIssuerOfPatientId() {
		return matchIssuerOfPatientId;
	}

	public void setMatchIssuerOfPatientId(boolean matchIssuerOfPatientId) {
		this.matchIssuerOfPatientId = matchIssuerOfPatientId;
	}

	@Column(name = "MatchPatientId", columnDefinition = "bit", nullable = false)
	public boolean isMatchPatientId() {
		return matchPatientId;
	}

	public void setMatchPatientId(boolean matchPatientId) {
		this.matchPatientId = matchPatientId;
	}

	@Column(name = "StgcmtEnabled", columnDefinition = "bit", nullable = true)
	public boolean isStgcmtEnabled() {
		return stgcmtEnabled;
	}

	public void setStgcmtEnabled(boolean stgcmtEnabled) {
		this.stgcmtEnabled = stgcmtEnabled;
	}

	@Column(name = "IdleTimeout", columnDefinition = "int", nullable = true)
	public int getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	@Column(name = "DimseRspTimeout", columnDefinition = "int", nullable = true)
	public int getDimseRspTimeout() {
		return dimseRspTimeout;
	}

	public void setDimseRspTimeout(int dimseRspTimeout) {
		this.dimseRspTimeout = dimseRspTimeout;
	}

	@Column(name = "DimseRspDelay", columnDefinition = "int", nullable = true)
	public int getDimseRspDelay() {
		return dimseRspDelay;
	}

	public void setDimseRspDelay(int dimseRspDelay) {
		this.dimseRspDelay = dimseRspDelay;
	}

	@Column(name = "MatchPatientsBirthDate", columnDefinition = "bit", nullable = false)
	public boolean isMatchPatientsBirthDate() {
		return matchPatientsBirthDate;
	}

	public void setMatchPatientsBirthDate(boolean matchPatientsBirthDate) {
		this.matchPatientsBirthDate = matchPatientsBirthDate;
	}

	@Column(name = "MatchPatientsName", columnDefinition = "bit", nullable = false)
	public boolean isMatchPatientsName() {
		return matchPatientsName;
	}

	public void setMatchPatientsName(boolean matchPatientsName) {
		this.matchPatientsName = matchPatientsName;
	}

	@Column(name = "MatchPatientsSex", columnDefinition = "bit", nullable = false)
	public boolean isMatchPatientsSex() {
		return matchPatientsSex;
	}

	public void setMatchPatientsSex(boolean matchPatientsSex) {
		this.matchPatientsSex = matchPatientsSex;
	}

	@Column(name = "AssociationReaperPeriod", columnDefinition = "int", nullable = true)
	public int getAssociationReaperPeriod() {
		return associationReaperPeriod;
	}

	public void setAssociationReaperPeriod(int associationReaperPeriod) {
		this.associationReaperPeriod = associationReaperPeriod;
	}
}
