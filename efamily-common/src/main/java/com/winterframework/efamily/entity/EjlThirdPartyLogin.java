package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;

public class EjlThirdPartyLogin extends FmlBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String token;
	private Integer status;
	private Integer type;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
