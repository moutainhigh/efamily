package com.winterframework.efamily.institution.dto;

import java.util.ArrayList;
import java.util.List;

public class UserFenceBaseInfo {

	private Long fenceId;
	private String location;
	private Long radius;
	private Long orgId;
	private String name;
	private String address;
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getFenceId() {
		return fenceId;
	}
	public void setFenceId(Long fenceId) {
		this.fenceId = fenceId;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "UserFenceBaseInfo [fenceId=" + fenceId + ", location="
				+ location + ", radius=" + radius + ", orgId=" + orgId
				+ ", name=" + name + ", address=" + address + "]";
	}
}
