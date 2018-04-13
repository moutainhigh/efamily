package com.winterframework.efamily.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountNumberBaseInfo {
	
	private Long orgId;
	private String orgName;
	private String roleName;
	private String name;
	private Integer status;
	private String phoneNumber;
	private String createAuthUser;
	private Date createAuthTime;
	private List<UrlAuthInfo> urlAuthInfoList = new ArrayList<UrlAuthInfo>();
	
	public List<UrlAuthInfo> getUrlAuthInfoList() {
		return urlAuthInfoList;
	}
	public void setUrlAuthInfoList(List<UrlAuthInfo> urlAuthInfoList) {
		this.urlAuthInfoList = urlAuthInfoList;
	}
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCreateAuthUser() {
		return createAuthUser;
	}
	public void setCreateAuthUser(String createAuthUser) {
		this.createAuthUser = createAuthUser;
	}
	public Date getCreateAuthTime() {
		return createAuthTime;
	}
	public void setCreateAuthTime(Date createAuthTime) {
		this.createAuthTime = createAuthTime;
	}
	@Override
	public String toString() {
		return "AccountNumberBaseInfo [orgId=" + orgId + ", orgName=" + orgName
				+ ", roleName=" + roleName + ", name=" + name + ", status="
				+ status + ", phoneNumber=" + phoneNumber + ", createAuthUser="
				+ createAuthUser + ", createAuthTime=" + createAuthTime
				+ ", urlAuthInfoList=" + urlAuthInfoList + "]";
	}

}
