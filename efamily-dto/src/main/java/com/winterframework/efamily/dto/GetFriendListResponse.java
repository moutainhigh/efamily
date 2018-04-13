package com.winterframework.efamily.dto;
public class GetFriendListResponse { 

	private String userList ;

	private String familyUserList;
	
	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

	public String getFamilyUserList() {
		return familyUserList;
	}

	public void setFamilyUserList(String familyUserList) {
		this.familyUserList = familyUserList;
	}
	
}