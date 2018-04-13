 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.base.model.Response;
import com.winterframework.efamily.core.base.FmlBaseEntity;
import com.winterframework.modules.web.util.JsonMapper;
/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlHealthSleep extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "EjlHealthSleep";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_DEVICE_ID = "deviceId";
	public static final String ALIAS_FROM_TIME = "fromTime";
	public static final String ALIAS_TO_TIME = "toTime";
	public static final String ALIAS_DEEP_STRUC = "deepStruc";
	public static final String ALIAS_WAKE_STRUC = "wakeStruc";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	
	//date formats
			public static final String FORMAT_FROM_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_TO_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private Long deviceId;
	private Date fromTime;
	private Date toTime;
	private String deepStruc;
	private String wakeStruc;
	private List<HealthyMonitorDataDeep> deeps;
	private List<HealthyMonitorDataWake> wakes;
	
	//columns END

	public EjlHealthSleep(){
	}

	public EjlHealthSleep(
		Long id
	){
		this.id = id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setDeviceId(Long value) {
		this.deviceId = value;
	}
	
	public Long getDeviceId() {
		return this.deviceId;
	}
	public String getFromTimeString() {
		return date2String(getFromTime(), FORMAT_FROM_TIME);
	}
	public void setFromTimeString(String value) {
		setFromTime(string2Date(value, FORMAT_FROM_TIME,Date.class));
	}
	
	public void setFromTime(Date value) {
		this.fromTime = value;
	}
	
	public Date getFromTime() {
		return this.fromTime;
	}
	public String getToTimeString() {
		return date2String(getToTime(), FORMAT_TO_TIME);
	}
	public void setToTimeString(String value) {
		setToTime(string2Date(value, FORMAT_TO_TIME,Date.class));
	}
	
	public void setToTime(Date value) {
		this.toTime = value;
	}
	
	public Date getToTime() {
		return this.toTime;
	}
	public void setDeepStruc(String value) {
		this.deepStruc = value;
	}
	
	public String getDeepStruc() {
		return this.deepStruc;
	}
	public void setWakeStruc(String value) {
		this.wakeStruc = value;
	}
	
	public String getWakeStruc() {
		return this.wakeStruc;
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
	
    public List<HealthyMonitorDataDeep> getDeeps() {
    	if(deepStruc != null){
    		List<String> list = (List<String>)JsonMapper.nonEmptyMapper().fromJson(deepStruc, JsonMapper.nonEmptyMapper().createCollectionType(List.class, String.class));
    		List<HealthyMonitorDataDeep> deepList = new ArrayList<HealthyMonitorDataDeep>();
    		for(String str:list){
    			HealthyMonitorDataDeep deep = new HealthyMonitorDataDeep();
    			deep.setFromTimeDeep(Long.valueOf(str.split("-")[0]));
    			deep.setToTimeDeep(Long.valueOf(str.split("-")[1]));
    			deepList.add(deep);
    		}
			return deepList;
		}else{
			return new ArrayList<HealthyMonitorDataDeep>();
		}
	}

	public void setDeeps(List<HealthyMonitorDataDeep> deeps) {
		this.deeps = deeps;
	}

	public List<HealthyMonitorDataWake> getWakes() {
		if(wakeStruc != null){
			List<String> list = (List<String>)JsonMapper.nonEmptyMapper().fromJson(wakeStruc, JsonMapper.nonEmptyMapper().createCollectionType(List.class, String.class));
			List<HealthyMonitorDataWake> wakeList = new ArrayList<HealthyMonitorDataWake>();
			for(String str:list){
				HealthyMonitorDataWake wake = new HealthyMonitorDataWake();
				wake.setFromTimeWake(Long.valueOf(str.split("-")[0]));
				wake.setToTimeWake(Long.valueOf(str.split("-")[1]));
				wakeList.add(wake);
			}
			return wakeList;
		}else{
			return new ArrayList<HealthyMonitorDataWake>();
		}
	}

	public void setWakes(List<HealthyMonitorDataWake> wakes) {
		this.wakes = wakes;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("DeviceId",getDeviceId())		
		.append("FromTime",getFromTime())		
		.append("ToTime",getToTime())		
		.append("DeepStruc",getDeepStruc())		
		.append("WakeStruc",getWakeStruc())		
		.append("CreatorId",getCreatorId())		
		.append("CreateTime",getCreateTime())		
		.append("UpdatorId",getUpdatorId())		
		.append("UpdateTime",getUpdateTime())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getUserId())
		.append(getDeviceId())
		.append(getFromTime())
		.append(getToTime())
		.append(getDeepStruc())
		.append(getWakeStruc())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlHealthSleep == false) return false;
		if(this == obj) return true;
		EjlHealthSleep other = (EjlHealthSleep)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getDeviceId(),other.getDeviceId())

		.append(getFromTime(),other.getFromTime())

		.append(getToTime(),other.getToTime())

		.append(getDeepStruc(),other.getDeepStruc())

		.append(getWakeStruc(),other.getWakeStruc())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

