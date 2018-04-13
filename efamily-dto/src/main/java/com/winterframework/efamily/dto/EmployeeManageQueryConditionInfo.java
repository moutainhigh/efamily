package com.winterframework.efamily.dto;

public class EmployeeManageQueryConditionInfo {

	private String queryValue;
	private String name;
	private String phoneNumber;
	private Long roleId;
	private Integer status;
	private String entryTimeStart;
	private String entryTimeEnd;
	
	public String getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getEntryTimeStart() {
		return entryTimeStart;
	}
	public void setEntryTimeStart(String entryTimeStart) {
		this.entryTimeStart = entryTimeStart;
	}
	public String getEntryTimeEnd() {
		return entryTimeEnd;
	}
	public void setEntryTimeEnd(String entryTimeEnd) {
		this.entryTimeEnd = entryTimeEnd;
	}
	@Override
	public String toString() {
		return "EmployeeManageQueryConditionInfo [queryValue=" + queryValue
				+ ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", roleId=" + roleId + ", status=" + status
				+ ", entryTimeStart=" + entryTimeStart + ", entryTimeEnd="
				+ entryTimeEnd + "]";
	}

	
}
