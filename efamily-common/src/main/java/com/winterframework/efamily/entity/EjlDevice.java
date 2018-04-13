 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlDevice extends FmlBaseEntity {
	
	private static final long serialVersionUID = -9135864439219711054L;
	//alias
	public static final String TABLE_ALIAS = "平台周边设备表（手机，手环，血糖仪。。）";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_TYPE = "1 手表 2 手环...";
	public static final String ALIAS_STATUS = "1  可用  （预留）";
	public static final String ALIAS_CODE = "设备标示符";
	public static final String ALIAS_USER_ID = "当前设备属于哪个用户使用中";
	public static final String ALIAS_FAMILY_ID = "当前设备所属的家庭";
	
	//date formats
	
	//columns START
	private String name;
	private Integer type;
	private Long status;
	private String code;
	private Long userId;
	private Long familyId;
	private Integer volume;
	private Integer brightness;
	private Integer quietMode;
	private Integer shake;
	private String  phoneNumber;
	private Integer onlineStatus;
	private Integer sleeplockStatus;
	private Date bindOnOffTime;
	private Integer runningMode;
	private String qrcodeResourceId;
	private String softwareVersion;	//软件版本
	private Integer softwareStatus;	//软件版本更新状态
	private Long softwareUpdatorId;	//软件版本更新人
	private Date softwareUpdateTime;	//软件版本更新时间
	private Date softwareFinishTime;	//软件版本更新完善时间
	private Integer operType;	//操作类型
	private Integer operStatus;	//操作状态
	private Long operUserId;	//操作用户ID
	private Date operTime;	//操作时间
	private Date operFinishTime;	//操作完成时间
	private Long bindUserId;	//绑定用户ID
	private Date bindTime;	//绑定时间
	private Date bindFinishTime;	//绑定完成时间
	
	public enum Type{
		WATCH(1),RING(2);
		private int _value;
		Type(int value){
			_value=value;
		}
		public int getValue(){
			return _value;
		}
	}
	
	public enum STATUS {
		//1绑定 0解绑
		BIND(1),
		UNBIND(0);
		
		private int _value;
		STATUS(int v){
			_value=v;
		}
		public int getValue() {
			return _value;
		}
		public void setValue(int _value) {
			this._value = _value;
		}
		
	}
	public enum SoftwareStatus {
		// 0初始 1完成2进行中
		INIT(0),
		DOING(1),
		SUCCESS(2),
		FAILED(3);
		
		private int _value;
		SoftwareStatus(int v){
			_value=v;
		}
		public int getValue() {
			return _value;
		}
		public void setValue(int _value) {
			this._value = _value;
		}
		
	}
	public enum OperType {
		// 1心率2血压
		HEART(1),
		BLOOD(2);
		
		private int _value;
		OperType(int v){
			_value=v;
		}
		public int getValue() {
			return _value;
		}
		public void setValue(int _value) {
			this._value = _value;
		}
		
	}
	public enum OperStatus {
		// 0初始 1请求2开始3结束
		INIT(0),
		REQ(1),
		START(2),
		FINISH(3);
		
		private int _value;
		OperStatus(int v){
			_value=v;
		}
		public int getValue() {
			return _value;
		}
		public void setValue(int _value) {
			this._value = _value;
		}
		
	}
	//columns END
	public EjlDevice(){
	}
	
	public String getQrcodeResourceId() {
		return qrcodeResourceId;
	}

	public void setQrcodeResourceId(String qrcodeResourceId) {
		this.qrcodeResourceId = qrcodeResourceId;
	}



	public Integer getRunningMode() {
		return runningMode;
	}

	public void setRunningMode(Integer runningMode) {
		this.runningMode = runningMode;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getBrightness() {
		return brightness;
	}

	public void setBrightness(Integer brightness) {
		this.brightness = brightness;
	}

	public Integer getQuietMode() {
		return quietMode;
	}

	public void setQuietMode(Integer quietMode) {
		this.quietMode = quietMode;
	}

	public Integer getShake() {
		return shake;
	}

	public void setShake(Integer shake) {
		this.shake = shake;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public EjlDevice(
		Long id
	){
		this.id = id;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setFamilyId(Long value) {
		this.familyId = value;
	}
	
	public Long getFamilyId() {
		return this.familyId;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getSleeplockStatus() {
		return sleeplockStatus;
	}

	public void setSleeplockStatus(Integer sleeplockStatus) {
		this.sleeplockStatus = sleeplockStatus;
	}

	
	public Date getBindOnOffTime() {
		return bindOnOffTime;
	}

	public void setBindOnOffTime(Date bindOnOffTime) {
		this.bindOnOffTime = bindOnOffTime;
	}
	
	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	
	public Integer getSoftwareStatus() {
		return softwareStatus;
	}

	public void setSoftwareStatus(Integer softwareStatus) {
		this.softwareStatus = softwareStatus;
	}

	public Long getSoftwareUpdatorId() {
		return softwareUpdatorId;
	}

	public void setSoftwareUpdatorId(Long softwareUpdatorId) {
		this.softwareUpdatorId = softwareUpdatorId;
	}

	public Date getSoftwareUpdateTime() {
		return softwareUpdateTime;
	}

	public void setSoftwareUpdateTime(Date softwareUpdateTime) {
		this.softwareUpdateTime = softwareUpdateTime;
	}
	
	public Date getSoftwareFinishTime() {
		return softwareFinishTime;
	}

	public void setSoftwareFinishTime(Date softwareFinishTime) {
		this.softwareFinishTime = softwareFinishTime;
	}
	
	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public Long getOperUserId() {
		return operUserId;
	}

	public void setOperUserId(Long operUserId) {
		this.operUserId = operUserId;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Date getOperFinishTime() {
		return operFinishTime;
	}

	public void setOperFinishTime(Date operFinishTime) {
		this.operFinishTime = operFinishTime;
	}
	
	public Long getBindUserId() {
		return bindUserId;
	}

	public void setBindUserId(Long bindUserId) {
		this.bindUserId = bindUserId;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public Date getBindFinishTime() {
		return bindFinishTime;
	}

	public void setBindFinishTime(Date bindFinishTime) {
		this.bindFinishTime = bindFinishTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Name",getName())		
		.append("Type",getType())		
		.append("Status",getStatus())		
		.append("Code",getCode())		
		.append("GmtCreated",getGmtCreated())		
		.append("UserId",getUserId())		
		.append("FamilyId",getFamilyId())	
		.append("BindOnOffTime",getBindOnOffTime())	
		.append("RunnigMode",getRunningMode())	
			.toString();
	}
	
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getName())
		.append(getType())
		.append(getStatus())
		.append(getCode())
		.append(getGmtCreated())
		.append(getUserId())
		.append(getFamilyId())
		.append(getBindOnOffTime())
		.append(getRunningMode())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlDevice == false) return false;
		if(this == obj) return true;
		EjlDevice other = (EjlDevice)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getName(),other.getName())

		.append(getType(),other.getType())

		.append(getStatus(),other.getStatus())

		.append(getCode(),other.getCode())

		.append(getGmtCreated(),other.getGmtCreated())

		.append(getUserId(),other.getUserId())

		.append(getFamilyId(),other.getFamilyId())
		
		.append(getBindOnOffTime(),other.getBindOnOffTime())
		
		.append(getRunningMode(),other.getRunningMode())
		
			.isEquals();
	}
}

