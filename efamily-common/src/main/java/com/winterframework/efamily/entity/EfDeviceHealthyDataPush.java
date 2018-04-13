 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;
 
import java.util.*;



import com.winterframework.efamily.core.base.FmlBaseEntity;


/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EfDeviceHealthyDataPush extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "EfDeviceHealthyDataPush";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_DEVICE_ID = "设备id";
	public static final String ALIAS_IMEI = "设备IMEI";
	public static final String ALIAS_TYPE = "类型(7心率,8血压)";
	public static final String ALIAS_HEALTHY_ID = "健康表对应id 心率或血压表ID";
	public static final String ALIAS_CUSTOMER_ID = "客户id";
	public static final String ALIAS_SEND_NUMBER = "发送次数";
	public static final String ALIAS_HEALTHY_DATA = "健康数据";
	public static final String ALIAS_STATUS = "0未发送 1已发送 2发送失败";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private Long deviceId;
	private String imei;
	private Integer type;
	private Long healthyId;
	private Long customerId;
	private Integer sendNumber;
	private String healthyData;
	private Integer status;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public EfDeviceHealthyDataPush(){
	}

	public enum Status{
		INIT(0),SUCCESS(1),FAILED(2);
		private int _value;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return _value;
		}
	}
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



	public Long getHealthyId() {
		return healthyId;
	}



	public void setHealthyId(Long healthyId) {
		this.healthyId = healthyId;
	}



	public Long getCustomerId() {
		return customerId;
	}



	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}



	public void setImei(String value) {
		this.imei = value;
	}
	
	public String getImei() {
		return this.imei;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public void setSendNumber(Integer value) {
		this.sendNumber = value;
	}
	
	public Integer getSendNumber() {
		return this.sendNumber;
	}
	
	public Integer addSendNumber() {
		return this.sendNumber++;
	}
	
	public void setHealthyData(String value) {
		this.healthyData = value;
	}
	
	public String getHealthyData() {
		return this.healthyData;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setCreatorId(Long value) {
		this.creatorId = value;
	}
	
	public Long getCreatorId() {
		return this.creatorId;
	}
	public String getCreateTimeString() {
		return date2String(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(string2Date(value, FORMAT_CREATE_TIME,Date.class));
	}
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdatorId(Long value) {
		this.updatorId = value;
	}
	
	public Long getUpdatorId() {
		return this.updatorId;
	}
	public String getUpdateTimeString() {
		return date2String(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(string2Date(value, FORMAT_UPDATE_TIME,Date.class));
	}
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}


	@Override
	public String toString() {
		return "EfDeviceHealthyDataPush [userId=" + userId + ", deviceId="
				+ deviceId + ", imei=" + imei + ", type=" + type
				+ ", healthyId=" + healthyId + ", customerId=" + customerId
				+ ", sendNumber=" + sendNumber + ", healthyData=" + healthyData
				+ ", status=" + status + ", remark=" + remark + ", creatorId="
				+ creatorId + ", createTime=" + createTime + ", updatorId="
				+ updatorId + ", updateTime=" + updateTime + "]";
	}
	
	

}

