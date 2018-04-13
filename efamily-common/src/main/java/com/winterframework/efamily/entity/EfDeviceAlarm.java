 
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class EfDeviceAlarm extends FmlBaseEntity {
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = -4367081812275333551L;
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private Integer type;	//告警类型
	private Long time; //操作时间
	private String value;	//告警值
	private Integer status;	//状态
	
	
	public enum Type{
		SOS(1),SIGNAL(2),BATTERY(3),FENCE(4),SITTING(5),SLEEP(6),HEART(7),BLOOD(8),BLOODSUGAR(9);
		private int _value;
		Type(int value){
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}
	public enum Status{
		INVALID(-1),UNHANDLED(0),HANDLED(1),FAILED(2);
		private int _value;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
	

