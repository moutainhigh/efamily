/**   
* @Title: RemindTaskStruc.java 
* @Package com.winterframework.efamily.server.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-2 下午5:30:06 
* @version V1.0   
*/
package com.winterframework.efamily.dto;

/** 
* @ClassName: RemindTaskStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-2 下午5:30:06 
*  
*/
public class RemindTaskStruc {

	private Long remindId;
	private String remindName;
	private Long userId;
	private String userName;
	public Long getRemindId() {
		return remindId;
	}
	public void setRemindId(Long remindId) {
		this.remindId = remindId;
	}
	public String getRemindName() {
		return remindName;
	}
	public void setRemindName(String remindName) {
		this.remindName = remindName;
	}
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
}
