 
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
/**
 * 设备告警目标
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月13日
 */
public class EfDeviceAlarmTarget extends FmlBaseEntity {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = -4580901531726012959L;
	private Long alarmId;	//告警ID
	private Integer alarmType;	//告警类型
	private Long targetId;	//目标ID
	private Integer isCustomer;	//是否客户类型
	private String value;	//告警内容
	private Integer status; //状态
	
	public enum Status{
		FAILED(-1),INIT(0),SUCCESS(1);
		private int _value;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}
	
	public Long getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}
	
	public Integer getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public Integer getIsCustomer() {
		return isCustomer;
	}
	public void setIsCustomer(Integer isCustomer) {
		this.isCustomer = isCustomer;
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
	@Override
	public String toString() {
		return "EfDeviceAlarmTarget [alarmId=" + alarmId + ", alarmType="
				+ alarmType + ", targetId=" + targetId + ", isCustomer="
				+ isCustomer + ", value=" + value + ", status=" + status + "]";
	}
	
	
	
}
	

