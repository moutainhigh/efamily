package com.winterframework.efamily.institution.dto;

public class UserFenceNameAndIdInfo {
	
	private Long orgId;
	private String location;
	private Long radius;
	private Integer type;
	private String address;
	private String createTime;
	public Long fenceId;
	public String name;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getRadius() {
		return radius;
	}
	public void setRadius(Long radius) {
		this.radius = radius;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getFenceId() {
		return fenceId;
	}
	public void setFenceId(Long fenceId) {
		this.fenceId = fenceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "UserFenceNameAndIdInfo [orgId=" + orgId 
				+ ", location=" + location + ", radius=" + radius + ", type="
				+ type + ", address=" + address + ", createTime=" + createTime
				+ ", fenceId=" + fenceId + ", name=" + name + "]";
	}

	
}
