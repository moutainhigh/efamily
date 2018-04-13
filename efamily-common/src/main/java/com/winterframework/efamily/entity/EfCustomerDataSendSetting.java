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


public class EfCustomerDataSendSetting extends FmlBaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "EfCustomerDataSendSetting";
	public static final String ALIAS_CUSTOMER_ID = "客户id";
	public static final String ALIAS_DATA_TYPE = "告警类型";
	public static final String ALIAS_SEND_URL = "告警调用URL";
	public static final String ALIAS_STATUS = "0未发送 1已发送";
	public static final String ALIAS_METHOD = "调用方法";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATOR_ID = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATOR_ID = "更新人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
			public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
			public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
	private Long customerId;
	private Integer dataType;
	private String sendUrl;
	private Integer status;
	private String method;
	private String remark;
	private Long creatorId;
	private Date createTime;
	private Long updatorId;
	private Date updateTime;
	//columns END

	public EfCustomerDataSendSetting(){
	}

	public EfCustomerDataSendSetting(
		Long id
	){
		this.id = id;
	}

	public void setCustomerId(Long value) {
		this.customerId = value;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}
	public void setDataType(Integer value) {
		this.dataType = value;
	}
	
	public Integer getDataType() {
		return this.dataType;
	}
	public void setSendUrl(String value) {
		this.sendUrl = value;
	}
	
	public String getSendUrl() {
		return this.sendUrl;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setMethod(String value) {
		this.method = value;
	}
	
	public String getMethod() {
		return this.method;
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
		return "EfCustomerDataSendSetting [customerId=" + customerId
				+ ", dataType=" + dataType + ", sendUrl=" + sendUrl
				+ ", status=" + status + ", method=" + method + ", remark="
				+ remark + ", creatorId=" + creatorId + ", createTime="
				+ createTime + ", updatorId=" + updatorId + ", updateTime="
				+ updateTime + "]";
	}
 
	
}

