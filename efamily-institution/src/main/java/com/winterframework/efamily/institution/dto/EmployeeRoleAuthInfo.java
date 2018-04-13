package com.winterframework.efamily.institution.dto;

import java.util.ArrayList;
import java.util.List;

import com.winterframework.efamily.entity.UrlAuthInfo;

public class EmployeeRoleAuthInfo {

	private Long orgRoleId;
	private Long orgId;//机构ID
	private String name;//角色名称
	private String remark;//角色描述
	private String createUser;//角色创建者
	private String createTime;//角色创建时间
	private List<UrlAuthInfo> urlAuthInfoList = new ArrayList<UrlAuthInfo>();
	
	public Long getOrgRoleId() {
		return orgRoleId;
	}
	public void setOrgRoleId(Long orgRoleId) {
		this.orgRoleId = orgRoleId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<UrlAuthInfo> getUrlAuthInfoList() {
		return urlAuthInfoList;
	}
	public void setUrlAuthInfoList(List<UrlAuthInfo> urlAuthInfoList) {
		this.urlAuthInfoList = urlAuthInfoList;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "EmployeeRoleAuthInfo [orgRoleId=" + orgRoleId + ", orgId="
				+ orgId + ", name=" + name + ", remark=" + remark
				+ ", createUser=" + createUser + ", createTime=" + createTime
				+ ", urlAuthInfoList=" + urlAuthInfoList + "]";
	}

}
