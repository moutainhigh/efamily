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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.core.base.BaseDaoImpl;
import com.winterframework.efamily.dao.IEfComWeatherDao;
import com.winterframework.efamily.entity.EfWeather;


@Repository("efComWeatherDao")
public class EfComWeatherDao<E extends EfWeather> extends BaseDaoImpl<EfWeather> implements IEfComWeatherDao{

	/**
	* Title: deleteAll
	* Description:
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEfComWeatherDao#deleteAll() 
	*/
	@Override
	public void deleteAll() throws Exception {
		this.sqlSessionTemplate.delete("deleteAll");
	}

	@Override
	public EfWeather getWeatherByCityName(String cityName) throws Exception {
		return this.sqlSessionTemplate.selectOne("getWeatherByCityName", cityName);
	}
	
	@Override
	public EfWeather getNewWeatherByCityName(String cityName) throws Exception {
		Map map = new HashMap();
		map.put("cityName", cityName);
		map.put("solarDate", DateUtils.getEndTimeOfCurrentDate());
		return this.sqlSessionTemplate.selectOne("getNewWeatherByCityName", map);
	}

	@Override
	public List<EfWeather> getFutherWeather(String cityName, Date beginDate,
			Integer limit) throws Exception {
		Map map = new HashMap();
		map.put("cityName", cityName);
		map.put("beginDate", beginDate);
		map.put("limit", limit);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getFutherWeather"), map);
	}
	
	
}
