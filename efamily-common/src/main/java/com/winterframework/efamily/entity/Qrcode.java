 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class Qrcode extends FmlBaseEntity {
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 4101824372913094731L;
	private String imei;	//设备IMEI
	private String imsi;	//SIM卡IMSI
	private String phoneNumber;	//电话号码
	private Integer status;	//状态(1:有效 0:失效)
	private String model;//型号
	private Integer type;    //设备类型(1手表 2手环...)
	private Integer simStatus;	//是否有卡(0无 1有)
	private Integer customerNumber;
	private String qrcodeResourceId;
	private String softwareVersion;	//软件版本
	
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
	
	public String getQrcodeResourceId() {
		return qrcodeResourceId;
	}
	public void setQrcodeResourceId(String qrcodeResourceId) {
		this.qrcodeResourceId = qrcodeResourceId;
	}
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSimStatus() {
		return simStatus;
	}
	public void setSimStatus(Integer simStatus) {
		this.simStatus = simStatus;
	}
	public Integer getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(Integer customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	
	
}
	

