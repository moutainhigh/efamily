 
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
/**
 * 客户告警设置
 * @ClassName
 * @Description
 * @author ibm
 * 2016年9月13日
 */
public class EfCustomerAlarmSetting extends FmlBaseEntity {
	   
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5546157821762940502L;
	private Long customerId;	//告警ID
	private Integer alarmType;	//告警类型
	private String alarmUrl;	//告警回调URL
	private Integer status; //状态
	private String method;
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Integer getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	public String getAlarmUrl() {
		return alarmUrl;
	}
	public void setAlarmUrl(String alarmUrl) {
		this.alarmUrl = alarmUrl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}
	