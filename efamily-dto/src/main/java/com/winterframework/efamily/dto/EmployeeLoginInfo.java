package com.winterframework.efamily.dto;

public class EmployeeLoginInfo {

	private Long orgEmployeeId;//用户ID
	private String orgEmployeeName;//用户名称
	private Long orgId;//机构ID
	private String orgName;//机构名称
	private Long loginTime;//登录时间
	private Long lastRequestTime;//最后刷新时间
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
	public Long getLastRequestTime() {
		return lastRequestTime;
	}
	public void setLastRequestTime(Long lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}
	
	public Long getOrgEmployeeId() {
		return orgEmployeeId;
	}
	public void setOrgEmployeeId(Long orgEmployeeId) {
		this.orgEmployeeId = orgEmployeeId;
	}
	public String getOrgEmployeeName() {
		return orgEmployeeName;
	}
	public void setOrgEmployeeName(String orgEmployeeName) {
		this.orgEmployeeName = orgEmployeeName;
	}
	@Override
	public String toString() {
		return "EmployeeLoginInfo [orgEmployeeId=" + orgEmployeeId
				+ ", orgEmployeeName=" + orgEmployeeName + ", orgId=" + orgId
				+ ", orgName=" + orgName + ", loginTime=" + loginTime
				+ ", lastRequestTime=" + lastRequestTime + "]";
	}

	
}
