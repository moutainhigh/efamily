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
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComLunarDao;
import com.winterframework.efamily.entity.EfLunar;

@Repository("efComLunarDao")
public class EfComLunarDao<E extends EfLunar> extends BaseDaoImpl<EfLunar> implements IEfComLunarDao{
	@Override
	public EfLunar getEfLunarByDate(Date date) throws Exception {
		return this.sqlSessionTemplate.selectOne("getEfLunarByDate", date);
	}

	/**
	* Title: deleteByDate
	* Description:
	* @param date
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEfComLunarDao#deleteByDate(java.util.Date) 
	*/
	@Override
	public void deleteByDate(Date date) throws Exception {
		 this.sqlSessionTemplate.selectOne("deleteByDate", date);
	}
}
