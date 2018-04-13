 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.dao.impl;




/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEjlComUserExtendInfoDao;
import com.winterframework.efamily.entity.EjlUserExtendInfo;


@Repository("ejlComUserExtendInfoDaoImpl")
public class EjlComUserExtendInfoDaoImpl <E extends EjlUserExtendInfo> extends BaseDaoImpl<EjlUserExtendInfo> implements IEjlComUserExtendInfoDao{
	@Override
	public EjlUserExtendInfo getByUserId(Long userId) throws Exception {
		return this.sqlSessionTemplate.selectOne(getQueryPath("getByUserId"), userId);
	}
	@Override
	public List<String> getAllCityName() throws Exception {
		return this.sqlSessionTemplate.selectList("getAllCityName");
	}
}
