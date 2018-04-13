package com.winterframework.efamily.dto.device;

public class NotyTaskRequest{
	private Long id;	//ID
	private Integer notyType;	//消息类型
	private Long messageId;	//消息ID
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private Integer status;	//状态(0:未发送 1:已发送 2:已送达 7:已删除)
	private String remark;	//备注
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNotyType() {
		return notyType;
	}
	public void setNotyType(Integer notyType) {
		this.notyType = notyType;
	}
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
