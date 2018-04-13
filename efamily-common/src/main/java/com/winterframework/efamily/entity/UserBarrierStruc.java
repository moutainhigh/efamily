package com.winterframework.efamily.entity;

public class UserBarrierStruc {

	private Long fenceId;
	private Long userId;
	private Integer status;
	private String location;
	private Long radius;
	private Integer type;
	private String remark;
	public Long getFenceId() {
		return fenceId;
	}
	public void setFenceId(Long fenceId) {
		this.fenceId = fenceId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
