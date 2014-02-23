package com.iworkstation.imageserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.iworkstation.imageserver.enumeration.ServerRuleApplyTimeEnum;
import com.iworkstation.imageserver.enumeration.ServerRuleTypeEnum;

@Entity
@Table(name = "ServerRule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.iworkstation.imageserver.domain.ServerEnum")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ServerRule {
	private String guid;
	private String ruleName;
	private String serverPartitionGUID;
	private ServerRuleTypeEnum serverRuleTypeEnum;
	private ServerRuleApplyTimeEnum serverRuleApplyTimeEnum;
	private boolean enabled;
	private boolean defaultRule;
	private boolean exemptRule;
	private String ruleXml;

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

	@Column(name = "RuleName", columnDefinition = "nvarchar(128)", nullable = false)
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name = "ServerPartitionGUID", columnDefinition = "uniqueidentifier", nullable = false)
	public String getServerPartitionGUID() {
		return serverPartitionGUID;
	}

	public void setServerPartitionGUID(String serverPartitionGUID) {
		this.serverPartitionGUID = serverPartitionGUID;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "ServerRuleTypeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public ServerRuleTypeEnum getServerRuleTypeEnum() {
		return serverRuleTypeEnum;
	}

	public void setServerRuleTypeEnum(ServerRuleTypeEnum serverRuleTypeEnum) {
		this.serverRuleTypeEnum = serverRuleTypeEnum;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "ServerRuleApplyTimeEnum", columnDefinition = "nvarchar(64)", nullable = false)
	public ServerRuleApplyTimeEnum getServerRuleApplyTimeEnum() {
		return serverRuleApplyTimeEnum;
	}

	public void setServerRuleApplyTimeEnum(
			ServerRuleApplyTimeEnum serverRuleApplyTimeEnum) {
		this.serverRuleApplyTimeEnum = serverRuleApplyTimeEnum;
	}

	@Column(name = "Enabled", columnDefinition = "bit", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "DefaultRule", columnDefinition = "bit", nullable = false)
	public boolean isDefaultRule() {
		return defaultRule;
	}

	public void setDefaultRule(boolean defaultRule) {
		this.defaultRule = defaultRule;
	}

	@Column(name = "ExemptRule", columnDefinition = "bit", nullable = false)
	public boolean isExemptRule() {
		return exemptRule;
	}

	public void setExemptRule(boolean exemptRule) {
		this.exemptRule = exemptRule;
	}

	@Column(name = "RuleXml", columnDefinition = "xml", nullable = false)
	public String getRuleXml() {
		return ruleXml;
	}

	public void setRuleXml(String ruleXml) {
		this.ruleXml = ruleXml;
	}
}
