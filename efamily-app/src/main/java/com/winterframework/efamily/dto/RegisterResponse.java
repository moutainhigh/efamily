package com.winterframework.efamily.dto;

/**
 * 注册返回dto
 * @author david
 *
 */
public class RegisterResponse {
	private Long userId;
	
    private String token;//根据客户端传递的一些信息加密一个字符串，确定用户是否需要重新登陆

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
}
