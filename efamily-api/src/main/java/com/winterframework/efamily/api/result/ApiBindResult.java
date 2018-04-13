 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.efamily.api.result;

import java.util.List;

import com.winterframework.efamily.api.dto.BindStruc;


public class ApiBindResult extends BaseApiResult{
	private List<BindStruc> bindList;	//绑定解绑设备列表

	public List<BindStruc> getBindList() {
		return bindList;
	}

	public void setBindList(List<BindStruc> bindList) {
		this.bindList = bindList;
	}
	
}

