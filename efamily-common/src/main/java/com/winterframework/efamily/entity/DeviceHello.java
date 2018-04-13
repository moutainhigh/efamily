package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;

public class DeviceHello extends FmlBaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6551105691865568517L;
	public static final String ALIAS_TYPE = "设备类型";
	public static final String ALIAS_IMEI = "设备类型";
	public static final String ALIAS_IMSI = "设备类型";
	
	private String imei;	//IMEI
	private String imsi;	//IMSI
	private Integer type;	//设备类型
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	
	
}
