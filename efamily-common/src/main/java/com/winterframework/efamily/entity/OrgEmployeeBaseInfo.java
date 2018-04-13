package com.winterframework.efamily.entity;

import java.util.Date;

public class OrgEmployeeBaseInfo {

	private Long orgEmployeeId;
	private Long orgId;
	private String roleName;
	private String name;
	private Integer status;
	private Integer loginAuth;
	private String idNumber;
	private String phoneNumber;
	private Date entryTime;//入职时间
	private Date leaveTime;//离职时间
	
	
	public Long getOrgEmployeeId() {
		return orgEmployeeId;
	}
	public void setOrgEmployeeId(Long orgEmployeeId) {
		this.orgEmployeeId = orgEmployeeId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLoginAuth() {
		return loginAuth;
	}
	public void setLoginAuth(Integer loginAuth) {
		this.loginAuth = loginAuth;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	@Override
	public String toString() {
		return "OrgEmployeeBaseInfo [orgEmployeeId=" + orgEmployeeId
				+ ", orgId=" + orgId + ", roleName=" + roleName + ", name="
				+ name + ", status=" + status + ", loginAuth=" + loginAuth
				+ ", idNumber=" + idNumber + ", phoneNumber=" + phoneNumber
				+ ", entryTime=" + entryTime + ", leaveTime=" + leaveTime + "]";
	}

}
