package com.winterframework.efamily.base.model;

/**
 * 推送规则
 * @ClassName
 * @Description
 * @author ibm
 * 2015年9月4日
 */
public class NotyRule {
	private boolean isOffline;	//是否离线发送
	private int expireTime;	//离线过期时间(minute) -1则无限制
	private boolean isHsn;	//是否高速网络发送
	private int priority;	//优先级(5,4,3,2,1优先级依次降低)
	private int index;	//队列索引
	public boolean isOffline() {
		return isOffline;
	}
	public void setOffline(boolean isOffline) {
		this.isOffline = isOffline;
	}
	public int getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}
	public boolean isHsn() {
		return isHsn;
	}
	public void setHsn(boolean isHsn) {
		this.isHsn = isHsn;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}	
	
}
