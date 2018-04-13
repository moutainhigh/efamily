package com.winterframework.efamily.institution.dto;

public class OrgRoleDto {

	private Long orgRoleId;
	private Long orgId;//机构ID
	private String name;//角色名称
	private String remark;//角色描述
	private String authIds;//权限的Ids 用 逗号隔开
	
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
	public String getAuthIds() {
		return authIds;
	}
	public void setAuthIds(String authIds) {
		this.authIds = authIds;
	}
	@Override
	public String toString() {
		return "OrgRoleDto [orgRoleId=" + orgRoleId + ", orgId=" + orgId
				+ ", name=" + name + ", remark=" + remark + ", authIds="
				+ authIds + "]";
	}

}
