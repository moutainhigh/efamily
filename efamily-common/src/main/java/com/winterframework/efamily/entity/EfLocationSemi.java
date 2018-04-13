 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * 定位处理半成品
 * @ClassName
 * @Description
 * @author ibm
 * 2016年3月8日
 */
public class EfLocationSemi extends FmlBaseEntity  implements Cloneable{
	  
	//date formats
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7813223384722844266L;
	//columns START
	private Long userId;	//用户ID
	private Long deviceId;	//设备ID
	private double longitude;	//经度
	private double latitude;	//纬度
	private Date time;	//坐标时间
	private Integer type;	//坐标类型
	private Integer source;	//坐标来源
	private Long sourceId;	//来源坐标ID
	private Integer status;	//状态(0 无效 1有效)
	private Integer flag;	//处理标识(0未处理 6处理坐标详情 9已完成)
	private double suspScore;	//可疑分值
	private Integer aggrCount;	//聚合点数
	private Date timeBegin;	//开始时间
	private Date timeEnd;	//结束时间
	private Long previousId;	//前一坐标ID
	private Long nextId;	//后一坐标ID
	private double distance;	//与前点距离(m)
	private String city;	//城市
	private String address;	//地址
	private Long radius;//半径 精度
	//columns END
	public enum Flag{ 
		//0未处理  4聚合删除 5聚合完成 6处理坐标详情 9已完成
		UNHANDLE(0),FILTERED(1),LINKED(2),DELETE(4),JUHEFINISH(5),INITJUHE(6),NEIGHBORING(7),DETAIL(30),SUSPECT(31),RETAIN(32),DISPOSE(33),FINISH(99);
		private int _value;
		Flag(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
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

	public Long getRadius() {
		return radius;
	}
	public void setRadius(Long radius) {
		this.radius = radius;
	}
	public String getLocation() {
		return latitude+","+longitude;
	}
	public void setLocation(String location) {
		this.latitude = Double.valueOf(location.split(",")[0]);
		this.longitude = Double.valueOf(location.split(",")[1]);
	}
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	public double getSuspScore() {
		return suspScore;
	}
	public void setSuspScore(double suspScore) {
		this.suspScore = suspScore;
	}
	public Integer getAggrCount() {
		return aggrCount;
	}
	public void setAggrCount(Integer aggrCount) {
		this.aggrCount = aggrCount;
	}
	public Date getTimeBegin() {
		return timeBegin;
	}
	public void setTimeBegin(Date timeBegin) {
		this.timeBegin = timeBegin;
	}
	public Date getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Long getPreviousId() {
		return previousId;
	}
	public void setPreviousId(Long previousId) {
		this.previousId = previousId;
	}
	public Long getNextId() {
		return nextId;
	}
	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(this.getDeviceId())
		.append(this.getUserId())
		.append(this.getTime())
		.append(this.getSource())
		.append(this.getType())
		.append(getRemark())
		.append(getCreatorId())
		.append(getCreateTime())
		.append(getUpdatorId())
		.append(getUpdateTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EfLocationSemi == false) return false;
		if(this == obj) return true;
		EfLocationSemi other = (EfLocationSemi)obj;
		
		return new EqualsBuilder()
		.append(getDeviceId(),other.getDeviceId())
		.append(getUserId(),other.getUserId())
		.append(getTime(),other.getTime())
		.append(getSource(),other.getSource())
		.append(getType(),other.getType())
		.append(getLongitude(),other.getLongitude())
		.append(getLatitude(),other.getLatitude())
		.append(getTimeBegin(),other.getTimeBegin())
		.append(getTimeEnd(),other.getTimeEnd())
		/*.append(getPreviousId(),other.getPreviousId())
		.append(getNextId(),other.getNextId())*/
		.append(getDistance(),other.getDistance())
		.append(getAddress(),other.getAddress())
		.append(getCreatorId(), other.getCreatorId())
		.append(getRemark(),other.getRemark()).isEquals();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id:"+this.getId() +"  userId:"+this.getUserId()+"  source:"+this.getSource()+"  type:"+this.getType() 
				+"  timeBegin:"+this.getTimeBegin()+"  timeEnd:"+this.getTimeEnd()+"  previousId:"+this.getPreviousId()
				+"  nextId:"+this.getNextId()+"  distance:"+this.getDistance()+"  address:"+this.getAddress()
				+"  sourceId:"+this.getSourceId()+"   time:"+this.getTime().toString()
				+"   distance:"+this.getDistance()+"  latitude:"+ this.getLatitude() +" longitude:"+ this.getLongitude();
		
	}
	@Override
	public EfLocationSemi clone() throws CloneNotSupportedException {
		return (EfLocationSemi)super.clone();
	}
    
}

