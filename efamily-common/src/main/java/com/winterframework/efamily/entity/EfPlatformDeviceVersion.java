 
 package com.winterframework.efamily.entity;

import java.util.Date;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
/**
 * 平台设备版本
 * @ClassName
 * @Description
 * @author ibm
 * 2016年10月28日
 */
public class EfPlatformDeviceVersion extends FmlBaseEntity {
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 7698322401871252933L;
	private Integer deviceType;	//设备类型	同EjlDevice.type
	private String deviceModel;	//设备型号
	private String deviceVersion;	//设备版本号
	private String openVersion; //公开版本号
	private Date publishTime;	//发布时间
	private Integer status;	//是否可用
	
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceVersion() {
		return deviceVersion;
	}
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	public String getOpenVersion() {
		return openVersion;
	}
	public void setOpenVersion(String openVersion) {
		this.openVersion = openVersion;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
	

