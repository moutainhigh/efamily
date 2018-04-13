 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.entity;

import com.winterframework.efamily.core.base.FmlBaseEntity;
 
public class FmlResource extends FmlBaseEntity {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -607260349946847383L;
	private String resourceId;
	private String type;	//类型
	private String extType;	//扩展类型
	private String filePath;	//资源路径
	private Integer isMulti;	//是否多清度
	
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExtType() {
		return extType;
	}
	public void setExtType(String extType) {
		this.extType = extType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getIsMulti() {
		return isMulti;
	}
	public void setIsMulti(Integer isMulti) {
		this.isMulti = isMulti;
	}
	
}
	

