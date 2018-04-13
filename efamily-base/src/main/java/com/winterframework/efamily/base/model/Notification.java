package com.winterframework.efamily.base.model;

public class Notification {
	private NotyTarget target;	//推送目标 
	private NotyMessage message;	//推送消息
	private boolean isRealTime;	//是否实时推送
	public NotyTarget getTarget() {
		return target;
	}
	public void setTarget(NotyTarget target) {
		this.target = target;
	}
	public NotyMessage getMessage() {
		return message;
	}
	public void setMessage(NotyMessage message) {
		this.message = message;
	}
	public boolean isRealTime() {
		return isRealTime;
	}
	public void setRealTime(boolean isRealTime) {
		this.isRealTime = isRealTime;
	}
	
	
}
