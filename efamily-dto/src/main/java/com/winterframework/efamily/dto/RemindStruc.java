/**   
* @Title: RemindStruc.java 
* @Package com.winterframework.efamily.server.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-9 下午4:42:10 
* @version V1.0   
*/
package com.winterframework.efamily.dto;

/** 
* @ClassName: RemindStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-9 下午4:42:10 
*  
*/
public class RemindStruc {

	private Long remindId;
	private String remindName;
	private Long remindType;
	private String remindContent;
	private Long deliverUserId;
	private Long deliverTime;
	private Long deliverDeadTime;
	private Long remindRepeatType;
	private String deliverUserName;
	private Long durationTime;
	private String remarkName;
	private String headImg;
	private Long userType;

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

	public Long getRemindType() {
		return remindType;
	}

	public void setRemindType(Long remindType) {
		this.remindType = remindType;
	}

	public String getRemindContent() {
		return remindContent;
	}

	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}

	public Long getDeliverUserId() {
		return deliverUserId;
	}

	public void setDeliverUserId(Long deliverUserId) {
		this.deliverUserId = deliverUserId;
	}

	public Long getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Long deliverTime) {
		this.deliverTime = deliverTime;
	}

	public Long getDeliverDeadTime() {
		return deliverDeadTime;
	}

	public void setDeliverDeadTime(Long deliverDeadTime) {
		this.deliverDeadTime = deliverDeadTime;
	}

	public Long getRemindRepeatType() {
		return remindRepeatType;
	}

	public void setRemindRepeatType(Long remindRepeatType) {
		this.remindRepeatType = remindRepeatType;
	}

	public String getDeliverUserName() {
		return deliverUserName;
	}

	public void setDeliverUserName(String deliverUserName) {
		this.deliverUserName = deliverUserName;
	}

	public Long getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Long durationTime) {
		this.durationTime = durationTime;
	}

	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}
}
