package com.winterframework.efamily.dto;

/** 
* @ClassName: AddressBookStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-16 下午1:57:00 
*  
*/
public class AddressBookStruc {
	private String name;
	private String headImage;
	private String phoneNumber;
	private Long userId;
	private Long isSos;
	
	
	public String getName() {
		return name;
	}
	public Long getIsSos() {
		return isSos;
	}
	public void setIsSos(Long isSos) {
		this.isSos = isSos;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
