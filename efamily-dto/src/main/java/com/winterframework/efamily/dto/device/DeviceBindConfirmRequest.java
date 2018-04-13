package com.winterframework.efamily.dto.device;

public class DeviceBindConfirmRequest{
	private Long toId;	//手机用户ID
	private int status;	//绑定结果
		
	public Long getToId() {
		return toId;
	}
	public void setToId(Long toId) {
		this.toId = toId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
