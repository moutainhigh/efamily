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


public class EfLocationMovemode extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 23232323l;
	//alias
	public static final String TABLE_ALIAS = "EfLocationMovemode";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_DEVICE_ID = "设备id";
	public static final String ALIAS_MOVE_MODE = "0静止 1步行 2跑步3骑车4汽车5火车6高铁7飞机";
	public static final String ALIAS_AVG_SPEED = "km/h";
	public static final String ALIAS_MIN_SPEED = "km/h";
	public static final String ALIAS_MAX_SPEED = "km/h";
	public static final String ALIAS_FROM_TIME = "开始时间";
	public static final String ALIAS_TO_TIME = "结束时间";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "creatorId";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "updatorId";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long userId;
	private Long deviceId;
	private Integer moveMode;
	private Double avgSpeed;
	private Double minSpeed;
	private Double maxSpeed;
	private Long fromTime;
	private Long toTime;
	private String curLocation;	//当前坐标
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public EfLocationMovemode(){
	}

	public EfLocationMovemode(
		Long id
	){
		this.id = id;
	}

	public enum MoveMode{ 
		//0静止 1步行 2跑步3骑车4汽车5火车6高铁7飞机
		STILL(-1),STOP(0),WALK(1),RUNNING(2),CYCLE(3),CAR(4),TRAIN(5),HIGHSPEEDRAIL(6),AIRCRAFT(7),UNDEFINE(8);
		private int _value;
		MoveMode(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}		
	}
	
	public enum MoveModeSpeed{ 
		//0静止 1步行 2跑步3骑车4汽车5火车6高铁7飞机
		Exception(-500,0,0),STOP(0,2,0),WALK(2,8,1),RUNNING(8,15,2),CYCLE(15,25,3),CAR(25,90,4),TRAIN(90,160,5),HIGHSPEEDRAIL(160,360,6),AIRCRAFT(360,2000,7),UNDEFINE(2000,Double.MAX_VALUE,8);
		private double _minvalue;
		private double _maxvalue;
		private int _moveMode;
		MoveModeSpeed(double value1,double value2,int moveMode){
			this._minvalue=value1;
			this._maxvalue=value2;
			this._moveMode = moveMode;
		}
		public String getValue(){
			return this._minvalue +","+	this._maxvalue;
		}
		public boolean isMoveMode(Double speed){
			return speed>=_minvalue&&speed<_maxvalue;
		}
		
		public int getMoveMode(){
			return _moveMode;
		}
	}
	
	public enum JuheDistance{
		//0静止 1步行 2跑步3骑车4汽车5火车6高铁7飞机
		STILL(-1,80),STOP(0,80),WALK(1,10),RUNNING(2,20),CYCLE(3,20),CAR(4,30),TRAIN(5,50),HIGHSPEEDRAIL(6,50),AIRCRAFT(7,50),UNDEFINE(8,120);
		
		private int _value;
		private double distance;
		JuheDistance(int value,double distance){
			this._value=value;
			this.distance = distance;
		}
		public int getValue(){
			return this._value;
		}
		public double getDistance(){
			return this.distance;
		}
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
	public void setMoveMode(Integer value) {
		this.moveMode = value;
	}
	
	public Integer getMoveMode() {
		return this.moveMode;
	}
	public void setAvgSpeed(Double value) {
		this.avgSpeed = value;
	}
	
	public Double getAvgSpeed() {
		return this.avgSpeed;
	}
	public void setMinSpeed(Double value) {
		this.minSpeed = value;
	}
	
	public Double getMinSpeed() {
		return this.minSpeed;
	}
	public void setMaxSpeed(Double value) {
		this.maxSpeed = value;
	}
	
	public Double getMaxSpeed() {
		return this.maxSpeed;
	}
	public void setFromTime(Long value) {
		this.fromTime = value;
	}
	
	public Long getFromTime() {
		return this.fromTime;
	}
	public void setToTime(Long value) {
		this.toTime = value;
	}
	
	public Long getToTime() {
		return this.toTime;
	}
	
	public String getCurLocation() {
		return curLocation;
	}

	public void setCurLocation(String curLocation) {
		this.curLocation = curLocation;
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
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("UserId",getUserId())		
		.append("DeviceId",getDeviceId())		
		.append("MoveMode",getMoveMode())		
		.append("AvgSpeed",getAvgSpeed())		
		.append("MinSpeed",getMinSpeed())		
		.append("MaxSpeed",getMaxSpeed())		
		.append("FromTime",getFromTime())		
		.append("ToTime",getToTime())		
		.append("Remark",getRemark())		
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
		.append(getMoveMode())
		.append(getAvgSpeed())
		.append(getMinSpeed())
		.append(getMaxSpeed())
		.append(getFromTime())
		.append(getToTime())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EfLocationMovemode == false) return false;
		if(this == obj) return true;
		EfLocationMovemode other = (EfLocationMovemode)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getUserId(),other.getUserId())

		.append(getDeviceId(),other.getDeviceId())

		.append(getMoveMode(),other.getMoveMode())

		.append(getAvgSpeed(),other.getAvgSpeed())

		.append(getMinSpeed(),other.getMinSpeed())

		.append(getMaxSpeed(),other.getMaxSpeed())

		.append(getFromTime(),other.getFromTime())

		.append(getToTime(),other.getToTime())

		.append(getRemark(),other.getRemark())

		.append(getCreatorId(),other.getCreatorId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdatorId(),other.getUpdatorId())

		.append(getUpdateTime(),other.getUpdateTime())

			.isEquals();
	}
}

