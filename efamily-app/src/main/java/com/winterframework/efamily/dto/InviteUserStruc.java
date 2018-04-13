package com.winterframework.efamily.dto;

/** 
* @ClassName: InviteUserStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-21 下午4:54:41 
*  
*/
public class InviteUserStruc {

	private Long userId;
	private String userName;
	private Integer oprType;
	private String phoneNumber;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOprType() {
		return oprType;
	}

	public void setOprType(Integer oprType) {
		this.oprType = oprType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
}
