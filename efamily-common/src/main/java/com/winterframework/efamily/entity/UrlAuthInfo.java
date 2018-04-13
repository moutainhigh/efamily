package com.winterframework.efamily.entity;

public class UrlAuthInfo {
	
	private Long authId;//authId
	private String name;//权限名称
	private Integer status;//是否拥有 权限: 0 不拥有  1 拥有

	public Long getAuthId() {
		return authId;
	}
	public void setAuthId(Long authId) {
		this.authId = authId;
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
	@Override
	public String toString() {
		return "UrlAuthInfo [authId=" + authId + ", name=" + name + ", status="
				+ status + "]";
	}

}
